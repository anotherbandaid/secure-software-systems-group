/**
 *  Copyright 2016 SmartThings, Inc.
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
	definition (
		name: "NFL Football",
		namespace: "SmartThingsDHs",
		author: "fieldsjm") {

		capability "Actuator"
        capability "Button"
        capability "Configuration"
        capability "Sensor"
        capability "Switch"
        capability "Health Check"
         
        attribute "Team_Abbr","string"
        attribute "Team_Name","string"
        attribute "Year","string"
        attribute "Value1","string"
    	attribute "Value2","string"
    	attribute "Value3","string"
		attribute "Value4","string"
		attribute "Value5","string"
		attribute "Value6","string"
		attribute "Value7","string"
		attribute "Value8","string"
		attribute "Value9","string"
		attribute "Value10","string"
		attribute "Value11","string"
		attribute "Value12","string"
		attribute "Value13","string"
		attribute "Value14","string"
		attribute "Value15","string"
		attribute "Value16","string"
		attribute "Value17","string"
		attribute "Value18","string"
		attribute "Value19","string"
		attribute "Value20","string"
		attribute "Value21","string"
		attribute "Value22","string"
		attribute "Value23","string"
		attribute "Value24","string"
		attribute "Value25","string"
		attribute "Value26","string"
		attribute "Value27","string"
		attribute "Value28","string"
		attribute "Value29","string"
        attribute "Value30","string"
        attribute "Value31","string"
        attribute "Value32","string"
        attribute "Value33","string"
        attribute "Value34","string"
        attribute "Value35","string"
        attribute "Value36","string"
        attribute "Value37","string"
        attribute "Value38","string"
        attribute "Value39","string"
        attribute "Value40","string"
        attribute "Value41","string"
        attribute "Value42","string"
        attribute "Value43","string"
        attribute "Value44","string"
        attribute "Value45","string"
        attribute "Value46","string"
        attribute "Value47","string"
        attribute "Value48","string"
        attribute "Value49","string"
        attribute "Value50","string"
        attribute "Value51","string"
        attribute "Value52","string"
        attribute "Value53","string"
        attribute "Value54","string"
        attribute "Value55","string"
        attribute "Value56","string"
        attribute "Value57","string"
        attribute "Value58","string"
        attribute "Value59","string"
        attribute "Value60","string"
        attribute "Value61","string"
        attribute "Value62","string"
        attribute "Value63","string"
        attribute "Value64","string"
        attribute "Value65","string"
        attribute "Value66","string"
        attribute "Value67","string"
        attribute "Value68","string"
        attribute "Value69","string"
        attribute "Value70","string"
        attribute "Value71","string"
        attribute "Value72","string"
        attribute "Value73","string"
        attribute "Value74","string"
        attribute "Value75","string"
        attribute "Value76","string"
        attribute "Value77","string"
        attribute "Value78","string"
        attribute "Value79","string"
        attribute "Value80","string"
        attribute "Value81","string"
        attribute "Value82","string"
        attribute "Value83","string"
        attribute "Value84","string"
        attribute "Value85","string"
        attribute "Value86","string"
        attribute "Value87","string"
        attribute "Value88","string"
        attribute "Value89","string"

        
    	command "push1"
        command "changeValue1"
        command "changeValue2"
        command "changeValue3"
		command "changeValue4"
		command "changeValue5"
		command "changeValue6"
		command "changeValue7"
		command "changeValue8"
		command "changeValue9"
		command "changeValue10"
		command "changeValue11"
		command "changeValue12"
		command "changeValue13"
		command "changeValue14"
		command "changeValue15"
		command "changeValue16"
		command "changeValue17"
		command "changeValue18"
		command "changeValue19"
		command "changeValue20"
		command "changeValue21"
		command "changeValue22"
		command "changeValue23"
		command "changeValue24"
		command "changeValue25"
		command "changeValue26"
		command "changeValue27"
		command "changeValue28"
		command "changeValue29"
        command "changeValue30"
        command "changeValue31"
        command "changeValue32"
        command "changeValue33"
        command "changeValue34"
        command "changeValue35"
        command "changeValue36"
        command "changeValue37"
        command "changeValue38"
        command "changeValue39"
        command "changeValue40"
        command "changeValue41"
        command "changeValue42"
        command "changeValue43"
        command "changeValue44"
        command "changeValue45"
        command "changeValue46"
        command "changeValue47"
        command "changeValue48"
        command "changeValue49"
        command "changeValue50"
        command "changeValue51"
        command "changeValue52"
        command "changeValue53"
        command "changeValue54"
        command "changeValue55"
        command "changeValue56"
        command "changeValue57"
        command "changeValue58"
        command "changeValue59"
        command "changeValue60"
        command "changeValue61"
        command "changeValue62"
        command "changeValue63"
        command "changeValue64"
        command "changeValue65"
        command "changeValue66"
        command "changeValue67"
        command "changeValue68"
        command "changeValue69"
        command "changeValue70"
        command "changeValue71"
		command "changeValue72"
        command "changeValue73"
        command "changeValue74"
        command "changeValue75"
        command "changeValue76"
        command "changeValue77"
        command "changeValue78"
        command "changeValue79"
        command "changeValue80"
        command "changeValue81"
        command "changeValue82"
        command "changeValue83"
        command "changeValue84"
        command "changeValue85"
        command "changeValue86"
        command "changeValue87"
        command "changeValue88"
        command "changeValue89"

	}
preferences {
	section("Team"){
    	input "nflteam", "enum", title: "Team", options: ["Arizona Cardinals","Atlanta Falcons","Baltimore Ravens","Buffalo Bills","Carolina Panthers","Chicago Bears","Cincinnati Bengals","Cleveland Browns","Dallas Cowboys","Denver Broncos","Detroit Lions","Green Bay Packers","Houston Texans","Indianapolis Colts","Jacksonville Jaguars","Kansas City Chiefs","Los Angeles Rams","Los Angeles Chargers","Miami Dolphins","Minnesota Vikings","New England Patriots","New Orleans Saints","New York Giants","New York Jets","Oakland Raiders","Philadelphia Eagles","Pittsburgh Steelers","Seattle Seahawks","San Francisco 49ers","Tampa Bay Buccaneers","Tennessee Titans","Washington Redskins"], multiple: false, required: true, defaultValue:"NFL"
        }
	section("Season"){
    	input "year", "text", title: "Year (in which season began)", required: true
        }
	}
	tiles (scale:2){
        valueTile("Main", "device.Main", width: 1, height: 1){
            state("Default", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'${currentValue}', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")
            }
		multiAttributeTile(name:"button1", type: "generic", width: 6, height: 4, decoration: "flat") {
        	tileAttribute ("device.button1", key: "PRIMARY_CONTROL"){
                attributeState("Default", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png", backgroundColor: "#013369")
                 attributeState("Arizona Cardinals", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png", backgroundColor: "#FFB612")
                 attributeState("Atlanta Falcons", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png", backgroundColor: "#A71930")
                 attributeState("Baltimore Ravens", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png", backgroundColor: "#000000")
                 attributeState("Buffalo Bills", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png", backgroundColor: "#C60C30")
                 attributeState("Carolina Panthers", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png", backgroundColor: "#BFC0BF")
                 attributeState("Chicago Bears", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png", backgroundColor: "#0B162A")
                 attributeState("Cincinnati Bengals", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png", backgroundColor: "#FB4F14")
                 attributeState("Cleveland Browns", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png", backgroundColor: "#311D00")
                 attributeState("Dallas Cowboys", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png", backgroundColor: "#869397")
                 attributeState("Denver Broncos", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png", backgroundColor: "#002244")
                 attributeState("Detroit Lions", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png", backgroundColor: "#0076B6")
                 attributeState("Green Bay Packers", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png", backgroundColor: "#203731")
                 attributeState("Houston Texans", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png", backgroundColor: "#03202F")
                 attributeState("Indianapolis Colts", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png", backgroundColor: "#A2AAAD")
                 attributeState("Jacksonville Jaguars", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png", backgroundColor: "#101820")
                 attributeState("Kansas City Chiefs", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png", backgroundColor: "#E31837")
                 attributeState("Los Angeles Rams", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png", backgroundColor: "#002244")
                 attributeState("Los Angeles Chargers", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png", backgroundColor: "#002A5E")
                 attributeState("Miami Dolphins", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png", backgroundColor: "#008E97")
                 attributeState("Minnesota Vikings", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png", backgroundColor: "#4F2683")
                 attributeState("New England Patriots", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png", backgroundColor: "#B0B7BC")
                 attributeState("New Orleans Saints", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png", backgroundColor: "#101820")
                 attributeState("New York Giants", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png", backgroundColor: "#A71930")
                 attributeState("New York Jets", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png", backgroundColor: "#125740")
                 attributeState("Oakland Raiders", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png", backgroundColor: "#A5ACAF")
                 attributeState("Philadelphia Eagles", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png", backgroundColor: "#004C54")
                 attributeState("Pittsburgh Steelers", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png", backgroundColor: "#FFB612")
                 attributeState("Seattle Seahawks", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png", backgroundColor: "#69BE28")
                 attributeState("San Francisco 49ers", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png", backgroundColor: "#B3995D")
                 attributeState("Tampa Bay Buccaneers", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png", backgroundColor: "#D50A0A")
                 attributeState("Tennessee Titans", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png", backgroundColor: "#0C2340")
                 attributeState("Washington Redskins", label:'', action: "push1", icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png", backgroundColor: "#773141")
				}
			tileAttribute("device.Value1", key: "SECONDARY_CONTROL") {
        		attributeState ("default", label:'Next: ${currentValue}')
            	}
        }
		valueTile("LiveHeader", "device.Value2", width: 2, height: 2){
            state "default", label:'${currentValue}'}
        valueTile("LiveTeams", "device.Value3", width: 4, height: 1){
            state "default", label:'${currentValue}'}
        valueTile("LiveScore", "device.Value4", width: 4, height: 1){
            state "default", label:'${currentValue}'}
        
        valueTile("Logo", "device.Logo", width: 3, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")
            }
            
        valueTile("Header", "device.Header", width: 6, height: 2){
            state "default", label:'${currentValue}'}
        valueTile("Record", "device.Value5", width: 3, height: 1){
        	state "default", label:'${currentValue}'}

        valueTile("Value6", "device.Value6", width: 1, height: 1){
            state("week1date", label:'Week 1\n${currentValue}')}
        valueTile("Value7", "device.Value7", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value7a", "device.Value7", width: 2, height: 1){
            state("week1team", label:'${currentValue}')}
        valueTile("Value8", "device.Value8", width: 1, height: 1){
            state("week1home", label:'${currentValue}')}
        valueTile("Value9", "device.Value9", width: 1, height: 1){
            state("week1score", label:'${currentValue}')}
        valueTile("Value10", "device.Value10", width: 1, height: 1){
            state("week2date", label:'Week 2\n${currentValue}')}
        valueTile("Value11", "device.Value11", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value11a", "device.Value11", width: 2, height: 1){
            state("week2team", label:'${currentValue}')}
        valueTile("Value12", "device.Value12", width: 1, height: 1){
            state("week2home", label:'${currentValue}')}
        valueTile("Value13", "device.Value13", width: 1, height: 1){
            state("week2score", label:'${currentValue}')}
        valueTile("Value14", "device.Value14", width: 1, height: 1){
            state("week3date", label:'Week 3\n${currentValue}')}
        valueTile("Value15", "device.Value15", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value15a", "device.Value15", width: 2, height: 1){
            state("week3team", label:'${currentValue}')}
        valueTile("Value16", "device.Value16", width: 1, height: 1){
            state("week3home", label:'${currentValue}')}
        valueTile("Value17", "device.Value17", width: 1, height: 1){
            state("week3score", label:'${currentValue}')}
        valueTile("Value18", "device.Value18", width: 1, height: 1){
            state("week4date", label:'Week 4\n${currentValue}')}
        valueTile("Value19", "device.Value19", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value19a", "device.Value19", width: 2, height: 1){
            state("week4team", label:'${currentValue}')}
        valueTile("Value20", "device.Value20", width: 1, height: 1){
            state("week4home", label:'${currentValue}')}
        valueTile("Value21", "device.Value21", width: 1, height: 1){
            state("week4score", label:'${currentValue}')}
        valueTile("Value22", "device.Value22", width: 1, height: 1){
            state("week5date", label:'Week 5\n${currentValue}')}
        valueTile("Value23", "device.Value23", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value23a", "device.Value23", width: 2, height: 1){
            state("week5team", label:'${currentValue}')}
        valueTile("Value24", "device.Value24", width: 1, height: 1){
            state("week5home", label:'${currentValue}')}
        valueTile("Value25", "device.Value25", width: 1, height: 1){
            state("week5score", label:'${currentValue}')}
        valueTile("Value26", "device.Value26", width: 1, height: 1){
            state("week6date", label:'Week 6\n${currentValue}')}
        valueTile("Value27", "device.Value27", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value27a", "device.Value27", width: 2, height: 1){
            state("week6team", label:'${currentValue}')}
        valueTile("Value28", "device.Value28", width: 1, height: 1){
            state("week6home", label:'${currentValue}')}
        valueTile("Value29", "device.Value29", width: 1, height: 1){
            state("week6score", label:'${currentValue}')}
        valueTile("Value30", "device.Value30", width: 1, height: 1){
            state("week7date", label:'Week 7\n${currentValue}')}
        valueTile("Value31", "device.Value31", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value31a", "device.Value31", width: 2, height: 1){
            state("week7team", label:'${currentValue}')}
        valueTile("Value32", "device.Value32", width: 1, height: 1){
            state("week7home", label:'${currentValue}')}
        valueTile("Value33", "device.Value33", width: 1, height: 1){
            state("week7score", label:'${currentValue}')}
        valueTile("Value34", "device.Value34", width: 1, height: 1){
            state("week8date", label:'Week 8\n${currentValue}')}
        valueTile("Value35", "device.Value35", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value35a", "device.Value35", width: 2, height: 1){
            state("week8team", label:'${currentValue}')}
        valueTile("Value36", "device.Value36", width: 1, height: 1){
            state("week8home", label:'${currentValue}')}
        valueTile("Value37", "device.Value37", width: 1, height: 1){
            state("week8score", label:'${currentValue}')}
        valueTile("Value38", "device.Value38", width: 1, height: 1){
            state("week9date", label:'Week 9\n${currentValue}')}
        valueTile("Value39", "device.Value39", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value39a", "device.Value39", width: 2, height: 1){
            state("week9team", label:'${currentValue}')}
        valueTile("Value40", "device.Value40", width: 1, height: 1){
            state("week9home", label:'${currentValue}')}
        valueTile("Value41", "device.Value41", width: 1, height: 1){
            state("week9score", label:'${currentValue}')}
        valueTile("Value42", "device.Value42", width: 1, height: 1){
            state("week10date", label:'Week 10\n${currentValue}')}
        valueTile("Value43", "device.Value43", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value43a", "device.Value43", width: 2, height: 1){
            state("week10team", label:'${currentValue}')}
        valueTile("Value44", "device.Value44", width: 1, height: 1){
            state("week10home", label:'${currentValue}')}
        valueTile("Value45", "device.Value45", width: 1, height: 1){
            state("week10score", label:'${currentValue}')}
        valueTile("Value46", "device.Value46", width: 1, height: 1){
            state("week11date", label:'Week 11\n${currentValue}')}
        valueTile("Value47", "device.Value47", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value47a", "device.Value47", width: 2, height: 1){
            state("week11team", label:'${currentValue}')}
        valueTile("Value48", "device.Value48", width: 1, height: 1){
            state("week11home", label:'${currentValue}')}
        valueTile("Value49", "device.Value49", width: 1, height: 1){
            state("week11score", label:'${currentValue}')}
        valueTile("Value50", "device.Value50", width: 1, height: 1){
            state("week12date", label:'Week 12\n${currentValue}')}
        valueTile("Value51", "device.Value51", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value51a", "device.Value51", width: 2, height: 1){
            state("week12team", label:'${currentValue}')}
        valueTile("Value52", "device.Value52", width: 1, height: 1){
            state("week12home", label:'${currentValue}')}
        valueTile("Value53", "device.Value53", width: 1, height: 1){
            state("week12score", label:'${currentValue}')}
        valueTile("Value54", "device.Value54", width: 1, height: 1){
            state("week13date", label:'Week 13\n${currentValue}')}
        valueTile("Value55", "device.Value55", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value55a", "device.Value55", width: 2, height: 1){
            state("week13team", label:'${currentValue}')}
        valueTile("Value56", "device.Value56", width: 1, height: 1){
            state("week13home", label:'${currentValue}')}
        valueTile("Value57", "device.Value57", width: 1, height: 1){
            state("week13score", label:'${currentValue}')}
        valueTile("Value58", "device.Value58", width: 1, height: 1){
            state("week14date", label:'Week 14\n${currentValue}')}
        valueTile("Value59", "device.Value59", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value59a", "device.Value59", width: 2, height: 1){
            state("week14team", label:'${currentValue}')}
        valueTile("Value60", "device.Value60", width: 1, height: 1){
            state("week14home", label:'${currentValue}')}
        valueTile("Value61", "device.Value61", width: 1, height: 1){
            state("week14score", label:'${currentValue}')}
        valueTile("Value62", "device.Value62", width: 1, height: 1){
            state("week15date", label:'Week 15\n${currentValue}')}
        valueTile("Value63", "device.Value63", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value63a", "device.Value63", width: 2, height: 1){
            state("week15team", label:'${currentValue}')}
        valueTile("Value64", "device.Value64", width: 1, height: 1){
            state("week15home", label:'${currentValue}')}
        valueTile("Value65", "device.Value65", width: 1, height: 1){
            state("week15score", label:'${currentValue}')}
        valueTile("Value66", "device.Value66", width: 1, height: 1){
            state("week16date", label:'Week 16\n${currentValue}')}
        valueTile("Value67", "device.Value67", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value67a", "device.Value67", width: 2, height: 1){
            state("week16team", label:'${currentValue}')}
        valueTile("Value68", "device.Value68", width: 1, height: 1){
            state("week16home", label:'${currentValue}')}
        valueTile("Value69", "device.Value69", width: 1, height: 1){
            state("week16score", label:'${currentValue}')}
        valueTile("Value70", "device.Value70", width: 1, height: 1){
            state("week17date", label:'Week 17\n${currentValue}')}
        valueTile("Value71", "device.Value71", width: 1, height: 1){
            state("Default", label:'', icon:"https://a.espncdn.com/i/teamlogos/leagues/500/nfl.png")
            state("Arizona Cardinals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ari.png")
            state("Atlanta Falcons", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/atl.png")
            state("Baltimore Ravens", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/bal.png")
            state("Buffalo Bills", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/buf.png")
            state("Carolina Panthers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/car.png")
            state("Chicago Bears", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/chi.png")
            state("Cincinnati Bengals", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cin.png")
            state("Cleveland Browns", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/cle.png")
            state("Dallas Cowboys", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/dal.png")
            state("Denver Broncos", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/den.png")
            state("Detroit Lions", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/det.png")
            state("Green Bay Packers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/gb.png")
            state("Houston Texans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/hou.png")
            state("Indianapolis Colts", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ind.png")
            state("Jacksonville Jaguars", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/jax.png")
            state("Kansas City Chiefs", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/kc.png")
            state("Los Angeles Rams", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lar.png")
            state("Los Angeles Chargers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/lac.png")
            state("Miami Dolphins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/mia.png")
            state("Minnesota Vikings", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/min.png")
            state("New England Patriots", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ne.png")
            state("New Orleans Saints", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/no.png")
            state("New York Giants", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png")
            state("New York Jets", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png")
            state("Oakland Raiders", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/oak.png")
            state("Philadelphia Eagles", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/phi.png")
            state("Pittsburgh Steelers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/pit.png")
            state("Seattle Seahawks", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sea.png")
            state("San Francisco 49ers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/sf.png")
            state("Tampa Bay Buccaneers", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/tb.png")
            state("Tennessee Titans", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/ten.png")
            state("Washington Redskins", label:'', icon:"https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png")}
        valueTile("Value71a", "device.Value71", width: 2, height: 1){
            state("week17team", label:'${currentValue}')}
        valueTile("Value72", "device.Value72", width: 1, height: 1){
            state("week17home", label:'${currentValue}')}
        valueTile("Value73", "device.Value73", width: 1, height: 1){
            state("week17score", label:'${currentValue}')}

        valueTile("NFL", "device.Text1", width: 6, height: 2) {state "default", label:'NFL Schedule\nThis Week:'}
        valueTile("Value74", "device.Value74", width: 3, height: 2){state "default", label:'${currentValue}'}
        valueTile("Value75", "device.Value75", width: 3, height: 2){state "default", label:'${currentValue}'}
        valueTile("Value76", "device.Value76", width: 3, height: 2){state "default", label:'${currentValue}'}
        valueTile("Value77", "device.Value77", width: 3, height: 2){state "default", label:'${currentValue}'}
        valueTile("Value78", "device.Value78", width: 3, height: 2){state "default", label:'${currentValue}'}
        valueTile("Value79", "device.Value79", width: 3, height: 2){state "default", label:'${currentValue}'}
        valueTile("Value80", "device.Value80", width: 3, height: 2){state "default", label:'${currentValue}'}
        valueTile("Value81", "device.Value81", width: 3, height: 2){state "default", label:'${currentValue}'}
        valueTile("Value82", "device.Value82", width: 3, height: 2){state "default", label:'${currentValue}'}
        valueTile("Value83", "device.Value83", width: 3, height: 2){state "default", label:'${currentValue}'}
        valueTile("Value84", "device.Value84", width: 3, height: 2){state "default", label:'${currentValue}'}
        valueTile("Value85", "device.Value85", width: 3, height: 2){state "default", label:'${currentValue}'}
        valueTile("Value86", "device.Value86", width: 3, height: 2){state "default", label:'${currentValue}'}
        valueTile("Value87", "device.Value87", width: 3, height: 2){state "default", label:'${currentValue}'}
        valueTile("Value88", "device.Value88", width: 3, height: 2){state "default", label:'${currentValue}'}
        valueTile("Value89", "device.Value89", width: 3, height: 2){state "default", label:'${currentValue}'}


 		main(["Main"])
 		details(["button1","LiveHeader","LiveTeams","LiveScore","Header","Logo","Record",,"Value6","Value7","Value7a","Value8","Value9","Value10","Value11","Value11a","Value12","Value13","Value14","Value15","Value15a","Value16","Value17","Value18","Value19","Value19a","Value20","Value21","Value22","Value23","Value23a","Value24","Value25","Value26","Value27","Value27a","Value28","Value29","Value30","Value31","Value31a","Value32","Value33","Value34","Value35","Value35a","Value36","Value37","Value38","Value39","Value39a","Value40","Value41","Value42","Value43","Value43a","Value44","Value45","Value46","Value47","Value47a","Value48","Value49","Value50","Value51","Value51a","Value52","Value53","Value54","Value55","Value55a","Value56","Value57","Value58","Value59","Value59a","Value60","Value61","Value62","Value63","Value63a","Value64","Value65","Value66","Value67","Value67a","Value68","Value69","Value70","Value71","Value71a","Value72","Value73","NFL","Value74","Value75","Value76","Value77","Value78","Value79","Value80","Value81","Value82","Value83","Value84","Value85","Value86","Value87","Value88","Value89"])
 	}
 }

def installed() {
    initialize()
}

def updated() {
    initialize()
}

def initialize() {

	sendEvent(name: "Main", value: "${settings.nflteam}")
    sendEvent(name: "Team_Abbr", value: Team_Abbr())
    sendEvent(name: "Team_Name", value: "${settings.nflteam}")
    sendEvent(name: "Year", value: "${settings.year}")
    sendEvent(name: "Logo", value: "${settings.nflteam}")
    sendEvent(name: "button1", value: "${settings.nflteam}")
    sendEvent(name: "Header", value: Heading())
    push1()
}
    
def parse(String description) {
}
def Team_Abbr(){
    if("${settings.nflteam}" == "Arizona Cardinals") {
        return "ARI"
    } else if("${settings.nflteam}" == "Atlanta Falcons") {
        return "ATL"
    } else if("${settings.nflteam}" == "Baltimore Ravens") {
        return "BAL"
    } else if("${settings.nflteam}" == "Buffalo Bills") {
        return "BUF"
    } else if("${settings.nflteam}" == "Carolina Panthers") {
        return "CAR"
    } else if("${settings.nflteam}" == "Chicago Bears") {
        return "CHI"
    } else if("${settings.nflteam}" == "Cincinnati Bengals") {
        return "CIN"
    } else if("${settings.nflteam}" == "Cleveland Browns") {
        return "CLE"
    } else if("${settings.nflteam}" == "Dallas Cowboys") {
        return "DAL"
    } else if("${settings.nflteam}" == "Denver Broncos") {
        return "DEN"
    } else if("${settings.nflteam}" == "Detroit Lions") {
        return "DET"
    } else if("${settings.nflteam}" == "Green Bay Packers") {
        return "GB"
    } else if("${settings.nflteam}" == "Houston Texans") {
        return "HOU"
    } else if("${settings.nflteam}" == "Indianapolis Colts") {
        return "IND"
    } else if("${settings.nflteam}" == "Jacksonville Jaguars") {
        return "JAX"
    } else if("${settings.nflteam}" == "Kansas City Chiefs") {
        return "KC"
    } else if("${settings.nflteam}" == "Los Angeles Rams") {
        return "LA"
    } else if("${settings.nflteam}" == "Los Angeles Chargers") {
        return "LAC"
    } else if("${settings.nflteam}" == "Miami Dolphins") {
        return "MIA"
    } else if("${settings.nflteam}" == "Minnesota Vikings") {
        return "MIN"
    } else if("${settings.nflteam}" == "New England Patriots") {
        return "NE"
    } else if("${settings.nflteam}" == "New Orleans Saints") {
        return "NO"
    } else if("${settings.nflteam}" == "New York Giants") {
        return "NYG"
    } else if("${settings.nflteam}" == "New York Jets") {
        return "NYJ"
    } else if("${settings.nflteam}" == "Oakland Raiders") {
        return "OAK"
    } else if("${settings.nflteam}" == "Philadelphia Eagles") {
        return "PHI"
    } else if("${settings.nflteam}" == "Pittsburgh Steelers") {
        return "PIT"
    } else if("${settings.nflteam}" == "Seattle Seahawks") {
        return "SEA"
    } else if("${settings.nflteam}" == "San Francisco 49ers") {
        return "SF"
    } else if("${settings.nflteam}" == "Tampa Bay Buccaneers") {
        return "TB"
    } else if("${settings.nflteam}" == "Tennessee Titans") {
        return "TEN"
    } else if("${settings.nflteam}" == "Washington Redskins") {
        return "WAS"
    } else{
    	return "NFL"
	}
}
def Heading(){
    return "${settings.nflteam} \n${settings.year} Schedule"
    }    

def changeValue1 (param1) {sendEvent("name":"Value1", "value":param1)}
def changeValue2 (param2) {sendEvent("name":"Value2", "value":param2)}
def changeValue3 (param3) {sendEvent("name":"Value3", "value":param3)}
def changeValue4 (param4) {sendEvent("name":"Value4", "value":param4)}
def changeValue5 (param5) {sendEvent("name":"Value5", "value":param5)}
def changeValue6 (param6) {sendEvent("name":"Value6", "value":param6)}
def changeValue7 (param7) {sendEvent("name":"Value7", "value":param7)}
def changeValue8 (param8) {sendEvent("name":"Value8", "value":param8)}
def changeValue9 (param9) {sendEvent("name":"Value9", "value":param9)}
def changeValue10 (param10) {sendEvent("name":"Value10", "value":param10)}
def changeValue11 (param11) {sendEvent("name":"Value11", "value":param11)}
def changeValue12 (param12) {sendEvent("name":"Value12", "value":param12)}
def changeValue13 (param13) {sendEvent("name":"Value13", "value":param13)}
def changeValue14 (param14) {sendEvent("name":"Value14", "value":param14)}
def changeValue15 (param15) {sendEvent("name":"Value15", "value":param15)}
def changeValue16 (param16) {sendEvent("name":"Value16", "value":param16)}
def changeValue17 (param17) {sendEvent("name":"Value17", "value":param17)}
def changeValue18 (param18) {sendEvent("name":"Value18", "value":param18)}
def changeValue19 (param19) {sendEvent("name":"Value19", "value":param19)}
def changeValue20 (param20) {sendEvent("name":"Value20", "value":param20)}
def changeValue21 (param21) {sendEvent("name":"Value21", "value":param21)}
def changeValue22 (param22) {sendEvent("name":"Value22", "value":param22)}
def changeValue23 (param23) {sendEvent("name":"Value23", "value":param23)}
def changeValue24 (param24) {sendEvent("name":"Value24", "value":param24)}
def changeValue25 (param25) {sendEvent("name":"Value25", "value":param25)}
def changeValue26 (param26) {sendEvent("name":"Value26", "value":param26)}
def changeValue27 (param27) {sendEvent("name":"Value27", "value":param27)}
def changeValue28 (param28) {sendEvent("name":"Value28", "value":param28)}
def changeValue29 (param29) {sendEvent("name":"Value29", "value":param29)}
def changeValue30 (param30) {sendEvent("name":"Value30", "value":param30)}
def changeValue31 (param31) {sendEvent("name":"Value31", "value":param31)}
def changeValue32 (param32) {sendEvent("name":"Value32", "value":param32)}
def changeValue33 (param33) {sendEvent("name":"Value33", "value":param33)}
def changeValue34 (param34) {sendEvent("name":"Value34", "value":param34)}
def changeValue35 (param35) {sendEvent("name":"Value35", "value":param35)}
def changeValue36 (param36) {sendEvent("name":"Value36", "value":param36)}
def changeValue37 (param37) {sendEvent("name":"Value37", "value":param37)}
def changeValue38 (param38) {sendEvent("name":"Value38", "value":param38)}
def changeValue39 (param39) {sendEvent("name":"Value39", "value":param39)}
def changeValue40 (param40) {sendEvent("name":"Value40", "value":param40)}
def changeValue41 (param41) {sendEvent("name":"Value41", "value":param41)}
def changeValue42 (param42) {sendEvent("name":"Value42", "value":param42)}
def changeValue43 (param43) {sendEvent("name":"Value43", "value":param43)}
def changeValue44 (param44) {sendEvent("name":"Value44", "value":param44)}
def changeValue45 (param45) {sendEvent("name":"Value45", "value":param45)}
def changeValue46 (param46) {sendEvent("name":"Value46", "value":param46)}
def changeValue47 (param47) {sendEvent("name":"Value47", "value":param47)}
def changeValue48 (param48) {sendEvent("name":"Value48", "value":param48)}
def changeValue49 (param49) {sendEvent("name":"Value49", "value":param49)}
def changeValue50 (param50) {sendEvent("name":"Value50", "value":param50)}
def changeValue51 (param51) {sendEvent("name":"Value51", "value":param51)}
def changeValue52 (param52) {sendEvent("name":"Value52", "value":param52)}
def changeValue53 (param53) {sendEvent("name":"Value53", "value":param53)}
def changeValue54 (param54) {sendEvent("name":"Value54", "value":param54)}
def changeValue55 (param55) {sendEvent("name":"Value55", "value":param55)}
def changeValue56 (param56) {sendEvent("name":"Value56", "value":param56)}
def changeValue57 (param57) {sendEvent("name":"Value57", "value":param57)}
def changeValue58 (param58) {sendEvent("name":"Value58", "value":param58)}
def changeValue59 (param59) {sendEvent("name":"Value59", "value":param59)}
def changeValue60 (param60) {sendEvent("name":"Value60", "value":param60)}
def changeValue61 (param61) {sendEvent("name":"Value61", "value":param61)}
def changeValue62 (param62) {sendEvent("name":"Value62", "value":param62)}
def changeValue63 (param63) {sendEvent("name":"Value63", "value":param63)}
def changeValue64 (param64) {sendEvent("name":"Value64", "value":param64)}
def changeValue65 (param65) {sendEvent("name":"Value65", "value":param65)}
def changeValue66 (param66) {sendEvent("name":"Value66", "value":param66)}
def changeValue67 (param67) {sendEvent("name":"Value67", "value":param67)}
def changeValue68 (param68) {sendEvent("name":"Value68", "value":param68)}
def changeValue69 (param69) {sendEvent("name":"Value69", "value":param69)}
def changeValue70 (param70) {sendEvent("name":"Value70", "value":param70)}
def changeValue71 (param71) {sendEvent("name":"Value71", "value":param71)}
def changeValue72 (param72) {sendEvent("name":"Value72", "value":param72)}
def changeValue73 (param73) {sendEvent("name":"Value73", "value":param73)}
def changeValue74 (param74) {sendEvent("name":"Value74", "value":param74)}
def changeValue75 (param75) {sendEvent("name":"Value75", "value":param75)}
def changeValue76 (param76) {sendEvent("name":"Value76", "value":param76)}
def changeValue77 (param77) {sendEvent("name":"Value77", "value":param77)}
def changeValue78 (param78) {sendEvent("name":"Value78", "value":param78)}
def changeValue79 (param79) {sendEvent("name":"Value79", "value":param79)}
def changeValue80 (param80) {sendEvent("name":"Value80", "value":param80)}
def changeValue81 (param81) {sendEvent("name":"Value81", "value":param81)}
def changeValue82 (param82) {sendEvent("name":"Value82", "value":param82)}
def changeValue83 (param83) {sendEvent("name":"Value83", "value":param83)}
def changeValue84 (param84) {sendEvent("name":"Value84", "value":param84)}
def changeValue85 (param85) {sendEvent("name":"Value85", "value":param85)}
def changeValue86 (param86) {sendEvent("name":"Value86", "value":param86)}
def changeValue87 (param87) {sendEvent("name":"Value87", "value":param87)}
def changeValue88 (param88) {sendEvent("name":"Value88", "value":param88)}
def changeValue89 (param89) {sendEvent("name":"Value89", "value":param89)}


def push1() {
	sendEvent(name: "button", value: "pushed", data: [buttonNumber: "1"], descriptionText: "$device.displayName button 1 was pushed", isStateChange: true)
}
