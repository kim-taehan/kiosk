package org.devleopx.kiosk.global.logging;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class MDCRequestLoggingFilter implements Filter {
    // Mapped Diagnostic Context
    private static final String REQUEST_ID = "request_id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());
        chain.doFilter(request, response);
        MDC.clear();
    }
}
