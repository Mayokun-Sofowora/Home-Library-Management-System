package com.dsa.homelibrary.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a member of the library system.
 */
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "borrower")
    private List<Loan> loans;

    /**
     * Default constructor.
     */
    public Member() {
    }

    /**
     * Searches the global collection based on a keyword.
     * @param keyword The keyword to search for.
     * @param allArtifacts A list of all bibliographic artifacts available in the system.
     * @return A list of bibliographic artifacts matching the keyword.
     */
    public List<BibliographicArtifact> searchGlobalCollection(String keyword, List<BibliographicArtifact> allArtifacts) {
        return allArtifacts.stream()
                .filter(artifact -> artifact.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        artifact.getType().toLowerCase().contains(keyword.toLowerCase()) ||
                        artifact.getLanguage().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Reserves an item for the member.
     * @param item The bibliographic artifact to reserve.
     * @return True if the reservation is successful, false otherwise.
     */
    public boolean reserveItem(BibliographicArtifact item) {
        if (item.checkAvailability()) {
            item.setAvailableCopies(item.getAvailableCopies() - 1);
            // Additional logic for reservation can be added here, e.g., adding to a reservation list
            return true;
        } else {
            return false;
        }
    }

    /**
     * Borrows an item for the member.
     * @param item The bibliographic artifact to borrow.
     * @return The loan object representing the borrowed item, or null if the item cannot be borrowed.
     */
    public Loan borrowItem(BibliographicArtifact item) {
        if (item.checkAvailability()) {
            Loan loan = new Loan();
            loan.setItem(item);
            loan.setBorrower(this);
            loans.add(loan);
            return loan;
        } else {
            return null;
        }
    }

    /**
     * Returns an item that was borrowed by the member.
     * @param loan The loan object representing the borrowed item.
     * @return True if the return is successful, false otherwise.
     */
    public boolean returnItem(Loan loan) {
        BibliographicArtifact item = loan.getItem();
        loans.remove(loan);
        return true;
    }

    // Getters and Setters...

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}
