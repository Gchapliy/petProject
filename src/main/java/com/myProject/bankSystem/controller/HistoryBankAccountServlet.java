package com.myProject.bankSystem.controller;

import com.myProject.bankSystem.dao.BankAccountDAO;
import com.myProject.bankSystem.entity.bankAccount.BankAccount;
import com.myProject.bankSystem.entity.bankAccount.BankAccountTransaction;
import com.myProject.bankSystem.entity.userAccount.UserAccount;
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
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@WebServlet(name = "historyBankAccount", urlPatterns = {"/history"})
public class HistoryBankAccountServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(HistoryBankAccountServlet.class);
    private final int TOTAL_ITEMS_PER_PAGE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserAccount userAccount = AppUtils.getLoginedUser(req.getSession());

        Connection connection = AppUtils.getStoredConnection(req);

        String bankAccountUuid = req.getParameter("uuid");

        Locale locale = (Locale) req.getAttribute("locale");

        String pageId = req.getParameter("page");

        int pageHistoryId = Integer.parseInt(pageId);

        int allHistory = 0;

        Pagination historyPagination = null;

        //Locale for Date
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);

        //Locale for Number
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);

        BankAccount bankAccount;

        List<BankAccountTransaction> bankAccountTransactions;

        boolean noHistory = false;

        if (bankAccountUuid != null) {
            bankAccount = userAccount.getBankAccountByUuid(bankAccountUuid);

            if (bankAccount != null) {

                logger.info("bank account uuid - " + bankAccountUuid + " found");

                allHistory = BankAccountDAO.getBankAccountTransactionsCountByUuid(connection, bankAccount);
                historyPagination = new Pagination(pageHistoryId, allHistory, TOTAL_ITEMS_PER_PAGE);

                bankAccountTransactions = BankAccountDAO.findBankAccountTransactionsByUuid(connection, bankAccount, historyPagination.getPageId() - 1, TOTAL_ITEMS_PER_PAGE);

                if (bankAccountTransactions == null || bankAccountTransactions.size() == 0) {
                    logger.info("bank account history - " + bankAccountUuid + " not found");

                    req.setAttribute("link", "bankAccount?uuid=" + bankAccountUuid);
                    req.setAttribute("uuid", bankAccountUuid);

                    noHistory = true;

                    LocaleUtils.setLocaleHeaderAndFooter(req);
                    LocaleUtils.setLocaleBankAccountHistory(req, noHistory);
                    LocaleUtils.setLocaleManagingInterface(req);

                    req.getRequestDispatcher("templates/bankAccountHistory.jsp").forward(req, resp);
                    return;
                }

                req.setAttribute("pageIdHistory", pageHistoryId);
                req.setAttribute("uuid", bankAccountUuid);
                req.setAttribute("transactionsHistory", bankAccountTransactions);
                req.setAttribute("dateFormat", dateFormat);
                req.setAttribute("numberFormat", numberFormat);
                req.setAttribute("bankAccount", bankAccount);
            } else {
                logger.info("bank account uuid - " + bankAccountUuid + " not found");
                noHistory = true;
            }
        } else {
            resp.sendError(resp.SC_NOT_FOUND);
            return;
        }

        req.setAttribute("allHistory", historyPagination.getPagesArray());
        req.setAttribute("link", "bankAccount?uuid=" + bankAccountUuid);
        req.setAttribute("pageIdHistory", pageHistoryId);

        LocaleUtils.setLocaleHeaderAndFooter(req);
        LocaleUtils.setLocaleBankAccountHistory(req, noHistory);
        LocaleUtils.setLocaleManagingInterface(req);

        req.getRequestDispatcher("templates/bankAccountHistory.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
