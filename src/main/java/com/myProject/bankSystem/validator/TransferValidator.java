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
    final static private int NUMBER_OF_ERRORS = 7;
    /*
        0 - isTransferSpecifyInvalid
        1 - isTransferSumInvalid
        2 - isRequiredTransfer
        3 - isPaySpecifyInvalid
        4 - isPayTransferSumInvalid
        5 - isRequiredPay
        6 - isNoHistory
    */

    public static boolean validate(HttpServletRequest request, HttpServletResponse response){

        String recepAccount = request.getParameter("recepAccount");
        String recepSum = request.getParameter("recepSum");

        logger.info("transfer data: " + recepAccount + ", " + recepSum);

        boolean isRequiredTransfer = false;
        boolean isTransferSpecifyInvalid = false;
        boolean isTransferSumInvalid = false;
        boolean hasError = false;

        boolean[] errors = new boolean[NUMBER_OF_ERRORS];

        if(recepAccount == null || recepSum == null){
            isRequiredTransfer = true;
            errors[2] = isRequiredTransfer;

            if(recepAccount != null) request.setAttribute("recepAccount", recepAccount);
            if(recepSum != null) request.setAttribute("recepSum", recepSum);

            LocaleUtils.setLocaleTransfersPayment(request, false, errors);

            logger.error("input data is null");
            return false;
        }

        BankAccount bankAccount = BankAccountDAO.findBankAccountByUuid(AppUtils.getStoredConnection(request), recepAccount);

        if(bankAccount == null){
            hasError = true;
            isTransferSpecifyInvalid = true;
            errors[0] = isTransferSpecifyInvalid;

            logger.error("transfer specify is invalid");
        }

        double sum;

        try {
            sum = Double.parseDouble(recepSum);
        } catch (Exception e){
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
