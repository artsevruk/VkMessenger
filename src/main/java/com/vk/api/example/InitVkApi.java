package com.vk.api.example;

import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс для инициализации API Вконтакте
 */

public class InitVkApi {

    public final static String PROPERTIES_FILE = "config.properties";

    /**
     * Инициализируем, авторизируем сообщество
     *
     * @param properties
     * @return UserActor - авторизованый пользователь, от имени которого будет использоваться API
     */
    public static GroupActor initActor(Properties properties) {
        int groupId = Integer.parseInt(properties.getProperty("groupId"));
        String groupToken = properties.getProperty("groupToken");
        if (groupId == 0 || groupToken == null) throw new RuntimeException("Parametors are not set");
        return new GroupActor(groupId, groupToken);
    }

    /**
     * Считываем данные из файла "config.properties"
     *
     * @return Properties
     * @throws FileNotFoundException
     */
    public static Properties readProperties(String propertiesFile) throws FileNotFoundException {
        InputStream inputStream = InitVkApi.class.getClassLoader().getResourceAsStream(propertiesFile);
        if (inputStream == null)
            throw new FileNotFoundException("property file '" + propertiesFile + "' not found in the classpath");

        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Incorrect properties file");
        }
    }

}
