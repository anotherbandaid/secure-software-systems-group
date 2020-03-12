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
 
 
//TODO: change parent updates to be based on subscribed events from children.
 
metadata {

    definition (name: "Simulated Power Strip", namespace: "jamesandariese", author: "James Andariese") {
        capability "Switch"
	}

	tiles {
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "off", label: '${currentValue}', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff"
			state "on", label: '${currentValue}', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#00A0DC"
		}
		childDeviceTile("outlet1Toggle", "outlet1", height: 1, width: 1, childTileName: "switch")
		childDeviceTile("outlet2Toggle", "outlet2", height: 1, width: 1, childTileName: "switch")
		childDeviceTile("outlet3Toggle", "outlet3", height: 1, width: 1, childTileName: "switch")
		childDeviceTile("outlet4Toggle", "outlet4", height: 1, width: 1, childTileName: "switch")
         }
}

def installed() {
    state.counter = state.counter ? state.counter + 1 : 1
    if (state.counter == 1) {
        addChildDevice(
            "Simulated Power Strip Outlet",
            "${device.deviceNetworkId}.1",
            null,
            [completedSetup: true, label: "${device.label} 1", componentName: "outlet1", componentLabel: "1"]).displayName = "$device.displayName 1"
        addChildDevice(
            "Simulated Power Strip Outlet",
            "${device.deviceNetworkId}.2",
            null,
            [completedSetup: true, label: "${device.label} 2", componentName: "outlet2", componentLabel: "2"]).displayName = "$device.displayName 2"
        addChildDevice(
            "Simulated Power Strip Outlet",
            "${device.deviceNetworkId}.3",
            null,
            [completedSetup: true, label: "${device.label} 3", componentName: "outlet3", componentLabel: "3"]).displayName = "$device.displayName 3"
        addChildDevice(
            "Simulated Power Strip Outlet",
            "${device.deviceNetworkId}.4",
            null,
            [completedSetup: true, label: "${device.label} 4", componentName: "outlet4", componentLabel: "4"]).displayName = "$device.displayName 4"
    }
}

def on() {
    childDevices.each{
        it.on()
    }
    null
}

def off() {
    childDevices.each{
        it.off()
    }
    null
}

def parse(description) {
    log.debug "$version parsing $description"
}

def childOn(dni) {
    log.debug "$version childOn($dni)"
    childDevices.each{
        if (it.deviceNetworkId == dni) {
            it.sendEvent(name:"switch", value:"on")
        }
    }
    sendEvent(name:"switch", value:"on")
    null
}

def childOff(dni) {
    log.debug "$version childOn($dni)"
    def allOff = true
    childDevices.each{
        if (it.deviceNetworkId == dni) {
            it.sendEvent(name:"switch", value:"off")
        }
    }
    childDevices.each{
        if (it.currentState("switch").value == "on") {
            allOff = false
        }
    }
    if (allOff) {
        sendEvent(name:"switch", value:"off")
    } else {
        sendEvent(name:"switch", value:"on")
    }
    null
}

private getVersion() {
	"PUBLISHED"
}