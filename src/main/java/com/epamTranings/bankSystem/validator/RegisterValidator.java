package com.epamTranings.bankSystem.validator;

import com.epamTranings.bankSystem.dao.UserDAO;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import com.epamTranings.bankSystem.utils.AppUtils;
import com.epamTranings.bankSystem.utils.LocaleUtils;
import com.epamTranings.bankSystem.utils.SecurityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class RegisterValidator {

    final static Logger logger = LogManager.getLogger(RegisterValidator.class);
    final static private int NUMBER_OF_ERRORS = 7;

    public static boolean validate(HttpServletRequest request, HttpServletResponse response) {

        String email = request.getParameter("userEmail");
        String name = request.getParameter("userName");
        String phone = request.getParameter("userPhone");
        String gender = request.getParameter("userGender");
        String password = request.getParameter("password");
        String repPassword = request.getParameter("repPassword");

        UserAccount user = null;

        String phoneRegex = "^\\d{4}-\\d{3}-\\d{4}$";
        String emailRegex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        String nameRegex = "[A-Z][a-z]*";
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$";
        /*
            ^                 # start-of-string
            (?=.*[0-9])       # a digit must occur at least once
            (?=.*[a-z])       # a lower case letter must occur at least once
            (?=.*[A-Z])       # an upper case letter must occur at least once
            (?=.*[@#$%^&+=])  # a special character must occur at least once
            (?=\S+$)          # no whitespace allowed in the entire string
            .{8,}             # anything, at least eight places though
            $                 # end-of-string*/

        boolean isUserExists = false;
        boolean isRequired = false;
        boolean isEmailInvalid = false;
        boolean isNameInvalid = false;
        boolean isPhoneInvalid = false;
        boolean isPasswordsNotEquals = false;
        boolean isPasswordInvalid = false;
        boolean hasError = false;

        boolean[] errors = new boolean[NUMBER_OF_ERRORS];

        logger.info("DATA: " + email + ", " + name + ", " + phone + ", " + gender + ", " + password + ", " + repPassword);

        if (email == null || name == null || phone == null || gender == null || password == null || repPassword == null) {
            isRequired = true;

            errors[0] = isRequired;

            if (email != null) request.setAttribute("userEmail", email);
            if (name != null) request.setAttribute("userName", name);
            if (phone != null) request.setAttribute("userPhone", phone);

            if (gender != null && gender.equals("male")) request.setAttribute("userGenderMale", "male");
            else if (gender != null && gender.equals("female")) request.setAttribute("userGenderFemale", "female");

            LocaleUtils.setLocaleRegisterPage(request, errors);

            logger.error("input data is null");
            return false;
        }

        if (email.isEmpty() || name.isEmpty() || phone.isEmpty() || gender.isEmpty() || password.isEmpty() || repPassword.isEmpty()) {
            isRequired = true;
            hasError = true;

            errors[0] = isRequired;
            logger.error("input data is empty");

        }

        if (!email.matches(emailRegex)) {
            hasError = true;
            isEmailInvalid = true;

            errors[1] = isEmailInvalid;

            logger.error("email is invalid");
        }

        if (!name.matches(nameRegex)) {
            hasError = true;
            isNameInvalid = true;

            errors[2] = isNameInvalid;

            logger.error("name is invalid");
        }

        if (!phone.matches(phoneRegex)) {
            hasError = true;
            isPhoneInvalid = true;

            errors[3] = isPhoneInvalid;

            logger.error("phone is invalid");
        }

        Connection conn = AppUtils.getStoredConnection(request);

        user = UserDAO.findUserByEmail(conn, email);

        if (user != null) {
            hasError = true;
            isUserExists = true;

            errors[4] = isUserExists;

            logger.error("user with email " + email + " is already exists");
        }

        if (!password.equals(repPassword)) {
            hasError = true;
            isPasswordsNotEquals = true;

            errors[5] = isPasswordsNotEquals;

            logger.error("passwords do not match");
        }

        if(!password.matches(passwordRegex)){
            hasError = true;
            isPasswordInvalid = true;

            errors[6] = isPasswordInvalid;

            logger.error("password is invalid");
        }

        if (hasError) {

            request.setAttribute("userEmail", email);
            request.setAttribute("userName", name);
            request.setAttribute("userPhone", phone);

            if (gender.equals("male")) request.setAttribute("userGenderMale", "male");
            else request.setAttribute("userGenderFemale", "female");

            LocaleUtils.setLocaleRegisterPage(request, errors);

            return false;
        }


        return true;
    }


}
