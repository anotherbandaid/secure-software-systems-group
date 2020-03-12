/**
* fingerprint inClusters: "0x72,0x86,0x70,0x85,0x25,0x73,0x32,0x31,0x7a,0x25,0x91,0x75,0xef,0x32,0x31,0x91,0x2b,0x26,0x25"
*
* 0x72 V1 0x72 Manufacturer Specific
* 0x86 V1 0x86 Version
* 0x70 XX 0x70 Configuration
* 0x85 V2 0x85 Association
* 0x25 V1 0x25 Switch Binary
* 0x73 V1 COMMAND_CLASS_POWERLEVEL 
* 0x32 V2 0x32 Meter
* 0x31 V2 0x31 Sensor Multilevel
* 0x7a V1 COMMAND_CLASS_FIRMWARE_UPDATE_MD 
* 0x25 V1 0x25 Switch Binary
* 0x91 V1 COMMAND_CLASS_MANUFACTURER_PROPRIETARY 
* 0x75 V2 COMMAND_CLASS_PROTECTION_V2 
* 0xef XX COMMAND_CLASS_MARK 
* 0x32 V2 0x32 Meter
* 0x31 V2 0x31 Sensor Multilevel
* 0x91 V1 COMMAND_CLASS_MANUFACTURER_PROPRIETARY 
* 0x2b V1 COMMAND_CLASS_SCENE_ACTIVATION 
* 0x26 V3 0x26 Switch Multilevel
* 0x25 V1 0x25 Switch Binary
* 0x20 V1 UNKNOWN
*
* References :
* https://graph.api.smartthings.com/ide/doc/zwave-utils.html4
* http://www.pepper1.net/zwavedb/device/4922
* 
* Modified by CSC for DT82TV motor
*/
metadata {
definition (name: "Fibaro FGRM-222 (Robin)", namespace: "Robin", author: "Robin Winbourne") {
capability "Actuator"
capability "Switch Level"
capability "Switch"
capability "Door Control"
capability "Contact Sensor"
capability "Refresh"
capability "Sensor"
capability "Configuration"
capability "Polling"

  fingerprint inClusters: "0x8E,0x72,0x86,0x70,0x85,0x73,0x32,0x26,0x31,0x25,0x91,0x75" 

  command "open"
  command "stop"
  command "close"
  command "setposition"
  command "resetParams2StDefaults"
  command "listCurrentParams"
  command "updateZwaveParam"
  command "test", ["number","number","number"]
  command "configure"
  command "motortime18"
  command "motortime240"
  command "forcecalibration"

}
}

tiles {
standardTile("open", "device.switch", inactiveLabel: false, decoration: "flat") {
state "default", label:'open', action:"open", icon:"st.doors.garage.garage-open"
}
standardTile("stop", "device.switch", inactiveLabel: false, decoration: "flat") {
state "default", label:'stop', action:"stop", icon:"st.doors.garage.garage-opening"
}
standardTile("close", "device.switch", inactiveLabel: false, decoration: "flat") {
state "default", label:'close', action:"close", icon:"st.doors.garage.garage-closed"
}
controlTile("levelSliderControl", "device.level", "slider", height: 1, width: 2, inactiveLabel: false) {
state "level", action:"setposition"
}

    standardTile("refresh", "device.switch", inactiveLabel: false, decoration: "flat") {
        state "default", label:'', action:"refresh.refresh", icon:"st.secondary.refresh"
    }
    standardTile("configure", "device.switch", inactiveLabel: false, decoration: "flat") {
        				state "default", label:"", action:"configure", icon:"st.secondary.configure"
                }
            standardTile("motortime18", "device.switch", inactiveLabel: false, decoration: "flat") {
        				state "default", label:"18", action:"motortime18"//, icon:"st.secondary.configure"
                }
                    standardTile("motortime240", "device.switch", inactiveLabel: false, decoration: "flat") {
        				state "default", label:"240", action:"motortime240"//, icon:"st.secondary.configure"
                }
standardTile("forcecalibration", "device.switch", inactiveLabel: false, decoration: "flat") {
        				state "default", label:"Param 10 = 0", action:"forcecalibration"//, icon:"st.secondary.configure"
                }


    details(["open", "stop", "close", "levelSliderControl", "refresh", "configure"])
}

 preferences {
        def paragraph = "Parameter Settings"
        input name: "param22", type: "number", range: "0..65535", defaultValue: "240", required: true,
            title: paragraph + "\n\n" +
                   "22. Motor operation time. \n\n" +
                   "Avilable Settings:\n" +
                   "0 - The function is disabled\n" +
                   "1 – 65535 (1 – 65535s)\n" +
                   "Default value: 240 (240s - 4 minutes)."

        input name: "param10", type: "number", range: "0..4", defaultValue: "1", required: true,
            title: "10. Roller Shutter operating mode " +
                  "Available settings:\n" +
                   "0 - Roller Blind Mode, without positioning,\n" +
                   "1 - Roller Blind Mode, with positioning,\n" +
                   "2 - Venetian Blind Mode, with positioning,\n" +
                   "3 - Gate Mode, without positioning,\n" +
                   "4 - Gate Mode, with positioning,\n" +
                   "Default value: 1."
		   

        input name: "param18", type: "number", range: "0..255", defaultValue: "1", required: true,
            title: "18. Motor operation detection. " +
                   "Power threshold to be interpreted as reaching a limit switch. (For DT82TV Motor, set this value to 0.)  \n" +
                  "Available settings:\n" +
                   "0 - 255 (1-255 W).\n" +
                   "Default value: 10 (10W)."
		   
	input name: "param29", type: "number", range: "0..1", defaultValue: "0", required: true,
            title: "29. Forced Roller Shutter calibration. " +
                   "By modifying the parameters setting from 0 to 1 a Roller Shutter enters the calibration mode. The parameter relevant only if a Roller Shutter is set to work in positioning mode (parameter 10 set to 1, 2 or 4).  \n" +
                  "Available settings:\n" +
                   "1 - Start calibration process. \n" +
                   "Default value: 0."   
}



