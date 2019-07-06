package tech.sollabs.gjall;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.sollabs.gjall.annotation.EnableApiLogging;
import tech.sollabs.gjall.handlers.AfterRequestLoggingHandler;
import tech.sollabs.gjall.handlers.BeforeRequestLoggingHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultGjallConfigurationTest.Config.class)
public class DefaultGjallConfigurationTest {

    @Autowired
    private GjallRequestLoggingFilter gjallFilter;

    private MockMvc mvc;

    @Autowired
    private BeforeRequestLoggingHandler beforeHandler;

    @Autowired
    private AfterRequestLoggingHandler afterHandler;

    private ArgumentCaptor<ApiLog> beforeLog = ArgumentCaptor.forClass(ApiLog.class);
    private ArgumentCaptor<ApiLog> afterLog = ArgumentCaptor.forClass(ApiLog.class);

    @Before
    public void setup() {
        this.mvc = standaloneSetup(new SimpleController())
                .addFilter(gjallFilter).build();
    }

    /**
     * Simple Request Logging test for default Configuration.
     *
     * it includes only uri and method when before request.
     */
    @Test
    public void testRequestLog() throws Exception {

        mvc.perform(post("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\" : \"value\"}"));

        verify(beforeHandler).handleLog(any(HttpServletRequest.class), beforeLog.capture());
        Assert.assertNotNull(beforeLog.getValue().getId());
        Assert.assertEquals("/test", beforeLog.getValue().getUri());
        Assert.assertEquals("POST", beforeLog.getValue().getMethod());
        Assert.assertNull(beforeLog.getValue().getClientInfo().getClient());
        Assert.assertNull(beforeLog.getValue().getClientInfo().getSessionId());
        Assert.assertNull(beforeLog.getValue().getClientInfo().getUser());
        Assert.assertNull(beforeLog.getValue().getRequestHeader());
        Assert.assertNull(beforeLog.getValue().getRequestBody());

        verify(afterHandler, never()).handleLog(any(HttpServletRequest.class), any(HttpServletResponse.class), afterLog.capture());
    }

    @EnableApiLogging
    static class Config {

        @Bean
        public BeforeRequestLoggingHandler beforeRequestLoggingHandler() {
            return mock(BeforeRequestLoggingHandler.class);
        }

        @Bean
        public AfterRequestLoggingHandler afterRequestLoggingHandler() {
            return mock(AfterRequestLoggingHandler.class);
        }
    }

    @RestController
    private static class SimpleController {

        @RequestMapping("/test")
        public String home(@RequestBody String requestBody) {
            return "{\"result\" : \"resultValue\"}";
        }
    }
}
