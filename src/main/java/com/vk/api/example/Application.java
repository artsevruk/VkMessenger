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

public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    private final VkApiClient apiClient;
    private final UserActor actor;
    private final Random random = new Random();

    public Application(VkApiClient apiClient, UserActor actor) {
        this.apiClient = apiClient;
        this.actor = actor;
    }

    public void sendMessage(int userId, String textMessage) {
        try {
            apiClient.messages().send(actor).message(textMessage).userId(userId).randomId(random.nextInt()).execute();
        } catch (ApiException e) {
            LOG.error("INVALID REQUEST", e);
        } catch (ClientException e) {
            LOG.error("NETWORK ERROR", e);
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        //267912605


        HttpTransportClient client = new HttpTransportClient();
        VkApiClient apiClient = new VkApiClient(client);

        UserActor actor = RequestHandler.initVkApi(RequestHandler.readProperties());

        Application application = new Application(apiClient,actor);
        application.sendMessage(267912605, "Hello my friend Artem . .");





    }
}
