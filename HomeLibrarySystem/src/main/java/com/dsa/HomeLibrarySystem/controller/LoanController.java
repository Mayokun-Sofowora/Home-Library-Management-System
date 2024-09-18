package com.dsa.HomeLibrarySystem.controller;

import com.dsa.HomeLibrarySystem.model.*;
import com.dsa.HomeLibrarySystem.service.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;
    private final BibliographicArtifactService artifactService;
    private final MemberService memberService;

    @Autowired
    public LoanController(LoanService loanService, BibliographicArtifactService artifactService, MemberService memberService) {
        this.loanService = loanService;
        this.artifactService = artifactService;
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        Optional<Loan> loan = loanService.getLoanById(id);
        return loan.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addLoan(@RequestBody Loan loan) {
        try {
            // Validate loan request
            loanService.validateLoanRequest(loan);
            // Retrieve member and artifact
            Member member = memberService.getMemberById(loan.getBorrower().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Member not found"));
            BibliographicArtifact artifact = artifactService.getBibliographicArtifactById(loan.getArtifact().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Bibliographic artifact not found"));
            // Check available copies
            if (artifact.getAvailableCopies() <= 0) {
                return ResponseEntity.badRequest().body("No copies available for loan");
            }
            System.out.println(member.getId());
            // Set relationships and dates
            loan.setBorrower(member);
            System.out.println(artifact.getId());
            loan.setArtifact(artifact);
            loan.setLoanDate(LocalDate.now());
            // Set a default return date, e.g., 14 days from now
            loan.setReturnDate(LocalDate.now().plusDays(14));
            // Save the loan
            Loan savedLoan = loanService.saveOrUpdateLoan(loan);
            // Update available copies
            artifact.setAvailableCopies(artifact.getAvailableCopies() - 1);
            artifactService.saveOrUpdateBibliographicArtifact(artifact);

            return new ResponseEntity<>(savedLoan, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the loan request");
        }
    }

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        try {
            if (loan.getBorrowerId() != null) {
                Member borrower = memberService.getMemberById(loan.getBorrowerId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Borrower ID"));
                loan.setBorrower(borrower);
            }
            if (loan.getArtifactId() != null) {
                BibliographicArtifact artifact = artifactService.getBibliographicArtifactById(loan.getArtifactId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Artifact ID"));
                
                if (artifact.getAvailableCopies() <= 0) {
                    return ResponseEntity.badRequest().body(null);
                }
                
                loan.setArtifact(artifact);
                artifact.setAvailableCopies(artifact.getAvailableCopies() - 1);
                artifactService.saveOrUpdateBibliographicArtifact(artifact);
            }
            if (loan.getLoanDate() == null) {
                loan.setLoanDate(LocalDate.now());
            }
            if (loan.getReturnDate() == null) {
                loan.setReturnDate(loan.getLoanDate().plusDays(14)); // Default to 14 days loan period
            }
            Loan savedLoan = loanService.saveOrUpdateLoan(loan);
            return new ResponseEntity<>(savedLoan, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long id, @RequestBody Loan loan) {
        loan.setId(id);
        Loan updatedLoan = loanService.saveOrUpdateLoan(loan);
        return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoanById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/renew")
    public ResponseEntity<Loan> renewLoan(@PathVariable Long id) {
        try {
            Loan renewedLoan = loanService.renewLoan(id);
            return ResponseEntity.ok(renewedLoan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Void> returnLoan(@PathVariable Long id) {
        try {
            loanService.returnLoan(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
