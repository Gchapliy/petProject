package com.myProject.bankSystem.controller;

import com.myProject.bankSystem.dao.BankAccountDAO;
import com.myProject.bankSystem.dao.UserDAO;
import com.myProject.bankSystem.entity.bankAccount.BankAccount;
import com.myProject.bankSystem.entity.bankAccount.BankAccountOrder;
import com.myProject.bankSystem.entity.userAccount.UserAccount;
import com.myProject.bankSystem.utils.AppUtils;
import com.myProject.bankSystem.utils.LocaleUtils;
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
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@WebServlet(name = "userPage", urlPatterns = { "/userPage" })
public class UserPageServlet extends HttpServlet{

    final static Logger logger = LogManager.getLogger(UserPageServlet.class);
    private final static String ROLE_ADMIN = "ROLE_ADMIN";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleUtils.setLocaleHeaderAndFooter(req);
        LocaleUtils.setLocaleHomePage(req);

        HttpSession session = req.getSession();

        // Check User has logged on
        UserAccount loginedUser = AppUtils.getLoginedUser(session);

        boolean isAdmin = false;
        boolean noUserOrders = false;

        // Not logged in
        if (loginedUser == null) {
            // Redirect to login page.
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if(loginedUser.getUserAccountRole().getRoleName().equals(ROLE_ADMIN)){
            isAdmin = true;



            List<BankAccountOrder> usersBankAccountOrders = BankAccountDAO.findAllBankAccountOrders(AppUtils.getStoredConnection(req));

            if(usersBankAccountOrders == null || usersBankAccountOrders.size() == 0)
                noUserOrders = true;
            else
                req.setAttribute("usersBankAccountOrders", usersBankAccountOrders);

            req.setAttribute("isAdmin", isAdmin);
        }

        Connection connection = AppUtils.getStoredConnection(req);
        List<BankAccount> bankAccounts = UserDAO.findUserBankAccounts(connection, loginedUser);
        List<BankAccountOrder> bankAccountOrders = BankAccountDAO.findBankAccountOrdersByUserAccount(connection, loginedUser);

        AppUtils.getLoginedUser(session).setUserBankAccounts(bankAccounts);
        AppUtils.getLoginedUser(session).setUserBankAccountsOrders(bankAccountOrders);

        logger.info("found accounts " + bankAccounts);
        logger.info("found orders " + bankAccountOrders);

        boolean noAccounts = false;
        boolean noOrders = false;

        // Store info to the request attribute before forwarding.
        req.setAttribute("user", loginedUser);

        if(bankAccounts == null || bankAccounts.size() == 0){
            noAccounts = true;
        }else {
            //Locale for Number
            NumberFormat numberFormat = NumberFormat.getNumberInstance((Locale) req.getAttribute("locale"));
            req.setAttribute("numberFormat", numberFormat);
            req.setAttribute("bankAccounts", bankAccounts);
        }

        if(bankAccountOrders == null || bankAccountOrders.size() == 0){
            noOrders = true;
        }else {
            //Locale for Date
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, (Locale) req.getAttribute("locale"));
            req.setAttribute("dateFormat", dateFormat);
            req.setAttribute("bankAccountOrders", bankAccountOrders);
        }

        LocaleUtils.setLocaleUserPage(req, noAccounts, noOrders, noUserOrders);

        // If the user has logged in, then forward to the page
        req.getRequestDispatcher("templates/userPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
