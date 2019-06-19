package com.epamTranings.bankSystem.controller;

import com.epamTranings.bankSystem.dao.UserDAO;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import com.epamTranings.bankSystem.utils.AppUtils;
import com.epamTranings.bankSystem.utils.CreateUserAccountUtil;
import com.epamTranings.bankSystem.utils.LocaleUtils;
import com.epamTranings.bankSystem.validator.RegisterValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "register", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(RegisterServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleUtils.setLocaleHeaderAndFooter(req);

        boolean[] errors = new boolean[6];
        errors[5] = true;

        LocaleUtils.setLocaleRegisterPage(req, errors);

        req.getRequestDispatcher("templates/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleUtils.setLocaleHeaderAndFooter(req);

        if (RegisterValidator.validate(req,resp)) {
            UserAccount userAccount = CreateUserAccountUtil.getUserAccountFromDataRequest(req);

            if(UserDAO.insertUserAccount(AppUtils.getStoredConnection(req), userAccount))
                resp.sendRedirect(req.getContextPath() + "/login");
            else
                req.getRequestDispatcher("templates/register.jsp").forward(req, resp);

        } else {

            req.getRequestDispatcher("templates/register.jsp").forward(req, resp);
        }
    }
}
