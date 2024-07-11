package com.dsa.homelibrary;

import com.dsa.homelibrary.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("default");
        createTestData(); // Initialize test data on servlet init
        System.out.println("test after creating test data");
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    private void createTestData() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Define authors
            Author author1 = new Author();
            author1.setName("George Orwell");
            author1.setCountry("United Kingdom");

            Author author2 = new Author();
            author2.setName("J.K. Rowling");
            author2.setCountry("United Kingdom");

            Author author3 = new Author();
            author3.setName("Harper Lee");
            author3.setCountry("United States");

            // Define genres
            Genre genre1 = new Genre();
            genre1.setName("Dystopian Fiction");

            Genre genre2 = new Genre();
            genre2.setName("Fantasy");

            Genre genre3 = new Genre();
            genre3.setName("Classic Literature");

            // Define bookshelf locations
            BookshelfLocation location1 = new BookshelfLocation();
            location1.setSection("A");
            location1.setShelfNumber(1);

            BookshelfLocation location2 = new BookshelfLocation();
            location2.setSection("B");
            location2.setShelfNumber(2);

            BookshelfLocation location3 = new BookshelfLocation();
            location3.setSection("C");
            location3.setShelfNumber(3);

            BookshelfLocation location4 = new BookshelfLocation();
            location4.setSection("D");
            location4.setShelfNumber(4);

            // Add authors to list
            List<Author> authors = new ArrayList<>();
            authors.add(author1);
            authors.add(author2);
            authors.add(author3);

            // Add genres to list
            List<Genre> genres = new ArrayList<>();
            genres.add(genre1);
            genres.add(genre2);
            genres.add(genre3);

            // Add bookshelf locations to list
            List<BookshelfLocation> bookshelfLocations = new ArrayList<>();
            bookshelfLocations.add(location1);
            bookshelfLocations.add(location2);
            bookshelfLocations.add(location3);
            bookshelfLocations.add(location4);

            // Add articles to list
            List<String> articles = new ArrayList<>();
            articles.add("Article 1");
            articles.add("Article 2");
            articles.add("Article 3");

            // Add reviews to list
            List<String> reviews = new ArrayList<>();
            reviews.add("Great read, highly recommended!");
            reviews.add("Captivating storyline, couldn't put it down!");
            reviews.add("Interesting characters, but slow pacing.");
            reviews.add("Intriguing plot twists, kept me guessing till the end.");

            // Create books
            Book book1 = new Book();
            book1.setTitle("1984");
            book1.setISBN("9780451524935");
            book1.setAvailableCopies(12);
            book1.setLocation(location1);
            book1.setAuthors(authors);
            book1.setGenres(genres);
            book1.setIsCensored(false);
            book1.setReviews(reviews);
            book1.setLanguage("Spanish");
            book1.setPublicationYear(1949);
            book1.setTotalCopies(2);
            book1.setType("Book");

            Book book2 = new Book();
            book2.setTitle("Harry Potter and the Philosopher's Stone");
            book2.setISBN("9780747532743");
            book2.setAvailableCopies(3);
            book2.setLocation(location2);
            book2.setAuthors(authors);
            book2.setGenres(genres);
            book2.setIsCensored(false);
            book2.setReviews(reviews);
            book2.setLanguage("French");
            book2.setPublicationYear(1997);
            book2.setTotalCopies(2);
            book2.setType("Book");

            Book book3 = new Book();
            book3.setTitle("To Kill a Mockingbird");
            book3.setISBN("9780061120084");
            book3.setAvailableCopies(5);
            book3.setLocation(location3);
            book3.setAuthors(authors);
            book3.setGenres(genres);
            book3.setIsCensored(false);
            book3.setReviews(reviews);
            book3.setLanguage("English");
            book3.setPublicationYear(1960);
            book3.setTotalCopies(2);
            book3.setType("Book");

            // Create journal
            Journal journal = new Journal();
            journal.setTitle("Science Journal");
            journal.setISSN("12345678");
            journal.setVolume(1);
            journal.setLanguage("English");
            journal.setPublicationYear(2024);
            journal.setAvailableCopies(1);
            journal.setType("Science-Journal");
            journal.setLocation(location4);
            journal.setAuthors(authors);
            journal.setGenres(genres);
            journal.setArticles(articles);
            journal.setLanguage("English");

            // Define a member
            Member member = new Member();
            member.setName("John Doe");
            member.setEmail("john.doe@email.com");

            // Add loans to list
            List<Loan> loans = new ArrayList<>();

            // Create loans
            Loan loan1 = new Loan();
            loan1.setLoanDate(new Date());
            loan1.setReturnDate(new Date());
            loan1.setBorrower(member);
            loan1.setItem(book1);
            loans.add(loan1);

            Loan loan2 = new Loan();
            loan2.setLoanDate(new Date());
            loan2.setReturnDate(new Date());
            loan2.setBorrower(member);
            loan2.setItem(book2);
            loans.add(loan2);

            Loan loan3 = new Loan();
            loan3.setLoanDate(new Date());
            loan3.setReturnDate(new Date());
            loan3.setBorrower(member);
            loan3.setItem(book3);
            loans.add(loan3);

            // Persist entities
            em.persist(author1);
            em.persist(author2);
            em.persist(author3);

            em.persist(genre1);
            em.persist(genre2);
            em.persist(genre3);

            em.persist(location1);
            em.persist(location2);
            em.persist(location3);
            em.persist(location4);

            em.persist(book1);
            em.persist(book2);
            em.persist(book3);

            em.persist(journal);

            em.persist(member);

            em.persist(loan1);
            em.persist(loan2);
            em.persist(loan3);

            System.out.println("Test flush mode: "+em.getFlushMode().toString());
            if(em.contains(book1))
                System.out.println("Yes, book1 exists");
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();
        try {
            List<Book> books = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
            List<Genre> genres = em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();

            request.setAttribute("books", books);
            request.setAttribute("genres", genres);
            request.getRequestDispatcher("/books.jsp").forward(request, response);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
