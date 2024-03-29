package it.restapp.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.restapp.project.dtos.AuthorDto;
import it.restapp.project.dtos.ReviewDto;
import it.restapp.project.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);
    @Autowired
    private ReviewService reviewService;

    @Operation(summary = "Insert review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Review created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorDto.class)) }),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content)
    })
    @PostMapping(value = "insert", produces = "application/json")
    public ResponseEntity<ReviewDto> insertReview(@RequestBody ReviewDto review) {
        try {
            LOGGER.info("Start call insertAuthor");
            review = reviewService.insertReview(review);
            if (review.getId() == null || review.getId() == 0)
                throw new NullPointerException("Review not inserted");

            review.add(linkTo(methodOn(ReviewController.class)
                    .insertReview(review)).withSelfRel());
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        }
        catch (Exception e){
            LOGGER.error("Error insertAuthor. Details: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
