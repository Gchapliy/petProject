package com.epamTranings.bankSystem.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleUtils {

    /**
     * Initiating locale for header and footer to every page
     * @param request
     */
    public static void setLocaleHeaderAndFooter(HttpServletRequest request){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        //Header locale settings
        request.setAttribute("home", resourceBundle.getString("home.home"));
        request.setAttribute("services", resourceBundle.getString("home.services"));
        request.setAttribute("login", resourceBundle.getString("home.login"));
        request.setAttribute("logout", resourceBundle.getString("home.logout"));

        //Locale settings for login page
        request.setAttribute("credit_credit1", resourceBundle.getString("home.credit.credit1"));
        request.setAttribute("credit_credit2", resourceBundle.getString("home.credit.credit2"));
        request.setAttribute("credit_credit3", resourceBundle.getString("home.credit.credit3"));
        request.setAttribute("credit_credit4", resourceBundle.getString("home.credit.credit4"));
        request.setAttribute("join", resourceBundle.getString("home.join"));

        //Footer locale settings
        request.setAttribute("footer_menu", resourceBundle.getString("home.footer.menu"));
        request.setAttribute("footer_contact", resourceBundle.getString("home.footer.contact"));
        request.setAttribute("footer_address", resourceBundle.getString("home.footer.address"));
        request.setAttribute("footer_phone", resourceBundle.getString("home.footer.phone"));
        request.setAttribute("footer_email", resourceBundle.getString("home.footer.email"));
    }

    /**
     * Initiating locale for home page
     * @param request
     */
    public static void setLocaleHomePage(HttpServletRequest request){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        request.setAttribute("title", resourceBundle.getString("home.title"));

        //Locale settings for home page
        request.setAttribute("welcome", resourceBundle.getString("home.welcome"));
        request.setAttribute("our_services", resourceBundle.getString("home.ourServices"));
        request.setAttribute("standard_title", resourceBundle.getString("home.standard.title"));
        request.setAttribute("standard_standard1", resourceBundle.getString("home.standard.standard1"));
        request.setAttribute("standard_standard2", resourceBundle.getString("home.standard.standard2"));
        request.setAttribute("standard_standard3", resourceBundle.getString("home.standard.standard3"));
        request.setAttribute("standard_standard4", resourceBundle.getString("home.standard.standard4"));
        request.setAttribute("deposit_title", resourceBundle.getString("home.deposit.title"));
        request.setAttribute("deposit_deposit1", resourceBundle.getString("home.deposit.deposit1"));
        request.setAttribute("deposit_deposit2", resourceBundle.getString("home.deposit.deposit2"));
        request.setAttribute("deposit_deposit3", resourceBundle.getString("home.deposit.deposit3"));
        request.setAttribute("deposit_deposit4", resourceBundle.getString("home.deposit.deposit4"));
        request.setAttribute("credit_title", resourceBundle.getString("home.credit.title"));
        request.setAttribute("credit_credit1", resourceBundle.getString("home.credit.credit1"));
        request.setAttribute("credit_credit2", resourceBundle.getString("home.credit.credit2"));
        request.setAttribute("credit_credit3", resourceBundle.getString("home.credit.credit3"));
        request.setAttribute("credit_credit4", resourceBundle.getString("home.credit.credit4"));
        request.setAttribute("join", resourceBundle.getString("home.join"));
    }

    /**
     * Initiating locale for login page. IsInvalid and isRequired need if corresponding errors  happen when
     * user types login data
     * @param request
     * @param isInvalid
     * @param isRequired
     */
    public static void setLocaleLoginPage(HttpServletRequest request, boolean isInvalid, boolean isRequired){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        request.setAttribute("title", resourceBundle.getString("login.title"));

        //Locale settings for login page
        request.setAttribute("email", resourceBundle.getString("login.email"));
        request.setAttribute("password", resourceBundle.getString("login.password"));
        request.setAttribute("loginBtn", resourceBundle.getString("login.loginBtn"));
        request.setAttribute("registerBtn", resourceBundle.getString("login.registerBtn"));
        if(isInvalid) request.setAttribute("error", resourceBundle.getString("login.errorInvalid"));
        if(isRequired) request.setAttribute("error", resourceBundle.getString("login.errorRequired"));
    }

    public static void setLocaleUserPage(HttpServletRequest request){

    }
}
