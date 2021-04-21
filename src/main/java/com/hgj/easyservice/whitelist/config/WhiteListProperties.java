package com.hgj.easyservice.whitelist.config;

import com.hgj.easyservice.whitelist.anno.WhiteListJoinPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "easyservice.whitelist")
public class WhiteListProperties {

    private String users;

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    @Bean
    @ConditionalOnMissingBean
    private WhiteListJoinPoint point(){
        return new WhiteListJoinPoint();
    }


}