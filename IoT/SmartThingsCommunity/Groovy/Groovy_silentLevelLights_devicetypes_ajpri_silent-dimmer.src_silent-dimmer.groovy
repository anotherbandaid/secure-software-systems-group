/**
 *  Silent Dimmer
 *
 *  Copyright 2016 Austin Pritchett
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
 
 /*
 Beta 1 - 18 December 2016
 	Initial Release 
 */
metadata {
	definition (name: "Silent Dimmer", namespace: "ajpri", author: "Austin Pritchett") {
		capability "Switch"
        capability "Refresh"
		capability "Switch Level"
	}


	simulator {
		// TODO: define status and reply messages here
	}


    tiles(scale: 2) {
        multiAttributeTile(name:"rich-control", type: "lighting", canChangeIcon: true){
            tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
              attributeState "on", label:'${name}', action:"switch.off", icon:"st.Kids.kid10", backgroundColor:"#79b821", nextState:"off"
              attributeState "off", label:'${name}', action:"switch.on", icon:"st.Kids.kid10", backgroundColor:"#ffffff", nextState:"on"
              }
            tileAttribute ("device.level", key: "SLIDER_CONTROL") {
              attributeState "level", action:"switch level.setLevel", range:"(0..100)"
            }
        }

        controlTile("levelSliderControl", "device.level", "slider", height: 1, width: 2, inactiveLabel: false, range:"(0..100)") {
            state "level", action:"switch level.setLevel"
        }

        standardTile("refresh", "device.switch", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
            state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
        }

        main(["rich-control"])
        details(["rich-control", "refresh"])
    }
}	

// handle commands
def on() {
	log.debug "Executing 'on'"
    sendEvent(name: "switch", value: "on")
	// TODO: handle 'on' command
}

def off() {
	log.debug "Executing 'off'"
    sendEvent(name: "switch", value: "off")
	// TODO: handle 'off' command
}

def setLevel(value) {
	log.debug "Executing 'setLevel'"
    sendEvent(name: "level", value: value)
}