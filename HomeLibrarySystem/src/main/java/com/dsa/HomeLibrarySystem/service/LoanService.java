package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.*;
import com.dsa.HomeLibrarySystem.repository.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    
    private final LoanRepository loanRepository;
    
    private EntityManager entityManager;

    @Autowired
    public LoanService(LoanRepository loanRepository, EntityManager entityManager) {
        this.loanRepository = loanRepository;
        this.entityManager = entityManager;
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Optional<Loan> getLoanById(Long loanId) {
        return loanRepository.findById(loanId);
    }

    public Loan saveOrUpdateLoan(Loan loan) {
        System.out.println("***************** Inside saveOrUpdateLoan ***************\n");
        if (loan.getBorrower() == null || loan.getArtifact() == null) {
            throw new IllegalArgumentException("Borrower and item must not be null.");
        }
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
        return loanRepository.findByBorrower_Id(borrowerId);
    }

    public List<Loan> getLoansByArtifactId(Long artifactId) {
        return loanRepository.findByArtifact_Id(artifactId);
    }

    public List<Loan> getOutstandingLoans() {
        return loanRepository.findByReturnDateIsNull();
    }

    public void deleteLoanById(Long loanId) {
        loanRepository.deleteById(loanId);
    }

    @Transactional
    public Loan renewLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found"));
        
        // Add your renewal logic here, e.g., extending the return date
        loan.setReturnDate(loan.getReturnDate().plusDays(14));
        
        return loanRepository.save(loan);
    }

    @Transactional
    public void returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found"));
        
        // Add your return logic here, e.g., updating the artifact's available copies
        BibliographicArtifact artifact = loan.getArtifact();
        artifact.setAvailableCopies(artifact.getAvailableCopies() + 1);
        
        // Delete the loan or mark it as returned
        loanRepository.delete(loan);
        resetAutoIncrementIfEmpty();
    }

    @Transactional
    public void resetAutoIncrementIfEmpty() {
        if (loanRepository.count() == 0) {
            // Reset auto-increment to 1 if the table is empty
            entityManager.createNativeQuery("ALTER TABLE loan AUTO_INCREMENT = 1").executeUpdate();
        }
    }

}
