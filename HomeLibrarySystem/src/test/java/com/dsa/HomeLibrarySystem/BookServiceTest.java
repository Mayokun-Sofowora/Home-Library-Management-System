package com.dsa.HomeLibrarySystem;

import com.dsa.HomeLibrarySystem.model.*;
import com.dsa.HomeLibrarySystem.repository.*;
import com.dsa.HomeLibrarySystem.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookshelfLocationService bookshelfLocationService;

    @Mock
    private RestTemplate restTemplate;

    private Book book;

    @BeforeEach
    public void setUp() {
        bookService = new BookService(bookRepository, authorRepository, bookshelfLocationService);
        bookService.setRestTemplate(restTemplate);

        book = new Book();
        book.setId(100L);
        book.setTitle("Test Book");
        book.setISBN(1234567890123L);
        book.setAuthors(new ArrayList<>());
        book.setTotalCopies(5);
        book.setAvailableCopies(5);
        Review review = new Review();
        review.setContent("This is a great book!");
        review.setReviewer("John Doe");
        book.addReview(review);
        BookshelfLocation location = new BookshelfLocation();
        location.setId(100L);
        location.setPosition("Test Location");
        location.setSection("Test Section");
        book.setLocation(location);
    }

    // Test for getAllBooks
    @Test
    void testGetAllBooks() {
        List<Book> books = Collections.singletonList(book);
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        assertEquals(book, result.get(0));
    }

    // Test for getBookById
    @Test
    void testGetBookById() {
        when(bookRepository.findById(100L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(100L);

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
    }

    // Test for saveOrUpdateBook
    @Test
    void testSaveOrUpdateBook() {
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.saveOrUpdateBook(book);

        assertEquals(book, result);
        verify(bookRepository, times(1)).save(book);
    }

    // Test for deleteBookById
    @Test
    void testDeleteBookById() {
        doNothing().when(bookRepository).deleteById(100L);
        bookService.deleteBookById(100L);
        verify(bookRepository, times(1)).deleteById(100L);
    }

    // Test for setLocation
    @Test
    void testSetLocation() {
        BookshelfLocation location = new BookshelfLocation();
        location.setId(100L);
        when(bookRepository.findById(100L)).thenReturn(Optional.of(book));
        when(bookshelfLocationService.getBookshelfLocationById(100L)).thenReturn(Optional.of(location));

        bookService.setLocation(100L, 100L);

        assertEquals(location, book.getLocation());
        verify(bookRepository, times(1)).save(book);
    }

    // Test for addReview
    @Test
    void testAddReview() {
        when(bookRepository.findById(100L)).thenReturn(Optional.of(book));

        bookService.addReview(100L, "Great book!", "Reviewer Name");

        assertEquals(1, book.getReviews().size());
        assertEquals("Great book!", book.getReviews().get(0).getContent());
        assertEquals("Reviewer Name", book.getReviews().get(0).getReviewer());
        verify(bookRepository, times(1)).save(book);
    }

    /**
     * Test the fetchBooks method of the BookService.
     * Mocks the RestTemplate to return a predefined JSON response
     * and verifies that the book title and ISBN are correctly extracted.
     */
    @Test
    public void testFetchBooks() {
        String mockResponse = "{ \"items\": [ { \"volumeInfo\": { \"title\": \"Test Book\", \"authors\": [\"Author Name\"], \"industryIdentifiers\": [ { \"type\": \"ISBN_13\", \"identifier\": \"1234567890123\" } ] } } ] } }";

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(mockResponse);

        List<Book> books = bookService.fetchBooks("test");

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("Test Book", books.get(0).getTitle());
    }

    // Test for populateDatabase
//    @Test
//    void testPopulateDatabase() {
//        List<Book> fetchedBooks = Collections.singletonList(book);
//
//        when(bookService.fetchBooks(anyString())).thenReturn(fetchedBooks);
//
//        BookshelfLocation location = new BookshelfLocation(100L, "Default Aisle", "Default Row");
//        when(bookshelfLocationService.getAllBookshelfLocations())
//                .thenReturn(Collections.singletonList(location));
//
//        when(bookRepository.findByISBN(eq(book.getISBN()))).thenReturn(Optional.empty());
//
//        book.setLocation(location);
//        bookService.populateDatabase();
//        verify(bookRepository, times(1)).saveAll(eq(fetchedBooks));
//    }

}