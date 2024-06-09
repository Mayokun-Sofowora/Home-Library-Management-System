package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<Journal, Long> {
}
