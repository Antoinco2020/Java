package it.restapp.project.repository;

import it.restapp.project.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly=true)
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
