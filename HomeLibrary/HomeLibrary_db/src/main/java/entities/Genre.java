package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * Represents a genre in the library system.
 */
@Entity
public class Genre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "genre_artifact",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "artifact_id")
    )
    private List<BibliographicArtifact> genreArtifacts;

    /**
     * Default constructor.
     */
    public Genre() {
    }

    // Getters and Setters

    /**
     * Retrieves the unique identifier of the genre.
     * @return The genre identifier.
     */
    public Long getGenreId() {
        return genreId;
    }

    /**
     * Sets the unique identifier of the genre.
     * @param genreId The genre identifier to be set.
     */
    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    /**
     * Retrieves the name of the genre.
     * @return The name of the genre.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the genre.
     * @param name The name of the genre to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the list of bibliographic artifacts associated with the genre.
     * @return The list of genre artifacts.
     */
    public List<BibliographicArtifact> getGenreArtifacts() {
        return genreArtifacts;
    }

    /**
     * Sets the list of bibliographic artifacts associated with the genre.
     * @param genreArtifacts The list of genre artifacts to be set.
     */
    public void setGenreArtifacts(List<BibliographicArtifact> genreArtifacts) {
        this.genreArtifacts = genreArtifacts;
    }
}
