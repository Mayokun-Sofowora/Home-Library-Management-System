package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.Book;
import com.dsa.HomeLibrarySystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    public Book saveOrUpdateBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBookById(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
