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
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "authors")
    private List<BibliographicArtifact> artifacts;

    /**
     * Default constructor.
     */
    public Author() {
    }

    // Getters and Setters

    /**
     * Retrieves the unique identifier of the author.
     *
     * @return The author identifier.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the author.
     *
     * @param authorId The author identifier to be set.
     */
    public void setId(Long authorId) {
        this.id = authorId;
    }

    /**
     * Retrieves the name of the author.
     *
     * @return The author name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the author.
     *
     * @param name The author name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the list of artifacts authored by the author.
     *
     * @return The list of authored artifacts.
     */
    public List<BibliographicArtifact> getArtifacts() {
        return artifacts;
    }

    /**
     * Sets the list of artifacts authored by the author.
     *
     * @param authoredArtifacts The list of authored artifacts to be set.
     */
    public void setArtifacts(List<BibliographicArtifact> authoredArtifacts) {
        this.artifacts = authoredArtifacts;
    }
}
