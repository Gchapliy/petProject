package com.epamTranings.bankSystem.dao;
import com.epamTranings.bankSystem.entity.bankAccount.BankAccount;
import com.epamTranings.bankSystem.entity.userAccount.Role;
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

        String sql = "Select u.Account_Id, u.Account_Name, u.Account_Gender, u.Account_Encrypted_Password, u.Account_Role, u.Account_Email, u.Account_Phone from User_Account u" +
                " where u.Account_Email = ?";


        PreparedStatement pstm = null;
        try {
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, userEmail);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                int accountId = rs.getInt("Account_Id");
                String accountName = rs.getString("Account_Name");
                String accountGender = rs.getString("Account_Gender");
                String accountEncryptedPassword = rs.getString("Account_Encrypted_Password");
                int accountRole = rs.getInt("Account_Role");
                String accountPhone = rs.getString("Account_Phone");

                Role userRole = findUserRoleByName(connection, accountRole);

                UserAccount user = new UserAccount();
                user.setUserAccountId(accountId);
                user.setUserAccountName(accountName);
                user.setUserAccountGender(accountGender);
                user.setUserAccountEncryptedPassword(accountEncryptedPassword);
                user.setUserAccountRole(userRole);
                user.setUserAccountEmail(userEmail);
                user.setUserAccountPhone(accountPhone);

                logger.info(user + " founded");

                return user;
            }
        } catch (SQLException e) {
            logger.error("error while find user");
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
    public static Role findUserRoleByName(Connection conn, int roleId) {

        String sql = "Select r.Role_Id, r.Role_Name from Role r" +
                " where r.Role_Id = ?";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
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
     * Find user bank accounts by user Email
     * @param connection
     * @param userAccount
     * @return
     */
    public static List<BankAccount> findUserBankAccounts(Connection connection, UserAccount userAccount) {

        String sql = "Select a.Account_Id, a.Account_Balance, a.Account_Create_Date, a.Account_Expiration_Date, " +
                     "a.Account_Owner, a.Account_Limit, a.Account_Interest_Rate, a.Account_Debt, a.Account_Type, " +
                     "a.Account_Uuid from Bank_Account a " +
                     "where a.Account_Owner=(select u.Account_Id from User_Account u where u.Account_Email=?)";

        List<BankAccount> list = new ArrayList<>();
        BankAccount bankAccount;

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userAccount.getUserAccountEmail());

            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                int id = rs.getInt("Account_Id");
                double balance = rs.getDouble("Account_Balance");
                Date createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Account_Create_Date"));
                Date expiratingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("Account_Expiration_Date"));
                double limit = rs.getDouble("Account_Limit");
                int interestRate = rs.getInt("Account_Interest_Rate");
                double debt = rs.getDouble("Account_Debt");
                BankAccount.AccountType type = BankAccount.AccountType.values()[rs.getInt("Account_Type")];
                String uuid = rs.getString("Account_Uuid");

                bankAccount = new BankAccount();
                bankAccount.setAccountId(id);
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
            logger.error("error while parse date");
            e.printStackTrace();
        }

        return list;
    }
}
