package com.dsa.homelibrary.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 * Represents a bookshelf location in the library system.
 */
@Entity
//@Table(name = "Bookshelf_Location", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"section", "Shelf_Number"})
//})
public class BookshelfLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column(nullable = false)
    private String section;

    @Column(nullable = false)
    private Integer shelfNumber;

    @OneToMany(mappedBy = "location")
    private List<BibliographicArtifact> artifacts;

    /**
     * Default constructor.
     */
    public BookshelfLocation() {
    }

    // Getters and Setters...

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(Integer shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public List<BibliographicArtifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<BibliographicArtifact> artifacts) {
        this.artifacts = artifacts;
    }
}
