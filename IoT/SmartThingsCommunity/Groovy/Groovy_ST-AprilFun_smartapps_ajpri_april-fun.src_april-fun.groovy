/* 
	Version 1 - April 1st, 2017
    	Initial Release
*/
 
definition(
    name: "April Fun",
    namespace: "ajpri",
    author: "Austin Pritchett",
    description: "When a switch is turned on, it'll turn off after a delay.",
    category: "",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Useless SmartApp") {
    	paragraph "When a light is turned on, it will automatically turn it off after a delay. Similar to Useless Boxes." 
        input name: "uselessBool", type: "bool", title: "Enable Useless SmartApp", description: "Enabled"
    	input "uselessSwitches", "capability.switch", multiple: true
        input "delay", "decimal", title: "Number of seconds", required: true, defaultValue: "2"
	}
    section("All-On") {
    	paragraph "When one light is turned on, the rest will be turned on" 
        input name: "allEnabled", type: "bool", title: "Enable All-On", description: "Enabled"
    	input "allSwitches", "capability.switch", multiple: true
        input "delay", "decimal", title: "Number of seconds", required: true, defaultValue: "2"
	}
}

def installed() {
	initialize()
}

def updated() {
	unsubscribe()
	initialize()
}

def initialize() {
    subscribe(uselessSwitches, "switch", uselessSwitchHandler)
    subscribe(allSwitches, "switch", allSwitchHandler)
}

def uselessSwitchHandler(evt) {
	if(uselessBool){
    	if (evt.value == "on") {
        	runIn(delay, switchesOff)
    	}
	}
}

def switchesOff() {
        uselessSwitches.off()
}

def allSwitchHandler(evt){
	if(allEnabled){
    	if (evt.value == "on") {
        	allSwitches.on()
    	}
	}
}