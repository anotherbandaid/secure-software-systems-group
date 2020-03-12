metadata {
	definition (name: "Roomba Control", namespace: "SmartThings", author: "fieldsjm", vid: "generic-contact-4", ocfdevicetype: "oic.d.garagedoor") {
		capability "Actuator"
		capability "Door Control"
        capability "Garage Door Control"
        capability "Momentary"
		capability "Refresh"
		capability "Sensor"
        capability "Switch"
        
        command "reset"
        command "dock"
	}

	simulator {
		
	}

	tiles (scale: 2){
    	/*Toggle actions: start --> running, running --> paused or dock, paused or docked --> start*/
		standardTile("toggle", "device.door", inactiveLabel: true, width: 6, height: 4) {
			state("closed", label:"Start", action:"door control.open", icon:"st.Electronics.electronics13", backgroundColor:"#8CFC03", nextState: "opening")
			state("opening", label:"In-Process", action:"door control.close", icon:"st.Health & Wellness.health7", backgroundColor:"#078bf7", nextState: "closing")
			state("closing", label:"Paused", action:"door control.open", icon:"http://cdn.device-icons.smartthings.com/sonos/pause-icon@2x.png", backgroundColor:"#FC030F", nextState: "opening")
			state("open", label:"Docking", icon:"st.presence.house.unlocked", backgroundColor:"#5F07F7")
			
		}
        /*Tile to start cleaning*/
		standardTile("open", "device.door", inactiveLabel: false, width: 3, height: 1, decoration: "flat") {
			state "default", label:"Start", action:"door control.open", icon:"st.Electronics.electronics13"
		}
        /*Tile to pause cleaning*/
		standardTile("close", "device.door", inactiveLabel: false, width: 3, height: 1, decoration: "flat") {
			state "default", label:"", action:"door control.close", icon:"st.sonos.pause-btn"
		}
        /*Tile to dock roomba*/
        standardTile("dock", "device.door", inactiveLabel: false, width: 3, height: 1, decoration: "flat") {
			state "default", label:"Dock", action:"dock", icon:"st.presence.house.unlocked"
		}
        /*Tile to reset status of tile to ready*/
        standardTile("reset", "device.door", inactiveLabel: false, width: 3, height: 1, decoration: "flat") {
			state "default", label:"Reset", action:"reset", icon:"st.secondary.refresh-icon"
        }
		/*Hidden switch for IFTT feedback of mission complete/error*/
		standardTile("switch", "device.switch", inactiveLabel: false, width: 3, height: 1){
			state("off", label: "", icon: "st.nest.empty", action: "switch.on", backgroundColor: "#ffffff", nextState: "on", defaultState: "true")
			state("on", label: "Complete", action: "switch.off", backgroundColor: "#00a0dc")
		}
        /*Status tile based on intended action*/
		valueTile("Status", "device.longText", width: 6, height: 4) {
			state ("Ready", label: "Ready", icon:"st.Electronics.electronics13", backgroundColor:"#8CFC03")
            state ("Running", label: "Running", icon:"st.Health & Wellness.health7", backgroundColor:"#078bf7")
            state ("Paused", label: "Paused", icon:"http://cdn.device-icons.smartthings.com/sonos/pause-icon@2x.png", backgroundColor:"#FC030F")
            state ("Docking", label: "Docking", icon:"st.presence.house.unlocked", backgroundColor:"#5F07F7")
            state ("Docked", label: "Docked", icon:"st.presence.house.secured", backgroundColor:"#5F07F7")
            state ("Ended", label: "Ended", icon:"st.Electronics.electronics13", backgroundColor:"#8CFC03")
            
		}

		main "Status"
		/*Recommend status or toggle as primary tile*/
        details(["Status", /*"toggle"*/, "open", "close", "dock", "reset"])
	}
}

def parse(String description) {
	log.trace "parse($description)"
}

def installed() {
	sendEvent(name: "door", value: "closed")
    sendEvent(name: "switch", value: "off")
    sendEvent(name: "longText", value: "Ready")
    }

def open() {
	sendEvent(name: "door", value: "opening")
    sendEvent(name: "longText", value: "Running")
}

def close() {
    sendEvent(name: "door", value: "closing")
    sendEvent(name: "longText", value: "Paused")
}

/*60sec converstaional delay during physical docking*/
def dock() {
	sendEvent(name: "door", value: "open")
    sendEvent(name: "longText", value: "Docking")
    runIn(60, docked)
}

/*10sec delay to reset to ready status*/
def docked() {
	sendEvent(name: "door", value: "closed")
    sendEvent(name: "longText", value: "Docked")
    runIn(10, reset)
    }

def reset() {
    sendEvent(name: "door", value: "closed")
    sendEvent(name: "switch", value: "off")
    sendEvent(name: "longText", value: "Ready")
}

def on() {
    sendEvent(name: "switch", value: "on")
    sendEvent(name: "longText", value: "Ended")
}

def off() {
	sendEvent(name: "door", value: "closed")
	sendEvent(name: "switch", value: "off")
    sendEvent(name: "longText", value: "Ready")
}