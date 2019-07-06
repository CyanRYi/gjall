package tech.sollabs.gjall;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.sollabs.gjall.annotation.EnableApiLogging;
import tech.sollabs.gjall.configurer.ApiLoggingConfigurerAdapter;
import tech.sollabs.gjall.configurer.ApiLoggingConfigurerBuilder;
import tech.sollabs.gjall.handlers.AfterRequestLoggingHandler;
import tech.sollabs.gjall.handlers.BeforeRequestLoggingHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CustomGjallConfigurationTest.Config.class)
public class CustomGjallConfigurationTest {

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

    @After
    public void tearDown() {
        reset(beforeHandler, afterHandler);
    }

    /**
     * Logging test of POST request for full Configuration
     *
     * in includes uri, method, client information, headers and body when before request
     * and status code, headers and body when after request
     */
    @Test
    public void testPostRequestLog() throws Exception {

        mvc.perform(post("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\" : \"value\"}"));

        verify(beforeHandler).handleLog(any(HttpServletRequest.class), beforeLog.capture());
        Assert.assertEquals("/test", beforeLog.getValue().getUri());
        Assert.assertEquals("POST", beforeLog.getValue().getMethod());
        Assert.assertEquals("127.0.0.1", beforeLog.getValue().getClientInfo().getClient());
        Assert.assertNull(beforeLog.getValue().getClientInfo().getSessionId());
        Assert.assertNull(beforeLog.getValue().getClientInfo().getUser());
        Assert.assertEquals("{Content-Type=[application/json;charset=ISO-8859-1], Content-Length=[17]}", beforeLog.getValue().getRequestHeader());
        Assert.assertEquals("{\"key\" : \"value\"}", beforeLog.getValue().getRequestBody());

        verify(afterHandler).handleLog(any(HttpServletRequest.class), any(HttpServletResponse.class), afterLog.capture());
        Assert.assertEquals(HttpStatus.OK, afterLog.getValue().getHttpStatus());
        Assert.assertEquals("{Content-Type=[text/plain;charset=ISO-8859-1], Content-Length=[26]}", afterLog.getValue().getResponseHeader());
        Assert.assertEquals("{\"result\" : \"resultValue\"}", afterLog.getValue().getResponseBody());

        Assert.assertNotNull(beforeLog.getValue().getId());
        Assert.assertEquals(beforeLog.getValue().getId(), afterLog.getValue().getId());
    }

    /**
     * Logging test of GET request for full Configuration
     *
     * in includes uri(with querystring), method, client information and headers when before request
     * and status code, headers and body when after request
     */
    @Test
    public void testGetRequestLog() throws Exception {

        mvc.perform(get("/test?query=param")
                .contentType(MediaType.APPLICATION_JSON));

        verify(beforeHandler).handleLog(any(HttpServletRequest.class), beforeLog.capture());
        Assert.assertEquals("/test?query=param", beforeLog.getValue().getUri());
        Assert.assertEquals("GET", beforeLog.getValue().getMethod());
        Assert.assertEquals("127.0.0.1", beforeLog.getValue().getClientInfo().getClient());
        Assert.assertNull(beforeLog.getValue().getClientInfo().getSessionId());
        Assert.assertNull(beforeLog.getValue().getClientInfo().getUser());
        Assert.assertEquals("{Content-Type=[application/json;charset=ISO-8859-1]}", beforeLog.getValue().getRequestHeader());
        Assert.assertNull(beforeLog.getValue().getRequestBody());

        verify(afterHandler).handleLog(any(HttpServletRequest.class), any(HttpServletResponse.class), afterLog.capture());
        Assert.assertEquals(HttpStatus.OK, afterLog.getValue().getHttpStatus());
        Assert.assertEquals("{Content-Type=[text/plain;charset=ISO-8859-1], Content-Length=[26]}", afterLog.getValue().getResponseHeader());
        Assert.assertEquals("{\"result\" : \"resultValue\"}", afterLog.getValue().getResponseBody());

        Assert.assertNotNull(beforeLog.getValue().getId());
        Assert.assertEquals(beforeLog.getValue().getId(), afterLog.getValue().getId());
    }

    @EnableApiLogging
    static class Config extends ApiLoggingConfigurerAdapter {

        @Override
        public void configure(ApiLoggingConfigurerBuilder configurerBuilder) {
            configurerBuilder
                    .request()
                    .includeHeaders(true)   // Include Request Header - default false
                    .payloadSize(1000)      // Include Request Payload(Request Body). if set 0, payload not logging - default 0
                .and()
                    .response()
                    .includeHeaders(true)   // Include Response Header - default false
                    .payloadSize(3000)      // Include Response Payload(Response Body). if set 0, payload not logging - default 0
                    .includeStatusCode(true)    // Include Response Status - default false
                .and()
                    .includeClientInfo(true)    // enable user ip address, userId, session id Logging - default false
                    .includeQueryString(true);  // uri include query string - default true
        }

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
        public String home(@RequestBody(required = false) String requestBody) {
            return "{\"result\" : \"resultValue\"}";
        }
    }
}
