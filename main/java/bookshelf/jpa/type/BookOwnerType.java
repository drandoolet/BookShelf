package bookshelf.jpa.type;

import bookshelf.jpa.entities.Author;
import bookshelf.jpa.entities.BookOwner;
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
public class BookOwnerType extends CommonType {
    public static final BookOwnerType INSTANCE = new BookOwnerType();

    private BookShelfService shelfService;

    public BookOwnerType() {
        super(BookOwner.class);
    }

    @Autowired
    public void setShelfService(BookShelfService shelfService) {
        this.shelfService = shelfService;
    }

    @Override
    public int[] sqlTypes() {
        return new int[] {LongType.INSTANCE.sqlType()};
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        BookOwner owner = new BookOwner();
        long id = Long.parseLong(resultSet.getString(strings[0]));
        Optional<Person> person = shelfService.findPersonById(id);

        if (person.isPresent()) {
            owner.setPerson(person.get());
            return owner;
        } else throw new IllegalArgumentException("SEVERE: No Person found with id: "+id);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (o == null) {
            preparedStatement.setNull(i, LongType.INSTANCE.sqlType());
        } else {
            BookOwner owner = (BookOwner) o;
            preparedStatement.setLong(i, owner.getPerson().getId());
        }
    }
}
