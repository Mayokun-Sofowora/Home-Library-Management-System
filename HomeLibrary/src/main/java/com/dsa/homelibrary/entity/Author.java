package com.dsa.homelibrary.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 * Represents an author in the library system.
 */
@Entity
//@Table(name = "Author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    @ManyToMany(mappedBy = "authors")
//    @JoinTable(
//            name = "Author_Artifact",
//            joinColumns = @JoinColumn(name = "Author_Id"),
//            inverseJoinColumns = @JoinColumn(name = "Artifact_Id")
//    )
    private List<BibliographicArtifact> authoredArtifacts;

    /**
     * Default constructor.
     */
    public Author() {
    }

    // Getters and Setters...
    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<BibliographicArtifact> getAuthoredArtifacts() {
        return authoredArtifacts;
    }

    public void setAuthoredArtifacts(List<BibliographicArtifact> authoredArtifacts) {
        this.authoredArtifacts = authoredArtifacts;
    }
}
