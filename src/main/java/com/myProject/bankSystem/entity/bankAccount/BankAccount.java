package com.myProject.bankSystem.entity.bankAccount;

import com.myProject.bankSystem.entity.userAccount.UserAccount;

import java.io.Serializable;
import java.util.Date;

public class BankAccount implements Serializable{
    private String accountUuid;
    private double accountBalance;
    private Date accountCreationDate;
    private Date accountExpirationDate;
    private UserAccount accountOwner;
    private double accountInterestRate;
    private double accountDebt;
    private double accountLimit;
    private AccountType accountType;

    public String getAccountUuid() {
        return accountUuid;
    }

    public void setAccountUuid(String accountUuid) {
        this.accountUuid = accountUuid;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Date getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(Date accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public Date getAccountExpirationDate() {
        return accountExpirationDate;
    }

    public void setAccountExpirationDate(Date accountExpirationDate) {
        this.accountExpirationDate = accountExpirationDate;
    }

    public UserAccount getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(UserAccount accountOwner) {
        this.accountOwner = accountOwner;
    }

    public double getAccountInterestRate() {
        return accountInterestRate;
    }

    public void setAccountInterestRate(double accountInterestRate) {
        this.accountInterestRate = accountInterestRate;
    }

    public double getAccountDebt() {
        return accountDebt;
    }

    public void setAccountDebt(double accountDebt) {
        this.accountDebt = accountDebt;
    }

    public double getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(double accountLimit) {
        this.accountLimit = accountLimit;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountUuid='" + accountUuid + '\'' +
                ", accountBalance=" + accountBalance +
                ", accountCreationDate=" + accountCreationDate +
                ", accountExpirationDate=" + accountExpirationDate +
                ", accountOwner=" + accountOwner +
                ", accountInterestRate=" + accountInterestRate +
                ", accountDebt=" + accountDebt +
                ", accountLimit=" + accountLimit +
                ", accountType=" + accountType +
                '}';
    }

    public enum AccountType{
        PAYMENT, DEPOSIT, CREDIT
    }
}
