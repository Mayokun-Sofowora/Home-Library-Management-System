package com.dsa.HomeLibrarySystem.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Represents a member of the library system.
 */
@Entity
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role;

    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loans = new ArrayList<>();

    // Default constructor
    public Member() {
    }

    // UserDetails implementation

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return authorities based on role or other logic
        return Collections.singletonList(() -> "ROLE_" + role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // Use email as username for authentication purposes
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement logic if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement logic if needed
    }

    // Custom methods

    public Loan borrowItem(BibliographicArtifact item) {
        if (item.checkAvailability()) {
            Loan loan = new Loan();
            loan.setArtifact(item);
            loan.setBorrower(this);
            loan.setLoanDate(LocalDate.now()); // Set loan date to current date
            item.setAvailableCopies(item.getAvailableCopies() - 1);
            loans.add(loan);
            return loan;
        }
        return null;
    }

    public boolean returnItem(Loan loan) {
        if (loan != null) {
            loans.remove(loan);
            loan.getArtifact().setAvailableCopies(loan.getArtifact().getAvailableCopies() + 1); // Increase available copies
            loan.setReturnDate(LocalDate.now()); // Set return date to current date
            return true;
        }
        return false;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long memberId) {
        this.id = memberId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
