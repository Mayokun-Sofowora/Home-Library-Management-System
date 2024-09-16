package com.dsa.HomeLibrarySystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

/**
 * Represents a loan of a bibliographic artifact to a member in the library system.
 */
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "artifact_id")
    private BibliographicArtifact artifact;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "member_id")
    private Member borrower;

    @Column(nullable = false)
    private LocalDate loanDate;

    private LocalDate returnDate;

    /**
     * Default constructor.
     */
    public Loan() {
        this.loanDate = LocalDate.now(); // Set the loan date to today by default
    }

    // Extend loan period by default (to one week)
    public boolean renewLoan() {
        if (returnDate != null) {
            return false;
        }
        this.returnDate = loanDate.plusWeeks(1);
        return true;
    }

    // Method to update available copies after borrowing/returning
    public void updateCopies(int borrowed) {
        if (artifact != null) {
            // Update available copies in the artifact
            artifact.setAvailableCopies(artifact.getAvailableCopies() - borrowed);
            if (artifact.getAvailableCopies() < 0) {
                artifact.setAvailableCopies(0);
            }
            // Check availability after the update
            if (!artifact.checkAvailability()) {
                System.out.println("No copies available!");
            }
        } else {
            System.out.println("Artifact is not associated with this loan.");
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long loanId) {
        this.id = loanId;
    }

    public BibliographicArtifact getArtifact() {
        return artifact;
    }

    public void setArtifact(BibliographicArtifact item) {
        this.artifact = item;
    }

    public Member getBorrower() {
        return borrower;
    }

    public void setBorrower(Member borrower) {
        this.borrower = borrower;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
