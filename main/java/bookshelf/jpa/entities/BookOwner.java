package bookshelf.jpa.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "book_owners")
public class BookOwner implements Serializable {
    public BookOwner() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Type(type = "bookshelf.jpa.type.PersonType")
    @Column(name = "person")
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return String.format("BookOwner. ID:'%d', person: '%s'",
                id, person.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof BookOwner)) return false;

        BookOwner owner = (BookOwner) obj;
        return this.person.equals(owner.person);
    }

    @Override
    public int hashCode() {
        int h = 23;
        if (person != null) h += person.hashCode();
        return h;
    }
}
