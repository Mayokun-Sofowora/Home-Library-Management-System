package entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Represents a loan of a bibliographic artifact to a member in the library system.
 */
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @ManyToOne
    @JoinColumn(name = "artifact_id")
    private BibliographicArtifact item;

    @ManyToOne
    @JoinColumn(name = "member_id")
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
        //TO DO 
        // Check if the loan has already been returned
        if (returnDate != null) {
            return false; // A loan that has been returned cannot be renewed
        }
        
        // Calculate the new return date (e.g., one week later)
        // You can implement the renewal logic based on your specific requirements here
        // In this example, we assume one week is added to the current date
        Date currentDate = new Date();
        long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000; // One week in milliseconds
        Date newReturnDate = new Date(currentDate.getTime() + oneWeekInMillis);
        
        // Set the new return date
        setReturnDate(newReturnDate);
        
        return true; // Renewal is successful
    }

    // Getters and Setters
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
