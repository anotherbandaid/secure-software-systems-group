/**
 *  Enhanced GE/Jasco Dimmer Switch
 *	Author: Daniel Ionescu (@Nomad-Tech)
 *
 * Variation of the stock SmartThings "Dimmer-Switch"
 * Variation of the twack "Better-Dimmer-Switch"
 * Variation of the ChadCK "Z-Wave Smart Fan Control"
 * Variation of the Dimmer Switch under Templates in the IDE 
 *Variation of the Enhanced Dimmer Switch (@desertblade)
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
	definition (name: "Enhanced Dimmer Switch with Levels", namespace: "desertblade", author: "Daniel Ionescu.") {
		capability "Switch Level"
		capability "Actuator"
		capability "Indicator"
		capability "Switch"
		capability "Polling"
		capability "Refresh"
		capability "Sensor"
    
    command "low"
    command "med"
    command "high"
    command "levelUp"
    command "levelDown"    
    command "updateSettings"
      
    attribute "currentState", "string"
    attribute "switch", "string"
	
fingerprint mfr: "0063", prod: "4944", model: "3031"

	}

	preferences {
       input ( "stepSize", "number", title: "zWave Size of Steps in Percent",
              defaultValue: 1, range: "1..99", required: false)
       input ( "stepDuration", "number", title: "zWave Steps Intervals each 10 ms",
              defaultValue: 3,range: "1..255", required: false)
       input ( "invertSwitch", "boolean", title: "Is the switch Inverted?",
              defaultValue: false, required: false)
       input ( "manualStepSize", "number", title: "Manual Size of Steps in Percent",
              defaultValue: 1, range: "1..99", required: false)
       input ( "manualStepDuration", "number", title: "Manual Steps Intervals Each 10 ms",
              defaultValue: 3,range: "1..255", required: false)
              
   section("Light Level Values") {
	input "lowThreshold", "number", title: "Low Button Light Value", range: "1..99"
	input "medThreshold", "number", title: "Medium Button Light Value", range: "1..99"
	input "highThreshold", "number", title: "High Button Light Value", range: "1..99"
    }

	simulator {
		status "on":  "command: 2003, payload: FF"
		status "off": "command: 2003, payload: 00"
		status "09%": "command: 2003, payload: 09"
		status "10%": "command: 2003, payload: 0A"
		status "33%": "command: 2003, payload: 21"
		status "66%": "command: 2003, payload: 42"
		status "99%": "command: 2003, payload: 63"

		// reply messages
		reply "2001FF,delay 5000,2602": "command: 2603, payload: FF"
		reply "200100,delay 5000,2602": "command: 2603, payload: 00"
		reply "200119,delay 5000,2602": "command: 2603, payload: 19"
		reply "200132,delay 5000,2602": "command: 2603, payload: 32"
		reply "20014B,delay 5000,2602": "command: 2603, payload: 4B"
		reply "200163,delay 5000,2602": "command: 2603, payload: 63"
	}

	tiles(scale: 2)
{
	multiAttributeTile(name:"switch", type: "lighting", width: 6, height: 4, canChangeIcon: true){
		tileAttribute ("device.currentState", key: "PRIMARY_CONTROL")
		{
		attributeState "default", label:'ADJUSTING', action:"refresh.refresh",icon:"st.Lighting.light13", backgroundColor:"#2179b8", nextState: "turningOff"
		attributeState "HIGH", label:'HIGH', action:"switch.off", icon:"st.illuminance.illuminance.bright", backgroundColor:"#79b821", nextState:"turningOff"
		attributeState "MED", label:'MED', action:"switch.off", icon:"st.illuminance.illuminance.light", backgroundColor:"#60931a", nextState:"turningOff" 
		attributeState "LOW", label:'LOW', action:"switch.off", icon:"st.illuminance.illuminance.dark", backgroundColor:"#486e13", nextState:"turningOff" 
		attributeState "OFF", label:'OFF', action:"switch.on", icon:"st.Lighting.light13", backgroundColor:"#ffffff", nextState: "turningOn"
	        attributeState "on", label:'ON', action:"switch.off", icon:"st.Lighting.light11", backgroundColor:"#79b821", nextState:"turningOff"
	        attributeState "turningOn", action:"switch.on", label:'TURNINGON', icon:"st.Lighting.light11", backgroundColor:"#2179b8", nextState: "turningOn"
		attributeState "turningOff", action:"switch.off", label:'TURNINGOFF', icon:"st.Lighting.light13", backgroundColor:"#2179b8", nextState: "turningOff"
		}
	            
	    	tileAttribute("device.level", key: "VALUE_CONTROL")
		{
	    	attributeState("VALUE_UP", action: "levelUp")
	        attributeState("VALUE_DOWN", action: "levelDown")
		}
		tileAttribute ("device.level", key: "SLIDER_CONTROL")
		{
		attributeState "level", action:"switch level.setLevel"
		}
}
	
		standardTile("indicator", "device.indicatorStatus", width: 2, height: 2, inactiveLabel: false, decoration: "flat")
		{
		state "when off", action:"indicator.indicatorWhenOn", icon:"st.indicators.lit-when-off"
		state "when on", action:"indicator.indicatorNever", icon:"st.indicators.lit-when-on"
		state "never", action:"indicator.indicatorWhenOff", icon:"st.indicators.never-lit"
		}
            
		standardTile("refresh", "device.switch", width: 2, height: 2, inactiveLabel: false, decoration: "flat")
		{
		state "default", label:'', action:"refresh.refresh", icon:"st.secondary.refresh"
		}

		valueTile("level", "device.level", inactiveLabel: false, decoration: "flat", width: 2, height: 2)
		{
		state "level", label:'${currentValue} %', unit:"%", backgroundColor:"#ffffff"
		}

	    standardTile("low", "device.currentState", inactiveLabel: false, width: 2, height: 2, canChangeBackground: false)
	    {
	    state "default", label: 'LOW', action: "low", icon:"st.illuminance.illuminance.dark", backgroundColor: "#ffffff"
	    state "LOW", label:'LOW', action: "low", icon:"st.illuminance.illuminance.dark", backgroundColor: "#486e13"
	    state "ADJUSTING.LOW", label:'LOW', action: "low", icon:"st.illuminance.illuminance.dark", backgroundColor: "#2179b8"
	    }
	        
	    standardTile("med", "device.currentState", inactiveLabel: false, width: 2, height: 2, canChangeBackground: false)
	    {
	    state "default", label: 'MED', action: "med", icon:"st.illuminance.illuminance.light", backgroundColor: "#ffffff"
	    state "MED", label: 'MED', action: "med", icon:"st.illuminance.illuminance.light", backgroundColor: "#60931a"
	    state "ADJUSTING.MED", label:'MED', action: "med", icon:"st.illuminance.illuminance.light", backgroundColor: "#2179b8"
	    }
	            
	    standardTile("high", "device.currentState", inactiveLabel: false, width: 2, height: 2, canChangeBackground: false)
	    {
	    state "default", label: 'HIGH', action: "high", icon:"st.illuminance.illuminance.bright", backgroundColor: "#ffffff"
	    state "HIGH", label: 'HIGH', action: "high", icon:"st.illuminance.illuminance.bright", backgroundColor: "#79b821"
	    state "ADJUSTING.HIGH", label:'HIGH', action: "high", icon:"st.illuminance.illuminance.bright", backgroundColor: "#2179b8"
      	    }


	main(["switch"])
	details(["switch", "low", "med", "high", "level", "indicator", "refresh"])

	}
}

def parse(String description) {
	def result = null
	if (description != "updated") {
		log.debug "parse() >> zwave.parse($description)"
		def cmd = zwave.parse(description, [0x20: 1, 0x26: 1, 0x70: 1])
		if (cmd) {
			result = zwaveEvent(cmd)
		}
	}
	if (result?.name == 'hail' && hubFirmwareLessThan("000.011.00602")) {
		result = [result, response(zwave.basicV1.basicGet())]
		log.debug "Was hailed: requesting state update"
	} else {
		log.debug "Parse returned ${result?.descriptionText}"
	}
	return result
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicReport cmd) {
	dimmerEvents(cmd)
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicSet cmd) {
	dimmerEvents(cmd)
}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv1.SwitchMultilevelReport cmd) {
	dimmerEvents(cmd)
}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv1.SwitchMultilevelSet cmd) {
	dimmerEvents(cmd)
}

private dimmerEvents(physicalgraph.zwave.Command cmd) {
	def value = (cmd.value ? "on" : "off")
	def result = [createEvent(name: "switch", value: value)]
	if (cmd.value && cmd.value <= 100) {
		result << createEvent(name: "level", value: cmd.value, unit: "%")
	}
	return result
}


def zwaveEvent(physicalgraph.zwave.commands.configurationv1.ConfigurationReport cmd) {
	log.debug "ConfigurationReport $cmd"
	def value = "when off"
	if (cmd.configurationValue[0] == 1) {value = "when on"}
	if (cmd.configurationValue[0] == 2) {value = "never"}
	createEvent([name: "indicatorStatus", value: value])
}

def zwaveEvent(physicalgraph.zwave.commands.hailv1.Hail cmd) {
	createEvent([name: "hail", value: "hail", descriptionText: "Switch button was pressed", displayed: false])
}

def zwaveEvent(physicalgraph.zwave.commands.manufacturerspecificv2.ManufacturerSpecificReport cmd) {
	log.debug "manufacturerId:   ${cmd.manufacturerId}"
	log.debug "manufacturerName: ${cmd.manufacturerName}"
	log.debug "productId:        ${cmd.productId}"
	log.debug "productTypeId:    ${cmd.productTypeId}"
	def msr = String.format("%04X-%04X-%04X", cmd.manufacturerId, cmd.productTypeId, cmd.productId)
	updateDataValue("MSR", msr)
	createEvent([descriptionText: "$device.displayName MSR: $msr", isStateChange: false])
}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv1.SwitchMultilevelStopLevelChange cmd) {
	[createEvent(name:"switch", value:"on"), response(zwave.switchMultilevelV1.switchMultilevelGet().format())]
}

def zwaveEvent(physicalgraph.zwave.Command cmd) {
	// Handles all Z-Wave commands we aren't interested in
	[:]
}

def on() {
	delayBetween([
			zwave.basicV1.basicSet(value: 0xFF).format(),
			zwave.switchMultilevelV1.switchMultilevelGet().format()
	],5000)
       
}

def off() {
	delayBetween([
			zwave.basicV1.basicSet(value: 0x00).format(),
			zwave.switchMultilevelV1.switchMultilevelGet().format()
	],5000)
}

def setLevel(value) {
	log.debug "setLevel >> value: $value"
	   def valueaux = value as Integer
	   
	   def lowThresholdvalue = (settings.lowThreshold != null && settings.lowThreshold != "") ? settings.lowThreshold.toInteger() : 33
		 def medThresholdvalue = (settings.medThreshold != null && settings.medThreshold != "") ? settings.medThreshold.toInteger() : 67
		 def highThresholdvalue = (settings.highThreshold != null && settings.highThreshold != "") ? settings.highThreshold.toInteger() : 99
		 
	   def level = Math.max(Math.min(valueaux, 99), 0)
	   
	   if (value == "LOW") { value = lowThresholdvalue }
		 if (value == "MED") { value = medThresholdvalue }
		 if (value == "HIGH") { value = highThresholdvalue }
	
	if (level > 0) {sendEvent(name: "switch", value: "on")} 
	else {sendEvent(name: "switch", value: "off")}
	
	sendEvent(name: "level", value: level, unit: "%")
	if (level == lowThresholdvalue) { sendEvent(name: "currentState", value: "ADJUSTING.LOW" as String, displayed: false) } 
	    else if (level == medThresholdvalue) { sendEvent(name: "currentState", value: "ADJUSTING.MED" as String, displayed: false) } 
	    else if (level == highThresholdvalue) { sendEvent(name: "currentState", value: "ADJUSTING.HIGH" as String, displayed: false) } 
	    else if (level == 0) { sendEvent(name: "currentState", value: "OFF" as String, displayed: false) } 
	    else { sendEvent(name: "currentState", value: "on" as String, displayed: false) }
	    
	delayBetween ([zwave.basicV1.basicSet(value: level).format(), zwave.switchMultilevelV1.switchMultilevelGet().format()], 5000)
}

def setLevel(value, duration) {
	log.debug "setLevel >> value: $value, duration: $duration"
	def valueaux = value as Integer
	  def lowThresholdvalue = (settings.lowThreshold != null && settings.lowThreshold != "") ? settings.lowThreshold.toInteger() : 33
		def medThresholdvalue = (settings.medThreshold != null && settings.medThreshold != "") ? settings.medThreshold.toInteger() : 67
		def highThresholdvalue = (settings.highThreshold != null && settings.highThreshold != "") ? settings.highThreshold.toInteger() : 99

		if (value == "LOW") { value = lowThresholdvalue }
		if (value == "MED") { value = medThresholdvalue }
		if (value == "HIGH") { value = highThresholdvalue }	
		
	def level = Math.max(Math.min(valueaux, 99), 0)
	def dimmingDuration = duration < 128 ? duration : 128 + Math.round(duration / 60)
	def getStatusDelay = duration < 128 ? (duration*1000)+2000 : (Math.round(duration / 60)*60*1000)+2000
	delayBetween ([zwave.switchMultilevelV2.switchMultilevelSet(value: level, dimmingDuration: dimmingDuration).format(),
				   zwave.switchMultilevelV1.switchMultilevelGet().format()], getStatusDelay)
}

def poll() {
	zwave.switchMultilevelV1.switchMultilevelGet().format()
}

def refresh() {
	log.debug "refresh() is called"
	def commands = []
	commands << zwave.switchMultilevelV1.switchMultilevelGet().format()
	if (getDataValue("MSR") == null) {
		commands << zwave.manufacturerSpecificV1.manufacturerSpecificGet().format()
	}
	delayBetween(commands,100)
}

def indicatorWhenOn() {
	sendEvent(name: "indicatorStatus", value: "when on")
	zwave.configurationV1.configurationSet(configurationValue: [1], parameterNumber: 3, size: 1).format()
}

def indicatorWhenOff() {
	sendEvent(name: "indicatorStatus", value: "when off")
	zwave.configurationV1.configurationSet(configurationValue: [0], parameterNumber: 3, size: 1).format()
}

def indicatorNever() {
	sendEvent(name: "indicatorStatus", value: "never")
	zwave.configurationV1.configurationSet(configurationValue: [2], parameterNumber: 3, size: 1).format()
}

def invertSwitch(invert) {
	if (invert) {
		zwave.configurationV1.configurationSet(configurationValue: [1], parameterNumber: 4, size: 1).format()
	}
	else {
		zwave.configurationV1.configurationSet(configurationValue: [0], parameterNumber: 4, size: 1).format()
	}
}

def updateSettings() {
	log.debug("Updating Switch Settings")
	
    //lets make sure we are in the the right ranges
    def stepSize = Math.max(Math.min(stepSize, 99), 1)
    def stepDuration = Math.max(Math.min(stepDuration, 255), 1)
    def manualStepSize = Math.max(Math.min(manualStepSize, 99), 1)
    def manualStepDuration = Math.max(Math.min(manualStepDuration, 255), 1)
   
     def cmds = []
        cmds << zwave.configurationV1.configurationSet(configurationValue: [stepSize], parameterNumber: 7, size: 1).format()
        cmds << zwave.configurationV1.configurationSet(configurationValue: [stepDuration], parameterNumber: 8, size: 1).format()
        cmds << zwave.configurationV1.configurationSet(configurationValue: [manualStepSize], parameterNumber: 9, size: 1).format()
        cmds << zwave.configurationV1.configurationSet(configurationValue: [manualStepDuration], parameterNumber: 10, size: 1).format()
        
        if (invertSwitch.toBoolean()) {
		    cmds << zwave.configurationV1.configurationSet(configurationValue: [1], parameterNumber: 4, size: 1).format()
		} else {
			cmds << zwave.configurationV1.configurationSet(configurationValue: [0], parameterNumber: 4, size: 1).format()
		}
        
        //Getting the new settings (check logs) -- Don't really use for anything else
      
        cmds << zwave.configurationV1.configurationGet(parameterNumber: 7).format()
   		cmds << zwave.configurationV1.configurationGet(parameterNumber: 8).format()
    	cmds << zwave.configurationV1.configurationGet(parameterNumber: 9).format()
    	cmds << zwave.configurationV1.configurationGet(parameterNumber: 10).format()
        cmds << zwave.configurationV1.configurationGet(parameterNumber: 4).format()
    
    delayBetween(cmds, 500)
