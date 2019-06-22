package com.myProject.bankSystem.controller;

import com.myProject.bankSystem.dao.BankAccountDAO;
import com.myProject.bankSystem.bean.bankAccount.BankAccount;
import com.myProject.bankSystem.bean.bankAccount.BankAccountOrder;
import com.myProject.bankSystem.utils.AppUtils;
import com.myProject.bankSystem.utils.CreateBankAccountUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "accessBankAccount", urlPatterns = {"/success"})
public class AccessBankAccountServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger(AccessBankAccountServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = AppUtils.getStoredConnection(req);

        String orderId = req.getParameter("id");

        logger.info("orderId to success: " + orderId);

        //find bank account order by id
        BankAccountOrder bankAccountOrder = BankAccountDAO.findBankAccountOrderById(connection, Integer.parseInt(orderId));

        //create bank account based on the order
        BankAccount bankAccount = CreateBankAccountUtil.createBankAccount(req, bankAccountOrder);

        //insert new bank account to db
        BankAccountDAO.insertBankAccount(connection, bankAccount);

        //change order status
        bankAccountOrder.setOrderStatus(BankAccountOrder.OrderStatus.ALLOWED);

        //update bank account order
        BankAccountDAO.updateBankAccountOrder(connection, bankAccountOrder);

        logger.info("bank account " + bankAccount + " created");

        resp.sendRedirect("/userPage?pageA=1&pageUsO=1&pageYO=1");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
