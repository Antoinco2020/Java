package it.restapp.project.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDto {
    private LocalDateTime date;
    private int vote;
    private String description;
}
