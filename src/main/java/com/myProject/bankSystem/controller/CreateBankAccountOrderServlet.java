package com.myProject.bankSystem.controller;

import com.myProject.bankSystem.dao.BankAccountDAO;
import com.myProject.bankSystem.entity.bankAccount.BankAccountOrder;
import com.myProject.bankSystem.entity.userAccount.UserAccount;
import com.myProject.bankSystem.utils.AppUtils;
import com.myProject.bankSystem.utils.CreateBankAccountOrderUtil;
import com.myProject.bankSystem.utils.LocaleUtils;
import com.myProject.bankSystem.validator.BankAccountOrderValidator;
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

        //check bank account order data
        if(BankAccountOrderValidator.validate(req)){

            //create order
            BankAccountOrder bankAccountOrder = CreateBankAccountOrderUtil.getBankAccountOrderDataFromRequest(req);

            if(BankAccountDAO.insertBankAccountOrder(AppUtils.getStoredConnection(req), bankAccountOrder)){
                //update logined user data
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
