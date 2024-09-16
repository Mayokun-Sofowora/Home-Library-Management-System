package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.*;
import com.dsa.HomeLibrarySystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BibliographicArtifactService {

    private final BibliographicArtifactRepository bibliographicArtifactRepository;
    private final BookRepository bookRepository;
    private final JournalRepository journalRepository;

    @Autowired
    public BibliographicArtifactService(BibliographicArtifactRepository bibliographicArtifactRepository,
                                        BookRepository bookRepository,
                                        JournalRepository journalRepository) {
        this.bibliographicArtifactRepository = bibliographicArtifactRepository;
        this.bookRepository = bookRepository;
        this.journalRepository = journalRepository;
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

    // Global search method
    public List<BibliographicArtifact> globalSearch(String query) {
        List<Book> books = bookRepository.findAll();
        List<Journal> journals = journalRepository.findAll();

        List<BibliographicArtifact> foundItems = books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        book.getAuthors().stream()
                                .anyMatch(author -> author.getName().toLowerCase().contains(query.toLowerCase())))
                .collect(Collectors.toList());

        foundItems.addAll(journals.stream()
                .filter(journal -> journal.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        journal.getAuthors().stream()
                                .anyMatch(editor -> editor.getName().toLowerCase().contains(query.toLowerCase())))
                .toList());

        return foundItems;
    }
}
