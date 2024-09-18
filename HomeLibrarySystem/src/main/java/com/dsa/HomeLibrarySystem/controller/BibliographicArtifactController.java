package com.dsa.HomeLibrarySystem.controller;

import com.dsa.HomeLibrarySystem.model.BibliographicArtifact;
import com.dsa.HomeLibrarySystem.service.BibliographicArtifactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artifacts")
public class BibliographicArtifactController {

    private final BibliographicArtifactService bibliographicArtifactService;

    @Autowired
    public BibliographicArtifactController(BibliographicArtifactService bibliographicArtifactService) {
        this.bibliographicArtifactService = bibliographicArtifactService;
    }

    @GetMapping
    public ResponseEntity<List<BibliographicArtifact>> getAllBibliographicArtifacts() {
        List<BibliographicArtifact> artifacts = bibliographicArtifactService.getAllBibliographicArtifacts();
        return new ResponseEntity<>(artifacts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BibliographicArtifact> getBibliographicArtifactById(@PathVariable Long id) {
        Optional<BibliographicArtifact> artifact = bibliographicArtifactService.getBibliographicArtifactById(id);
        return artifact.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<BibliographicArtifact> createBibliographicArtifact(@RequestBody BibliographicArtifact artifact) {
        BibliographicArtifact savedArtifact = bibliographicArtifactService.saveOrUpdateBibliographicArtifact(artifact);
        return new ResponseEntity<>(savedArtifact, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BibliographicArtifact> updateBibliographicArtifact(@PathVariable Long id, @RequestBody BibliographicArtifact artifact) {
        artifact.setId(id);
        BibliographicArtifact updatedArtifact = bibliographicArtifactService.saveOrUpdateBibliographicArtifact(artifact);
        return new ResponseEntity<>(updatedArtifact, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBibliographicArtifact(@PathVariable Long id) {
        bibliographicArtifactService.deleteBibliographicArtifactById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
