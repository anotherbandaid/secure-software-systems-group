/*
*  Sonoff S20 - Device Handler
*  Type: Device Handler
* 
*  Copyright 2007 (C) chancsc@gmail.com
* 
*  v1.0
*/

import groovy.json.JsonSlurper

metadata {
	definition (name: "Sonoff", namespace: "csc", author: "CSC") {
        capability "Switch"
        capability "Refresh"
        capability "Indicator"
        
		attribute "triggerswitch", "string"
		command "DeviceTrigger"
        command	"led"
	}

	preferences {
		input("DeviceIP", "string", title:"Device IP Address", description: "Please enter your device's IP Address", required: true, displayDuringSetup: true)
		input("DevicePort", "string", title:"Device Port", description: "Empty assumes port 80.", required: false, displayDuringSetup: true)
		//input("DevicePathOn", "string", title:"URL Path for ON", description: "Rest of the URL, include forward slash.", displayDuringSetup: true)
		//input("DevicePathOff", "string", title:"URL Path for OFF", description: "Rest of the URL, include forward slash.", displayDuringSetup: true)
		input(name: "DevicePostGet", type: "enum", title: "POST or GET", options: ["POST","GET"], defaultValue: "GET", required: false, displayDuringSetup: true)
		section() {
			input("HTTPAuth", "bool", title:"Requires User Auth?", description: "Choose if the HTTP requires basic authentication", defaultValue: false, required: true, displayDuringSetup: true)
			input("HTTPUser", "string", title:"HTTP User", description: "Enter your basic username", required: false, displayDuringSetup: true)
			input("HTTPPassword", "string", title:"HTTP Password", description: "Enter your basic password", required: false, displayDuringSetup: true)
		}
	}


	// simulator metadata
	simulator {
	}

	// UI tile definitions
	tiles {
		standardTile("DeviceTrigger", "device.triggerswitch", width: 2, height: 2, canChangeIcon: true) {
			state "triggeroff", label: 'Off', action: "on", icon: "st.switches.switch.off", backgroundColor: "#ffffff", nextState: "on"
			state "triggeron", label: 'On', action: "off", icon: "st.switches.switch.on", backgroundColor: "#79b821", nextState: "off"
		}
        standardTile("refresh", "command.refresh", decoration: "flat") {
			state "default", label:'', action:"refresh.refresh", icon:"st.secondary.refresh"
		}
		standardTile("indicator", "device.indicatorStatus", inactiveLabel: false, decoration: "flat") {
			state "off", action:"indicator.indicatorWhenOff", icon:"st.indicators.lit-when-on"
			state "on", action:"indicator.indicatorWhenOn", icon:"st.indicators.never-on"
		}
                
		main "DeviceTrigger"
			details (["DeviceTrigger", "refresh", "indicator"])
	}
}

def parse(String description) {
	log.debug(description)
}

def on() {
	def DevicePathOn = "/control?cmd=gpio%2C12%2C1"
	log.debug "---ON COMMAND--- ${DevicePathOn}"
    sendEvent(name: "triggerswitch", value: "triggeron", isStateChange: true)
	runCmd(DevicePathOn)
}

def off() {
	def DevicePathOff = "/control?cmd=gpio%2C12%2C0"
	log.debug "---OFF COMMAND--- ${DevicePathOff}"
    sendEvent(name: "triggerswitch", value: "triggeroff", isStateChange: true)
    runCmd(DevicePathOff)
}

def indicatorWhenOn() {
	def DevicePathOn = "/control?cmd=gpio%2C13%2C0"
	log.debug "---on COMMAND--- ${DevicePathOn}"
    sendEvent(name: "indicatorStatus", value: "off", isStateChange: true)
    runCmd(DevicePathOn)
}

def indicatorWhenOff() {
	def DevicePathOff = "/control?cmd=gpio%2C13%2C1"
	log.debug "---OFF COMMAND--- ${DevicePathOff}"
    sendEvent(name: "indicatorStatus", value: "on", isStateChange: true)
    runCmd(DevicePathOff)
}

def runCmd(String varCommand) {
	def host = DeviceIP
	def LocalDevicePort = ''
	if (DevicePort==null) { LocalDevicePort = "80" } else { LocalDevicePort = DevicePort }

	def userpassascii = "${HTTPUser}:${HTTPPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()

	log.debug "The device id configured is: $device.deviceNetworkId"

	def path = varCommand
	log.debug "path is: $path"
	//log.debug "Uses which method: $DevicePostGet"
	def body = "" 
	//log.debug "body is: $body"

	def headers = [:] 
	headers.put("HOST", "$host:$LocalDevicePort")
	headers.put("Content-Type", "application/x-www-form-urlencoded")
	if (HTTPAuth) {
		headers.put("Authorization", userpass)
	}
	log.debug "The Header is $headers"
	def method = "POST"
	try {
		if (DevicePostGet.toUpperCase() == "GET") {
			method = "GET"
			}
		}
	catch (Exception e) {
		settings.DevicePostGet = "POST"
		log.debug e
		log.debug "You must not have set the preference for the DevicePOSTGET option"
	}
	log.debug "The method is $method"
	try {
		def hubAction = new physicalgraph.device.HubAction(
			method: method,
			path: path,
			body: body,
			headers: headers
			)
		log.debug hubAction
		return hubAction
	}
	catch (Exception e) {
		log.debug "Hit Exception $e on $hubAction"
	}
    
    //sendEvent
    if (varCommand == "off"){
    	sendEvent(name: "switch", value: "off")
        log.debug "Executing OFF"
    } else {
    	sendEvent(name: "switch", value: "on")
        log.debug "Executing ON"
    }
    
}
