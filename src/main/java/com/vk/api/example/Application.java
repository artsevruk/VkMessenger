package com.vk.api.example;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.Random;


/**
 * Класс для работы с сообщениями в VK
 */
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    private VkApiClient vk;
    private UserActor actor;
    private final Random random = new Random();

    public Application(VkApiClient vk, UserActor actor) {
        this.vk = vk;
        this.actor = actor;
    }

    /**
     * Отправляет сообщение ползователю
     *
     * @param userId      - id пользователя
     * @param textMessage - текст сообщения
     */
    public Integer sendMessage(int userId, String textMessage) {
        try {
            Integer messageId = vk.messages().send(actor).message(textMessage).userId(userId).randomId(random.nextInt()).execute();
            return messageId;
        } catch (ApiException e) {
            LOG.error("INVALID REQUEST", e);
            return null;
        } catch (ClientException e) {
            LOG.error("NETWORK ERROR", e);
            return null;
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        HttpTransportClient client = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(client);


        //267912605
        Application message = new Application(vk, InitVkApi.initActor(InitVkApi.readProperties(InitVkApi.PROPERTIES_FILE)));
        message.sendMessage(267912605, "Hello my friend Artem . .");


    }
}
