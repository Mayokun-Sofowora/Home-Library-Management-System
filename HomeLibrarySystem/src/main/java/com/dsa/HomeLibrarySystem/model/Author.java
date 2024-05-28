package com.dsa.HomeLibrarySystem.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Represents an author in the library system.
 */
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;
    private String name;
    private String country;

    @ManyToMany
    @JoinTable(
            name = "author_artifact",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "artifact_id")
    )
    private List<BibliographicArtifact> authoredArtifacts;

    /**
     * Default constructor.
     */
    public Author() {
    }

    // Getters and Setters

    /**
     * Retrieves the unique identifier of the author.
     * @return The author identifier.
     */
    public Long getAuthorId() {
        return authorId;
    }

    /**
     * Sets the unique identifier of the author.
     * @param authorId The author identifier to be set.
     */
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    /**
     * Retrieves the name of the author.
     * @return The author name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the author.
     * @param name The author name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the country of the author.
     * @return The author country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the author.
     * @param country The author country to be set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Retrieves the list of artifacts authored by the author.
     * @return The list of authored artifacts.
     */
    public List<BibliographicArtifact> getAuthoredArtifacts() {
        return authoredArtifacts;
    }

    /**
     * Sets the list of artifacts authored by the author.
     * @param authoredArtifacts The list of authored artifacts to be set.
     */
    public void setAuthoredArtifacts(List<BibliographicArtifact> authoredArtifacts) {
        this.authoredArtifacts = authoredArtifacts;
    }
}
