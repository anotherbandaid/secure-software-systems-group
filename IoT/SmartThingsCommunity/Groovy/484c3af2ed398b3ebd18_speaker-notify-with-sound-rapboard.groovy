/**
 *  Copyright 2015 dburman 
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
 *  Author: dburman 
 *  Version: 2.1
 *  Date: 2019-11-07
 */
definition(
  name: "Speaker Notify with RapBoard",
  namespace: "smartthings",
  author: "dburman",
  description: "Play therapboard.com sounds or custom message through your speakers when the mode changes or other events occur.",
  category: "SmartThings Labs",
  iconUrl: "https://s3.amazonaws.com/smartapp-icons/Partner/sonos.png",
  iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Partner/sonos@2x.png"
)

preferences {
  page(name: "mainPage", title: "Play a message on your Speaker when something happens", install: true, uninstall: true)
  page(name: "chooseTrack", title: "Select a song or station")
  page(name: "timeIntervalInput", title: "Only during a certain time") {
    section {
      input "starting", "time", title: "Starting", required: false
      input "ending", "time", title: "Ending", required: false
    }
  }
}

def mainPage() {
  dynamicPage(name: "mainPage") {
    def anythingSet = anythingSet()
    if (anythingSet) {
      section("Play message when"){
        ifSet "motion", "capability.motionSensor", title: "Motion Here", required: false, multiple: true
        ifSet "contact", "capability.contactSensor", title: "Contact Opens", required: false, multiple: true
        ifSet "contactClosed", "capability.contactSensor", title: "Contact Closes", required: false, multiple: true
        ifSet "acceleration", "capability.accelerationSensor", title: "Acceleration Detected", required: false, multiple: true
        ifSet "mySwitch", "capability.switch", title: "Switch Turned On", required: false, multiple: true
        ifSet "mySwitchOff", "capability.switch", title: "Switch Turned Off", required: false, multiple: true
        ifSet "arrivalPresence", "capability.presenceSensor", title: "Arrival Of", required: false, multiple: true
        ifSet "departurePresence", "capability.presenceSensor", title: "Departure Of", required: false, multiple: true
        ifSet "smoke", "capability.smokeDetector", title: "Smoke Detected", required: false, multiple: true
        ifSet "water", "capability.waterSensor", title: "Water Sensor Wet", required: false, multiple: true
        ifSet "button1", "capability.button", title: "Button Press", required:false, multiple:true //remove from production
        ifSet "triggerModes", "mode", title: "System Changes Mode", required: false, multiple: true
        ifSet "timeOfDay", "time", title: "At a Scheduled Time", required: false
      }
    }
    def hideable = anythingSet || app.installationState == "COMPLETE"
    def sectionTitle = anythingSet ? "Select additional triggers" : "Play message when..."

    section(sectionTitle, hideable: hideable, hidden: true){
      ifUnset "motion", "capability.motionSensor", title: "Motion Here", required: false, multiple: true
      ifUnset "contact", "capability.contactSensor", title: "Contact Opens", required: false, multiple: true
      ifUnset "contactClosed", "capability.contactSensor", title: "Contact Closes", required: false, multiple: true
      ifUnset "acceleration", "capability.accelerationSensor", title: "Acceleration Detected", required: false, multiple: true
      ifUnset "mySwitch", "capability.switch", title: "Switch Turned On", required: false, multiple: true
      ifUnset "mySwitchOff", "capability.switch", title: "Switch Turned Off", required: false, multiple: true
      ifUnset "arrivalPresence", "capability.presenceSensor", title: "Arrival Of", required: false, multiple: true
      ifUnset "departurePresence", "capability.presenceSensor", title: "Departure Of", required: false, multiple: true
      ifUnset "smoke", "capability.smokeDetector", title: "Smoke Detected", required: false, multiple: true
      ifUnset "water", "capability.waterSensor", title: "Water Sensor Wet", required: false, multiple: true
      ifUnset "button1", "capability.button", title: "Button Press", required:false, multiple:true //remove from production
      ifUnset "triggerModes", "mode", title: "System Changes Mode", description: "Select mode(s)", required: false, multiple: true
      ifUnset "timeOfDay", "time", title: "At a Scheduled Time", required: false
    }
    section{
      input "actionType", "enum", title: "Action?", required: true, defaultValue: "Custom Message", options: [
      "Custom Message",
      "Random",
      "2chainz_4",
      "2chainz_yeah2",
      "2chainz_tellem",
      "2chainz_tru",
      "2chainz_unh3",
      "2chainz_watchout",
      "2chainz_whistle",
      "2pac_4",
      "2pac_5",
      "2pac_6",
      "21savage_21",
      "50_5",
      "50_8",
      "50_11",
      "50cent_2",
      "action_bronsolino",
      "action_yeah",
      "action_yo",
      "actionbronson_istme",
      "actionbronson_unh",
      "akon_1",
      "beanie_mac",
      "bigboi_1",
      "bigl_3",
      "bigl_4",
      "bigl_5",
      "bigsean_boi2",
      "bigsean_doit",
      "bigsean_holdup2",
      "bigsean_ohgod",
      "bigsean_okay",
      "bigsean_stop",
      "bigsean_whoa",
      "bigsean_whoathere",
      "bigsean_unhunh",
      "birdman_4",
      "birdman_10",
      "birdman_16",
      "birdman_1",
      "birdman_6",
      "birdman_birdman",
      "birdman_nonsense",
      "birdman_respeck",
      "bobby_hahaha",
      "bowwow_yeah",
      "bunb_ugk4life",
      "busta_6",
      "busta_1",
      "busta_2",
      "busta_5",
      "camron_1",
      "camron_2",
      "chance_aghh2",
      "chedda_car4",
      "chedda_ugh",
      "keef_bang",
      "keef_catchup",
      "chingy_1",
      "currensy_1",
      "dabrat_comeon",
      "dabrat_lookout",
      "dabrat_oh",
      "danny_stop",
      "danny_yeah",
      "dannybrown_laugh2",
      "danny_check",
      "davidbanner_5",
      "desiigner_pandapanda",
      "desiigner_rahhh",
      "desiigner_yeha",
      "diddy_1",
      "diddy_3",
      "diddy_4",
      "diddy_5",
      "diddy_6",
      "diddy_7",
      "dizzee_1",
      "djdrama_dramatic",
      "djkhaled_2",
      "djkhaled_3",
      "khaled_blessup2",
      "khaled_majorkey3",
      "khaled_Riel_Life",
      "khaled_theydontwant",
      "khaled_wethebest",
      "khaled_youdontlovemenomo",
      "khaled_anotherone",
      "djmustard_onthebeat",
      "djpaul_2",
      "djpaul_3",
      "djpaul_9",
      "dmx_1",
      "dmx_3",
      "dmx_6",
      "dmx_7",
      "drake_2",
      "drake_3",
      "drake_4",
      "drake_5",
      "drake_goddamngoddamn",
      "drake_worst",
      "drake_yeahyuh3",
      "drummaboy_1",
      "e40_1",
      "e40_2",
      "eazye_1",
      "eminem_3",
      "eminem_4",
      "fatjoe_1",
      "fatjoe_9",
      "fatjoe_5",
      "fetty_1738",
      "fetty_hey2",
      "fetty_yeahbaby",
      "flava_1",
      "foxy_yeah",
      "foxy_unh2",
      "foxy_heyo",
      "freeway_1",
      "french_1",
      "french_haan",
      "future_brrr",
      "future_hendrix",
      "future_hey",
      "future_woo",
      "ghostface_yo",
      "grandmaster_1",
      "gucci_1",
      "gucci_4",
      "gucci_14",
      "gucci_8",
      "gucci_9",
      "hurricanechris_1",
      "icecube_1",
      "inspectahdeck_killahill",
      "jadakiss_3",
      "jarule_1",
      "jarule_2",
      "jayz_7",
      "jayz_9",
      "jayz_1",
      "jayz5",
      "jayz7",
      "jayz8",
      "jayz_itsthero",
      "jayz_itsyoboy",
      "jayz_jiggaman",
      "jayz_woo",
      "jayz_yessir",
      "jayz_young",
      "jazzypha_1",
      "jcole_bitch",
      "jermaine_unh",
      "jones_8",
      "jones_14",
      "juelz_2",
      "juicyj_1",
      "juicyj_8",
      "juicyj_9",
      "juicyj_10",
      "juicyj_7",
      "juicyj_yeahhoe",
      "kanebeatz_inthebuilding",
      "kanye_1",
      "kanye_ouh",
      "kanye_unh",
      "kendrick_biatch3",
      "kendrick_tootoo",
      "killermike_2",
      "killermike_3",
      "krsone_1",
      "lilb_1",
      "lilb_2",
      "liljon_2",
      "liljon_3",
      "liljon_8",
      "liljon_4",
      "lilkim_queenb",
      "lilkim_uhunh",
      "lilscrappy_1",
      "liluzivert_yah2",
      "weezy_14",
      "weezy_22",
      "weezy_29",
      "weezy_4",
      "weezy_16",
      "weezy_17",
      "weezy_25",
      "weezy_30",
      "weezy_31",
      "lilyachty_lilboat",
      "banks_unh",
      "banks_yeah",
      "ludacris_2",
      "ludacris_woo",
      "madeintyo_skrrskrr",
      "mannie_1",
      "mannie_2",
      "mastaace_awwyeah",
      "mceiht_1",
      "mchammer_1",
      "meek_bow",
      "meek_unh",
      "methodman_1",
      "methodman_tical",
      "methodman_yo",
      "metro_somemo",
      "mikejones_2",
      "mikejones_iceage",
      "mikejones_jyeah",
      "mop_1",
      "mop_2",
      "nas_1",
      "nas_3",
      "nas_6",
      "natedogg_1",
      "nicki_augh",
      "nicki_cat",
      "nicki_laugh2",
      "nicki_ok",
      "nicki_unh",
      "nicki_youngmoney",
      "nore_1",
      "biggie_1",
      "biggie_2",
      "oj_4",
      "odb_shimmy",
      "odb_yo",
      "pharrell_1",
      "pharrell_2",
      "pill_1",
      "pimpc_4",
      "pimpc_1",
      "pimpc_sweetjones3",
      "pitbull_1",
      "pitbull_2",
      "pitbull_3",
      "pitbull_6",
      "problem_what2",
      "projectpat_1",
      "pushat_1",
      "pushat_haha",
      "pushat_unh",
      "pushat_haha3",
      "quavo_ayy",
      "quavo_gone2",
      "quavo_ugh",
      "quavo_youngnigga",
      "quavo_migo",
      "raekwon_yo",
      "raesremmurd_hay",
      "redman_heyo",
      "richhomie_hey",
      "richhomie_quan",
      "ross_1",
      "ross_2",
      "ross_4",
      "ross_woo",
      "schoolboy_yawk",
      "seanprice_1",
      "thugga_2",
      "thugga_3",
      "snoop_5",
      "snoop_4",
      "snoop_1",
      "soulja_2",
      "soulja_4",
      "soulja_5",
      "swizz_1",
      "swizz_goddamit",
      "takeoff_ayy",
      "takeoff_bitch",
      "takeoff_graaa",
      "takeoff_lean",
      "takeoff_money",
      "takeoff_takeoff",
      "takeoff_woo",
      "takeoff_woowoo",
      "takeoff_damn",
      "takeoff_ugh",
      "ti_5",
      "ti_2",
      "ti_3",
      "ti_22",
      "ti_32",
      "tooshort_1",
      "tpain_2",
      "tpain1",
      "trapaholics_1",
      "trapaholics_damnson",
      "traviscott_laflame",
      "traviscott_straightup",
      "traviscott_yah",
      "treysongz_4",
      "treysongz_uhunh",
      "trick_1",
      "trick_2",
      "trick_4",
      "tydolla_dollasign",
      "tyga_unh",
      "vado_1",
      "waka_1",
      "waka_8",
      "willsmith_1",
      "willsmith_2",
      "willsmith_3",
      "wiz_1",
      "wiz_unh",
      "yg_skrrt",
      "yogotti_yeah",
      "jeezy_10",
      "jeezy_11",
      "jeezy_1",
      "youngthug_boss",
      "youngthug_ew",
      "youngthug_git",
      "youngthug_ish",
      "youngthug_phoo",
      "youngthug_wha"
      ]
      input "message","text",title:"Play this message", required:false, multiple: false
    }
    section {
      input "audiospeaker", "capability.audioNotification", title: "On this Audio player", required: true
    }
    section("More options", hideable: true, hidden: true) {
      input "resumePlaying", "bool", title: "Resume currently playing music after notification", required: false, defaultValue: true
        input "volume", "number", title: "Temporarily change volume", description: "0-100%", required: false
        input "frequency", "decimal", title: "Minimum time between actions (defaults to every event)", description: "Minutes", required: false
        href "timeIntervalInput", title: "Only during a certain time", description: timeLabel ?: "Tap to set", state: timeLabel ? "complete" : "incomplete"
        input "days", "enum", title: "Only on certain days of the week", multiple: true, required: false,
        options: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
        if (settings.modes) {
          input "modes", "mode", title: "Only when mode is", multiple: true, required: false
        }
      input "oncePerDay", "bool", title: "Only once per day", required: false, defaultValue: false
    }
    section([mobileOnly:true]) {
      label title: "Assign a name", required: false
      mode title: "Set for specific mode(s)", required: false
    }
  }
}

