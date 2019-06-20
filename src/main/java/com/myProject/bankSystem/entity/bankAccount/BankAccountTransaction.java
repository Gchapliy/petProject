package com.myProject.bankSystem.entity.bankAccount;

import java.io.Serializable;
import java.util.Date;

public class BankAccountTransaction implements Serializable{

    private int transactionId;
    private BankAccount bankAccountFrom;
    private BankAccount bankAccountTo;
    private Date transactionDate;
    private double transactionAmount;
    private String transactionTarget;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public BankAccount getBankAccountFrom() {
        return bankAccountFrom;
    }

    public void setBankAccountFrom(BankAccount bankAccountFrom) {
        this.bankAccountFrom = bankAccountFrom;
    }

    public BankAccount getBankAccountTo() {
        return bankAccountTo;
    }

    public void setBankAccountTo(BankAccount bankAccountTo) {
        this.bankAccountTo = bankAccountTo;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionTarget() {
        return transactionTarget;
    }

    public void setTransactionTarget(String transactionTarget) {
        this.transactionTarget = transactionTarget;
    }

    @Override
    public String toString() {
        return "BankAccountTransaction{" +
                "transactionId=" + transactionId +
                ", bankAccountFrom=" + bankAccountFrom +
                ", bankAccountTo=" + bankAccountTo +
                ", transactionDate=" + transactionDate +
                ", transactionAmount=" + transactionAmount +
                ", transactionTarget='" + transactionTarget + '\'' +
                '}';
    }
}
