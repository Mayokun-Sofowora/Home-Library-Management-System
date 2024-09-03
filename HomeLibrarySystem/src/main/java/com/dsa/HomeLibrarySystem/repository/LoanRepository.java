package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
