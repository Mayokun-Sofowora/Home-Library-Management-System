package com.dsa.HomeLibrarySystem.controller;

import com.dsa.HomeLibrarySystem.model.*;
import com.dsa.HomeLibrarySystem.service.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "*") // Add CORS configuration if needed
public class LoanController {

    private final LoanService loanService;
    private final MemberService memberService;
    private final BibliographicArtifactService artifactService;

    @Autowired
    public LoanController(LoanService loanService, MemberService memberService, BibliographicArtifactService artifactService) {
        this.loanService = loanService;
        this.memberService = memberService;
        this.artifactService = artifactService;
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        List<Loan> loan = loans.stream().map(loanService::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(loan, HttpStatus.OK);
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
            // Set relationships and dates
            loan.setBorrower(member);
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
        Loan savedLoan = loanService.saveOrUpdateLoan(loan);
        return new ResponseEntity<>(savedLoan, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<String> returnLoan(@PathVariable Long id) {
        try {
            Loan loan = loanService.getLoanById(id).orElseThrow(() -> new IllegalArgumentException("Loan not found"));
            Member member = loan.getBorrower();

            boolean returned = member.returnItem(loan);
            if (returned) {
                loanService.saveOrUpdateLoan(loan);
                return ResponseEntity.ok("Item returned successfully!");
            } else {
                return ResponseEntity.badRequest().body("Unable to return the item.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while returning the item: " + e.getMessage());
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

    @PostMapping("/renew/{id}")
    public ResponseEntity<Boolean> renewLoan(@PathVariable Long id) {
        boolean renewed = loanService.renewLoan(id);
        return new ResponseEntity<>(renewed, renewed ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
