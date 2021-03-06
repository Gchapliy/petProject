package com.myProject.bankSystem.utils;

import com.myProject.bankSystem.bean.userAccount.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleUtils {

    final static Logger logger = LogManager.getLogger(LocaleUtils.class);

    /**
     * Initiating locale for header and footer to every page
     *
     * @param request
     */
    public static void setLocaleHeaderAndFooter(HttpServletRequest request) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        UserAccount loginedUser = AppUtils.getLoginedUser(request.getSession());

        if (loginedUser != null) {
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
     *
     * @param request
     */
    public static void setLocaleHomePage(HttpServletRequest request) {
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

        request.setAttribute("credit_credit1", resourceBundle.getString("home.credit.credit1") + " " + numberFormat.format(Integer.parseInt(creditFrom))
                + " " + to + " " + numberFormat.format(Integer.parseInt(creditTo)) + " " + usd);
        request.setAttribute("credit_credit2", resourceBundle.getString("home.credit.credit2") + " " + numberFormat.format(Integer.parseInt(creditPercent)) + "%");
        request.setAttribute("credit_credit3", resourceBundle.getString("home.credit.credit3"));
        request.setAttribute("credit_credit4", resourceBundle.getString("home.credit.credit4"));
        request.setAttribute("join", resourceBundle.getString("home.join"));
    }

    /**
     * Initiating locale for login page. IsInvalid and isRequired need if corresponding errors  happen when
     * user types login data
     *
     * @param request
     * @param isInvalid
     * @param isRequired
     */
    public static void setLocaleLoginPage(HttpServletRequest request, boolean isInvalid, boolean isRequired) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        request.setAttribute("title", resourceBundle.getString("login.title"));

        //Locale settings for login page
        request.setAttribute("email", resourceBundle.getString("login.email"));
        request.setAttribute("password", resourceBundle.getString("login.password"));
        request.setAttribute("loginBtn", resourceBundle.getString("login.loginBtn"));
        request.setAttribute("registerBtn", resourceBundle.getString("login.registerBtn"));

        if (isInvalid) request.setAttribute("error", resourceBundle.getString("login.errorInvalid"));
        if (isRequired) request.setAttribute("error", resourceBundle.getString("login.errorRequired"));
    }

    /**
     * Initiating locale for register page. errors need if corresponding errors  happen when
     * user types register data
     *
     * @param request
     * @param errors
     */
    public static void setLocaleRegisterPage(HttpServletRequest request, boolean[] errors) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        request.setAttribute("title", resourceBundle.getString("register.title"));

        //Locale settings for register page
        request.setAttribute("email", resourceBundle.getString("register.email"));
        request.setAttribute("name", resourceBundle.getString("register.name"));
        request.setAttribute("name", resourceBundle.getString("register.name"));
        request.setAttribute("phone", resourceBundle.getString("register.phone"));
        request.setAttribute("gender", resourceBundle.getString("register.gender"));
        request.setAttribute("male", resourceBundle.getString("register.male"));
        request.setAttribute("female", resourceBundle.getString("register.female"));
        request.setAttribute("password", resourceBundle.getString("register.password"));
        request.setAttribute("repPassword", resourceBundle.getString("register.repPassword"));
        request.setAttribute("registerBtn", resourceBundle.getString("register.registerBtn"));

        /*
        0 - isRequired
        1 - isEmailInvalid
        2 - isNameInvalid
        3 - isPhoneInvalid
        4 - isUserExists
        5 - isPasswordsNotEquals
        6 - isPasswordInvalid
        */

        if (errors[0]) request.setAttribute("errorRequired", resourceBundle.getString("register.errorRequired"));
        if (errors[1]) request.setAttribute("errorEmail", resourceBundle.getString("register.errorEmailInvalid"));
        if (errors[2]) request.setAttribute("errorName", resourceBundle.getString("register.errorNameInvalid"));
        if (errors[3]) request.setAttribute("errorPhone", resourceBundle.getString("register.errorPhoneInvalid"));
        if (errors[4]) request.setAttribute("errorUserExists", resourceBundle.getString("register.errorUserExists"));
        if (errors[5])
            request.setAttribute("errorPasswordEqual", resourceBundle.getString("register.errorPasswordsEqual"));
        if (errors[6])
            request.setAttribute("errorPasswordInvalid", resourceBundle.getString("register.errorPasswordsInvalid"));

    }

    /**
     * Initiating locale for user page. NoAccounts need if user doesn't have any bank accounts
     *
     * @param request
     * @param noAccounts
     */
    public static void setLocaleUserPage(HttpServletRequest request, boolean noAccounts, boolean noOrders, boolean noUserOrders) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        request.setAttribute("title", resourceBundle.getString("user.title"));

        //Locale settings for user page
        request.setAttribute("welcome", resourceBundle.getString("user.welcome"));
        request.setAttribute("yourAccounts", resourceBundle.getString("user.yourAccounts"));
        request.setAttribute("accNumber", resourceBundle.getString("user.accNumber"));
        request.setAttribute("accType", resourceBundle.getString("user.accType"));
        request.setAttribute("balance", resourceBundle.getString("user.balance"));
        request.setAttribute("yourOrders", resourceBundle.getString("user.yourOrders"));
        request.setAttribute("usersOrders", resourceBundle.getString("user.usersOrders"));
        request.setAttribute("createDate", resourceBundle.getString("user.createDate"));
        request.setAttribute("expirationDate", resourceBundle.getString("user.expirationDate"));
        request.setAttribute("orderOwner", resourceBundle.getString("user.orderOwner"));
        request.setAttribute("accountType", resourceBundle.getString("user.accountType"));
        request.setAttribute("accountBalance", resourceBundle.getString("user.accountBalance"));
        request.setAttribute("accountLimit", resourceBundle.getString("user.accountLimit"));
        request.setAttribute("accountInterestRate", resourceBundle.getString("user.accountInterestRate"));
        request.setAttribute("status", resourceBundle.getString("user.status"));
        request.setAttribute("success", resourceBundle.getString("user.success"));
        request.setAttribute("deny", resourceBundle.getString("user.deny"));
        request.setAttribute("orders", resourceBundle.getString("user.noOrders"));
        request.setAttribute("delete", resourceBundle.getString("user.delete"));
        request.setAttribute("confirm", resourceBundle.getString("user.confirm"));
        request.setAttribute("confirmSuccess", resourceBundle.getString("user.confirmSuccess"));
        request.setAttribute("confirmDeny", resourceBundle.getString("user.confirmDeny"));
        request.setAttribute("confirmQuestion", resourceBundle.getString("user.confirmQuestion"));
        request.setAttribute("successQuestion", resourceBundle.getString("user.successQuestion"));
        request.setAttribute("denyQuestion", resourceBundle.getString("user.denyQuestion"));

        if (noAccounts) request.setAttribute("noAccounts", resourceBundle.getString("user.noAccounts"));
        if (noOrders) request.setAttribute("noOrders", resourceBundle.getString("user.noOrders"));
        if (noUserOrders) request.setAttribute("noUserOrders", resourceBundle.getString("user.noUserOrders"));
    }

    /**
     * Initiating locale for bank account managing interface
     *
     * @param request
     */
    public static void setLocaleManagingInterface(HttpServletRequest request) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        //Locale settings for managing interface
        request.setAttribute("managing", resourceBundle.getString("managing.managing"));
        request.setAttribute("history", resourceBundle.getString("managing.history"));
        request.setAttribute("paymentTransfers", resourceBundle.getString("managing.paymentTransfers"));
        request.setAttribute("settings", resourceBundle.getString("managing.settings"));
        request.setAttribute("back", resourceBundle.getString("managing.back"));
        request.setAttribute("createAccount", resourceBundle.getString("managing.createAccount"));
    }

    /**
     * Initiating locale for bank account page.
     *
     * @param request
     */
    public static void setLocaleBankAccount(HttpServletRequest request) {
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
        request.setAttribute("currency", resourceBundle.getString("bankAccount.currency"));
    }

    /**
     * Initiating locale for bank account history. NoHistory uses when bank account transactions are not exists
     *
     * @param request
     * @param noHistory
     */
    public static void setLocaleBankAccountHistory(HttpServletRequest request, boolean noHistory) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        //Locale settings for bank account history page
        request.setAttribute("title", resourceBundle.getString("bankAccount.history.title"));
        request.setAttribute("subTitle", resourceBundle.getString("bankAccount.history.subTitle"));
        request.setAttribute("accountFrom", resourceBundle.getString("bankAccount.history.accountFrom"));
        request.setAttribute("accountTo", resourceBundle.getString("bankAccount.history.accountTo"));
        request.setAttribute("date", resourceBundle.getString("bankAccount.history.date"));
        request.setAttribute("target", resourceBundle.getString("bankAccount.history.target"));
        request.setAttribute("amount", resourceBundle.getString("bankAccount.history.amount"));
        request.setAttribute("currency", resourceBundle.getString("bankAccount.history.currency"));

        if (noHistory) request.setAttribute("noHistory", resourceBundle.getString("bankAccount.history.noHistory"));

    }

    /**
     * Initiating locale for new bank account page. DepSumError and credSumError use if corresponding exceptions appears
     *
     * @param request
     */
    public static void setLocaleNewBankAccount(HttpServletRequest request, boolean depSumError, boolean credSumError, boolean stndSunError) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        //Locale settings for new bank account page
        request.setAttribute("title", resourceBundle.getString("bankAccount.new.title"));
        request.setAttribute("chooseAccType", resourceBundle.getString("bankAccount.new.chooseAccType"));
        request.setAttribute("standard", resourceBundle.getString("bankAccount.new.standard"));
        request.setAttribute("deposit", resourceBundle.getString("bankAccount.new.deposit"));
        request.setAttribute("credit", resourceBundle.getString("bankAccount.new.credit"));
        request.setAttribute("credit", resourceBundle.getString("bankAccount.new.credit"));
        request.setAttribute("chooseDepTerm", resourceBundle.getString("bankAccount.new.chooseDepTerm"));
        request.setAttribute("threeMonths", resourceBundle.getString("bankAccount.new.3months"));
        request.setAttribute("sixMonths", resourceBundle.getString("bankAccount.new.6months"));
        request.setAttribute("twelveMonths", resourceBundle.getString("bankAccount.new.12months"));
        request.setAttribute("eighteenMonths", resourceBundle.getString("bankAccount.new.18months"));
        request.setAttribute("twentyFourMonths", resourceBundle.getString("bankAccount.new.24months"));
        request.setAttribute("typeDepSum", resourceBundle.getString("bankAccount.new.typeDepSum"));
        request.setAttribute("typeStndSum", resourceBundle.getString("bankAccount.new.typeStndSum"));
        request.setAttribute("currency", resourceBundle.getString("bankAccount.new.currency"));
        request.setAttribute("depPercent", resourceBundle.getString("bankAccount.new.depPercent"));
        request.setAttribute("typeCredSum", resourceBundle.getString("bankAccount.new.typeCredSum"));
        request.setAttribute("chooseCredTerm", resourceBundle.getString("bankAccount.new.chooseCredTerm"));
        request.setAttribute("credPercent", resourceBundle.getString("bankAccount.new.creditPercent"));
        request.setAttribute("sendOrder", resourceBundle.getString("bankAccount.new.sendOrder"));
        request.setAttribute("orderCreated", resourceBundle.getString("bankAccount.new.orderCreated"));

        if (depSumError) request.setAttribute("depSumError", resourceBundle.getString("bankAccount.new.depSumError"));
        if (credSumError)
            request.setAttribute("credSumError", resourceBundle.getString("bankAccount.new.credSumError"));
        if(stndSunError) request.setAttribute("stndSumError", resourceBundle.getString("bankAccount.new.stndSumError"));
    }

    /**
     * Initiating locale for transfer and payment page. errors need if corresponding errors  happen when
     * user types data
     *
     * @param request
     * @param isDeposit
     * @param errors
     */
    public static void setLocaleTransfersPayment(HttpServletRequest request, boolean isDeposit, boolean[] errors) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        //Locale settings for new bank account page
        request.setAttribute("title", resourceBundle.getString("bankAccount.transfers.title"));
        request.setAttribute("account", resourceBundle.getString("bankAccount.transfers.account"));
        request.setAttribute("accType", resourceBundle.getString("bankAccount.transfers.type"));
        request.setAttribute("accBalance", resourceBundle.getString("bankAccount.transfers.balance"));
        request.setAttribute("actions", resourceBundle.getString("bankAccount.transfers.actions"));
        request.setAttribute("accountFrom", resourceBundle.getString("bankAccount.transfers.accountFrom"));
        request.setAttribute("accountTo", resourceBundle.getString("bankAccount.transfers.accountTo"));
        request.setAttribute("date", resourceBundle.getString("bankAccount.transfers.date"));
        request.setAttribute("payTarget", resourceBundle.getString("bankAccount.transfers.target"));
        request.setAttribute("amount", resourceBundle.getString("bankAccount.transfers.amount"));
        request.setAttribute("transferTitle", resourceBundle.getString("bankAccount.transfers.transfer.title"));
        request.setAttribute("transferSpecify", resourceBundle.getString("bankAccount.transfers.transfer.specify"));
        request.setAttribute("transferSum", resourceBundle.getString("bankAccount.transfers.transfer.sum"));
        request.setAttribute("transferBtn", resourceBundle.getString("bankAccount.transfers.transfer.btn"));
        request.setAttribute("payTitle", resourceBundle.getString("bankAccount.transfers.payment.title"));
        request.setAttribute("paySpecify", resourceBundle.getString("bankAccount.transfers.payment.specify"));
        request.setAttribute("payTarget", resourceBundle.getString("bankAccount.transfers.payment.target"));
        request.setAttribute("payBtn", resourceBundle.getString("bankAccount.transfers.payment.btn"));

        /*
        0 - isTransferSpecifyInvalid
        1 - isTransferSumInvalid
        2 - isRequiredTransfer
        3 - isPaySpecifyInvalid
        4 - isPayTransferSumInvalid
        5 - isRequiredPay
        6 - isNoHistory
        7 - isPayTargetInvalid
        8 - isAccountTransferInvalid
        */

        if (errors[0])
            request.setAttribute("errorTransferSpecify", resourceBundle.getString("bankAccount.transfers.transfer.errorTransferSpecify"));
        if (errors[1])
            request.setAttribute("errorTransferSum", resourceBundle.getString("bankAccount.transfers.transfer.errorTransferSum"));
        if (errors[2])
            request.setAttribute("errorRequiredTransfer", resourceBundle.getString("bankAccount.transfers.transfer.errorRequiredTransfer"));
        if (errors[3])
            request.setAttribute("errorPaySpecify", resourceBundle.getString("bankAccount.transfers.payment.errorPaySpecify"));
        if (errors[4])
            request.setAttribute("errorPayTransferSum", resourceBundle.getString("bankAccount.transfers.payment.errorPayTransferSum"));
        if (errors[5])
            request.setAttribute("errorRequiredPay", resourceBundle.getString("bankAccount.transfers.payment.errorRequiredPay"));
        if (errors[6]) {
            if (isDeposit)
                request.setAttribute("noHistory", resourceBundle.getString("bankAccount.transfers.deposit.noHistory"));
            else request.setAttribute("noHistory", resourceBundle.getString("bankAccount.transfers.credit.noHistory"));
        }
        if (errors[7])
            request.setAttribute("errorPayTarget", resourceBundle.getString("bankAccount.transfers.payment.errorPayTarget"));

        if(errors[8])
            request.setAttribute("errorAccountTransfer", resourceBundle.getString("bankAccount.transfers.transfer.errorAccountTransferInvalid"));

        if (isDeposit) {
            request.setAttribute("historyTitle", resourceBundle.getString("bankAccount.transfers.deposit.title"));
        } else {
            request.setAttribute("historyTitle", resourceBundle.getString("bankAccount.transfers.credit.title"));
        }
    }

    public static void setLocaleError(HttpServletRequest request, boolean isError, boolean isException, boolean isAccessDenied) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        //Locale for error page
        if (isError) {
            request.setAttribute("title", resourceBundle.getString("error.title"));
        }

        //Locale for exception page
        if (isException) {
            request.setAttribute("title", resourceBundle.getString("exception.title"));
            request.setAttribute("text", resourceBundle.getString("exception.text"));
        }

        //Locale for access denied page
        if (isAccessDenied) {
            request.setAttribute("title", resourceBundle.getString("accessDenied.title"));
        }

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
