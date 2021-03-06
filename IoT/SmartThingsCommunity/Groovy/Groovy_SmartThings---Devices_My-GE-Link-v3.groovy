/**
 *  GE Link Bulb
 *
 *  Copyright 2014 SmartThings
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
 *  Thanks to Chad Monroe @cmonroe and Patrick Stuart @pstuart, and others
 *
 ******************************************************************************
 *                                Changes
 ******************************************************************************
 *
 *  Change 1:	2014-10-10 (wackford)
 *				Added setLevel event so subscriptions to the event will work
 *  Change 2:	2014-12-10 (jscgs350 using Sticks18's code and effort!)
 *				Modified parse section to properly identify bulb status in the app when manually turned on by a physical switch
 *  Change 3:	2014-12-12 (jscgs350, Sticks18's)
 *				Modified to ensure dimming was smoother, and added fix for dimming below 7
 *	Change 4:	2014-12-14 Part 1 (Sticks18)
 *				Modified to ignore unnecessary level change responses to prevent level skips
 *	Change 5:	2014-12-14 Part 2 (Sticks18, jscgs350)
 *				Modified to clean up trace&debug logging, added new code from @sticks18 for parsing "on/off" to determine if the bulb is manually turned on and immediately update the app
 *	Change 6:	2015-01-02	(Sticks18)
 *				Modified to allow dim rate in Preferences. Added ability to dim during On/Off commands and included this option in Preferences. Defaults are "Normal" and no dim for On/Off.
 *	Change 7:	2015-01-09	(tslagle13)
 *				dimOnOff is was boolean, and switched to enum. Properly update "rampOn" and "rampOff" when refreshed or a polled (dim transition for On/Off commands)
 *	Change 8:	2015-03-06	(Juan Risso)
 *				Slider range from 0..100
 *	Change 9:	2015-03-06	(Juan Risso)
 *				Setlevel -> value to integer (to prevent smartapp calling this function from not working).
 *  Change 10:  2015-09-06  (Sticks18)
 *              Modified tile layout to make use of new multiattribute tile for dimming. Added dim adjustment when sending setLevel() if bulb is off, so it will transition smoothly from 0.
 *  Change 11: 2016-01-03  (Sticks18)
 *              Added alert feature, updated layout and improved setLevel to accept any duration.
 *
 */
metadata {
	definition (name: "My GE Link Bulb v3", namespace: "jscgs350", author: "smartthings") {

    	capability "Actuator"
        capability "Configuration"
        capability "Refresh"
		capability "Sensor"
        capability "Switch"
		capability "Switch Level"
        capability "Polling"
        
        command "alert"
        
        attribute "attDimRate", "string"
        attribute "attDimOnOff", "string"

		fingerprint profileId: "0104", inClusters: "0000,0003,0004,0005,0006,0008,1000", outClusters: "0019"
	}

	// UI tile definitions
	tiles(scale: 2) {
		multiAttributeTile(name: "switch", type: "lighting", width: 6, height: 4, canChangeIcon: true, canChangeBackground: true) {
			tileAttribute("device.switch", key: "PRIMARY_CONTROL") {
                  attributeState "off", label: '${name}', action: "switch.on", icon: "st.switches.light.off", backgroundColor: "#ffffff", nextState: "turningOn"
			      attributeState "on", label: '${name}', action: "switch.off", icon: "st.switches.light.on", backgroundColor: "#79b821", nextState: "turningOff"
                  attributeState "turningOff", label: '${name}', action: "switch.on", icon: "st.switches.light.off", backgroundColor: "#ffffff", nextState: "turningOn"
			      attributeState "turningOn", label: '${name}', action: "switch.off", icon: "st.switches.light.on", backgroundColor: "#79b821", nextState: "turningOff"
            }
            tileAttribute("device.level", key: "SLIDER_CONTROL") {
                  attributeState "level", action:"switch level.setLevel"
            }
            tileAttribute("level", key: "SECONDARY_CONTROL") {
                  attributeState "level", label: 'Light dimmed to ${currentValue}%'
            }    
		}
		standardTile("refresh", "device.switch", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
		}
        valueTile("attDimRate", "device.attDimRate", inactiveLabel: false, decoration: "flat", width: 4, height: 1) {
			state "attDimRate", label: 'Dim rate: ${currentValue}'
		}
        valueTile("attDimOnOff", "device.attDimOnOff", inactiveLabel: false, decoration: "flat", width: 4, height: 1) {
			state "attDimOnOff", label: 'Dim for on/off: ${currentValue}'
		}
		controlTile("levelSliderControl", "device.level", "slider", height: 2, width: 6, inactiveLabel: false, range: "(0..100)") {
			state "level", action:"switch level.setLevel"
		}
        
        
		main "switch"
		details(["switch","attDimRate", "refresh", "attDimOnOff","levelSliderControl"])
	}
	
	    preferences {
        
        	input("dimRate", "enum", title: "Dim Rate", options: ["Instant", "Normal", "Slow", "Very Slow"], defaultValue: "Normal", required: false, displayDuringSetup: true)
            input("dimOnOff", "enum", title: "Dim transition for On/Off commands?", options: ["Yes", "No"], defaultValue: "No", required: false, displayDuringSetup: true)
            
    }
}

