package com.myProject.bankSystem.validator;

import com.myProject.bankSystem.controller.CreateBankAccountOrderServlet;
import com.myProject.bankSystem.utils.LocaleUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class BankAccountOrderValidator {
    final static Logger logger = LogManager.getLogger(BankAccountOrderValidator.class);

    public static boolean validate(HttpServletRequest req){
        String type = req.getParameter("accType");
        boolean depSumError = false;
        boolean credSumError = false;

         if (type.equals("deposit")) {

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

                req.setAttribute("dep_perc", CreateBankAccountOrderServlet.DEPOSIT_PERCENT);
                req.setAttribute("cred_perc", CreateBankAccountOrderServlet.CREDIT_PERCENT);

                return false;
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

                req.setAttribute("dep_perc", CreateBankAccountOrderServlet.DEPOSIT_PERCENT);
                req.setAttribute("cred_perc", CreateBankAccountOrderServlet.CREDIT_PERCENT);

                return false;
            }
        }

        return true;
    }
}
