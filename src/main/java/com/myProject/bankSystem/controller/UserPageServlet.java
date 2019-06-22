package com.myProject.bankSystem.controller;

import com.myProject.bankSystem.dao.BankAccountDAO;
import com.myProject.bankSystem.dao.UserDAO;
import com.myProject.bankSystem.bean.bankAccount.BankAccount;
import com.myProject.bankSystem.bean.bankAccount.BankAccountOrder;
import com.myProject.bankSystem.bean.userAccount.UserAccount;
import com.myProject.bankSystem.pagination.Pagination;
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

    private final String ROLE_ADMIN = "ROLE_ADMIN";

    private final int TOTAL_ITEMS_PER_PAGE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleUtils.setLocaleHeaderAndFooter(req);
        LocaleUtils.setLocaleHomePage(req);

        HttpSession session = req.getSession();

        Connection connection = AppUtils.getStoredConnection(req);

        UserAccount loginedUser;

        List<BankAccount> bankAccounts = null;
        List<BankAccountOrder> bankAccountOrders = null;

        boolean isAdmin = false;
        boolean noUserOrders = false;
        boolean noAccounts = false;
        boolean noOrders = false;

        //pagination pages numbers
        String pageIdAccounts = req.getParameter("pageA");
        String pageIdUsersOrders = req.getParameter("pageUsO");
        String pageIdYourOrders = req.getParameter("pageYO");

        int pageIdAcc = Integer.parseInt(pageIdAccounts);
        int pageIdUsO = Integer.parseInt(pageIdUsersOrders);
        int pageIdYO = Integer.parseInt(pageIdYourOrders);

        int allAccounts = 0;
        int allUserOrders = 0;
        int allYourOrders = 0;

        Pagination accountsPagination = null;
        Pagination userOrdersPagination = null;
        Pagination yourOrdersPagination = null;

        // Check User has logged on
        loginedUser = AppUtils.getLoginedUser(session);

        // Not logged in
        if (loginedUser == null) {
            // Redirect to login page.
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if(loginedUser.getUserAccountRole().getRoleName().equals(ROLE_ADMIN)){
            isAdmin = true;

            //Pagination for yousers orders
            allUserOrders = BankAccountDAO.getBankAccountsUsersOrdersCount(connection);
            userOrdersPagination = new Pagination(pageIdUsO, allUserOrders, TOTAL_ITEMS_PER_PAGE);

            List<BankAccountOrder> usersBankAccountOrders = BankAccountDAO.findAllBankAccountOrdersInProgress(AppUtils.getStoredConnection(req), userOrdersPagination.getPageId() - 1, TOTAL_ITEMS_PER_PAGE);

            req.setAttribute("allUsersOrdersPages", userOrdersPagination.getPagesArray());
            req.setAttribute("pageIdUsersOrders", pageIdUsO);
            logger.info("found users orders " + usersBankAccountOrders);

            if(usersBankAccountOrders == null || usersBankAccountOrders.size() == 0)
                noUserOrders = true;
            else{
                req.setAttribute("usersBankAccountOrders", usersBankAccountOrders);
            }

            req.setAttribute("isAdmin", isAdmin);
        }

        req.setAttribute("pageIdUsersOrders", pageIdUsO);

        //Pagination for your accounts
        allAccounts = BankAccountDAO.getBankAccountsCount(connection, loginedUser);
        accountsPagination = new Pagination(pageIdAcc, allAccounts, TOTAL_ITEMS_PER_PAGE);

        bankAccounts = UserDAO.findUserBankAccounts(connection, loginedUser, accountsPagination.getPageId() - 1, TOTAL_ITEMS_PER_PAGE);

        req.setAttribute("allAccountsPages", accountsPagination.getPagesArray());
        req.setAttribute("pageIdAccount", pageIdAcc);

        //Pagination for your orders
        allYourOrders = BankAccountDAO.getBankAccountsUserOrdersByUserAccountCount(connection, loginedUser);
        yourOrdersPagination = new Pagination(pageIdYO, allYourOrders, TOTAL_ITEMS_PER_PAGE);

        bankAccountOrders = BankAccountDAO.findBankAccountOrdersByUserAccount(connection, loginedUser, yourOrdersPagination.getPageId() - 1, TOTAL_ITEMS_PER_PAGE);

        req.setAttribute("allYourOrdersPages", yourOrdersPagination.getPagesArray());
        req.setAttribute("pageIdYourOrders", pageIdYO);

        AppUtils.getLoginedUser(session).setUserBankAccounts(bankAccounts);
        AppUtils.getLoginedUser(session).setUserBankAccountsOrders(bankAccountOrders);

        logger.info("found your accounts " + bankAccounts);
        logger.info("found your orders " + bankAccountOrders);


        // Store info to the request attribute before forwarding.
        req.setAttribute("user", loginedUser);

        if(bankAccounts == null || bankAccounts.size() == 0){
            noAccounts = true;
        }else {

            req.setAttribute("bankAccounts", bankAccounts);
        }

        if(bankAccountOrders == null || bankAccountOrders.size() == 0){
            noOrders = true;
        }else {

            req.setAttribute("bankAccountOrders", bankAccountOrders);
        }

        //Locale for Date
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, (Locale) req.getAttribute("locale"));
        req.setAttribute("dateFormat", dateFormat);

        //Locale for Number
        NumberFormat numberFormat = NumberFormat.getNumberInstance((Locale) req.getAttribute("locale"));
        req.setAttribute("numberFormat", numberFormat);

        //Locale for user page
        LocaleUtils.setLocaleUserPage(req, noAccounts, noOrders, noUserOrders);

        // If the user has logged in, then forward to the page
        req.getRequestDispatcher("templates/userPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
