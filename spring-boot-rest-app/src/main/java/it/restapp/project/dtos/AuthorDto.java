package it.restapp.project.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AuthorDto extends RepresentationModel<AuthorDto> {
    private Long id;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String nationality;
    private List<BookDto> books = new ArrayList<>();
    private List<ReviewDto> reviews = new ArrayList<>();
}
