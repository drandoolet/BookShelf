package bookshelf;

import bookshelf.jpa.entities.Author;
import bookshelf.jpa.entities.Book;
import bookshelf.jpa.entities.BookOwner;
import bookshelf.jpa.entities.Person;
import bookshelf.jpa.service.BookShelfService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootApplication
@EnableJpaRepositories
public class Application {
    private static ApplicationContext context;

    public static void main(String[] args) {

        context = SpringApplication.run(Application.class, args);

        Person person = new Person();
        person.setName("Joshua");
        person.setSurname("Bloch");
        person.setYear_born(Integer.valueOf(1961).shortValue());

        Person artem = new Person();
        artem.setName("Artem");
        artem.setSurname("Umansky");
        artem.setYear_born(Integer.valueOf(1995).shortValue());

        BookOwner owner = new BookOwner();
        owner.setPerson(artem);

        Author author = new Author();
        author.setPerson(person);

        Book book = new Book.Builder()
                .setAuthor(author)
                .setName("Effective Java")
                .setYear(Integer.valueOf(2018).shortValue())
                .setOwner(owner)
                .build();

        context.getBean(BookShelfService.class).saveAll(List.of(person, artem, owner, author, book));

        findBlochBooks(context);
    }

    private static void findBlochBooks(ApplicationContext context) {
        BookShelfService service = context.getBean(BookShelfService.class);
        List<Book> books = service.findBooksByAuthorNameAndSurname("Joshua", "Bloch");

        System.out.println(books.toString());
    }

    private static void insertBook(Book book, ApplicationContext context) {
        BookShelfService service = context.getBean(BookShelfService.class);
        service.saveBook(book);
    }
}
