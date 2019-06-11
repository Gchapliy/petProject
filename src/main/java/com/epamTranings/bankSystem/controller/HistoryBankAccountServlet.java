package com.epamTranings.bankSystem.controller;

import com.epamTranings.bankSystem.dao.BankAccountDAO;
import com.epamTranings.bankSystem.entity.bankAccount.BankAccount;
import com.epamTranings.bankSystem.entity.bankAccount.BankAccountTransaction;
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
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@WebServlet(name = "historyBankAccount", urlPatterns = {"/history"})
public class HistoryBankAccountServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(HistoryBankAccountServlet.class);

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
        List<BankAccountTransaction> bankAccountTransactions;

        if (bankAccountUuid != null) {
            bankAccount = userAccount.getBankAccountByUuid(bankAccountUuid);
            if(bankAccount != null){

                logger.info("bank account uuid - " + bankAccountUuid + " found");

                bankAccountTransactions = BankAccountDAO.findBankAccountTransactionsByUuid(AppUtils.getStoredConnection(req), bankAccountUuid);
                if(bankAccountTransactions == null || bankAccountTransactions.size() == 0){
                    logger.info("bank account history - " + bankAccountUuid + " not found");
                    req.setAttribute("noBankAccountHistory", "bank account history - " + bankAccountUuid + " not found");
                    req.getRequestDispatcher("templates/bankAccountHistory.jsp").forward(req, resp);
                }

                req.setAttribute("history", bankAccountTransactions);
                req.setAttribute("dateFormat", dateFormat);
                req.setAttribute("numberFormat", numberFormat);
                req.setAttribute("bankAccount", bankAccount);
            }
            else {
                logger.info("bank account uuid - " + bankAccountUuid + " not found");
                req.setAttribute("noBankAccount", "bank account uuid - " + bankAccountUuid + " not found");
            }
        } else {
            req.getRequestDispatcher("templates/404.jsp").forward(req, resp);
        }

        req.setAttribute("depositType", BankAccount.AccountType.DEPOSIT);
        req.setAttribute("creditType", BankAccount.AccountType.CREDIT);
        LocaleUtils.setLocaleHeaderAndFooter(req);
        LocaleUtils.setLocaleBankAccountHistory(req);
        req.getRequestDispatcher("templates/bankAccountHistory.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
