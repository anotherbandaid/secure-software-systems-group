const mqtt = require('aliyun-iot-mqtt');
// Device identity triplet + area
const options = {
    "productKey": "a1l2QQqA4Mi",
    "deviceName": "Bulb",
    "deviceSecret": "nI4vzrHzDCX7N10jC1mFnhthS9fSSZp8",
    "regionId": "cn-shanghai"
};

// 1. Establish a connection
const client = mqtt.getAliyunIotMqttClient(options);
// 2. Subscribe to topics
setTimeout(function() {
    client.subscribe(`/${options.productKey}/${options.deviceName}/user/get`)
}, 3 * 1000);
// 3. Publish messages
setTimeout(function() {
    client.publish(`/${options.productKey}/${options.deviceName}/user/update`, getPostData(),{qos:1});
}, 5 * 1000);
// 4. Close the connection
setTimeout(function() {
    client.end();
}, 8 * 1000);


function getPostData() {
    const payloadJson = {
        action: "switch",
        status: true
    }
    console.log("payloadJson " + JSON.stringify(payloadJson))
    return JSON.stringify(payloadJson);
}