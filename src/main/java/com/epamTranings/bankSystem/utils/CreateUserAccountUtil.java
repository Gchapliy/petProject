package com.epamTranings.bankSystem.utils;

import com.epamTranings.bankSystem.entity.userAccount.UserAccount;

import javax.servlet.http.HttpServletRequest;

public class CreateUserAccountUtil {

    public static UserAccount getUserAccountFromDataRequest(HttpServletRequest request){

        String email = (String) request.getAttribute("userEmail");
        String name = (String) request.getAttribute("userName");
        String phone = (String) request.getAttribute("userPhone");
        String gender = (String) request.getAttribute("userGender");
        String password = (String) request.getAttribute("password");

        UserAccount userAccount = new UserAccount();


        userAccount.setUserAccountEmail(email);
        userAccount.setUserAccountName(name);
        userAccount.setUserAccountPhone(phone);
        userAccount.setUserAccountGender(gender);


        return userAccount;
    }
}
