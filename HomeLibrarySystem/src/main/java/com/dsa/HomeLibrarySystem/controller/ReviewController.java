package com.dsa.HomeLibrarySystem.controller;

import com.dsa.HomeLibrarySystem.model.Book;
import com.dsa.HomeLibrarySystem.model.Review;
import com.dsa.HomeLibrarySystem.service.BookService;
import com.dsa.HomeLibrarySystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final BookService bookService;

    @Autowired
    public ReviewController(ReviewService reviewService, BookService bookService) {
        this.reviewService = reviewService;
        this.bookService = bookService;
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

    @PostMapping
    public ResponseEntity<Review> createReview(
            @RequestParam Long bookId,
            @RequestParam String reviewText) {
        System.out.println("Creating review for bookId: " + bookId + " with reviewText: " + reviewText);

        Book book = bookService.getBookById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Review review = new Review();
        review.setContent(reviewText);
        review.setBibliographicArtifact(book); // Associate the review with the book
        review.setReviewDate(LocalDateTime.now()); // Set the current date/time

        Review savedReview = reviewService.saveOrUpdateReview(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        Optional<Review> existingReview = reviewService.getReviewById(id);
        if (existingReview.isPresent()) {
            review.setReviewId(id); // Set the existing ID
            Review updatedReview = reviewService.saveOrUpdateReview(review);
            return new ResponseEntity<>(updatedReview, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitReview(
            @RequestParam Long bookId,
            @RequestParam String reviewText) {

        try {
            Book book = bookService.getBookById(bookId)
                    .orElseThrow(() -> new IllegalArgumentException("Book not found"));

            Review review = new Review();
            review.setContent(reviewText);
            review.setBibliographicArtifact(book);

            reviewService.saveOrUpdateReview(review);

            return new ResponseEntity<>("Review submitted successfully.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error submitting review. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
