import com.myProject.bankSystem.dao.BankAccountDAO;
import com.myProject.bankSystem.dao.UserDAO;
import com.myProject.bankSystem.bean.bankAccount.BankAccount;
import com.myProject.bankSystem.bean.bankAccount.BankAccountOrder;
import com.myProject.bankSystem.bean.bankAccount.BankAccountTransaction;
import com.myProject.bankSystem.bean.userAccount.Role;
import com.myProject.bankSystem.utils.dbConnectionUtils.MySQLConnectionUtil;
import com.myProject.bankSystem.bean.userAccount.UserAccount;
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

    private final int TEST_COUNTER = 10;

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
        accounts = userDAO.findUserBankAccounts(connection, userAccount, 1, 10);
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
        transactions = bankAccountDAO.findBankAccountTransactionsByUuid(connection, bankAccount, 0, 100);
    }

    @Test
    public void insertBankAccountOrderTest(){
        BankAccountOrder bankAccountOrder;
        BankAccountOrder bankAccountOrderRead;
        userAccount = userDAO.findUserByEmail(connection, "grishachapliy1@gmail.com");
        double balance;

        for (int i = 0; i < TEST_COUNTER; i++) {
            balance = i;

            bankAccountOrder = mockito.mock(BankAccountOrder.class);

            setMockBankAccountOrderGetDataRules(mockito, bankAccountOrder, userAccount, balance);

            Assert.assertTrue(bankAccountDAO.insertBankAccountOrder(connection, bankAccountOrder));

            List<BankAccountOrder> orders = bankAccountDAO.findBankAccountOrdersByUserAccount(connection, userAccount, 0, 100);

            bankAccountOrderRead = orders.get(orders.size() - 1);

            Assert.assertTrue(bankAccountOrder.getAccountBalance() == bankAccountOrderRead.getAccountBalance());

            Assert.assertTrue(bankAccountDAO.deleteBankAccountOrderById(connection, bankAccountOrderRead.getOrderId()));
        }
    }

    @Test
    public void insertUserAccountTest(){
        UserAccount userAccount;
        UserAccount userAccountRead;

        for (int i = 3; i < TEST_COUNTER; i++) {
            userAccount = mockito.mock(UserAccount.class);

            setMockUserAccountGetDataRules(mockito, userAccount, i);

            Assert.assertTrue(userDAO.insertUserAccount(connection, userAccount));

            userAccountRead = userDAO.findUserByEmail(connection, userAccount.getUserAccountEmail());
            Assert.assertTrue(userAccountRead != null);

            Assert.assertTrue(userDAO.deleteUserAccount(connection, userAccount));
        }

    }

    @Test
    public void findRoleTest(){

        Role roleAdmin = UserDAO.findUserRoleByName(connection, "ROLE_ADMIN");
        Role roleUser = UserDAO.findUserRoleByName(connection, "ROLE_USER");

        Assert.assertTrue(roleAdmin.getRoleName().equals("ROLE_ADMIN"));
        Assert.assertTrue(roleUser.getRoleName().equals("ROLE_USER"));
    }

    @Test
    public void insertBankAccountTransactionTest(){
        BankAccountTransaction bankAccountTransaction;
        BankAccountTransaction bankAccountTransactionRead;
        userAccount = userDAO.findUserByEmail(connection, "grishachapliy1@gmail.com");
        String uuid = "82b07ab1-d082-4be4-a9c4-52f959c295cb";

        bankAccount = bankAccountDAO.findBankAccountByUuid(connection, uuid);

        for (int i = 0; i < TEST_COUNTER; i++) {
            bankAccountTransaction = mockito.mock(BankAccountTransaction.class);

            setMockBankAccountTransactionGetDataRules(mockito, bankAccountTransaction, bankAccount);

            bankAccountDAO.insertBankAccountTransaction(connection, bankAccountTransaction);

            List<BankAccountTransaction> list = bankAccountDAO.findBankAccountTransactionsByUuid(connection, bankAccount, 0, 100);
            bankAccountTransactionRead = list.get(list.size() - 1);

            Assert.assertTrue(bankAccountTransaction.getTransactionTarget().equals(bankAccountTransactionRead.getTransactionTarget()));

            Assert.assertTrue(bankAccountDAO.deleteBankAccountTransaction(connection, bankAccountTransactionRead.getTransactionId()));
        }
    }

    @Test
    public void updateBankAccountTest(){
        BankAccount test = mockito.mock(BankAccount.class);
        userAccount = userDAO.findUserByEmail(connection, "grishachapliy1@gmail.com");

        setMockBankAccountGetDataRules(mockito, test, userAccount);

        bankAccountDAO.insertBankAccount(connection, test);

        for (int i = 0; i < TEST_COUNTER; i++) {

            test.setAccountBalance(test.getAccountBalance() + i);
            Assert.assertTrue(bankAccountDAO.updateBankAccount(connection, test));

            BankAccount bankAccountRead = bankAccountDAO.findBankAccountByUuid(connection, test.getAccountUuid());

            Assert.assertTrue(test.getAccountUuid().equals(bankAccountRead.getAccountUuid()));
        }

        Assert.assertTrue(bankAccountDAO.deleteBankAccount(connection, test.getAccountUuid()));

    }

    @Test
    public void findRowsAccountCountTest(){
        userAccount = userDAO.findUserByEmail(connection, "grishachapliy1@gmail.com");

        Assert.assertTrue(BankAccountDAO.getBankAccountsCount(connection, userAccount) > 0);
    }

    private void setMockUserAccountGetDataRules(Mockito mock, UserAccount userAccount, int number){
        Role role = new Role();
        role.setRoleID(2);
        role.setRoleName("ROLE_USER");

        mock.when(userAccount.getUserAccountEmail()).thenReturn("email" + number);
        mock.when(userAccount.getUserAccountName()).thenReturn("name" + number);
        mock.when(userAccount.getUserAccountRole()).thenReturn(role);
        mock.when(userAccount.getUserAccountEncryptedPassword()).thenReturn("password" + number);
        mock.when(userAccount.getUserAccountGender()).thenReturn("male");
        mock.when(userAccount.getAccountCreateDate()).thenReturn(new Date());
        mock.when(userAccount.getUserAccountPhone()).thenReturn("phone" + number);

    }

    private void setMockBankAccountOrderGetDataRules(Mockito mock, BankAccountOrder bankAccountOrder, UserAccount userAccount, double balance){
        mock.when(bankAccountOrder.getOrderCreateDate()).thenReturn(new Date());
        mock.when(bankAccountOrder.getAccountExpirationDate()).thenReturn(new Date());
        mock.when(bankAccountOrder.getOrderOwner()).thenReturn(userAccount);
        mock.when(bankAccountOrder.getOrderStatus()).thenReturn(BankAccountOrder.OrderStatus.ALLOWED);
        mock.when(bankAccountOrder.getAccountType()).thenReturn(BankAccount.AccountType.PAYMENT);
        mock.when(bankAccountOrder.getAccountBalance()).thenReturn(balance);
    }

    private void setMockBankAccountTransactionGetDataRules(Mockito mock, BankAccountTransaction bankAccountTransaction, BankAccount bankAccount){
        mock.when(bankAccountTransaction.getTransactionAmount()).thenReturn(200d);
        mock.when(bankAccountTransaction.getBankAccountFrom()).thenReturn(bankAccount);
        mock.when(bankAccountTransaction.getBankAccountTo()).thenReturn(bankAccount);
        mock.when(bankAccountTransaction.getTransactionDate()).thenReturn(new Date());
        mock.when(bankAccountTransaction.getTransactionTarget()).thenReturn("test");
    }

    private void setMockBankAccountGetDataRules(Mockito mock, BankAccount bankAccount, UserAccount userAccount){
        mock.when(bankAccount.getAccountUuid()).thenReturn("test");
        mock.when(bankAccount.getAccountBalance()).thenReturn(100d);
        mock.when(bankAccount.getAccountType()).thenReturn(BankAccount.AccountType.PAYMENT);
        mock.when(bankAccount.getAccountDebt()).thenReturn(100d);
        mock.when(bankAccount.getAccountExpirationDate()).thenReturn(new Date());
        mock.when(bankAccount.getAccountInterestRate()).thenReturn(10d);
        mock.when(bankAccount.getAccountLimit()).thenReturn(100d);
        mock.when(bankAccount.getAccountOwner()).thenReturn(userAccount);
        mock.when(bankAccount.getAccountCreationDate()).thenReturn(new Date());
    }
}