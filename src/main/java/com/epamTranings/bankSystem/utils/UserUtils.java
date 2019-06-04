package com.epamTranings.bankSystem.utils;

import com.epamTranings.bankSystem.dbConnection.MySQLConnectionUtil;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

public class UserUtils {

    public static final String ATR_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
    private static final String ATR_NAME_USER_EMAIL = "ATTRIBUTE_FOR_STORE_USER_EMAIL_IN_COOKIE";

    final static Logger logger = LogManager.getLogger(UserUtils.class);

    /**
     * Store connection in request attribute.
     * @param request
     * @param conn
     */
    public static void storeConnection(ServletRequest request, Connection conn) {
        request.setAttribute(ATR_NAME_CONNECTION, conn);
        logger.info("Connection stored in request");
    }

    /**
     * Get the Connection object has been stored in attribute of the request.
     * @param request
     * @return
     */
    public static Connection getStoredConnection(ServletRequest request) {
        Connection conn = (Connection) request.getAttribute(ATR_NAME_CONNECTION);
        logger.info("Connection loaded from request");
        return conn;
    }

    /**
     * Store user info in Session.
     * @param session
     * @param loginedUserAccount
     */
    public static void storeLoginedUser(HttpSession session, UserAccount loginedUserAccount) {
        session.setAttribute("loginedUser", loginedUserAccount);
        logger.info("UserAccount_" + loginedUserAccount + " stored in session");
    }

    /**
     * Get the userAccount information stored in the session.
     * @param session
     * @return
     */
    public static UserAccount getLoginedUser(HttpSession session) {
        UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
        logger.info("UserAccount_" + loginedUser + "  loaded from session");
        return loginedUser;
    }

    /**
     * Store userAccount info in Cookie
     * @param response
     * @param userAccount
     */
    public static void storeUserCookie(HttpServletResponse response, UserAccount userAccount) {
        Cookie cookieUserEmail = new Cookie(ATR_NAME_USER_EMAIL, userAccount.getUserAccountEmail());
        // 1 day (Converted to seconds)
        cookieUserEmail.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUserEmail);
        logger.info("UserAccount_" + userAccount + "  stored in cookie");
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
                if (ATR_NAME_USER_EMAIL.equals(cookie.getName())) {
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
        Cookie cookieUserEmail = new Cookie(ATR_NAME_USER_EMAIL, null);
        // 0 seconds (This cookie will expire immediately)
        cookieUserEmail.setMaxAge(0);
        response.addCookie(cookieUserEmail);
        logger.info("userAccount removed from cookie");
    }
}