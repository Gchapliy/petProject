package com.epamTranings.bankSystem.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterValidator {

    final static Logger logger = LogManager.getLogger(RegisterValidator.class);


    public static boolean validate(HttpServletRequest request, HttpServletResponse response){

        logger.info("GENDER: " + request.getParameter("userGender"));

        return false;
    }
}
