package it.restapp.project.service;

import it.restapp.project.dao.AuthorDao;
import it.restapp.project.dtos.AuthorDto;
import it.restapp.project.entities.Author;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorServiceImpl.class);
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthorDao authorDao;

    @Override
    public List<AuthorDto> getAuthorByName(String name) {
        List<AuthorDto> authorDtos = null;
        try {
            LOGGER.info("Retrieve authors from name: {}", name);
            List<Author> authors = authorDao.getByName(name);
            LOGGER.info("Authors founded {}", authors.size());
            authorDtos = authors
                    .stream()
                    .map(source -> modelMapper.map(source, AuthorDto.class))
                    .toList();
        }
        catch (Exception e){
            LOGGER.error("Error getAuthorByName. Details: ", e);
            throw e;
        }
        return authorDtos;
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<AuthorDto> authorDtos = null;
        try {
            LOGGER.info("Retrieve all authors");
            List<Author> authors = authorDao.getAll();
            LOGGER.info("Authors founded {}", authors.size());
            authorDtos = authors
                    .stream()
                    .map(source -> modelMapper.map(source, AuthorDto.class))
                    .toList();
        }
        catch (Exception e){
            LOGGER.error("Error getAllAuthors. Details: ", e);
            throw e;
        }
        return authorDtos;
    }

    @Override
    public AuthorDto insertAuthor(AuthorDto authorDto) {
        try {
            LOGGER.info("Insert author {}", authorDto);
            Author author = modelMapper.map(authorDto, Author.class);
            author = authorDao.insert(author);
            LOGGER.info("Author inserted, the id is {}", author.getId());
            authorDto = modelMapper.map(author, AuthorDto.class);
        }
        catch (Exception e){
            LOGGER.error("Error insertAuthor. Details: ", e);
            throw e;
        }
        return authorDto;
    }
}
