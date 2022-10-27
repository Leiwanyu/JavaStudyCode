package com.lendea.java_common_mistakes.config;

import com.lendea.java_common_mistakes.Interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lendea
 * @date 2022/8/9 15:04
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        final InterceptorRegistration registration = registry.addInterceptor(new UserInterceptor());
        //所有路径都拦截
        registration.addPathPatterns("/**");
        //忽略拦截路径
        registration.excludePathPatterns("/login");
    }
}
