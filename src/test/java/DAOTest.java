import com.epamTranings.bankSystem.dao.BankAccountDAO;
import com.epamTranings.bankSystem.dao.UserDAO;
import com.epamTranings.bankSystem.entity.bankAccount.BankAccount;
import com.epamTranings.bankSystem.entity.bankAccount.BankAccountTransaction;
import com.epamTranings.bankSystem.utils.dbConnectionUtils.MySQLConnectionUtil;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class DAOTest {
    private UserDAO userDAO;
    private BankAccountDAO bankAccountDAO;
    private UserAccount userAccount;
    private BankAccount bankAccount;
    private List<BankAccount> accounts;
    private List<BankAccountTransaction> transactions;
    private Connection connection;

    @Before
    public void init(){
        connection = MySQLConnectionUtil.getMySQLConnection();
    }

    @Test
    public void findUserByEmailTest(){
        userAccount = userDAO.findUserByEmail(connection, "grishachapliy1@gmail.com");
    }

    @Test
    public void findBankAccountsByUserAccountEmail(){
        userAccount = userDAO.findUserByEmail(connection, "grishachapliy1@gmail.com");
        accounts = userDAO.findUserBankAccounts(connection, userAccount);
    }

    @Test
    public void findBankAccountByUuid(){
        String uuid = "82b07ab1-d082-4be4-a9c4-52f959c295cb";
        bankAccount = bankAccountDAO.findBankAccountByUuid(connection, uuid);
    }

    @Test
    public void findBankAccountTransactionsByBankAccountUuid(){
        String uuid = "82b07ab1-d082-4be4-a9c4-52f959c295cb";
        bankAccount = bankAccountDAO.findBankAccountByUuid(connection, uuid);
        transactions = bankAccountDAO.findBankAccountTransactionsByUuid(connection, bankAccount);

        System.out.println(transactions);
    }
}
