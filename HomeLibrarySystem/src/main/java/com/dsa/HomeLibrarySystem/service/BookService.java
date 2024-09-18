package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.*;
import com.dsa.HomeLibrarySystem.repository.*;
import jakarta.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.dsa.HomeLibrarySystem.service.AuthorService.getOrCreateAuthor;


@Service
public class BookService {

    private static final String API_URL_TEMPLATE = "https://www.googleapis.com/books/v1/volumes?q=%s";
    private final BookRepository bookRepository;
    private final BookshelfLocationService bookshelfLocationService;
    private RestTemplate restTemplate;

    @Autowired
    public BookService(BookRepository bookRepository, BookshelfLocationService bookshelfLocationService) {
        this.bookRepository = bookRepository;
        this.bookshelfLocationService = bookshelfLocationService;
        this.restTemplate = new RestTemplate();
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    public void deleteBookById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public Book saveOrUpdateBook(Book book) {
        Optional<Book> existingBook = bookRepository.findByISBN(book.getISBN());
        if (existingBook.isPresent()) {
            Book updatedBook = existingBook.get();
            updateBookFields(updatedBook, book);
            return bookRepository.save(updatedBook);
        } else {
            return bookRepository.save(book);
        }
    }

    private void updateBookFields(Book existingBook, Book newBook) {
        existingBook.setTitle(newBook.getTitle());
        existingBook.setAuthors(newBook.getAuthors());
        existingBook.setTotalCopies(newBook.getTotalCopies());
        existingBook.setAvailableCopies(newBook.getAvailableCopies());
        existingBook.setLanguage(newBook.getLanguage());
        existingBook.setYear(newBook.getYear());
        existingBook.setLocation(newBook.getLocation());
        // Don't update ISBN as it's unique and shouldn't change
    }

    public List<Book> fetchBooks(String query) {
        String url = String.format(API_URL_TEMPLATE, (query != null ? query : "subject:fiction"));
        String response = restTemplate.getForObject(url, String.class);
        List<Book> books = new ArrayList<>();

        JSONObject jsonResponse = new JSONObject(response);
        JSONArray items = jsonResponse.optJSONArray("items");
        if (items != null) {
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                String isbn = extractISBN(volumeInfo);
                if (isbn == null) continue;
                Book book = new Book();
                book.setTitle(volumeInfo.optString("title"));
                book.setAuthors(extractAuthors(volumeInfo));
                book.setISBN(isbn);
                book.setType("book");
                book.setTotalCopies(generateRandomCopies());
                book.setAvailableCopies(book.getTotalCopies());
                book.setLanguage(volumeInfo.optString("language", "unknown"));
                book.setYear(volumeInfo.optString("publishedDate", "unknown").substring(0, 4));
                book.setLocation(bookshelfLocationService.getRandomLocation());

                books.add(saveOrUpdateBook(book));
            }
        }
        return books;
    }

    private String extractISBN(JSONObject volumeInfo) {
        JSONArray industryIdentifiers = volumeInfo.optJSONArray("industryIdentifiers");
        if (industryIdentifiers != null) {
            for (int j = 0; j < industryIdentifiers.length(); j++) {
                JSONObject identifier = industryIdentifiers.getJSONObject(j);
                if (identifier.getString("type").equals("ISBN_13")) {
                    try {
                        return identifier.getString("identifier");
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
        return null;
    }

    private List<Author> extractAuthors(JSONObject volumeInfo) {
        List<Author> authors = new ArrayList<>();
        JSONArray authorsArray = volumeInfo.optJSONArray("authors");
        if (authorsArray != null) {
            for (int j = 0; j < authorsArray.length(); j++) {
                String authorName = authorsArray.getString(j);
                authors.add(getOrCreateAuthor(authorName));
            }
        }
        return authors;
    }

    private int generateRandomCopies() {
        return new Random().nextInt(10) + 1;
    }

    public void setLocation(Long bookId, Long locationId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Optional<BookshelfLocation> locationOptional = bookshelfLocationService.getBookshelfLocationById(locationId);

        if (bookOptional.isPresent() && locationOptional.isPresent()) {
            Book book = bookOptional.get();
            BookshelfLocation location = locationOptional.get();

            book.setLocation(location);
            bookRepository.save(book);
        }
    }

    // Search method for books
    public List<Book> searchBooks(String query) {
        List<Book> books;
        if (query != null && !query.isEmpty()) {
            books = bookRepository.findByTitleContainingIgnoreCaseWithDetails(query);
        } else {
            books = bookRepository.findAllWithDetails();
        }
        return books.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private Book convertToDTO(Book book) {
        Book dto = new Book();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setISBN(book.getISBN());
        dto.setAuthors(book.getAuthors());
        if (book.getLocation() != null) {
            dto.setLocation(new BookshelfLocation(
                    book.getLocation().getShelfNumber(),
                    book.getLocation().getSection(),
                    book.getLocation().getPosition()
            ));
        }
        return dto;
    }

    public void addReview(Long bookId, String reviewContent, String reviewer) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            Review review = new Review();
            review.setContent(reviewContent);
            review.setReviewer(reviewer);
            book.getReviews().add(review);
            bookRepository.save(book);
        }
    }

    @Transactional
    public void populateDatabase() {
        List<Book> newBooks = fetchBooks("subject:fiction");
        List<Book> existingBooks = bookRepository.findAll();
        Map<String, Book> existingBooksMap = existingBooks.stream()
                .collect(Collectors.toMap(Book::getISBN, Function.identity()));

        List<Book> booksToSave = new ArrayList<>();
        for (Book newBook : newBooks) {
            Book bookToSave = existingBooksMap.getOrDefault(newBook.getISBN(), newBook);
            if (existingBooksMap.containsKey(newBook.getISBN())) {
                // Update existing book
                newBook.setType("book");
                newBook.setLocation(bookshelfLocationService.getRandomLocation());

                updateBookFields(bookToSave, newBook);
            } else {
                // New book
                bookToSave.setLocation(bookshelfLocationService.getRandomLocation());
            }
            booksToSave.add(bookToSave);
        }

        bookRepository.saveAll(booksToSave);
    }

    @Transactional
    public Book createBook(Book book) {
        // Validate the input
        if (book.getAuthors() == null || book.getAuthors().isEmpty()) {
            throw new IllegalArgumentException("At least one author name is required.");
        }
        // Create a new book instance
        Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setISBN(book.getISBN());
        newBook.setTotalCopies(book.getTotalCopies());
        newBook.setAvailableCopies(book.getAvailableCopies());
        newBook.setLanguage(book.getLanguage());
        newBook.setYear(book.getYear());

        newBook.setCensored(book.isCensored());

        newBook.setLocation(book.getLocation());
        // Create or retrieve the authors based on the provided names
        List<Author> authors = new ArrayList<>();
        for (Author author : book.getAuthors()) {
            String authorName = author.getName();
            if (authorName == null || authorName.isEmpty()) {
                throw new IllegalArgumentException("Author name is required.");
            }
            // Use the getOrCreateAuthor method
            authors.add(getOrCreateAuthor(authorName));
        }
        newBook.setAuthors(authors);
        // Save the new book to the database
        return bookRepository.save(newBook);
    }

}
