/**
 *  Enlighten Solar System
 *
 *  Copyright 2016 Alan Anderson based on originals by Ronald Gouldner and Umesh Sirsiwal
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
 
 /* 
 * For monthly KW estimates go to the Settings page on the Enphase Web site.  Find the monthly estimated array 
 * production capacity on the Enphase "Array Details"  Divide that number by the numbers of days in the month.
 * This will provide a daily estimate in Kilo Watt hours.
 */
 
 
preferences {
    input("user_id", "text", title: "Enphase Dev Account User ID")
    input("system_id", "text", title: "Enphase System ID")
    input("key", "text", title: "Enphase Dev Account Key")
    input("jan", "number", title: "January Estimate in kwh")
    input("feb", "number", title: "February Estimate in kwh")
    input("mar", "number", title: "March Estimate in kwh")
    input("apr", "number", title: "April Estimate in kwh")
    input("may", "number", title: "May Estimate in kwh")
    input("jun", "number", title: "June Estimate in kwh")
    input("jul", "number", title: "July Estimate in kwh")
    input("aug", "number", title: "August Estimate in kwh")
    input("sep", "number", title: "September Estimate in kwh")
    input("oct", "number", title: "October Estimate in kwh")
    input("nov", "number", title: "November Estimate in kwh")
    input("dec", "number", title: "December Estimate in kwh")
    
}
metadata {
	definition (name: "Enlighten Solar System", namespace: "andersonas25", author: "Alan Anderson") {
	capability "Power Meter" 
    capability "Refresh"
	capability "Polling"
        
    attribute "energy_today", "STRING"
    attribute "energy_life", "STRING"
	attribute "production_level", "STRING"
	attribute "today_max_prod", "NUMBER"
	attribute "today_max_prod_str", "STRING"
	attribute "today_max_day", "STRING"
	attribute "reported_id", "STRING"
    attribute "month", "NUMBER"
    attribute "enphase_daily_array_estimate", "NUMBER"
    
        
    fingerprint deviceId: "RRGEnlightenPV"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
            valueTile("reported_id", "device.reported_id") {
				state ("reported_id", label: '${currentValue}\nEnphase', unit:"", backgroundColor: "#0000ff"
					   //icon:"https://github.com/gouldner/smartthings-enlighten/raw/master/PoweredByLogo.jpg"
                       )
            }
            valueTile("energy_today", "device.energy_today") {
   	         state("energy_today", label: '${currentValue}K\nToday', unit:"KWh", backgroundColors: [
                    [value: 2, color: "#bc2323"],
                    [value: 5, color: "#d04e00"],
                    [value: 10, color: "#f1d801"],
                    [value: 20, color: "#90d2a7"],
		            [value: 30, color: "#44b621"],
                    [value: 40, color: "#1e9cbb"],
                    [value: 50, color: "#153591"]
    	            ]
            	)
        	}
            valueTile("power", "device.power") {
   	         state("Power", label: '${currentValue}W\nPower', unit:"W", backgroundColor: "#000000")
        	}
			valueTile("productionLevel", "device.production_level") {
				state("productionLevel", label: '${currentValue}%\nProd', unit:"%", backgroundColor: "#0000FF")
			}
			valueTile("todayMaxProd", "device.today_max_prod_str") {
				state("todayMaxProd", label: '${currentValue}%\nMax', unit:"%", backgroundColor: "#0000FF")
			}

            valueTile("systemSize", "device.systemSize") {
				state("systemSize", label: '${currentValue}W\nSize', unit:"W", backgroundColor: "#0000FF")
			}
            
          valueTile("Estimated", "device.Enphase_Estimated_str") {
				state("Enphase_Estimated", label: '${currentValue}KW\n', unit:"KW", backgroundColor: "#0000FF")
			}
                    
            valueTile("energy_life", "device.energy_life", width: 1, height: 1, canChangeIcon: true) {
   	         state("energy_life", label: '${currentValue}M\nLife', unit:"MWh", backgroundColors: [
                    [value: 2, color: "#bc2323"],
                    [value: 5, color: "#d04e00"],
                    [value: 10, color: "#f1d801"],
                    [value: 20, color: "#90d2a7"],
		            [value: 30, color: "#44b621"],
                    [value: 40, color: "#1e9cbb"],
                    [value: 50, color: "#153591"],
    	            ]
            	)
        	}    

            standardTile("refresh", "device.energy_today", inactiveLabel: false, decoration: "flat") {
                state "default", action:"polling.poll", icon:"st.secondary.refresh"
            }

        
        main (["power","energy_today"])
        details(["power","energy_today", "energy_life", "productionLevel", "todayMaxProd", "refresh","systemSize", "Estimated", "reported_id"]) 
	}
}


// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"

}

def poll() {
	refresh()
}

def refresh() {
  log.debug "Executing 'refresh'"
  energyRefresh()
}


def energyRefresh() {  
  log.debug "Executing 'energyToday'"
  
  def cmd = "https://api.enphaseenergy.com/api/v2/systems/${settings.system_id}/summary?key=${settings.key}&user_id=${settings.user_id}";
  log.debug "Sending request cmd[${cmd}]"
  
  httpGet(cmd) {resp ->
        if (resp.data) {
        	log.debug "${resp.data}"
            def energyToday = resp.data.energy_today/1000
            def energyLife = resp.data.energy_lifetime/1000000
            def currentPower = resp.data.current_power
			def systemSize = resp.data.size_w
			def productionLevel = currentPower/systemSize * 100
			def systemId = resp.data.system_id
			def now=new Date()
			def tz = location.timeZone
			def todayDay = now.format("dd",tz)
			def today_max_day = device.currentValue("today_max_day")
            def today_max_prod = device.currentValue("today_max_prod")
            
            // calculate new todayMaxProd percentage based on monthly enphase array info
            // month list from enphase array data daily estimate based on month 0 to 11 for Jan to Dec
            def int month = now.getAt(Calendar.MONTH)
            def enphase_daily_array_estimate = [settings.jan, settings.feb, settings.mar, 
            	settings.apr, settings.may, settings.jun, settings.jul, settings.aug, 
                settings.sep, settings.oct, settings.nov, settings.dec]
                
            def todayMaxProd = energyToday/enphase_daily_array_estimate[month] * 100
            def Enphase_Estimated = enphase_daily_array_estimate[month]
			            
			log.debug "todayMaxProd was ${todayMaxProd}"
            log.debug "enphase_daily_array_estimate was ${enphase_daily_array_estimate}"
           
            
			log.debug "System Id ${system_id}"
            log.debug "Energy today ${energyToday}"
            log.debug "Energy life ${energyLife}"
            log.debug "Current Power Level ${currentPower}"
			log.debug "System Size ${systemSize}"
			log.debug "Production Level ${currentPower}"
			log.debug "todayMaxProd is now ${todayMaxProd}"
			log.debug "today_max_day ${today_max_day}"
			log.debug "todayDay ${todayDay}"
            //log.debug "todayPeak ${todayPeak}"
			
			// If day has changed set today_max_day to new value
			if (today_max_day == null || today_max_day != todayDay) {
				log.debug "Setting today_max_day=${todayDay}"
				sendEvent(name: 'today_max_day', value: (todayDay))
				// New day reset todayMaxProd
				todayMaxProd = productionLevel
			}
            
            // String.format("%5.2f", energyToday)
            delayBetween([sendEvent(name: 'energy_today', value: (String.format("%1.3f", energyToday)))
                          ,sendEvent(name: 'energy_life', value: (String.format("%3.3f",energyLife)))
                          ,sendEvent(name: 'power', value: (currentPower))
						  ,sendEvent(name: 'production_level', value: (String.format("%5.2f",productionLevel)))
						  ,sendEvent(name: 'today_max_prod', value: (todayMaxProd))
						  ,sendEvent(name: 'today_max_prod_str', value: (String.format("%5.3f",todayMaxProd)))
                          ,sendEvent(name: 'Enphase_Estimated_str', value: (String.format("%2.4f",Enphase_Estimated)))
						  ,sendEvent(name: 'reported_id', value: (systemId))
                          ,sendEvent(name: 'systemSize', value: (systemSize))
	                     ])
			
			
        }
        if(resp.status == 200) {
            	log.debug "poll results returned"
        }
         else {
            log.error "polling children & got http status ${resp.status}"
        }
    }
}