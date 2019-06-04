import com.epamTranings.bankSystem.dao.UserDAO;
import com.epamTranings.bankSystem.dbConnection.MySQLConnectionUtil;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

public class DAOTest {
    private MySQLConnectionUtil connection;
    private UserDAO userDAO;

    @Before
    public void init(){
    }

    @Test
    public void daoTest(){
        UserAccount userAccount = userDAO.findUserByEmail(connection.getMySQLConnection(), "grishachapliy1@gmail.com");
        System.out.println(userAccount);
    }
}
