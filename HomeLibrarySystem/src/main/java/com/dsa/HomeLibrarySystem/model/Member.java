package com.dsa.HomeLibrarySystem.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    private Long memberId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role;

    @OneToMany(mappedBy = "borrower")
    private List<Loan> loans;

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
        return true; // Implement your logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement your logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement your logic if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement your logic if needed
    }

    // Custom methods

    public List<BibliographicArtifact> searchGlobalCollection(String keyword) {
        // TODO: Implement search logic
        return List.of();
    }

    public boolean reserveItem(BibliographicArtifact item) {
        // TODO: Implement reservation logic
        return item.checkAvailability();
    }

    public Loan borrowItem(BibliographicArtifact item) {
        // TODO: Implement borrowing logic
        if (item.checkAvailability()) {
            Loan loan = new Loan();
            loan.setItem(item);
            loan.setBorrower(this);
            loans.add(loan);
            return loan;
        }
        return null;
    }

    public boolean returnItem(Loan loan) {
        // TODO: Implement return logic
        loans.remove(loan);
        return true;
    }

    // Getters and setters

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
