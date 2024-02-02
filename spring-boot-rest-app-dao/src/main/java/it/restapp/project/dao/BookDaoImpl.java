package it.restapp.project.dao;

import it.restapp.project.entities.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl extends AbstractDao<Book, String> implements BookDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookDaoImpl.class);
    @Override
    public Book getByIsbn(String isbn) {
        Book book = null;
        try {
            LOGGER.info("Get book by isbn {}", isbn);
            book = super.getByParam("isbn", isbn).get(0);
            LOGGER.info("Book retrieved {}", book);
        }
        catch (Exception e){
            LOGGER.error("Error get book by isbn. Details: ", e);
            throw e;
        }
        return book;
    }

    @Override
    public List<Book> getByTitle(String title) {
        List<Book> books = null;
        try {
            LOGGER.info("Get books by title {}", title);
            books = super.getByParam("title", title);
            LOGGER.info("Books retrieved {}", books);
        }
        catch (Exception e){
            LOGGER.error("Error get books by title. Details: ", e);
            throw e;
        }
        return books;
    }

    @Override
    public List<Book> getByClassification(String classification) {
        List<Book> books = null;
        try {
            LOGGER.info("Get books by classification {}", classification);
            books = super.getByParam("classification", classification);
            LOGGER.info("Books retrieved {}", books);
        }
        catch (Exception e){
            LOGGER.error("Error get books by classification. Details: ", e);
            throw e;
        }
        return books;
    }

    @Override
    public long countBooks() {
        long result = 0;
        try {
            LOGGER.info("Get count of books");
            String JPQL = "SELECT COUNT(b.ISBN) FROM BOOK b";
            result = (long) entityManager.createQuery(JPQL).getSingleResult();
            LOGGER.info("Total books {}", result);
        }
        catch (Exception e){
            LOGGER.error("Error count books. Details: ", e);
            throw e;
        }
        return result;
    }
}
