package it.restapp.project.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDto extends RepresentationModel<ReviewDto> {
    private Long id;
    private LocalDateTime date;
    private int vote;
    private String description;
}
