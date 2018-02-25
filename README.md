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

## usage
1. @EnableGjall(it makes to work default setting) with @Configuration and add [Before/After]RequestLoggingHandler bean
1. or make class extends GjallConfigurerAdapter to Spring Bean and configure like below

```java
@Configuration
public class GjallConfig extends GjallConfigurerAdapter {

    @Override
    public void configure(GjallConfigurerBuilder gjall) {
        gjall
                .beforeHandler((httpServletRequest, apiLog) -> {
                    // implements Actions you want to do BEFORE REQUEST
                    // OR object that implements  GjallBeforeRequestHandler - default SimpleGjallBeforeRequestHandler
                })
                .afterHandler((httpServletRequest, httpServletResponse, apiLog) -> {
                    // implements Actions you want to do AFTER REQUEST
                    // OR object that implements  GjallAfterRequestHandler - default SimpleGjallAfterRequestHandler
                })
                .request()
                    .includeHeaders(true)   // Include Request Header - default false
                    .payloadSize(1000)      // Include Request Payload(Request Body). if set 0, payload not logging - default 0
                    .and()
                .response()
                    .includeHeaders(true)   // Include Response Header - default false
                    .payloadSize(3000)      // Include Response Payload(Response Body). if set 0, payload not logging - default 0
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