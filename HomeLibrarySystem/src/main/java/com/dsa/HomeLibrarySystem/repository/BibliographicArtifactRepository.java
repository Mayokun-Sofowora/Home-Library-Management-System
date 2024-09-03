package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.BibliographicArtifact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliographicArtifactRepository extends JpaRepository<BibliographicArtifact, Long> {
    boolean existsByTitle(String title);
}
