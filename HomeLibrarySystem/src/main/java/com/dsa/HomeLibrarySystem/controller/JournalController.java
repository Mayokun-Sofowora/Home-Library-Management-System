package com.dsa.HomeLibrarySystem.controller;

import com.dsa.HomeLibrarySystem.model.Journal;
import com.dsa.HomeLibrarySystem.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/journals")
public class JournalController {

    private final JournalService journalService;

    @Autowired
    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    // Fetch all journals
    @GetMapping
    public ResponseEntity<List<Journal>> getAllJournals() {
        List<Journal> journals = journalService.getAllJournals();
        return new ResponseEntity<>(journals, HttpStatus.OK);
    }

    // Search journals by a query string
    @GetMapping("/search")
    public ResponseEntity<List<Journal>> searchJournals(@RequestParam String query) {
        List<Journal> journals = journalService.searchJournals(query);
        return new ResponseEntity<>(journals, HttpStatus.OK);
    }

    // Fetch journal by ID
    @GetMapping("/{id}")
    public ResponseEntity<Journal> getJournalById(@PathVariable Long id) {
        Optional<Journal> journal = journalService.getJournalById(id);
        return journal.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Add a new journal
    @PostMapping
    public ResponseEntity<Journal> addJournal(@RequestBody Journal journal) {
        Journal savedJournal = journalService.saveOrUpdateJournal(journal);
        return new ResponseEntity<>(savedJournal, HttpStatus.CREATED);
    }

    // Update an existing journal
    @PutMapping("/{id}")
    public ResponseEntity<Journal> updateJournal(@PathVariable Long id, @RequestBody Journal journal) {
        journal.setId(id);
        Journal updatedJournal = journalService.saveOrUpdateJournal(journal);
        return new ResponseEntity<>(updatedJournal, HttpStatus.OK);
    }

    // Delete a journal by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJournal(@PathVariable Long id) {
        journalService.deleteJournalById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
