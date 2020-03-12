/**
*  D-Link Camera Virtual Switch Controller
*  Version 1.0.0
*
*  Copyright 2016 Ben Lebson
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
definition(
    name: "D-Link Camera Virtual Switch Controller",
    namespace: "blebson",
    author: "Ben Lebson",
    description: "Links a switch to your D-Link camera functions.",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Camera/dlink.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Camera/dlink@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Camera/dlink@3x.png")


preferences {
    section("Switch") {		 
        input("mySwitch", "capability.switch", title: "Select Switch:", required: true, multiple: false)
    }
    section("Camera") {
        input("camera", "capability.imageCapture", title: "Select Camera:", description: "NOTE: Currently only compatable with D-Link Devices made by BLebson")	
        input("commandOn","enum", title: "Command sent when on:", description: "Please be sure your camera supports your selected command.", required:false, 
              options: [["On": "Motion On"], 
                        ["Off": "Motion Off"], 
                        ["pirOn": "PIR Sensor On"], 
                        ["pirOff": "PIR Sensor Off"], 
                        ["nvOn": "Night Vision On"], 
                        ["nvOff": "Night Vision Off"], 
                        ["nvAuto": "Night Vision Auto"], 
                        ["vrOn": "Video Recording On"], 
                        ["vrOff": "Video Recording Off"], 
                        ["left": "Move Left"], 
                        ["right": "Move Right"], 
                        ["up": "Move Up"], 
                        ["down": "Move Down"], 
                        ["home": "Move to Home Position"], 
                        ["presetOne": "Move to Preset One"], 
                        ["presetTwo": "Move to Preset Two"], 
                        ["presetThree": "Move to Preset Three"]])
        input("commandOff","enum", title: "Command sent when off:", description: "Please be sure your camera supports your selected command.", required:false, 
              options: [["On": "Motion On"], 
                        ["Off": "Motion Off"], 
                        ["pirOn": "PIR Sensor On"], 
                        ["pirOff": "PIR Sensor Off"], 
                        ["nvOn": "Night Vision On"], 
                        ["nvOff": "Night Vision Off"], 
                        ["nvAuto": "Night Vision Auto"], 
                        ["vrOn": "Video Recording On"], 
                        ["vrOff": "Video Recording Off"], 
                        ["left": "Move Left"], 
                        ["right": "Move Right"], 
                        ["up": "Move Up"], 
                        ["down": "Move Down"], 
                        ["home": "Move to Home Position"], 
                        ["presetOne": "Move to Preset One"], 
                        ["presetTwo": "Move to Preset Two"], 
                        ["presetThree": "Move to Preset Three"]])

    }
}

def installed() {
    log.debug "Installed with settings: ${settings}"

    initialize()
}

def updated() {
    log.debug "Updated with settings: ${settings}"

    unsubscribe()
    initialize()
}

def initialize() {
    // TODO: subscribe to attributes, devices, locations, etc.
    subscribe(mySwitch, "switch.on", onStateChanged)	
    subscribe(mySwitch, "switch.off", offStateChanged)
}

def onStateChanged(evt) {

    log.debug("State Changed to Off." )
    camera."$commandOn"()

}

def offStateChanged(evt) {

    log.debug("State Changed to On." )
    camera."$commandOff"()

}
