package com.dsa.HomeLibrarySystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a review for a bibliographic artifact in the library system.
 */
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String reviewer;

    private LocalDateTime reviewDate;

    @ManyToOne
    @JoinColumn(name = "artifact_id", nullable = false)
    private BibliographicArtifact bibliographicArtifact;

    // Default constructor
    public Review() {
        this.reviewDate = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public BibliographicArtifact getBibliographicArtifact() {
        return bibliographicArtifact;
    }

    public void setBibliographicArtifact(BibliographicArtifact bibliographicArtifact) {
        this.bibliographicArtifact = bibliographicArtifact;
    }
}
