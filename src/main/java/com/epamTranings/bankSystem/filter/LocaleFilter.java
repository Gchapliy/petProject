package com.epamTranings.bankSystem.filter;

import com.epamTranings.bankSystem.utils.AppUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "localeFilter", urlPatterns = {"/*"})
public class LocaleFilter implements Filter {
    private Locale locale;
    private String language = "en";
    private String country = "US";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)  request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (request.getParameter("language") != null) {
            String[] pLanguage = request.getParameter("language").split("_");
            language = pLanguage[0];
            country = pLanguage[1];
            locale = new Locale(language, country);

            AppUtils.storeLocale(resp, locale);
            request.setAttribute("locale", locale);
        } else {
            locale = AppUtils.getStoredLocale(req);
            if(locale != null)
                request.setAttribute("locale", locale);
            else {
                locale = new Locale(language, country);
                request.setAttribute("locale", locale);
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
