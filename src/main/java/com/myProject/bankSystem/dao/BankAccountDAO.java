package com.myProject.bankSystem.dao;

import com.myProject.bankSystem.bean.bankAccount.BankAccount;
import com.myProject.bankSystem.bean.bankAccount.BankAccountOrder;
import com.myProject.bankSystem.bean.bankAccount.BankAccountTransaction;
import com.myProject.bankSystem.bean.userAccount.UserAccount;
import com.myProject.bankSystem.utils.dbConnectionUtils.ConnectionUtils;
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
    public static List<BankAccountTransaction> findBankAccountTransactionsByUuid(Connection connection, BankAccount bankAccount, int start, int total) {

        String sql = "Select t.Transaction_id, t.Bank_Account_From_Uuid, t.Bank_Account_To_Uuid, t.Transaction_Date, t.Transaction_Amount, t.Transaction_Target " +
                "from Bank_Account_Transaction t where t.Bank_Account_From_Uuid = ? or t.Bank_Account_To_Uuid = ? limit ?, ?";

        List<BankAccountTransaction> list = new ArrayList<>();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, bankAccount.getAccountUuid());
            pstm.setString(2, bankAccount.getAccountUuid());
            pstm.setInt(3, start);
            pstm.setInt(4, total);

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
     * Find rows count in bank account transactions table
     * @param connection
     * @return
     */
    public static int getBankAccountTransactionsCountByUuid(Connection connection, BankAccount bankAccount) {
        String sql = "Select count(*) as rowcount from Bank_Account_Transaction where Bank_Account_From_Uuid = ? or Bank_Account_To_Uuid = ?";

        int count = 0;

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, bankAccount.getAccountUuid());
            pstm.setString(2, bankAccount.getAccountUuid());
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                count = rs.getInt("rowcount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Find bank account transactions by bank account's uuid
     *
     * @param connection
     * @param bankAccountTransactionId
     * @return
     */
    public static BankAccountTransaction findBankAccountTransactionById(Connection connection, int bankAccountTransactionId) {

        String sql = "Select t.Transaction_Id, t.Bank_Account_From_Uuid, t.Bank_Account_To_Uuid, t.Transaction_Date, t.Transaction_Amount, t.Transaction_Target " +
                "from Bank_Account_Transaction t where t.Transaction_Id = ?";

        BankAccountTransaction bankAccountTransaction = null;

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, bankAccountTransactionId);

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("Transaction_id");
                String uuidFrom = rs.getString("Bank_Account_From_Uuid");
                String uuidTo = rs.getString("Bank_Account_To_Uuid");
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Transaction_Date"));
                double amount = rs.getDouble("Transaction_Amount");
                String target = rs.getString("Transaction_Target");

                BankAccount from;
                BankAccount to;

                to = findBankAccountByUuid(connection, uuidTo);
                from = findBankAccountByUuid(connection, uuidFrom);

                bankAccountTransaction = new BankAccountTransaction();

                bankAccountTransaction.setTransactionId(id);
                bankAccountTransaction.setBankAccountFrom(from);
                bankAccountTransaction.setBankAccountTo(to);
                bankAccountTransaction.setTransactionDate(date);
                bankAccountTransaction.setTransactionAmount(amount);
                bankAccountTransaction.setTransactionTarget(target);

            }
        } catch (SQLException e) {
            logger.error("error while find bank account transactions");
            e.printStackTrace();
        } catch (ParseException e) {
            logger.error("error while parse date during finding bank account transactions");
            e.printStackTrace();
        }

        return bankAccountTransaction;
    }

    /**
     * Insert bank account transaction to db
     *
     * @param connection
     * @param bankAccountTransaction
     * @return
     */
    public static boolean insertBankAccountTransaction(Connection connection, BankAccountTransaction bankAccountTransaction) {

        String sql = "Insert into Bank_Account_Transaction (Bank_Account_From_Uuid, Bank_Account_To_Uuid, Transaction_Date," +
                "Transaction_Amount, Transaction_Target) values(?, ?, ?, ?, ?)";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int result = 0;

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, bankAccountTransaction.getBankAccountFrom().getAccountUuid());
            pstm.setString(2, bankAccountTransaction.getBankAccountTo().getAccountUuid());
            pstm.setString(3, sdf.format(bankAccountTransaction.getTransactionDate()));
            pstm.setDouble(4, bankAccountTransaction.getTransactionAmount());
            pstm.setString(5, bankAccountTransaction.getTransactionTarget());

            connection.setAutoCommit(false);

            result = pstm.executeUpdate();

            connection.commit();
            logger.info("bank account transaction created");
        } catch (SQLException e) {
            logger.error("error while insert bank account transaction");
            e.printStackTrace();

            ConnectionUtils.rollbackQuietly(connection);
            return false;
        }

        return true;
    }

    public static boolean deleteBankAccountTransaction(Connection connection, int bankAccountTransactionId) {

        String sql = "Delete from Bank_Account_Transaction where Transaction_Id = ?";

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, bankAccountTransactionId);

            connection.setAutoCommit(false);

            pstm.executeUpdate();

            connection.commit();
            logger.info("bank account transaction with id " + bankAccountTransactionId + " deleted");
        } catch (SQLException e) {
            logger.info("bank account transaction with id " + bankAccountTransactionId + " deleted");

            e.printStackTrace();

            ConnectionUtils.rollbackQuietly(connection);
            return false;
        }

        return true;
    }

    /**
     * Find rows count in bank account table
     * @param connection
     * @return
     */
    public static int getBankAccountsCount(Connection connection, UserAccount userAccount){
        String sql = "Select count(*) as rowcount from Bank_Account where Account_Owner = ?";

        int count = 0;

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userAccount.getUserAccountEmail());
            ResultSet rs = pstm.executeQuery();

            if(rs.next()){
                count = rs.getInt("rowcount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
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
     * Insert bank account data to db
     *
     * @param connection
     * @param bankAccount
     * @return
     */
    public static boolean insertBankAccount(Connection connection, BankAccount bankAccount) {

        String sql = "Insert into Bank_Account values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, bankAccount.getAccountUuid());
            pstm.setDouble(2, bankAccount.getAccountBalance());
            pstm.setString(3, sdf.format(bankAccount.getAccountCreationDate()));
            pstm.setString(4, sdf.format(bankAccount.getAccountExpirationDate()));
            pstm.setString(5, bankAccount.getAccountOwner().getUserAccountEmail());
            pstm.setDouble(6, bankAccount.getAccountLimit());
            pstm.setDouble(7, bankAccount.getAccountInterestRate());
            pstm.setDouble(8, bankAccount.getAccountDebt());
            pstm.setInt(9, bankAccount.getAccountType().ordinal());

            connection.setAutoCommit(false);

            pstm.executeUpdate();

            connection.commit();
            logger.info("bank account with uuid " + bankAccount.getAccountUuid() + " inserted");
        } catch (SQLException e) {
            logger.error("error while inserting bank account with uuid " + bankAccount.getAccountUuid());
            e.printStackTrace();

            ConnectionUtils.rollbackQuietly(connection);
            return false;
        }

        return true;
    }

    /**
     * Update bank account data in db
     *
     * @param connection
     * @param bankAccount
     * @return
     */
    public static boolean updateBankAccount(Connection connection, BankAccount bankAccount) {

        String sql = "Update Bank_Account set Account_Balance = ?, Account_Expiration_Date = ?, Account_Owner = ?, Account_Limit = ?," +
                "Account_Interest_Rate = ?, Account_Debt = ?, Account_Type = ? where Account_Uuid = ?";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setDouble(1, bankAccount.getAccountBalance());
            pstm.setString(2, sdf.format(bankAccount.getAccountExpirationDate()));
            pstm.setString(3, bankAccount.getAccountOwner().getUserAccountEmail());
            pstm.setDouble(4, bankAccount.getAccountLimit());
            pstm.setDouble(5, bankAccount.getAccountInterestRate());
            pstm.setDouble(6, bankAccount.getAccountDebt());
            pstm.setInt(7, bankAccount.getAccountType().ordinal());
            pstm.setString(8, bankAccount.getAccountUuid());

            connection.setAutoCommit(false);

            pstm.executeUpdate();

            connection.commit();
            logger.info("bank account updated");
        } catch (SQLException e) {
            logger.error("error while update bank account");
            e.printStackTrace();

            ConnectionUtils.rollbackQuietly(connection);
            return false;
        }

        return true;
    }

    /**
     * Delete bank account from db
     *
     * @param connection
     * @param bankAccountUuid
     * @return
     */
    public static boolean deleteBankAccount(Connection connection, String bankAccountUuid) {

        String sql = "Delete from Bank_Account where Account_Uuid = ?";

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, bankAccountUuid);

            connection.setAutoCommit(false);

            pstm.executeUpdate();

            connection.commit();
            logger.info("bank account with uuid " + bankAccountUuid + " deleted");
        } catch (SQLException e) {
            logger.error("error while deleting bak account with uuid " + bankAccountUuid);
            e.printStackTrace();

            ConnectionUtils.rollbackQuietly(connection);
            return false;
        }

        return true;
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

            ConnectionUtils.rollbackQuietly(connection);
            return false;
        }

        return true;
    }

    public static boolean updateBankAccountOrder(Connection connection, BankAccountOrder order){

        String sql = "Update Bank_Account_Order set Order_Create_Date = ?, Order_Owner = ?, Order_Status = ?, Account_Expiration_Date = ?," +
                "Account_Balance = ?, Account_Limit = ?, Account_Interest_Rate = ?, Account_Type = ? where Order_Id = ?";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, sdf.format(order.getOrderCreateDate()));
            pstm.setString(2, order.getOrderOwner().getUserAccountEmail());
            pstm.setInt(3, order.getOrderStatus().ordinal());
            pstm.setString(4, sdf.format(order.getAccountExpirationDate()));
            pstm.setDouble(5, order.getAccountBalance());
            pstm.setDouble(6, order.getAccountLimit());
            pstm.setDouble(7, order.getAccountInterestRate());
            pstm.setInt(8, order.getAccountType().ordinal());
            pstm.setInt(9, order.getOrderId());

            connection.setAutoCommit(false);

            pstm.executeUpdate();

            connection.commit();
            logger.info("bank account order updated");
        } catch (SQLException e) {
            logger.error("error while updating bank account order");
            e.printStackTrace();

            ConnectionUtils.rollbackQuietly(connection);
            return false;
        }

        return true;
    }

    /**
     * Delete bank account order by id
     *
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

            ConnectionUtils.rollbackQuietly(connection);
            return false;
        }

        return true;
    }

    /**
     * Find bank account order by order id
     * @param connection
     * @param bankAccountOrderId
     * @return
     */
    public static BankAccountOrder findBankAccountOrderById(Connection connection, int bankAccountOrderId) {

        String sql = "Select b.Order_Id, b.Order_Create_Date, b.Order_Owner, b.Order_Status, b.Account_Expiration_Date," +
                "b.Account_Balance, b.Account_Limit, b.Account_Interest_Rate, b.Account_Type from Bank_Account_Order b where b.Order_Id = ?";

        BankAccountOrder bankAccountOrder = new BankAccountOrder();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, bankAccountOrderId);
            ResultSet rs = pstm.executeQuery();

            if(rs.next()){
                int orderId = rs.getInt("Order_Id");
                Date createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Order_Create_Date"));
                Date expirationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Account_Expiration_Date"));
                BankAccountOrder.OrderStatus status = BankAccountOrder.OrderStatus.values()[rs.getInt("Order_Status")];
                double accountBalance = rs.getDouble("Account_Balance");
                double accountLimit = rs.getDouble("Account_Limit");
                double interestRate = rs.getDouble("Account_Interest_Rate");
                BankAccount.AccountType accountType = BankAccount.AccountType.values()[rs.getInt("Account_Type")];
                String orderOwner = rs.getString("Order_Owner");

                UserAccount orderOwnerAccount = UserDAO.findUserByEmail(connection, orderOwner);


                bankAccountOrder.setOrderId(orderId);
                bankAccountOrder.setOrderCreateDate(createDate);
                bankAccountOrder.setAccountExpirationDate(expirationDate);
                bankAccountOrder.setOrderStatus(status);
                bankAccountOrder.setAccountBalance(accountBalance);
                bankAccountOrder.setAccountLimit(accountLimit);
                bankAccountOrder.setAccountInterestRate(interestRate);
                bankAccountOrder.setAccountType(accountType);
                bankAccountOrder.setOrderOwner(orderOwnerAccount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("error while find bank account order in db");
        } catch (ParseException e) {
            logger.error("error while parse date during find bank account order in db");
            e.printStackTrace();
        }

        return bankAccountOrder;
    }

    /**
     * Find all user orders from db
     *
     * @param connection
     * @return
     */
    public static List<BankAccountOrder> findAllBankAccountOrdersInProgress(Connection connection, int start, int total) {
        String sql = "Select b.Order_Id, b.Order_Create_Date, b.Order_Owner, b.Order_Status, b.Account_Expiration_Date, " +
                "b.Account_Balance, b.Account_Limit, b.Account_Interest_Rate, b.Account_Type from Bank_Account_Order b where b.Order_Status = ? limit ?, ?";

        List<BankAccountOrder> bankAccountOrders = new LinkedList<>();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, BankAccountOrder.OrderStatus.IN_PROGRESS.ordinal());
            pstm.setInt(2, start);
            pstm.setInt(3, total);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                BankAccountOrder bankAccountOrder = new BankAccountOrder();

                int orderId = rs.getInt("Order_Id");
                Date createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Order_Create_Date"));
                Date expirationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Account_Expiration_Date"));
                BankAccountOrder.OrderStatus status = BankAccountOrder.OrderStatus.values()[rs.getInt("Order_Status")];
                double accountBalance = rs.getDouble("Account_Balance");
                double accountLimit = rs.getDouble("Account_Limit");
                double interestRate = rs.getDouble("Account_Interest_Rate");
                BankAccount.AccountType accountType = BankAccount.AccountType.values()[rs.getInt("Account_Type")];
                String orderOwner = rs.getString("Order_Owner");

                UserAccount orderOwnerAccount = UserDAO.findUserByEmail(connection, orderOwner);

                bankAccountOrder.setOrderId(orderId);
                bankAccountOrder.setOrderCreateDate(createDate);
                bankAccountOrder.setAccountExpirationDate(expirationDate);
                bankAccountOrder.setOrderStatus(status);
                bankAccountOrder.setAccountBalance(accountBalance);
                bankAccountOrder.setAccountLimit(accountLimit);
                bankAccountOrder.setAccountInterestRate(interestRate);
                bankAccountOrder.setAccountType(accountType);
                bankAccountOrder.setOrderOwner(orderOwnerAccount);

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

    /**
     * Find rows count in bank account users orders table
     * @param connection
     * @return
     */
    public static int getBankAccountsUsersOrdersCount(Connection connection) {
        String sql = "Select count(*) as rowcount from Bank_Account_Order where Order_Status = ?";

        int count = 0;

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, BankAccountOrder.OrderStatus.IN_PROGRESS.ordinal());
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                count = rs.getInt("rowcount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }


        /**
         * Find bank account orders by user account email in db
         *
         * @param connection
         * @param userAccount
         * @return
         */
    public static List<BankAccountOrder> findBankAccountOrdersByUserAccount(Connection connection, UserAccount userAccount, int start, int total) {

        String sql = "Select b.Order_Id, b.Order_Create_Date, b.Order_Owner, b.Order_Status, b.Account_Expiration_Date, " +
                "b.Account_Balance, b.Account_Limit, b.Account_Interest_Rate, b.Account_Type from Bank_Account_Order b " +
                "where b.Order_Owner = ? limit ?, ?";

        List<BankAccountOrder> bankAccountOrders = new LinkedList<>();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userAccount.getUserAccountEmail());
            pstm.setInt(2, start);
            pstm.setInt(3, total);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
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

    /**
     * Find rows count in bank account user orders table
     * @param connection
     * @return
     */
    public static int getBankAccountsUserOrdersByUserAccountCount(Connection connection, UserAccount logined) {
        String sql = "Select count(*) as rowcount from Bank_Account_Order where Order_Owner = ?";

        int count = 0;

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, logined.getUserAccountEmail());
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                count = rs.getInt("rowcount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
}
