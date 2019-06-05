package com.epamTranings.bankSystem.config;

import java.util.*;

public class SecurityConfig {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    // String: Role
    // List<String>: urlPatterns.
    private static final Map<String, List<String>> mapConfig = new HashMap<>();

    /**
     * Initialising map with roles and corresponding to them url patterns
     */
    public static void init(){

        //Configure for "ADMIN" Role
        List<String> urlPatternsAdmin = new ArrayList<>();

        urlPatternsAdmin.add("/adminPage");

        mapConfig.put(ROLE_ADMIN, urlPatternsAdmin);

        //Configure for "USER" Role
        List<String> urlPatternsUser = new ArrayList<>();

        urlPatternsUser.add("/userPage");

        mapConfig.put(ROLE_USER, urlPatternsUser);
    }

    /**
     * Getting all app roles from mapConfig
     * @return
     */
    public static Set<String> getAllAppRoles() {
        return mapConfig.keySet();
    }

    /**
     * Getting url patterns from mapConfig by specific role
     * @param role
     * @return
     */
    public static List<String> getUrlPatternsForRole(String role) {
        return mapConfig.get(role);
    }
}
