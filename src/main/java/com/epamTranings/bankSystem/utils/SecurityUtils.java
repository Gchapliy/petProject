package com.epamTranings.bankSystem.utils;

import com.epamTranings.bankSystem.config.SecurityConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;
import java.util.Set;

public class SecurityUtils {

    /**
     * Encode password
      * @param password
     * @return
     */
    public static String encodePassword(String password){
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    /**
     * Check password typed by user with password from db
     * @param encryptedAccountPassword
     * @param checkingPassword
     * @return
     */
    public static boolean checkPassword(String encryptedAccountPassword, String checkingPassword){
        String decodedPassword = new String(Base64.getDecoder().decode(encryptedAccountPassword.getBytes()));

        return decodedPassword.equals(checkingPassword);
    }

    /**
     * Check whether this 'request' is required to login or not.
     * @param request
     * @return
     */
    public static boolean isSecurityPage(HttpServletRequest request) {
        String urlPattern = UrlPatternUtils.getUrlPattern(request);

        Set<String> roles = SecurityConfig.getAllAppRoles();

        for (String role : roles) {
            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if this 'request' has a 'valid role'?
     * @param request
     * @return
     */
    public static boolean hasPermission(HttpServletRequest request) {
        String urlPattern = UrlPatternUtils.getUrlPattern(request);

        Set<String> allRoles = SecurityConfig.getAllAppRoles();

        for (String role : allRoles) {
            if (!request.isUserInRole(role)) {
                continue;
            }
            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }
}