def chooseTrack() {
  dynamicPage(name: "chooseTrack") {
    section{
      input "song","enum",title:"Play this track", required:true, multiple: false, options: songOptions()
    }
  }
}

private songOptions() {

  // Make sure current selection is in the set

  def options = new LinkedHashSet()
  if (state.selectedSong?.station) {
    options << state.selectedSong.station
  }
  else if (state.selectedSong?.description) {
    // TODO - Remove eventually? 'description' for backward compatibility
    options << state.selectedSong.description
  }

  // Query for recent tracks
  def states = audiospeaker.statesSince("trackData", new Date(0), [max:30])
  def dataMaps = states.collect{it.jsonValue}
  options.addAll(dataMaps.collect{it.station})

  log.trace "${options.size()} songs in list"
  options.take(20) as List
}

private saveSelectedSong() {
  try {
    def thisSong = song
    log.info "Looking for $thisSong"
    def songs = audiospeaker.statesSince("trackData", new Date(0), [max:30]).collect{it.jsonValue}
    log.info "Searching ${songs.size()} records"

    def data = songs.find {s -> s.station == thisSong}
    log.info "Found ${data?.station}"
    if (data) {
      state.selectedSong = data
      log.debug "Selected song = $state.selectedSong"
    }
    else if (song == state.selectedSong?.station) {
      log.debug "Selected existing entry '$song', which is no longer in the last 20 list"
    }
    else {
      log.warn "Selected song '$song' not found"
    }
  }
  catch (Throwable t) {
    log.error t
  }
}

