package com.epamTranings.bankSystem.dao;

import com.epamTranings.bankSystem.entity.bankAccount.BankAccount;
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

        PreparedStatement pstm;

        try {
            pstm = connection.prepareStatement(sql);
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

                if(uuidFrom.equals(bankAccount.getAccountUuid())) {
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
     * @param connection
     * @param uuid
     * @return
     */
    public static BankAccount findBankAccountByUuid(Connection connection, String uuid) {

        String sql = "Select a.Account_Balance, a.Account_Create_Date, a.Account_Expiration_Date, " +
                "a.Account_Owner, a.Account_Limit, a.Account_Interest_Rate, a.Account_Debt, a.Account_Type, " +
                "a.Account_Uuid from Bank_Account a " +
                "where a.Account_Uuid = ?";

        PreparedStatement pstm;
        BankAccount bankAccount = null;
        UserAccount owner;

        try {
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, uuid);
            ResultSet rs = pstm.executeQuery();

            if(rs.next()){
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
}
