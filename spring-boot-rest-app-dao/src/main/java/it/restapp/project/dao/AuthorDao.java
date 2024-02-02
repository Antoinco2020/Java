package it.restapp.project.dao;

import it.restapp.project.entities.Author;

import java.time.LocalDate;
import java.util.List;

public interface AuthorDao {
    Author getById(Long id);
    List<Author> getAll();
    List<Author> getByName(String name);
    List<Author> getByNationality(String nationality);
    List<Author> getByDateOfBirth(LocalDate date);
    long countAuthors();
    Author insert(Author author);
    Author update(Author author);
    void delete(Author author);
}