private anythingSet() {
  for (name in ["motion","contact","contactClosed","acceleration","mySwitch","mySwitchOff","arrivalPresence","departurePresence","smoke","water","button1","timeOfDay","triggerModes","timeOfDay"]) {
    if (settings[name]) {
      return true
    }
  }
  return false
}

private ifUnset(Map options, String name, String capability) {
  if (!settings[name]) {
    input(options, name, capability)
  }
}

private ifSet(Map options, String name, String capability) {
  if (settings[name]) {
    input(options, name, capability)
  }
}

def installed() {
  log.debug "Installed with settings: ${settings}"
  subscribeToEvents()
}

def updated() {
  log.debug "Updated with settings: ${settings}"
  unsubscribe()
  unschedule()
  subscribeToEvents()
}

def subscribeToEvents() {
  subscribe(app, appTouchHandler)
  subscribe(contact, "contact.open", eventHandler)
  subscribe(contactClosed, "contact.closed", eventHandler)
  subscribe(acceleration, "acceleration.active", eventHandler)
  subscribe(motion, "motion.active", eventHandler)
  subscribe(mySwitch, "switch.on", eventHandler)
  subscribe(mySwitchOff, "switch.off", eventHandler)
  subscribe(arrivalPresence, "presence.present", eventHandler)
  subscribe(departurePresence, "presence.not present", eventHandler)
  subscribe(smoke, "smoke.detected", eventHandler)
  subscribe(smoke, "smoke.tested", eventHandler)
  subscribe(smoke, "carbonMonoxide.detected", eventHandler)
  subscribe(water, "water.wet", eventHandler)
  subscribe(button1, "button.pushed", eventHandler)

  if (triggerModes) {
    subscribe(location, modeChangeHandler)
  }

  if (timeOfDay) {
    schedule(timeOfDay, scheduledTimeHandler)
  }

  if (song) {
    saveSelectedSong()
  }
}

