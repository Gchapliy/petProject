package com.epamTranings.bankSystem.dao;

import com.epamTranings.bankSystem.entity.bankAccount.BankAccount;
import com.epamTranings.bankSystem.entity.bankAccount.BankAccountOrder;
import com.epamTranings.bankSystem.entity.bankAccount.BankAccountTransaction;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BankAccountDAO {

    final static Logger logger = LogManager.getLogger(BankAccountDAO.class);

    /**
     * Find bank account transactions by bank account's uuid
     *
     * @param connection
     * @param bankAccount
     * @return
     */
    public static List<BankAccountTransaction> findBankAccountTransactionsByUuid(Connection connection, BankAccount bankAccount) {

        String sql = "Select t.Transaction_id, t.Bank_Account_From_Uuid, t.Bank_Account_To_Uuid, t.Transaction_Date, t.Transaction_Amount, t.Transaction_Target " +
                "from Bank_Account_Transaction t where t.Bank_Account_From_Uuid = ? or t.Bank_Account_To_Uuid = ?";

        List<BankAccountTransaction> list = new ArrayList<>();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, bankAccount.getAccountUuid());
            pstm.setString(2, bankAccount.getAccountUuid());

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Transaction_id");
                String uuidFrom = rs.getString("Bank_Account_From_Uuid");
                String uuidTo = rs.getString("Bank_Account_To_Uuid");
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Transaction_Date"));
                double amount = rs.getDouble("Transaction_Amount");
                String target = rs.getString("Transaction_Target");
                BankAccount from;
                BankAccount to;

                if (uuidFrom.equals(bankAccount.getAccountUuid())) {
                    from = bankAccount;
                    to = findBankAccountByUuid(connection, uuidTo);
                } else {
                    to = bankAccount;
                    from = findBankAccountByUuid(connection, uuidFrom);
                }

                BankAccountTransaction bankAccountTransaction = new BankAccountTransaction();

                bankAccountTransaction.setTransactionId(id);
                bankAccountTransaction.setBankAccountFrom(from);
                bankAccountTransaction.setBankAccountTo(to);
                bankAccountTransaction.setTransactionDate(date);
                bankAccountTransaction.setTransactionAmount(amount);
                bankAccountTransaction.setTransactionTarget(target);

                list.add(bankAccountTransaction);
            }
        } catch (SQLException e) {
            logger.error("error while find bank account transactions");
            e.printStackTrace();
        } catch (ParseException e) {
            logger.error("error while parse date during finding bank account transactions");
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Find bank account by uuid
     *
     * @param connection
     * @param uuid
     * @return
     */
    public static BankAccount findBankAccountByUuid(Connection connection, String uuid) {

        String sql = "Select a.Account_Balance, a.Account_Create_Date, a.Account_Expiration_Date, " +
                "a.Account_Owner, a.Account_Limit, a.Account_Interest_Rate, a.Account_Debt, a.Account_Type, " +
                "a.Account_Uuid from Bank_Account a " +
                "where a.Account_Uuid = ?";


        BankAccount bankAccount = null;
        UserAccount owner;

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, uuid);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("Account_Balance");
                Date createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Account_Create_Date"));
                Date expiratingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Account_Expiration_Date"));
                double limit = rs.getDouble("Account_Limit");
                int interestRate = rs.getInt("Account_Interest_Rate");
                double debt = rs.getDouble("Account_Debt");
                String email = rs.getString("Account_Owner");

                owner = UserDAO.findUserByEmail(connection, email);
                BankAccount.AccountType type = BankAccount.AccountType.values()[rs.getInt("Account_Type")];

                bankAccount = new BankAccount();
                bankAccount.setAccountBalance(balance);
                bankAccount.setAccountCreationDate(createDate);
                bankAccount.setAccountExpirationDate(expiratingDate);
                bankAccount.setAccountLimit(limit);
                bankAccount.setAccountInterestRate(interestRate);
                bankAccount.setAccountDebt(debt);
                bankAccount.setAccountType(type);
                bankAccount.setAccountOwner(owner);
                bankAccount.setAccountUuid(uuid);
            }
        } catch (SQLException e) {
            logger.error("error while find bank account");
            e.printStackTrace();
        } catch (ParseException e) {
            logger.error("error while parse date during finding bank account");
            e.printStackTrace();
        }

        return bankAccount;
    }

    /**
     * Insert bank account order to db
     *
     * @param connection
     * @param bankAccountOrder
     * @return
     */
    public static boolean insertBankAccountOrder(Connection connection, BankAccountOrder bankAccountOrder) {

        String sql = "Insert into Bank_Account_Order(Order_Create_Date, Order_Owner, Order_Status, Account_Expiration_Date, " +
                "Account_Balance, Account_Limit, Account_Interest_Rate, Account_Type) values (?, ?, ?, ?, ?, ?, ?, ?)";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, sdf.format(bankAccountOrder.getOrderCreateDate()));
            pstm.setString(2, bankAccountOrder.getOrderOwner().getUserAccountEmail());
            pstm.setInt(3, bankAccountOrder.getOrderStatus().ordinal());
            pstm.setString(4, sdf.format(bankAccountOrder.getAccountExpirationDate()));
            pstm.setDouble(5, bankAccountOrder.getAccountBalance());
            pstm.setDouble(6, bankAccountOrder.getAccountLimit());
            pstm.setDouble(7, bankAccountOrder.getAccountInterestRate());
            pstm.setInt(8, bankAccountOrder.getAccountType().ordinal());

            connection.setAutoCommit(false);

            pstm.executeUpdate();

            connection.commit();
            logger.info("bank account order created");
        } catch (SQLException e) {
            logger.error("error while insert bank account order");
            e.printStackTrace();

            transactionRollback(connection);
            return false;
        }

        return true;
    }

    /**
     * Delete bank account order by id
     * @param connection
     * @param bankAccountOrderId
     * @return
     */
    public static boolean deleteBankAccountOrderById(Connection connection, int bankAccountOrderId) {

        String sql = "Delete from Bank_Account_Order where Order_Id = ?";

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, bankAccountOrderId);

            connection.setAutoCommit(false);

            pstm.executeUpdate();

            connection.commit();
            logger.info("new bank account order with id: " + bankAccountOrderId + " deleted");
        } catch (SQLException e) {
            logger.error("error while delete bank account order with id: " + bankAccountOrderId);
            e.printStackTrace();

           transactionRollback(connection);
           return false;
        }

        return true;
    }

    /**
     * Find bank account orders by user account email in db
     * @param connection
     * @param userAccount
     * @return
     */
    public static List<BankAccountOrder> findBankAccountOrderByUserAccount(Connection connection, UserAccount userAccount){
        String sql = "Select b.Order_Id, b.Order_Create_Date, b.Order_Owner, b.Order_Status, b.Account_Expiration_Date, " +
                "b.Account_Balance, b.Account_Limit, b.Account_Interest_Rate, b.Account_Type from Bank_Account_Order b " +
                "where b.Order_Owner = ?";

        List<BankAccountOrder> bankAccountOrders = new LinkedList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userAccount.getUserAccountEmail());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                BankAccountOrder bankAccountOrder = new BankAccountOrder();

                int orderId = rs.getInt("Order_Id");
                Date createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Order_Create_Date"));
                Date expirationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Account_Expiration_Date"));
                BankAccountOrder.OrderStatus status = BankAccountOrder.OrderStatus.values()[rs.getInt("Order_Status")];
                double accountBalance = rs.getDouble("Account_Balance");
                double accountLimit = rs.getDouble("Account_Limit");
                double interestRate = rs.getDouble("Account_Interest_Rate");
                BankAccount.AccountType accountType = BankAccount.AccountType.values()[rs.getInt("Account_Type")];

                bankAccountOrder.setOrderId(orderId);
                bankAccountOrder.setOrderCreateDate(createDate);
                bankAccountOrder.setAccountExpirationDate(expirationDate);
                bankAccountOrder.setOrderStatus(status);
                bankAccountOrder.setAccountBalance(accountBalance);
                bankAccountOrder.setAccountLimit(accountLimit);
                bankAccountOrder.setAccountInterestRate(interestRate);
                bankAccountOrder.setAccountType(accountType);
                bankAccountOrder.setOrderOwner(userAccount);

                bankAccountOrders.add(bankAccountOrder);
            }
        } catch (SQLException e) {
            logger.error("error while find bank account order in db");
            e.printStackTrace();
        } catch (ParseException e) {
            logger.error("error while parse date during find bank account order in db");
            e.printStackTrace();
        }

        return bankAccountOrders;
    }

    private static void transactionRollback(Connection connection){
        try {
            connection.rollback();
            logger.info("transaction rollback");
        } catch (SQLException e1) {
            logger.error("transaction didn't rollback");
            e1.printStackTrace();
        }
    }
}
