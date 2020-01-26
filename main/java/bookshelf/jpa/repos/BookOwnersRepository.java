package bookshelf.jpa.repos;

import bookshelf.jpa.entities.BookOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookOwnersRepository extends JpaRepository<BookOwner, Long> {
}
