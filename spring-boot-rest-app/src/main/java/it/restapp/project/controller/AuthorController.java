package it.restapp.project.controller;

import it.restapp.project.dtos.AuthorDto;
import it.restapp.project.service.AuthorService;
import it.restapp.project.service.AuthorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/author")
public class AuthorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);
    @Autowired
    private AuthorService authorService;

    @GetMapping(value = "all", produces = "application/json")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        try {
            LOGGER.info("Start call getAllAuthors");
            List<AuthorDto> authorDtos = authorService.getAllAuthors();
            if (authorDtos.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            LOGGER.info("Return authors, total: {}", authorDtos.size());

            return new ResponseEntity<>(authorDtos, HttpStatus.OK);
        }
        catch (Exception e){
            LOGGER.error("Error getAllAuthors. Details: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "get", produces = "application/json")
    public ResponseEntity<List<AuthorDto>> getAuthorByName(@RequestParam String name) {
        try {
            LOGGER.info("Start call getAuthorByName");
            List<AuthorDto> authorDtos = authorService.getAuthorByName(name);
            if (authorDtos.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            LOGGER.info("Return authors, total: {}", authorDtos.size());

            return new ResponseEntity<>(authorDtos, HttpStatus.OK);
        }
        catch (Exception e){
            LOGGER.error("Error getAuthorByName. Details: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "insert", produces = "application/json")
    public ResponseEntity<AuthorDto> insertAuthor(@RequestBody AuthorDto author) {
        try {
            LOGGER.info("Start call insertAuthor");
            author = authorService.insertAuthor(author);
            if (author.getId() == null || author.getId() == 0)
                throw new NullPointerException("Author not inserted");

            return new ResponseEntity<>(author, HttpStatus.CREATED);
        }
        catch (Exception e){
            LOGGER.error("Error insertAuthor. Details: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
