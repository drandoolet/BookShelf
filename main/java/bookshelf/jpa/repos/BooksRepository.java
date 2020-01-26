package bookshelf.jpa.repos;

import bookshelf.jpa.entities.Author;
import bookshelf.jpa.entities.Book;
import bookshelf.jpa.entities.BookOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
    //List<Book> findBooksByAuthorId(Long id);

    //List<Book> findBooksByOwnerId(Long id);

    List<Book> findBooksByOwner(BookOwner owner);

    List<Book> findBooksByAuthor(Author author);

    List<Book> findBooksByName(String name);
}
