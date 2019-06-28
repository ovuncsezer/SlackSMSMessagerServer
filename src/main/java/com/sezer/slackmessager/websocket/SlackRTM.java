package com.sezer.slackmessager.websocket;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

public class SlackRTM {

    private static final Logger logger = Logger.getLogger(SlackRTM.class.getName());

    /** Websocket client */
    private static OkHttpClient client;
    /** Websocket Listener */
    private static SlackWebSocketListener listener;
    /** Websocket object returned after connection is established */
    private static WebSocket webSocket;
    /** Websocket URL to be connect */
    private static String wsURL;

    /** Sends initial REST request to connect Slack RTM API */
    public static void rtmConnect(){
        final String uri = "https://slack.com/api/rtm.connect?token=xoxb-507116504512-659844106642-Av1LeR7MKw9hIpOb84qaKjni";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        logger.info(result);

        JSONObject json = new JSONObject(result);
        wsURL = (String) json.get("url");
        connect();
    }

    /** Opens a connection to Slack RTM API via websocket */
    public static void connect(){
        logger.info("Connecting to Slack RTM API via websocket...");
        client = new OkHttpClient();
        listener = new SlackWebSocketListener();

        Request request = new Request.Builder().url(wsURL).build();
        webSocket = client.newWebSocket(request, listener);
    }

    /** Sends the text data to Slack RTM API through websocket
     * @param json has to be a String object of the json data with keys below
     * "id": unique id of the message to be sent
     * "type": has to be "message" for chat messages
     * "channel": unique ID of the slack channel that will receive the message
     * "text": the text value of the message to be sen */
    public static void sendToSocket(String json){
        if( webSocket != null){
            webSocket.send(json);
        }
    }

    public static void reconnect(){
        webSocket.close(0, "closed");
        rtmConnect();
    }
}
