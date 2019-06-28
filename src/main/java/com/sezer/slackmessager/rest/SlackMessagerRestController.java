package com.sezer.slackmessager.rest;


import com.sezer.slackmessager.firebase.FirebaseService;
import com.sezer.slackmessager.websocket.SlackRTM;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class SlackMessagerRestController {

    private static Logger logger = Logger.getLogger(SlackMessagerRestController.class.getName());

    @GetMapping("/hola")
    public String hola() {
        return "Hola a todos!";
    }

    @GetMapping("/registerToken/{token}")
    public String registerToken(@PathVariable("token") String token) {
        FirebaseService.setDeviceToken(token);

        return "OK!";
    }

    @GetMapping("/authKey/{token}")
    public String setAuthKey(@PathVariable("token") String token) {
        FirebaseService.setAuthKeyFcm(token);

        return "OK!";
    }

    @GetMapping("/token")
    public String getToken() {
        return FirebaseService.getDeviceToken();
    }

    @PostMapping(value="/sms", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String sms(@RequestBody String smsMessage) {

        JSONObject sendMessageJSON = new JSONObject();
        try {
            /** Constructs the JSON object to send Slack RTM API*/
            sendMessageJSON.put("id", System.currentTimeMillis());
            sendMessageJSON.put("type", "message");
            sendMessageJSON.put("channel", "CKK9UJT09");
            sendMessageJSON.put("text", new JSONObject(smsMessage).getString("message"));
        } catch (JSONException e) {
            logger.severe("Error on building slack JSON: " + e.getMessage());
            return "NOT OK!";
        }

        /** Send SMS message to Slack RTM API via websocket */
        SlackRTM.sendToSocket(sendMessageJSON.toString());

        return "OK!";
    }

    @PostMapping(value="/notif", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String sendNotif(@RequestBody String message) {
        FirebaseService.sendPushNotification(new JSONObject(message).getString("message"));
        return "OK!";
    }

    @GetMapping("/connect")
    public String connect() {
        SlackRTM.reconnect();

        return "OK!";
    }
}
