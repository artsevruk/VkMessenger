package com.vk.api.example;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiCaptchaException;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.users.UserField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
     * Печатает информацию о пользователе
     *
     * @param userId - id пользователя
     */
    public void printUserInfo(String userId) {
        try {
            LOG.info("printUserInfo: " + vk.users().get(actor).userIds(userId).fields(UserField.CITY).executeAsString());
        } catch (ClientException e) {
        }

    }

    /**
     * Печатает коды стан
     */
    public void printCodesCountries() {
        try {
            LOG.info("printCodesCountries: " + vk.database().getCountries(actor).executeAsString());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * Печатает список городов, по заданной стране
     *
     * @param codeCountry - код страны
     */
    public void printCodesCities(int codeCountry) {
        try {
            LOG.info("printCodesCities: " + vk.database().getCities(actor, codeCountry).executeAsString());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * Печатает заданное количество пользователей по заданному городу
     *
     * @param idCity       - код города
     * @param counterUsers - количство пользователей
     */
    public void printUsersByCity(int idCity, int counterUsers) {
        try {
            LOG.info("printUsersByCity: " + vk.users().search(actor).city(idCity).count(counterUsers).executeAsString());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * Печатает 10 пользователей из города Солигорска
     */
    public void printUsersByCityFromSoligorsk() {
        int idSoligorsk = 2164;
        int counterUsers = 10;
        LOG.info("printUsersByCityFromSoligorsk: ");
        printUsersByCity(idSoligorsk, counterUsers);
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
                timeOut(2000);
            }

        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        HttpTransportClient client = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(client);

        //267912605
        Application application = new Application(vk, InitVkApi.initActor(InitVkApi.readProperties(InitVkApi.PROPERTIES_FILE)));
        //message.sendMessage(267912605, "Hello my friend Artem . .");

        ArrayList<Integer> userId = new ArrayList<Integer>();
        userId.add(267912605);
        userId.add(267912605);
        userId.add(267912605);
        userId.add(267912605);
        userId.add(267912605);
        userId.add(267912605);
        userId.add(267912605);
        userId.add(267912605);
        userId.add(267912605);
        userId.add(267912605);


        application.sendMessages(userId, " test sendMessages ");

        application.printCodesCountries();
        application.printUserInfo("288024722");
        application.printUsersByCityFromSoligorsk();

    }
}
