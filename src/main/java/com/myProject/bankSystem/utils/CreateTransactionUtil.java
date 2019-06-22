package com.myProject.bankSystem.utils;

import com.myProject.bankSystem.dao.BankAccountDAO;
import com.myProject.bankSystem.bean.bankAccount.BankAccount;
import com.myProject.bankSystem.bean.bankAccount.BankAccountTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class CreateTransactionUtil {

    final static Logger logger = LogManager.getLogger(CreateTransactionUtil.class);

    private static final String TRANSACTION_TARGET = "transfer";

    public static BankAccountTransaction createBankAccountTransaction(HttpServletRequest request) {

        String uuidFrom = request.getParameter("uuid");
        String uuidTo = request.getParameter("recepAccount");
        String target = request.getParameter("target");

        double transactionAmount = Double.parseDouble(request.getParameter("recepSum"));

        BankAccount from = BankAccountDAO.findBankAccountByUuid(AppUtils.getStoredConnection(request), uuidFrom);
        BankAccount to = BankAccountDAO.findBankAccountByUuid(AppUtils.getStoredConnection(request), uuidTo);

        String transactionTarget = TRANSACTION_TARGET;

        if (target != null && !target.isEmpty()) {
            transactionTarget = target;
        }

        BankAccountTransaction transaction = new BankAccountTransaction();
        transaction.setBankAccountFrom(from);
        transaction.setBankAccountTo(to);
        transaction.setTransactionAmount(transactionAmount);
        transaction.setTransactionDate(new Date());
        transaction.setTransactionTarget(transactionTarget);

        logger.info("transaction created");

        return transaction;
    }
}
