/**
 *  Copyright 2015 SmartThings
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
metadata {
    definition (name: "Roomba Control v2", namespace: "SmartThingsDHs", author: "fieldsjm", mnmn: "SmartThings", vid: "generic-button-1", ocfDeviceType: "oic.d.robotcleaner") {
        capability "Actuator"
        capability "Alarm"
        capability "Button"
        capability "Holdable Button"
        capability "Configuration"
        capability "Lock"
        capability "Sensor"
        capability "Switch"
        capability "Health Check"

        command "push1"
        command "push2"
        command "push3"
        command "push4"
        command "push5"
    }

    simulator {
    }
    
    tiles (scale: 2){
	/*Status tile based on intended action*/
	multiAttributeTile(name:"status", type: "generic", width: 6, height: 4){
    	tileAttribute ("device.status", key: "PRIMARY_CONTROL") {
			attributeState ("Ready", label: "Ready", icon:"st.samsung.da.RC_ic_rc", backgroundColor:"#8CFC03")
			attributeState ("Running", label: "Cleaning\nIn-Process", icon:"st.Health & Wellness.health7", backgroundColor:"#078bf7")
			attributeState ("Paused", label: "Cleaning\nIn-Process", icon:"http://cdn.device-icons.smartthings.com/sonos/pause-icon@2x.png", backgroundColor:"#FC030F")
			attributeState ("Docking", label: "Docking", icon:"st.presence.house.unlocked", backgroundColor:"#5F07F7")
			attributeState ("Docked", label: "Docked", icon:"st.presence.house.secured", backgroundColor:"#5F07F7")
			attributeState ("Stopped", label: "Cleaning\nIn-Process", icon:"https://raw.githubusercontent.com/fieldsjm/Resources/master/stop.png", backgroundColor:"#FC030F")
			attributeState ("Ended", label: "Cleaning\nComplete", icon:"st.samsung.da.RC_ic_rc", backgroundColor:"#8CFC03")
			attributeState ("Error", label: "Error", icon:"https://github.com/fieldsjm/Resources/blob/master/warning.png?raw=true", backgroundColor:"#FC030F")
		}
        tileAttribute ("device.Text", key: "SECONDARY_CONTROL") {
        	attributeState ("Running", label: "Running")
            attributeState ("Paused", label: "Paused")
            attributeState ("Stopped", label: "Stopped")
            attributeState ("Docked", label: "Docked")            
        }
	}
	   /*Start*/
        standardTile("push1", "device.button", width: 3, height: 2, decoration: "flat") {
            state "default", label: "Start", icon:"st.samsung.da.RC_ic_rc", backgroundColor: "#ffffff", action: "push1"
        }
        /*Pause*/
        standardTile("push2", "device.button", width: 3, height: 2, decoration: "flat") {
            state "default", label: "Pause", icon:"http://cdn.device-icons.smartthings.com/sonos/pause-icon@2x.png", backgroundColor: "#ffffff", action: "push2"
        }
        /*Dock*/
        standardTile("push3", "device.button", width: 3, height: 2, decoration: "flat") {
            state "default", label: "Dock", icon:"st.nest.nest-home", backgroundColor: "#ffffff", action: "push3"
        }
        /*Stop*/
        standardTile("push4", "device.button", width: 3, height: 2, decoration: "flat") {
            state "default", label: "", icon:"st.sonos.stop-btn", backgroundColor: "#ffffff", action: "push4"
        }
        /*Reset*/
        standardTile("push5", "device.button", width: 6, height: 2, decoration: "flat") {
            state "default", label: "Reset", icon:"st.secondary.refresh-icon", backgroundColor: "#ffffff", action: "push5"
        }
        /*Switch for IFTT feedback of Started - Hidden by default*/
        standardTile("switch", "device.switch", inactiveLabel: false, width: 6, height: 2, decoration: "flat"){
			state("off", label: '${name}', action: "switch.on", backgroundColor: "#ffffff", nextState: "on", defaultState: "true")
			state("on", label: '${name}', action: "switch.off", backgroundColor: "#00a0dc", nextState: "off")
		}
        /*Alarm for IFTT feedback of Error - Hidden by default*/
        standardTile("alarm", "device.alarm", inactiveLabel: false, width: 6, height: 2, decoration: "flat") {
			state("off", label:'off', action:'alarm.strobe', icon:"st.alarm.alarm.alarm", backgroundColor:"#ffffff", nextState: "strobe", defaultState: "true")
			state("strobe", label:'strobe!', action:'alarm.off', icon:"st.alarm.alarm.alarm", backgroundColor:"#e86d13", nextState: "off")
		}
        /*Lock for IFTT feedback of mission complete - Hidden by default*/
		standardTile("lock", "device.lock", inactiveLabel: false, width: 6, height: 2, decoration: "flat"){
			state("unlocked", label: '${name}', action: "lock.lock", backgroundColor: "#ffffff", nextState: "locked", defaultState: "true")
			state("locked", label: '${name}', action: "lock.unlock", backgroundColor: "#00a0dc", nextState: "unlocked")
		}
        
        main "status"
        details(["status","push1","push2","push3","push4", "push5"])
    }
}

