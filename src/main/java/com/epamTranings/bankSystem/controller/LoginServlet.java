package com.epamTranings.bankSystem.controller;

import com.epamTranings.bankSystem.dao.UserDAO;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import com.epamTranings.bankSystem.utils.AppUtils;
import com.epamTranings.bankSystem.utils.SecurityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("templates/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userEmail = req.getParameter("userEmail");
        String password = req.getParameter("password");
        String rememberMeStr = req.getParameter("rememberMe");
        boolean remember = "Y".equals(rememberMeStr);

        UserAccount user = null;
        boolean hasError = false;
        String errorString = null;

        if(userEmail == null || password == null || userEmail.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Required username and password!";
        } else {
            Connection conn = AppUtils.getStoredConnection(req);
                // Find the user in the DB.
                user = UserDAO.findUserByEmail(conn, userEmail);

                if (user == null || !SecurityUtils.checkPassword(user.getUserAccountEncryptedPassword(), password)) {
                    hasError = true;
                    errorString = "User Name or password invalid";
                }
            }

        // If error, forward to login.jsp
        if (hasError) {
            user = new UserAccount();
            user.setUserAccountEmail(userEmail);
            user.setUserAccountEncryptedPassword(password);

            // Store information in request attribute, before forward.
            req.setAttribute("errorString", errorString);
            req.setAttribute("userEmail", user);

            logger.info("User: " + userEmail + " typed wrong data");
            req.getRequestDispatcher("templates/login.jsp").forward(req, resp);
        }
        // If no error
        // Store user information in Session
        // And redirect to user page.
        else {
            HttpSession session = req.getSession();
            AppUtils.storeLoginedUser(session, user);

            // If user checked "Remember me".
            if (remember) {
                AppUtils.storeUserCookie(resp, user);
            }
            // Else delete cookie.
            else {
                AppUtils.deleteUserCookie(resp);
            }

            logger.info("User: " + userEmail + " is login");

            // Redirect to userInfo page.
            resp.sendRedirect(req.getContextPath() + "/userPage");
        }
        }
    }

