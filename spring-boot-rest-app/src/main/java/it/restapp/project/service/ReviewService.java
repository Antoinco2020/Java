package it.restapp.project.service;

import it.restapp.project.dtos.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto insertReview(ReviewDto review);
    ReviewDto getReviewById(Long id);
    List<ReviewDto> getAllReviews();
}
