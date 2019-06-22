import com.myProject.bankSystem.pagination.Pagination;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class PaginationTest {

    private Mockito mockito;
    private Pagination pagination;

    @Test
    public void paginationCalculateAllPagesTest(){
        pagination = new Pagination(1, 100, 10);

        Assert.assertTrue(pagination.getAllPages() == 10);

        pagination = new Pagination(1, 100, 100);

        Assert.assertTrue(pagination.getAllPages() == 1);
    }

    @Test
    public void paginationUpdatePageIdTest(){
        pagination = new Pagination(2, 100, 10);

        Assert.assertTrue(pagination.getPageId() == 11);

        pagination = new Pagination(10, 100, 10);

        Assert.assertTrue(pagination.getPageId() == 91);
    }
}
