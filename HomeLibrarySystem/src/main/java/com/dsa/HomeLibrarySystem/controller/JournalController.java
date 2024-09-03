package com.dsa.HomeLibrarySystem.controller;

import com.dsa.HomeLibrarySystem.model.Journal;
import com.dsa.HomeLibrarySystem.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journals")
public class JournalController {

    private final JournalService journalService;

    @Autowired
    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping
    public ResponseEntity<List<Journal>> getAllJournals() {
        List<Journal> journals = journalService.getAllJournals();
        return new ResponseEntity<>(journals, HttpStatus.OK);
    }

    @GetMapping("/searchJournals")
    public ResponseEntity<List<Journal>> searchJournals(@RequestParam String query) {
        List<Journal> journals = journalService.searchJournals(query);
        return new ResponseEntity<>(journals, HttpStatus.OK);
    }

    @GetMapping("/journals/{id}")
    public String viewJournalDetails(@PathVariable Long id, Model model) {
        Optional<Journal> journal = journalService.getJournalById(id);
        if (journal.isPresent()) {
            model.addAttribute("journal", journal.get());
            return "journal-details";
        } else {
            return "error"; // handle the error case appropriately
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Journal> getJournalById(@PathVariable Long id) {
        Optional<Journal> journal = journalService.getJournalById(id);
        return journal.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

//    @PostMapping
//    public ResponseEntity<Journal> createJournal(@RequestBody Journal journal) {
//        Journal savedJournal = journalService.saveOrUpdateJournal(journal);
//        return new ResponseEntity<>(savedJournal, HttpStatus.CREATED);
//    }

    @PostMapping("/add")
    public ResponseEntity<Journal> addJournal(@RequestBody Journal journal) {
        Journal savedJournal = journalService.saveOrUpdateJournal(journal);
        return new ResponseEntity<>(savedJournal, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Journal> updateJournal(@PathVariable Long id, @RequestBody Journal journal) {
        journal.setId(id);
        Journal updatedJournal = journalService.saveOrUpdateJournal(journal);
        return new ResponseEntity<>(updatedJournal, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJournal(@PathVariable Long id) {
        journalService.deleteJournalById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
