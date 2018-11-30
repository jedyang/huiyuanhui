package com.yunsheng.huiyuanhui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 目的是加上对静态资源路径中/static的解析
        // springboot默认是classpath:/META-INF/resources/，classpath:/resources/，classpath:/static/，classpath:/public/
        // 所以浏览器中资源不能加static，但是不加static idea开发时，idea找不到，有点不方便
        if(!registry.hasMappingForPattern("/static/**")){
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        }
        super.addResourceHandlers(registry);
    }

}