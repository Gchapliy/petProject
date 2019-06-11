package com.epamTranings.bankSystem.controller;

import com.epamTranings.bankSystem.dao.UserDAO;
import com.epamTranings.bankSystem.entity.bankAccount.BankAccount;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import com.epamTranings.bankSystem.utils.AppUtils;
import com.epamTranings.bankSystem.utils.LocaleUtils;
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
import java.util.List;
import java.util.UUID;

@WebServlet(name = "userPage", urlPatterns = { "/userPage" })
public class UserPageServlet extends HttpServlet{

    final static Logger logger = LogManager.getLogger(UserPageServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleUtils.setLocaleHeaderAndFooter(req);
        LocaleUtils.setLocaleHomePage(req);

        HttpSession session = req.getSession();

        // Check User has logged on
        UserAccount loginedUser = AppUtils.getLoginedUser(session);

        // Not logged in
        if (loginedUser == null) {
            // Redirect to login page.
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Connection connection = AppUtils.getStoredConnection(req);
        List<BankAccount> bankAccounts = UserDAO.findUserBankAccounts(connection, loginedUser);

        AppUtils.getLoginedUser(session).setUserBankAccounts(bankAccounts);

        logger.info("found accounts " + bankAccounts);

        boolean noAccounts = false;
        // Store info to the request attribute before forwarding.
        req.setAttribute("user", loginedUser);

        if(bankAccounts == null || bankAccounts.size() == 0){
            noAccounts = true;
        }else {
            req.setAttribute("bankAccounts", bankAccounts);
        }

        LocaleUtils.setLocaleUserPage(req, noAccounts);

        // If the user has logged in, then forward to the page
        req.getRequestDispatcher("templates/userPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
