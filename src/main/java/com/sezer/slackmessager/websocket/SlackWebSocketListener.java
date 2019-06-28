package com.sezer.slackmessager.websocket;

import com.sezer.slackmessager.firebase.FirebaseService;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

/**
 * @author ovuncsezer on 20/06/2019
 *
 * WebSocketListener to handle websocket operations such as inital connection
 * or receiveing new messages from socket
 */
public class SlackWebSocketListener extends WebSocketListener {


    private static Logger logger = Logger.getLogger(SlackWebSocketListener.class.getName());

    private static final int NORMAL_CLOSURE_STATUS = 1000;
    public static boolean is_disconnected = false;

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        logger.info("Websocket connection is established!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        logger.info("Received:" + text);

        try {
            JSONObject eventJSON = new JSONObject(text);
            if (eventJSON.has("type") && eventJSON.get("type").equals("message")){
                logger.info("New Message Received:" + text);
                String messageBody = eventJSON.getString("text");
                FirebaseService.sendPushNotification(messageBody);
            }
        } catch (JSONException e) {
            logger.severe("Error converting action to JSON: " + e.getMessage());
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        logger.info("Received bytes : " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        logger.info("Closing socket: " + code + " / " + reason);
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        logger.severe("Error : " + t.getMessage());
        is_disconnected = true;
    }
}
