package it.restapp.project.repository;

import it.restapp.project.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly=true)
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String Isbn);
}
