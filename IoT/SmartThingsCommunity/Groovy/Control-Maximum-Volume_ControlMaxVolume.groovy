/**
 *  Control Maximum Volume
 *
 *  Copyright 2018 Alan Moore
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
    name: "Control Maximum Volume",
    namespace: "mrmoorey",
    author: "Alan Moore",
    description: "Control the maximum volume of a speaker (Sonos, Bose, etc). This is the parent SmartApp allowing multiple automations",
    category: "My Apps",
    iconUrl: "http://cdn.device-icons.smartthings.com/Electronics/electronics16-icn.png",
    iconX2Url: "http://cdn.device-icons.smartthings.com/Electronics/electronics16-icn@2x.png",
    iconX3Url: "http://cdn.device-icons.smartthings.com/Electronics/electronics16-icn@3x.png",
    singleInstance: true)


preferences {
    page(name: "mainPage", title: "Speakers To Control", install: true, uninstall: true) {
        if(state?.installed) {
            section("Add a New Speaker Automation") {
                app(name: "controlVolumeChild", namespace: "mrmoorey", appName: "Control Maximum Volume Child", title: "New Speaker", page: "mainPage", multiple: true, install: true)
            }
        } else {
            section("Initial Install") {
                paragraph "This SmartApp installs Control Maximum Volume so you can add multiple child speaker automations. Click install / done, then go to SmartApps in the flyout menu to add or edit speakers."
            }
        }
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
    state.installed = true
}
