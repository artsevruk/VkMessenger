import com.vk.api.example.Application;
import com.vk.api.example.InitVkApi;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.testng.Assert;

import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ApplicationTest {
    private HttpTransportClient client = new HttpTransportClient();
    private VkApiClient vk = new VkApiClient(client);
    //private Application application = new Application(vk, InitVkApi.initActor(InitVkApi.readProperties(InitVkApi.PROPERTIES_FILE)));
    private ArrayList<Integer> userId = new ArrayList<Integer>();



    public ApplicationTest() throws FileNotFoundException {
    }

    @Test
    public void sendedMessageSuccessfully() {
        //Assert.assertNotNull(application.sendMessage(267912605, "Hello my friend Artem . ."));
    }

    @Test
    public void sendedMessageNotSuccessfully() {
        //Assert.assertNull(application.sendMessage(3, "Hello my friend Artem . ."));
    }

}
