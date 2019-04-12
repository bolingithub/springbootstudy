package com.example.springbootstudy.pretreatment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppMvcConfig implements WebMvcConfigurer {

    @Autowired
    UserTokenInterceptor userTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userTokenInterceptor).excludePathPatterns(
                "/user/userLogin",
                "/user/sendSmsCode",
                "/user/userRegister");
    }
}
