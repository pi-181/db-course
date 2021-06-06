package com.invokegs.dbcoursework.security;

import com.invokegs.dbcoursework.service.PostService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class UserSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {
    private final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    private final PostService postService;

    public UserSecurityExpressionHandler(PostService postService) {
        this.postService = postService;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            Authentication authentication, MethodInvocation invocation) {
        final UserSecurityExpressionRoot root = new UserSecurityExpressionRoot(postService, authentication);
        root.setThis(invocation.getThis());
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(this.trustResolver);
        root.setRoleHierarchy(getRoleHierarchy());
        return root;
    }

}
