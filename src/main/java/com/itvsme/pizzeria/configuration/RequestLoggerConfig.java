package com.itvsme.pizzeria.configuration;

import com.itvsme.pizzeria.logger.RequestLogger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RequestLoggerConfig implements WebMvcConfigurer
{
    private RequestLogger requestLogger;

    public RequestLoggerConfig(RequestLogger requestLogger)
    {
        this.requestLogger = requestLogger;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(requestLogger);
    }
}
