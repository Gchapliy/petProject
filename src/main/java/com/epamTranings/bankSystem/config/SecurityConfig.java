package com.epamTranings.bankSystem.config;

import java.util.*;

public class SecurityConfig {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    // String: Role
    // List<String>: urlPatterns.
    private static final Map<String, List<String>> mapConfig = new HashMap<>();

    static {
        init();
    }
    /**
     * Initialising map with roles and corresponding to them url patterns
     */
    public static void init(){

        //Configure for "ADMIN" Role
        List<String> urlPatternsAdmin = new ArrayList<>();

        urlPatternsAdmin.add("/adminPage");
        urlPatternsAdmin.add("/userPage");
        urlPatternsAdmin.add("/standard");
        urlPatternsAdmin.add("/deposit");
        urlPatternsAdmin.add("/credit");
        urlPatternsAdmin.add("/bankAccount");
        urlPatternsAdmin.add("/historyBankAccount");

        mapConfig.put(ROLE_ADMIN, urlPatternsAdmin);

        //Configure for "USER" Role
        List<String> urlPatternsUser = new ArrayList<>();

        urlPatternsUser.add("/userPage");
        urlPatternsUser.add("/standard");
        urlPatternsUser.add("/deposit");
        urlPatternsUser.add("/credit");
        urlPatternsUser.add("/bankAccount");
        urlPatternsAdmin.add("/historyBankAccount");

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