// Parse incoming device messages to generate events
def parse(String description) {
	log.trace description
    
    if (description?.startsWith("on/off:")) {    
		log.debug "The bulb was sent a command to do something just now..."
		if (description[-1] == "1") {
        	def result = createEvent(name: "switch", value: "on")
            log.debug "On command was sent maybe from manually turning on? : Parse returned ${result?.descriptionText}"
            return result
        } else if (description[-1] == "0") {
        	def result = createEvent(name: "switch", value: "off")
            log.debug "Off command was sent : Parse returned ${result?.descriptionText}"
            return result
        }
    }
    
    def msg = zigbee.parse(description)

	if (description?.startsWith("catchall:")) {
		// log.trace msg
		// log.trace "data: $msg.data"

        def x = description[-4..-1]
        // log.debug x
		def z = description[18]
        
        if (z == "8" || msg.cluster == 3){}
        
        else {
        
        	switch (x) 
        	{

        		case "0000":

            		def result = createEvent(name: "switch", value: "off")
            		log.debug "${result?.descriptionText}"
           			return result
                	break

            	case "1000":

            		def result = createEvent(name: "switch", value: "off")
            		log.debug "${result?.descriptionText}"
           			return result
                	break

            	case "0100":

            		def result = createEvent(name: "switch", value: "on")
            		log.debug "${result?.descriptionText}"
           			return result
                	break

            	case "1001":

            		def result = createEvent(name: "switch", value: "on")
            		log.debug "${result?.descriptionText}"
           			return result
                	break
        	}
    	}
    }    

    if (description?.startsWith("read attr")) {

        // log.trace description[27..28]
        // log.trace description[-2..-1]

    	if (description[27..28] == "0A") {

        	// log.debug description[-2..-1]
        	def i = Math.round(convertHexToInt(description[-2..-1]) / 256 * 100 )
			sendEvent( name: "level", value: i )
        	sendEvent( name: "switch.setLevel", value: i) //added to help subscribers

    	} 

    	else {

    		if (description[-2..-1] == "00" && state.trigger == "setLevel") {
        		// log.debug description[-2..-1]
        		def i = Math.round(convertHexToInt(description[-2..-1]) / 256 * 100 )
				sendEvent( name: "level", value: i )
        		sendEvent( name: "switch.setLevel", value: i) //added to help subscribers   
        	}    

        	if (description[-2..-1] == state.lvl) {
        		// log.debug description[-2..-1]
        		def i = Math.round(convertHexToInt(description[-2..-1]) / 256 * 100 )
				sendEvent( name: "level", value: i )
        		sendEvent( name: "switch.setLevel", value: i) //added to help subscribers
        	}    

    	}
    }

}

def alert(alertTime = 7) {
    log.debug alertTime
    def payload = swapEndianHex(hex(alertTime,4))
    "st cmd 0x${device.deviceNetworkId} 1 3 0 {${payload}}"
}

def poll() {

    [
	"st rattr 0x${device.deviceNetworkId} 1 6 0", "delay 500",
    "st rattr 0x${device.deviceNetworkId} 1 8 0", "delay 500",
    "st wattr 0x${device.deviceNetworkId} 1 8 0x10 0x21 {${state.dOnOff}}"
    ]
    
}

