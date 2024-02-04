package it.restapp.project.event;

import it.restapp.project.entities.Review;
import lombok.Data;

@Data
public class ReviewInsertedEvent {
    private final Review review;
}
