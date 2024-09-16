package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.*;
import com.dsa.HomeLibrarySystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Optional<Loan> getLoanById(Long loanId) {
        return loanRepository.findById(loanId);
    }

    public Loan saveOrUpdateLoan(Loan loan) {
//        if (loan.getBorrower() == null || loan.getArtifact() == null) {
//            throw new IllegalArgumentException("Borrower and item must not be null.");
//        }
        return loanRepository.save(loan);
    }

    public Optional<Loan> getLoanByReturnDate(Date date) {
        return loanRepository.findByReturnDate(date);
    }

    public Loan convertToDto(Loan loan) {
        Member borrower = loan.getBorrower();
        BibliographicArtifact artifact = loan.getArtifact();
        // Create a shallow copy of the loan with only the necessary information
        Loan loanResponse = new Loan();
        loanResponse.setId(loan.getId());
        loanResponse.setLoanDate(loan.getLoanDate());
        loanResponse.setReturnDate(loan.getReturnDate());
        // Set borrower details
        if (borrower != null) {
            Member borrowerResponse = new Member();
            borrowerResponse.setId(borrower.getId());
            borrowerResponse.setName(borrower.getName());
            loanResponse.setBorrower(borrowerResponse);
        }
        // Set artifact details
        if (artifact != null) {
            BibliographicArtifact artifactResponse = new BibliographicArtifact() {
                @Override
                public boolean checkAvailability() {
                    return artifact.getAvailableCopies() > 0;
                }
            };
            artifactResponse.setId(artifact.getId());
            artifactResponse.setTitle(artifact.getTitle());
            artifactResponse.setType(artifact.getType());
            loanResponse.setArtifact(artifactResponse);
        }
        return loanResponse;
    }

    public void validateLoanRequest(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null");
        }
        if (loan.getBorrower() == null || loan.getBorrower().getId() == null) {
            throw new IllegalArgumentException("Borrower information is required");
        }
        if (loan.getArtifact() == null || loan.getArtifact().getId() == null) {
            throw new IllegalArgumentException("Artifact information is required");
        }
        // Add any other validation rules as needed
    }

    public List<Loan> getLoansByBorrowerId(Long borrowerId) {
        return loanRepository.findByBorrowerId(borrowerId).stream().toList();
    }

    public List<Loan> getLoansByArtifactId(Long artifactId) {
        return loanRepository.findByArtifactId(artifactId).stream().toList();
    }

    public List<Loan> getOutstandingLoans() {
        return loanRepository.findByReturnDateIsNull().stream().toList();
    }

    public void deleteLoanById(Long loanId) {
        loanRepository.deleteById(loanId);
    }

    public boolean renewLoan(Long loanId) {
        Optional<Loan> optionalLoan = loanRepository.findById(loanId);
        if (optionalLoan.isPresent()) {
            Loan loan = optionalLoan.get();
            if (!loan.renewLoan()) return false; // Renew the loan
            loanRepository.save(loan); // Save the updated loan
            return true;
        } else {
            return false; // Loan not found
        }
    }
}
