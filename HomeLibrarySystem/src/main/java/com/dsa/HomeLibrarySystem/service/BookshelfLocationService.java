package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.BookshelfLocation;
import com.dsa.HomeLibrarySystem.model.Book;
import com.dsa.HomeLibrarySystem.repository.BookRepository;
import com.dsa.HomeLibrarySystem.repository.BookshelfLocationRepository;
import com.dsa.HomeLibrarySystem.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.*;

@Service
public class BookshelfLocationService {

    private final BookshelfLocationRepository bookshelfLocationRepository;
    private final BookRepository bookRepository;
    private final JournalRepository journalRepository;
    private BookshelfLocationService bookshelfLocationService;
    private final Random random = new Random();


    @Autowired
    public BookshelfLocationService(BookshelfLocationRepository bookshelfLocationRepository, BookRepository bookRepository, JournalRepository journalRepository) {
        this.bookshelfLocationRepository = bookshelfLocationRepository;
        this.bookRepository = bookRepository;
        this.journalRepository = journalRepository;
    }

    @PostConstruct
    public void init() {
        List<BookshelfLocation> locations = Arrays.asList(
                new BookshelfLocation(1L, "Aisle 1", "Row 1"),
                new BookshelfLocation(2L, "Aisle 2", "Row 2"),
                new BookshelfLocation(3L, "Aisle 3", "Row 3"),
                new BookshelfLocation(4L, "Aisle 4", "Row 1"),
                new BookshelfLocation(5L, "Aisle 5", "Row 2"),
                new BookshelfLocation(6L, "Aisle 1", "Row 3"),
                new BookshelfLocation(7L, "Aisle 2", "Row 1"),
                new BookshelfLocation(8L, "Aisle 3", "Row 2"),
                new BookshelfLocation(9L, "Aisle 4", "Row 3"),
                new BookshelfLocation(10L, "Aisle 5", "Row 1"),
                new BookshelfLocation(11L, "Aisle 1", "Row 2"),
                new BookshelfLocation(12L, "Aisle 2", "Row 3"),
                new BookshelfLocation(13L, "Aisle 3", "Row 1"),
                new BookshelfLocation(14L, "Aisle 4", "Row 2"),
                new BookshelfLocation(15L, "Aisle 5", "Row 3"),
                new BookshelfLocation(16L, "Aisle 6", "Row 4"),
                new BookshelfLocation(17L, "Aisle 7", "Row 5"),
                new BookshelfLocation(18L, "Aisle 8", "Row 1"),
                new BookshelfLocation(19L, "Aisle 9", "Row 2"),
                new BookshelfLocation(20L, "Aisle 10", "Row 3"),
                new BookshelfLocation(21L, "Aisle 6", "Row 2"),
                new BookshelfLocation(22L, "Aisle 7", "Row 3"),
                new BookshelfLocation(23L, "Aisle 8", "Row 4"),
                new BookshelfLocation(24L, "Aisle 9", "Row 5"),
                new BookshelfLocation(25L, "Aisle 10", "Row 1"),
                new BookshelfLocation(26L, "Aisle 6", "Row 3"),
                new BookshelfLocation(27L, "Aisle 7", "Row 1"),
                new BookshelfLocation(28L, "Aisle 8", "Row 2"),
                new BookshelfLocation(29L, "Aisle 9", "Row 3"),
                new BookshelfLocation(30L, "Aisle 10", "Row 4"),
                new BookshelfLocation(31L, "Aisle 6", "Row 5"),
                new BookshelfLocation(32L, "Aisle 7", "Row 4"),
                new BookshelfLocation(33L, "Aisle 8", "Row 5"),
                new BookshelfLocation(34L, "Aisle 9", "Row 1"),
                new BookshelfLocation(35L, "Aisle 10", "Row 2"),
                new BookshelfLocation(36L, "Aisle 11", "Row 3"),
                new BookshelfLocation(37L, "Aisle 12", "Row 4"),
                new BookshelfLocation(38L, "Aisle 13", "Row 5"),
                new BookshelfLocation(39L, "Aisle 14", "Row 1"),
                new BookshelfLocation(40L, "Aisle 15", "Row 2"),
                new BookshelfLocation(41L, "Aisle 11", "Row 1"),
                new BookshelfLocation(42L, "Aisle 12", "Row 2"),
                new BookshelfLocation(43L, "Aisle 13", "Row 3"),
                new BookshelfLocation(44L, "Aisle 14", "Row 4"),
                new BookshelfLocation(45L, "Aisle 15", "Row 5"),
                new BookshelfLocation(46L, "Aisle 11", "Row 5"),
                new BookshelfLocation(47L, "Aisle 12", "Row 3"),
                new BookshelfLocation(48L, "Aisle 13", "Row 1"),
                new BookshelfLocation(49L, "Aisle 14", "Row 2"),
                new BookshelfLocation(50L, "Aisle 15", "Row 3")
        );

        for (BookshelfLocation location : locations) {
            Optional<BookshelfLocation> existingLocation = bookshelfLocationRepository
                    .findBySectionAndShelfNumberAndPosition(
                            location.getSection(),
                            location.getShelfNumber(),
                            location.getPosition()
                    );

            if (existingLocation.isEmpty()) {
                bookshelfLocationRepository.save(location);
            }
        }
    }

    public BookshelfLocation getRandomLocation() {
        List<BookshelfLocation> allLocations = bookshelfLocationRepository.findAll();
        if (allLocations.isEmpty()) {
            throw new NoSuchElementException("No bookshelf locations available");
        }
        int randomIndex = random.nextInt(allLocations.size());
        return allLocations.get(randomIndex);
    }

    public void setLocation(Long artifactId, Long locationId) {
        if (bookshelfLocationService.isLocationFull(locationId)) {
            throw new IllegalStateException("The selected location is full");
        }

        Optional<Book> bookOptional = bookRepository.findById(artifactId);
        Optional<BookshelfLocation> locationOptional = bookshelfLocationRepository.findById(locationId);

        if (bookOptional.isPresent() && locationOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setLocation(locationOptional.get());
            bookRepository.save(book);
        }
    }

    public boolean isLocationFull(Long locationId) {
        BookshelfLocation location = bookshelfLocationRepository.findById(locationId).orElse(null);
        if (location != null) {
            long count = bookRepository.countByLocationId(locationId) +
                    journalRepository.countByLocationId(locationId);
            // Assuming each location can hold 50 items
            return count >= 50;
        }
        return false;
    }

    public List<BookshelfLocation> getAllBookshelfLocations() {
        return bookshelfLocationRepository.findAll();
    }

    public Optional<BookshelfLocation> getBookshelfLocationById(Long bookshelfLocationId) {
        return bookshelfLocationRepository.findById(bookshelfLocationId);
    }

    public BookshelfLocation saveOrUpdateBookshelfLocation(BookshelfLocation bookshelfLocation) {
        return bookshelfLocationRepository.save(bookshelfLocation);
    }

    public void deleteBookshelfLocationById(Long bookshelfLocationId) {
        bookshelfLocationRepository.deleteById(bookshelfLocationId);
    }

    public Optional<BookshelfLocation> getBookshelfLocation(String section, Long shelfNumber, String position) {
        return bookshelfLocationRepository.findBySectionAndShelfNumberAndPosition(section, shelfNumber, position);
    }
}
