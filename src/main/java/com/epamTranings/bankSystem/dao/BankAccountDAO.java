package com.epamTranings.bankSystem.dao;

import com.epamTranings.bankSystem.entity.bankAccount.BankAccountTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class BankAccountDAO {

    final static Logger logger = LogManager.getLogger(BankAccountDAO.class);

    public static List<BankAccountTransaction> findBankAccountTransactionsByUuid(Connection connection, String uuid){

        return null;
    }
}
