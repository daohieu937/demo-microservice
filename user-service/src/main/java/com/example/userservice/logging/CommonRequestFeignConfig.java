package com.example.userservice.logging;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 *
 * @author HuyHV
 *
 * CommonRequestFeignConfig
 * Auto set request_id to all request with Feign
 * How to use:
 *
 * @SpringBootApplication
 * @EnableFeignClients(defaultConfiguration= {CommonRequestFeignConfig.class})
 * public class Application {
 *
 * }
 *
 */
public class CommonRequestFeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String requestId = RequestUtils.currentRequestId();
        requestTemplate.header(RequestUtils.HEADER_REQUEST_ID, requestId);
    }
}
