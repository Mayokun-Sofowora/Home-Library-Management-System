package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.BibliographicArtifact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BibliographicArtifactRepository extends JpaRepository<BibliographicArtifact, Long> {
    boolean existsByTitle(String title);
    Optional<BibliographicArtifact> findById(Long id);
    List<BibliographicArtifact> findByTitleContainingIgnoreCase(String title);
}
