package com.epamTranings.bankSystem.servlet;

import com.epamTranings.bankSystem.dao.UserDAO;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import com.epamTranings.bankSystem.utils.UserUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
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
            Connection conn = UserUtils.getStoredConnection(req);
                // Find the user in the DB.
                user = UserDAO.findUserByEmail(conn, userEmail);

                if (user == null) {
                    hasError = true;
                    errorString = "User Name or password invalid";
                }
            }

        // If error, forward to /WEB-INF/views/login.jsp
        if (hasError) {
            user = new UserAccount();
            user.setUserAccountEmail(userEmail);
            user.setUserAccountEncryptedPassword(password);

            // Store information in request attribute, before forward.
            req.setAttribute("errorString", errorString);
            req.setAttribute("user", user);

            // Forward to /WEB-INF/views/login.jsp
            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("templates/login.jsp");

            dispatcher.forward(req, resp);
        }
        // If no error
        // Store user information in Session
        // And redirect to userInfo page.
        else {
            HttpSession session = req.getSession();
            UserUtils.storeLoginedUser(session, user);

            // If user checked "Remember me".
            if (remember) {
                UserUtils.storeUserCookie(resp, user);
            }
            // Else delete cookie.
            else {
                UserUtils.deleteUserCookie(resp);
            }

            // Redirect to userInfo page.
            resp.sendRedirect(req.getContextPath() + "/userPage");
        }
        }
    }

