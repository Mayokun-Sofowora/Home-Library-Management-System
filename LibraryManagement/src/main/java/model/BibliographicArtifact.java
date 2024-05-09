package model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Represents a bibliographic artifact in the library system.
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED) // We use JOINED strategy for inheritance
public abstract class BibliographicArtifact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String type;
    private int availableCopies;

    @ManyToMany(mappedBy = "authoredArtifacts")
    private List<Author> authors;

    @ManyToMany(mappedBy = "genreArtifacts")
    private List<Genre> genres;

    @ManyToOne
    private BookshelfLocation location;

    

    /**
     * Default constructor.
     */
    public BibliographicArtifact() {
    }

    // Abstract method to be implemented by subclasses

    /**
     * Checks the availability of the bibliographic artifact.
     * @return True if the artifact is available, false otherwise.
     */
    public abstract boolean checkAvailability();

    // Getters and Setters

    /**
     * Retrieves the unique identifier of the bibliographic artifact.
     * @return The artifact identifier.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the bibliographic artifact.
     * @param id The artifact identifier to be set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the title of the bibliographic artifact.
     * @return The artifact title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the bibliographic artifact.
     * @param title The artifact title to be set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the type of the bibliographic artifact.
     * @return The artifact type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the bibliographic artifact.
     * @param type The artifact type to be set.
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * Retrieves the type of the bibliographic artifact.
     * @return The artifact type.
     */
    public int getavailableCopies() {
        return availableCopies;
    }

    /**
     * Sets the type of the bibliographic artifact.
     * @param availableCopies the artifact type to be set
     */
    public void setavailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
    
    /**
     * Retrieves the list of authors of the bibliographic artifact.
     * @return The list of authors.
     */
    public List<Author> getAuthors() {
        return authors;
    }

    /**
     * Sets the list of authors of the bibliographic artifact.
     * @param authors The list of authors to be set.
     */
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    /**
     * Retrieves the list of genres of the bibliographic artifact.
     * @return The list of genres.
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * Sets the list of genres of the bibliographic artifact.
     * @param genres The list of genres to be set.
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     * Retrieves the location of the bibliographic artifact in the library.
     * @return The artifact location.
     */
    public BookshelfLocation getLocation() {
        return location;
    }

    /**
     * Sets the location of the bibliographic artifact in the library.
     * @param location The artifact location to be set.
     */
    public void setLocation(BookshelfLocation location) {
        this.location = location;
    }

   
}
