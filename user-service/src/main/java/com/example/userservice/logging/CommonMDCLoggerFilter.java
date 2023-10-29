package com.example.userservice.logging;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author HuyHV
 *
 * MDC add param
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class CommonMDCLoggerFilter implements Filter {

    @Value("${management.endpoints.web.base-path:}")
    private String managementEndpoint;

    @Value("${application.logging.tracing:false}")
    private boolean appLogTracing;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String requestId = request.getHeader(RequestUtils.HEADER_REQUEST_ID);
        if (!StringUtils.hasText(requestId)) {
            requestId = RequestUtils.generateRequestId();
        }
        response.setHeader(RequestUtils.HEADER_REQUEST_ID, requestId);
        MDC.put(RequestUtils.CONTEXT_REQUEST_ID, requestId);
        MDC.put(RequestUtils.CONTEXT_REQUEST_URL, request.getRequestURI());
        MDC.put(RequestUtils.CONTEXT_REQUEST_METHOD, request.getMethod());
        MDC.put(RequestUtils.CONTEXT_REQUEST_TYPE, "request");
        long start = System.currentTimeMillis();
        chain.doFilter(req, res);
        MDC.put(RequestUtils.CONTEXT_REQUEST_TYPE, "response");
        MDC.put(RequestUtils.CONTEXT_RESPONSE_TIME, String.valueOf(System.currentTimeMillis() - start));
        MDC.put(RequestUtils.CONTEXT_RESPONSE_STATUS, String.valueOf(response.getStatus()));
        // Log response time
        if ((appLogTracing && !request.getRequestURI().startsWith(managementEndpoint))
                || (HttpStatus.OK.value() != response.getStatus() && request.getRequestURI().startsWith(managementEndpoint))) {
            log.info(HttpStatus.valueOf(response.getStatus()).name());
        }
        MDC.remove(RequestUtils.CONTEXT_RESPONSE_TIME);
        MDC.remove(RequestUtils.CONTEXT_RESPONSE_STATUS);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}

