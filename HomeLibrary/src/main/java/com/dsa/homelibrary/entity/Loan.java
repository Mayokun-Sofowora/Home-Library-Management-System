package com.dsa.homelibrary.entity;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Represents a loan of a bibliographic artifact to a member in the library system.
 */
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Artifact_Id", nullable = false)
    private BibliographicArtifact item;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Member_Id", nullable = false)
    private Member borrower;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date loanDate;

    @Temporal(TemporalType.DATE)
    private Date returnDate;

    /**
     * Default constructor.
     */
    public Loan() {
    }

    /**
     * Renews the loan by extending the return date.
     * @return True if the renewal is successful, false otherwise.
     */
    public boolean renewLoan() {
        if (returnDate != null) {
            return false;
        }
        Date currentDate = new Date();
        long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000;
        Date newReturnDate = new Date(currentDate.getTime() + oneWeekInMillis);
        setReturnDate(newReturnDate);
        return true;
    }

    // Getters and Setters...

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public BibliographicArtifact getItem() {
        return item;
    }

    public void setItem(BibliographicArtifact item) {
        this.item = item;
    }

    public Member getBorrower() {
        return borrower;
    }

    public void setBorrower(Member borrower) {
        this.borrower = borrower;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
