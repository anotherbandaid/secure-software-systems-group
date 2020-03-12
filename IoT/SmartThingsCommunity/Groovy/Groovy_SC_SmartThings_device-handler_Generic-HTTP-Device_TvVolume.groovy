/**
 *  TVvolume v1.0.20160619
 *
 *  Source code can be found here: https://github.com/JZ-SmartThings/SmartThings/blob/master/Devices/TVDevice/TVDevice.groovy
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
	definition (name: "TVvolume", author: "JZ", namespace:"JZ") {
		capability "Switch"
		attribute "displayName", "string"
		command "tvmute"
		command "ResetTiles"
	}

	preferences {
		input("DeviceIP", "string", title:"Device IP Address", description: "Please enter your device's IP Address", required: true, displayDuringSetup: true)
		input("DevicePort", "string", title:"Device Port", description: "Empty assumes port 80.", required: false, displayDuringSetup: true)
		input("DevicePathOn", "string", title:"URL Path for ON", description: "Rest of the URL, include forward slash.", displayDuringSetup: true)
		input("DevicePathOff", "string", title:"URL Path for OFF", description: "Rest of the URL, include forward slash.", displayDuringSetup: true)
		input(name: "DevicePostGet", type: "enum", title: "POST or GET", options: ["POST","GET"], defaultValue: "POST", required: false, displayDuringSetup: true)
		section() {
			input("HTTPAuth", "bool", title:"Requires User Auth?", description: "Choose if the HTTP requires basic authentication", defaultValue: false, required: true, displayDuringSetup: true)
			input("HTTPUser", "string", title:"HTTP User", description: "Enter your basic username", required: false, displayDuringSetup: true)
			input("HTTPPassword", "string", title:"HTTP Password", description: "Enter your basic password", required: false, displayDuringSetup: true)
		}
	}
	
	simulator {
	}

	tiles(scale: 2) {
		valueTile("displayName", "device.displayName", width: 4, height: 4, decoration: "flat") {
			state("default", label: 'TV Volume: \n${currentValue}', backgroundColor:"#ffffff")
		}
		standardTile("switchon", "device.switch", width: 2, height: 2, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label: 'ON', action: "on", icon: "st.custom.buttons.add-icon", backgroundColor: "#FF6600", nextState: "trying"
			state "trying", label: 'TRYING', action: "ResetTiles", icon: "st.custom.buttons.add-icon", backgroundColor: "#FFAA33"
		}
		standardTile("switchoff", "device.switch", width: 2, height: 2, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label:'OFF' , action: "off", icon: "st.custom.buttons.subtract-icon", backgroundColor:"#53a7c0", nextState: "trying"
			state "trying", label: 'TRYING', action: "ResetTiles", icon: "st.custom.buttons.subtract-icon", backgroundColor: "#FFAA33"
		}
		standardTile("tvmute", "device.switch", width: 2, height: 2, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
			state "default", label: 'MUTE', action: "tvmute", icon: "st.custom.sonos.muted", backgroundColor: "#FFFFFF", nextState: "trying"
			state "trying", label: 'TRYING', action: "ResetTiles", icon: "st.custom.sonos.muted", backgroundColor: "#FFAA33"
		}
		main "tvmute"
		details(["displayName","switchon", "switchoff", "tvmute"])
	}
}

def ResetTiles() {
	sendEvent(name: "switchon", value: "default", isStateChange: true)
	sendEvent(name: "switchoff", value: "default", isStateChange: true)
	sendEvent(name: "tvmute", value: "default", isStateChange: true)
	log.debug "Resetting tiles."
}

def on() {
	log.debug "---ON COMMAND---"
	runCmd("/ir?tv=volup")
}
def off() {
	log.debug "---OFF COMMAND---"
	runCmd("/ir?tv=voldown")
}
def tvmute() {
	runCmd("/ir?tv=mute")
}

def runCmd(String varCommand) {
	def host = DeviceIP
	def hosthex = convertIPtoHex(host).toUpperCase()
	def LocalDevicePort = ''
	if (DevicePort==null) { LocalDevicePort = "80" } else { LocalDevicePort = DevicePort }
	def porthex = convertPortToHex(LocalDevicePort).toUpperCase()
	device.deviceNetworkId = "$hosthex:$porthex"
	def userpassascii = "${HTTPUser}:${HTTPPassword}"
	def userpass = "Basic " + userpassascii.encodeAsBase64().toString()

	log.debug "The device id configured is: $device.deviceNetworkId"

	def path = varCommand
	log.debug "path is: $path"
	log.debug "Uses which method: $DevicePostGet"
	def body = varCommand 
	log.debug "body is: $body"

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
		hubAction.options = [outputMsgToS3:false]
		log.debug hubAction
		hubAction
	}
	catch (Exception e) {
		log.debug "Hit Exception $e on $hubAction"
	}
}

def parse(String description) {
//	log.debug "Parsing '${description}'"
	def whichTile = ''
	def map = [:]
	def retResult = []
	def descMap = parseDescriptionAsMap(description)
	def jsonlist = [:]
	def bodyReturned = ' '
	def headersReturned = ' '
	if (descMap["body"] && descMap["headers"]) {
		bodyReturned = new String(descMap["body"].decodeBase64())
		headersReturned = new String(descMap["headers"].decodeBase64())
	}
//	log.debug "BODY---" + bodyReturned
//	log.debug "HEADERS---" + headersReturned

	if (descMap["body"]) {
		if (headersReturned.contains("application/json")) {
			def body = new String(descMap["body"].decodeBase64())
			def slurper = new JsonSlurper()
			jsonlist = slurper.parseText(body)
			//log.debug "JSONLIST---" + jsonlist."CPU"
			jsonlist.put ("Date", new Date().format("yyyy-MM-dd h:mm:ss a", location.timeZone))
		} else if (headersReturned.contains("text/html")) {
			jsonlist.put ("Date", new Date().format("yyyy-MM-dd h:mm:ss a", location.timeZone))
			def data=bodyReturned.eachLine { line ->
			
				if (line.length()<15 && !line.contains("<") && !line.contains(">") && !line.contains("/")) {
					log.trace "---" + line
					if (line.contains('tv=')) { jsonlist.put ("tv", line.replace("tv=","")) }
				}
			}
		}
	}
	if (descMap["body"] && (headersReturned.contains("application/json") || headersReturned.contains("text/html"))) {
		//putImageInS3(descMap)
		if (jsonlist."tv"=="volup") {
			sendEvent(name: "switchon", value: "default", isStateChange: true)
			whichTile = 'mainon'
		}
		if (jsonlist."tv"=="voldown") {
			sendEvent(name: "switchoff", value: "default", isStateChange: true)
			whichTile = 'mainoff'
		}
		if (jsonlist."tv"=="mute") {
			sendEvent(name: "tvmute", value: "default", isStateChange: true)
			whichTile = 'tvmute'
		}
	}

	log.debug jsonlist

	//RESET THE DEVICE ID TO GENERIC/RANDOM NUMBER. THIS ALLOWS MULTIPLE DEVICES TO USE THE SAME ID/IP
	device.deviceNetworkId = "ID_WILL_BE_CHANGED_AT_RUNTIME_" + (Math.abs(new Random().nextInt()) % 99999 + 1)

	//CHANGE NAME TILE
	sendEvent(name: "displayName", value: DeviceIP, unit: "")

	//RETURN BUTTONS TO CORRECT STATE
	log.debug 'whichTile: ' + whichTile
    switch (whichTile) {
        case 'mainoff':
			sendEvent(name: "switch", value: "off", isStateChange: true)
			def result = createEvent(name: "switchon", value: "default", isStateChange: true)
			return result
        case 'mainon':
			sendEvent(name: "switch", value: "on", isStateChange: true)
			def result = createEvent(name: "switchon", value: "default", isStateChange: true)
			return result
        case 'tvmute':
			sendEvent(name: "switch", value: "default", isStateChange: true)
			def result = createEvent(name: "tvmute", value: "default", isStateChange: true)
			return result
    }
}

def parseDescriptionAsMap(description) {
	description.split(",").inject([:]) { map, param ->
	def nameAndValue = param.split(":")
	map += [(nameAndValue[0].trim()):nameAndValue[1].trim()]
	}
}
private String convertIPtoHex(ipAddress) { 
	String hex = ipAddress.tokenize( '.' ).collect {  String.format( '%02x', it.toInteger() ) }.join()
	//log.debug "IP address entered is $ipAddress and the converted hex code is $hex"
	return hex
}
private String convertPortToHex(port) {
	String hexport = port.toString().format( '%04x', port.toInteger() )
	//log.debug hexport
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
	//log.debug device.deviceNetworkId
	def ip = convertHexToIP(parts[0])
	def port = convertHexToInt(parts[1])
	return ip + ":" + port
}
