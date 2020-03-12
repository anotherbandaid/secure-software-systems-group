import groovy.json.JsonOutput

/**
 *
 * Arrival Sensor HA with Disable
 *
 *  Copyright 2018 Warren Poschman
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
 
def dthVersion() {
	return "1.1"
}

/*
* Change Log:
* 2018-7-29  - (1.1) Improved tracking and added user options to force mode upon enable / disable
* 2018-7-26  - (1.0) Initial release
* 2018-7-24  - (0.1) Debug release
*/


metadata {
    definition (name: "Arrival Sensor HA with Disable", namespace: "LLWarrenP", author: "LLWarrenP") {
        capability "Tone"
        capability "Actuator"
        capability "Presence Sensor"
        capability "Sensor"
        capability "Battery"
        capability "Configuration"
        capability "Health Check"

		attribute "enabled", "string"
        command "enable"
        command "disable"
        command "toggle"

        fingerprint inClusters: "0000,0001,0003,000F,0020", outClusters: "0003,0019",
                        manufacturer: "SmartThings", model: "tagv4", deviceJoinName: "Arrival Sensor"
    }

    preferences {
        section {
            image(name: 'educationalcontent', multiple: true, images: [
                "http://cdn.device-gse.smartthings.com/Arrival/Arrival1.png",
                "http://cdn.device-gse.smartthings.com/Arrival/Arrival2.png"
                ])
        }
        section {
            input "checkInterval", "enum", title: "Presence timeout (minutes)", description: "Tap to set",
                    defaultValue:"2", options: ["2", "3", "5"], displayDuringSetup: false
        }
        section {
            input "disabledMode", "enum", title: "When disabling sensor, set presence to:", description: "Tap to set",
                    defaultValue:"auto", options: ["auto", "present", "not present"], displayDuringSetup: false
            input "enabledMode", "enum", title: "When enabling sensor, set presence to:", description: "Tap to set",
                    defaultValue:"auto", options: ["auto", "present", "not present"], displayDuringSetup: false
        }
    }

    tiles {
        standardTile("presence", "device.presence", width: 2, height: 2, canChangeBackground: true) {
            state "present", labelIcon:"st.presence.tile.present", backgroundColor:"#00a0dc"
            state "not present", labelIcon:"st.presence.tile.not-present", backgroundColor:"#ffffff"
        }
        standardTile("beep", "device.beep", decoration: "flat") {
            state "beep", label:'', action:"tone.beep", icon:"st.secondary.beep", backgroundColor:"#ffffff"
        }
        standardTile("enabled", "device.enabled", width: 1, height: 1) {
			state "disabled-present", label:'DISABLED', action:"toggle", icon: "st.sonos.pause-icon", backgroundColor:"#d64040"
			state "disabled-not present", label:'DISABLED', action:"toggle", icon: "st.sonos.pause-icon", backgroundColor:"#d64040"
			state "enabled-present", label:'ENABLED', action:"toggle", icon: "st.sonos.play-icon", backgroundColor:"#64d640"
			state "enabled-not present", label:'ENABLED', action:"toggle", icon: "st.sonos.play-icon", backgroundColor:"#64d640"
        }
        valueTile("battery", "device.battery", decoration: "flat", inactiveLabel: false) {
            state "battery", label:'${currentValue}% battery', unit:""
        }

        main "presence"
        details(["presence", "beep", "enabled", "battery"])
    }
}

def updated() {
    startTimer()
}

def installed() {
    // Arrival sensors only goes OFFLINE when Hub is off
    sendEvent(name: "DeviceWatch-Enroll", value: JsonOutput.toJson([protocol: "zigbee", scheme:"untracked"]), displayed: false)
}

def configure() {
    def cmds = zigbee.readAttribute(zigbee.POWER_CONFIGURATION_CLUSTER, 0x0020) + zigbee.batteryConfig(20, 20, 0x01)
    log.debug "configure -- cmds: ${cmds}"
    return cmds
}

def beep() {
    log.debug "Sending Identify command to beep the sensor for 5 seconds"
    return zigbee.command(0x0003, 0x00, "0500")
}

def parse(String description) {
    state.lastCheckin = now()
    handlePresenceEvent(true)

    if (description?.startsWith('read attr -')) {
        handleReportAttributeMessage(description)
    }

    return []
}

private handleReportAttributeMessage(String description) {
    def descMap = zigbee.parseDescriptionAsMap(description)
    if (descMap.clusterInt == 0x0001 && descMap.attrInt == 0x0020) {
        handleBatteryEvent(Integer.parseInt(descMap.value, 16))
    }
}

/**
 * Create battery event from reported battery voltage.
 *
 * @param volts Battery voltage in .1V increments
 */
