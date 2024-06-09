package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.Journal;
import com.dsa.HomeLibrarySystem.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    private final JournalRepository journalRepository;

    @Autowired
    public JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    public List<Journal> getAllJournals() {
        return journalRepository.findAll();
    }

    public Optional<Journal> getJournalById(Long journalId) {
        return journalRepository.findById(journalId);
    }

    public Journal saveOrUpdateJournal(Journal journal) {
        return journalRepository.save(journal);
    }

    public void deleteJournalById(Long journalId) {
        journalRepository.deleteById(journalId);
    }
}