def parse(String description) {
log.debug "Parsing '${description}'"

def result = null
def cmd = zwave.parse(description, [0x20: 1, 0x25: 1, 0x31: 2, 0x32: 2, 0x85: 2, 0x75: 2, 0x26: 3])
log.debug "Parsed ${cmd}"

if (cmd ) {
    result = createEvent(zwaveEvent(cmd))
    return result
} else {
    log.debug "Non-parsed event: ${description}"
}
}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv3.SwitchMultilevelSet cmd) {
log.debug "Report SwitchMultilevelSet:${cmd}"

}

def init()
{
log.debug "oo"
}

def zwaveEvent(physicalgraph.zwave.commands.switchbinaryv1.SwitchBinaryGet cmd) {
log.debug "Report SwitchBinaryGet:${cmd}"

}

def zwaveEvent(physicalgraph.zwave.commands.switchbinaryv1.SwitchBinaryReport cmd) {
log.debug "Report SwitchBinaryReport:${cmd}"

}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv3.SwitchMultilevelReport cmd) {
log.debug "Report SwitchMultilevelReport:${cmd}"

}

def zwaveEvent(physicalgraph.zwave.commands.switchbinaryv1.SwitchBinarySet cmd) {
log.debug "Report SwitchBinarySet:${cmd}"

}

def zwaveEvent(physicalgraph.zwave.commands.configurationv1.ConfigurationReport cmd) {
def value = "when off"
if (cmd.configurationValue[0] == 1) {value = "when on"}
if (cmd.configurationValue[0] == 2) {value = "never"}
[name: "indicatorStatus", value: value, display: false]
}

def zwaveEvent(physicalgraph.zwave.commands.versionv1.VersionCommandClassReport cmd) {
def rcc = ""
rcc = Integer.toHexString(cmd.requestedCommandClass.toInteger()).toString()

if (cmd.commandClassVersion > 0) {log.debug "0x${rcc}_V${cmd.commandClassVersion}"}
}

def open() {
delayBetween([
zwave.basicV1.basicSet(value: 0xFF).format(),
zwave.switchBinaryV1.switchBinaryGet().format()
],500)
}

def stop() {
delayBetween([
zwave.switchBinaryV1.switchBinarySet(switchValue: 0xFF).format()
],500)
}

def close() {
delayBetween([
zwave.basicV1.basicSet(value: 0x00).format(),
zwave.switchBinaryV1.switchBinaryGet().format()
],500)
}

//"test" button for for parameter changes in IDE
//1 and 2 byte parameters only
def test(param ,value, size) {
def hByte = (value / 256).toInteger()
def lByte = value.toInteger() % 256 //mod
def pv
//log.debug "lb:${lByte} hb:${hByte}"
if (size == 1) {
pv = [lByte]
} else {
pv = [hByte,lByte]
}
def params = [paramNumber:param.toInteger(),value:pv,size:size.toInteger()]

//log.debug "test:${params.inspect()}"
updateZwaveParam(params)
}

/**
* This method will allow the user to update device parameters (behavior) from an app.
* A "Zwave Tweaker" app will be developed as an interface to do this. Or the user can
* write his/her own app to envoke this method. No type or value checking is done to
* compare to what device capability or reaction. It is up to user to read OEM
* documentation prio to envoking this method.
*
*

THIS IS AN ADVANCED OPERATION. USE AT YOUR OWN RISK! READ OEM DOCUMENTATION!
*
* @param List[paramNumber:80,value:10,size:1]
*
*
* @return none
*/
def updateZwaveParam(params) {
//
if ( params ) { 
def pNumber = params.paramNumber
def pSize	= params.size
//def pValue	= [params.value]
def pValue = params.value
log.debug "Make sure device is awake and in recieve mode"
log.debug "Updating ${device.displayName} parameter number '${pNumber}' with value '${pValue}' with size of '${pSize}'"

	def cmds = []
    cmds << zwave.configurationV1.configurationSet(configurationValue: pValue, parameterNumber: pNumber, size: pSize).format()
    cmds << zwave.configurationV1.configurationGet(parameterNumber: pNumber).format()
    delayBetween(cmds, 1000)        
}
}

