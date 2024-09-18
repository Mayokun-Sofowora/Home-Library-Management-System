package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.*;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByReturnDate(Date date);

    List<Loan> findByBorrower_Id(Long borrowerId);

    List<Loan> findByArtifact_Id(Long artifactId);

    List<Loan> findByReturnDateIsNull();


}
