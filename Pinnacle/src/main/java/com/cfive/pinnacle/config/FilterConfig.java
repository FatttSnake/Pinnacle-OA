package com.cfive.pinnacle.config;

import com.cfive.pinnacle.filter.ExceptionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ExceptionFilter> exceptionFilterFilterRegistrationBean(ExceptionFilter exceptionFilter) {
        FilterRegistrationBean<ExceptionFilter> registrationBean = new FilterRegistrationBean<>(exceptionFilter);
        registrationBean.setName("exceptionFilter");
        registrationBean.setOrder(-100);
        return registrationBean;
    }
}
