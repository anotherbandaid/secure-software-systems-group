/**
 *  Generic HTTP Device v1.0.20160402
 *
 *  Source code can be found here: https://github.com/JZ-SmartThings/SmartThings/blob/master/Devices/Generic%20HTTP%20Device/GenericHTTPDevice.groovy
 *
 *  Copyright 2016 JZ
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

import groovy.json.JsonSlurper

metadata {
	definition (name: "Generic HTTP Device - ESP8266 - Tower Fan", author: "JZ", namespace:"JZ") {
		capability "Switch"
		attribute "triggerswitch", "string"
		attribute "tvswitch", "string"
		attribute "timeradd", "string"
		attribute "speed", "string"
		attribute "modeswitch", "string"
        
		command "DeviceTrigger"
		command "TvTrigger"
		command "speedTrigger"
		command "timerAddTrigger"
		command "modeTrigger"
	}


	preferences {
		input("DeviceIP", "string", title:"Device IP Address", description: "Please enter your device's IP Address", required: true, displayDuringSetup: true)
		input("DevicePort", "string", title:"Device Port", description: "Please enter port 80 or your device's Port", required: true, displayDuringSetup: true)
		input("DevicePath", "string", title:"URL Path", description: "Rest of the URL, include forward slash.", displayDuringSetup: true)
		input(name: "DevicePostGet", type: "enum", title: "POST or GET", options: ["POST","GET"], required: true, displayDuringSetup: true)
		input("DeviceBodyText", "string", title:'Body Content', description: 'Type in "GateTrigger=" or "CustomTrigger="', required: true, displayDuringSetup: true)
		input("UseJSON", "bool", title:"Use JSON instead of HTML?", description: "Use JSON instead of HTML?", defaultValue: false, required: false, displayDuringSetup: true)
		section() {
			input("HTTPAuth", "bool", title:"Requires User Auth?", description: "Choose if the HTTP requires basic authentication", defaultValue: false, required: true, displayDuringSetup: true)
			input("HTTPUser", "string", title:"HTTP User", description: "Enter your basic username", required: false, displayDuringSetup: true)
			input("HTTPPassword", "string", title:"HTTP Password", description: "Enter your basic password", required: false, displayDuringSetup: true)
		}
	}

	simulator {
	}

	tiles {
		standardTile("DeviceTrigger", "device.triggerswitch", width: 2, height: 2, canChangeIcon: true, canChangeBackground: true) {
			state "triggeroff", label:'OFF' , action: "on", icon: "st.Appliances.appliances11", backgroundColor:"#ffffff", nextState: "trying"
			state "triggeron", label: 'ON', action: "off", icon: "st.Appliances.appliances11", backgroundColor: "#79b821", nextState: "trying"
			state "trying", label: 'TRYING', action: "", icon: "st.Appliances.appliances11", backgroundColor: "#FFAA33"
		}
		standardTile("TvTrigger", "device.tvswitch", width: 2, height: 2, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label:'TV' , action: "TvTrigger", icon: "st.Electronics.electronics18", backgroundColor:"#ffffff", nextState: "trying"
			state "trying", label: 'TRYING', action: "", icon: "st.Electronics.electronics18", backgroundColor: "#FFAA33"
		}    
		standardTile("speedTrigger", "device.speed", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label:'Speed' , action: "speedTrigger", icon: "st.Weather.weather1", backgroundColor:"#ff4c4c", nextState: "trying"
			state "trying", label: 'TRYING', action: "", icon: "st.Weather.weather1", backgroundColor: "#FFAA33"
		}    
		standardTile("modeTrigger", "device.modeswitch", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label:'MODE' , action: "modeTrigger", icon: "st.samsung.da.RAC_4line_02_ic_fan", backgroundColor:"#03f4fb", nextState: "trying"
			state "trying", label: 'TRYING', action: "", icon: "st.samsung.da.RAC_4line_02_ic_fan", backgroundColor: "#FFAA33"
		}
		standardTile("timerAddTrigger", "device.timeradd", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label:'Timer', action: "timerAddTrigger", icon: "st.Health & Wellness.health7", backgroundColor:"#fff400", nextState: "trying"
			state "trying", label: 'TRYING', action: "", icon: "st.Health & Wellness.health7", backgroundColor: "#FFAA33"
		}

		main "DeviceTrigger"
		details(["DeviceTrigger", "TvTrigger", "modeTrigger", "speedTrigger", "timerAddTrigger"])
	}
}

def on() {
	log.debug "Triggered on!!!"
	sendEvent(name: "triggerswitch", value: "triggeron", isStateChange: true)
    state.fan = "on";
	runCmd("fan=on")
}
def off() {
	log.debug "Triggered off!!!"
	sendEvent(name: "triggerswitch", value: "triggeroff", isStateChange: true)
    state.fan = "off";
	runCmd("fan=power")
}

def TvTrigger() {
	log.debug "On/Off TV!!!"
    sendEvent(name: "tvswitch", value: "default", isStateChange: true)
	runCmd("tv=power")
}

def speedTrigger() {
	log.debug "Add Speed!!!"
    sendEvent(name: "speed", value: "default", isStateChange: true)
	runCmd("fan=speed")
}
def timerAddTrigger() {
	log.debug "Timer!!!"
    sendEvent(name: "timeradd", value: "default", isStateChange: true)
	runCmd("fan=timeradd")
}
def modeTrigger() {
	log.debug "Mode changed!!!"
    sendEvent(name: "modeswitch", value: "default", isStateChange: true)
	runCmd("fan=mode")
}

def runCmd(String varCommand) {
	//SC
    def host = DeviceIP
    //def host = "192.168.0.18"
    //def DevicePort = "80"
	def hosthex = convertIPtoHex(host).toUpperCase()
	def porthex = convertPortToHex(DevicePort).toUpperCase()
    
    //20160718 - commented off, as no point saving this & cause conflic, as long as it's unique then ok
	//device.deviceNetworkId = "$hosthex:$porthex"
	def userpassascii = "${HTTPUser}:${HTTPPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()

	//log.debug "The device id configured is: $device.deviceNetworkId"
    //SC
    def path = DevicePath + varCommand
    //def path = "/ir?tv=power"
	log.debug "path is: $path"
	//log.debug "Uses which method: $DevicePostGet"
	def body = ""//varCommand
	//log.debug "body is: $body"

	def headers = [:]
	headers.put("HOST", "$host:$DevicePort")
	headers.put("Content-Type", "application/x-www-form-urlencoded")
	if (HTTPAuth) {
		headers.put("Authorization", userpass)
	}
	//log.debug "The Header is $headers"
	def method = "GET"
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
        //SC
		//hubAction.options = [outputMsgToS3:false]
        
		//log.debug hubAction
        //SC
        //hubAction
		return hubAction
	}
	catch (Exception e) {
		log.debug "Hit Exception $e on $hubAction"
	}
}

def parse(String description) {
	//log.debug "Parsing '${description}'"
	def whichTile = ''	
	log.debug "state.fan " + state.fan
	
    if (state.fan == "on") {
    	//sendEvent(name: "triggerswitch", value: "triggergon", isStateChange: true)
        whichTile = 'mainon'
    }
    if (state.fan == "off") {
    	//sendEvent(name: "triggerswitch", value: "triggergoff", isStateChange: true)
        whichTile = 'mainoff'
    }
	
	//RESET THE DEVICE ID TO GENERIC/RANDOM NUMBER. THIS ALLOWS MULTIPLE DEVICES TO USE THE SAME ID/IP
    //20160718 - commented off, as no point saving this & cause conflic, as long as it's unique then ok
	//device.deviceNetworkId = "ID_WILL_BE_CHANGED_AT_RUNTIME_" + (Math.abs(new Random().nextInt()) % 99999 + 1)
    
    //RETURN BUTTONS TO CORRECT STATE
	log.debug 'whichTile: ' + whichTile
    switch (whichTile) {
        case 'mainon':
			def result = createEvent(name: "switch", value: "on", isStateChange: true)
			return result
        case 'mainoff':
			def result = createEvent(name: "switch", value: "off", isStateChange: true)
			return result
        default:
			def result = createEvent(name: "testswitch", value: "default", isStateChange: true)
			//log.debug "testswitch returned ${result?.descriptionText}"
			return result
    }
}

private String convertIPtoHex(ipAddress) {
	String hex = ipAddress.tokenize( '.' ).collect {  String.format( '%02x', it.toInteger() ) }.join()
	//log.debug "IP address entered is $ipAddress and the converted hex code is $hex"
	return hex
}
private String convertPortToHex(port) {
	String hexport = port.toString().format( '%04x', port.toInteger() )
	//log.debug "Port entered is $port and the converted hex code is $hexport"
	return hexport
}
private Integer convertHexToInt(hex) {
	Integer.parseInt(hex,16)
}
private String convertHexToIP(hex) {
	//log.debug("Convert hex to ip: $hex")
	[convertHexToInt(hex[0..1]),convertHexToInt(hex[2..3]),convertHexToInt(hex[4..5]),convertHexToInt(hex[6..7])].join(".")
}
private getHostAddress() {
	def parts = device.deviceNetworkId.split(":")
	log.debug device.deviceNetworkId
	def ip = convertHexToIP(parts[0])
	def port = convertHexToInt(parts[1])
	return ip + ":" + port
}
