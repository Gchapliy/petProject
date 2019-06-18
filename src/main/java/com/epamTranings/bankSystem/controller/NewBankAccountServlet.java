package com.epamTranings.bankSystem.controller;

import com.epamTranings.bankSystem.dao.BankAccountDAO;
import com.epamTranings.bankSystem.entity.bankAccount.BankAccountOrder;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import com.epamTranings.bankSystem.utils.AppUtils;
import com.epamTranings.bankSystem.utils.CreateBankAccountOrderUtils;
import com.epamTranings.bankSystem.utils.LocaleUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "newBankAccount", urlPatterns = {"/newBankAccount"})
public class NewBankAccountServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(NewBankAccountServlet.class);

    private final double DEPOSIT_PERCENT = 10;
    private final double CREDIT_PERCENT = 45;

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

        String type = req.getParameter("accType");
        boolean depSumError = false;
        boolean credSumError = false;
        BankAccountOrder bankAccountOrder;

        UserAccount userAccount = AppUtils.getLoginedUser(req.getSession());

        if (type.equals("standard")) {

        } else if (type.equals("deposit")) {

            String depTerm = req.getParameter("depositTerm");
            String depSum = req.getParameter("depSum");

            logger.info("typed depTerm: " + depTerm + ", typed depSum: " + depSum);
            double sum = 0;

            try {
                if (depSum.isEmpty()) throw new Exception();

                sum = Double.parseDouble(depSum);
            } catch (Exception e) {
                logger.error("depSum is wrong");

                depSumError = true;
                LocaleUtils.setLocaleNewBankAccount(req, depSumError, credSumError);

                req.setAttribute("dep_perc", DEPOSIT_PERCENT);
                req.setAttribute("cred_perc", CREDIT_PERCENT);

                req.getRequestDispatcher("templates/newBankAccount.jsp").forward(req, resp);
                return;
            }


        } else if (type.equals("credit")) {

            String creditTerm = req.getParameter("creditTerm");
            String credSum = req.getParameter("credSum");

            logger.info("typed credTerm: " + creditTerm + ", typed credSum: " + credSum);
            double sum = 0;

            try {
                if (credSum.isEmpty()) throw new Exception();

                sum = Double.parseDouble(credSum);
            } catch (Exception e) {
                logger.error("credSum is wrong");

                credSumError = true;
                LocaleUtils.setLocaleNewBankAccount(req, depSumError, credSumError);

                req.setAttribute("dep_perc", DEPOSIT_PERCENT);
                req.setAttribute("cred_perc", CREDIT_PERCENT);

                req.getRequestDispatcher("templates/newBankAccount.jsp").forward(req, resp);
                return;
            }
        }

        bankAccountOrder = CreateBankAccountOrderUtils.getBankAccountOrderDataFromRequest(req);
        if(BankAccountDAO.insertBankAccountOrder(AppUtils.getStoredConnection(req), bankAccountOrder)){
            req.getRequestDispatcher("templates/newBankAccountOrderCreated.jsp").forward(req, resp);
            return;
        }

        doGet(req, resp);
    }
}
