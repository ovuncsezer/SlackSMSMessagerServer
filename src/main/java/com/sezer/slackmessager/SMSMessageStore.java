package com.sezer.slackmessager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ovuncsezer
 *
 * Class keeps messages received from SMS throughtout the lifecycle of the application
 */
public class SMSMessageStore {

    private static List<String> messages = new ArrayList<>();

    /** Adds newly received SMS message to the list */
    public static void addMessage(String message){
        String timestamp = new Timestamp(System.currentTimeMillis()).toString();
        messages.add(timestamp + "\n" + message);
    }

    /** Concatenates message to print via REST call */
    public static String getMessagesAsString(){
        StringBuilder builder = new StringBuilder();

        for(String message : messages){
            builder.append(message + "\n--------------------------\n");
        }

        return builder.toString();
    }
}
