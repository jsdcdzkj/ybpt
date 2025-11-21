package com.jsdc.ybpt.common;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
public class BizLogAutoConfiguration {

    @Bean
    public BizLogAspect BizLogAspect() {
        return new BizLogAspect();
    }

}
