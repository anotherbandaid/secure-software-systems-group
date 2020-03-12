/**
 *  RM Bridge TV Remote
 *
 *  Copyright 20168 Enis Hoca
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
 * 	Author: Enis Hoca - enishoca@outlook.com
 *  For Details -  see https://community.smartthings.com/t/release-generic-mini-split-ac-remote-device-handler-using-broadlink-rm-pro-and-mini/127288
 *                 and https://community.smartthings.com/t/generic-tv-remote-device-handler-using-broadlink-rm-pro-and-mini/117384  
 */
 
preferences {    
	section("Internal Access"){
        input "internal_ip", "text", title: "IP for RM Bridge", description: "(ie. 192.168.0.78)", required: true
        input "internal_port", "text", title: "Port", description: "(ie. 7474)" , required: true
        input "POWEROFF", "text", title: "AC Off", description: "(RM Bridge code name)" , required: true
 
  
        input "cool_64", "text", title: "Cool 64", description: "(RM Bridge code name)" , required: false
        input "cool_66", "text", title: "Cool 66", description: "(RM Bridge code name)" , required: false
        input "cool_68", "text", title: "Cool 68", description: "(RM Bridge code name)" , required: false
        input "cool_70", "text", title: "Cool 70", description: "(RM Bridge code name)" , required: false
        input "cool_72", "text", title: "Cool 72", description: "(RM Bridge code name)" , required: false
        input "cool_74", "text", title: "Cool 74", description: "(RM Bridge code name)" , required: false
        input "cool_76", "text", title: "Cool 76", description: "(RM Bridge code name)" , required: false
        input "cool_78", "text", title: "Cool 78", description: "(RM Bridge code name)" , required: false
        
        input "hot_64", "text", title: "Hot 64", description: "(RM Bridge code name)" , required: false
        input "hot_66", "text", title: "Hot 66", description: "(RM Bridge code name)" , required: false
        input "hot_68", "text", title: "Hot 68", description: "(RM Bridge code name)" , required: false
        input "hot_70", "text", title: "Hot 70", description: "(RM Bridge code name)" , required: false
        input "hot_72", "text", title: "Hot 72", description: "(RM Bridge code name)" , required: false
        input "hot_74", "text", title: "Hot 74", description: "(RM Bridge code name)" , required: false
        input "hot_76", "text", title: "Hot 76", description: "(RM Bridge code name)" , required: false
        input "hot_78", "text", title: "Hot 78", description: "(RM Bridge code name)" , required: false
        
        input "humid_66", "text", title: "Dehumidify 66", description: "(RM Bridge code name)" , required: false
        input "humid_68", "text", title: "Dehumidify 68", description: "(RM Bridge code name)" , required: false
        input "humid_70", "text", title: "Dehumidify 70", description: "(RM Bridge code name)" , required: false
        input "humid_72", "text", title: "Dehumidify 72", description: "(RM Bridge code name)" , required: false
        input "humid_74", "text", title: "Dehumidify 74", description: "(RM Bridge code name)" , required: false
        input "humid_76", "text", title: "Dehumidify 76", description: "(RM Bridge code name)" , required: false
        input "humid_78", "text", title: "Dehumidify 78", description: "(RM Bridge code name)" , required: false
	}
}
 
