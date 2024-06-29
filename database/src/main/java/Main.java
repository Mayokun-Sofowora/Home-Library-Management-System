import java.util.Date;
import java.util.List;

import entity.*;
import jakarta.persistence.*;


/**
 *
 * Main class to demonstrate the persistence of authors and books.
 */
public class Main {
    public static void main(String[] args) {
        Main main = new Main();

        // Create and persist Genres
        Genre genre1 = new Genre(); genre1.setName("Classic");
        Genre genre2 = new Genre(); genre2.setName("Psychological Fiction");
        Genre genre3 = new Genre(); genre3.setName("Mystery");
        Genre genre4 = new Genre(); genre4.setName("Fantasy");
        Genre genre5 = new Genre(); genre5.setName("Children's literature");
        Genre genre6 = new Genre(); genre6.setName("Adventure");
        Genre genre7 = new Genre(); genre7.setName("Literary fiction");
        Genre genre8 = new Genre(); genre8.setName("Philosophical Fiction");
        Genre genre9 = new Genre(); genre9.setName("Drama");

        // Example: Retrieve existing books from the database
        List<BibliographicArtifact> artifacts = main.getAllBibliographicArtifactsFromDatabase();

//        for (BibliographicArtifact artifact : artifacts) {
//            if (artifact instanceof Author) {
//                // Handle Author specific logic
//            } else if (artifact instanceof Genre) {
//                // Handle Genre specific logic
//            }
//            // Add more conditions for other subclasses if needed
//        }
        // Add genre artifacts for each genre
        genre1.setGenreArtifacts(artifacts.subList(0, 2)); // Assuming first two artifacts are of Classic genre
        genre2.setGenreArtifacts(artifacts.subList(2, 4)); // Assuming next two artifacts are of Psychological Fiction genre
        genre3.setGenreArtifacts(artifacts.subList(4, 6)); // Assuming next two artifacts are of Mystery genre
        genre4.setGenreArtifacts(artifacts.subList(6, 8)); // Assuming next two artifacts are of Fantasy genre

        // Persist genres
        main.persistObject(genre1);
        main.persistObject(genre2);
        main.persistObject(genre3);
        main.persistObject(genre4);

        // Display confirmation message
        System.out.println("Genres added successfully!");

        main.persistObject(genre1);
        main.persistObject(genre2);
        main.persistObject(genre3);
        main.persistObject(genre4);
        main.persistObject(genre5);
        main.persistObject(genre6);
        main.persistObject(genre7);
        main.persistObject(genre8);
        main.persistObject(genre9);

        // Create and persist Authors
        Author author1 = new Author(); author1.setName("C.S Lewis"); author1.setCountry("United States");
        Author author2 = new Author(); author2.setName("Virginia Woolf"); author2.setCountry("England");
        Author author3 = new Author(); author3.setName("Fyodor Dostoevsky"); author3.setCountry("Russia");
        Author author4 = new Author(); author4.setName("J.K. Rowling"); author4.setCountry("United Kingdom");
        Author author5 = new Author(); author5.setName("Gabriel García Márquez"); author5.setCountry("Colombia");
        Author author6 = new Author(); author6.setName("Haruki Murakami"); author6.setCountry("Japan");

        // Add authored artifacts for each author
        author1.setAuthoredArtifacts(artifacts.subList(0, 2)); // Assuming first two artifacts are authored by C.S. Lewis
        author2.setAuthoredArtifacts(artifacts.subList(2, 4)); // Assuming next two artifacts are authored by Virginia Woolf
        author3.setAuthoredArtifacts(artifacts.subList(4, 6)); // Assuming next two artifacts are authored by Fyodor Dostoevsky

        // Persist authors
        main.persistObject(author1);
        main.persistObject(author2);
        main.persistObject(author3);
        main.persistObject(author4);
        main.persistObject(author5);
        main.persistObject(author6);

        // Display confirmation message
        System.out.println("Authors added successfully!");

        // Create and persist Bookshelf Locations
        BookshelfLocation location1 = new BookshelfLocation(); location1.setShelfNumber(1); location1.setSection("A"); location1.setPosition("Top");
        BookshelfLocation location2 = new BookshelfLocation(); location2.setShelfNumber(2); location2.setSection("B"); location2.setPosition("Middle");
        BookshelfLocation location3 = new BookshelfLocation(); location3.setShelfNumber(3); location3.setSection("C"); location3.setPosition("Bottom");
        BookshelfLocation location4 = new BookshelfLocation(); location4.setShelfNumber(1); location4.setSection("B"); location4.setPosition("Bottom");
        BookshelfLocation location5 = new BookshelfLocation(); location5.setShelfNumber(2); location5.setSection("A"); location5.setPosition("Middle");
        BookshelfLocation location6 = new BookshelfLocation(); location6.setShelfNumber(3); location6.setSection("C"); location6.setPosition("Middle");

        main.persistObject(location1);
        main.persistObject(location2);
        main.persistObject(location3);
        main.persistObject(location4);
        main.persistObject(location5);
        main.persistObject(location6);

        // Create and persist Books
        Book book1 = new Book();
        book1.setTitle("Crime and Punishment"); book1.setISBN(101037783); book1.setTotalCopies(13); book1.setAvailableCopies(7); book1.setLanguage("Russian");
        book1.setYear(1866); book1.setCensored(false); book1.setType("Novel"); book1.setReviews(List.of("Excellent!", "Very good"));
        book1.setAuthors(List.of(author3)); book1.setGenres(List.of(genre1, genre2, genre3)); book1.setLocation(location1);

        Book book2 = new Book();
        book2.setTitle("The Chronicles of Narnia"); book2.setISBN(980021751); book2.setTotalCopies(11); book2.setAvailableCopies(5); book2.setLanguage("English");
        book2.setYear(1950); book2.setCensored(false); book2.setType("Fantasy"); book2.setReviews(List.of("A magical journey with unforgettable characters."));
        book2.setAuthors(List.of(author1)); book2.setGenres(List.of(genre4, genre5)); book2.setLocation(location2);

        Book book3 = new Book();
        book3.setTitle("Mrs. Dalloway"); book3.setISBN(281048121); book3.setTotalCopies(5); book3.setAvailableCopies(2); book3.setLanguage("English");
        book3.setYear(1925); book3.setCensored(false); book3.setType("Novel"); book3.setReviews(List.of("Engaging narrative.", "Classic literature."));
        book3.setAuthors(List.of(author2)); book3.setGenres(List.of(genre7, genre1)); book3.setLocation(location6);

        Book book4 = new Book();
        book4.setTitle("Harry Potter and the Philosopher's Stone"); book4.setISBN(147476709); book4.setTotalCopies(10); book4.setAvailableCopies(7); book4.setLanguage("English");
        book4.setYear(1997); book4.setCensored(false); book4.setType("Fantasy"); book4.setReviews(List.of("A magical start to an epic series!"));
        book4.setAuthors(List.of(author4)); book4.setGenres(List.of(genre4, genre6)); book4.setLocation(location1);

        Book book5 = new Book();
        book5.setTitle("The Brothers Karamazov"); book5.setISBN(246810121); book5.setTotalCopies(8); book5.setAvailableCopies(5); book5.setLanguage("Russian");
        book5.setYear(1880); book5.setCensored(false); book5.setType("Novel"); book5.setReviews(List.of("A profound exploration of faith and doubt."));
        book5.setAuthors(List.of(author3)); book5.setGenres(List.of(genre1, genre8)); book5.setLocation(location2);

        Book book6 = new Book();
        book6.setTitle("One Hundred Years of Solitude"); book6.setISBN(369121518); book6.setTotalCopies(12); book6.setAvailableCopies(9); book6.setLanguage("Spanish");
        book6.setYear(1967); book6.setCensored(false); book6.setType("Magic Realism"); book6.setReviews(List.of("A masterpiece of magical realism."));
        book6.setAuthors(List.of(author5)); book6.setGenres(List.of(genre7, genre1)); book6.setLocation(location3);

        Book book7 = new Book();
        book7.setTitle("Kafka on the Shore"); book7.setISBN(741852963); book7.setTotalCopies(7); book7.setAvailableCopies(4); book7.setLanguage("Japanese");
        book7.setYear(2002); book7.setCensored(false); book7.setType("Fantasy"); book7.setReviews(List.of("A surreal and captivating story."));
        book7.setAuthors(List.of(author6)); book7.setGenres(List.of(genre4, genre2)); book7.setLocation(location4);

        main.persistObject(book1);
        main.persistObject(book2);
        main.persistObject(book3);
        main.persistObject(book4);
        main.persistObject(book5);
        main.persistObject(book6);
        main.persistObject(book7);

        // Create and persist Journals
        Journal journal1 = new Journal();
        journal1.setTitle("Science Journal"); journal1.setISSN("1234-5678"); journal1.setVolume(10); journal1.setIssue(3); journal1.setAvailableCopies(100); journal1.setLanguage("English");
        journal1.setYear(2023); journal1.setCensored(false); journal1.setType("Journal"); journal1.setLocation(location3);

        Journal journal2 = new Journal();
        journal2.setTitle("Medical Journal"); journal2.setISSN("9876-5432"); journal2.setVolume(5); journal2.setIssue(2); journal2.setAvailableCopies(50); journal2.setLanguage("English");
        journal2.setYear(2022); journal2.setCensored(false); journal2.setType("Journal"); journal2.setLocation(location1);

        Journal journal3 = new Journal();
        journal3.setTitle("Revista Científica"); journal3.setISSN("1111-2222"); journal3.setVolume(15); journal3.setIssue(4); journal3.setAvailableCopies(60); journal3.setLanguage("Spanish");
        journal3.setYear(2021); journal3.setCensored(false); journal3.setType("Journal"); journal3.setLocation(location2);

        Journal journal4 = new Journal();
        journal4.setTitle("Journal des Sciences"); journal4.setISSN("3333-4444"); journal4.setVolume(20); journal4.setIssue(5); journal4.setAvailableCopies(40); journal4.setLanguage("French");
        journal4.setYear(2020); journal4.setCensored(false); journal4.setType("Journal"); journal4.setLocation(location3);

        main.persistObject(journal1);
        main.persistObject(journal2);
        main.persistObject(journal3);
        main.persistObject(journal4);

        // Create and persist Members
        Member member1 = new Member(); member1.setName("John Doe"); member1.setEmail("john.doe@example.com");
        Member member2 = new Member(); member2.setName("Jane Smith"); member2.setEmail("jane.smith@example.com");
        Member member3 = new Member(); member3.setName("Carlos Mendoza");member3.setEmail("carlos.mendoza@example.com");

        main.persistObject(member1);
        main.persistObject(member2);
        main.persistObject(member3);

        // Create and persist Loans
        Loan loan1 = new Loan();
        loan1.setItem(book1); loan1.setBorrower(member1); loan1.setLoanDate(new Date()); loan1.setReturnDate(null);

        Loan loan2 = new Loan();
        loan2.setItem(journal1); loan2.setBorrower(member2); loan2.setLoanDate(new Date()); loan2.setReturnDate(null);

        main.persistObject(loan1);
        main.persistObject(loan2);

        // Find and print data
        main.findAuthors();
        main.findBooks();
        main.findJournals();
        main.findMembers();
        main.findLoans();
    }

    public void persistObject(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
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
            emf.close();
        }
    }

    public void findAuthors() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
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
            emf.close();
        }
    }

    public void findBooks() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
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
            emf.close();
        }
    }

    public void findJournals() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
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
            emf.close();
        }
    }

    public void findMembers() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            List<Member> members = em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
            for (Member member : members) {
                System.out.println(member);
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    public void findLoans() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            List<Loan> loans = em.createQuery("SELECT l FROM Loan l", Loan.class).getResultList();
            for (Loan loan : loans) {
                System.out.println(loan);
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    public List<BibliographicArtifact> getAllBibliographicArtifactsFromDatabase() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<BibliographicArtifact> artifacts = null;
        try {
            Query query = em.createQuery("SELECT ba FROM BibliographicArtifact ba");
            artifacts = query.getResultList();
            for (BibliographicArtifact artifact : artifacts) {
//                if (artifact instanceof Author) {
//                    ((Author) artifact).getAuthoredArtifacts().size(); // Initialize authoredArtifacts collection
//                } else if (artifact instanceof Genre) {
//                    ((Genre) artifact).getGenreArtifacts().size(); // Initialize genreArtifacts collection
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
        return artifacts;
    }

}
