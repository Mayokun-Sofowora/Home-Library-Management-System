package com.dsa.HomeLibrarySystem.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a review for a bibliographic artifact in the library system.
 */
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(nullable = false)
    private String reviewer;

    private LocalDate reviewDate;

    @ManyToOne
    private BibliographicArtifact artifact;

    // Default constructor
    public Review() {
        this.reviewDate = LocalDate.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long reviewId) {
        this.id = reviewId;
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

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public BibliographicArtifact getArtifact() {
        return artifact;
    }

    public void setArtifact(BibliographicArtifact bibliographicArtifact) {
        this.artifact = bibliographicArtifact;
    }
}