def configure() {
	log.debug "Executing 'configure'"
    delayBetween([
        //  zwave.configurationV1.configurationSet(parameterNumber: 22, size: 2, scaledConfigurationValue: param22.toInteger()),
         // zwave.configurationV1.configurationSet(parameterNumber: 22, size: 2, configurationValue:[param22.value]).format(),
         zwave.configurationV1.configurationSet(parameterNumber: 22, size: 2, configurationValue:[param22.value]).format(),
	 zwave.configurationV1.configurationSet(parameterNumber: 10, size: 1, configurationValue:[param10.value]).format(),
	 zwave.configurationV1.configurationSet(parameterNumber: 18, size: 1, configurationValue:[param18.value]).format(),
	 zwave.configurationV1.configurationSet(parameterNumber: 29, size: 1, configurationValue:[param29.value]).format(),
	 zwave.associationV2.associationSet(groupingIdentifier:1, nodeId:[zwaveHubNodeId]).format(),
          zwave.associationV2.associationSet(groupingIdentifier:2, nodeId:[zwaveHubNodeId]).format(),
          zwave.associationV2.associationSet(groupingIdentifier:3, nodeId:[zwaveHubNodeId]).format(),
          
    ])
}

def motortime18() {
	log.debug "Executing 'Motor Time 18'"
    delayBetween([
         // zwave.configurationV1.configurationSet(parameterNumber: 22, size: 2, scaledConfigurationValue: 18),
   zwave.configurationV1.configurationSet(parameterNumber: 22, size: 2, configurationValue:[18]).format(),
   ])
}

def motortime240() {
	log.debug "Executing 'Motor Time 240'"
    delayBetween([
         // zwave.configurationV1.configurationSet(parameterNumber: 22, size: 2, scaledConfigurationValue: 240),
  zwave.configurationV1.configurationSet(parameterNumber: 22, size: 2, configurationValue:[240]).format(),
  ])
}

def forcecalibration() {
	log.debug "Executing 'param 10 = 0'"
    delayBetween([
       //   zwave.configurationV1.configurationSet(parameterNumber: 29, size: 1, scaledConfigurationValue: [1]).format(),
	  zwave.configurationV1.configurationSet(parameterNumber: 10, size: 1, configurationValue:[0]).format(),
    ])
}

/**
* Sets all of available Fibaro parameters back to the device defaults except for what
* SmartThings needs to support the stock functionality as released. This will be
* called from the "Fibaro Tweaker" or user's app.
*
*

THIS IS AN ADVANCED OPERATION. USE AT YOUR OWN RISK! READ OEM DOCUMENTATION!
*
* @param none
*
* @return none
*/
def resetParams2StDefaults() {
log.debug "Resetting Sensor Parameters to Defaults"
def cmds = []
cmds << zwave.configurationV1.configurationSet(configurationValue: [0], parameterNumber: 2, size: 1).format()
cmds << zwave.configurationV1.configurationSet(configurationValue: [0], parameterNumber: 3, size: 2).format()
cmds << zwave.configurationV1.configurationSet(configurationValue: [1], parameterNumber: 10, size: 1).format()
cmds << zwave.configurationV1.configurationSet(configurationValue: [0], parameterNumber: 12, size: 2).format()
cmds << zwave.configurationV1.configurationSet(configurationValue: [1], parameterNumber: 13, size: 1).format()
cmds << zwave.configurationV1.configurationSet(configurationValue: [0], parameterNumber: 14, size: 1).format()
cmds << zwave.configurationV1.configurationSet(configurationValue: [0], parameterNumber: 18, size: 1).format()

delayBetween(cmds, 500)
}

/**
* Lists all of available Fibaro parameters and thier current settings out to the 
* logging window in the IDE This will be called from the "Fibaro Tweaker" or 
* user's own app.
*
*

THIS IS AN ADVANCED OPERATION. USE AT YOUR OWN RISK! READ OEM DOCUMENTATION!
*
* @param none
*
* @return none
*/
def listCurrentParams() {
log.debug "Listing of current parameter settings of ${device.displayName}"
def cmds = []
cmds << zwave.configurationV1.configurationGet(parameterNumber: 10).format() 
cmds << zwave.configurationV1.configurationGet(parameterNumber: 12).format()
cmds << zwave.configurationV1.configurationGet(parameterNumber: 17).format()

delayBetween(cmds, 500)
}
