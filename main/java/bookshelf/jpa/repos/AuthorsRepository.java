package bookshelf.jpa.repos;

import bookshelf.jpa.entities.Author;
import bookshelf.jpa.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Long> {
    Optional<Author> findAuthorByPerson(Person person);
}
