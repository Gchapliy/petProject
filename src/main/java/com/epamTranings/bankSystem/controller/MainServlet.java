package com.epamTranings.bankSystem.controller;

import com.epamTranings.bankSystem.utils.UTF8Control;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet(name = "homePage", urlPatterns = {"/home"})
public class MainServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger(MainServlet.class);
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        setLocaleAttributes(request);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /**
     * localization initialising for home page
     * @param request
     */
    private void setLocaleAttributes(HttpServletRequest request) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"), new UTF8Control());

        request.setAttribute("title", resourceBundle.getString("home.title"));
        request.setAttribute("home", resourceBundle.getString("home.home"));
        request.setAttribute("services", resourceBundle.getString("home.services"));
        request.setAttribute("login", resourceBundle.getString("home.login"));
        request.setAttribute("logout", resourceBundle.getString("home.logout"));
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
        request.setAttribute("footer_menu", resourceBundle.getString("home.footer.menu"));
        request.setAttribute("footer_contact", resourceBundle.getString("home.footer.contact"));
        request.setAttribute("footer_address", resourceBundle.getString("home.footer.address"));
        request.setAttribute("footer_phone", resourceBundle.getString("home.footer.phone"));
        request.setAttribute("footer_email", resourceBundle.getString("home.footer.email"));
    }

}
