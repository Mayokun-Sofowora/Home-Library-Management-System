package com.dsa.HomeLibrarySystem.component;

import com.dsa.HomeLibrarySystem.service.BookService;
import com.dsa.HomeLibrarySystem.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookService bookService;

    private final JournalService journalService;

    public DataLoader(BookService bookService, JournalService journalService) {
        this.bookService = bookService;
        this.journalService = journalService;
    }

    @Override
    public void run(String... args) throws Exception {
        bookService.populateDatabase();
        journalService.populateDatabase();
    }
}
