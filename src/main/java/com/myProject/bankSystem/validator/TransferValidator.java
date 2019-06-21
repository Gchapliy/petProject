package com.myProject.bankSystem.validator;

import com.myProject.bankSystem.dao.BankAccountDAO;
import com.myProject.bankSystem.entity.bankAccount.BankAccount;
import com.myProject.bankSystem.utils.AppUtils;
import com.myProject.bankSystem.utils.LocaleUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TransferValidator {

    final static Logger logger = LogManager.getLogger(TransferValidator.class);
    final static private int NUMBER_OF_ERRORS = 8;
    /*
        0 - isTransferSpecifyInvalid
        1 - isTransferSumInvalid
        2 - isRequiredTransfer
        3 - isPaySpecifyInvalid
        4 - isPayTransferSumInvalid
        5 - isRequiredPay
        6 - isNoHistory
        7 - isPayTargetInvalid
    */

    /**
     * Validate user data from transfer interface
     *
     * @param request
     * @param response
     * @return
     */
    public static boolean validate(HttpServletRequest request, HttpServletResponse response) {

        String uuidFrom = request.getParameter("uuid");
        String recepAccount = request.getParameter("recepAccount");
        String recepSum = request.getParameter("recepSum");

        double balance = (double) request.getAttribute("balance");

        logger.info("transfer data: " + recepAccount + ", " + recepSum + ", " + balance);

        boolean isRequiredTransfer = false;
        boolean isTransferSpecifyInvalid = false;
        boolean isTransferSumInvalid = false;
        boolean hasError = false;

        boolean[] errors = new boolean[NUMBER_OF_ERRORS];

        if (recepAccount == null || recepSum == null) {
            isRequiredTransfer = true;
            errors[2] = isRequiredTransfer;

            if (recepAccount != null) request.setAttribute("recepAccount", recepAccount);
            if (recepSum != null) request.setAttribute("recepSum", recepSum);

            LocaleUtils.setLocaleTransfersPayment(request, false, errors);

            logger.error("input data is null");
            return false;
        }

        if (recepAccount.isEmpty() || recepSum.isEmpty()) {
            hasError = true;
            isRequiredTransfer = true;
            errors[2] = isRequiredTransfer;

            if (recepAccount != null) request.setAttribute("recepAccount", recepAccount);
            if (recepSum != null) request.setAttribute("recepSum", recepSum);

            logger.error("input data is empty");
        }

        BankAccount bankAccount = BankAccountDAO.findBankAccountByUuid(AppUtils.getStoredConnection(request), recepAccount);

        if (bankAccount == null || bankAccount.getAccountUuid().equals(uuidFrom)) {
            hasError = true;
            isTransferSpecifyInvalid = true;
            errors[0] = isTransferSpecifyInvalid;

            logger.error("transfer specify is invalid");
        }

        double sum;

        try {
            sum = Double.parseDouble(recepSum);
            if (sum <= 0) throw new IllegalArgumentException();
            if (sum > balance) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            hasError = true;
            isTransferSumInvalid = true;
            errors[1] = isTransferSumInvalid;

            logger.error("transfer sum is invalid");
        }

        if (hasError) {

            request.setAttribute("recepAccount", recepAccount);
            request.setAttribute("recepSum", recepSum);

            LocaleUtils.setLocaleTransfersPayment(request, false, errors);

            return false;
        }

        return true;
    }
}
