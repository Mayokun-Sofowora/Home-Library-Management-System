package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.Loan;
import com.dsa.HomeLibrarySystem.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByReturnDate(Date date);

    Optional<Loan> findByBorrowerId(Long memberId);

    Optional<Loan> findByArtifactId(Long artifactId);

    Optional<Loan> findByReturnDateIsNull();


}
