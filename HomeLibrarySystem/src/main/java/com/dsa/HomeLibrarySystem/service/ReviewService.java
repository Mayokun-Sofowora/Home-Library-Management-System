package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.BibliographicArtifact;
import com.dsa.HomeLibrarySystem.model.Review;
import com.dsa.HomeLibrarySystem.repository.BibliographicArtifactRepository;
import com.dsa.HomeLibrarySystem.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BibliographicArtifactRepository artifactRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BibliographicArtifactRepository artifactRepository) {
        this.reviewRepository = reviewRepository;
        this.artifactRepository = artifactRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Review saveOrUpdateReview(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }

    public Review createReview(Long bookId, String content, String reviewer) {
        BibliographicArtifact book = artifactRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));

        Review review = new Review();
        review.setContent(content);
        review.setReviewer(reviewer);
        review.setReviewDate(LocalDate.now());
        review.setArtifact(book);

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsForBook(Long bookId) {
        return reviewRepository.findByArtifactId(bookId);
    }
}
