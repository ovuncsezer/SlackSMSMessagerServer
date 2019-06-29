package com.sezer.slackmessager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SMSMessageStore {

    private static List<String> messages = new ArrayList<>();

    public static void addMessage(String message){
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        messages.add(timestamp + "\n" + message);
    }

    public static String getMessagesAsString(){
        StringBuilder builder = new StringBuilder();

        for(String message : messages){
            builder.append(message + "\n--------------------------\n");
        }

        return builder.toString();
    }
}
