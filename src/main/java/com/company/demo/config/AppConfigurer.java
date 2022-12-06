package com.company.demo.config;

import com.company.demo.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/**").excludePathPatterns("/index","/login","/addCompany","/updateCompany","/companies","/contacts","/api/**","/css/**","/js/**");
    }

}
