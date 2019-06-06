package com.epamTranings.bankSystem.controller;

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
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages", (Locale) request.getAttribute("locale"));
        request.setAttribute("welcome", resourceBundle.getString("welcome"));

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
