package com.wgx.threekingdomskills.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * author:wgx
 * version:1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${file.storageFolder}")
    private String storageFolder;

    //放行静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //静态资源映射，映射到uploadFolder(/home/wgx/generals/)，访问/home/wgx/general/**，映射到服务器上的文件夹下
        registry.addResourceHandler(storageFolder + "**").addResourceLocations("file:" + storageFolder);
    }
}
