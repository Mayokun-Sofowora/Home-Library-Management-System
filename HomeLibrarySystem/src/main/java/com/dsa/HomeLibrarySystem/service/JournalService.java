package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.*;
import com.dsa.HomeLibrarySystem.repository.*;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.dsa.HomeLibrarySystem.service.AuthorService.getOrCreateAuthor;

@Service
public class JournalService {

    private static final String API_URL = "https://api.crossref.org/works?filter=type:journal-article";

    private final JournalRepository journalRepository;
    private final BookshelfLocationRepository bookshelfLocationRepository;
    private final BookshelfLocationService bookshelfLocationService;
    private final AuthorRepository authorRepository;
    private RestTemplate restTemplate;

    @Autowired
    public JournalService(JournalRepository journalRepository, BookshelfLocationRepository bookshelfLocationRepository,
                          BookshelfLocationService bookshelfLocationService, AuthorRepository authorRepository) {
        this.journalRepository = journalRepository;
        this.bookshelfLocationRepository = bookshelfLocationRepository;
        this.bookshelfLocationService = bookshelfLocationService;
        this.authorRepository = authorRepository;
        this.restTemplate = new RestTemplate();

    }
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Journal> getAllJournals() {
        return journalRepository.findAll();
    }

    public Optional<Journal> getJournalById(Long journalId) {
        return journalRepository.findById(journalId);
    }

    public void deleteJournalById(Long journalId) { journalRepository.deleteById(journalId); }

    public List<Journal> searchJournals(String query) { return journalRepository.findByTitleContainingIgnoreCase(query);
    }

    public Journal saveOrUpdateJournal(Journal journal) {
        Optional<Journal> existingJournal = journalRepository.findByISSN(journal.getISSN());
        if (existingJournal.isPresent()) {
            Journal updatedJournal = existingJournal.get();
            updateJournalFields(updatedJournal, journal);
            return journalRepository.save(updatedJournal);
        } else {
            return journalRepository.save(journal);
        }
    }

    private void updateJournalFields(Journal existingJournal, Journal newJournal) {
        existingJournal.setTitle(newJournal.getTitle());
        existingJournal.setAuthors(newJournal.getAuthors());
        existingJournal.setVolume(newJournal.getVolume());
        existingJournal.setIssue(newJournal.getIssue());
        existingJournal.setTotalCopies(newJournal.getTotalCopies());
        existingJournal.setAvailableCopies(newJournal.getAvailableCopies());
        existingJournal.setLanguage(newJournal.getLanguage());
        existingJournal.setYear(newJournal.getYear());
        existingJournal.setLocation(newJournal.getLocation());
        // Don't update ISSN as it's unique and shouldn't change
    }

    public List<Journal> fetchJournals() {
        String response = restTemplate.getForObject(API_URL, String.class);
        List<Journal> journals = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray items = jsonResponse.getJSONObject("message").getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);

                String issn = extractISSN(item);
                if (issn == null) continue;

                Journal journal = new Journal();
                journal.setTitle(item.optString("title"));
                journal.setAuthors(extractAuthors(item));
                journal.setISSN(issn);
                journal.setType("journal");
                journal.setVolume(item.optInt("volume"));
                journal.setIssue(item.optInt("issue"));
                journal.setTotalCopies(generateRandomCopies());
                journal.setAvailableCopies(journal.getTotalCopies());
                journal.setLanguage(item.optString("language", "unknown"));
                // Extract and set year
                if (item.has("published-print") && item.getJSONObject("published-print").has("date-parts")) {
                    JSONArray dateParts = item.getJSONObject("published-print").getJSONArray("date-parts");
                    if (!dateParts.isEmpty()) {
                        JSONArray yearArray = dateParts.getJSONArray(0);
                        if (!yearArray.isEmpty()) {
                            int year = yearArray.getInt(0); // Extract year
                            journal.setYear(String.valueOf(year));
                        }
                    }
                } else {
                    journal.setYear("0"); // Set to 0 if year not found
                }
                journal.setLocation(bookshelfLocationService.getRandomLocation());
                journals.add(saveOrUpdateJournal(journal));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return journals;
    }

    private String extractISSN(JSONObject item) {
        JSONArray issnArray = item.optJSONArray("ISSN");
        if (issnArray != null && !issnArray.isEmpty()) {
            return issnArray.getString(0);
        }
        return null;
    }

    private List<Author> extractAuthors(JSONObject item) {
        List<Author> authors = new ArrayList<>();
        JSONArray authorsArray = item.optJSONArray("author");
        if (authorsArray != null) {
            for (int j = 0; j < authorsArray.length(); j++) {
                JSONObject authorObject = authorsArray.getJSONObject(j);
                String givenName = authorObject.optString("given", "");
                String familyName = authorObject.optString("family", "");
                String authorName = givenName + " " + familyName;
                authors.add(AuthorService.getOrCreateAuthor(authorName));
            }
        }
        return authors;
    }

    private int generateRandomCopies() {
        return new Random().nextInt(10) + 1;
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

    @Transactional
    public void populateDatabase() {
        List<Journal> newJournals = fetchJournals();
        List<Journal> existingJournals = journalRepository.findAll();
        Map<String, Journal> existingJournalsMap = existingJournals.stream()
                .collect(Collectors.toMap(Journal::getISSN, Function.identity()));

        List<Journal> journalsToSave = new ArrayList<>();
        for (Journal newJournal : newJournals) {
            Journal journalToSave = existingJournalsMap.getOrDefault(newJournal.getISSN(), newJournal);
            if (existingJournalsMap.containsKey(newJournal.getISSN())) {
                // Update existing journal
                newJournal.setType("journal");
                newJournal.setLocation(bookshelfLocationService.getRandomLocation());

                updateJournalFields(journalToSave, newJournal);
            } else {
                // New journal
                journalToSave.setLocation(bookshelfLocationService.getRandomLocation());
            }
            journalsToSave.add(journalToSave);
        }

        journalRepository.saveAll(journalsToSave);
    }

    @Transactional
    public Journal createJournal(Journal journal) {
        // Validate the input
        if (journal.getAuthors() == null || journal.getAuthors().isEmpty()) {
            throw new IllegalArgumentException("At least one author is required.");
        }

        // Create a new journal instance
        Journal newJournal = new Journal();
        newJournal.setTitle(journal.getTitle());
        newJournal.setISSN(journal.getISSN());
        newJournal.setLanguage(journal.getLanguage());
        newJournal.setTotalCopies(journal.getTotalCopies());
        newJournal.setAvailableCopies(journal.getAvailableCopies());

        newJournal.setYear(journal.getYear());

        newJournal.setCensored(journal.isCensored());
        newJournal.setLocation(journal.getLocation());

        // Fetch or create authors based on provided names
        List<Author> authors = new ArrayList<>();
        for (Author author : journal.getAuthors()) {
            String authorName = author.getName();
            if (authorName == null || authorName.isEmpty()) {
                throw new IllegalArgumentException("Author name is required.");
            }
            // Use the getOrCreateAuthor method
            authors.add(getOrCreateAuthor(authorName));
        }
        newJournal.setAuthors(authors);

        // Save the journal
        Journal savedJournal = journalRepository.save(newJournal);

        // Update the authors' list of authored artifacts
        for (Author author : authors) {
            author.getArtifacts().add(savedJournal);
            authorRepository.save(author);
        }

        return savedJournal;
    }
}
