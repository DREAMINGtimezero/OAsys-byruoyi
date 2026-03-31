package com.ruoyi.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class CommonsMultipartConfig {

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        // 1. 设置总请求大小（2GB）
        resolver.setMaxUploadSize(2097152000);
        // 2. 设置单个文件大小（1GB）
        resolver.setMaxUploadSizePerFile(1048576000);
        // 3. 设置文件个数限制（关键：允许30个文件）
        resolver.setMaxInMemorySize(30);
        // 4. 字符编码
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }
}