/**
 *	Enlighten Solar System (Local)
 *
 *	Copyright 2016 Andreas Amann
 *
 * Modified by Alan Anderson (2017) to use the API from the TED5000 (The Energy Detective).
 *
 *	Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *	in compliance with the License. You may obtain a copy of the License at:
 *
 *			http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *	on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *	for the specific language governing permissions and limitations under the License.
 *
 */

def version() {
	return "1.1.3-ted (20170116)\n© 2017 Alan Anderson"
}

preferences {
	input("confIpAddr", "string", title:"TED5000 Local IP Address",
		required: true, displayDuringSetup: true)
	input("confTcpPort", "number", title:"TCP Port",
		defaultValue:"80", required: true, displayDuringSetup: true)
    input("SolarMTU", "string", title:"MTU for Solar",
		defaultValue:"MTU2", required: true, displayDuringSetup: true)    
	input("confNumInverters", "number", title:"Number of Inverters/Panels",
		required: true, displayDuringSetup: true)
	input(title: "", description: "Inverter Size (W)\n\nRated maximum power in Watts for each inverter\n\nUse '225' for M215 and '250' for M250", type: "paragraph", element: "paragraph", displayDuringSetup: true)
	input("confInverterSize", "number", title:"",
		required: true, displayDuringSetup: true)
	input(title: "", description: "Panel Size (W)\n\nRated maximum power in Watts for each panel\n\nThis can be different than the maximum inverter power above", type: "paragraph", element: "paragraph", displayDuringSetup: true)
	input("confPanelSize", "number", title:"",
		required: true, displayDuringSetup: true)
	input(title:"", description: "Version: ${version()}", type: "paragraph", element: "paragraph")
}

