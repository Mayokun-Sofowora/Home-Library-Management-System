package com.dsa.HomeLibrarySystem.model;

import jakarta.persistence.*;

/**
 * Represents a journal in the library system.
 */
@Entity
public class Journal extends BibliographicArtifact {
    private int journalId;
    private String ISSN;
    private int volume;
    private int issue;
    private int availableCopies;

    /**
     * Default constructor.
     */
    public Journal() {
    }

    // Getters and Setters

    /**
     * Retrieves the International Standard Serial Number (ISSN) of the journal.
     * @return The ISSN of the journal.
     */
    public String getISSN() {
        return ISSN;
    }

    /**
     * Sets the International Standard Serial Number (ISSN) of the journal.
     * @param ISSN The ISSN to be set.
     */
    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    /**
     * Retrieves the volume number of the journal.
     * @return The volume number.
     */
    public int getVolume() {
        return volume;
    }

    /**
     * Sets the volume number of the journal.
     * @param volume The volume number to be set.
     */
    public void setVolume(int volume) {
        this.volume = volume;
    }

    /**
     * Retrieves the issue number of the journal.
     * @return The issue number.
     */
    public int getIssue() {
        return issue;
    }

    /**
     * Sets the issue number of the journal.
     * @param issue The issue number to be set.
     */
    public void setIssue(int issue) {
        this.issue = issue;
    }

    /**
     * Retrieves the number of available copies of the journal.
     * @return The number of available copies.
     */
    public int getAvailableCopies() {
        return availableCopies;
    }

    /**
     * Sets the number of available copies of the journal.
     * @param availableCopies The number of available copies to be set.
     */
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    /**
     * Checks the availability of the journal based on the number of available copies.
     * @return True if there are available copies, false otherwise.
     */
    @Override
    public boolean checkAvailability() {
       return availableCopies > 0; 
    }
}
