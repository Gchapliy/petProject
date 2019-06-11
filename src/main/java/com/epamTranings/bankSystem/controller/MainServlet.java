package com.epamTranings.bankSystem.controller;

import com.epamTranings.bankSystem.utils.LocaleUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "homePage", urlPatterns = {"/home"})
public class MainServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger(MainServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LocaleUtils.setLocaleHeaderAndFooter(request);
        LocaleUtils.setLocaleHomePage(request);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
