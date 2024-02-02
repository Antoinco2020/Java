package it.restapp.project.dao;

import it.restapp.project.entities.Book;

import java.util.List;

public interface BookDao {
    Book getByIsbn(String isbn);
    List<Book> getAll();
    List<Book> getByTitle(String title);
    List<Book> getByClassification(String classification);
    long countBooks();
    Book insert(Book book);
    Book update(Book book);
    void delete(Book book);
}
