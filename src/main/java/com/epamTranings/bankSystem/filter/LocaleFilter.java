package com.epamTranings.bankSystem.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "localeFilter", urlPatterns = {"/*"})
public class LocaleFilter implements Filter {
    private Locale locale;
    private String language = "en";
    private String country = "US";
    private Cookie localeCookie;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        if (request.getParameter("language") != null) {
            String[] pLanguage = request.getParameter("language").split("_");
            language = pLanguage[0];
            country = pLanguage[1];
            locale = new Locale(language, country);

          /*  System.out.println("LOCALE " + locale);
            request.setAttribute("country", locale.getDisplayCountry());

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
            request.setAttribute("fstring", resourceBundle.getString("label.title"));*/
        } else {
            locale = new Locale(language, country);
        }

        request.setAttribute("language", language);
        request.setAttribute("country", country);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
