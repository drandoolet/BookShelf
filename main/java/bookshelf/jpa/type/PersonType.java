package bookshelf.jpa.type;

import bookshelf.Application;
import bookshelf.jpa.entities.Author;
import bookshelf.jpa.entities.Person;
import bookshelf.jpa.service.BookShelfService;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.LongType;
import org.hibernate.type.ShortType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class PersonType extends CommonType {
    public static final PersonType INSTANCE = new PersonType();

    private PersonType() {
        super(Person.class);
    }

    @Override
    public int[] sqlTypes() { // id, name, surname, year b, year p
        return new int[] {
                LongType.INSTANCE.sqlType(),
                StringType.INSTANCE.sqlType(),
                StringType.INSTANCE.sqlType(),
                ShortType.INSTANCE.sqlType(),
                ShortType.INSTANCE.sqlType()
        };
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet,
                              String[] strings,
                              SharedSessionContractImplementor sharedSessionContractImplementor,
                              Object o) throws HibernateException, SQLException {
        long id = Long.parseLong(resultSet.getString(strings[0]));
        String name = resultSet.getString(1);
        String surname = resultSet.getString(2);
        short born = Short.parseShort(resultSet.getString(3));
        short passed = Short.parseShort(resultSet.getString(4));

        return new Person.Builder()
                .setId(id)
                .setName(name)
                .setSurname(surname)
                .setYearBorn(born)
                .setYearPassed(passed)
                .build();
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement,
                            Object o, int i,
                            SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (Objects.isNull(o)) {
            preparedStatement.setNull(i, LongType.INSTANCE.sqlType());
        } else {
            Person person = (Person) o;
            preparedStatement.setLong(i, person.getId());
            preparedStatement.setString(i+1, person.getName());
            preparedStatement.setString(i+2, person.getSurname());
            preparedStatement.setShort(i+3, person.getYear_born());
            preparedStatement.setShort(i+4, person.getYear_passed());
        }
    }
}
