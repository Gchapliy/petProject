package com.myProject.bankSystem.handler;

import com.myProject.bankSystem.utils.LocaleUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "exceptionHandler", urlPatterns = {"/exceptionHandler"})
public class ExceptionHandler extends HttpServlet{

    final static Logger logger = LogManager.getLogger(ExceptionHandler.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isError = false;
        boolean isException = true;
        boolean isAccessDenied = false;

        Throwable throwable = (Throwable)
                req.getAttribute("javax.servlet.error.exception");

        logger.error("exception " + throwable.getStackTrace());

        LocaleUtils.setLocaleHeaderAndFooter(req);
        LocaleUtils.setLocaleError(req, isError, isException, isAccessDenied);

        req.getRequestDispatcher("templates/exception.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
