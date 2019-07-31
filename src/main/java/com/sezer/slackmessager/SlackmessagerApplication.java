package com.sezer.slackmessager;

import com.sezer.slackmessager.websocket.SlackRTM;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

@SpringBootApplication
public class SlackmessagerApplication {

    private static Logger logger = Logger.getLogger(SlackmessagerApplication.class.getName());

    private  static Properties props;

    public static void main(String[] args) {
        SpringApplication.run(SlackmessagerApplication.class, args);
        /** Accesses to properties file */
        try {
            Resource resource = new ClassPathResource("/application.properties");
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            logger.severe("Error accessing properties file, exiting the application");
            System.exit(0);
        }
        /** Initiates connection process to Slack RTM API */
        SlackRTM.rtmConnect();
    }

    /** Returns the value of the property key */
    public static String getPropertyValue(String key){
        return props.getProperty(key);
    }
}
