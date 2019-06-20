package com.myProject.bankSystem.entity.bankAccount;

import com.myProject.bankSystem.entity.userAccount.UserAccount;

import java.io.Serializable;
import java.util.Date;

public class BankAccountOrder implements Serializable {

    private int orderId;
    private Date orderCreateDate;
    private UserAccount orderOwner;
    private OrderStatus orderStatus;
    private Date accountExpirationDate;
    private double accountBalance;
    private double accountLimit;
    private double accountInterestRate;
    private BankAccount.AccountType accountType;

    public enum OrderStatus{
        IN_PROGRESS, ALLOWED, REJECTED
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderCreateDate() {
        return orderCreateDate;
    }

    public void setOrderCreateDate(Date orderCreateDate) {
        this.orderCreateDate = orderCreateDate;
    }

    public UserAccount getOrderOwner() {
        return orderOwner;
    }

    public void setOrderOwner(UserAccount orderOwner) {
        this.orderOwner = orderOwner;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getAccountExpirationDate() {
        return accountExpirationDate;
    }

    public void setAccountExpirationDate(Date accountExpirationDate) {
        this.accountExpirationDate = accountExpirationDate;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(double accountLimit) {
        this.accountLimit = accountLimit;
    }

    public double getAccountInterestRate() {
        return accountInterestRate;
    }

    public void setAccountInterestRate(double accountInterestRate) {
        this.accountInterestRate = accountInterestRate;
    }

    public BankAccount.AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(BankAccount.AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "BankAccountOrder{" +
                "orderId=" + orderId +
                ", orderCreateDate=" + orderCreateDate +
                ", orderOwner=" + orderOwner.getUserAccountEmail() +
                ", orderStatus=" + orderStatus +
                ", accountExpirationDate=" + accountExpirationDate +
                ", accountBalance=" + accountBalance +
                ", accountLimit=" + accountLimit +
                ", accountInterestRate=" + accountInterestRate +
                ", accountType=" + accountType +
                '}';
    }
}
