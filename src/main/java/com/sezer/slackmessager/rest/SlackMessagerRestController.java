package com.sezer.slackmessager.rest;


import com.sezer.slackmessager.SMSMessageStore;
import com.sezer.slackmessager.SlackmessagerApplication;
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

    @GetMapping("/")
    public String hola() {
        return "Hola a todos!";
    }

    @GetMapping("/registerToken/{token}")
    public String registerToken(@PathVariable("token") String token) {
        FirebaseService.setDeviceToken(token);

        return "OK!";
    }

    @GetMapping("/token")
    public String getToken() {
        return FirebaseService.getDeviceToken();
    }

    @GetMapping("/authKey/{token}")
    public String setAuthKey(@PathVariable("token") String token) {
        FirebaseService.setAuthKeyFcm(token);

        return "OK!";
    }

    @GetMapping("/authKey")
    public String getAuthKey() {
        return FirebaseService.getAuthKey();
    }

    @GetMapping("/slackToken/{token}")
    public String setSlackToken(@PathVariable("token") String token) {
        SlackRTM.setSlackToken(token);

        return "OK!";
    }

    @GetMapping("/slackToken")
    public String getSlackToken() {
        return SlackRTM.getSlackToken();
    }

    @PostMapping(value="/sms", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String sms(@RequestBody String smsMessage) {

        String channel = SlackmessagerApplication.getPropertyValue("slack_channel_id");
        /** Parses request to get message sent by SMS */
        JSONObject requestBody = new JSONObject(smsMessage);
        String message = requestBody.getString("message");
        if (requestBody.has("channel")){
            channel = requestBody.getString("channel");
        }

        /** Constructs JSON Object to send Slack through RTM API */
        JSONObject sendMessageJSON = new JSONObject();
        try {
            /** Constructs the JSON object to send Slack RTM API*/
            sendMessageJSON.put("id", System.currentTimeMillis());
            sendMessageJSON.put("type", "message");
            sendMessageJSON.put("channel", channel);
            sendMessageJSON.put("text", message);
        } catch (JSONException e) {
            logger.severe("Error on building slack JSON: " + e.getMessage());
            return "NOT OK!";
        }

        /** Send SMS message to Slack RTM API via websocket */
        SlackRTM.sendToSocket(sendMessageJSON.toString());
        SMSMessageStore.addMessage(message);

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

    @GetMapping("/messages")
    public String getMessages() {
        return SMSMessageStore.getMessagesAsString();
    }
}
