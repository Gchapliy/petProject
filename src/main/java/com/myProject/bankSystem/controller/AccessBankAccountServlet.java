package com.myProject.bankSystem.controller;

import com.myProject.bankSystem.dao.BankAccountDAO;
import com.myProject.bankSystem.entity.bankAccount.BankAccount;
import com.myProject.bankSystem.entity.bankAccount.BankAccountOrder;
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

@WebServlet(name = "accessBankAccount", urlPatterns = {"/success"})
public class AccessBankAccountServlet extends HttpServlet{
    final static Logger logger = LogManager.getLogger(AccessBankAccountServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String orderId = req.getParameter("id");

        logger.info("orderId to success: " + orderId);

        BankAccountOrder bankAccountOrder = BankAccountDAO.findBankAccountOrderById(AppUtils.getStoredConnection(req), Integer.parseInt(orderId));

        BankAccount bankAccount = CreateBankAccountUtil.createBankAccount(req, bankAccountOrder);

        BankAccountDAO.insertBankAccount(AppUtils.getStoredConnection(req), bankAccount);

        bankAccountOrder.setOrderStatus(BankAccountOrder.OrderStatus.ALLOWED);
        BankAccountDAO.updateBankAccountOrder(AppUtils.getStoredConnection(req), bankAccountOrder);

        logger.info("bank account " + bankAccount + " created");

        resp.sendRedirect("/userPage?pageA=1&pageUsO=1&pageYO=1");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
