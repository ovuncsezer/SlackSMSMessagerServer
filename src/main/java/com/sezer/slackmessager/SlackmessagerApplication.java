package com.sezer.slackmessager;

import com.sezer.slackmessager.websocket.SlackRTM;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SlackmessagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlackmessagerApplication.class, args);
        SlackRTM.rtmConnect();
    }
}
