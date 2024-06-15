package entities;

import javax.persistence.*;

import java.util.List;
import java.util.Set;

/**
 * Represents a bibliographic artifact in the library system.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // We use JOINED strategy for inheritance
public abstract class BibliographicArtifact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String type;
    private int availableCopies;
    private String language;
    
    @Column(name = "publication_year")
    private int year;
    private boolean isCensored;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCensored() {
        return isCensored;
    }

    public void setCensored(boolean censored) {
        this.isCensored = censored;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public BookshelfLocation getLocation() {
        return location;
    }

    public void setLocation(BookshelfLocation location) {
        this.location = location;
    }
}
