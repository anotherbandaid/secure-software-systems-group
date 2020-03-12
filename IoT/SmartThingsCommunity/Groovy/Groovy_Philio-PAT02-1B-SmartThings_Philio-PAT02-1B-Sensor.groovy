/*
 * Philio PAT02-1B 2-in-1 Multi Sensor Device Type by He-Man321
 *
 * Based on Philio PST02-1A 4-in-1 Multi Sensor  by Ertan Deniz
 * AND Philio PSM02 4-in-1 Multi Sensor Device Type by eyeonall
 * AND PSM01 Sensor created by SmartThings/Paul Spee
 * AND SmartThings' Aeon MultiSensor 6 Device Type
 */

metadata {
    definition (name: "Philio PAT02-1B Sensor", namespace: "", author: "He-Man321") {
        capability "Temperature Measurement"
        capability "Relative Humidity Measurement"
        capability "Configuration"
        capability "Sensor"
        capability "Battery"
        capability "Tamper Alert"
        capability "Refresh"
        capability "Polling"
        command "clearTamper"
        //Not sure what the fingerprint is needed for?  Can it be removed???
        fingerprint deviceId: "0x0701", inClusters: "0x5E,0x72,0x86,0x59,0x73,0x5A,0x8F,0x98,0x7A", outClusters: "0x20"
        fingerprint mfr:"013C", prod:"0002", model:"0020"
    }
    tiles {
        valueTile("temperature", "device.temperature", inactiveLabel: false) {
            state "temperature", label:'${currentValue}°',
                    backgroundColors:[
                            [value: 31, color: "#153591"],
                            [value: 44, color: "#1e9cbb"],
                            [value: 59, color: "#90d2a7"],
                            [value: 74, color: "#44b621"],
                            [value: 84, color: "#f1d801"],
                            [value: 95, color: "#d04e00"],
                            [value: 96, color: "#bc2323"]
                    ]
        }
        valueTile("humidity", "device.humidity", inactiveLabel: false) {
            state "humidity", label:'${currentValue}°',
                    backgroundColors:[
                            [value: 31, color: "#153591"],
                            [value: 44, color: "#1e9cbb"],
                            [value: 59, color: "#90d2a7"],
                            [value: 74, color: "#44b621"],
                            [value: 84, color: "#f1d801"],
                            [value: 95, color: "#d04e00"],
                            [value: 96, color: "#bc2323"]
                    ]
        }        
        standardTile("tampering", "device.tamper", decoration: "flat", width: 1, height: 1) {			
			state "clear", label:"OK", backgroundColor: "#44b621"
			state "detected", label:"Tamper! (tap to clear)", action:"clearTamper", backgroundColor: "#bc2323"
		}        
        valueTile("battery", "device.battery", inactiveLabel: false, decoration: "flat") {
            state "battery", label:'${currentValue}% battery', unit:""
        }
        main(["temperature", "humidity"])
        details(["temperature", "humidity", "battery", "tampering", "configure", "refresh"])
    }
    preferences {
        input "tempOffset", "number", title: "Temperature Offset", description: "Adjust temperature by this many degrees", range: "*..*", displayDuringSetup: false
        input "humiOffset", "number", title: "Humidity Offset", description: "Adjust humidity by this percent", range: "*..*", displayDuringSetup: false
    }
}

preferences {
}

def installed() {
    log.debug "Installed with settings: ${settings}"
    updateDataValue("configured", "false") //wait until the next time device wakeup to send configure command after user change preference
}

def updated() {
    log.debug "Updated with settings: ${settings}"
    updateDataValue("configured", "false") //wait until the next time device wakeup to send configure command after user change preference
}

def parse(String description) {
    def result = null
    //Never seen this error occur, so presumably this IF can be removed???
    if (description.startsWith("Err 106")) {
        log.debug "PAT02: parse() >> Err 106"
        result = createEvent( name: "secureInclusion", value: "failed", isStateChange: true,
                descriptionText: "This sensor failed to complete the network security key exchange. If you are unable to control it via SmartThings, you must remove it from your network and add it again.")
    } else if (description != "updated") {
    	//Not sure what these values are for or if they are needed???
        def cmd = zwave.parse(description, [0x20: 1, 0x30: 2, 0x31: 5, 0x70: 1, 0x72: 1, 0x80: 1, 0x84: 2, 0x85: 1, 0x86: 1])
        if (cmd) {
            result = zwaveEvent(cmd)
        }
    }
    log.debug "PAT02: after zwaveEvent(cmd) >> Parsed '${description}' to ${result.inspect()}"
    return result
}

