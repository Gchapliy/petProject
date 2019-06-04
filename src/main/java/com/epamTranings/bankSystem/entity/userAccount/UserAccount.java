package com.epamTranings.bankSystem.entity.userAccount;

import com.epamTranings.bankSystem.entity.bankAccount.BankAccount;

import java.io.Serializable;
import java.util.List;

public class UserAccount implements Serializable{
    private int userAccountId;
    private String userAccountName;
    private String userAccountGender;
    private String userAccountEncryptedPassword;
    private Role userAccountRole;
    private String userAccountEmail;
    private String userAccountPhone;
    private List<BankAccount> userBankAccounts;

    public UserAccount() {
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getUserAccountName() {
        return userAccountName;
    }

    public void setUserAccountName(String userAccountName) {
        this.userAccountName = userAccountName;
    }

    public String getUserAccountGender() {
        return userAccountGender;
    }

    public void setUserAccountGender(String userAccountGender) {
        this.userAccountGender = userAccountGender;
    }

    public String getUserAccountEncryptedPassword() {
        return userAccountEncryptedPassword;
    }

    public void setUserAccountEncryptedPassword(String userAccountEncryptedPassword) {
        this.userAccountEncryptedPassword = userAccountEncryptedPassword;
    }

    public Role getUserAccountRole() {
        return userAccountRole;
    }

    public void setUserAccountRole(Role userAccountRole) {
        this.userAccountRole = userAccountRole;
    }

    public String getUserAccountEmail() {
        return userAccountEmail;
    }

    public void setUserAccountEmail(String userAccountEmail) {
        this.userAccountEmail = userAccountEmail;
    }

    public String getUserAccountPhone() {
        return userAccountPhone;
    }

    public void setUserAccountPhone(String userAccountPhone) {
        this.userAccountPhone = userAccountPhone;
    }

    public List<BankAccount> getUserBankAccounts() {
        return userBankAccounts;
    }

    public void setUserBankAccounts(List<BankAccount> userBankAccounts) {
        this.userBankAccounts = userBankAccounts;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "userAccountId=" + userAccountId +
                ", userAccountName='" + userAccountName + '\'' +
                ", userAccountGender='" + userAccountGender + '\'' +
                ", userAccountRole=" + userAccountRole +
                ", userAccountEmail='" + userAccountEmail + '\'' +
                ", userAccountPhone='" + userAccountPhone + '\'' +
                '}';
    }
}
