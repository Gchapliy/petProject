package com.myProject.bankSystem.utils;

import com.myProject.bankSystem.bean.userAccount.UserAccount;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AppUtils {

    private static int REDIRECT_ID = 0;

    private static final Map<Integer, String> id_uri_map = new HashMap<>();
    private static final Map<String, Integer> uri_id_map = new HashMap<>();

    public static final String ATR_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
    private static final String ATR_NAME_USER = "ATTRIBUTE_FOR_USER";
    private static final String ATR_LOCALE = "ATTRIBUTE_FOR_LOCALE";
    private static final int LOCALE_COOKIE_LIFE = 10 * 365 * 24 * 60 * 60; // 10 years
    private static final int USER_COOKIE_LIFE = 2 * 60 * 60; // 2 hours

    /**
     * Store connection in request attribute.
     * @param request
     * @param conn
     */
    public static void storeConnection(ServletRequest request, Connection conn) {
        request.setAttribute(ATR_NAME_CONNECTION, conn);
    }

    /**
     * Get the Connection object has been stored in attribute of the request.
     * @param request
     * @return
     */
    public static Connection getStoredConnection(ServletRequest request) {
        Connection conn = (Connection) request.getAttribute(ATR_NAME_CONNECTION);
        return conn;
    }

    /**
     * Store locale to cookie
     * @param response
     * @param locale
     */
    public static void storeLocale(HttpServletResponse response, Locale locale){
        Cookie cookieLocale = new Cookie(ATR_LOCALE, locale.getLanguage() + "_" + locale.getCountry());
        cookieLocale.setMaxAge(LOCALE_COOKIE_LIFE);
        response.addCookie(cookieLocale);
    }

    /**
     * Get Locale from Cookie
     * @param request
     * @return
     */
    public static Locale getStoredLocale(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATR_LOCALE.equals(cookie.getName())) {
                    String[] localeData = cookie.getValue().split("_");
                    return new Locale(localeData[0], localeData[1]);
                }
            }
        }
        return null;
    }

    /**
     * Store user info in Session.
     * @param session
     * @param loginedUserAccount
     */
    public static void storeLoginedUser(HttpSession session, UserAccount loginedUserAccount) {
        session.setAttribute("loginedUser", loginedUserAccount);
    }

    /**
     * Get the userAccount information stored in the session.
     * @param session
     * @return
     */
    public static UserAccount getLoginedUser(HttpSession session) {
        UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
        return loginedUser;
    }

    /**
     * Store userAccount info in Cookie
     * @param response
     * @param userAccount
     */
    public static void storeUserCookie(HttpServletResponse response, UserAccount userAccount) {
        Cookie cookieUserEmail = new Cookie(ATR_NAME_USER, userAccount.getUserAccountEmail());
        // 1 day (Converted to seconds)
        cookieUserEmail.setMaxAge(USER_COOKIE_LIFE);
        response.addCookie(cookieUserEmail);
    }

    /**
     * Getting userAccount info from cookie
     * @param request
     * @return
     */
    public static String getUserEmailInCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATR_NAME_USER.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Delete cookie with userAccount data
     * @param response
     */
    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserEmail = new Cookie(ATR_NAME_USER, null);
        // 0 seconds (This cookie will expire immediately)
        cookieUserEmail.setMaxAge(0);
        response.addCookie(cookieUserEmail);
    }

    /**
     * Store request URI after login
     * @param session
     * @param requestUri
     * @return
     */
    public static int storeRedirectAfterLoginUrl(HttpSession session, String requestUri) {
        Integer id = uri_id_map.get(requestUri);

        if (id == null) {
            id = REDIRECT_ID++;

            uri_id_map.put(requestUri, id);
            id_uri_map.put(id, requestUri);
            return id;
        }

        return id;
    }

    /**
     * Get redirect URI after login
     * @param session
     * @param redirectId
     * @return
     */
    public static String getRedirectAfterLoginUrl(HttpSession session, int redirectId) {
        String url = id_uri_map.get(redirectId);
        if (url != null) {
            return url;
        }
        return null;
    }
}
