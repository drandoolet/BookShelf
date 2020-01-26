package bookshelf.jpa.service;

import bookshelf.jpa.entities.Author;
import bookshelf.jpa.entities.Book;
import bookshelf.jpa.entities.BookOwner;
import bookshelf.jpa.entities.Person;
import bookshelf.jpa.repos.AuthorsRepository;
import bookshelf.jpa.repos.BookOwnersRepository;
import bookshelf.jpa.repos.BooksRepository;
import bookshelf.jpa.repos.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookShelfService {
    private BooksRepository booksRepository;
    private AuthorsRepository authorsRepository;
    private BookOwnersRepository bookOwnersRepository;
    private PeopleRepository peopleRepository;

    @Autowired
    public BookShelfService(BooksRepository booksRepository,
                            AuthorsRepository authorsRepository,
                            BookOwnersRepository bookOwnersRepository,
                            PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.authorsRepository = authorsRepository;
        this.bookOwnersRepository = bookOwnersRepository;
        this.peopleRepository = peopleRepository;
    }

    public BookShelfService() {
    }

    public List<Book> findBooksByAuthor(Author author) {
        return new ArrayList<>(booksRepository.findBooksByAuthor(author));
    }

    public List<Book> findBooksByOwner(BookOwner owner) {
        return new ArrayList<>(booksRepository.findBooksByOwner(owner));
    }

    public List<Author> findPossibleAuthorsByBookName(String bookName) {
        return booksRepository
                .findBooksByName(bookName)
                .stream()
                .map(book -> authorsRepository.findById(book.getAuthor().getId()).get())
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByAuthorNameAndSurname(String name, String surname) {
        Optional<Person> person = peopleRepository.findPersonByNameAndSurname(name, surname);

        if (!person.isPresent())
            throw new IllegalArgumentException("No Person found with name: " + name + ", surname: " + surname);

        Optional<Author> author = authorsRepository.findAuthorByPerson(person.get());

        if (!author.isPresent())
            throw new IllegalArgumentException("No Author found with name: " + name + ", surname: " + surname);

        return booksRepository.findBooksByAuthor(author.get());
    }

    public Optional<Person> findPersonById(long id) {
        return peopleRepository.findById(id);
    }

    public Optional<Author> findAuthorById(long id) {
        return authorsRepository.findById(id);
    }

    public void saveBook(Book book) {
        booksRepository.save(book);
    }

    public void saveBookOwner(BookOwner owner) {
        bookOwnersRepository.save(owner);
    }

    public void saveAuthor(Author author) {
        authorsRepository.save(author);
    }

    public void savePerson(Person person) {
        peopleRepository.save(person);
    }

    public void save(Object o) {
        if (o instanceof Author) {
            saveAuthor((Author) o);
            return;
        }
        if (o instanceof BookOwner) {
            saveBookOwner((BookOwner) o);
            return;
        }
        if (o instanceof Person) {
            savePerson((Person) o);
            return;
        }
        if (o instanceof Book) {
            saveBook((Book) o);
            return;
        }
        throw new IllegalArgumentException("No possible instances found, cannot be saved.");
    }

    public void saveAll(List<Object> list) {
        list.forEach(this::save);
    }
}
