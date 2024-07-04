package com.dsa.homelibrary.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 * Represents a book in the library system.
 */
@Entity
//@Table(name = "Book")
//@DiscriminatorValue("BOOK")
public class Book extends BibliographicArtifact {
    @Column(unique = true, nullable = false)
    private String ISBN;

    private int totalCopies;
    private int availableCopies;

    @ElementCollection
    private List<String> reviews;

    /**
     * Default constructor.
     */
    public Book() {
    }

    // Getters and Setters...
    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean checkAvailability() {
        return availableCopies > 0;
    }
}
