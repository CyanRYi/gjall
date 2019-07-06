# gjall
Gjall - Spring Extension 2nd Series. API Logging.<br/>
Gjall(_Gjallarhorn_) name stemmed from the Norse Mythology. - [wikipedia](https://en.wikipedia.org/wiki/Gjallarhorn)

## Requirement

* spring-web : 4.2.0-RELEASE
* servlet-api : 3.0.1

API Logging is a important issue in modern web environment reasons below.
* communicate with front-end developers more easier
* can find illegal access patterns

Spring framework provides AbstractRequestLoggingFilter since 1.2.5<br/>
and ContentCachingRequest(Response)Wrapper since 4.1.3<br/>
but we need more information in our products.

gjall defines its own goal - doing simply, get powerful API logging.

### time goes by...
But now, we can use httptrace endpoint of [actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html) over Spring Boot 2.   
httptrace endpoint includes almost all of feature of gjall, and provide more convenient and stable.  

We recommend gjall to users like below
- still use Spring 4.2.x
- can not use Spring Boot2 for various reasons
- need hard request/response logging includes **BODY** for various reasons

**but, always be careful to use BODY logging**

## usage
1. add @EnableApiLogging(and add [Before/After]RequestLoggingHandler bean if you need)
1. or make class extends ApiLoggingConfigurerAdapter to Spring Bean and configure like below

```java
@Configuration
public class GjallConfig extends ApiLoggingConfigurerAdapter {
                         
    @Override
    public void configure(ApiLoggingConfigurerBuilder configurerBuilder) {
        configurerBuilder
                .beforeHandler((httpServletRequest, apiLog) -> {
                    // implements Actions you want to do BEFORE REQUEST
                    // OR make Spring Bean type of BeforeRequestLoggingHandler 
                })
                .afterHandler((httpServletRequest, httpServletResponse, apiLog) -> {
                    // implements Actions you want to do AFTER REQUEST
                    // OR make Spring Bean type of AfterRequestLoggingHandler
                })
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
}
```

## Source Code Repository
(https://github.com/CyanRYi/gjall)

## License
MIT

### Contributor
@Cyan Raphael Yi