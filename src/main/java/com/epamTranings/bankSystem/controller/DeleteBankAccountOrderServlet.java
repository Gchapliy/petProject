package com.epamTranings.bankSystem.controller;

import com.epamTranings.bankSystem.dao.BankAccountDAO;
import com.epamTranings.bankSystem.utils.AppUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "deleteBankAccountOrder", urlPatterns = {"/delete"})
public class DeleteBankAccountOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int orderId = Integer.parseInt(req.getParameter("id"));

        BankAccountDAO.deleteBankAccountOrderById(AppUtils.getStoredConnection(req), orderId);

        resp.sendRedirect("/userPage");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
