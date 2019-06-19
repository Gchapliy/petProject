package com.epamTranings.bankSystem.controller;

import com.epamTranings.bankSystem.entity.bankAccount.BankAccount;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import com.epamTranings.bankSystem.utils.AppUtils;
import com.epamTranings.bankSystem.utils.LocaleUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "paymentTransfers", urlPatterns = {"/paymentTransfers"})
public class PaymentTransfersServlet extends HttpServlet {

    private UserAccount userAccount;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleUtils.setLocaleHeaderAndFooter(req);

        String uuid = req.getParameter("uuid");
        UserAccount userAccount = AppUtils.getLoginedUser(req.getSession());

        BankAccount bankAccount = userAccount.getBankAccountByUuid(uuid);

        if(bankAccount != null){
            req.setAttribute("uuid", bankAccount.getAccountUuid());
            req.setAttribute("balance", bankAccount.getAccountBalance());
            BankAccount.AccountType type = bankAccount.getAccountType();

            req.setAttribute("type", bankAccount.getAccountType());

            if(type == BankAccount.AccountType.PAYMENT) req.setAttribute("payment", bankAccount.getAccountType());
            if(type == BankAccount.AccountType.DEPOSIT) req.setAttribute("deposit", bankAccount.getAccountType());
            if(type == BankAccount.AccountType.CREDIT) req.setAttribute("credit", bankAccount.getAccountType());
        } else {

            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }


        req.getRequestDispatcher("templates/paymentTransfers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
