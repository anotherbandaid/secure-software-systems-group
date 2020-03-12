/**
 *  LIFX Scene Button
 *
 *  Copyright 2017 James Andariese
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
	definition (name: "LIFX Scene Button", namespace: "jamesandariese", author: "James Andariese") {
		capability "Momentary"
        capability "Button"
        
        command "setNoPower"
        command "duration1"
        command "duration2"
        command "duration3"
        command "duration5"
        command "duration10"
        command "duration20"
        command "duration30"
        command "duration60"
        command "duration120"
	}


	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		// TODO: define your main and details tiles here
        // standard flat tile with only a label
		standardTile("push", "device.switch", width: 2, height: 2) {
			state "label", label: 'Activate', action: "push", backgroundColor: "#ffffff", defaultState: true
		}
		standardTile("duration1", "device.switch", width: 1, height: 1) {
			state "label", label: 'Fade 1s', action: "duration1", backgroundColor: "#ffffff", defaultState: true
		}
		standardTile("duration2", "device.switch", width: 1, height: 1) {
			state "label", label: 'Fade 2s', action: "duration2", backgroundColor: "#ffffff", defaultState: true
		}
		standardTile("setNoPower", "device.switch", width: 2, height: 1) {
			state "label", label: 'Set Without Power', action: "setNoPower", backgroundColor: "#ffffff", defaultState: true
		}
		standardTile("duration3", "device.switch", width: 1, height: 1) {
			state "label", label: 'Fade 3s', action: "duration3", backgroundColor: "#ffffff", defaultState: true
		}
		standardTile("duration5", "device.switch", width: 1, height: 1) {
			state "label", label: 'Fade 5s', action: "duration5", backgroundColor: "#ffffff", defaultState: true
		}
		standardTile("duration10", "device.switch", width: 1, height: 1) {
			state "label", label: 'Fade 10s', action: "duration10", backgroundColor: "#ffffff", defaultState: true
		}
		standardTile("duration20", "device.switch", width: 1, height: 1) {
			state "label", label: 'Fade 20s', action: "duration20", backgroundColor: "#ffffff", defaultState: true
		}
		standardTile("duration30", "device.switch", width: 1, height: 1) {
			state "label", label: 'Fade 30s', action: "duration30", backgroundColor: "#ffffff", defaultState: true
		}
		standardTile("duration60", "device.switch", width: 1, height: 1) {
			state "label", label: 'Fade 60s', action: "duration60", backgroundColor: "#ffffff", defaultState: true
		}
		standardTile("duration120", "device.switch", width: 1, height: 1) {
			state "label", label: 'Fade 120s', action: "duration120", backgroundColor: "#ffffff", defaultState: true
		}
	}
}

def push() {
	parent.activateScene(device, true, 0)
}

def setNoPower() {
	parent.activateScene(device, false, 0)
}

def duration1() {
	parent.activateScene(device, true, 1)
}

def duration2() {
	parent.activateScene(device, true, 2)
}

def duration3() {
	parent.activateScene(device, true, 3)
}

def duration5() {
	parent.activateScene(device, true, 5)
}

def duration10() {
	parent.activateScene(device, true, 10)
}

def duration20() {
	parent.activateScene(device, true, 20)
}

def duration30() {
	parent.activateScene(device, true, 30)
}

def duration60() {
	parent.activateScene(device, true, 60)
}

def duration120() {
	parent.activateScene(device, true, 120)
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}