metadata {
	definition (name: "RM Bridge AC Remote", namespace: "enishoca", author: "Enis Hoca") {
        capability "switch" 
 
 
        command "cool_64" 
        command "cool_66"
        command "cool_68"    
        command "cool_70"          
        command "cool_72"    
        command "cool_74"
        command "cool_76"
        command "cool_78"
        
        command "hot_64" 
        command "hot_66"
        command "hot_68"    
        command "hot_70"          
        command "hot_72"    
        command "hot_74"
        command "hot_76"
        command "hot_78"
        
        command "humid_66"
        command "humid_68"    
        command "humid_70"          
        command "humid_72"    
        command "humid_74"
        command "humid_76"
        command "humid_78"
 
	}

    standardTile("switch", "device.switch", canChangeIcon: true) {
        state "default", label:'OFF', action:"all", icon:"st.samsung.da.RAC_ic_aircon", backgroundColor:"#79b821"
    }

    standardTile("power", "device.switch", decoration: "flat", canChangeIcon: false) {
        state "default", label:'OFF', action:"switch.off", icon:"st.samsung.da.RC_ic_power", backgroundColor:"#888888"
    }
    valueTile("cool_64", "device.switch") {
        state "val", label:'64', action:"cool_64", backgroundColor: "#66d9ff", defaultState: true
    }
    valueTile("cool_66", "device.switch") {
        state "val", label:'66', action:"cool_66", backgroundColor: "#66d9ff", defaultState: true
    }
    valueTile("cool_68", "device.switch") {
        state "val", label:'68', action:"cool_68", backgroundColor: "#66d9ff", defaultState: true
    }
    valueTile("cool_70", "device.switch") {
        state "val", label:'70', action:"cool_70", backgroundColor: "#66d9ff", defaultState: true
    }
    valueTile("cool_72", "device.switch") {
        state "val", label:'72', action:"cool_72", backgroundColor: "#66d9ff", defaultState: true
    }
    valueTile("cool_74", "device.switch") {
        state "val", label:'74', action:"cool_74", backgroundColor: "#66d9ff", defaultState: true
    }
    valueTile("cool_76", "device.switch") {
        state "val", label:'76', action:"cool_76", backgroundColor: "#66d9ff", defaultState: true
    }
    valueTile("cool_78", "device.switch") {
        state "val", label:'78', action:"cool_78", backgroundColor: "#66d9ff", defaultState: true
    }
    
 
    valueTile("humid_66", "device.switch") {
        state "val", label:'66', action:"humid_66", backgroundColor: "#888888", defaultState: true
    }
    valueTile("humid_68", "device.switch") {
        state "val", label:'68', action:"humid_68", backgroundColor: "#888888", defaultState: true
    }
    valueTile("humid_70", "device.switch") {
        state "val", label:'70', action:"humid_70", backgroundColor: "#888888", defaultState: true
    }
    valueTile("humid_72", "device.switch") {
        state "val", label:'72', action:"humid_72", backgroundColor: "#888888", defaultState: true
    }
    valueTile("humid_74", "device.switch") {
        state "val", label:'74', action:"humid_74", backgroundColor: "#888888", defaultState: true
    }
    valueTile("humid_76", "device.switch") {
        state "val", label:'76', action:"humid_76", backgroundColor: "#888888", defaultState: true
    }
    valueTile("humid_78", "device.switch") {
        state "val", label:'78', action:"humid_78", backgroundColor: "#888888", defaultState: true
    }
    
    valueTile("hot_64", "device.switch") {
        state "val", label:'64', action:"hot_64", backgroundColor: "#ff531a", defaultState: true
    }
    valueTile("hot_66", "device.switch") {
        state "val", label:'66', action:"hot_66", backgroundColor: "#ff531a", defaultState: true
    }
    valueTile("hot_68", "device.switch") {
        state "val", label:'68', action:"hot_68", backgroundColor: "#ff531a", defaultState: true
    }
    valueTile("hot_70", "device.switch") {
        state "val", label:'70', action:"hot_70", backgroundColor: "#ff531a", defaultState: true
    }
    valueTile("hot_72", "device.switch") {
        state "val", label:'72', action:"hot_72", backgroundColor: "#ff531a", defaultState: true
    }
    valueTile("hot_74", "device.switch") {
        state "val", label:'74', action:"hot_74", backgroundColor: "#ff531a", defaultState: true
    }
    valueTile("hot_76", "device.switch") {
        state "val", label:'76', action:"hot_76", backgroundColor: "#ff531a", defaultState: true
    }
    valueTile("hot_78", "device.switch") {
        state "val", label:'78', action:"hot_78", backgroundColor: "#ff531a", defaultState: true
    }
    
    main "switch"
    details (["power","cool_64","hot_64", "humid_66","cool_66","hot_66","humid_68","cool_68","hot_68","humid_70","cool_70","hot_70",
               "humid_72","cool_72","hot_72","humid_74","cool_74","hot_74","humid_76","cool_76","hot_76","humid_78","cool_78","hot_78"
    ])
}
 
