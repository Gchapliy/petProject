package com.epamTranings.bankSystem.controller;

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
import java.io.IOException;

@WebServlet(name = "bankAccountPage", urlPatterns = {"/bankAccount"})
public class BankAccountServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger(BankAccountServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserAccount userAccount = AppUtils.getLoginedUser(req.getSession());
        String bankAccountUuid = req.getParameter("uuid");
        BankAccount bankAccount;

        if (bankAccountUuid != null) {
            bankAccount = userAccount.getBankAccountByName(bankAccountUuid);
            if(bankAccount != null){

                logger.info("bank account found - " + bankAccount);
                req.setAttribute("bankAccount", bankAccount);
            }
        } else {
            req.getRequestDispatcher("templates/404.jsp");
        }

        LocaleUtils.setLocaleHeaderAndFooter(req);
        req.getRequestDispatcher("templates/bankAccount.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);

    }
}
