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
 *
 */
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    private VkApiClient vk;
    private UserActor actor;
    private final Random random = new Random();

    public Application(VkApiClient vk, UserActor actor){
        this.vk = vk;
        this.actor = actor;
    }

    /**
     * Отправляет сообщение ползователю
     *
     * @param userId      - id пользователя
     * @param textMessage - текст сообщения
     */
    public void sendMessage(int userId, String textMessage) {
        try {
            vk.messages().send(actor).message(textMessage).userId(userId).randomId(random.nextInt()).execute();
        } catch (ApiException e) {
            LOG.error("INVALID REQUEST", e);
        } catch (ClientException e) {
            LOG.error("NETWORK ERROR", e);
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        HttpTransportClient client = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(client);


        //267912605
        Application application = new Application(vk, InitVkApi.initActor(InitVkApi.readProperties(InitVkApi.PROPERTIES_FILE)));
        application.sendMessage(3, "Hello my friend Artem . .");


    }
}
