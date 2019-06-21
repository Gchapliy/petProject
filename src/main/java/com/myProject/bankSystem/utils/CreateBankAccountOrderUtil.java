package com.myProject.bankSystem.utils;

import com.myProject.bankSystem.entity.bankAccount.BankAccount;
import com.myProject.bankSystem.entity.bankAccount.BankAccountOrder;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

public class CreateBankAccountOrderUtil {

    private static final int STANDARD_EXP_DATE_YEARS = 4;
    private static final int ONE_DAY_IN_MILLIS = (1000 * 60 * 60 * 24);

    public static BankAccountOrder getBankAccountOrderDataFromRequest(HttpServletRequest request) {
        String accType = request.getParameter("accType");

        Calendar create = Calendar.getInstance();
        Calendar expiration = Calendar.getInstance();
        int daysInYear = create.getActualMaximum(Calendar.DAY_OF_YEAR);

        Date createDate = new Date();
        create.setTime(createDate);
        expiration.setTime(createDate);

        BankAccountOrder bankAccountOrder = new BankAccountOrder();
        bankAccountOrder.setOrderCreateDate(createDate);
        bankAccountOrder.setOrderOwner(AppUtils.getLoginedUser(request.getSession()));
        bankAccountOrder.setOrderStatus(BankAccountOrder.OrderStatus.IN_PROGRESS);

        if (accType.equals("standard")) {
            double stndSum = Double.parseDouble(request.getParameter("stndSum"));
            expiration.add(Calendar.YEAR, STANDARD_EXP_DATE_YEARS);

            bankAccountOrder.setAccountExpirationDate(expiration.getTime()); // standard account for 4 years
            bankAccountOrder.setAccountBalance(stndSum);
            bankAccountOrder.setAccountLimit(0); // if 0 than no limit
            bankAccountOrder.setAccountInterestRate(0);
            bankAccountOrder.setAccountType(BankAccount.AccountType.PAYMENT);

        } else if (accType.equals("deposit")) {
            int depTerm = Integer.parseInt(request.getParameter("depositTerm"));
            double depSum = Double.parseDouble(request.getParameter("depSum"));
            double depPerc = Double.parseDouble(request.getParameter("dep_perc"));

            expiration.add(Calendar.MONTH, (depTerm));

            long days = (expiration.getTime().getTime() - create.getTime().getTime()) / (ONE_DAY_IN_MILLIS);

            bankAccountOrder.setAccountExpirationDate(expiration.getTime());
            bankAccountOrder.setAccountBalance(depSum);
            bankAccountOrder.setAccountLimit(((depSum * (depPerc / 100)) * days) / daysInYear + depSum);
            bankAccountOrder.setAccountInterestRate(depPerc);
            bankAccountOrder.setAccountType(BankAccount.AccountType.DEPOSIT);

            System.out.println("days: " + days + ", daysInYear:" + daysInYear + ", depPerc: " + depPerc + ", depTerm:" + depTerm);

        } else if (accType.equals("credit")) {
            double credSum = Double.parseDouble(request.getParameter("credSum"));
            int creditTerm = Integer.parseInt(request.getParameter("creditTerm"));
            double credPerc = Double.parseDouble(request.getParameter("cred_perc"));

            expiration.add(Calendar.MONTH, (creditTerm));

            bankAccountOrder.setAccountExpirationDate(expiration.getTime());
            bankAccountOrder.setAccountBalance(credSum);
            bankAccountOrder.setAccountLimit((credSum * credPerc * (creditTerm + 1)) / (24 * 100) + credSum); //(Sc * % (p + 1)) / (24 * 100%) = %%, где Sc – credit summ; % - percent; р – credit term.
            bankAccountOrder.setAccountInterestRate(credPerc);
            bankAccountOrder.setAccountType(BankAccount.AccountType.CREDIT);
        }

        return bankAccountOrder;
    }
}
