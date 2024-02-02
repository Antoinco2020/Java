package it.restapp.project.service;

import it.restapp.project.dtos.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAuthorByName(String name);

    List<AuthorDto> getAllAuthors();
    AuthorDto insertAuthor(AuthorDto authorDto);
}
