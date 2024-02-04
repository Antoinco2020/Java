package it.restapp.project.service;

import it.restapp.project.dtos.ReviewDto;
import it.restapp.project.entities.Review;
import it.restapp.project.event.ReviewInsertedEvent;
import it.restapp.project.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public ReviewDto insertReview(ReviewDto reviewDto) {
        try {
            LOGGER.info("Insert review {}", reviewDto);
            Review review = modelMapper.map(reviewDto, Review.class);
            review = reviewRepository.saveAndFlush(review);
            LOGGER.info("Review inserted, the id is {}", review.getId());
            reviewDto = modelMapper.map(review, ReviewDto.class);
            applicationEventPublisher.publishEvent(new ReviewInsertedEvent(review));
        }
        catch (Exception e){
            LOGGER.error("Error insertReview. Details: ", e);
            throw e;
        }
        return reviewDto;
    }

    @Override
    public ReviewDto getReviewById(Long id) {
        ReviewDto reviewDto = null;
        try {
            LOGGER.info("Retrieve review from id: {}", id);
            Optional<Review> review = reviewRepository.findById(id);
            if(review.isEmpty()){
                throw new NullPointerException(String.format("Review not present for id %s", id));
            }
            LOGGER.info("Review founded {}", review);
            reviewDto = modelMapper.map(review.get(), ReviewDto.class);
        }
        catch (Exception e){
            LOGGER.error("Error getReviewById. Details: ", e);
            throw e;
        }
        return reviewDto;
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        List<ReviewDto> reviewDtos = null;
        try {
            LOGGER.info("Retrieve all reviews");
            List<Review> authors = reviewRepository.findAll();
            LOGGER.info("Reviews founded {}", authors.size());
            reviewDtos = authors
                    .stream()
                    .map(source -> modelMapper.map(source, ReviewDto.class))
                    .toList();
        }
        catch (Exception e){
            LOGGER.error("Error getAllReviews. Details: ", e);
            throw e;
        }
        return reviewDtos;
    }
}
