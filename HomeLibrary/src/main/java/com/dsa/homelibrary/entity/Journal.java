package com.dsa.homelibrary.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 * Represents a journal in the library system.
 */
@Entity
public class Journal extends BibliographicArtifact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artifactId;

    @Column(unique = true, nullable = false)
    private String ISSN;

    @Column(nullable = false)
    private int volume;

    @Column(nullable = false)
    private int issue;

    @ElementCollection
    private List<String> articles;

    private int availableCopies;

    /**
     * Default constructor.
     */
    public Journal() {
    }

    // Getters and Setters...

    public Long getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Long artifactId) {
        this.artifactId = artifactId;
    }

    public String getISSN() {
        return ISSN;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    public List<String> getArticles() {
        return articles;
    }

    public void setArticles(List<String> articles) {
        this.articles = articles;
    }

    @Override
    public boolean checkAvailability() {
        return availableCopies > 0;
    }
}