def parse(String description) {

}

/*Start*/
def push1() {
    push(1)
}

/*Pause*/
def push2() {
    push(2)
    sendEvent(name: "status", value: "Paused")
    sendEvent(name: "Text", value: "Paused")
}

/*Dock with conversational delay to Docked then Ready Status - no feedback available*/
def push3() {
    push(3)
    sendEvent(name: "status", value: "Docking")
    sendEvent(name: "Text", value: "Running")
    runIn(60, docked)
}
def docked() {
	sendEvent(name: "status", value: "Docked")
    sendEvent(name: "Text", value: "Docked")
    runIn(10, push5)
}

/*Stop*/
def push4() {
    push(4)
    sendEvent(name: "status", value: "Stopped")
    sendEvent(name: "Text", value: "Stopped")
}

/*Status Reset*/
def push5() {
    push(5)
	unlock()
    off()    
    sendEvent(name: "status", value: "Ready")
    sendEvent(name: "Text", value: "Docked")
}

private push(button) {
    log.debug "$device.displayName button $button was pushed"
    sendEvent(name: "button", value: "pushed", data: [buttonNumber: button], descriptionText: "$device.displayName button $button was pushed", isStateChange: true)
}

/*Feedback Mechanisms*/
/*Started*/
def on(){
	sendEvent(name: "switch", value: "on")
    sendEvent(name: "status", value: "Running")
    sendEvent(name: "Text", value: "Running")
    }
def off(){    
    sendEvent(name: "switch", value: "off")
    sendEvent(name: "alarm", value: "off")
    sendEvent(name: "status", value: "Ready")
    sendEvent(name: "Text", value: "Docked")
    }
/*Misson Complete*/
def lock() {
    sendEvent(name: "lock", value: "locked")
    sendEvent(name: "status", value: "Ended")
    sendEvent(name: "Text", value: "Docked")
	}
def unlock() {
    sendEvent(name: "lock", value: "unlocked")
    sendEvent(name: "status", value: "Ready")
    sendEvent(name: "Text", value: "Docked")
	}
/*Error*/
def strobe() {
	sendEvent(name: "alarm", value: "strobe")
    sendEvent(name: "status", value: "Error")
    sendEvent(name: "Text", value: "Stopped")
    }

def installed() {
    initialize()
}

def updated() {
    initialize()
}

def initialize() {
    sendEvent(name: "numberOfButtons", value: 5)
    sendEvent(name: "status", value: "Ready")
    sendEvent(name: "Text", value: "Docked")

    sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
    sendEvent(name: "healthStatus", value: "online")
    sendEvent(name: "DeviceWatch-Enroll", value: [protocol: "cloud", scheme:"untracked"].encodeAsJson(), displayed: false)
}