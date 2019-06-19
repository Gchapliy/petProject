package com.epamTranings.bankSystem.utils;

import com.epamTranings.bankSystem.dao.UserDAO;
import com.epamTranings.bankSystem.entity.userAccount.Role;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import com.epamTranings.bankSystem.validator.LoginValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

public class CreateUserAccountUtil {
    final static Logger logger = LogManager.getLogger(CreateUserAccountUtil.class);


    public static UserAccount getUserAccountFromDataRequest(HttpServletRequest request){

        String email = request.getParameter("userEmail");
        String name = request.getParameter("userName");
        String phone = request.getParameter("userPhone");
        String gender = request.getParameter("userGender");
        String password = request.getParameter("password");

        String encryptedPassword = SecurityUtils.encodePassword(password);

        UserAccount userAccount = new UserAccount();
        Role roleUser = UserDAO.findUserRoleByName(AppUtils.getStoredConnection(request), "ROLE_USER");

        userAccount.setUserAccountEmail(email);
        userAccount.setUserAccountName(name);
        userAccount.setUserAccountPhone(phone);
        userAccount.setUserAccountGender(gender);
        userAccount.setUserAccountEncryptedPassword(encryptedPassword);
        userAccount.setAccountCreateDate(new Date());
        userAccount.setUserAccountRole(roleUser);

        logger.info("user account with email " + email + " created");

        return userAccount;
    }
}
