/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.dsa.homelibrary_db;

import entities.*;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * Main class to demonstrate the persistence of authors and books.
 */
public class Main {
    public static void main(String[] args) {
        // Create and persist a few authors
        Author author1 = new Author();
        author1.setName("C.S Lewis");
        author1.setCountry("United States");
        
        Author author2 = new Author();
        author2.setName("Virginia Woolf");
        author2.setCountry("England");
        
        Author author3 = new Author();
        author2.setName("Fyodor Dostoevsky");
        author2.setCountry("Russia");
        
        Author author4 = new Author();
        author4.setName("J.K. Rowling");
        author4.setCountry("United Kingdom");
        
        Main main = new Main();
        
        main.persistObject(author1);
        main.persistObject(author2);
        main.persistObject(author3);
        main.persistObject(author4);
        
        // Create and persist a few books
        Book book1 = new Book();
        book1.setTitle("Crime and Punishment");
        book1.setISBN(101037783);
        book1.setTotalCopies(13);
        book1.setAvailableCopies(7);
        book1.setReviews(List.of("Excellent!", "Very good", 
                                 "A compelling story with well-developed characters.", 
                                 "Thoroughly enjoyed the intricate plot twists.", 
                                 "An engaging read that I couldn't put down!", 
                                 "Would highly recommend to anyone who loves a good mystery."));
        book1.setAuthors(List.of(author1)); // Fyodor Dostoevsky
        book1.setGenres(Set.of("Classic", "Psychological Fiction", "Mystery"));

        Book book2 = new Book();
        book2.setTitle("The Chronicles of Narnia");
        book2.setISBN(980021751);
        book2.setTotalCopies(11);
        book2.setAvailableCopies(5);
        book2.setReviews(List.of("A magical journey with unforgettable characters.",
                                 "Captivating story that kept me hooked from beginning to end.",
                                 "An absolute classic! Must read for all ages.",
                                 "Beautifully written, with a profound sense of wonder.",
                                 "A timeless tale of adventure and courage."));
        book2.setAuthors(List.of(author1)); // C.S. Lewis
        book2.setGenres(Set.of("Fantasy", "Children's literature"));
        
        Book book3 = new Book();
        book3.setTitle("Mrs. Dalloway");
        book3.setISBN(281048121);
        book3.setTotalCopies(5);
        book3.setAvailableCopies(2);
        book3.setReviews(List.of("Engaging narrative.", "Classic literature."));
        book3.setAuthors(List.of(author3)); // Virginia Woolf
        book3.setGenres(Set.of("Literary fiction", "Classic"));
        
        Book book4 = new Book();
        book4.setTitle("Harry Potter and the Philosopher's Stone");
        book4.setISBN(147476709);
        book4.setTotalCopies(10);
        book4.setAvailableCopies(7);
        book4.setReviews(List.of("A magical start to an epic series!", 
                                 "Loved every bit of it. A true classic."));
        book4.setAuthors(List.of(author2)); // J.K. Rowling
        book4.setGenres(Set.of("Fantasy", "Adventure"));
        
        Book book5 = new Book();
        book5.setTitle("The Brothers Karamazov");
        book5.setISBN(246810121);
        book5.setTotalCopies(8);
        book5.setAvailableCopies(5);
        book5.setReviews(List.of("A profound exploration of faith and doubt.", 
                                 "Dostoevsky's masterpiece.", 
                                 "An intense and thought-provoking read."));
        book5.setAuthors(List.of(author1)); // Fyodor Dostoevsky
        book5.setGenres(Set.of("Classic", "Philosophical Fiction", "Drama"));

        main.persistObject(book1);
        main.persistObject(book2);
        main.persistObject(book3);
        main.persistObject(book4);
        main.persistObject(book5);
        
        // Create and persist a couple of journals
        Journal journal1 = new Journal();
        journal1.setTitle("Science Journal");
        journal1.setISSN("1234-5678");
        journal1.setVolume(10);
        journal1.setIssue(3);
        journal1.setAvailableCopies(100);

        Journal journal2 = new Journal();
        journal2.setTitle("Medical Journal");
        journal2.setISSN("9876-5432");
        journal2.setVolume(5);
        journal2.setIssue(2);
        journal2.setAvailableCopies(50);

        main.persistObject(journal1);
        main.persistObject(journal2);

        // Find and print authors
        main.findAuthors();

        // Find and print books
        main.findBooks();

        // Find and print journals
        main.findJournals();
    }

    public void persistObject(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.DSA_HomeLibrary_db_jar_PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void findAuthors() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.DSA_HomeLibrary_db_jar_PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Query query = em.createQuery("SELECT a FROM Author a");
            List<Author> authorList = query.getResultList();
            for (Author author : authorList) {
                System.out.println("Found author: " + author.getName() + ", Country: " + author.getCountry());
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void findBooks() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.DSA_HomeLibrary_db_jar_PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Query query = em.createQuery("SELECT b FROM Book b");
            List<Book> bookList = query.getResultList();
            for (Book book : bookList) {
                System.out.println("Found book: " + book.getTitle() + ", ISBN: " + book.getISBN() +
                        ", Total Copies: " + book.getTotalCopies() + ", Available Copies: " + book.getAvailableCopies() +
                        ", Reviews: " + book.getReviews());
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public void findJournals() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.DSA_HomeLibrary_db_jar_PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Query query = em.createQuery("SELECT j FROM Journal j");
            List<Journal> journalList = query.getResultList();
            for (Journal journal : journalList) {
                System.out.println("Found journal: " + journal.getTitle() + " with ISSN " + journal.getISSN());
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
