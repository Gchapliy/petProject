package com.myProject.bankSystem.utils;

import com.myProject.bankSystem.entity.bankAccount.BankAccount;
import com.myProject.bankSystem.entity.bankAccount.BankAccountOrder;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

public class CreateBankAccountOrderUtil {

    private static final int STANDARD_EXP_DATE_YEARS = 4;

    public static BankAccountOrder getBankAccountOrderDataFromRequest(HttpServletRequest request) {
        String accType = request.getParameter("accType");

        Calendar c = Calendar.getInstance();
        Date createDate = new Date();
        c.setTime(createDate);

        BankAccountOrder bankAccountOrder = new BankAccountOrder();
        bankAccountOrder.setOrderCreateDate(createDate);
        bankAccountOrder.setOrderOwner(AppUtils.getLoginedUser(request.getSession()));
        bankAccountOrder.setOrderStatus(BankAccountOrder.OrderStatus.IN_PROGRESS);

        if (accType.equals("standard")) {
            c.add(Calendar.YEAR, STANDARD_EXP_DATE_YEARS);

            bankAccountOrder.setAccountExpirationDate(c.getTime()); // standard account for 4 years
            bankAccountOrder.setAccountBalance(0);
            bankAccountOrder.setAccountLimit(0); // if 0 than no limit
            bankAccountOrder.setAccountInterestRate(0);
            bankAccountOrder.setAccountType(BankAccount.AccountType.PAYMENT);

        } else if (accType.equals("deposit")) {
            int depTerm = Integer.parseInt(request.getParameter("depositTerm"));
            double depSum = Double.parseDouble(request.getParameter("depSum"));
            double depPerc = Double.parseDouble(request.getParameter("dep_perc"));

            c.add(Calendar.MONTH, (depTerm));

            bankAccountOrder.setAccountExpirationDate(c.getTime());
            bankAccountOrder.setAccountBalance(depSum);
            bankAccountOrder.setAccountLimit(((depSum * depPerc) * depTerm) + depSum);
            bankAccountOrder.setAccountInterestRate(depPerc);
            bankAccountOrder.setAccountType(BankAccount.AccountType.DEPOSIT);

        } else if (accType.equals("credit")) {
            double credSum = Double.parseDouble(request.getParameter("credSum"));
            int creditTerm = Integer.parseInt(request.getParameter("creditTerm"));
            double credPerc = Double.parseDouble(request.getParameter("cred_perc"));

            c.add(Calendar.MONTH, (creditTerm));

            bankAccountOrder.setAccountExpirationDate(c.getTime());
            bankAccountOrder.setAccountBalance(credSum);
            bankAccountOrder.setAccountBalance(credSum);
            bankAccountOrder.setAccountLimit((credSum * credPerc * (creditTerm + 1)) / (24 * 100)); //(Sc * % (p + 1)) / (24 * 100%) = %%, где Sc – credit summ; % - percent; р – credit term.
            bankAccountOrder.setAccountInterestRate(credPerc);
            bankAccountOrder.setAccountType(BankAccount.AccountType.CREDIT);
        }

        return bankAccountOrder;
    }
}
