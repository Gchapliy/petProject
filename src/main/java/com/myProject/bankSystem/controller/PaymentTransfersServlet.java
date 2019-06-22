package com.myProject.bankSystem.controller;

import com.myProject.bankSystem.dao.BankAccountDAO;
import com.myProject.bankSystem.bean.bankAccount.BankAccount;
import com.myProject.bankSystem.bean.bankAccount.BankAccountTransaction;
import com.myProject.bankSystem.bean.userAccount.UserAccount;
import com.myProject.bankSystem.pagination.Pagination;
import com.myProject.bankSystem.utils.AppUtils;
import com.myProject.bankSystem.utils.CreateTransactionUtil;
import com.myProject.bankSystem.utils.LocaleUtils;
import com.myProject.bankSystem.validator.PaymentValidator;
import com.myProject.bankSystem.validator.TransferValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@WebServlet(name = "paymentTransfers", urlPatterns = {"/paymentTransfers"})
public class PaymentTransfersServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(PaymentTransfersServlet.class);
    final static private int NUMBER_OF_ERRORS = 9;
    final static private int TOTAL_ITEMS_PER_PAGE = 5;
    private static final int ONE_DAY_IN_MILLIS = (1000 * 60 * 60 * 24);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleUtils.setLocaleHeaderAndFooter(req);

        Connection connection = AppUtils.getStoredConnection(req);

        String uuid = req.getParameter("uuid");
        UserAccount userAccount = AppUtils.getLoginedUser(req.getSession());

        //Locale for numbers
        NumberFormat numberFormat = NumberFormat.getNumberInstance((Locale) req.getAttribute("locale"));
        //Locale for date
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, (Locale) req.getAttribute("locale"));

        String pageId = req.getParameter("page");

        int pageHistoryId = Integer.parseInt(pageId);

        int allHistory = 0;

        Pagination historyPagination = null;

        boolean isDeposit = false;
        boolean isNoHistory = false;
        boolean[] errors = new boolean[NUMBER_OF_ERRORS];

        BankAccount bankAccount = userAccount.getBankAccountByUuid(uuid);

        if (bankAccount != null) {
            req.setAttribute("uuid", bankAccount.getAccountUuid());
            req.setAttribute("balance", bankAccount.getAccountBalance());
            BankAccount.AccountType type = bankAccount.getAccountType();

            req.setAttribute("type", bankAccount.getAccountType());

            allHistory = BankAccountDAO.getBankAccountTransactionsCountByUuid(connection, bankAccount);
            historyPagination = new Pagination(pageHistoryId, allHistory, TOTAL_ITEMS_PER_PAGE);

            if (type == BankAccount.AccountType.PAYMENT) req.setAttribute("payment", "payment");
            else if (type == BankAccount.AccountType.DEPOSIT || type == BankAccount.AccountType.CREDIT){
                req.setAttribute("depositCredit", "depositCredit");

                List<BankAccountTransaction> transactions = BankAccountDAO.findBankAccountTransactionsByUuid(connection, bankAccount, historyPagination.getPageId() - 1, TOTAL_ITEMS_PER_PAGE);

                if(type == BankAccount.AccountType.DEPOSIT)
                    isDeposit = true;

                if(transactions == null || transactions.size() == 0){
                    isNoHistory = true;
                    errors[6] = isNoHistory;

                } else {
                    req.setAttribute("numberFormat", numberFormat);
                    req.setAttribute("dateFormat", dateFormat);
                    req.setAttribute("transactionsHistory", transactions);
                }
            }
        } else {

            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        req.setAttribute("uuid", uuid);
        req.setAttribute("pageHistoryId", pageHistoryId);
        req.setAttribute("allHistory", historyPagination.getPagesArray());
        LocaleUtils.setLocaleTransfersPayment(req, isDeposit, errors);
        req.getRequestDispatcher("templates/paymentTransfers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleUtils.setLocaleHeaderAndFooter(req);

        Connection connection = AppUtils.getStoredConnection(req);

        String type = req.getParameter("type");
        String uuidFrom = req.getParameter("uuid");
        String uuidTo = req.getParameter("recepAccount");

        UserAccount userAccount = AppUtils.getLoginedUser(req.getSession());
        BankAccount bankAccountFrom = BankAccountDAO.findBankAccountByUuid(connection, uuidFrom);
        BankAccount bankAccountTo = BankAccountDAO.findBankAccountByUuid(connection, uuidTo);

        req.setAttribute("uuid", uuidFrom);
        req.setAttribute("type", bankAccountFrom.getAccountType());
        req.setAttribute("balance", bankAccountFrom.getAccountBalance());

        boolean isDeposit = false;
        boolean[] errors = new boolean[NUMBER_OF_ERRORS];

        //Validating transfer data and creating corresponding transaction
        if (type.equals("transfer")) {
            if (TransferValidator.validate(req, resp)) {

                BankAccountTransaction transaction = CreateTransactionUtil.createBankAccountTransaction(req);

                if (BankAccountDAO.insertBankAccountTransaction(connection, transaction)){

                    bankAccountFrom.setAccountBalance(bankAccountFrom.getAccountBalance() - transaction.getTransactionAmount());
                    bankAccountTo.setAccountBalance(bankAccountTo.getAccountBalance() + transaction.getTransactionAmount());

                    if(bankAccountTo.getAccountType() == BankAccount.AccountType.DEPOSIT){
                        double depSum = bankAccountTo.getAccountBalance();
                        double depPerc = bankAccountTo.getAccountInterestRate();
                        long days = (bankAccountTo.getAccountExpirationDate().getTime() - bankAccountTo.getAccountCreationDate().getTime()) / ONE_DAY_IN_MILLIS;
                        Calendar c = Calendar.getInstance();
                        c.setTime(bankAccountTo.getAccountCreationDate());
                        int daysInYear = c.getActualMaximum(Calendar.DAY_OF_YEAR);

                        bankAccountTo.setAccountLimit(((depSum * (depPerc / 100)) * days) / daysInYear + depSum);
                    } else if(bankAccountTo.getAccountType() == BankAccount.AccountType.CREDIT){

                        bankAccountTo.setAccountDebt(bankAccountTo.getAccountDebt() - transaction.getTransactionAmount());
                    }

                    if(BankAccountDAO.updateBankAccount(connection, bankAccountFrom)){
                        logger.info("transfer transaction from completed successfully");
                    } else {
                        req.setAttribute("payment", "payment");
                        req.getRequestDispatcher("templates/paymentTransfers.jsp").forward(req, resp);
                        return;
                    }

                    if(BankAccountDAO.updateBankAccount(connection, bankAccountTo)){
                        logger.info("transfer transaction to completed successfully");
                    } else {
                        req.setAttribute("payment", "payment");
                        req.getRequestDispatcher("templates/paymentTransfers.jsp").forward(req, resp);
                        return;
                    }

                    resp.sendRedirect(req.getContextPath() + "/userPage?pageA=1&pageUsO=1&pageYO=1");
                    return;
                }
                else {
                    req.setAttribute("payment", "payment");
                    req.getRequestDispatcher("templates/paymentTransfers.jsp").forward(req, resp);
                    return;
                }

            } else {
                req.setAttribute("payment", "payment");
                req.getRequestDispatcher("templates/paymentTransfers.jsp").forward(req, resp);
                return;
            }

            //Validating payment data and creating corresponding transaction
        } else if(type.equals("payment")){

            if(PaymentValidator.validate(req, resp)){

                BankAccountTransaction transaction = CreateTransactionUtil.createBankAccountTransaction(req);

                if (BankAccountDAO.insertBankAccountTransaction(connection, transaction)){

                    bankAccountFrom.setAccountBalance(bankAccountFrom.getAccountBalance() - transaction.getTransactionAmount());
                    bankAccountTo.setAccountBalance(bankAccountTo.getAccountBalance() + transaction.getTransactionAmount());

                    if(BankAccountDAO.updateBankAccount(connection, bankAccountFrom)){
                        logger.info("payment transaction from completed successfully");
                    } else {
                        req.setAttribute("payment", "payment");
                        req.getRequestDispatcher("templates/paymentTransfers.jsp").forward(req, resp);
                        return;
                    }
                    if(BankAccountDAO.updateBankAccount(connection, bankAccountTo)){
                        logger.info("payment transaction to completed successfully");
                    } else {
                        req.setAttribute("payment", "payment");
                        req.getRequestDispatcher("templates/paymentTransfers.jsp").forward(req, resp);
                        return;
                    }

                    resp.sendRedirect(req.getContextPath() + "/userPage?pageA=1&pageUsO=1&pageYO=1");
                    return;
                }
                else {
                    req.setAttribute("payment", "payment");
                    req.getRequestDispatcher("templates/paymentTransfers.jsp").forward(req, resp);
                    return;
                }
            } else {
                req.setAttribute("payment", "payment");
                req.getRequestDispatcher("templates/paymentTransfers.jsp").forward(req, resp);
                return;
            }
        }
    }
}
