package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.Loan;
import com.dsa.HomeLibrarySystem.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return loanRepository.save(loan);
    }

    public void deleteLoanById(Long loanId) {
        loanRepository.deleteById(loanId);
    }

    public boolean renewLoan(Long loanId) {
        Optional<Loan> optionalLoan = loanRepository.findById(loanId);
        if (optionalLoan.isPresent()) {
            Loan loan = optionalLoan.get();
            return loan.renewLoan() && loanRepository.save(loan) != null;
        } else {
            return false;
        }
    }
}
