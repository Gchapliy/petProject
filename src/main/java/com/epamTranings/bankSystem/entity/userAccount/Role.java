package com.epamTranings.bankSystem.entity.userAccount;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Role implements Serializable{

    private int roleID;
    private String roleName;
    private List<UserAccount> accountsRoles;

    public Role() {
        this.accountsRoles = new ArrayList<>();
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<UserAccount> getAccountsRoles() {
        return accountsRoles;
    }

    public void setAccountsRoles(List<UserAccount> accountsRoles) {
        this.accountsRoles = accountsRoles;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleID=" + roleID +
                ", roleName='" + roleName + '\'' +
                ", accountsRoles=" + accountsRoles +
                '}';
    }
}
