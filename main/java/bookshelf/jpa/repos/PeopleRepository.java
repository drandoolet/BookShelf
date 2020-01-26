package bookshelf.jpa.repos;

import bookshelf.jpa.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {
    Optional<Person> findPersonByNameAndSurname(String name, String surname);
}
