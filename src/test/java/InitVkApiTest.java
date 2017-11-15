import com.vk.api.example.Application;
import com.vk.api.example.InitVkApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import java.util.Properties;

public class InitVkApiTest {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);


    @Test
    public void userIdIsNullinitActorInitVkApi() {
        Properties properties = new Properties();
        properties.setProperty("userId", "0");
        try {
            Assert.assertNull(InitVkApi.initActor(properties));
        } catch (RuntimeException e) {
            LOG.info("Parameters are not set");
        }
    }

    @Test
    public void tokenIsNullInitVkApi() {
        Properties properties = new Properties();
        properties.setProperty("userId", "2131434");
        try {
            Assert.assertNull(InitVkApi.initActor(properties));
        } catch (RuntimeException e) {
            LOG.info("Parameters are not set");
        }
    }

    @Test
    public void parametrsIsNotNullInitVkApi() {
        Properties properties = new Properties();
        properties.setProperty("userId", "13546576");
        properties.setProperty("token", "461a8b6239511a6673c1aa");
        Assert.assertNotNull(InitVkApi.initActor(properties));
    }


    @Test
    public void readProretiesIsNull() {
        try {
            Assert.assertNull(InitVkApi.readProperties("fake.properties"));
        } catch (FileNotFoundException e) {
            LOG.info("property file not found in the classpath");
        }
    }

    @Test
    public void readProretiesIsNotNull() throws FileNotFoundException {
        Assert.assertNotNull(InitVkApi.readProperties(InitVkApi.PROPERTIES_FILE));
    }

    @Test
    public void shouldBeConformityreadPropertiesTest() throws FileNotFoundException {
        Assert.assertEquals(InitVkApi.readProperties(InitVkApi.PROPERTIES_FILE).getProperty("userId"), "6239253");
        Assert.assertEquals(InitVkApi.readProperties(InitVkApi.PROPERTIES_FILE).getProperty("token"), "dad461a8b6239511a6673c1a7e42a7f28dece0ddaba962062e131b6dc36dcabbe9b282e19db76edf25076");
    }

    @Test
    void shouldBeNotConformityReadPropertiesTest() throws FileNotFoundException {
        Assert.assertNotEquals(InitVkApi.readProperties(InitVkApi.PROPERTIES_FILE).getProperty("userId"), "232321");
        Assert.assertNotEquals(InitVkApi.readProperties(InitVkApi.PROPERTIES_FILE).getProperty("token"), "dad461a8b6239511a6673c1aa7f28dece0ddaba962062e131b6dc36dcabbe9b282e19db76edf25076");
    }

}
