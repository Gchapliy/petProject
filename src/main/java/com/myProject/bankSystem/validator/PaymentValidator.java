package com.myProject.bankSystem.validator;

import com.myProject.bankSystem.dao.BankAccountDAO;
import com.myProject.bankSystem.bean.bankAccount.BankAccount;
import com.myProject.bankSystem.utils.AppUtils;
import com.myProject.bankSystem.utils.LocaleUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PaymentValidator {
    final static Logger logger = LogManager.getLogger(PaymentValidator.class);

    final static private int NUMBER_OF_ERRORS = 9;
    final static private int TARGET_LENGTH = 10;
    /*
        0 - isTransferSpecifyInvalid
        1 - isTransferSumInvalid
        2 - isRequiredTransfer
        3 - isPaySpecifyInvalid
        4 - isPayTransferSumInvalid
        5 - isRequiredPay
        6 - isNoHistory
        7 - isPayTargetInvalid
        8 - isAccountTransferInvalid
    */

    /**
     * Validate user data from payment interface
     *
     * @param request
     * @param response
     * @return
     */
    public static boolean validate(HttpServletRequest request, HttpServletResponse response) {

        String uuidFrom = request.getParameter("uuid");
        String recepAccount = request.getParameter("recepAccount");
        String recepSum = request.getParameter("recepSum");
        String target = request.getParameter("target").replaceAll("[<>!@#$%&*()_]+", "");

        double balance = (double) request.getAttribute("balance");

        logger.info("payment data: " + recepAccount + ", " + recepSum + ", " + balance + ", " + target);

        boolean isRequiredPayment = false;
        boolean isPaymentSpecifyInvalid = false;
        boolean isPaymentSumInvalid = false;
        boolean isPaymentTargetInvalid = false;
        boolean hasError = false;

        boolean[] errors = new boolean[NUMBER_OF_ERRORS];

        if (recepAccount == null || recepSum == null || target == null) {
            isRequiredPayment = true;
            errors[5] = isRequiredPayment;

            if (recepAccount != null) request.setAttribute("recepAccountPay", recepAccount);
            if (target != null) request.setAttribute("target", target);
            if (recepSum != null) request.setAttribute("recepSumPay", recepSum);

            LocaleUtils.setLocaleTransfersPayment(request, false, errors);

            logger.error("input data is null");
            return false;
        }

        if (recepAccount.isEmpty() || recepSum.isEmpty() || target.isEmpty()) {
            hasError = true;
            isRequiredPayment = true;
            errors[5] = isRequiredPayment;

            if (recepAccount != null) request.setAttribute("recepAccountPay", recepAccount);
            if (target != null) request.setAttribute("target", target);
            if (recepSum != null) request.setAttribute("recepSumPay", recepSum);

            logger.error("input data is empty");
        }

        BankAccount bankAccount = BankAccountDAO.findBankAccountByUuid(AppUtils.getStoredConnection(request), recepAccount);

        if (bankAccount == null || bankAccount.getAccountUuid().equals(uuidFrom)) {
            hasError = true;
            isPaymentSpecifyInvalid = true;
            errors[3] = isPaymentSpecifyInvalid;

            logger.error("payment specify is invalid");
        }

        if (target.length() < TARGET_LENGTH) {
            hasError = true;
            isPaymentTargetInvalid = true;
            errors[7] = isPaymentTargetInvalid;

            logger.error("payment target is invalid");
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
            isPaymentSumInvalid = true;
            errors[4] = isPaymentSumInvalid;

            logger.error("payment sum is invalid");
        }

        if (hasError) {

            request.setAttribute("recepAccountPay", recepAccount);
            request.setAttribute("target", target);
            request.setAttribute("recepSumPay", recepSum);

            LocaleUtils.setLocaleTransfersPayment(request, false, errors);

            return false;
        }

        return true;
    }
}
