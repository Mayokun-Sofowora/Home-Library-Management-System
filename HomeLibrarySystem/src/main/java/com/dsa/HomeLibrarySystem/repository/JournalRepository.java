package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JournalRepository extends JpaRepository<Journal, Long> {
    Optional<Journal> findByTitle(String title);
    Optional<Journal> findByLocationId(Long locationId);
    long countByLocationId(Long locationId);
    List<Journal> findByTitleContainingIgnoreCase(String title);
}
