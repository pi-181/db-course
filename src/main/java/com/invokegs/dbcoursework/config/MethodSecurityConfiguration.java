package com.invokegs.dbcoursework.config;

import com.invokegs.dbcoursework.expression.UserSecurityExpressionHandler;
import com.invokegs.dbcoursework.service.PostService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
    private final PostService postService;

    public MethodSecurityConfiguration(PostService postService) {
        this.postService = postService;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new UserSecurityExpressionHandler(postService);
    }

    @Bean
    protected MethodSecurityExpressionHandler expressionHandler() {
        return createExpressionHandler();
    }

}
