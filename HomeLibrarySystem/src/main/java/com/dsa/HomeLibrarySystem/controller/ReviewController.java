package com.dsa.HomeLibrarySystem.controller;

import com.dsa.HomeLibrarySystem.model.*;
import com.dsa.HomeLibrarySystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    @Autowired
    public ReviewController(ReviewService reviewService, BookService bookService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        Optional<Review> existingReview = reviewService.getReviewById(id);
        if (existingReview.isPresent()) {
            review.setId(id); // Set the existing ID
            Review updatedReview = reviewService.saveOrUpdateReview(review);
            return new ResponseEntity<>(updatedReview, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitReview(@RequestParam Long bookId,
                                               @RequestParam String reviewText,
                                               Principal principal) {
        String username = principal != null ? principal.getName() : "Anonymous";
        reviewService.createReview(bookId, reviewText, username);
        return ResponseEntity.ok("Review submitted successfully");
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Review>> getBookReviews(@PathVariable Long bookId) {
        List<Review> reviews = reviewService.getReviewsForBook(bookId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
