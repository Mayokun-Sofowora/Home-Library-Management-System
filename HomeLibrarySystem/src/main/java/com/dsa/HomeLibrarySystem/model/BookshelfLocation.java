package com.dsa.HomeLibrarySystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

/**
 * Represents a bookshelf location in the library system.
 */
@Entity
public class BookshelfLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    private Long shelfNumber;
    @JsonIgnore
    private String section;
    @JsonIgnore
    private String position;

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "location")
    private List<BibliographicArtifact> artifacts;

    /**
     * Default constructor.
     */
    public BookshelfLocation() {
    }

    // Parameterized constructor
    public BookshelfLocation(Long shelfNumber, String section, String position) {
        this.shelfNumber = shelfNumber;
        this.section = section;
        this.position = position;
    }

    // Getters and Setters

    /**
     * Retrieves the unique identifier of the bookshelf location.
     * @return The bookshelf location identifier.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the bookshelf location.
     * @param id The bookshelf location identifier to be set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the shelf number of the bookshelf location.
     * @return The shelf number.
     */
    public Long getShelfNumber() {
        return shelfNumber;
    }

    /**
     * Sets the shelf number of the bookshelf location.
     * @param shelfNumber The shelf number to be set.
     */
    public void setShelfNumber(Long shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    /**
     * Retrieves the section of the bookshelf location.
     * @return The section.
     */
    public String getSection() {
        return section;
    }

    /**
     * Sets the section of the bookshelf location.
     * @param section The section to be set.
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * Retrieves the position of the bookshelf location.
     * @return The position.
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the position of the bookshelf location.
     * @param position The position to be set.
     */
    public void setPosition(String position) {
        this.position = position;
    }

    public List<BibliographicArtifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<BibliographicArtifact> artifacts) {
        this.artifacts = artifacts;
    }

}
