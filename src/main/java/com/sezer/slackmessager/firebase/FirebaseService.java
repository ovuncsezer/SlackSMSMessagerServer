package com.sezer.slackmessager.firebase;

import com.sezer.slackmessager.SlackmessagerApplication;
import com.sezer.slackmessager.rest.SlackMessagerRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

/**
 * @author ovuncsezer
 *
 * Class handles Firebase operations.
 *
 * Sends push notifications to the Android device and
 * stores necessary information regarding notificaion operations
 */
public class FirebaseService {

    private static Logger logger = Logger.getLogger(SlackMessagerRestController.class.getName());

    /** Firebase Authorization token */
    private static String authKeyFcm = SlackmessagerApplication.getPropertyValue("authKeyFcm");
    /** Token of the Android device */
    private static String deviceToken = SlackmessagerApplication.getPropertyValue("deviceToken");


    /**
     * Sends the push notification to device with the message given
     * @param message Message is the Slack message read through Slack RTM.
     */
    public static void sendPushNotification(String message){

        logger.info("Sending push notification...");
        final String uri = SlackmessagerApplication.getPropertyValue("firebase_uri");
        String requestJson =   "{\"data\": {\"message\": \"" + message + "\"}," +
                "\"registration_ids\": [\"" + deviceToken + "\"]}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "key=" + authKeyFcm);
        logger.info(requestJson);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        /** Sends POST request to firebase server in order to send notification */
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
