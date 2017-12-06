package com.vk.api.example;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.Actor;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiCaptchaException;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.responses.GetMembersResponse;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import com.vk.api.sdk.queries.users.UserField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Класс для работы с сообщениями в VK
 */
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    private VkApiClient vk;
    private GroupActor actor;
    private final Random random = new Random();

    public Application(VkApiClient vk, GroupActor actor) {
        this.vk = vk;
        this.actor = actor;
    }

    /**
     * Метод делает паузу
     *
     * @param milli - время, в миллисекундах
     */
    private void timeOut(long milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Отправляет сообщение ползователю
     *
     * @param userId      - id пользователя
     * @param textMessage - текст сообщения
     * @return - id отправленного сообщения
     */
    public Integer sendMessage(int userId, String textMessage) {


        try {
            return vk.messages().send(actor).message(textMessage).userId(userId).randomId(random.nextInt()).execute();
        } catch (ApiCaptchaException e) {
            LOG.error("Captcha needed", e);
            LOG.info("captcha_sid: " + e.getSid());
            LOG.info("captcha_img: " + e.getImage());
            try {
                timeOut(20000);
                return vk.messages().send(actor).message(textMessage).userId(userId).randomId(random.nextInt()).captchaKey("�").captchaSid(e.getSid()).execute();//-&#00000  �
            } catch (ApiException ex) {
                LOG.error("Invalid request", ex);
                return null;
            } catch (ClientException ex) {
                LOG.error("Network error", ex);
                return null;
            }
        } catch (ApiException e) {
            LOG.error("Invalid request", e);
            return null;
        } catch (ClientException e) {
            LOG.error("Network error", e);
            return null;
        }
    }

    /**
     * Отправляет сообщения всем пользователям в списке
     *
     * @param usersId     - список id пользователей
     * @param textMessage - текст сообщения
     */
    public void sendMessages(ArrayList<Integer> usersId, String textMessage) {
        if (usersId.size() > 0) {
            for (int i = 0; i < usersId.size(); i++) {
                sendMessage(usersId.get(i), textMessage);
                LOG.info("Message sended - " + i);
                timeOut(500);
            }

        }
    }


    public static void main(String[] args) throws FileNotFoundException, ApiException, ClientException {
        HttpTransportClient client = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(client);

        //267912605
        //Application application = new Application(vk, InitVkApi.initActor(InitVkApi.readProperties(InitVkApi.PROPERTIES_FILE)));
        //application.sendMessage(267912605, "Hello my friend Artem . .");

        ArrayList<Integer> userIds = new ArrayList<Integer>();
        userIds.add(267912605);
        userIds.add(454424913);




        //application.sendMessages(userIds, " test sendMessages ");

        //application.printCodesCountries();
        //application.printUserInfo("288024722");
        //application.printUsersByCityFromSoligorsk();

        Random random = new Random();
        GroupActor group = new GroupActor(158061370, "0a9d87eb106a60d09a376cb55afbb1a25d7859283afea6a39f4558f5e49eda94b8d005e0b0f99a4be7790");

        Application application = new Application(vk, group);

        GetMembersResponse members = application.vk.groups().getMembers(group).groupId("158061370").execute();

        System.out.println("--------3-------"+ members);


        //application.sendMessages((ArrayList<Integer>) members.getItems()," 1 Test messages ");




    }
}
