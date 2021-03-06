/*
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy
 *  of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations
 *  under the License.
 */

preferences {
        input "email", "text", title: "Whistle E-Mail", description: "E-Mail", required: true
        input "password", "password", title: "Whistle Password", description: "Password", required: true
        input "petID", "number", title: "Whistle Pet ID", description: "Whistle Pet ID #", required: true
        input "homeID", "number", title: "Whistle Home ID", description: "Whistle Home ID #", required: true
		}

metadata {
	definition (name: "Whistle Presence", namespace: "swamplynx", author: "SwampLynx", mnmn:"SmartThings", vid:"generic-arrival") {
		capability "Presence Sensor"
		capability "Occupancy Sensor"
		capability "Sensor"
        capability "Battery"
        capability "Refresh"
        capability "Polling"
        capability "Health Check"
	}

	simulator {
		status "present": "presence: 1"
		status "not present": "presence: 0"
		status "occupied": "occupancy: 1"
		status "unoccupied": "occupancy: 0"
	}

	tiles {
		standardTile("presence", "device.presence", width: 2, height: 2, canChangeBackground: true) {
			state("present", labelIcon:"st.presence.tile.mobile-present", backgroundColor:"#00A0DC")
			state("not present", labelIcon:"st.presence.tile.mobile-not-present", backgroundColor:"#ffffff")
		}
        valueTile("battery", "device.battery", inactiveLabel: false, decoration: "flat", width: 1, height: 1) {
			state("battery", label:'${currentValue}% battery', unit:"")
		}
        standardTile("refresh", "device.weather", decoration: "flat", width: 1, height: 1) {
            state "default", label: "", action: "refresh", icon:"st.secondary.refresh"
        }
		main (["presence", "battery", "refresh"])
		details (["presence", "battery", "refresh"])
	}
}

def parse(String description) {
	def name = parseName(description)
	def value = parseValue(description)
	def linkText = getLinkText(device)
	def descriptionText = parseDescriptionText(linkText, value, description)
	def handlerName = getState(value)
	def isStateChange = isStateChange(device, name, value)

	def results = [
    	translatable: true,
		name: name,
		value: value,
		unit: null,
		linkText: linkText,
		descriptionText: descriptionText,
		handlerName: handlerName,
		isStateChange: isStateChange,
		displayed: displayed(description, isStateChange)
	]
	log.debug "Parse returned $results.descriptionText"
	return results
}

private String parseName(String description) {
	if (description?.startsWith("presence: ")) {
		return "presence"
	} else if (description?.startsWith("occupancy: ")) {
		return "occupancy"
	}
	null
}

private String parseValue(String description) {
	switch(description) {
		case "presence: 1": return "present"
		case "presence: 0": return "not present"
		case "occupancy: 1": return "occupied"
		case "occupancy: 0": return "unoccupied"
		default: return description
	}
}

private parseDescriptionText(String linkText, String value, String description) {
	switch(value) {
		case "present": return "{{ linkText }} has arrived"
		case "not present": return "{{ linkText }} has left"
		case "occupied": return "{{ linkText }} is inside"
		case "unoccupied": return "{{ linkText }} is away"
		default: return value
	}
}

private getState(String value) {
	switch(value) {
		case "present": return "arrived"
		case "not present": return "left"
		case "occupied": return "inside"
		case "unoccupied": return "away"
		default: return value
	}
}

def refresh() { 
	 log.info("Whistle Presence Refresh Requested")
     runEvery1Minute (poll)
     log.debug "Data will repoll every minute" 
    callAPI()
}
def poll() { 
	log.info("Whistle Presence Poll or Scheduled Poll Requested")
   callAPI()
}

def getAPIkey() {
	return "Bearer ${state.token}"
}

private def callAPI() {
    if (petID){
        def accessToken = getAPIkey()
        def params = [
            uri: "https://app.whistle.com",
            path: "/api/pets/${petID}",
            contentType: "application/json",
            headers: [
            	"Authorization": "${accessToken}",
                "Accept": "application/vnd.whistle.com.v4+json",
                "Content-Type": "application/json",
                "Connection": "keep-alive",
                "Accept-Language": "en-us",
                "Accept-Encoding": "br, gzip, deflate",
                "User-Agent": "Winston/2.5.3 (iPhone; iOS 12.0.1; Build:1276; Scale/2.0)" ],
                      ]
      try {
      	log.debug "Starting HTTP GET request to Whistle Data API"
    	httpGet(params) { resp ->                
        	if(resp.status == 200) {
	        	log.debug "Request to Whistle Data API was OK, parsing data"
  
                def batt = resp.data.pet.device.battery_level
                log.info "Whistle battery status is ${batt}%"
                sendEvent(name:"battery", value: batt, unit: "%")
                
                def locationIDnum = resp.data.pet.last_location.place.id.toInteger()
                def locationStatus = resp.data.pet.last_location.place.status.toString()
                def homeIDnum = "${homeID}".toInteger()
                
                log.debug "Current Home ID is ${homeIDnum}"
                log.debug "Current Pet Location ID is ${locationIDnum}"
                log.debug "Current Pet Location Status is ${locationStatus}"
               
                if (locationIDnum.equals(homeIDnum) && locationStatus.equals("in_beacon_range")) {
                                sendEvent(name: "presence", value: "present")
                                log.info "Pet is on Home WiFi Beacon, Updating Presence to Present"
                            } 
                else if (locationIDnum.equals(homeIDnum) && locationStatus.equals("in_geofence_range")) {
                                sendEvent(name: "presence", value: "present")
                                log.info "Pet inside Home Geofence, Updating Presence to Present"
                            } 
                            else {
                                sendEvent(name: "presence", value: "not present")
                                log.info "Pet is NOT Home, Updating Presence to Not Present"
                            }
            
    		}
            else {
        		log.error "Data Request got HTTP status ${resp.status}"
        	}
      
        }
    } catch(e) {
    	if (e.message.equals("Unauthorized")) {
        log.debug "User unauthorized, requesting new token"
        callAPIauth()
        }
        else {
        log.error "Something went wrong with the data API call $e"
        }
    }
}
       else {
       log.debug "Pet ID is missing from the settings"
      }
   }
   
private def callAPIauth() {
				def authparams = [
            			uri: "https://app.whistle.com",
            			path: "/api/login",
            				contentType: "application/json",
            				headers: [
                				"Accept": "application/vnd.whistle.com.v4+json",
                				"Content-Type": "multipart/form-data",
                				"Connection": "keep-alive",
                				"Accept-Language": "en-us",
                				"Accept-Encoding": "br, gzip, deflate",
                				"User-Agent": "Winston/2.5.3 (iPhone; iOS 12.0.1; Build:1276; Scale/2.0)" ],
          				 body: [
           						"email": "${email}",
                				"password": "${password}" ],
                						]		
      		try {
      			log.debug "Starting HTTP POST Login request to Whistle Login API"
    			httpPost(authparams) { resp ->

 					if(resp.status == 201) {
	        			log.debug "Request to Whistle Login API was OK, storing token and calling Data API"
                        state.token = resp.data.auth_token
                        callAPI()
                        					}
                   else {
        		log.error "Login Request got HTTP status ${resp.status}"
        	}
               						 }				
     } catch(e) {
    	log.error "Something went wrong with the login token API call $e"
    }
}