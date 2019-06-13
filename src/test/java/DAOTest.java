import com.epamTranings.bankSystem.dao.BankAccountDAO;
import com.epamTranings.bankSystem.dao.UserDAO;
import com.epamTranings.bankSystem.entity.bankAccount.BankAccount;
import com.epamTranings.bankSystem.entity.bankAccount.BankAccountOrder;
import com.epamTranings.bankSystem.entity.bankAccount.BankAccountTransaction;
import com.epamTranings.bankSystem.utils.dbConnectionUtils.MySQLConnectionUtil;
import com.epamTranings.bankSystem.entity.userAccount.UserAccount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class DAOTest {
    private UserDAO userDAO;
    private BankAccountDAO bankAccountDAO;
    private UserAccount userAccount;
    private BankAccount bankAccount;
    private List<BankAccount> accounts;
    private List<BankAccountTransaction> transactions;
    private Connection connection;
    private Mockito mockito;

    @Before
    public void init(){
        connection = MySQLConnectionUtil.getMySQLConnection();
    }

    @Test
    public void findUserByEmailTest(){
        userAccount = userDAO.findUserByEmail(connection, "grishachapliy1@gmail.com");
    }

    @Test
    public void findBankAccountsByUserAccountEmailTest(){
        userAccount = userDAO.findUserByEmail(connection, "grishachapliy1@gmail.com");
        accounts = userDAO.findUserBankAccounts(connection, userAccount);
    }

    @Test
    public void findBankAccountByUuidTest(){
        String uuid = "82b07ab1-d082-4be4-a9c4-52f959c295cb";
        bankAccount = bankAccountDAO.findBankAccountByUuid(connection, uuid);
    }

    @Test
    public void findBankAccountTransactionsByBankAccountUuidTest(){
        String uuid = "82b07ab1-d082-4be4-a9c4-52f959c295cb";
        bankAccount = bankAccountDAO.findBankAccountByUuid(connection, uuid);
        transactions = bankAccountDAO.findBankAccountTransactionsByUuid(connection, bankAccount);
    }

    @Test
    public void insertBankAccountOrderTest(){
        BankAccountOrder bankAccountOrder;
        BankAccountOrder bankAccountOrderRead;
        userAccount = userDAO.findUserByEmail(connection, "grishachapliy1@gmail.com");
        double balance;

        for (int i = 0; i < 10; i++) {
            balance = i;

            bankAccountOrder = mockito.mock(BankAccountOrder.class);

            setMockGetDataRules(mockito, bankAccountOrder, userAccount, balance);

            Assert.assertTrue(bankAccountDAO.insertBankAccountOrder(connection, bankAccountOrder));

            bankAccountOrderRead = bankAccountDAO.findBankAccountOrderByUserAccount(connection, userAccount).get(0);
            Assert.assertTrue(bankAccountOrder.getAccountBalance() == bankAccountOrderRead.getAccountBalance());

            Assert.assertTrue(bankAccountDAO.deleteBankAccountOrderById(connection, bankAccountOrderRead.getOrderId()));
        }
    }

    private void setMockGetDataRules(Mockito mock, BankAccountOrder bankAccountOrder, UserAccount userAccount, double balance){
        mock.when(bankAccountOrder.getOrderCreateDate()).thenReturn(new Date());
        mock.when(bankAccountOrder.getAccountExpirationDate()).thenReturn(new Date());
        mock.when(bankAccountOrder.getOrderOwner()).thenReturn(userAccount);
        mock.when(bankAccountOrder.getOrderStatus()).thenReturn(BankAccountOrder.OrderStatus.ALLOWED);
        mock.when(bankAccountOrder.getAccountType()).thenReturn(BankAccount.AccountType.PAYMENT);
        mock.when(bankAccountOrder.getAccountBalance()).thenReturn(balance);
    }
}
