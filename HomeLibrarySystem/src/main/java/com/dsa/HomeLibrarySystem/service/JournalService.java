package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.BookshelfLocation;
import com.dsa.HomeLibrarySystem.model.Journal;
import com.dsa.HomeLibrarySystem.repository.BookshelfLocationRepository;
import com.dsa.HomeLibrarySystem.repository.JournalRepository;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JournalService {

    private static final String API_URL = "https://api.crossref.org/works?filter=type:journal-article";

    private final JournalRepository journalRepository;
    private final BookshelfLocationRepository bookshelfLocationRepository;

    @Autowired
    public JournalService(JournalRepository journalRepository, BookshelfLocationRepository bookshelfLocationRepository) {
        this.journalRepository = journalRepository;
        this.bookshelfLocationRepository = bookshelfLocationRepository;
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

    public List<Journal> fetchJournals() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(API_URL, String.class);
        System.out.println("Raw response: " + response); // Debug line

        List<Journal> journals = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject message = jsonResponse.getJSONObject("message");
            JSONArray items = message.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String ISSN = item.optString("ISSN");
                int volume = item.optInt("volume");
                int total = item.optInt("total");
                int available = item.optInt("available");
                int issue = item.optInt("issue");

                Journal journal = new Journal();
                journal.setTitle(item.optString("title"));
                journal.setISSN(ISSN);
                journal.setVolume(volume);
                journal.setTotalCopies(total);
                journal.setIssue(issue);
                journal.setAvailableCopies(available);

                journals.add(journal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return journals;
    }

    public void setLocation(Long artifactId, Long locationId) {
        Optional<Journal> journalOptional = journalRepository.findById(artifactId);
        Optional<BookshelfLocation> locationOptional = bookshelfLocationRepository.findById(locationId);

        if (journalOptional.isPresent() && locationOptional.isPresent()) {
            Journal journal = journalOptional.get();
            journal.setLocation(locationOptional.get());
            journalRepository.save(journal);
        }
    }

    public List<Journal> searchJournals(String query) {
        return journalRepository.findByTitleContainingIgnoreCase(query);
    }

    @Transactional
    public void populateDatabase() {
        List<Journal> newJournals = fetchJournals();
        List<Journal> existingJournals = journalRepository.findAll();
        Set<String> existingTitles = existingJournals.stream()
                .map(Journal::getTitle)
                .collect(Collectors.toSet());

        for (Journal journal : newJournals) {
            if (!existingTitles.contains(journal.getTitle())) {
                journalRepository.save(journal);
            } else {
                Optional<Journal> existingOptional = existingJournals.stream()
                        .filter(j -> j.getTitle().equals(journal.getTitle()))
                        .findFirst();

                if (existingOptional.isPresent()) {
                    Journal existing = existingOptional.get();
                    existing.setISSN(journal.getISSN());
                    existing.setVolume(journal.getVolume());
                    existing.setIssue(journal.getIssue());
                    existing.setTotalCopies(journal.getTotalCopies());
                    existing.setAvailableCopies(journal.getAvailableCopies());
                    existing.setLocation(journal.getLocation());
                    journalRepository.save(existing);
                }
            }
        }
    }
}
