package bookshelf.jpa.entities;

import bookshelf.jpa.type.AuthorType;
import bookshelf.jpa.type.BookOwnerType;
import com.google.common.base.Objects;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "books")
@TypeDef(name = "owner", typeClass = BookOwner.class, defaultForType = BookOwnerType.class)
@TypeDef(name = "author", typeClass = Author.class, defaultForType = AuthorType.class)
public class Book implements Serializable {
    public Book() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //@Type(type = "bookshelf.jpa.type.BookOwnerType")
    @Column(name = "owner", nullable = true)
    private BookOwner owner;

    //@Type(type = "bookshelf.jpa.type.AuthorType")
    @Column(name = "author", nullable = false)
    private Author author;

    @Column(name = "year", nullable = true)
    private Short year;

    @Column(name = "name", nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookOwner getOwner() {
        return owner;
    }

    public void setOwner(BookOwner owner) {
        this.owner = owner;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Book. ID:'%d', name:'%s', author:'%s', year:'%d', owner:'%s'",
                id, name, author.toString(), year, owner.toString());
    }
/*
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Book)) return false;

        Book book = (Book) obj;
        return name.equals(book.name) &&
                author.equals(book.author) &&
                year.equals(book.year) &&
                owner.equals(book.owner);
    }

    @Override
    public int hashCode() {
        int h = 23;
        h = h + name.hashCode() + author.hashCode();

        if (year != null) h += year.hashCode();
        if (owner != null) h += owner.hashCode();

        return h;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equal(id, book.id) &&
                Objects.equal(owner, book.owner) &&
                Objects.equal(author, book.author) &&
                Objects.equal(year, book.year) &&
                Objects.equal(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, owner, author, year, name);
    }

    private Book(Builder builder) {
        author = builder.author;
        owner = builder.owner;
        year = builder.year;
        name = builder.name;
    }

    public static class Builder {
        private String name;
        private Author author;
        private BookOwner owner = null;
        private short year = 0;

        public Builder() {}

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAuthor(Author author) {
            this.author = author;
            return this;
        }

        public Builder setOwner(BookOwner bookOwner) {
            this.owner = bookOwner;
            return this;
        }

        public Builder setYear(short year) {
            this.year = year;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}
