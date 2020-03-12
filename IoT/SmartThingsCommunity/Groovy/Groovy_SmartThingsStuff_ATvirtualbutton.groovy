/**
 * AT VirtualButton - McGyvered by MaxVonEvil 2019
 * The purpose of this DHT is to provide a virtual pushbutton type, which can be used on ActionTiles panels. 
 * I use it to trigger WebCore pistons, smartlighting rules, etc.
 * It's based on the original "Simulated Button" DHT with a couple of code tweaks to make it work with AT.
 *
 *  Copyright 2015 SmartThings
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
metadata {
	definition (name: "Simulated Button PLUS", namespace: "MaxVonEvil", author: "MaxVonEvil") {
		capability "Actuator"
		capability "Button"
	  capability "Momentary"
		capability "Sensor"
		capability "Health Check"
        
        command "push"
        command "hold"
	}

	simulator {

	}
	tiles {
		standardTile("button", "device.button", width: 1, height: 1) {
			state "default", label: "", icon: "st.unknown.zwave.remote-controller", backgroundColor: "#ffffff"
		}
 		standardTile("push", "device.button", width: 1, height: 1, decoration: "flat") {
			state "default", label: "Push", backgroundColor: "#ffffff", action: "push"
		} 
 		standardTile("hold", "device.button", width: 1, height: 1, decoration: "flat") {
			state "default", label: "Hold", backgroundColor: "#ffffff", action: "hold"
		}          
		main "button"
		details(["button","push","hold"])
	}
}

def parse(String description) {
	
}

def hold() {
	sendEvent(name: "button", value: "held", data: [buttonNumber: "1"], descriptionText: "$device.displayName button 1 was held", isStateChange: true)
} 

def push() {
	sendEvent(name: "button", value: "pushed", data: [buttonNumber: "1"], descriptionText: "$device.displayName button 1 was pushed", isStateChange: true)
}

def installed() {
	log.trace "Executing 'installed'"
	initialize()
}

def updated() {
	log.trace "Executing 'updated'"
	initialize()
}

private initialize() {
	log.trace "Executing 'initialize'"

	sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
	sendEvent(name: "healthStatus", value: "online")
	sendEvent(name: "DeviceWatch-Enroll", value: [protocol: "cloud", scheme:"untracked"].encodeAsJson(), displayed: false)
}
