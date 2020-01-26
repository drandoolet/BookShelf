package bookshelf.jpa.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "people")
public class Person implements Serializable {
    public Person() {
    }

    private Person(Builder builder) {
        name = builder.name;
        surname = builder.surname;
        year_born = builder.year_born;
        year_passed = builder.year_passed;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "year_born", nullable = true)
    private short year_born;

    @Column(name = "year_passed", nullable = true)
    private short year_passed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public short getYear_born() {
        return year_born;
    }

    public void setYear_born(short year_born) {
        this.year_born = year_born;
    }

    public short getYear_passed() {
        return year_passed;
    }

    public void setYear_passed(short year_passed) {
        this.year_passed = year_passed;
    }

    @Override
    public String toString() {
        return String.format("Person. ID:'%d', name:'%s %s', born:'%d', passed:'%d'",
                id, name, surname, year_born, year_passed);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Person)) return false;

        Person person = (Person) obj;
        return (name.equals(person.name) &&
                surname.equals(person.surname) &&
                year_born == person.year_born &&
                year_passed == person.year_passed);
    }

    @Override
    public int hashCode() {
        int h = 23;
        h = h + name.hashCode() + surname.hashCode() + year_born + year_passed;

        return h;
    }

    public static class Builder {
        private String name, surname;
        private short year_born = 0, year_passed = 0;
        private long id = 0;

        public Builder() {}

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSurname(String s) {
            surname = s;
            return this;
        }

        public Builder setYearBorn(short y) {
            year_born = y;
            return this;
        }

        public Builder setYearPassed(short y) {
            year_passed = y;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
