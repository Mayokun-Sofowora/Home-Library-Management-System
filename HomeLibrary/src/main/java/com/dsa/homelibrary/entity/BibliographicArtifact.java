package com.dsa.homelibrary.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * Represents a bibliographic artifact in the library system.
 */
@Entity
//@Table(name = "Bibliographic_Artifact")
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "TYPE")
public abstract class BibliographicArtifact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artifactId;

    @Column(unique = true, nullable = false)
    private String title;

    private String type;
    private int availableCopies;
    private String language;
    private int publicationYear;
    private boolean isCensored;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Author_Artifact",
            joinColumns = @JoinColumn(name = "Artifact_Id"),
            inverseJoinColumns = @JoinColumn(name = "Author_Id")
    )
    private List<Author> authors;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Genre_Artifact",
            joinColumns = @JoinColumn(name = "Artifact_Id"),
            inverseJoinColumns = @JoinColumn(name = "Genre_Id")
    )
    private List<Genre> genres;

    @OneToMany(mappedBy = "item")
    private List<Loan> loans;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Location_Id")
    private BookshelfLocation location;

    /**
     * Default constructor.
     */
    public BibliographicArtifact() {
    }

    /**
     * Checks the availability of the bibliographic artifact.
     * @return True if the artifact is available, false otherwise.
     */
    public abstract boolean checkAvailability();

    // Getters and Setters...
    public Long getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Long artifactId) {
        this.artifactId = artifactId;
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

    public boolean getIsCensored() {
        return isCensored;
    }

    public void setIsCensored(boolean isCensored) {
        this.isCensored = isCensored;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
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

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}