def acAction(buttonPath) {
    log.debug settings
	if (settings."$buttonPath"){
        def path = "/code/" + settings."$buttonPath"
        //log.debug path   
		def result = new physicalgraph.device.HubAction(
				method: "GET",		/* If you want to use the RM Bridge, change the method from "POST" to "Get" */
				path: path,
				headers: [HOST: "${internal_ip}:${internal_port}"],
			)
        log.debug result
        sendHubCommand(result)
        log.debug buttonPath
	} else {
    log.debug "Undefined"
  }
}


def updated() {
 
    if (settings.internal_ip != null && settings.internal_port != null ) {
       device.deviceNetworkId = "${settings.internal_ip}:${settings.port}"
    }
}
def parse(String description) {
	return null
}

def off() {
	log.debug "Turning TV OFF"
    acAction "POWEROFF" 
    sendEvent(name:"Command", value: "Power Off", displayed: true) 
    sendEvent(name : "switch", value : "off");
}
 
def on() {
	log.debug "Turning TV OFF"
    acAction("POWEROFF") 
    sendEvent(name:"Command", value: "Power On", displayed: true) 
    sendEvent(name : "switch", value : "on");
}


def cool_64() {
    log.debug "cool_64 pressed"
    acAction("cool_64")   
} 
def cool_66() {
    log.debug "cool_66 pressed"
    acAction("cool_66")   
} 
def cool_68() {
    log.debug "cool_68 pressed"
    acAction("cool_68")   
} 
def cool_70() {
    log.debug "cool_70 pressed"
    acAction("cool_70")   
} 
def cool_72() {
    log.debug "cool_72 pressed"
    acAction("cool_72")   
} 
def cool_74() {
    log.debug "cool_74 pressed"
    acAction("cool_74")   
} 
def cool_76() {
    log.debug "cool_76 pressed"
    acAction("cool_76")   
} 
def cool_78() {
    log.debug "cool_78 pressed"
    acAction("cool_78")   
} 


def hot_64() {
  log.debug "hot_64 pressed"
  acAction("hot_64")   
} 
def hot_66() {
  log.debug "hot_66 pressed"
  acAction("hot_66")   
} 
def hot_68() {
  log.debug "hot_68 pressed"
  acAction("hot_68")   
} 
def hot_70() {
  log.debug "hot_70 pressed"
  acAction("hot_70")   
} 
def hot_72() {
  log.debug "hot_72 pressed"
  acAction("hot_72")   
} 
def hot_74() {
  log.debug "hot_74 pressed"
  acAction("hot_74")   
} 
def hot_76() {
  log.debug "hot_76 pressed"
  acAction("hot_76")   
} 
def hot_78() {
  log.debug "hot_78 pressed"
  acAction("hot_78")   
} 

def humid_66() {
  log.debug "humid_66 pressed"
  acAction("humid_66")   
} 
def humid_68() {
  log.debug "humid_68 pressed"
  acAction("humid_68")   
} 
def humid_70() {
  log.debug "humid_70 pressed"
  acAction("humid_70")   
} 
def humid_72() {
  log.debug "humid_72 pressed"
  acAction("humid_72")   
} 
def humid_74() {
  log.debug "humid_74 pressed"
  acAction("humid_74")   
} 
def humid_76() {
  log.debug "humid_76 pressed"
  acAction("humid_76")   
} 
def humid_78() {
  log.debug "humid_78 pressed"
  acAction("humid_78")   
} 
