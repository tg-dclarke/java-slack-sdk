package test_with_remote_apis;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.SlackApiResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.Data;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserDefinedMethodsTest {

    @Data
    public static class MyResponse implements SlackApiResponse {
        private boolean ok;
        private String warning;
        private String error;
        private String needed;
        private String provided;

        private String url;
        private String team;
        private String user;
        private String teamId;
        private String userId;
        private String botId;
    }

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    @Test
    public void runMethods() throws IOException, SlackApiException {
        MyResponse response = slack.methods().postFormWithTokenAndParseResponse(
                f -> f.add("something", "important"), // request body
                "auth.test", // endpoint
                botToken, // xoxb-***
                MyResponse.class // response class
        );
        assertThat(response.getError(), is(nullValue()));
    }
}
