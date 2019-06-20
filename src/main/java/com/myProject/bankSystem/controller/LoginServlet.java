package com.myProject.bankSystem.controller;

import com.myProject.bankSystem.utils.LocaleUtils;
import com.myProject.bankSystem.validator.LoginValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleUtils.setLocaleHeaderAndFooter(req);
        LocaleUtils.setLocaleLoginPage(req, false, false);

        req.getRequestDispatcher("templates/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LocaleUtils.setLocaleHeaderAndFooter(req);

        if (LoginValidator.validate(req, resp)) {

            resp.sendRedirect(req.getContextPath() + "/userPage");
        } else {

            req.getRequestDispatcher("templates/login.jsp").forward(req, resp);
        }
    }
}

