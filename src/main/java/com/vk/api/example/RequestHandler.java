package com.vk.api.example;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RequestHandler {

    private final static String PROPERTIES_FILE = "config.properties";

    public static UserActor initVkApi(Properties properties) {
        int userId = Integer.parseInt(properties.getProperty("userId"));
        String token = properties.getProperty("token");
        if (userId == 0 || token == null) throw new RuntimeException("Params are not set");
        return new UserActor(userId, token);
    }

    public static Properties readProperties() throws FileNotFoundException {
        InputStream inputStream = Application.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        if (inputStream == null)
            throw new FileNotFoundException("property file '" + PROPERTIES_FILE + "' not found in the classpath");

        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Incorrect properties file");
        }
    }

}
