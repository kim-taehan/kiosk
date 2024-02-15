package org.devleopx.kiosk.global.logging;

import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class LoggingConfig {

    @Bean
    public FilterRegistrationBean mdcRequestLoggingFilter() {

        FilterRegistrationBean<Filter> filterFilter = new FilterRegistrationBean<>();
        filterFilter.setFilter(new MDCRequestLoggingFilter());
        filterFilter.setOrder(1);
        filterFilter.addUrlPatterns("/*");
        return filterFilter;
    }
}