metadata {
	definition (name: "TED5000 (local)", namespace: "andersonas25", author: "Alan Anderson") {
		capability "Power Meter"
		capability "Energy Meter"
		capability "Refresh"
		capability "Polling"

		attribute "energy_str", "string"
		attribute "energy_yesterday", "string"
		attribute "energy_MTD", "string"
		attribute "energy_Grid", "string"
		attribute "power_details", "string"
		attribute "efficiency", "string"
		attribute "efficiency_yesterday", "string"
		attribute "efficiency_MTD", "string"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles(scale: 2) {
		// this tile is used for display in device list (to get correct colorization)
		valueTile(
			"power",
			"device.power") {
				state("power",
					label: '${currentValue}W',
					unit: "W",
					icon: "https://raw.githubusercontent.com/ahndee/Envoy-ST/master/devicetypes/aamann/enlighten-envoy-local.src/Solar.png",
					backgroundColors: [
						[value: 0, color: "#bc2323"],
						[value: 1000, color: "#1e9cbb"],
						[value: 2000, color: "#90d2a7"]
					])
		}
		// this tile is used only to provide an icon in the recent events list
		valueTile(
			"energy",
			"device.energy") {
				state("energy",
					label: '${currentValue}',
					unit: "kWh")
		}
		// the following tiles are used for display in the device handler
		multiAttributeTile(
			name:"SolarMulti",
			type:"generic",
			width:6,
			height:4) {
				tileAttribute("device.power", key: "PRIMARY_CONTROL") {
					attributeState("power",
						label: '${currentValue}W',
						icon: "https://raw.githubusercontent.com/ahndee/Envoy-ST/master/devicetypes/aamann/enlighten-envoy-local.src/Solar-2.png",
						unit: "W",
						backgroundColors: [
							[value: 0, color: "#bc2323"],
							[value: 1000, color: "#1e9cbb"],
							[value: 2000, color: "#90d2a7"]
						])
			}
			tileAttribute("device.power_details", key: "SECONDARY_CONTROL") {
				attributeState("power_details",
					label: '${currentValue}')
			}
		}
		standardTile(
			"today",
			"today",
			width: 2,
			height: 2) {
				state("default",
					label: "Today")
		}
		valueTile(
			"energy_str",
			"device.energy_str",
			width: 2,
			height: 2,
			decoration: "flat",
			wordWrap: false) {
				state("energy_str",
					label: '${currentValue}')
		}
		valueTile(
			"efficiency",
			"device.efficiency",
			width: 2,
			height: 2) {
				state("efficiency",
					label: '${currentValue}',
					backgroundColors: [
						[value: 0, color: "#bc2323"],
						[value: 2, color: "#d04e00"],
						[value: 4, color: "#f1d801"],
						[value: 5, color: "#90d2a7"],
						[value: 6, color: "#44b621"]
					])
		}
		standardTile(
			"yesterday",
			"yesterday",
			width: 2,
			height: 2) {
				state("default",
					label: "Yesterday")
		}
		valueTile(
			"energy_yesterday",
			"device.energy_yesterday",
			width: 2,
			height: 2,
			decoration: "flat",
			wordWrap: false) {
				state("energy_yesterday",
					label: '${currentValue}')
		}
		valueTile(
			"efficiency_yesterday",
			"device.efficiency_yesterday",
			width: 2,
			height: 2) {
				state("efficiency_yesterday",
					label: '${currentValue}',
					backgroundColors: [
						[value: 0, color: "#bc2323"],
						[value: 2, color: "#d04e00"],
						[value: 4, color: "#f1d801"],
						[value: 5, color: "#90d2a7"],
						[value: 6, color: "#44b621"]
					])
		}
		standardTile(
			"MTD",
			"MTD",
			width: 2,
			height: 2) {
				state("default",
					label: "Month to Date")
		}
		valueTile(
			"energy_MTD",
			"device.energy_MTD",
			width: 2,
			height: 2,
			decoration: "flat",
			wordWrap: false) {
				state("energy_MTD",
					label: '${currentValue}')
		}
		valueTile(
			"efficiency_MTD",
			"device.efficiency_MTD",
			width: 2,
			height: 2) {
				state("efficiency_MTD",
					label: '${currentValue}',
					backgroundColors: [
						[value: 0, color: "#bc2323"],
						[value: 2, color: "#d04e00"],
						[value: 4, color: "#f1d801"],
						[value: 5, color: "#90d2a7"],
						[value: 6, color: "#44b621"]
					])
		}
		standardTile(
			"grid",
			"grid",
			width: 2,
			height: 2) {
				state("default",
					label: "Grid Net")
		}
		valueTile(
			"energy_Grid",
			"device.energy_Grid",
			width: 2,
			height: 2,
			decoration: "flat",
			wordWrap: false) {
				state("energy_Grid",
					label: '${currentValue}')
		}
		standardTile(
			"refresh",
			"device.refresh",
			inactiveLabel: false,
			decoration: "flat",
			width: 2,
			height: 2) {
				state("default",
					action:"polling.poll",
					label: "Refresh",
					icon:"st.secondary.refresh-icon")
		}
		htmlTile(name:"graphHTML",
			action: "getGraphHTML",
			refreshInterval: 1,
			width: 6,
			height: 4,
			whitelist: ["www.gstatic.com"])

		main "power"
		details(["SolarMulti", "graphHTML", "today", "energy_str", "efficiency", "yesterday", "energy_yesterday", "efficiency_yesterday", "MTD", "energy_MTD", "efficiency_MTD", "grid", "energy_Grid", "refresh"])
	}
}

mappings {
	path("/getGraphHTML") {action: [GET: "getGraphHTML"]}
}

def poll() {
	pullData()
}

def refresh() {
	pullData()
}

def updated() {
	log.trace("$device.displayName updated with settings: ${settings.inspect()}")
	state.maxPower = settings.confNumInverters * settings.confInverterSize
	pullData()
}

private def updateDNI() {
	if (!state.dni || state.dni != device.deviceNetworkId || (state.mac && state.mac != device.deviceNetworkId)) {
		device.setDeviceNetworkId(createNetworkId(settings.confIpAddr, settings.confTcpPort))
		state.dni = device.deviceNetworkId
	}
}

private String createNetworkId(ipaddr, port) {
	if (state.mac) {
		return state.mac
	}
	def hexIp = ipaddr.tokenize('.').collect {
		String.format('%02X', it.toInteger())
	}.join()
	def hexPort = String.format('%04X', port.toInteger())
	return "${hexIp}:${hexPort}"
}

private String getHostAddress() {
	return "${settings.confIpAddr}:${settings.confTcpPort}"
}

def pullData() {
	log.debug "${device.displayName} - requesting latest data from Envoy…"
	updateDNI()
	return new physicalgraph.device.HubAction([
		path: "/api/LiveData.xml",
		method: "GET",
		headers: [HOST:getHostAddress()]
	])
}

String getDataString(Integer seriesIndex) {
	def dataString = ""
	def dataTable = []
	switch (seriesIndex) {
		case 1:
			dataTable = state.energyTableYesterday
			break
		case 2:
			dataTable = state.powerTableYesterday
			break
		case 3:
			dataTable = state.energyTable
			break
		case 4:
			dataTable = state.powerTable
			break
	}
	dataTable.each() {
		def dataArray = [[it[0],it[1],0],null,null,null,null]
		dataArray[seriesIndex] = it[2]
		dataString += dataArray.toString() + ","
	}
	return dataString
}

private Map parseXMLLiveData(body) {
	def data = [:]
//	def LiveData = new XmlSlurper().parseText(body, MTU)
	def LiveData = new XmlSlurper().parseText(body)
    data.wattHoursToday =  Integer.parseInt("${LiveData.Power.MTU2['PowerTDY']}").abs()
    data.wattHoursMTD = Integer.parseInt("${LiveData.Power.MTU2['PowerMTD']}").abs()
    data.wattHoursGrid =  Integer.parseInt("${LiveData.Power.Total['PowerNow']}")
    data.wattsNow = Integer.parseInt("${LiveData.Power.MTU2['PowerNow']}").abs()
	return data
}


def parse(String message) {
	def msg = parseLanMessage(message)
	if (!state.mac || state.mac != msg.mac) {
		state.mac = msg.mac
	}
	if (!msg.body) {
		log.error "No HTTP body found in '${message}'"
		return null
	}
	//def data = msg.json
    //def data = msg.xml
//	def data = parseXMLLiveData(msg.body, settings.SolarMTU)
	def data = parseXMLLiveData(msg.body)
	if (data == state.lastData) {
		log.debug "${device.displayName} - no new data"
		return null
	}
	state.lastData = data
	log.debug "${device.displayName} - new data: ${data}"
	def energyToday = (data.wattHoursToday/1000).toFloat()
 	def energyMTD = (data.wattHoursMTD/1000).toFloat()
	def energyGrid = (data.wattHoursGrid/1000).toFloat()
	def currentPower = data.wattsNow
	def todayDay = new Date().format("dd",location.timeZone)
	def powerTable = state.powerTable
	def energyTable = state.energyTable
	if (!state.today || state.today != todayDay) {
		state.peakpower = currentPower
		state.today = todayDay
		state.powerTableYesterday = powerTable
		state.energyTableYesterday = energyTable
		powerTable = powerTable ? [] : null
		energyTable = energyTable ? [] : null
		state.lastPower = 0
		sendEvent(name: 'energy_yesterday', value: device.currentState("energy_str")?.value, displayed: false)
		sendEvent(name: 'efficiency_yesterday', value: device.currentState("efficiency")?.value, displayed: false)
	}
	def previousPower = state.lastPower != null ? state.lastPower : currentPower
	def powerChange = currentPower - previousPower
	state.lastPower = currentPower
	if (state.peakpower <= currentPower) {
		state.peakpower = currentPower
		state.peakpercentage = (100*state.peakpower/state.maxPower).toFloat()
	}
	def events = []
	events << createEvent(name: 'power_details', value: ("(" + String.format("%+,d", powerChange) + "W) — Today's Peak: " + String.format("%,d", state.peakpower) + "W (" + String.format("%.1f", state.peakpercentage) + "%)"), displayed: false)
	events << createEvent(name: 'energy_MTD', value: String.format("%,#.3f", energyMTD) + "KWh", displayed: false)
	events << createEvent(name: 'energy_Grid', value: String.format("%,#.3f", energyGrid) + "KWh", displayed: false)
	def efficiencyToday = (1000*energyToday/(settings.confNumInverters * settings.confPanelSize)).toFloat()
	events << createEvent(name: 'efficiency', value: String.format("%#.3f", efficiencyToday) + "\nkWh/kW", displayed: false)
	def efficiencyMTD = (1000/7*energyMTD/(settings.confNumInverters * settings.confPanelSize)).toFloat()
	events << createEvent(name: 'efficiency_MTD', value: String.format("%#.3f", efficiencyMTD) + "\nkWh/kW", displayed: false)
	events << createEvent(name: 'energy_str', value: String.format("%,#.3f", energyToday) + "kWh", displayed: false)
	events << createEvent(name: 'energy', value: energyToday, unit: "kWh", descriptionText: "Energy is " + String.format("%,#.3f", energyToday) + "kWh\n(Efficiency: " + String.format("%#.3f", efficiencyToday) + "kWh/kW)")
	events << createEvent(name: 'power', value: currentPower, unit: "W", descriptionText: "Power is " + String.format("%,d", currentPower) + "W (" + String.format("%#.1f", 100*currentPower/state.maxPower) + "%)\n(" + String.format("%+,d", powerChange) + "W since last reading)")
	// get power data for yesterday and today so we can create a graph
	if (state.powerTableYesterday == null || state.energyTableYesterday == null || powerTable == null || energyTable == null) {
		def startOfToday = timeToday("00:00", location.timeZone)
		def newValues
		if (state.powerTableYesterday == null || state.energyTableYesterday == null) {
			log.trace "Querying DB for yesterday's data…"
			def dataTable = []
			def powerData = device.statesBetween("power", startOfToday - 1, startOfToday, [max: 578]) // 24h in 2.5min intervals should be more than sufficient…
			// work around a bug where the platform would return less than the requested number of events (as June 2016, only 50 events are returned)
		    // was 288 for envoy  578 should equated to 2.5 minutes.
            if (powerData.size()) {
                while ((newValues = device.statesBetween("power", startOfToday - 1, powerData.last().date, [max: 288])).size()) {
                    powerData += newValues
                }
                powerData.reverse().each() {
                    dataTable.add([it.date.format("H", location.timeZone),it.date.format("m", location.timeZone),it.integerValue])
                }
			}
			state.powerTableYesterday = dataTable
			dataTable = []
			def energyData = device.statesBetween("energy", startOfToday - 1, startOfToday, [max: 288])
			if (energyData.size()) {
                while ((newValues = device.statesBetween("energy", startOfToday - 1, energyData.last().date, [max: 288])).size()) {
                    energyData += newValues
                }
                // we drop the first point after midnight (0 energy) in order to have the graph scale correctly
                energyData.reverse().drop(1).each() {
                    dataTable.add([it.date.format("H", location.timeZone),it.date.format("m", location.timeZone),it.floatValue])
                }
			}
			state.energyTableYesterday = dataTable
		}
		if (powerTable == null || energyTable == null) {
			log.trace "Querying DB for today's data…"
			powerTable = []
			def powerData = device.statesSince("power", startOfToday, [max: 288])
			if (powerData.size()) {
                while ((newValues = device.statesBetween("power", startOfToday, powerData.last().date, [max: 288])).size()) {
                    powerData += newValues
                }
                powerData.reverse().each() {
                    powerTable.add([it.date.format("H", location.timeZone),it.date.format("m", location.timeZone),it.integerValue])
                }
			}
			energyTable = []
			def energyData = device.statesSince("energy", startOfToday, [max: 288])
			if (energyData.size()) {
                while ((newValues = device.statesBetween("energy", startOfToday, energyData.last().date, [max: 288])).size()) {
                    energyData += newValues
                }
                energyData.reverse().drop(1).each() {
                    energyTable.add([it.date.format("H", location.timeZone),it.date.format("m", location.timeZone),it.floatValue])
                }
			}
		}
	}
	// add latest power & energy readings for the graph
	if (currentPower > 0 || powerTable.size() != 0) {
		def newDate = new Date()
		powerTable.add([newDate.format("H", location.timeZone),newDate.format("m", location.timeZone),currentPower])
		energyTable.add([newDate.format("H", location.timeZone),newDate.format("m", location.timeZone),energyToday])
	}
	state.powerTable = powerTable
	state.energyTable = energyTable
	return events
}

def getStartTime() {
	def startTime = 24
	if (state.powerTable.size()) {
		startTime = state.powerTable.min{it[0].toInteger()}[0].toInteger()
	}
	if (state.powerTableYesterday.size()) {
		startTime = Math.min(startTime, state.powerTableYesterday.min{it[0].toInteger()}[0].toInteger())
	}
	return startTime
}

def getGraphHTML() {
	def html = """
		<!DOCTYPE html>
			<html>
				<head>
					<meta http-equiv="cache-control" content="max-age=0"/>
					<meta http-equiv="cache-control" content="no-cache"/>
					<meta http-equiv="expires" content="0"/>
					<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT"/>
					<meta http-equiv="pragma" content="no-cache"/>
					<meta name="viewport" content="width = device-width">
					<meta name="viewport" content="initial-scale = 1.0, user-scalable=no">
					<style type="text/css">body,div {margin:0;padding:0}</style>
					<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
					<script type="text/javascript">
						google.charts.load('current', {packages: ['corechart']});
						google.charts.setOnLoadCallback(drawGraph);
						function drawGraph() {
							var data = new google.visualization.DataTable();
							data.addColumn('timeofday', 'time');
							data.addColumn('number', 'Energy (Yesterday)');
							data.addColumn('number', 'Power (Yesterday)');
							data.addColumn('number', 'Energy (Today)');
							data.addColumn('number', 'Power (Today)');
							data.addRows([
								${getDataString(1)}
								${getDataString(2)}
								${getDataString(3)}
								${getDataString(4)}
							]);
							var options = {
								fontName: 'San Francisco, Roboto, Arial',
								height: 240,
								hAxis: {
									format: 'H:mm',
									minValue: [${getStartTime()},0,0],
									slantedText: false
								},
								series: {
									0: {targetAxisIndex: 1, color: '#FFC2C2', lineWidth: 1},
									1: {targetAxisIndex: 0, color: '#D1DFFF', lineWidth: 1},
									2: {targetAxisIndex: 1, color: '#FF0000'},
									3: {targetAxisIndex: 0, color: '#004CFF'}
								},
								vAxes: {
									0: {
										title: 'Power (W)',
										format: 'decimal',
										textStyle: {color: '#004CFF'},
										titleTextStyle: {color: '#004CFF'}
									},
									1: {
										title: 'Energy (kWh)',
										format: 'decimal',
										textStyle: {color: '#FF0000'},
										titleTextStyle: {color: '#FF0000'}
									}
								},
								legend: {
									position: 'none'
								},
								chartArea: {
									width: '72%',
									height: '85%'
								}
							};
							var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
							chart.draw(data, options);
						}
					</script>
				</head>
				<body>
					<div id="chart_div"></div>
				</body>
			</html>
		"""
	render contentType: "text/html", data: html, status: 200
}