def updated() {
	
    sendEvent( name: "attDimRate", value: "${dimRate}" )
    sendEvent( name: "attDimOnOff", value: "${dimOnOff}" )
    
	state.dOnOff = "0000"
    
	if (dimRate) {

		switch (dimRate) 
        	{

        		case "Instant":

            		state.rate = "0000"
                	if (dimOnOff) { state.dOnOff = "0000"}
                    break

            	case "Normal":

            		state.rate = "1500"
                    if (dimOnOff) { state.dOnOff = "0015"}
                	break

            	case "Slow":

            		state.rate = "2500"
                    if (dimOnOff) { state.dOnOff = "0025"}
               		break
                
            	case "Very Slow":
            
            		state.rate = "3500"
                    if (dimOnOff) { state.dOnOff = "0035"}
                	break

        	}
    
    }
    
    else {
    
    	state.rate = "1500"
        state.dOnOff = "0000"
        
    }
    
        if (dimOnOff == "Yes"){
			switch (dimOnOff){
        		case "InstantOnOff":

            		state.rate = "0000"
                	if (state.rate == "0000") { state.dOnOff = "0000"}
                    break

            	case "NormalOnOff":

            		state.rate = "1500"
                    if (state.rate == "1500") { state.dOnOff = "0015"}
                	break

            	case "SlowOnOff":

            		state.rate = "2500"
                    if (state.rate == "2500") { state.dOnOff = "0025"}
               		break
                
            	case "Very SlowOnOff":
            
            		state.rate = "3500"
                    if (state.rate == "3500") { state.dOnOff = "0035"}
                	break

        	}
            
    }
    else{
    	state.dOnOff = "0000"
    }
    
    "st wattr 0x${device.deviceNetworkId} 1 8 0x10 0x21 {${state.dOnOff}}"


}

def on() {
	state.lvl = "00"
    state.trigger = "on/off"

    // log.debug "on()"
	sendEvent(name: "switch", value: "on")
	"st cmd 0x${device.deviceNetworkId} 1 6 1 {}"
}

def off() {
	state.lvl = "00"
    state.trigger = "on/off"

    // log.debug "off()"
	sendEvent(name: "switch", value: "off")
	"st cmd 0x${device.deviceNetworkId} 1 6 0 {}"
}

def refresh() {
    
    [
	"st rattr 0x${device.deviceNetworkId} 1 6 0", "delay 500",
    "st rattr 0x${device.deviceNetworkId} 1 8 0", "delay 500",
    "st wattr 0x${device.deviceNetworkId} 1 8 0x10 0x21 {${state.dOnOff}}"
    ]
    poll()
    
}

def setLevel(value, duration = 2.0) {

    def cmds = []

	if (value == 0) {
		sendEvent(name: "switch", value: "off")
		cmds << "st cmd 0x${device.deviceNetworkId} 1 8 0 {00 ${state.rate}}"
	}
	else if (device.latestValue("switch") == "off") {
        cmds << "st cmd 0x${device.deviceNetworkId} 1 8 0 {00 0000}"
        sendEvent(name: "switch", value: "on")
	}

    sendEvent(name: "level", value: value)
    value = (value * 255 / 100)
    def level = hex(value);
    
    duration = duration * 10
    def tranTime = swapEndianHex(hex(duration, 4))

    state.trigger = "setLevel"
    state.lvl = "${level}"

    cmds << "st cmd 0x${device.deviceNetworkId} 1 8 4 {${level} ${tranTime}}"

    log.debug cmds
    cmds
}

def configure() {

	String zigbeeId = swapEndianHex(device.hub.zigbeeId)
	log.debug "Confuguring Reporting and Bindings."
	def configCmds = [	

        //Switch Reporting
        "zcl global send-me-a-report 6 0 0x10 0 3600 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 1", "delay 1000",

        //Level Control Reporting
        "zcl global send-me-a-report 8 0 0x20 5 3600 {0010}", "delay 200",
        "send 0x${device.deviceNetworkId} 1 1", "delay 1500",

        "zdo bind 0x${device.deviceNetworkId} 1 1 6 {${device.zigbeeId}} {}", "delay 1000",
		"zdo bind 0x${device.deviceNetworkId} 1 1 8 {${device.zigbeeId}} {}", "delay 500",
	]
    return configCmds + refresh() // send refresh cmds as part of config
}

private hex(value, width=2) {
	def s = new BigInteger(Math.round(value).toString()).toString(16)
	while (s.size() < width) {
		s = "0" + s
	}
	s
}

private Integer convertHexToInt(hex) {
	Integer.parseInt(hex,16)
}

private getEndpointId() {
    new BigInteger(device.endpointId, 16).toString()
}

private String swapEndianHex(String hex) {
    reverseArray(hex.decodeHex()).encodeHex()
}

private byte[] reverseArray(byte[] array) {
    int i = 0;
    int j = array.length - 1;
    byte tmp;
    while (j > i) {
        tmp = array[j];
        array[j] = array[i];
        array[i] = tmp;
        j--;
        i++;
    }
    return array
}
