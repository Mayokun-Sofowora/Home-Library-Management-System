package com.dsa.HomeLibrarySystem.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a book in the library system.
 */
@Entity
public class Book extends BibliographicArtifact {
    @Column(unique = true, nullable = false)
    private Long ISBN;
    private int totalCopies;
    private int availableCopies;

    /**
     * Default constructor.
     */
    public Book() {
    }

    // Getters and Setters

    /**
     * Retrieves the International Standard Book Number (ISBN) of the book.
     * @return The ISBN of the book.
     */
    public Long getISBN() {
        return ISBN;
    }

    /**
     * Sets the International Standard Book Number (ISBN) of the book.
     * @param ISBN The ISBN to be set.
     */
    public void setISBN(Long ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * Retrieves the total number of copies of the book.
     * @return The total number of copies.
     */
    public int getTotalCopies() {
        return totalCopies;
    }

    /**
     * Sets the total number of copies of the book.
     * @param totalCopies The total number of copies to be set.
     */
    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    /**
     * Retrieves the number of available copies of the book.
     * @return The number of available copies.
     */
    public int getAvailableCopies() {
        return availableCopies;
    }

    /**
     * Sets the number of available copies of the book.
     * @param availableCopies The number of available copies to be set.
     */
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    /**
     * Checks the availability of the book based on the number of available copies.
     * @return True if there are available copies, false otherwise.
     */
    @Override
    public boolean checkAvailability() {
         return availableCopies > 0;
    }
}
