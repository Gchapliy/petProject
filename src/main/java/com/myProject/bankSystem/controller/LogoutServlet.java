package com.myProject.bankSystem.controller;

import com.myProject.bankSystem.bean.userAccount.UserAccount;
import com.myProject.bankSystem.utils.AppUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "logout", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet{

    final static Logger logger = LogManager.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserAccount loginedUser = (UserAccount)req.getSession().getAttribute("loginedUser");

        req.getSession().invalidate();
        AppUtils.deleteUserCookie(resp);

        logger.info("User: " + loginedUser.getUserAccountEmail() + " is logout");

        // Redirect to Home Page.
        resp.sendRedirect("/home");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

}
