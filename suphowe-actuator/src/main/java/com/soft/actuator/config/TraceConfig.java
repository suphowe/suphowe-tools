package com.soft.actuator.config;

import org.springframework.boot.actuate.autoconfigure.trace.http.HttpTraceAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.trace.http.HttpTraceProperties;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * actuator web请求跟踪
 * @author suphowe
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "management.trace.http", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(HttpTraceProperties.class)
@AutoConfigureBefore(HttpTraceAutoConfiguration.class)
public class TraceConfig {

    private List<HttpTrace> list = new CopyOnWriteArrayList<>();

    private final int traceCount = 99;

    @Bean
    @ConditionalOnMissingBean(HttpTraceRepository.class)
    public HttpTraceRepository httpTraceProperties() {
        return new HttpTraceRepository() {
            @Override
            public List<HttpTrace> findAll() {
                return list;
            }

            @Override
            public void add(HttpTrace trace) {
                if (list.size() > traceCount) {
                    list.remove(0);
                }
            }
        };
    }
}