def eventHandler(evt) {
  log.trace "eventHandler($evt?.name: $evt?.value)"
  if (allOk) {
    log.trace "allOk"
    loadText()
    def lastTime = state[frequencyKey(evt)]
    if (oncePerDayOk(lastTime)) {
      if (frequency) {
        if (lastTime == null || (now() - lastTime) >= (frequency * 60000)) {
          takeAction(evt)
        }
        else {
          log.debug "Not taking action because $frequency minutes have not elapsed since last action"
        }
      }
      else {
        takeAction(evt)
      }
    }
    else {
      log.debug "Not taking action because it was already taken today"
    }
  }
}
def modeChangeHandler(evt) {
  log.trace "modeChangeHandler $evt.name: $evt.value ($triggerModes)"
  if (evt.value in triggerModes) {
    eventHandler(evt)
  }
}

def scheduledTimeHandler() {
  eventHandler(null)
}

def appTouchHandler(evt) {
  takeAction(evt)
}

private takeAction(evt) {

  log.trace "takeAction()"

  if (resumePlaying){
    audiospeaker.playTrackAndResume(state.sound.uri, volume)
  }
  else {
    audiospeaker.playTrackAndRestore(state.sound.uri, volume)
  }

  if (frequency || oncePerDay) {
    state[frequencyKey(evt)] = now()
  }
  log.trace "Exiting takeAction()"
}

private frequencyKey(evt) {
  "lastActionTimeStamp"
}

private dayString(Date date) {
  def df = new java.text.SimpleDateFormat("yyyy-MM-dd")
  if (location.timeZone) {
    df.setTimeZone(location.timeZone)
  }
  else {
    df.setTimeZone(TimeZone.getTimeZone("America/New_York"))
  }
  df.format(date)
}

private oncePerDayOk(Long lastTime) {
  def result = true
  if (oncePerDay) {
    result = lastTime ? dayString(new Date()) != dayString(new Date(lastTime)) : true
    log.trace "oncePerDayOk = $result"
  }
  result
}

private getAllOk() {
  modeOk && daysOk && timeOk
}

private getModeOk() {
  def result = !modes || modes.contains(location.mode)
  log.trace "modeOk = $result"
  result
}

private getDaysOk() {
  def result = true
  if (days) {
    def df = new java.text.SimpleDateFormat("EEEE")
    if (location.timeZone) {
      df.setTimeZone(location.timeZone)
    }
    else {
      df.setTimeZone(TimeZone.getTimeZone("America/New_York"))
    }
    def day = df.format(new Date())
    result = days.contains(day)
  }
  log.trace "daysOk = $result"
  result
}

private getTimeOk() {
  def result = true
  if (starting && ending) {
    def currTime = now()
    def start = timeToday(starting, location?.timeZone).time
    def stop = timeToday(ending, location?.timeZone).time
    result = start < stop ? currTime >= start && currTime <= stop : currTime <= stop || currTime >= start
  }
  log.trace "timeOk = $result"
  result
}

private hhmm(time, fmt = "h:mm a")
{
  def t = timeToday(time, location.timeZone)
  def f = new java.text.SimpleDateFormat(fmt)
  f.setTimeZone(location.timeZone ?: timeZone(time))
  f.format(t)
}

private getTimeLabel()
{
  (starting && ending) ? hhmm(starting) + "-" + hhmm(ending, "h:mm a z") : ""
}

