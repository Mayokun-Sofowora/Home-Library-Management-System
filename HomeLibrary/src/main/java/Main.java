import com.dsa.homelibrary.entity.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    private EntityManagerFactory emf;
    private EntityManager em;

    public static void main(String[] args) {
        Main main = new Main();
        main.initialize();

        main.createTestData();

        main.closeEntityManagerFactory();
    }

    public void initialize() {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
    }

    public void closeEntityManagerFactory() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    public void createTestData() {
        try {
            em.getTransaction().begin();
            List<Author> authors = new ArrayList<>();
            List<Genre> genres  = new ArrayList<>();
            List<BookshelfLocation> bookshelfLocations = new ArrayList<>();
            List<Loan> loans = new ArrayList<>();
            List<String> articles = new ArrayList<>();
            BookshelfLocation bsh = new BookshelfLocation(); bsh.setSection("A");bsh.setShelfNumber(1);
            BookshelfLocation bsh2 = new BookshelfLocation(); bsh2.setSection("B");bsh2.setShelfNumber(2);
            BookshelfLocation bsh3 = new BookshelfLocation(); bsh3.setSection("C");bsh3.setShelfNumber(3);
            BookshelfLocation bsh4 = new BookshelfLocation(); bsh4.setSection("D");bsh4.setShelfNumber(4);
            Author a = new Author();a.setName("John");a.setCountry("Poland");
            Author a2 = new Author();a2.setName("Joan");a2.setCountry("Poland");
            Genre g1 = new Genre();g1.setName("Action");
            Genre g2 = new Genre();g2.setName("Action2");
            authors.add(a);authors.add(a2);
            genres.add(g1);genres.add(g2);
            Book b = new Book();b.setLocation(bsh);b.setLocation(bsh);b.setISBN("132");b.setAvailableCopies(1);
            b.setTitle("Book 1");b.setAuthors(authors);b.setGenres(genres);
            Book b2 = new Book();b.setLocation(bsh2);b2.setISBN("3245");b2.setAvailableCopies(1);
            b2.setTitle("Book 2");b2.setAuthors(authors);b2.setGenres(genres);
            Journal j = new Journal(); j.setIsCensored(true);j.setISSN("123");j.setVolume(2);j.setLanguage("English");
            j.setTitle("Journal 1");j.setAuthors(authors);j.setGenres(genres);j.setPublicationYear(1999);
            j.setAvailableCopies(1);j.setType("Science-Journal");j.setLocation(bsh3);j.setLocation(bsh4);
            j.setArticles(articles);
            articles.add("Article 1");articles.add("Article 2");articles.add("Article 3");
            bookshelfLocations.add(bsh);bookshelfLocations.add(bsh2);bookshelfLocations.add(bsh3);bookshelfLocations.add(bsh4);

            Member m = new Member(); m.setEmail("John.doe@email.com");m.setName("John Doe");m.setLoans(loans);

            Date loanDate = new Date(2007, 0, 1, 8,23,23);
            Date dueDate = new Date(2017, 0, 1, 8,23,23);
            Loan l = new Loan();l.setLoanDate(loanDate);l.setReturnDate(dueDate);l.setBorrower(m);l.setItem(b);
            loans.add(l);
            //bsh.setArtifacts();
            em.persist(b);
            em.persist(b2);
            em.persist(j);
            em.persist(bsh);
            em.persist(m);
            em.persist(l);

            System.out.println("SSSSSSSSSSS "+em.getFlushMode().toString());
            if(em.contains(b))
                System.out.println("yes");
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
}
