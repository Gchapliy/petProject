import com.epamTranings.bankSystem.dao.UserDAO;
import com.epamTranings.bankSystem.entity.bankAccount.BankAccount;
import com.epamTranings.bankSystem.utils.dbConnectionUtils.MySQLConnectionUtil;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DAOTest {
    private MySQLConnectionUtil connection;
    private UserDAO userDAO;
    private UserAccount userAccount;
    private List<BankAccount> accounts;

    @Before
    public void init(){
    }

    @Test
    public void findUserTest(){
        userAccount = userDAO.findUserByEmail(connection.getMySQLConnection(), "grishachapliy1@gmail.com");
    }

    @Test
    public void findBankAccounts(){
        userAccount = userDAO.findUserByEmail(connection.getMySQLConnection(), "grishachapliy1@gmail.com");
        accounts = userDAO.findUserBankAccounts(connection.getMySQLConnection(), userAccount);
        System.out.println(accounts);
    }
}
