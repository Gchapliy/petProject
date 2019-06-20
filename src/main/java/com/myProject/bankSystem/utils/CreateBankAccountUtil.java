package com.myProject.bankSystem.utils;

import com.myProject.bankSystem.dao.BankAccountDAO;
import com.myProject.bankSystem.entity.bankAccount.BankAccount;
import com.myProject.bankSystem.entity.bankAccount.BankAccountOrder;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class CreateBankAccountUtil {

    public static BankAccount createBankAccount(HttpServletRequest request, BankAccountOrder order){

        BankAccount bankAccount = new BankAccount();

        bankAccount.setAccountBalance(order.getAccountBalance());
        bankAccount.setAccountCreationDate(order.getOrderCreateDate());
        bankAccount.setAccountExpirationDate(order.getAccountExpirationDate());
        bankAccount.setAccountInterestRate(order.getAccountInterestRate());
        bankAccount.setAccountLimit(order.getAccountLimit());
        bankAccount.setAccountOwner(order.getOrderOwner());
        bankAccount.setAccountType(order.getAccountType());

        if(order.getAccountType() == BankAccount.AccountType.PAYMENT || order.getAccountType() == BankAccount.AccountType.DEPOSIT){
            bankAccount.setAccountDebt(0);
        } else if(order.getAccountType() == BankAccount.AccountType.CREDIT){
            bankAccount.setAccountDebt(order.getAccountLimit());
        }

        while (true){
            byte[] bytes = (order.getAccountBalance() + "" +
                    "" + order.getOrderCreateDate() + "" +
                    "" + order.getAccountExpirationDate() + "" +
                    "" + order.getAccountInterestRate() + "" +
                    "" + order.getAccountLimit() + "" +
                    "" + order.getOrderOwner().getUserAccountEmail() +
                    "" + order.getAccountType()).getBytes();

            UUID uuid = UUID.nameUUIDFromBytes(bytes);

            if(BankAccountDAO.findBankAccountByUuid(AppUtils.getStoredConnection(request), uuid.toString()) == null){
                bankAccount.setAccountUuid(uuid.toString());
                break;
            }
        }

        return bankAccount;
    }
}
