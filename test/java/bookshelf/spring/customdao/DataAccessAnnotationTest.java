package bookshelf.spring.customdao;

import bookshelf.boot.config.CustomAnnotationConfiguration;
import bookshelf.jpa.entities.Author;
import bookshelf.jpa.entities.BookOwner;
import bookshelf.jpa.repos.AllPurposeDAO;
import bookshelf.jpa.repos.DataAccess;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CustomAnnotationConfiguration.class})
public class DataAccessAnnotationTest {

    @DataAccess(entity = Author.class)
    private AllPurposeDAO<Author> authorDao;

    @DataAccess(entity = BookOwner.class)
    private AllPurposeDAO<BookOwner> bookOwnerDao;

    @DataAccess(entity = Author.class)
    private AllPurposeDAO<Author> anotherAuthorDao;


    @Test
    public void test_IsInjectedDaoSingleton() {
        Assert.assertNotSame(authorDao, bookOwnerDao);
        Assert.assertNotEquals(authorDao, bookOwnerDao);
        Assert.assertSame(authorDao, anotherAuthorDao);
    }
}
