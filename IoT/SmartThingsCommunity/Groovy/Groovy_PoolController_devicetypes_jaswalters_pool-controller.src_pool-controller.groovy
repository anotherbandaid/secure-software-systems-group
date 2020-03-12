/**
 *  Raspberry Pi Pool Controller
 *
 *  Copyright 2019
 *
 *  Control your pool equipment using a Raspberry Pi
 *
 *  
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

import groovy.json.JsonSlurper

preferences {
    input("ip", "string", title: "IP Address", description: "192.168.x.x", required: true, displayDuringSetup: true)
    input("port", "string", title: "Port", description: "8000", defaultValue: 8000, required: true, displayDuringSetup: true)
}

metadata {
    definition(name: "Pool Controller", namespace: "jaswalters", author: "Jason Walters") {
        capability "Polling"
        capability "Refresh"
        capability "Temperature Measurement"
        capability "Switch"
        capability "Sensor"
        capability "Actuator"
        attribute "cpuPercentage", "string"
        attribute "memory", "string"
        attribute "diskUsage", "string"
        attribute "FountainMode", "string"
        attribute "RPiPower", "string"
        attribute "cpuTemperature", "string"
        attribute "memUsage", "string"
        attribute "PoolPump", "string"
        attribute "WaterFallMode", "string"
        attribute "SpaMode", "string"

        command "restart"
        command "FountainModeOn"
        command "WaterFallModeOn"
        command "SpaModeOn"
        command "WaterFallModeOn"
		command "PoolPumpOff"
        command "PoolPumpOn"
    }

    simulator {
        // TODO: define status and reply messages here
    }

    tiles(scale: 1) {
        multiAttributeTile(name: "PoolPump", width: 1, height: 1) {
            tileAttribute("device.PoolPump", key: "PRIMARY_CONTROL") {
                attributeState "on", label: 'On', action: "PoolPumpOff", icon: "https://img.icons8.com/ios/50/000000/pump-filled.png", backgroundColor: "#00a0dc"
                attributeState "off", label: 'Off', action: "PoolPumpOn", icon: "https://img.icons8.com/ios/50/000000/pump.png", backgroundColor: "#ffffff"
                attributeState "turningOn", label: 'Turning On', icon: "https://img.icons8.com/ios/50/000000/pump-filled.png", backgroundColor: "#00a0dc"
                attributeState "turningOff", label: 'Turning Off', icon: "https://img.icons8.com/ios/50/000000/pump.png", backgroundColor: "#ffffff"
            }
        }
        valueTile("cpuTemperature", "device.cpuTemperature", width: 2, height: 2) {
            state "temperature", label: '${currentValue}°F', icon: "https://img.icons8.com/wired/64/000000/processor.png", unit: "F"
        }
        standardTile("RPiPower", "device.RPiPower", width: 2, height: 2, decoration: "flat") {
            state "off", label: 'Off', icon: "https://img.icons8.com/metro/50/000000/raspberry-pi.png", nextState: "on"
            state "on", label: 'On', icon: "https://img.icons8.com/metro/50/000000/raspberry-pi.png", nextState: "off"
            state "rebooting", label: 'Rebooting', icon: "https://img.icons8.com/metro/50/000000/raspberry-pi.png", nextState: "off"
        }
        valueTile("cpuPercentage", "device.cpuPercentage", width: 2, height: 2) {
            state "default", label: '${currentValue}% CPU', icon: "https://img.icons8.com/wired/64/000000/processor.png"
        }
        standardTile("FountainMode", "device.FountainMode", width: 2, height: 2, decoration: "flat") {
            state "off", action: "FountainModeOn", label: 'Fountain Mode Off', icon: "https://img.icons8.com/ios/24/000000/fountain.png"
            state "turningOn", label: 'Turning \nOn', icon: "https://img.icons8.com/ios/24/000000/fountain-filled.png", backgroundColor: "#1e9cbb"
            state "turningOff", label: 'Turning \nOff', icon: "https://img.icons8.com/ios/24/000000/fountain.png"
            state "on", action: "FountainModeOn", label: 'Fountain Mode On', icon: "https://img.icons8.com/ios/24/000000/fountain-filled.png", backgroundColor: "#1e9cbb"
        }
        standardTile("WaterFallMode", "device.WaterFallMode", width: 2, height: 2, decoration: "flat") {
            state "off", action: "WaterFallModeOn", label: 'WaterFall Mode \nOff', icon: "https://img.icons8.com/ios/50/000000/waterfall.png"
            state "turningOn", label: 'Turning \nOn', icon: "https://img.icons8.com/ios/50/000000/waterfall-filled.png", backgroundColor: "#1e9cbb"
            state "turningOff", label: 'Turning \nOff', icon: "https://img.icons8.com/ios/50/000000/waterfall.png"
            state "on", action: "WaterFallModeOn", label: 'WaterFall Mode \nOn', icon: "https://img.icons8.com/ios/50/000000/waterfall-filled.png", backgroundColor: "#1e9cbb"
        }
        standardTile("SpaMode", "device.SpaMode", width: 2, height: 2, decoration: "flat") {
            state "off", action: "SpaModeOn", label: 'Spa Mode \nOff', icon: "https://img.icons8.com/ios/50/000000/spa.png"
            state "turningOn", label: 'Turning \nOn', icon: "https://img.icons8.com/ios/50/000000/spa-filled.png", backgroundColor: "#1e9cbb"
            state "turningOff", label: 'Turning \nOff', icon: "https://img.icons8.com/ios/50/000000/spa.png"
            state "on", action: "SpaModeOn", label: 'Spa Mode \nOn', icon: "https://img.icons8.com/ios/50/000000/spa-filled.png", backgroundColor: "#1e9cbb"
        }
        valueTile("diskUsage", "device.diskUsage", width: 2, height: 2) {
            state "default", label: '${currentValue}% Used', unit: "Percent", icon: "https://img.icons8.com/ios/50/000000/sd.png"
        }
        valueTile("memUsage", "device.memUsage", width: 2, height: 2) {
            state "default", label: '${currentValue}% Used', icon: "https://img.icons8.com/ios/50/000000/brain.png"
        }
        standardTile("restart", "device.restart", width: 3, height: 1, inactiveLabel: false, decoration: "flat") {
            state "default", action: "restart", label: "Restart", displayName: "Restart"
        }
        standardTile("refresh", "device.refresh", width: 3, height: 1, inactiveLabel: false, decoration: "flat") {
            state "default", action: "polling.poll", icon: "st.secondary.refresh"
        }
        standardTile("emptyfiller", "null", width: 2, height: 2, decoration: "flat") {
            state "empty", label: '', defaultState: true
        }
        main "PoolPump"
        details(["PoolPump", "FountainMode", "WaterFallMode", "SpaMode", "RPiPower", "cpuTemperature", "cpuPercentage", "diskUsage", "emptyfiller", "memUsage", "restart", "refresh"])
    }
}

// ------------------------------------------------------------------

// parse events into attributes
def parse(String description) {
    def map = [: ]
    def descMap = parseDescriptionAsMap(description)
    def body = new String(descMap["body"].decodeBase64())
    def slurper = new JsonSlurper()
    def result = slurper.parseText(body)
    log.debug "result ${result}"

    if (result) {
        if (state.reboot == "true") {
            state.reboot = "false"
        }
        log.debug "Pool controller is up"
        sendEvent(name: "RPiPower", value: "on")
    } else {
        log.debug "The Raspberry Pi may be offline!"
    }

    log.debug "RPi Power Status: stringValue: ${device.currentState("RPiPower").stringValue}"

	if (result.PoolPump == "On")
    {
        log.debug "PoolPump: ${result.PoolPump}"
        sendEvent(name: "PoolPump", value: "on")
        if (device.currentState("PoolPump").stringValue == "on")
        {
        	state.PoolPump = "on"
        }
    }
    else if (result.PoolPump == "Off")
    {
    	log.debug "PoolPump: ${result.PoolPump}"
        sendEvent(name: "PoolPump", value: "off", isStateChange: true)
        if (device.currentState("PoolPump").stringValue == "off")
        {
        	state.PoolPump = "off"
        }
    }
    
    if (result.cpu_temp)
    {
        log.debug "cpu_temp: ${result.cpu_temp}"
        sendEvent(name: "cpuTemperature", value: result.cpu_temp)
    }

    if (result.cpu_usage)
    {
        log.debug "cpu $result.cpu_usage"
        sendEvent(name: "cpuPercentage", value: result.cpu_usage)
    }

    if (result.mem_usage)
    {
        log.debug "mem_usage: ${result.mem_usage}"
        sendEvent(name: "memUsage", value: result.mem_usage)
    }

    if (result.disk_usage)
    {
        log.debug "disk_usage: ${result.disk_usage}"
        sendEvent(name: "diskUsage", value: result.disk_usage)
    }
    log.debug "mode ${result.Mode}"

    if (result.Mode && state.reboot != "true")
    {
        if (result.Mode == "FountainModeOff")
        {
            sendEvent(name: "FountainMode", value: "on")

            if (device.currentState("FountainMode").stringValue == "on") {
                //Save the valve postion to state object
                state.Mode = "FountainMode"
                sendEvent(name: "WaterFallMode", value: "off")
                sendEvent(name: "SpaMode", value: "off")
            }
        } else if (result.Mode == "WaterFallModeOff")
        {
            sendEvent(name: "WaterFallMode", value: "on")

            if (device.currentState("WaterFallMode").stringValue == "on")
            {
                //Save the valve postion to state object
                state.Mode = "WaterFallMode"
                sendEvent(name: "FountainMode", value: "off")
                sendEvent(name: "SpaMode", value: "off")
            }
        } else if (result.Mode == "SpaModeOff")
        {
            sendEvent(name: "SpaMode", value: "on")

            if (device.currentState("SpaMode").stringValue == "on")
            {
                //Save the valve postion to state object
                state.Mode = "SpaMode"
                sendEvent(name: "FountainMode", value: "off")
                sendEvent(name: "WaterFallMode", value: "off")
            }
        }

        if (result.Mode == "FountainMode")
        {
            sendEvent(name: "FountainMode", value: "turningOn")
            GetIncomingMode(state.Mode)
        } else if (result.Mode == "WaterFallMode")
        {
            sendEvent(name: "WaterFallMode", value: "turningOn")
            GetIncomingMode(state.Mode)
        } else if (result.Mode == "SpaMode")
        {
            sendEvent(name: "SpaMode", value: "turningOn")
            GetIncomingMode(state.Mode)
        }
        log.debug "Pool valves have been set to ${state.Mode}"
    }
        
    	if (state.refresh == "true")
        {
            state.refresh = "false"
    	}
}

def PoolPumpOff()
{
	if (state.reboot != "true")
    {
    	sendEvent(name: "PoolPump", value: "turningOff")
        def uri = "/macros/PoolPumpOff"
        postAction(uri)
    }
}

def PoolPumpOn()
{
	if (state.reboot != "true" && CheckModeState() == "true")
    {
    	sendEvent(name: "PoolPump", value: "turningOn")
        log.debug "fountain on"
        def uri = "/macros/PoolPumpOn"
        postAction(uri)
    }
}

def CheckModeState()
{
	if (device.currentState("SpaMode").stringValue == "on" || device.currentState("FountainMode").stringValue == "on" || device.currentState("WaterFallMode").stringValue == "on")
    {
    	return "true"
    }
    else
    {
    	return "false"
    }
}

//Sends post request to raspberry webiopi to turn on fountain via turnvlaves macro script
def postFountainMode() {
    if (state.reboot != "true")
    {
        log.debug "fountain on"
        def uri = "/macros/FountainModeOn"
        postAction(uri)
    }

}

def FountainModeOn()
{
	if (CheckModeState() == "true")
    {
    	if (state.Mode != "FountainMode")
        {
    		if (device.currentState("PoolPump").stringValue == "on")
        	{
        		PoolPumpOff()
            	def count = 0
            	def pumpStatus = device.currentState("PoolPump").stringValue
            	log.debug "pump status before ${device.currentState("PoolPump").stringValue}"
            	while(pumpStatus != "off" || count > 20)
            {
            	pumpStatus = device.currentState("PoolPump").stringValue
                pause(500)
                count++
            }
        }
        
        log.debug "pump status ${device.currentState("PoolPump").stringValue}"
        log.debug "pool pump ${state.PoolPump}"
        	if (device.currentState("PoolPump").stringValue == "off")
        	{
            	postFountainMode()
            	pause(10000)
            	postFountainModeOff()
            	def FountainOn = device.currentState("FountainMode").stringValue
            	while(FountainOn != "on")
            	{
            		FountainOn = device.currentState("FountainMode").stringValue
                	pause(500)
           		}
            	if (device.currentState("FountainMode").stringValue == "on")
            	{
            		PoolPumpOn()
            	}
        	}
        }
    }
}

def GetIncomingMode(Mode) {
    switch (Mode) {
        case "FountainMode":
            sendEvent(name: "FountainMode", value: "turningOff")
            break
        case "WaterFallMode":
            sendEvent(name: "WaterFallMode", value: "turningOff")
            break
        case "SpaMode":
            sendEvent(name: "SpaMode", value: "turningOff")
            break
    }
}

def postFountainModeOff() {
    if (state.reboot != "true" && device.currentState("RPiPower").stringValue == "on")
    {
        log.debug "fountain off"
        def uri = "/macros/FountainModeOff"
        postAction(uri)
    }
}

def postWaterFallModeOn() {
    if (state.reboot != "true" && device.currentState("RPiPower").stringValue == "on") {
        //sendEvent(name: "WaterFallMode", value: 'turningOn')
        def uri = "/macros/WaterFallModeOn"
        postAction(uri)
    }
}

def WaterFallModeOn()
{
if (CheckModeState() == "true")
{
    if (state.Mode != "WaterFallMode")
    {
    	if (device.currentState("PoolPump").stringValue == "on")
        {
        	PoolPumpOff()
            def count = 0
            def pumpStatus = device.currentState("PoolPump").stringValue
            while(pumpStatus != "off" || count > 20)
            {
            	pumpStatus = device.currentState("PoolPump").stringValue
                pause(500)
                count++
            }
        }
        
        log.debug "pump status ${device.currentState("PoolPump").stringValue}"
        if (device.currentState("PoolPump").stringValue == "off")
        {
            postWaterFallModeOn()
            pause(10000)
            postWaterFallModeOff()
            def WaterFallOn = device.currentState("WaterFallMode").stringValue
            while(WaterFallOn != "on")
            {
            	WaterFallOn = device.currentState("WaterFallMode").stringValue
                pause(500)
            }
            if (device.currentState("WaterFallMode").stringValue == "on")
            {
            	PoolPumpOn()
            }
        }
        }
    }
}

def postWaterFallModeOff()
{
    if (state.reboot != "true" && device.currentState("RPiPower").stringValue == "on") {
        def uri = "/macros/WaterFallModeOff"
        postAction(uri)
    }
}

def postSpaModeOn()
{
    if (state.reboot != "true" && device.currentState("RPiPower").stringValue == "on") {
        def uri = "/macros/SpaModeOn"
        postAction(uri)
    }
}

def postSpaModeOff()
{
    if (state.reboot != "true" && device.currentState("RPiPower").stringValue == "on") {
        def uri = "/macros/SpaModeOff"
        postAction(uri)
    }
}

def SpaModeOn()
{
if (CheckModeState() == "true")
    {
    	if (device.currentState("PoolPump").stringValue == "on")
        {
        	PoolPumpOff()
            def count = 0
            def pumpStatus = device.currentState("PoolPump").stringValue
            while(pumpStatus != "off" || count > 20)
            {
            	pumpStatus = device.currentState("PoolPump").stringValue
                pause(500)
                count++
            }
        }
        
        log.debug "pump status ${device.currentState("PoolPump").stringValue}"
        if (device.currentState("PoolPump").stringValue == "off")
        {
            postSpaModeOn()
            pause(10000)
            postSpaModeOff()
            def SpaOn = device.currentState("SpaMode").stringValue
            while(SpaOn != "on")
            {
            	SpaOn = device.currentState("SpaMode").stringValue
                pause(500)
            }
            if (device.currentState("SpaMode").stringValue == "on")
            {
            	PoolPumpOn()
            }
        }
    }
}

def poll() {
    log.debug "Executing 'poll'"
    log.debug "reboot ${state.reboot}"
    state.refresh = "true"
    if (state.reboot != "true")
    {
        sendEvent(name: "RPiPower", value: "off")
        sendEvent(name: "PoolPump", value: "off")
        sendEvent(name: "PoolPump", value: "${state.PoolPump}")
    }
    getRPiData()
    GetValveMode(state.Mode)
}

def refresh() {
    log.debug "Executing 'refresh'"
    poll()
}

def restart() {
    log.debug "Restart was pressed"
    if (device.currentState("RPiPower").stringValue != "off") {
        state.reboot = "true"
        sendEvent(name: "RPiPower", value: "rebooting")
        def uri = "/macros/rebootRPi"
        postAction(uri)
    }
}

// Get Raspberry Pi stats
private getRPiData() {
    def uri = "/macros/getRPiData"
    postAction(uri)
}

//Gets the postion of the valve from state.ReturnValvePos
def GetValveMode(ValveState)
{
        if (ValveState == "FountainMode") {
            sendEvent(name: "WaterFallMode", value: "off")
            sendEvent(name: "SpaMode", value: "off")
            sendEvent(name: "FountainMode", value: "on")
        } else if (ValveState == "SpaMode") {
            sendEvent(name: "FountainMode", value: "off")
            sendEvent(name: "WaterFallMode", value: "off")
            sendEvent(name: "SpaMode", value: "on")
        } else if (ValveState == "WaterFallMode") {
            sendEvent(name: "FountainMode", value: "off")
            sendEvent(name: "SpaMode", value: "off")
            sendEvent(name: "WaterFallMode", value: "on")
        }
}

def pause(millis) {
   def passed = 0
   def now = new Date().time
   log.debug "pausing... at Now: $now"
   /* This loop is an impolite busywait. We need to be given a true sleep() method, please. */
   while ( passed < millis ) {
       passed = new Date().time - now
   }
}

private postAction(uri) {
    setDeviceNetworkId(ip, port)

        sendHubCommand(new physicalgraph.device.HubAction(
        method: "POST",
        path: uri
    ))
}

def parseDescriptionAsMap(description) {
    description.split(",").inject([: ]) {map,param ->
        def nameAndValue = param.split(":")
        map += [(nameAndValue[0].trim()): nameAndValue[1].trim()]
    }
}

private delayAction(long time) {
    new physicalgraph.device.HubAction("delay $time")
}

private setDeviceNetworkId(ip, port) {
    def iphex = convertIPtoHex(ip)
    def porthex = convertPortToHex(port)
    device.deviceNetworkId = "$iphex:$porthex"
    //log.debug "Device Network Id set to ${iphex}:${porthex}"
}

private getHostAddress() {
    return "${ip}:${port}"
}

private String convertIPtoHex(ipAddress) {
    String hex = ipAddress.tokenize('.').collect {
        String.format('%02x', it.toInteger())
    }.join()
    return hex
}

private String convertPortToHex(port) {
    String hexport = port.toString().format('%04x', port.toInteger())
    return hexport
}