def zwaveEvent(physicalgraph.zwave.commands.securityv1.SecurityMessageEncapsulation cmd) {
	//Not sure what these values are for or if they are needed???
    def encapsulatedCommand = cmd.encapsulatedCommand([0x20: 1, 0x30: 2, 0x31: 5, 0x70: 1, 0x72: 1, 0x80: 1, 0x84: 2, 0x85: 1, 0x86: 1])
    log.debug "PAT02: encapsulated: ${encapsulatedCommand}"
    if (encapsulatedCommand) {
        zwaveEvent(encapsulatedCommand)
    } else {
        log.warn "PAT02: unable to extract encapsulated cmd from $cmd!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
        createEvent(descriptionText: cmd.toString())
    }
}

def zwaveEvent(physicalgraph.zwave.commands.wakeupv2.WakeUpNotification cmd) {
	//This device will send reasonably frequent updates anyway, so not sure if this section is needed???
    def result = [createEvent(descriptionText: "${device.displayName} woke up", isStateChange: false)]
    def cmds = []
    if (!state.lastbatt || now() - state.lastbatt > 24*60*60*1000) {
        result << response(command(zwave.batteryV1.batteryGet()))
        result << response("delay 1200")  // leave time for device to respond to batteryGet
    }
    if (getDataValue("configured") == "true") {
        log.debug("PAT02: late configure")
        result << response(configure())
    } else {
        log.debug("PAT02: Device has been configured sending >> wakeUpNoMoreInformation()")
        cmds << zwave.wakeUpV2.wakeUpNoMoreInformation().format()
        result << response(cmds)
    }
    result
}

def zwaveEvent(physicalgraph.zwave.commands.sensormultilevelv5.SensorMultilevelReport cmd)
{
    log.debug "PAT02: SensorMultilevel ${cmd.toString()} Type ${cmd.sensorType.toString()}"
    def map = [:]
    switch (cmd.sensorType) {
        case 1:
            // temperature
            def cmdScale = cmd.scale == 1 ? "F" : "C"
            map.value = convertTemperatureIfNeeded(cmd.scaledSensorValue, cmdScale, 1)
            map.unit = getTemperatureScale()
            map.name = "temperature"
            if (tempOffset) {
                def offset = tempOffset as int
                def v = map.value as int
                map.value = v + offset
            }
            log.debug "PAT02: adjusted temp value ${map.value}"
            break;
        case 5:
            // humidity
            map.value = cmd.scaledSensorValue.toInteger().toString()
            map.unit = "%"
            map.name = "humidity"
            if (humiOffset) {
                def offset = humiOffset as int
                def v = map.value as int
                map.value = v + offset
            }
            log.debug "PAT02: adjusted humidity value ${map.value}"
            break;
    }
    createEvent(map)
}

def zwaveEvent(physicalgraph.zwave.commands.batteryv1.BatteryReport cmd) {
    log.debug "PAT02: BatteryReport ${cmd.toString()}}"
    def map = [:]
    map.name = "battery"
    map.value = cmd.batteryLevel > 0 ? cmd.batteryLevel.toString() : 1
    map.unit = "%"
    map.displayed = false
    createEvent(map)
}

def zwaveEvent(physicalgraph.zwave.commands.notificationv3.NotificationReport cmd) {    
    def map = [:]
    if (cmd.notificationType == 7) {
        map.name = "tamper"
		map.value = "detected"
		map.descriptionText = "$device.displayName is detected"
        createEvent(map)
    }
}

def clearTamper() {
	log.debug "PAT02: clearing tamper"
    def map = [:]
	map.name = "tamper"
	map.value = "clear"
	map.descriptionText = "$device.displayName is cleared"
	createEvent(map)
    sendEvent(map)
}

def zwaveEvent(physicalgraph.zwave.Command cmd) {
    log.debug "PAT02: Catchall reached for cmd: ${cmd.toString()}!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
}

def configure() {
    log.debug "PAT02: configure() called"
    def request = []
	request << zwave.configurationV1.configurationSet(parameterNumber: 10, size: 1, scaledConfigurationValue: 12) // Auto report Battery time 1-127
    request << zwave.configurationV1.configurationSet(parameterNumber: 13, size: 1, scaledConfigurationValue: 6) // Auto report Temperature time 1-127
    request << zwave.configurationV1.configurationSet(parameterNumber: 14, size: 1, scaledConfigurationValue: 6) // Auto report Humidity time 1-127
    request << zwave.configurationV1.configurationSet(parameterNumber: 21, size: 1, scaledConfigurationValue: 3) // Report temprature when it changes 3 Fahrenheit
    request << zwave.configurationV1.configurationSet(parameterNumber: 23, size: 1, scaledConfigurationValue: 1) // Report humidity when it changes 1%
	updateDataValue("configured", "true")
}