package com.epamTranings.bankSystem.dao;

import com.epamTranings.bankSystem.entity.bankAccount.BankAccount;
import com.epamTranings.bankSystem.entity.userAccount.Role;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import com.epamTranings.bankSystem.utils.dbConnectionUtils.ConnectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class UserDAO {
    final static Logger logger = LogManager.getLogger(UserDAO.class);

    /**
     * Find userAccount data in db by email
     *
     * @param connection
     * @param userEmail
     * @return
     * @throws SQLException
     */
    public static UserAccount findUserByEmail(Connection connection,
                                              String userEmail) {

        String sql = "Select u.Account_Name, u.Account_Gender, u.Account_Encrypted_Password, u.Account_Role, u.Account_Email, u.Account_Phone," +
                " u.Account_Create_Date from User_Account u" +
                " where u.Account_Email = ?";

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userEmail);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                String accountName = rs.getString("Account_Name");
                String accountGender = rs.getString("Account_Gender");
                String accountEncryptedPassword = rs.getString("Account_Encrypted_Password");
                int accountRole = rs.getInt("Account_Role");
                String accountPhone = rs.getString("Account_Phone");
                Date createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Account_Create_Date"));

                Role userRole = findUserRoleById(connection, accountRole);

                UserAccount user = new UserAccount();
                user.setUserAccountName(accountName);
                user.setUserAccountGender(accountGender);
                user.setUserAccountEncryptedPassword(accountEncryptedPassword);
                user.setUserAccountRole(userRole);
                user.setUserAccountEmail(userEmail);
                user.setUserAccountPhone(accountPhone);
                user.setAccountCreateDate(createDate);

                logger.info(user + " founded");

                return user;
            }
        } catch (SQLException e) {
            logger.error("error while find user");
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        logger.error("userAccount with email " + userEmail + " not founded");

        return null;
    }

    /**
     * Find userAccount role in db by role's name
     *
     * @param conn
     * @param roleId
     * @return
     * @throws SQLException
     */
    public static Role findUserRoleById(Connection conn, int roleId) {

        String sql = "Select r.Role_Id, r.Role_Name from Role r" +
                " where r.Role_Id = ?";

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, roleId);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                String roleName = rs.getString("Role_Name");

                Role userRole = new Role();
                userRole.setRoleID(roleId);
                userRole.setRoleName(roleName);

                logger.info(userRole + " founded");

                return userRole;
            }
        } catch (SQLException e) {
            logger.error("error while find role");
            e.printStackTrace();
        }


        logger.error("userRole with id " + roleId + " not founded");

        return null;
    }

    /**
     * Find userAccount role in db by role's name
     *
     * @param conn
     * @param roleName
     * @return
     * @throws SQLException
     */
    public static Role findUserRoleByName(Connection conn, String roleName) {

        String sql = "Select r.Role_Id, r.Role_Name from Role r" +
                " where r.Role_Name = ?";

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, roleName);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                int roleId = rs.getInt("Role_Id");

                Role userRole = new Role();
                userRole.setRoleID(roleId);
                userRole.setRoleName(roleName);

                logger.info(userRole + " founded");

                return userRole;
            }
        } catch (SQLException e) {
            logger.error("error while find role");
            e.printStackTrace();
        }

        logger.error("userRole with name " + roleName + " not founded");

        return null;
    }

    /**
     * Insert new user account to db
     * @param connection
     * @param account
     * @return
     */
    public static boolean insertUserAccount(Connection connection, UserAccount account){

        String sql = "Insert into User_Account values(?, ?, ?, ?, ?, ?, ?)";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, account.getUserAccountEmail());
            pstm.setString(2, account.getUserAccountName());
            pstm.setString(3, account.getUserAccountGender());
            pstm.setString(4, account.getUserAccountEncryptedPassword());
            pstm.setInt(5, account.getUserAccountRole().getRoleID());
            pstm.setString(6, account.getUserAccountPhone());
            pstm.setString(7, sdf.format(account.getAccountCreateDate()));

            connection.setAutoCommit(false);

            pstm.executeUpdate();

            connection.commit();
            logger.info("user account created");
        } catch (SQLException e) {
            logger.error("error while insert user account");
            e.printStackTrace();

            ConnectionUtils.rollbackQuietly(connection);
            return false;
        }

        return true;
    }

    /**
     * Delete user account from db
     * @param connection
     * @param account
     * @return
     */
    public static boolean deleteUserAccount(Connection connection, UserAccount account){

        String sql = "Delete from User_Account where Account_Email = ?";

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, account.getUserAccountEmail());

            connection.setAutoCommit(false);

            pstm.executeUpdate();

            connection.commit();
            logger.info("user account with email: " + account.getUserAccountEmail() + " deleted");
        } catch (SQLException e) {
            logger.error("error while delete user account with email: " + account.getUserAccountEmail());
            e.printStackTrace();

            ConnectionUtils.rollbackQuietly(connection);
            return false;
        }

        return true;
    }

    /**
     * Find user bank accounts by user Email
     *
     * @param connection
     * @param userAccount
     * @return
     */
    public static List<BankAccount> findUserBankAccounts(Connection connection, UserAccount userAccount) {

        String sql = "Select a.Account_Balance, a.Account_Create_Date, a.Account_Expiration_Date, " +
                "a.Account_Owner, a.Account_Limit, a.Account_Interest_Rate, a.Account_Debt, a.Account_Type, " +
                "a.Account_Uuid from Bank_Account a " +
                "where a.Account_Owner=(select u.Account_Email from User_Account u where u.Account_Email=?)";

        List<BankAccount> list = new LinkedList<>();
        BankAccount bankAccount;

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userAccount.getUserAccountEmail());

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                double balance = rs.getDouble("Account_Balance");
                Date createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Account_Create_Date"));
                Date expiratingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Account_Expiration_Date"));
                double limit = rs.getDouble("Account_Limit");
                int interestRate = rs.getInt("Account_Interest_Rate");
                double debt = rs.getDouble("Account_Debt");
                BankAccount.AccountType type = BankAccount.AccountType.values()[rs.getInt("Account_Type")];
                String uuid = rs.getString("Account_Uuid");

                bankAccount = new BankAccount();
                bankAccount.setAccountBalance(balance);
                bankAccount.setAccountCreationDate(createDate);
                bankAccount.setAccountExpirationDate(expiratingDate);
                bankAccount.setAccountLimit(limit);
                bankAccount.setAccountInterestRate(interestRate);
                bankAccount.setAccountDebt(debt);
                bankAccount.setAccountType(type);
                bankAccount.setAccountOwner(userAccount);
                bankAccount.setAccountUuid(uuid);

                list.add(bankAccount);
            }
        } catch (SQLException e) {
            logger.error("error while find user bank accounts");
            e.printStackTrace();
        } catch (ParseException e) {
            logger.error("error while parse date during finding user bank accounts");
            e.printStackTrace();
        }

        return list;
    }
}
