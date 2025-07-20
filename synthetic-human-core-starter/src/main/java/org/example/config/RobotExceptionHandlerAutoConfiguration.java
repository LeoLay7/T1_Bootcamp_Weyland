package org.example.config;

import org.example.exception.RobotExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication
public class RobotExceptionHandlerAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(RobotExceptionHandler.class)
    public RobotExceptionHandler robotExceptionHandler() {
        return new RobotExceptionHandler();
    }
}
