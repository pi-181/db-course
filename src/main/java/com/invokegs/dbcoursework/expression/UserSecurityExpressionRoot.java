package com.invokegs.dbcoursework.expression;

import com.invokegs.dbcoursework.entity.Post;
import com.invokegs.dbcoursework.entity.SecurityUserDetails;
import com.invokegs.dbcoursework.entity.User;
import com.invokegs.dbcoursework.service.PostService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class UserSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
    private final PostService postService;

    private Object filterObject;
    private Object returnObject;
    private Object target;

    /**
     * Creates a new instance
     *
     * @param authentication the {@link Authentication} to use. Cannot be null.
     */
    public UserSecurityExpressionRoot(PostService postService, Authentication authentication) {
        super(authentication);
        this.postService = postService;
    }

    public boolean isPostAuthor(Long postId) {
        final User user = ((SecurityUserDetails) getPrincipal()).getUser();
        return postService.getPost(postId).map(p -> p.isAuthor(user)).orElse(false);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    void setThis(Object target) {
        this.target = target;
    }

    public Object getThis() {
        return target;
    }

}
