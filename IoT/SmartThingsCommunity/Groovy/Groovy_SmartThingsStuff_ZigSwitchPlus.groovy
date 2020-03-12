/**
 *  Zigbee switch augmented with Momentary (pulse) capability for use with garage opener switches and such
 *  The device type handler once installed, will allow you to configure the pulse length in seconds (min 1)
 *  Note: Be sure to configure it, as the pulsing otherwise won't work.
 *
 *  Mod by Max Ranzau (max@ranzau.net)
 *  Use at your own risk, blah-di-blah-boilerplate-disclaimer.
 */

metadata {
    definition (name: "ZigBee Switch PLUS", namespace: "MaxVonEvil", author: "MaxVonEvil", ocfDeviceType: "oic.d.switch", runLocally: false, minHubCoreVersion: '000.019.00012', executeCommandsLocally: true) {
        capability "Actuator"
        capability "Configuration"
        capability "Refresh"
        capability "Switch"
        capability "Health Check"
        capability "Momentary"

        fingerprint profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006"
        fingerprint profileId: "0104", inClusters: "0000, 0003, 0006", outClusters: "0003, 0006, 0019, 0406", manufacturer: "Leviton", model: "ZSS-10", deviceJoinName: "Leviton Switch"
        fingerprint profileId: "0104", inClusters: "0000, 0003, 0006", outClusters: "000A", manufacturer: "HAI", model: "65A21-1", deviceJoinName: "Leviton Wireless Load Control Module-30amp"
        fingerprint profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006", outClusters: "0003, 0006, 0008, 0019, 0406", manufacturer: "Leviton", model: "DL15A", deviceJoinName: "Leviton Lumina RF Plug-In Appliance Module"
        fingerprint profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006", outClusters: "0003, 0006, 0008, 0019, 0406", manufacturer: "Leviton", model: "DL15S", deviceJoinName: "Leviton Lumina RF Switch"
        fingerprint profileId: "C05E", inClusters: "0000, 0003, 0004, 0005, 0006, 1000, 0B04, FC0F", outClusters: "0019", manufacturer: "OSRAM", model: "Plug 01", deviceJoinName: "OSRAM SMART+ Plug"
        fingerprint profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006, 0B05, FC01, FC08", outClusters: "0003, 0019", manufacturer: "LEDVANCE", model: "PLUG", deviceJoinName: "SYLVANIA SMART+ Smart Plug"
    }

    // simulator metadata
    simulator {
        // status messages
        status "on": "on/off: 1"
        status "off": "on/off: 0"

        // reply messages
        reply "zcl on-off on": "on/off: 1"
        reply "zcl on-off off": "on/off: 0"
    }

    tiles(scale: 2) {
        multiAttributeTile(name:"switch", type: "lighting", width: 6, height: 4, canChangeIcon: true){
            tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
                attributeState "on", label:'${name}', action:"switch.off", icon:"st.switches.light.on", backgroundColor:"#00A0DC", nextState:"turningOff"
                attributeState "off", label:'${name}', action:"switch.on", icon:"st.switches.light.off", backgroundColor:"#ffffff", nextState:"turningOn"
                attributeState "turningOn", label:'${name}', action:"switch.off", icon:"st.switches.light.on", backgroundColor:"#00A0DC", nextState:"turningOff"
                attributeState "turningOff", label:'${name}', action:"switch.on", icon:"st.switches.light.off", backgroundColor:"#ffffff", nextState:"turningOn"
            }
        }
        standardTile("push", "device.push", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
            state "default", label:"Pulse", action:"momentary.push", icon:"st.secondary.activity"
        }        
        standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
            state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
        }
                main "switch"
        details(["switch", "push","refresh"])
    }

	preferences {
		input "pulseLength", "number",
			title: "Length of pulse (Seconds)",
            description: "Number of seconds to close the switch before opening it again. Minimum value is 1. Maximum is 7200 (2 hours)",
			defaultValue: 1,
			range: "1..7200",
			displayDuringSetup: false, 
			required: true
		input "debugOutput", "bool", 
			title: "Enable debug logging?", 
			defaultValue: true, 
			displayDuringSetup: false, 
			required: false
	}

}

// Parse incoming device messages to generate events
def parse(String description) {
    log.debug "description is $description"
    def event = zigbee.getEvent(description)
    if (event) {
        sendEvent(event)
    }
    else {
        log.warn "DID NOT PARSE MESSAGE for description : $description"
        log.debug zigbee.parseDescriptionAsMap(description)
    }
}

def off() {
	logDebug "*** OFF"
    zigbee.off()
}

def on() {
	logDebug "*** ON"
    zigbee.on()
}


// ------------------------------------------------------------------------

def push() {
    def sLetter    
    if (settings?.pulseLength > 1) { sLetter = "s." } else { sLetter = "." }
    logDebug "*** Momentary pulse for " + settings?.pulseLength + " second" + sLetter
	delayBetween([
		zigbee.on(),
		zigbee.off()
	], (settings?.pulseLength * 1000))
}

// PING is used by Device-Watch in attempt to reach the Device
def ping() {
    return refresh()
}

def refresh() {
    zigbee.onOffRefresh() + zigbee.onOffConfig()
}

def configure() {
    // Device-Watch allows 2 check-in misses from device + ping (plus 2 min lag time)
    sendEvent(name: "checkInterval", value: 2 * 10 * 60 + 2 * 60, displayed: false, data: [protocol: "zigbee", hubHardwareId: device.hub.hardwareID])
    logDebug "Configuring Reporting and Bindings."
    zigbee.onOffRefresh() + zigbee.onOffConfig()
}

private logDebug(msg) {
	if (settings?.debugOutput || settings?.debugOutput == null) {
		log.debug "$msg"
	}
}
