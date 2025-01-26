package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.*;
import com.RulesTV.RulesTV.repositories.ReviewRepository;
import com.RulesTV.RulesTV.rest.DTO.EpisodeDTO;
import com.RulesTV.RulesTV.rest.DTO.ReviewDTO;
import com.RulesTV.RulesTV.rest.DTO.WatchHistoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/all")
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(review -> {
            ReviewDTO dto = new ReviewDTO(
                    review.getId(),
                    review.getReviewText(),
                    review.getMovie() != null ? review.getMovie().getId() : null,
                    review.getEpisode() != null ? review.getEpisode().getId() : null,
                    review.getProfile() != null ? review.getProfile().getId() : null,
                    review.getRating(),
                    review.getCreatedAt(),
                    review.getUpdatedAt()
            );

            dto.setReview_text(review.getReviewText());
            if (review.getMovie() != null) {
                dto.setMovie_id(review.getMovie().getId());
            }
            if (review.getEpisode() != null) {
                dto.setEpisode_id(review.getEpisode().getId());
            }
            if (review.getProfile() != null) {
                dto.setProfile_id(review.getProfile().getId());
            }
            dto.setRating(review.getRating());
            dto.setCreated_at(review.getCreatedAt());
            dto.setUpdate_at(review.getUpdatedAt());

            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable int id) {
        Review review = reviewRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Review with ID " + id + " not found"));

        ReviewDTO dto = new ReviewDTO(
                review.getId(),
                review.getReviewText(),
                review.getMovie() != null ? review.getMovie().getId() : null,
                review.getEpisode() != null ? review.getEpisode().getId() : null,
                review.getProfile() != null ? review.getProfile().getId() : null,
                review.getRating(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );

        dto.setReview_text(review.getReviewText());
        if (review.getMovie() != null) {
            dto.setMovie_id(review.getMovie().getId());
        }
        if (review.getEpisode() != null) {
            dto.setEpisode_id(review.getEpisode().getId());
        }
        if (review.getProfile() != null) {
            dto.setProfile_id(review.getProfile().getId());
        }
        dto.setRating(review.getRating());
        dto.setCreated_at(review.getCreatedAt());
        dto.setUpdate_at(review.getUpdatedAt());

        return ResponseEntity.ok(dto);
    }


    @PostMapping("/post")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review savedReview = reviewRepository.save(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);

    }

    @PutMapping("/put/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable int id, @RequestBody Review updatedReview) {
        return reviewRepository.findById(id)
                .map(existingReview -> {
                    // Update fields if the new values are provided
                    if (updatedReview.getCreatedAt() != null) {
                        existingReview.setCreatedAt(updatedReview.getCreatedAt());
                    }
                    if (updatedReview.getUpdatedAt() != null) {
                        existingReview.setUpdatedAt(updatedReview.getUpdatedAt());
                    }
                    if (updatedReview.getReviewText() != null) {
                        existingReview.setReviewText(updatedReview.getReviewText());
                    }
                    if (updatedReview.getProfile() != null) {
                        existingReview.setProfile(updatedReview.getProfile());
                    }
                    if (updatedReview.getMovie() != null) {
                        existingReview.setMovie(updatedReview.getMovie());
                    }
                    if (updatedReview.getEpisode() != null) {
                        existingReview.setEpisode(updatedReview.getEpisode());
                    }
                    if (updatedReview.getRating() != null) { // Check if rating is updated
                        existingReview.setRating(updatedReview.getRating());
                    }

                    // Save the updated review to the database
                    reviewRepository.save(existingReview);

                    // Create a DTO to return as the response
                    ReviewDTO dto = new ReviewDTO(
                            existingReview.getId(),
                            existingReview.getReviewText(),
                            existingReview.getMovie() != null ? existingReview.getMovie().getId() : null,
                            existingReview.getEpisode() != null ? existingReview.getEpisode().getId() : null,
                            existingReview.getProfile() != null ? existingReview.getProfile().getId() : null,
                            existingReview.getRating(),
                            existingReview.getCreatedAt(),
                            existingReview.getUpdatedAt()
                    );

                    return ResponseEntity.ok(dto);
                })
                .orElseThrow(() -> new RuntimeException("Review with ID " + id + " not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable int id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return ResponseEntity.ok("The Review with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Review with ID " + id + " not found.");
        }
    }


    /*
    *
    *

    *
    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO(
                review.getReviewText(),
                review.getMovie() != null ? review.getMovie().getId() : null,
                review.getEpisode() != null ? review.getEpisode().getId() : null,
                review.getProfile().getId(),
                review.getRating(),
                review.getCreatedAt(),
                review.getUpdatedAt());

        dto.setReview_text(review.getReviewText());
        dto.setProfile_id(review.getProfile().getId());
        dto.setRating(review.getRating());
        dto.setCreated_at(review.getCreatedAt());
        dto.setUpdate_at(review.getUpdatedAt());

        if (review.getMovie() != null) {
            dto.setMovie_id(review.getMovie().getId());
        }
        if (review.getEpisode() != null) {
            dto.setEpisode_id(review.getEpisode().getId());
        }

        return dto;
    }
    * */

}


