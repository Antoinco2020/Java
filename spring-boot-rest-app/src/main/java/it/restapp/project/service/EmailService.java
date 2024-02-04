package it.restapp.project.service;

import it.restapp.project.entities.Review;

public interface EmailService {
    void sendReviewEmail(Review review) throws Exception ;
}
