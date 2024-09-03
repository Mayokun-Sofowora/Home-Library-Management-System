package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.BibliographicArtifact;
import com.dsa.HomeLibrarySystem.repository.BibliographicArtifactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BibliographicArtifactService {

    private final BibliographicArtifactRepository bibliographicArtifactRepository;

    @Autowired
    public BibliographicArtifactService(BibliographicArtifactRepository bibliographicArtifactRepository) {
        this.bibliographicArtifactRepository = bibliographicArtifactRepository;
    }

    public List<BibliographicArtifact> getAllBibliographicArtifacts() {
        return bibliographicArtifactRepository.findAll();
    }

    public Optional<BibliographicArtifact> getBibliographicArtifactById(Long artifactId) {
        return bibliographicArtifactRepository.findById(artifactId);
    }

    public BibliographicArtifact saveOrUpdateBibliographicArtifact(BibliographicArtifact artifact) {
        return bibliographicArtifactRepository.save(artifact);
    }

    public void deleteBibliographicArtifactById(Long artifactId) {
        bibliographicArtifactRepository.deleteById(artifactId);
    }
}
