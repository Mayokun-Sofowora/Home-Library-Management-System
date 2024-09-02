package com.dsa.HomeLibrarySystem.controller;

import com.dsa.HomeLibrarySystem.model.BookshelfLocation;
import com.dsa.HomeLibrarySystem.service.BookshelfLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookshelf-locations")
public class BookshelfLocationController {

    private final BookshelfLocationService bookshelfLocationService;

    @Autowired
    public BookshelfLocationController(BookshelfLocationService bookshelfLocationService) {
        this.bookshelfLocationService = bookshelfLocationService;
    }

    @GetMapping
    public ResponseEntity<List<BookshelfLocation>> getAllBookshelfLocations() {
        List<BookshelfLocation> bookshelfLocations = bookshelfLocationService.getAllBookshelfLocations();
        return new ResponseEntity<>(bookshelfLocations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookshelfLocation> getBookshelfLocationById(@PathVariable Long id) {
        Optional<BookshelfLocation> bookshelfLocation = bookshelfLocationService.getBookshelfLocationById(id);
        return bookshelfLocation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<BookshelfLocation> createBookshelfLocation(@RequestBody BookshelfLocation bookshelfLocation) {
        BookshelfLocation savedBookshelfLocation = bookshelfLocationService.saveOrUpdateBookshelfLocation(bookshelfLocation);
        return new ResponseEntity<>(savedBookshelfLocation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookshelfLocation> updateBookshelfLocation(@PathVariable Long id, @RequestBody BookshelfLocation bookshelfLocation) {
        bookshelfLocation.setId(id);
        BookshelfLocation updatedBookshelfLocation = bookshelfLocationService.saveOrUpdateBookshelfLocation(bookshelfLocation);
        return new ResponseEntity<>(updatedBookshelfLocation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookshelfLocation(@PathVariable Long id) {
        bookshelfLocationService.deleteBookshelfLocationById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
