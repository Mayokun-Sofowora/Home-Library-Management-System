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
    private String ISBN;

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
    public String getISBN() {
        return ISBN;
    }

    /**
     * Sets the International Standard Book Number (ISBN) of the book.
     * @param ISBN The ISBN to be set.
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    // Abstract method.

    @Override
    public boolean checkAvailability() {
        return getAvailableCopies() > 0;
    }

}
