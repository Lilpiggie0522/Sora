package com.piggie.config;

import com.piggie.properties.AliOssProperties;
import com.piggie.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: OssConfiguration
 * Package: com.piggie.config
 * Description: configuration class that is used to create utils object
 *
 * @Author Piggie
 * @Create 8/02/2024 5:41 pm
 * @Version 1.0
 */
@Slf4j
@Configuration
public class OssConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("start generating alioss util bean: {}", aliOssProperties);
        return new AliOssUtil(aliOssProperties.getEndpoint(), aliOssProperties.getBucketName());
    }
}
