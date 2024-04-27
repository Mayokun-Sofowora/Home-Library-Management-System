package com.library.domain;

import jakarta.persistence.*;
import java.util.List;

/**
 * Represents a member of the library system.
 */
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String name;
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
     * @return A list of bibliographic artifacts matching the keyword.
     */
    public List<BibliographicArtifact> searchGlobalCollection(String keyword) {
        //TO DO 
        // Implementation of the search method
        // Perform logic to search the global collection based on the keyword
        // For now, return an empty list
        return List.of();
    }

    /**
     * Reserves an item for the member.
     * @param item The bibliographic artifact to reserve.
     * @return True if the reservation is successful, false otherwise.
     */
    public boolean reserveItem(BibliographicArtifact item) {
        //TO DO 
        // Implementation of the reservation
        // Perform logic to reserve an item
        // Check if the item is available for reservation and update its state
        if (item.checkAvailability()) {
            // Perform the reservation (e.g., update the item's state and create a reservation record)
            return true;
        } else {
            // The item is not available for reservation
            return false;
        }
    }

    /**
     * Borrows an item for the member.
     * @param item The bibliographic artifact to borrow.
     * @return The loan object representing the borrowed item, or null if the item cannot be borrowed.
     */
    public Loan borrowItem(BibliographicArtifact item) {
        //TO DO 
        // Implementation of borrowing an item
        // Perform logic to borrow an item
        // Check if the item is available for loan, update its state, and create a loan record
        if (item.checkAvailability()) {
            // Perform the loan (e.g., update the item's state and create a loan record)
            Loan loan = new Loan();
            loan.setItem(item);
            loan.setBorrower(this);
            // Update the item's state (e.g., mark as borrowed)
            // Save the loan to the member's loan list
            loans.add(loan);
            return loan;
        } else {
            // The item is not available for loan
            return null;
        }
    }

    /**
     * Returns an item that was borrowed by the member.
     * @param loan The loan object representing the borrowed item.
     * @return True if the return is successful, false otherwise.
     */
    public boolean returnItem(Loan loan) {
        //TO DO 
        // Implementation of returning an item
        // Perform logic to return a borrowed item
        // Update the item's state and the loan record
        BibliographicArtifact item = loan.getItem();
        // Update the item's state (e.g., mark as available)
        // Update the loan record (e.g., set return date and mark as returned)
        // Remove the loan from the member's loan list
        loans.remove(loan);
        return true;
    }

    // Getters and Setters
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
