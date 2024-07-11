package com.dsa.homelibrary.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 * Represents a genre in the library system.
 */
@Entity
//@Table(name = "Genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "genres")
//    @JoinTable(
//            name = "Genre_Artifact",
//            joinColumns = @JoinColumn(name = "Genre_Id"),
//            inverseJoinColumns = @JoinColumn(name = "Artifact_Id")
//    )
    private List<BibliographicArtifact> genreArtifacts;

    /**
     * Default constructor.
     */
    public Genre() {
    }

    // Getters and Setters...

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BibliographicArtifact> getGenreArtifacts() {
        return genreArtifacts;
    }

    public void setGenreArtifacts(List<BibliographicArtifact> genreArtifacts) {
        this.genreArtifacts = genreArtifacts;
    }
}
