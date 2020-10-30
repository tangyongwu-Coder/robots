package com.sirius.robots.web.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sirius.robots.web.filter.LogIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author 孟星魂
 * @version 5.0 createTime: 2019/12/26
 */
@Configuration
public class AppConfig {

    @Bean
    public ThreadPoolExecutor externalThreadPool() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("external-gateway-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 100, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100),
                threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean
    public FilterRegistrationBean logIdFilterRegistrationBean() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(logIdFilter());
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.addInitParameter("exclusions",
                "/js/**,/css/**,/static/images/**,/fonts/**,/images/**,/login,/register,/,/index,/forgot," +
                        "/out,/user/login,/user/forgot,/user/register,/user/sendCode");
        return registration;
    }

    @Bean
    public LogIdFilter logIdFilter() {
        return new LogIdFilter();
    }
}
