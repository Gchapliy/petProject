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

@WebServlet(name = "newBankAccount", urlPatterns = {"/newBankAccount"})
public class NewBankAccountServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(NewBankAccountServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleUtils.setLocaleHeaderAndFooter(req);
        LocaleUtils.setLocaleNewBankAccount(req);
        req.getRequestDispatcher("templates/newBankAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocaleUtils.setLocaleHeaderAndFooter(req);

        String type = req.getParameter("accType");

        if(type.equals(req.getAttribute("standard"))){

        } else if(type.equals(req.getAttribute("deposit"))){

        } else if(type.equals(req.getAttribute("credit"))){

        }

        LocaleUtils.setLocaleNewBankAccount(req);

        req.getRequestDispatcher("templates/newBankAccountOrderCreated.jsp").forward(req, resp);
    }
}
