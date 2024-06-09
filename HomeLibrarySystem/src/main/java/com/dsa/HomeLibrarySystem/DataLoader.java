package com.dsa.HomeLibrarySystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.dsa.HomeLibrarySystem.model.Book;
import com.dsa.HomeLibrarySystem.model.Author;
import com.dsa.HomeLibrarySystem.repository.BookRepository;
import com.dsa.HomeLibrarySystem.repository.AuthorRepository;

import java.util.Arrays;


@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Load books
        Book book1 = new Book();
        book1.setTitle("The Hunger Games");
        book1.setTotalCopies(1);
        book1.setISBN(8674446);
        book1.setAvailableCopies(5);
        book1.setType("book");
        book1.setId(1L);
        book1.setReviews(Arrays.asList("Great book!", "Must read!"));
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("To Kill a Mockingbird");
        book2.setTotalCopies(4);
        book2.setISBN(9045646);
        book2.setAvailableCopies(5);
        book2.setType("book");
        book2.setId(2L);
        book2.setReviews(Arrays.asList("Classic!", "A Masterpiece!"));
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setTitle("The Little Prince");
        book3.setTotalCopies(2);
        book3.setISBN(4535246);
        book3.setAvailableCopies(5);
        book3.setType("book");
        book3.setId(3L);
        book3.setReviews(Arrays.asList("Charming!", "A timeless classic!"));
        bookRepository.save(book3);

        // Load authors
        Author author1 = new Author();
        author1.setName("Suzanne Collins");
        author1.setCountry("United States");
        authorRepository.save(author1);

        Author author2 = new Author();
        author2.setName("Harper Lee");
        author2.setCountry("United States");
        authorRepository.save(author2);

        Author author3 = new Author();
        author3.setName("Antoine de Saint-Exupéry");
        author3.setCountry("France");
        authorRepository.save(author3);

    }
}
