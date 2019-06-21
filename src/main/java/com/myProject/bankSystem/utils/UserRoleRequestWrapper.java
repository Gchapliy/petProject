package com.myProject.bankSystem.utils;

import com.myProject.bankSystem.entity.userAccount.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

/**
 * An extension for the HTTPServletRequest that overrides the getUserPrincipal()
 * and isUserInRole(). We supply these implementations here, where they are not
 * normally populated unless we are going through the facility provided by the
 * container.
 * <p>
 * If he userEmail or roles are null on this wrapper, the parent request is consulted
 * to try to fetch what ever the container has set for us. This is intended to
 * be created and used by the UserRoleFilter.
 */
public class UserRoleRequestWrapper extends HttpServletRequestWrapper {
    private String userEmail;
    private Role role;
    private HttpServletRequest realRequest;

    public UserRoleRequestWrapper(String userEmail, Role role, HttpServletRequest request) {
        super(request);
        this.userEmail = userEmail;
        this.role = role;
        this.realRequest = request;
    }

    @Override
    public boolean isUserInRole(String role) {
        if (role == null) {
            return this.realRequest.isUserInRole(role);
        }

        return role.equals(this.role.getRoleName());
    }

    @Override
    public Principal getUserPrincipal() {
        if (this.userEmail == null) {
            return realRequest.getUserPrincipal();
        }

        // Make an anonymous implementation to just return our user
        return () -> userEmail;
    }
}
