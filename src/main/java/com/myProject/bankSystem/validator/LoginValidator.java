package com.myProject.bankSystem.validator;

import com.myProject.bankSystem.dao.UserDAO;
import com.myProject.bankSystem.bean.userAccount.UserAccount;
import com.myProject.bankSystem.utils.AppUtils;
import com.myProject.bankSystem.utils.LocaleUtils;
import com.myProject.bankSystem.utils.SecurityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

public class LoginValidator {
    final static Logger logger = LogManager.getLogger(LoginValidator.class);

    public static boolean validate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userEmail = req.getParameter("userEmail");
        String password = req.getParameter("password");

        UserAccount user = null;

        boolean hasError = false;
        boolean isRequired = false;
        boolean isInvalid = false;

        if (userEmail == null || password == null || userEmail.length() == 0 || password.length() == 0) {
            hasError = true;
            isRequired = true;
        } else {
            Connection conn = AppUtils.getStoredConnection(req);
            // Find the user in the DB.
            user = UserDAO.findUserByEmail(conn, userEmail);

            if (user == null || !SecurityUtils.checkPassword(user.getUserAccountEncryptedPassword(), password)) {
                hasError = true;
                isInvalid = true;
            }
        }

        // If error, forward to login.jsp
        if (hasError) {
            user = new UserAccount();
            user.setUserAccountEmail(userEmail);
            user.setUserAccountEncryptedPassword(password);

            // Store information in request attribute, before forward.
            req.setAttribute("userEmail", user.getUserAccountEmail());

            logger.info("User: " + userEmail + " typed wrong data");

            LocaleUtils.setLocaleLoginPage(req, isInvalid, isRequired);
            return false;

        } else {
            HttpSession session = req.getSession();
            AppUtils.storeLoginedUser(session, user);

            AppUtils.storeUserCookie(resp, user);

            logger.info("User: " + userEmail + " is login");
        }

        return true;
    }

}
