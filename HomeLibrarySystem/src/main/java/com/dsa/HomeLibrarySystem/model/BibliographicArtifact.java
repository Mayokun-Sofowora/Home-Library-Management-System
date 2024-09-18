package com.dsa.HomeLibrarySystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.*;

/**
 * Represents a bibliographic artifact in the library system.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BibliographicArtifact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "artifact_author", joinColumns = @JoinColumn(name = "artifact_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors = new ArrayList<>();

    @Version
    private int availableCopies;

    private int totalCopies;

    @OneToMany(mappedBy = "artifact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "artifact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loans;

    private boolean isCensored;

    private String language;

    @Column(name = "publication_year")
    private String year;

    @JsonBackReference
    @JsonIgnore
    @ManyToOne
    private BookshelfLocation location;

    @Column(nullable = false)
    private String type;

    /**
     * Default constructor.
     */
    public BibliographicArtifact() {
    }

    // Abstract method to be implemented by subclasses

    /**
     * Checks the availability of the bibliographic artifact.
     *
     * @return True if the artifact is available, false otherwise.
     */
    public abstract boolean checkAvailability();

    // Getters and Setters for BibliographicArtifact

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCensored() {
        return isCensored;
    }

    public void setCensored(boolean censored) {
        this.isCensored = censored;
    }

    public boolean canBeLoaned() {
        return checkAvailability();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        if (totalCopies < 0) {
            throw new IllegalArgumentException("Total copies cannot be negative");
        }
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        if (availableCopies < 0) {
            throw new IllegalArgumentException("Available copies cannot be negative");
        }
        this.availableCopies = availableCopies;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setArtifact(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setArtifact(null);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public BookshelfLocation getLocation() {
        return location;
    }

    public void setLocation(BookshelfLocation location) {
        this.location = location;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}