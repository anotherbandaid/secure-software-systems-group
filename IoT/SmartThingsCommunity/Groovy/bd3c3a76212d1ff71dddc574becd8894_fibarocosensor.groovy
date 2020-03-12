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
metadata {
	definition (name: "Fibaro CO Sensor", namespace: "gregoiredore", author: "Grégoire Doré") {
		capability "Sensor"
		capability "Battery"
		capability "Configuration"
		attribute "carbonMonoxide", "enum"

		fingerprint mfr: "0086", prod: "0102", model: "0064" //to be changed

	}


	tiles {
		standardTile("sensor", "device.sensor", width: 3, height: 2) {
			state("inactive", label:'CO LEVEL CLEAR', icon:"st.alarm.smoke.clear", backgroundColor:"#58ce5e")
			state("active", label:'CO LEVEL ABNORMAL', icon:"st.alarm.carbon-monoxide.carbon-monoxide", backgroundColor:"#dc0000")
			state("tested", label:'device testing' , icon:"st.alarm.carbon-monoxide.carbon-monoxide", backgroundColor:"#47beed") 
		}
		valueTile("battery", "device.battery", width: 3, height: 1, inactiveLabel: false, decoration: "flat") {
			state "battery", label:'${currentValue}% battery', unit:""
		}

		main "sensor"
		details(["sensor", "battery"])
	}
}

//to be changed
private getCommandClassVersions() {
	[0x20: 1, 0x30: 1, 0x80: 1, 0x84: 1, 0x71: 3]
}

def parse(String description) {
	def result = []
	if (description.startsWith("Err")) {
	    result = createEvent(descriptionText:description, displayed:true)
	} else {
		def cmd = zwave.parse(description, commandClassVersions)
		if (cmd) {
			result = zwaveEvent(cmd)
		}
	}
	log.debug "Parsed '$description' to $result"
	return result
}

def sensorValueEvent(value) {
	if (value == 0) {
		createEvent([ name: "sensor", value: "inactive" ])
	} else if (value == 255) {
		createEvent([ name: "sensor", value: "active" ])
	} else {
		[ createEvent([ name: "sensor", value: "active" ]),
			createEvent([ name: "level", value: value ]) ]
	}
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicReport cmd)
{
	sensorValueEvent(cmd.value)
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicSet cmd)
{
	sensorValueEvent(cmd.value)
}

def zwaveEvent(physicalgraph.zwave.commands.sensorbinaryv1.SensorBinaryReport cmd)
{
	sensorValueEvent(cmd.sensorValue)
}

def zwaveEvent(physicalgraph.zwave.commands.alarmv1.AlarmReport cmd)
{
	sensorValueEvent(cmd.alarmLevel)
}

def zwaveEvent(physicalgraph.zwave.commands.sensoralarmv1.SensorAlarmReport cmd)
{
	sensorValueEvent(cmd.sensorState)
}

def notificationEvent(String description, String value = "active") {
	createEvent([ name: "sensor", value: value, descriptionText: description, isStateChange: true ])
}

def zwaveEvent(physicalgraph.zwave.commands.notificationv3.NotificationReport cmd)
{
	def result = []
	{  // carbonMonoxyde Alarm
		setDeviceType("Z-Wave carbonMonoxyde Alarm")
		switch (cmd.event) {
			case 0x00:
			case 0xFE:
				result << notificationEvent("Carbon Monoxyde is clear", "inactive")
				result << createEvent(name: "carbonMonoxyde", value: "clear")
				break
			case 0x01:
			case 0x02:
				result << notificationEvent("Carbon Monoxyde detected")
				result << createEvent(name: "carbonMonoxyde", value: "detected")
				break
			case 0x03:
				result << notificationEvent("Carbon Monoxyde tested")
				result << createEvent(name: "carbonMonoxyde", value: "tested")
				break
		}
	} 
	result
}

def zwaveEvent(physicalgraph.zwave.commands.batteryv1.BatteryReport cmd) {
	def map = [ name: "battery", unit: "%" ]
	if (cmd.batteryLevel == 0xFF) {
		map.value = 1
		map.descriptionText = "${device.displayName} has a low battery"
	} else {
		map.value = cmd.batteryLevel
	}
	createEvent(map)
}

def zwaveEvent(physicalgraph.zwave.commands.securityv1.SecurityMessageEncapsulation cmd) {
	def encapsulatedCommand = cmd.encapsulatedCommand(commandClassVersions)
	if (encapsulatedCommand) {
		state.sec = 1
		zwaveEvent(encapsulatedCommand)
	}
}

def zwaveEvent(physicalgraph.zwave.commands.crc16encapv1.Crc16Encap cmd)
{
	// def encapsulatedCommand = cmd.encapsulatedCommand(commandClassVersions)
	def version = commandClassVersions[cmd.commandClass as Integer]
	def ccObj = version ? zwave.commandClass(cmd.commandClass, version) : zwave.commandClass(cmd.commandClass)
	def encapsulatedCommand = ccObj?.command(cmd.command)?.parse(cmd.data)
	if (encapsulatedCommand) {
		return zwaveEvent(encapsulatedCommand)
	}
}

def zwaveEvent(physicalgraph.zwave.commands.multichannelv3.MultiChannelCmdEncap cmd) {
	def result = null
	def encapsulatedCommand = cmd.encapsulatedCommand(commandClassVersions)
	log.debug "Command from endpoint ${cmd.sourceEndPoint}: ${encapsulatedCommand}"
	if (encapsulatedCommand) {
		result = zwaveEvent(encapsulatedCommand)
	}
	result
}

def zwaveEvent(physicalgraph.zwave.commands.multicmdv1.MultiCmdEncap cmd) {
	log.debug "MultiCmd with $numberOfCommands inner commands"
	cmd.encapsulatedCommands(commandClassVersions).collect { encapsulatedCommand ->
		zwaveEvent(encapsulatedCommand)
	}.flatten()
}

def zwaveEvent(physicalgraph.zwave.Command cmd) {
	createEvent(descriptionText: "$device.displayName: $cmd", displayed: false)
}


def configure() {
	if (zwaveInfo.zw && zwaveInfo.zw.cc?.contains("84")) {
		zwave.wakeUpV1.wakeUpNoMoreInformation().format()
	}
}