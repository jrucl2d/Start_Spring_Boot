package com.yu.project09;

import com.yu.project09.interceptor.LoginCheckInterceptor;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Log
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor()).addPathPatterns("/login"); // '/login' URI에 대해서 인터셉터가 발동됨

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
