package com.epamTranings.bankSystem.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet("/home")
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getParameter("language") != null){
            String[] pLanguage = request.getParameter("language").split("_");
            String language = pLanguage[0];
            String country = pLanguage[1];
            Locale locale = new Locale(language, country);
            // request.setAttribute("country", locale.getDisplayCountry());
            request.setAttribute("country", "COUNTRY");

            //Locale for Number
            NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
            request.setAttribute("fnumber", numberFormat.format(1234567));

            //Locale for Currency
            NumberFormat numberFormatCurency = NumberFormat.getCurrencyInstance(locale);
            request.setAttribute("fcurrency", numberFormatCurency.format(1234567));

            //Locale for Percent
            NumberFormat numberFormatPercent = NumberFormat.getPercentInstance(locale);
            request.setAttribute("fpercent", numberFormatPercent.format(12.34));

            //Locale for Date
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
            request.setAttribute("fdate", dateFormat.format(new Date()));

            //Locale for String
            ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", locale);
            request.setAttribute("fstring", resourceBundle.getString("label.title"));
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
