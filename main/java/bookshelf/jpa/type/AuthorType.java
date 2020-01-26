package bookshelf.jpa.type;

import bookshelf.jpa.entities.Author;
import bookshelf.jpa.entities.Person;
import bookshelf.jpa.service.BookShelfService;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class AuthorType extends CommonType {
    public static final AuthorType INSTANCE = new AuthorType();

    private BookShelfService shelfService;

    @Autowired
    public void setShelfService(BookShelfService shelfService) {
        this.shelfService = shelfService;
    }

    public AuthorType() {
        super(Author.class);
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{LongType.INSTANCE.sqlType()};
    }

    @Override
    public Author nullSafeGet(ResultSet resultSet,
                              String[] strings,
                              SharedSessionContractImplementor sharedSessionContractImplementor,
                              Object o) throws HibernateException, SQLException {
        Author author = new Author();
        long id = Long.parseLong(resultSet.getString(strings[0]));
        Optional<Person> person = shelfService.findPersonById(id);

        if (person.isPresent()) {
            author.setPerson(person.get());
            return author;
        } else throw new IllegalArgumentException("SEVERE: No Person found with id: "+id);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement,
                            Object o,
                            int i,
                            SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (o == null) {
            preparedStatement.setNull(i, LongType.INSTANCE.sqlType());
        } else {
            Author author = (Author) o;
            if (author.getId() != 0) preparedStatement.setLong(i, author.getId());
            preparedStatement.setLong(i+1, author.getPerson().getId());
        }
    }
}
