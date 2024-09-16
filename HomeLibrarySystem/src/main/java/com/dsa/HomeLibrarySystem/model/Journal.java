package com.dsa.HomeLibrarySystem.model;

import jakarta.persistence.*;

/**
 * Represents a journal in the library system.
 */
@Entity
public class Journal extends BibliographicArtifact {
    private String ISSN;

    private int volume;

    private int issue;

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

    // Abstract method.

    @Override
    public boolean checkAvailability() {
        return getAvailableCopies() > 0;
    }

}
