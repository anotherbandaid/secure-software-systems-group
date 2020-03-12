/**
 *  GoodWe Monitoring Portal
 *
 *  Copyright 2018 Ron van de Graaf
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
  definition (name: "GoodWe Monitoring Portal", namespace: "ronvandegraaf", author: "ronvandegraaf") {
    capability "Power Meter"
    capability "Energy Meter"
    capability "Refresh"
    capability "Polling"

    attribute "lastupdate", "string"
  }

  preferences {
    section ("Settings") {
      input name:"Username", type:"text", title:"Your Username", required: true
    }
  }

  tiles(scale: 2) {
    multiAttributeTile(name:"curpower", type:"generic", width:6, height:4) {
      tileAttribute("device.curpower", key: "PRIMARY_CONTROL") {
        attributeState "Normal", label:'${currentValue}', icon:"st.Weather.weather14", backgroundColor:"#00a0dc"
      }
    }

    valueTile("eday", "device.eday", width: 2, height: 1, decoration: "flat") {
      state "default", label:"Today:\n" + '${currentValue}'
    }

    valueTile("etotal", "device.etotal", width: 2, height: 1, decoration: "flat") {
      state "default", label:"Total:\n" + '${currentValue}'
    }

    valueTile("totalincome", "device.totalincome", width: 2, height: 1, decoration: "flat", inactiveLabel: false) {
      state "default", label:"Total income:\n" + '${currentValue}'
    }

    valueTile("lastupdate", "lastupdate", width: 5, height: 1, inactiveLabel: false) {
      state "default", label:"Last updated: " + '${currentValue}'
    }

    standardTile("refresh", "device.refresh", width: 1, height: 1, inactiveLabel: false, decoration: "flat") {
      state "default", action:"refresh.refresh", icon:"st.secondary.refresh"
    }

    main (["curpower"])
    details(["curpower", "eday", "etotal", "totalincome", "lastupdate", "refresh"])
  }
}

def installed() {
  log.debug "Installed with settings: ${settings}"
}

def uninstalled() {
  log.debug "uninstalled()"
}

def updated() {
  log.debug "Updated with settings: ${settings}"

  initialize()
}

def initialize() {
  poll()
  runEvery15Minutes("poll")
}

def refresh() {
  poll()
}

def poll() {
  if (Username == null) {
    log.debug "Username missing in preferences"

    return
  }

  httpGet(uri: "https://eu.goodwe-power.com/Mobile/GetMyPowerStationByUser?userName=${Username}", contentType: 'application/json') { resp ->
      log.debug "Start httpGet"

      if (resp.data) {
      	log.debug "resp data: ${resp.data[0]}"
        log.debug "resp data: ${resp.data[0].currentPower}"
        log.debug "resp data: ${resp.data[0].value_eTotal}"
        log.debug "resp data: ${resp.data[0].value_eDayTotal}"
        log.debug "resp data: ${resp.data[0].value_totalIncome}"

        def curpower = resp.data[0].currentPower
        def etotal = resp.data[0].value_eTotal
        def eday = resp.data[0].value_eDayTotal
        def totalincome = resp.data[0].value_totalIncome
        def status = resp.data.status

        sendEvent([name: "curpower", value: curpower])
        sendEvent([name: "etotal", value: etotal])
        sendEvent([name: "eday", value: eday])
        sendEvent([name: "totalincome", value: totalincome])
        
        sendEvent(name: 'lastupdate', value: lastUpdated(now()), unit: "")
      }

      if(resp.status == 200) {
        log.debug "Request was OK"
      }

      else {
        log.error "Request got http status ${resp.status}"
      }
    }
}

def lastUpdated(time) {
  def timeNow = now()
  def lastUpdate = ""

  if(location.timeZone == null) {
    log.debug "Cannot set update time : location not defined in app"
  }

  else {
    lastUpdate = new Date(timeNow).format("MMM dd yyyy HH:mm", location.timeZone)
  }

  return lastUpdate
}
