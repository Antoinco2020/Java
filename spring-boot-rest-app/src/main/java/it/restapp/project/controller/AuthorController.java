package it.restapp.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.restapp.project.dtos.AuthorDto;
import it.restapp.project.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/author")
public class AuthorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);
    @Autowired
    private AuthorService authorService;

    @Operation(summary = "Get All Authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Authors List",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuthorDto.class)) }),
            @ApiResponse(responseCode = "204",
                    description = "No authors found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content)
    })
    @GetMapping(value = "all", produces = "application/json")
    public ResponseEntity<CollectionModel<AuthorDto>> getAllAuthors() {
        try {
            LOGGER.info("Start call getAllAuthors");
            List<AuthorDto> authorDtos = authorService.getAllAuthors();
            if (authorDtos.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            LOGGER.info("Return authors, total: {}", authorDtos.size());
            Link link = linkTo(methodOn(AuthorController.class)
                    .getAllAuthors()).withSelfRel();
            return new ResponseEntity<>(CollectionModel.of(authorDtos, link), HttpStatus.OK);
        }
        catch (Exception e){
            LOGGER.error("Error getAllAuthors. Details: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Author by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Authors List",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuthorDto.class)) }),
            @ApiResponse(responseCode = "204",
                    description = "No authors found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content)
    })
    @GetMapping(value = "get", produces = "application/json")
    public ResponseEntity<CollectionModel<AuthorDto>> getAuthorByName(@RequestParam String name) {
        try {
            LOGGER.info("Start call getAuthorByName");
            List<AuthorDto> authorDtos = authorService.getAuthorByName(name);
            if (authorDtos.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            LOGGER.info("Return authors, total: {}", authorDtos.size());
            Link link = linkTo(methodOn(AuthorController.class)
                    .getAuthorByName(name)).withSelfRel();
            return new ResponseEntity<>(CollectionModel.of(authorDtos, link), HttpStatus.OK);
        }
        catch (Exception e){
            LOGGER.error("Error getAuthorByName. Details: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Insert an author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Author created",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuthorDto.class)) }),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content)
    })
    @PostMapping(value = "insert", produces = "application/json")
    public ResponseEntity<AuthorDto> insertAuthor(@RequestBody AuthorDto author) {
        try {
            LOGGER.info("Start call insertAuthor");
            author = authorService.insertAuthor(author);
            if (author.getId() == null || author.getId() == 0)
                throw new NullPointerException("Author not inserted");

            author.add(linkTo(methodOn(AuthorController.class)
                    .insertAuthor(author)).withSelfRel());
            return new ResponseEntity<>(author, HttpStatus.CREATED);
        }
        catch (Exception e){
            LOGGER.error("Error insertAuthor. Details: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