private handleBatteryEvent(volts) {
	def descriptionText
    if (volts == 0 || volts == 255) {
        log.debug "Ignoring invalid value for voltage (${volts/10}V)"
    }
    else {
        def batteryMap = [28:100, 27:100, 26:100, 25:90, 24:90, 23:70,
                          22:70, 21:50, 20:50, 19:30, 18:30, 17:15, 16:1, 15:0]
        def minVolts = 15
        def maxVolts = 28

        if (volts < minVolts)
            volts = minVolts
        else if (volts > maxVolts)
            volts = maxVolts
        def value = batteryMap[volts]
        if (value != null) {
            def linkText = getLinkText(device)
            descriptionText = '{{ linkText }} battery was {{ value }}'
            def eventMap = [
                name: 'battery',
                value: value,
                descriptionText: descriptionText,
                translatable: true
            ]
            log.debug "Creating battery event for voltage=${volts/10}V: ${linkText} ${eventMap.name} is ${eventMap.value}%"
            sendEvent(eventMap)
        }
    }
}

private handlePresenceEvent(present) {
    def wasPresent = device.currentState("presence")?.value == "present"
    if (!wasPresent && present) {
        log.debug "Sensor is present"
        startTimer()
    } else if (!present) {
        log.debug "Sensor is not present"
        stopTimer()
    }
    def linkText = getLinkText(device)
    def descriptionText
    def enabledStatus = ""
    if ( present )
    	descriptionText = "{{ linkText }} has arrived"
    else
    	descriptionText = "{{ linkText }} has left"
    if ((device.currentValue("enabled") == "disabled-present") || (device.currentValue("enabled") == "disabled-not present")) {
    	// Device is disabled so we won't generate a presence event but instead just track the status behind the scenes by generating an enabled event
    	log.debug "${linkText} is ${device.currentValue("enabled")}: not creating presence event"
        enabledStatus = "disabled-"
        enabledStatus = enabledStatus.concat(present ? "present" : "not present")
		if (device.currentValue("enabled") != enabledStatus) sendEvent(name: "enabled", value: enabledStatus, isStateChange: true)
	}
    else {
    	// Device is enabled so we will generate a presence event and an enabled event
	    def eventMap = [
        	name: "presence",
        	value: present ? "present" : "not present",
        	linkText: linkText,
        	descriptionText: descriptionText,
        	translatable: true
    		]
        enabledStatus = "enabled-"
        enabledStatus = enabledStatus.concat(present ? "present" : "not present")
		if (device.currentValue("enabled") != enabledStatus) sendEvent(name: "enabled", value: enabledStatus, isStateChange: true)
	   	log.debug "Creating presence event: ${device.displayName} ${eventMap.name} is ${eventMap.value} with status ${device.currentValue("enabled")}"
    	sendEvent(eventMap)
    }
}

private startTimer() {
    log.debug "Scheduling periodic timer"
    runEvery1Minute("checkPresenceCallback")
}

private stopTimer() {
    log.debug "Stopping periodic timer"
    unschedule()
}

def checkPresenceCallback() {
    def timeSinceLastCheckin = (now() - state.lastCheckin) / 1000
    def theCheckInterval = (checkInterval ? checkInterval as int : 2) * 60
    log.debug "Sensor checked in ${timeSinceLastCheckin} seconds ago"
    if (timeSinceLastCheckin >= theCheckInterval) {
        handlePresenceEvent(false)
    }
}

def toggle() {
	// Button pressed, toggle the enabled state (which also tracks the current presence)
	if ((device.currentValue("enabled") == "enabled-present") || (device.currentValue("enabled") == "enabled-not present"))
    	disable()
    else
    	enable()
}

def enable() {
    // Force presence per user settings
    log.debug "Setting sensor presence to ${settings.enabledMode}"
	if (settings.enabledMode && (settings.enabledMode != "auto")) {
    	stopTimer()
    	sendEvent(name: "presence", value: settings.enabledMode, translatable: true)
        }
    else if (settings.enabledMode && (settings.enabledMode == "auto"))
	    startTimer()
	// Enable the device and update the enabled status to reflect the new status
	log.debug "Enabling ${getLinkText(device)}"
    if (device.currentValue("presence") == "present")
		sendEvent(name: "enabled", value: "enabled-present", isStateChange: true)
    else if (device.currentValue("presence") == "not present")
		sendEvent(name: "enabled", value: "enabled-not present", isStateChange: true)
}

def disable() {
    // Force presence per user settings
    log.debug "Setting sensor presence to ${settings.disabledMode}"
	if (settings.disabledMode && (settings.disabledMode != "auto")) {
    	stopTimer()
    	sendEvent(name: "presence", value: settings.disabledMode, translatable: true)
        }
    else if (settings.disabledMode && (settings.disabledMode == "auto"))
	    startTimer()
	// Disable the device and update the enabled status to reflect the new status
	log.debug "Disabling ${getLinkText(device)}"
    state.updatePresence = false
    if (device.currentValue("presence") == "present")
		sendEvent(name: "enabled", value: "disabled-present", isStateChange: true)
    else if (device.currentValue("presence") == "not present")
		sendEvent(name: "enabled", value: "disabled-not present", isStateChange: true)
}