private loadText() {
  def trackuri = [
  "2chainz_4.mp3",
  "2chainz_yeah2.mp3",
  "2chainz_tellem.mp3",
  "2chainz_tru.mp3",
  "2chainz_unh3.mp3",
  "2chainz_watchout.mp3",
  "2chainz_whistle.mp3",
  "2pac_4.mp3",
  "2pac_5.mp3",
  "2pac_6.mp3",
  "21savage_21.mp3",
  "50_5.mp3",
  "50_8.mp3",
  "50_11.mp3",
  "50cent_2.mp3",
  "action_bronsolino.mp3",
  "action_yeah.mp3",
  "action_yo.mp3",
  "actionbronson_istme.mp3",
  "actionbronson_unh.mp3",
  "akon_1.mp3",
  "beanie_mac.mp3",
  "bigboi_1.mp3",
  "bigl_3.mp3",
  "bigl_4.mp3",
  "bigl_5.mp3",
  "bigsean_boi2.mp3",
  "bigsean_doit.mp3",
  "bigsean_holdup2.mp3",
  "bigsean_ohgod.mp3",
  "bigsean_okay.mp3",
  "bigsean_stop.mp3",
  "bigsean_whoa.mp3",
  "bigsean_whoathere.mp3",
  "bigsean_unhunh.mp3",
  "birdman_4.mp3",
  "birdman_10.mp3",
  "birdman_16.mp3",
  "birdman_1.mp3",
  "birdman_6.mp3",
  "birdman_birdman.mp3",
  "birdman_nonsense.mp3",
  "birdman_respeck.mp3",
  "bobby_hahaha.mp3",
  "bowwow_yeah.mp3",
  "bunb_ugk4life.mp3",
  "busta_6.mp3",
  "busta_1.mp3",
  "busta_2.mp3",
  "busta_5.mp3",
  "camron_1.mp3",
  "camron_2.mp3",
  "chance_aghh2.mp3",
  "chedda_car4.mp3",
  "chedda_ugh.mp3",
  "keef_bang.mp3",
  "keef_catchup.mp3",
  "chingy_1.mp3",
  "currensy_1.mp3",
  "dabrat_comeon.mp3",
  "dabrat_lookout.mp3",
  "dabrat_oh.mp3",
  "danny_stop.mp3",
  "danny_yeah.mp3",
  "dannybrown_laugh2.mp3",
  "danny_check.mp3",
  "davidbanner_5.mp3",
  "desiigner_pandapanda.mp3",
  "desiigner_rahhh.mp3",
  "desiigner_yeha.mp3",
  "diddy_1.mp3",
  "diddy_3.mp3",
  "diddy_4.mp3",
  "diddy_5.mp3",
  "diddy_6.mp3",
  "diddy_7.mp3",
  "dizzee_1.mp3",
  "djdrama_dramatic.mp3",
  "djkhaled_2.mp3",
  "djkhaled_3.mp3",
  "khaled_blessup2.mp3",
  "khaled_majorkey3.mp3",
  "khaled_Riel_Life.mp3",
  "khaled_theydontwant.mp3",
  "khaled_wethebest.mp3",
  "khaled_youdontlovemenomo.mp3",
  "khaled_anotherone.mp3",
  "djmustard_onthebeat.mp3",
  "djpaul_2.mp3",
  "djpaul_3.mp3",
  "djpaul_9.mp3",
  "dmx_1.mp3",
  "dmx_3.mp3",
  "dmx_6.mp3",
  "dmx_7.mp3",
  "drake_2.mp3",
  "drake_3.mp3",
  "drake_4.mp3",
  "drake_5.mp3",
  "drake_goddamngoddamn.mp3",
  "drake_worst.mp3",
  "drake_yeahyuh3.mp3",
  "drummaboy_1.mp3",
  "e40_1.mp3",
  "e40_2.mp3",
  "eazye_1.mp3",
  "eminem_3.mp3",
  "eminem_4.mp3",
  "fatjoe_1.mp3",
  "fatjoe_9.mp3",
  "fatjoe_5.mp3",
  "fetty_1738.mp3",
  "fetty_hey2.mp3",
  "fetty_yeahbaby.mp3",
  "flava_1.mp3",
  "foxy_yeah.mp3",
  "foxy_unh2.mp3",
  "foxy_heyo.mp3",
  "freeway_1.mp3",
  "french_1.mp3",
  "french_haan.mp3",
  "future_brrr.mp3",
  "future_hendrix.mp3",
  "future_hey.mp3",
  "future_woo.mp3",
  "ghostface_yo.mp3",
  "grandmaster_1.mp3",
  "gucci_1.mp3",
  "gucci_4.mp3",
  "gucci_14.mp3",
  "gucci_8.mp3",
  "gucci_9.mp3",
  "hurricanechris_1.mp3",
  "icecube_1.mp3",
  "inspectahdeck_killahill.mp3",
  "jadakiss_3.mp3",
  "jarule_1.mp3",
  "jarule_2.mp3",
  "jayz_7.mp3",
  "jayz_9.mp3",
  "jayz_1.mp3",
  "jayz5.mp3",
  "jayz7.mp3",
  "jayz8.mp3",
  "jayz_itsthero.mp3",
  "jayz_itsyoboy.mp3",
  "jayz_jiggaman.mp3",
  "jayz_woo.mp3",
  "jayz_yessir.mp3",
  "jayz_young.mp3",
  "jazzypha_1.mp3",
  "jcole_bitch.mp3",
  "jermaine_unh.mp3",
  "jones_8.mp3",
  "jones_14.mp3",
  "juelz_2.mp3",
  "juicyj_1.mp3",
  "juicyj_8.mp3",
  "juicyj_9.mp3",
  "juicyj_10.mp3",
  "juicyj_7.mp3",
  "juicyj_yeahhoe.mp3",
  "kanebeatz_inthebuilding.mp3",
  "kanye_1.mp3",
  "kanye_ouh.mp3",
  "kanye_unh.mp3",
  "kendrick_biatch3.mp3",
  "kendrick_tootoo.mp3",
  "killermike_2.mp3",
  "killermike_3.mp3",
  "krsone_1.mp3",
  "lilb_1.mp3",
  "lilb_2.mp3",
  "liljon_2.mp3",
  "liljon_3.mp3",
  "liljon_8.mp3",
  "liljon_4.mp3",
  "lilkim_queenb.mp3",
  "lilkim_uhunh.mp3",
  "lilscrappy_1.mp3",
  "liluzivert_yah2.mp3",
  "weezy_14.mp3",
  "weezy_22.mp3",
  "weezy_29.mp3",
  "weezy_4.mp3",
  "weezy_16.mp3",
  "weezy_17.mp3",
  "weezy_25.mp3",
  "weezy_30.mp3",
  "weezy_31.mp3",
  "lilyachty_lilboat.mp3",
  "banks_unh.mp3",
  "banks_yeah.mp3",
  "ludacris_2.mp3",
  "ludacris_woo.mp3",
  "madeintyo_skrrskrr.mp3",
  "mannie_1.mp3",
  "mannie_2.mp3",
  "mastaace_awwyeah.mp3",
  "mceiht_1.mp3",
  "mchammer_1.mp3",
  "meek_bow.mp3",
  "meek_unh.mp3",
  "methodman_1.mp3",
  "methodman_tical.mp3",
  "methodman_yo.mp3",
  "metro_somemo.mp3",
  "mikejones_2.mp3",
  "mikejones_iceage.mp3",
  "mikejones_jyeah.mp3",
  "mop_1.mp3",
  "mop_2.mp3",
  "nas_1.mp3",
  "nas_3.mp3",
  "nas_6.mp3",
  "natedogg_1.mp3",
  "nicki_augh.mp3",
  "nicki_cat.mp3",
  "nicki_laugh2.mp3",
  "nicki_ok.mp3",
  "nicki_unh.mp3",
  "nicki_youngmoney.mp3",
  "nore_1.mp3",
  "biggie_1.mp3",
  "biggie_2.mp3",
  "oj_4.mp3",
  "odb_shimmy.mp3",
  "odb_yo.mp3",
  "pharrell_1.mp3",
  "pharrell_2.mp3",
  "pill_1.mp3",
  "pimpc_4.mp3",
  "pimpc_1.mp3",
  "pimpc_sweetjones3.mp3",
  "pitbull_1.mp3",
  "pitbull_2.mp3",
  "pitbull_3.mp3",
  "pitbull_6.mp3",
  "problem_what2.mp3",
  "projectpat_1.mp3",
  "pushat_1.mp3",
  "pushat_haha.mp3",
  "pushat_unh.mp3",
  "pushat_haha3.mp3",
  "quavo_ayy.mp3",
  "quavo_gone2.mp3",
  "quavo_ugh.mp3",
  "quavo_youngnigga.mp3",
  "quavo_migo.mp3",
  "raekwon_yo.mp3",
  "raesremmurd_hay.mp3",
  "redman_heyo.mp3",
  "richhomie_hey.mp3",
  "richhomie_quan.mp3",
  "ross_1.mp3",
  "ross_2.mp3",
  "ross_4.mp3",
  "ross_woo.mp3",
  "schoolboy_yawk.mp3",
  "seanprice_1.mp3",
  "thugga_2.mp3",
  "thugga_3.mp3",
  "snoop_5.mp3",
  "snoop_4.mp3",
  "snoop_1.mp3",
  "soulja_2.mp3",
  "soulja_4.mp3",
  "soulja_5.mp3",
  "swizz_1.mp3",
  "swizz_goddamit.mp3",
  "takeoff_ayy.mp3",
  "takeoff_bitch.mp3",
  "takeoff_graaa.mp3",
  "takeoff_lean.mp3",
  "takeoff_money.mp3",
  "takeoff_takeoff.mp3",
  "takeoff_woo.mp3",
  "takeoff_woowoo.mp3",
  "takeoff_damn.mp3",
  "takeoff_ugh.mp3",
  "ti_5.mp3",
  "ti_2.mp3",
  "ti_3.mp3",
  "ti_22.mp3",
  "ti_32.mp3",
  "tooshort_1.mp3",
  "tpain_2.mp3",
  "tpain1.mp3",
  "trapaholics_1.mp3",
  "trapaholics_damnson.mp3",
  "traviscott_laflame.mp3",
  "traviscott_straightup.mp3",
  "traviscott_yah.mp3",
  "treysongz_4.mp3",
  "treysongz_uhunh.mp3",
  "trick_1.mp3",
  "trick_2.mp3",
  "trick_4.mp3",
  "tydolla_dollasign.mp3",
  "tyga_unh.mp3",
  "vado_1.mp3",
  "waka_1.mp3",
  "waka_8.mp3",
  "willsmith_1.mp3",
  "willsmith_2.mp3",
  "willsmith_3.mp3",
  "wiz_1.mp3",
  "wiz_unh.mp3",
  "yg_skrrt.mp3",
  "yogotti_yeah.mp3",
  "jeezy_10.mp3",
  "jeezy_11.mp3",
  "jeezy_1.mp3",
  "youngthug_boss.mp3",
  "youngthug_ew.mp3",
  "youngthug_git.mp3",
  "youngthug_ish.mp3",
  "youngthug_phoo.mp3",
  "youngthug_wha.mp3"
  ]

  def trackname = [
  "2chainz_4",
  "2chainz_yeah2",
  "2chainz_tellem",
  "2chainz_tru",
  "2chainz_unh3",
  "2chainz_watchout",
  "2chainz_whistle",
  "2pac_4",
  "2pac_5",
  "2pac_6",
  "21savage_21",
  "50_5",
  "50_8",
  "50_11",
  "50cent_2",
  "action_bronsolino",
  "action_yeah",
  "action_yo",
  "actionbronson_istme",
  "actionbronson_unh",
  "akon_1",
  "beanie_mac",
  "bigboi_1",
  "bigl_3",
  "bigl_4",
  "bigl_5",
  "bigsean_boi2",
  "bigsean_doit",
  "bigsean_holdup2",
  "bigsean_ohgod",
  "bigsean_okay",
  "bigsean_stop",
  "bigsean_whoa",
  "bigsean_whoathere",
  "bigsean_unhunh",
  "birdman_4",
  "birdman_10",
  "birdman_16",
  "birdman_1",
  "birdman_6",
  "birdman_birdman",
  "birdman_nonsense",
  "birdman_respeck",
  "bobby_hahaha",
  "bowwow_yeah",
  "bunb_ugk4life",
  "busta_6",
  "busta_1",
  "busta_2",
  "busta_5",
  "camron_1",
  "camron_2",
  "chance_aghh2",
  "chedda_car4",
  "chedda_ugh",
  "keef_bang",
  "keef_catchup",
  "chingy_1",
  "currensy_1",
  "dabrat_comeon",
  "dabrat_lookout",
  "dabrat_oh",
  "danny_stop",
  "danny_yeah",
  "dannybrown_laugh2",
  "danny_check",
  "davidbanner_5",
  "desiigner_pandapanda",
  "desiigner_rahhh",
  "desiigner_yeha",
  "diddy_1",
  "diddy_3",
  "diddy_4",
  "diddy_5",
  "diddy_6",
  "diddy_7",
  "dizzee_1",
  "djdrama_dramatic",
  "djkhaled_2",
  "djkhaled_3",
  "khaled_blessup2",
  "khaled_majorkey3",
  "khaled_Riel_Life",
  "khaled_theydontwant",
  "khaled_wethebest",
  "khaled_youdontlovemenomo",
  "khaled_anotherone",
  "djmustard_onthebeat",
  "djpaul_2",
  "djpaul_3",
  "djpaul_9",
  "dmx_1",
  "dmx_3",
  "dmx_6",
  "dmx_7",
  "drake_2",
  "drake_3",
  "drake_4",
  "drake_5",
  "drake_goddamngoddamn",
  "drake_worst",
  "drake_yeahyuh3",
  "drummaboy_1",
  "e40_1",
  "e40_2",
  "eazye_1",
  "eminem_3",
  "eminem_4",
  "fatjoe_1",
  "fatjoe_9",
  "fatjoe_5",
  "fetty_1738",
  "fetty_hey2",
  "fetty_yeahbaby",
  "flava_1",
  "foxy_yeah",
  "foxy_unh2",
  "foxy_heyo",
  "freeway_1",
  "french_1",
  "french_haan",
  "future_brrr",
  "future_hendrix",
  "future_hey",
  "future_woo",
  "ghostface_yo",
  "grandmaster_1",
  "gucci_1",
  "gucci_4",
  "gucci_14",
  "gucci_8",
  "gucci_9",
  "hurricanechris_1",
  "icecube_1",
  "inspectahdeck_killahill",
  "jadakiss_3",
  "jarule_1",
  "jarule_2",
  "jayz_7",
  "jayz_9",
  "jayz_1",
  "jayz5",
  "jayz7",
  "jayz8",
  "jayz_itsthero",
  "jayz_itsyoboy",
  "jayz_jiggaman",
  "jayz_woo",
  "jayz_yessir",
  "jayz_young",
  "jazzypha_1",
  "jcole_bitch",
  "jermaine_unh",
  "jones_8",
  "jones_14",
  "juelz_2",
  "juicyj_1",
  "juicyj_8",
  "juicyj_9",
  "juicyj_10",
  "juicyj_7",
  "juicyj_yeahhoe",
  "kanebeatz_inthebuilding",
  "kanye_1",
  "kanye_ouh",
  "kanye_unh",
  "kendrick_biatch3",
  "kendrick_tootoo",
  "killermike_2",
  "killermike_3",
  "krsone_1",
  "lilb_1",
  "lilb_2",
  "liljon_2",
  "liljon_3",
  "liljon_8",
  "liljon_4",
  "lilkim_queenb",
  "lilkim_uhunh",
  "lilscrappy_1",
  "liluzivert_yah2",
  "weezy_14",
  "weezy_22",
  "weezy_29",
  "weezy_4",
  "weezy_16",
  "weezy_17",
  "weezy_25",
  "weezy_30",
  "weezy_31",
  "lilyachty_lilboat",
  "banks_unh",
  "banks_yeah",
  "ludacris_2",
  "ludacris_woo",
  "madeintyo_skrrskrr",
  "mannie_1",
  "mannie_2",
  "mastaace_awwyeah",
  "mceiht_1",
  "mchammer_1",
  "meek_bow",
  "meek_unh",
  "methodman_1",
  "methodman_tical",
  "methodman_yo",
  "metro_somemo",
  "mikejones_2",
  "mikejones_iceage",
  "mikejones_jyeah",
  "mop_1",
  "mop_2",
  "nas_1",
  "nas_3",
  "nas_6",
  "natedogg_1",
  "nicki_augh",
  "nicki_cat",
  "nicki_laugh2",
  "nicki_ok",
  "nicki_unh",
  "nicki_youngmoney",
  "nore_1",
  "biggie_1",
  "biggie_2",
  "oj_4",
  "odb_shimmy",
  "odb_yo",
  "pharrell_1",
  "pharrell_2",
  "pill_1",
  "pimpc_4",
  "pimpc_1",
  "pimpc_sweetjones3",
  "pitbull_1",
  "pitbull_2",
  "pitbull_3",
  "pitbull_6",
  "problem_what2",
  "projectpat_1",
  "pushat_1",
  "pushat_haha",
  "pushat_unh",
  "pushat_haha3",
  "quavo_ayy",
  "quavo_gone2",
  "quavo_ugh",
  "quavo_youngnigga",
  "quavo_migo",
  "raekwon_yo",
  "raesremmurd_hay",
  "redman_heyo",
  "richhomie_hey",
  "richhomie_quan",
  "ross_1",
  "ross_2",
  "ross_4",
  "ross_woo",
  "schoolboy_yawk",
  "seanprice_1",
  "thugga_2",
  "thugga_3",
  "snoop_5",
  "snoop_4",
  "snoop_1",
  "soulja_2",
  "soulja_4",
  "soulja_5",
  "swizz_1",
  "swizz_goddamit",
  "takeoff_ayy",
  "takeoff_bitch",
  "takeoff_graaa",
  "takeoff_lean",
  "takeoff_money",
  "takeoff_takeoff",
  "takeoff_woo",
  "takeoff_woowoo",
  "takeoff_damn",
  "takeoff_ugh",
  "ti_5",
  "ti_2",
  "ti_3",
  "ti_22",
  "ti_32",
  "tooshort_1",
  "tpain_2",
  "tpain1",
  "trapaholics_1",
  "trapaholics_damnson",
  "traviscott_laflame",
  "traviscott_straightup",
  "traviscott_yah",
  "treysongz_4",
  "treysongz_uhunh",
  "trick_1",
  "trick_2",
  "trick_4",
  "tydolla_dollasign",
  "tyga_unh",
  "vado_1",
  "waka_1",
  "waka_8",
  "willsmith_1",
  "willsmith_2",
  "willsmith_3",
  "wiz_1",
  "wiz_unh",
  "yg_skrrt",
  "yogotti_yeah",
  "jeezy_10",
  "jeezy_11",
  "jeezy_1",
  "youngthug_boss",
  "youngthug_ew",
  "youngthug_git",
  "youngthug_ish",
  "youngthug_phoo",
  "youngthug_wha"
  ]

  def int index
  if (actionType.equals("Custom Message"))
  {
    if (message) {
      state.sound = textToSpeech(message instanceof List ? message[0] : message) // not sure why this is (sometimes) needed)
    }
    else {
      state.sound = textToSpeech("custom message with no message in the $app.label smart app. please")
    }
  }
  else
  {
    if (actionType.equals("Random")) {
      index = Math.abs(new Random().nextInt() % (trackuri.size()))
    }
    else {
      index = trackname.indexOf(actionType);
    }
    if ((index >= 0) && (index < trackuri.size())) {
      state.sound = [uri:"http://therapboard.com/audio/" + trackuri[index], duration:"4"]
    }
    else {
      state.sound = textToSpeech("You done messed up setting up $app.label smart app")
    }
  }
}
