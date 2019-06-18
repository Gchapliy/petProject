package com.epamTranings.bankSystem.controller;

import com.epamTranings.bankSystem.dao.BankAccountDAO;
import com.epamTranings.bankSystem.entity.bankAccount.BankAccountOrder;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import com.epamTranings.bankSystem.utils.AppUtils;
import com.epamTranings.bankSystem.utils.CreateBankAccountOrderUtils;
import com.epamTranings.bankSystem.utils.LocaleUtils;
import com.epamTranings.bankSystem.validator.BankAccountOrderValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "createBankAccountOrder", urlPatterns = {"/newBankAccount"})
public class CreateBankAccountOrderServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(CreateBankAccountOrderServlet.class);

    public static final double DEPOSIT_PERCENT = 10;
    public static final double CREDIT_PERCENT = 45;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleUtils.setLocaleHeaderAndFooter(req);
        LocaleUtils.setLocaleNewBankAccount(req, false, false);

        req.setAttribute("dep_perc", DEPOSIT_PERCENT);
        req.setAttribute("cred_perc", CREDIT_PERCENT);

        req.getRequestDispatcher("templates/newBankAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleUtils.setLocaleHeaderAndFooter(req);

        if(BankAccountOrderValidator.validate(req)){
            BankAccountOrder bankAccountOrder = CreateBankAccountOrderUtils.getBankAccountOrderDataFromRequest(req);
            if(BankAccountDAO.insertBankAccountOrder(AppUtils.getStoredConnection(req), bankAccountOrder)){
                UserAccount userAccount = AppUtils.getLoginedUser(req.getSession());
                userAccount.getUserBankAccountsOrders().add(bankAccountOrder);

                AppUtils.storeLoginedUser(req.getSession(), userAccount);

                req.getRequestDispatcher("templates/newBankAccountOrderCreated.jsp").forward(req, resp);
                return;
            }

            doGet(req, resp);
        } else {

            req.getRequestDispatcher("templates/newBankAccount.jsp").forward(req, resp);
        }
    }
}
