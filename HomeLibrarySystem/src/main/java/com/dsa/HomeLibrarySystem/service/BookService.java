package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.*;
import com.dsa.HomeLibrarySystem.repository.AuthorRepository;
import com.dsa.HomeLibrarySystem.repository.BookRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BookService {

    private static final String API_URL_TEMPLATE = "https://www.googleapis.com/books/v1/volumes?q=%s";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookshelfLocationService bookshelfLocationService;
    private RestTemplate restTemplate;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookshelfLocationService bookshelfLocationService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
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

    public Book saveOrUpdateBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBookById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public void setLocation(Long bookId, Long locationId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Optional<BookshelfLocation> locationOptional = bookshelfLocationService.getBookshelfLocationById(locationId);

        if (bookOptional.isPresent() && locationOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setLocation(locationOptional.get());
            bookRepository.save(book);
        }
    }

    public Optional<Book> findBooksByLocation(Long locationId) {
        return bookRepository.findByLocationId(locationId);
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

    public List<Book> fetchBooks(String query) {
        String url = String.format(API_URL_TEMPLATE, (query != null ? query : "subject:fiction"));
        String response = restTemplate.getForObject(url, String.class);
        List<Book> books = new ArrayList<>();

        JSONObject jsonResponse = new JSONObject(response);
        JSONArray items = jsonResponse.optJSONArray("items");

        BookshelfLocation defaultLocation = bookshelfLocationService.getBookshelfLocationById(1L)
                .orElse(new BookshelfLocation(1L, "Default Aisle", "Default Row"));

        if (items != null) {
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                String title = volumeInfo.optString("title");

                JSONArray authorsArray = volumeInfo.optJSONArray("authors");
                List<Author> authors = new ArrayList<>();
                if (authorsArray != null) {
                    for (int j = 0; j < authorsArray.length(); j++) {
                        String authorName = authorsArray.getString(j);
                        Author author = authorRepository.findByName(authorName)
                                .orElseGet(() -> {
                                    Author newAuthor = new Author();
                                    newAuthor.setName(authorName);
                                    return authorRepository.save(newAuthor);
                                });
                        authors.add(author);
                    }
                }

                JSONArray industryIdentifiers = volumeInfo.optJSONArray("industryIdentifiers");
                Long isbn = null;
                if (industryIdentifiers != null) {
                    for (int j = 0; j < industryIdentifiers.length(); j++) {
                        JSONObject identifier = industryIdentifiers.getJSONObject(j);
                        if (identifier.getString("type").equals("ISBN_13")) {
                            try {
                                isbn = Long.parseLong(identifier.getString("identifier"));
                            } catch (NumberFormatException e) {
                                isbn = null;
                            }
                            break;
                        }
                    }
                }
                if (isbn == null) {
                    continue;
                }

                Random random = new Random();
                int totalCopies = random.nextInt(10) + 1;
                int availableCopies = totalCopies;

                Book book = new Book();
                book.setTitle(title);
                book.setAuthors(authors);
                book.setISBN(isbn);
                book.setTotalCopies(totalCopies);
                book.setAvailableCopies(availableCopies);
                book.setLocation(defaultLocation);
                books.add(book);
            }
        }

        return books;
    }

    public void populateDatabase() {
        List<Book> newBooks = fetchBooks("subject:fiction");
        List<BookshelfLocation> locations = bookshelfLocationService.getAllBookshelfLocations();
        Random random = new Random();

        List<Book> booksToSave = new ArrayList<>();
        for (Book book : newBooks) {
            Optional<Book> existingOptional = bookRepository.findByISBN(book.getISBN());
            if (existingOptional.isPresent()) {
                Book existing = existingOptional.get();
                existing.setTitle(book.getTitle());
                existing.setAuthors(book.getAuthors());
                booksToSave.add(existing);
            } else {
                book.setLocation(locations.get(random.nextInt(locations.size())));
                booksToSave.add(book);
            }
        }

        bookRepository.saveAll(booksToSave);
    }
}
