package com.epamTranings.bankSystem.entity.bankAccount;

import com.epamTranings.bankSystem.entity.userAccount.UserAccount;

import java.util.Date;

public class BankAccount {
    private int accountId;
    private double accountBalance;
    private Date accountCreateDate;
    private Date accountExpirationDate;
    private UserAccount accountOwner;
    private double accountInterestRate;
    private double accountDebt;
    private double accountLimit;
    private AccountType accountType;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Date getAccountCreateDate() {
        return accountCreateDate;
    }

    public void setAccountCreateDate(Date accountCreateDate) {
        this.accountCreateDate = accountCreateDate;
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

    public void setAccountInterestRate(int accountInterestRate) {
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
                "accountBalance=" + accountBalance +
                ", accountCreateDate=" + accountCreateDate +
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
