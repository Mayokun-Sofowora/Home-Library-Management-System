package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.BookshelfLocation;
import com.dsa.HomeLibrarySystem.repository.BookshelfLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookshelfLocationService {

    private final BookshelfLocationRepository bookshelfLocationRepository;

    @Autowired
    public BookshelfLocationService(BookshelfLocationRepository bookshelfLocationRepository) {
        this.bookshelfLocationRepository = bookshelfLocationRepository;
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
}

