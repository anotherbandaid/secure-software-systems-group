/**
 *  Aquarium
 *
 *  Author: ronnycarr@gmail.com
 *  Date: 2014-03-22
 */


 // for the UI
metadata {
	// Automatically generated. Make future change here.
	definition (name: "AquaMonitor", author: "ronnycarr@gmail.com") {
		capability "Image Capture"
		capability "Polling"
    capability "Temperature Measurement"
		capability "Sensor"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
    	carouselTile("camera", "device.image", width: 3, height: 2) {

    	}
        standardTile("take", "device.image", width: 1, height: 1, canChangeIcon: false, inactiveLabel: true, canChangeBackground: false, decoration: "flat") {
      		state "take", label: "", action: "Image Capture.take", icon: "st.secondary.take"
    	}
        valueTile("temperature", "device.temperature", inactiveLabel: true) {
        	state "default", label:'${currentValue}°', unit:"F",
            backgroundColors:[
            	[value: 77, color: "#bc2323"],
                [value: 78, color: "#f1d801"],
                [value: 80, color: "#44b621"],
                [value: 82, color: "#f1d801"],
                [value: 83, color: "#bc2323"]
            ]
        }
      standardTile("refresh", "device.motionAlarm", inactiveLabel: false, decoration: "flat") {
          state "default", action:"polling.poll", icon:"st.secondary.refresh"
      }

    	main "temperature"
    	details(["camera", "take", "temperature", "refresh"])
	}
}

private take(){

  log.debug "taking photo"
  action("/api/cam.jpg", "GET", def options = [outputMsgToS3:true])
}

def parseCameraResponse(def response) {
  if (response.headers.'Content-Type'.contains("image/jpeg")) {
    def imageBytes = response.data
    if (imageBytes) {
      storeImage(getPictureName(), imageBytes)
    }
  }
}

def temperature() {
	log.debug "getting temperature"

  action("/api/get.php?ref=current_water_temp&opt=F", "GET", "")
}

private action(uri, type, options){

  def hubAction = new physicalgraph.device.HubAction(
    method: type,
    path: uri,
    headers: [HOST:getHostAddress()]
  )
  
  if(options){
    hubAction.options = options
  }

  hubAction
}


def poll() {

	temperature()
}

def parse(String description) {
  log.debug("Parsing '${description}'")

  def map = stringToMap(description)

  if (map.bucket && map.key) { //got a s3 pointer
    putImageInS3(map)
  }
  else{

    def headerString = new String(map.headers.decodeBase64())
    def bodyString = new String(map.body.decodeBase64())

    sendEvent(name: "temperature", value: bodyString, unit: "F")
  }
}

def putImageInS3(map) {
  def s3ObjectContent
  try {
    def imageBytes = getS3Object(map.bucket, map.key + ".jpg")

    if(imageBytes) {
      s3ObjectContent = imageBytes.getObjectContent()
      def bytes = new ByteArrayInputStream(s3ObjectContent.bytes)
      storeImage(getPictureName(), bytes)
    }
  }
  catch(Exception e) {
    log.error e
  }

  finally {
    //explicitly close the stream
    if (s3ObjectContent) { s3ObjectContent.close() }
  }
}

private getPictureName() {
  def pictureUuid = java.util.UUID.randomUUID().toString().replaceAll('-', '')
  return device.deviceNetworkId + "_$pictureUuid" + ".jpg"
}

private Integer convertHexToInt(hex){
  Integer.parseInt(hex,16)
}

private String convertHexToIP(hex){
  [convertHexToInt(hex[0..1]),convertHexToInt(hex[2..3]),convertHexToInt(hex[4..5]),convertHexToInt(hex[6..7])].join(".")
}

private getHostAddress(){
  def parts = device.deviceNetworkId.split(":")
  def ip = convertHexToIP(parts[0])
  def port = convertHexToInt(parts[1])
  return ip + ":" + port
}
