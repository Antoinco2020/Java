package it.restapp.project.dao;

import it.restapp.project.entities.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class AuthorDaoImpl extends AbstractDao<Author, Long> implements AuthorDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorDaoImpl.class);
    @Override
    public Author getById(Long id) {
        Author author = null;
        try {
            LOGGER.info("Get Author by id {}", id);
            author = super.getById(id);
            LOGGER.info("Author retrieved {}", author);
        }
        catch (Exception e){
            LOGGER.error("Error get author by id. Details: ", e);
            throw e;
        }
        return author;
    }
    @Override
    public List<Author> getByName(String name) {
        List<Author> authors = null;
        try {
            LOGGER.info("Get Authors by name {}", name);
            authors = super.getByParam("name", name);
            LOGGER.info("Authors retrieved {}", authors);
        }
        catch (Exception e){
            LOGGER.error("Error get authors by name. Details: ", e);
            throw e;
        }
        return authors;
    }
    @Override
    public List<Author> getByNationality(String nationality) {
        List<Author> authors = null;
        try {
            LOGGER.info("Get Authors by nationality {}", nationality);
            authors = super.getByParam("nationality", nationality);
            LOGGER.info("Authors retrieved {}", authors);
        }
        catch (Exception e){
            LOGGER.error("Error get authors by nationality. Details: ", e);
            throw e;
        }
        return authors;
    }
    @Override
    public List<Author> getByDateOfBirth(LocalDate date) {
        List<Author> authors = null;
        try {
            LOGGER.info("Get Authors by date of birth {}", date);
            authors = super.getByParam("dateOfBirth", date);
            LOGGER.info("Authors retrieved {}", authors);
        }
        catch (Exception e){
            LOGGER.error("Error get authors by date of birth. Details: ", e);
            throw e;
        }
        return authors;
    }
    @Override
    public long countAuthors() {
        long result = 0;
        try {
            LOGGER.info("Get count of authors");
            String JPQL = "SELECT COUNT(a.ID) FROM AUTHOR a";
            result = (long) entityManager.createQuery(JPQL).getSingleResult();
            LOGGER.info("Total authors {}", result);
        }
        catch (Exception e){
            LOGGER.error("Error count authors. Details: ", e);
            throw e;
        }
        return result;
    }
}
