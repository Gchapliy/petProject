package com.epamTranings.bankSystem.utils;

import com.epamTranings.bankSystem.entity.bankAccount.BankAccount;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleUtils {

    /**
     * Initiating locale for header and footer to every page
     * @param request
     */
    public static void setLocaleHeaderAndFooter(HttpServletRequest request){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        UserAccount loginedUser = AppUtils.getLoginedUser(request.getSession());

        if(loginedUser != null){
            request.setAttribute("userName", loginedUser.getUserAccountName());
        }

        //Header locale settings
        request.setAttribute("home", resourceBundle.getString("home.home"));
        request.setAttribute("services", resourceBundle.getString("home.services"));
        request.setAttribute("login", resourceBundle.getString("home.login"));
        request.setAttribute("logout", resourceBundle.getString("home.logout"));

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
        Locale locale = (Locale) request.getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", locale, new UTF8Control());

        request.setAttribute("title", resourceBundle.getString("home.title"));

        //Locale for Number
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);

        //Locale settings for home page
        request.setAttribute("welcome", resourceBundle.getString("home.welcome"));
        request.setAttribute("our_services", resourceBundle.getString("home.ourServices"));
        request.setAttribute("standard_title", resourceBundle.getString("home.standard.title"));
        request.setAttribute("standard_standard1", resourceBundle.getString("home.standard.standard1"));
        request.setAttribute("standard_standard2", resourceBundle.getString("home.standard.standard2"));
        request.setAttribute("standard_standard3", resourceBundle.getString("home.standard.standard3"));
        request.setAttribute("standard_standard4", resourceBundle.getString("home.standard.standard4"));
        request.setAttribute("deposit_title", resourceBundle.getString("home.deposit.title"));

        String depositFromPercent = resourceBundle.getString("home.deposit.fromPercent");
        String depositToPercent = resourceBundle.getString("home.deposit.toPercent");
        request.setAttribute("deposit_deposit1", resourceBundle.getString("home.deposit.deposit1"));
        request.setAttribute("deposit_deposit2", resourceBundle.getString("home.deposit.deposit2") + " "
                + numberFormat.format(Double.parseDouble(depositFromPercent)) + "% - " + numberFormat.format(Double.parseDouble(depositToPercent)) + "%");

        request.setAttribute("deposit_deposit3", resourceBundle.getString("home.deposit.deposit3"));
        request.setAttribute("deposit_deposit4", resourceBundle.getString("home.deposit.deposit4"));

        request.setAttribute("credit_title", resourceBundle.getString("home.credit.title"));
        String creditFrom = resourceBundle.getString("home.credit.fromMoney");
        String to = resourceBundle.getString("home.credit.to");
        String creditTo = resourceBundle.getString("home.credit.toMoney");
        String creditPercent = resourceBundle.getString("home.credit.interestRate");
        String usd = resourceBundle.getString("home.credit.usd");

        request.setAttribute("credit_credit1", resourceBundle.getString("home.credit.credit1") + " " +numberFormat.format(Integer.parseInt(creditFrom))
                + " " + to + " " + numberFormat.format(Integer.parseInt(creditTo)) + " " + usd);
        request.setAttribute("credit_credit2", resourceBundle.getString("home.credit.credit2") + " " + numberFormat.format(Integer.parseInt(creditPercent)) + "%");
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

    /**
     * Initiating locale for user page. NoAccounts need if user doesn't have any bank accounts
     * @param request
     * @param noAccounts
     */
    public static void setLocaleUserPage(HttpServletRequest request, boolean noAccounts){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        request.setAttribute("title", resourceBundle.getString("user.title"));

        //Locale settings for user page
        request.setAttribute("welcome", resourceBundle.getString("user.welcome"));
        request.setAttribute("yourAccounts", resourceBundle.getString("user.yourAccounts"));
        request.setAttribute("accNumber", resourceBundle.getString("user.accNumber"));
        request.setAttribute("accType", resourceBundle.getString("user.accType"));
        request.setAttribute("balance", resourceBundle.getString("user.balance"));
        if(noAccounts) request.setAttribute("noAccounts", resourceBundle.getString("user.noAccounts"));
    }

    public static void setLocaleBankAccount(HttpServletRequest request){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        //Locale settings for bank account page
        request.setAttribute("title", resourceBundle.getString("bankAccount.title"));
        request.setAttribute("uuid", resourceBundle.getString("bankAccount.uuid"));
        request.setAttribute("type", resourceBundle.getString("bankAccount.type"));
        request.setAttribute("balance", resourceBundle.getString("bankAccount.balance"));
        request.setAttribute("creationDate", resourceBundle.getString("bankAccount.creationDate"));
        request.setAttribute("expirationDate", resourceBundle.getString("bankAccount.expirationDate"));
        request.setAttribute("interestRate", resourceBundle.getString("bankAccount.interestRate"));
        request.setAttribute("debt", resourceBundle.getString("bankAccount.debt"));
        request.setAttribute("limit", resourceBundle.getString("bankAccount.limit"));
    }

    /*  System.out.println("LOCALE " + locale);
            request.setAttribute("country", locale.getDisplayCountry());

            //Locale for Number
            NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
            request.setAttribute("fnumber", numberFormat.format(1234567));



            //Locale for Percent
            NumberFormat numberFormatPercent = NumberFormat.getPercentInstance(locale);
            request.setAttribute("fpercent", numberFormatPercent.format(12.34));

            //Locale for Date
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
            request.setAttribute("fdate", dateFormat.format(new Date()));

            //Locale for String
            ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", locale);
            request.setAttribute("fstring", resourceBundle.getString("label.title"));*/

}
