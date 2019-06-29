package com.sezer.slackmessager.firebase;

import com.sezer.slackmessager.rest.SlackMessagerRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

public class FirebaseService {

    private static Logger logger = Logger.getLogger(SlackMessagerRestController.class.getName());

    private static String authKeyFcm = "AAAAfDbNb8w:APA91bGQevkUa18jVfZzN0ZrJNd3obTpOSlXSGHWbSCK5FNg" +
            "295BRohfcjvIvY82Nt5rTz5n6MULWZ0BnUA-wX2UJeoDUcYAaudceuaWzly0rwJo3X6UHka8AmzHjSKiEAHnIxy0YfAj";

    private static String deviceToken = "dKUqPezntKA:APA91bGGCdaMp-j5FG3Snc53kGhZCKO2LU4BRTgKL2MsVyzdQQ7HSsfR9-" +
            "lVE9PR4I8qwCMXjSlToZ3tSRoQmDIFcyFJ8ACp7yYFrgnJ1KmMwfbzNNSJF2YlXMAxoQTydABx1UezmkP7";


    public static void sendPushNotification(String message){

        logger.info("Sending push notification...");
        final String uri = "https://fcm.googleapis.com/fcm/send";
        String requestJson =   "{\"data\": {\"message\": \"" + message + "\"}," +
                "\"registration_ids\": [\"" + deviceToken + "\"]}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "key=" + authKeyFcm);
        logger.info(requestJson);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        String result = restTemplate.postForObject(uri, entity, String.class);
        logger.info(result);
    }

    public static String getDeviceToken(){
        return deviceToken;
    }
    public static String getAuthKey(){
        return authKeyFcm;
    }

    public static void setDeviceToken(String token){
        logger.info("Device token set to " + token);
        deviceToken = token;
    }

    public static void setAuthKeyFcm(String authKey){
        logger.info("Auth key set to " + authKey);
        authKeyFcm = authKey;
    }
}
