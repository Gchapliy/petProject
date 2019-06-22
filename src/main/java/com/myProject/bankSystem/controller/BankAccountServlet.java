package com.myProject.bankSystem.controller;

import com.myProject.bankSystem.bean.bankAccount.BankAccount;
import com.myProject.bankSystem.bean.userAccount.UserAccount;
import com.myProject.bankSystem.utils.AppUtils;
import com.myProject.bankSystem.utils.LocaleUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Locale;

@WebServlet(name = "bankAccountPage", urlPatterns = {"/bankAccount"})
public class BankAccountServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger(BankAccountServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserAccount userAccount = AppUtils.getLoginedUser(req.getSession());
        String bankAccountUuid = req.getParameter("uuid");


        Locale locale = (Locale) req.getAttribute("locale");
        //Locale for Date
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);

        //Locale for Number
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);

        BankAccount bankAccount;

        if (bankAccountUuid != null) {
            bankAccount = userAccount.getBankAccountByUuid(bankAccountUuid);
            if (bankAccount != null) {

                logger.info("bank account uuid - " + bankAccountUuid + " found");
                req.setAttribute("dateFormat", dateFormat);
                req.setAttribute("numberFormat", numberFormat);
                req.setAttribute("bankAccount", bankAccount);
            } else {
                logger.info("bank account uuid - " + bankAccountUuid + " not found");
                req.setAttribute("noBankAccount", "bank account uuid - " + bankAccountUuid + " not found");
            }
        } else {
            req.getRequestDispatcher("templates/error.jsp").forward(req, resp);
        }

        req.setAttribute("link", "userPage?pageA=1&pageUsO=1&pageYO=1");
        req.setAttribute("depositType", BankAccount.AccountType.DEPOSIT);
        req.setAttribute("creditType", BankAccount.AccountType.CREDIT);

        LocaleUtils.setLocaleHeaderAndFooter(req);
        LocaleUtils.setLocaleBankAccount(req);
        LocaleUtils.setLocaleManagingInterface(req);

        req.getRequestDispatcher("templates/bankAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
