/**
 *  Heating Control Thermostat
 *
 *  Copyright 2017 Anders Sveen &lt;anders@f12.no&gt;
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
    definition(name: "Heating Control Thermostat", namespace: "smartthings.f12.no", author: "Anders Sveen <anders@f12.no>") {
        capability "Thermostat Heating Setpoint"
        capability "Thermostat Mode"
        capability "Thermostat Operating State"


        // Heating Setpoing
        command "setHeatingSetpoint"
        // Thermostat Mode
        command "auto"
        command "cool"
        command "emergencyHeat"
        command "heat"
        command "off"
        command "setThermostatMode"
        // Thermostat Operating State
        // None actually

        // Internal commands related to logic, only used by the tiles
        command "temperatureUp"
        command "temperatureDown"
    }


    simulator {
        // TODO: define status and reply messages here
    }

    preferences {
        input name: "enabled", type: "bool", title: "Enabled?", defaultValue: true, required: false
    }


    tiles {
        multiAttributeTile(name: "thermostatFull", type: "thermostat", width: 6, height: 4) {
            tileAttribute("device.temperature", key: "PRIMARY_CONTROL") {
                attributeState("default", label: '${currentValue}', unit: "C")
            }
            tileAttribute("device.heatingSetpoint", key: "VALUE_CONTROL") {
                attributeState("VALUE_UP", action: "temperatureUp")
                attributeState("VALUE_DOWN", action: "temperatureDown")
            }
            tileAttribute("device.thermostatOperatingState", key: "OPERATING_STATE") {
                attributeState("idle", backgroundColor: "#44b621")
                attributeState("heating", backgroundColor: "#ffa81e")
                attributeState("cooling", backgroundColor: "#269bd2")
            }
            tileAttribute("device.thermostatMode", key: "THERMOSTAT_MODE") {
                attributeState("off", label: '${name}')
                attributeState("heat", label: '${name}')
                attributeState("cool", label: '${name}')
                attributeState("auto", label: '${name}')
            }
            tileAttribute("device.heatingSetpoint", key: "HEATING_SETPOINT") {
                attributeState("default", label: '${currentValue}', unit: "C")
            }
        }
        main("thermostatFull")
    }
}

def parse(String description) {
    log.debug "Parsing '${description}'"
}

def evaluate(outlets) {
    evaluateTemp(new Double(device.currentState("heatingSetpoint").value), new Double(device.currentState("temperature").value), outlets)
}

def evaluateTemp(Double setTemp, Double currentTemp, outlets) {
    log.debug("Desired temp is ${setTemp} in room ${device.name} with current temperature of ${currentTemp}")

    def currentMode = device.currentState("thermostatOperatingState").value
    def desiredMode = "idle"

    String heatingMode = "off"
    Double threshold = 0.5

    // Temp too low, start heating
    if (currentMode == "idle" && currentTemp < (setTemp - threshold)) {
        log.debug("Current temp (${currentTemp}) is lower than desired (${setTemp}) in room ${device.name}. Heating.")
        desiredMode = "heating"
        // Heating, not reached max yet
    } else if (currentMode == "heating" && currentTemp < (setTemp + threshold)) {
        log.debug("Continuing heating...")
        desiredMode = "heating"
    } else {
        log.debug("Idling...")
    }

    flipState(desiredMode == "heating" ? "on" : "off", outlets)

    if (currentMode != desiredMode) {
        updateMode(desiredMode, setTemp)
    }
}

private flipState(desiredState, outlets) {
    List wrongState = outlets.findAll { outlet -> outlet.currentValue("switch") != desiredState }

    wrongState.each { outlet ->
        if (enabled) {
            if (desiredState == "on") {
                outlet.on()
            } else {
                outlet.off()
            }
        } else {
            log.debug("Thermostat not enabled. Not changing state of switch.")
        }
    }
    if (wrongState.size > 0) {
        log.debug "Changed ${wrongState.size()} outlets in wrong state (Target state: $desiredState) ..."
    }
    return wrongState.size > 0
}


def updateMode(mode, setpoint) {
    if (setpoint == null) {
        throw new IllegalStateException("Could not update state because of null setpoint")
    }
    log.debug("Updated mode with ${mode} and temp ${setpoint}")
    mySendEvent(name: "thermostatOperatingState", value: mode)
    mySendEvent(name: "heatingSetpoint", value: setpoint)
}

def updateTemperature(newTemperature) {
    log.debug("Set temperature: ${newTemperature}")
    mySendEvent(name: "temperature", value: newTemperature)
}

// Capability methods
def setHeatingSetpoint(newTemp) {
    log.debug("setHeatingSetpoint(newTemp)")
    mySendEvent(name: "heatingSetpoint", value: newTemp)
    parent.evaluateAllRooms(this)
}

def auto() {
    log.debug("auto()")
}

def cool() {
    log.debug("cool()")
}

def emergencyHeat() {
    log.debug("emergencyHeat()")
}

def heat() {
    log.debug("heat()")
}

def off() {
    log.debug("off()")
}

def setCoolingSetpoint(setpoint) {
    log.debug("setCoolingSetpoint()" + setpoint)
}

def setSchedule(schedule) {
    log.debug("setSchedule()" + schedule)
}

def setThermostatFanMode(fanmode) {
    log.debug("setThermostatFanMode()" + fanmode)
}

def setThermostatMode(mode) {
    log.debug("setThermostatMode()" + mode)
}

// handle commands
def temperatureUp() {
    setHeatingSetpoint(new Double(device.currentState("heatingSetpoint").value) + 0.5)
}

def temperatureDown() {
    setHeatingSetpoint(new Double(device.currentState("heatingSetpoint").value) - 0.5)
}

def mySendEvent(event) {
    log.debug("SendEvent: " + event)
    sendEvent(event)
}
