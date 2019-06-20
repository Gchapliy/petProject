package com.myProject.bankSystem.controller;

import com.myProject.bankSystem.dao.BankAccountDAO;
import com.myProject.bankSystem.dao.UserDAO;
import com.myProject.bankSystem.entity.bankAccount.BankAccount;
import com.myProject.bankSystem.entity.bankAccount.BankAccountTransaction;
import com.myProject.bankSystem.entity.userAccount.UserAccount;
import com.myProject.bankSystem.utils.AppUtils;
import com.myProject.bankSystem.utils.CreateTransactionUtil;
import com.myProject.bankSystem.utils.LocaleUtils;
import com.myProject.bankSystem.validator.TransferValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "paymentTransfers", urlPatterns = {"/paymentTransfers"})
public class PaymentTransfersServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(PaymentTransfersServlet.class);
    final static private int NUMBER_OF_ERRORS = 7;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleUtils.setLocaleHeaderAndFooter(req);

        String uuid = req.getParameter("uuid");
        UserAccount userAccount = AppUtils.getLoginedUser(req.getSession());

        boolean isDeposit = false;
        boolean[] errors = new boolean[NUMBER_OF_ERRORS];

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

        LocaleUtils.setLocaleTransfersPayment(req, false, new boolean[10]);
        req.getRequestDispatcher("templates/paymentTransfers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleUtils.setLocaleHeaderAndFooter(req);

        String type = req.getParameter("type");
        String uuid = req.getParameter("uuid");

        if(type.equals("transfer")){
            if(TransferValidator.validate(req, resp)){

                BankAccountTransaction transaction = CreateTransactionUtil.createBankAccountTransaction(req);

                if (BankAccountDAO.insertBankAccountTransaction(AppUtils.getStoredConnection(req), transaction))
                    resp.sendRedirect(req.getContextPath() + "/userPage");
                else
                    req.getRequestDispatcher("templates/paymentTransfer.jsp").forward(req, resp);

            } else {

                req.getRequestDispatcher("templates/paymentTransfer.jsp").forward(req, resp);
            }
        }



    }
}
