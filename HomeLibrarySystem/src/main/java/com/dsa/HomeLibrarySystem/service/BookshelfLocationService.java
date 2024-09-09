package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.BookshelfLocation;
import com.dsa.HomeLibrarySystem.model.Book;
import com.dsa.HomeLibrarySystem.repository.BookRepository;
import com.dsa.HomeLibrarySystem.repository.BookshelfLocationRepository;
import com.dsa.HomeLibrarySystem.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookshelfLocationService {

    private final BookshelfLocationRepository bookshelfLocationRepository;
    private final BookRepository bookRepository;
    private final JournalRepository journalRepository;
    private BookshelfLocationService bookshelfLocationService;


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
                new BookshelfLocation(15L, "Aisle 5", "Row 3")
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
