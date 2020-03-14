/**
 *  Lutron Caseta Wireless Wall Dimmer device type for SmartWink.
 *
 *  This file is automatically generated from a template. Do not modify directly.
 *  Please refer to http://www.github.com/quantiletree/SmartWink for the source code.
 *
 *  Wink and the Wink Hub are trademarks of Wink, Inc. Lutron, Pico, Caseta, and Serena are trademarks of
 *  Lutron Electronics Co., Inc. SmartWink is an independent third-party application designed to bridge these systems,
 *  and is not affiliated with or sponsored by Wink, Lutron, or SmartThings.
 *
 *  Copyright 2015 Michael Barnathan (michael@barnathan.name)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

// 
metadata {
    definition(
        // 
        name: "WALL_DIMMER",
        namespace: "smartwink",
        author: "Michael Barnathan",
        description: "Lutron Caseta Wireless Wall Dimmer",
        category: "SmartThings Labs",
        iconUrl: "http://cdn.device-icons.smartthings.com/Home/home30-icn.png",
        iconX2Url: "http://cdn.device-icons.smartthings.com/Home/home30-icn@2x.png"
    ) {
        attribute "hubMac", "string"

        capability "Actuator"       // no commands
        capability "Refresh"        // refresh()
        capability "Sensor"         // no commands
        capability "Switch"   	    // on(), off()
        capability "Switch Level"   // setLevel()

        command "subscribe"
        command "unsubscribe"
    }

    simulator {}

    tiles(scale: 2) {
        multiAttributeTile(name:"switch", type: "lighting", width: 6, height: 4, canChangeIcon: true) {
            tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
                attributeState "on", label:'${name}', action:"switch.off", icon:"st.Home.home30", backgroundColor:"#79b821", nextState:"turningOff"
                attributeState "off", label:'${name}', action:"switch.on", icon:"st.Home.home30", backgroundColor:"#ffffff", nextState:"turningOn"
                attributeState "turningOn", label:'${name}', action:"switch.off", icon:"st.Home.home30", backgroundColor:"#79b821", nextState:"turningOff"
                attributeState "turningOff", label:'${name}', action:"switch.on", icon:"st.Home.home30", backgroundColor:"#ffffff", nextState:"turningOn"
            }
            tileAttribute ("device.level", key: "SLIDER_CONTROL") {
                attributeState "level", action: "switch level.setLevel"
            }
        }

        standardTile("refresh", "device.refresh", decoration: "flat", width: 2, height: 2) {
            state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
        }

        valueTile("level", "device.level", decoration: "flat", width: 2, height: 2) {
            state "level", label:'${currentValue} %', unit:"%"
        }

        valueTile("hubMac", "device.hubMac", decoration: "flat", width: 2, height: 2) {
            state "default", label:'Wink Hub: ${currentValue}', width: 2, height: 2
        }

        main "switch"
        details(["switch", "refresh", "level", "hubMac"])
    }
}

// Fires when the refresh button is pressed.
def refresh() {
    log.info "Received refresh request for ${device.deviceNetworkId}"
    unsubscribe()
    subscribe()
}

def on() {
    setLevel(100)
}

def off() {
    setLevel(0)
}

def setLevel(level) {
    def oldLevel = device.currentValue("level") ?: 0

    log.info("Wall dimmer ${device.deviceNetworkId} level set to: ${level} from ${oldLevel}")
    if (level == oldLevel || level > 100 || level < 0) return null

    if (oldLevel != 0 && level == 0) {
        sendEvent(name: "switch", value: "turningOff")
    } else if (oldLevel == 0 && level != 0) {
        sendEvent(name: "switch", value: "turningOn")
    }

    def netAddr = hubIp()
    def dni = device.deviceNetworkId

    int scaledLevel = level * 255 / 100
    log.debug "Sending scaled level ${scaledLevel} / 255 to device."

    return new physicalgraph.device.HubAction([
            method: "POST",
            path: "/set.php",
            headers: [
                    HOST: "${netAddr}:80",
                    "Content-Length": 0
            ],
            query: [device: dni, attr: "Level", value: "${scaledLevel}"]
    ], "${dni}")
}

def handleEvent(parsedEvent, hub, json) {
    log.info "Wall dimmer ${device.deviceNetworkId} handling event: ${json}"
    if (json.containsKey("value")) {
        def level = json.value as Integer
        def scaledLevel = (level * 100 / 255) as Integer
        if (scaledLevel == device.currentValue("level")) {
            // When updated from the app, the response and an independent update both come back.
            // I like keeping this behavior for redundancy, but we don't need to see updates with identical level.
            return null
        }
        log.info "Received level ${level} / 255. Setting device level to ${scaledLevel} / 100."
        sendEvent(name: "level", value: scaledLevel)
        sendEvent(name: "switch", value: (scaledLevel == 0 ? "off" : "on"))
    } else {
        log.warn "Wall dimmer level event ${json} has no value attribute."
    }
    return null
}

def parse(description) {
    log.debug "Parsing '${description}'"

    def keyVal = description.split(":", 2)
    if (keyVal[0] in ["level", "switch"]) {
        return createEvent(name: keyVal[0], value: keyVal[1])
    } else if (keyVal[0] == "updated") {
        log.trace "Wall dimmer was updated"
        return null
    } else {
        log.warn "Unknown event in Wall dimmer parse(): ${description}"
        return null
    }
}

// // This device can request and handle subscriptions.

private getCallBackAddress() {
    device.hub.getDataValue("localIP") + ":" + device.hub.getDataValue("localSrvPortTCP")
}

private getUpnpHost() {
    def upnp_port = 1081
    def hubAddr = hubIp()

    def addressParts = hubAddr.split(":")
    def host = addressParts[0]
    return "${host}:${upnp_port}"
}

private getUpnpPath() {
    return "/upnp/event/${device.deviceNetworkId}/all"
}

def subscribe() {
    subscribe(getUpnpHost(), getUpnpPath())
}

def unsubscribe() {
    log.info "Received unsubscribe request for ${device.deviceNetworkId}"
    unsubscribe(getUpnpHost(), getUpnpPath())
}

def subscribe(host, path) {
    def address = getCallBackAddress()
    def callbackPath = "http://${address}/notify$path"
    log.info "Received subscribe for ${device.deviceNetworkId} ($host, $path, $callbackPath)"

    new physicalgraph.device.HubAction(
            method: "SUBSCRIBE",
            path: path,
            headers: [
                    HOST: host,
                    CALLBACK: "<${callbackPath}>",
                    NT: "upnp:event",
                    TIMEOUT: "Second-9999999999999"
            ]
    )
}

def unsubscribe(host, path) {
    def sid = getDeviceDataByName("subscriptionId")
    if (!sid) {
        return null
    }
    log.trace "unsubscribe($host, $path, $sid)"
    new physicalgraph.device.HubAction(
            method: "UNSUBSCRIBE",
            path: path,
            headers: [
                    HOST: host,
                    SID: "uuid:${sid}",
            ]
    )
}

def hubIp() {
    return parent.state.foundHubs[device.currentValue("hubMac").replaceAll(":", "")]
}
