package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.Book;;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT DISTINCT b FROM Book b " +
                "LEFT JOIN FETCH b.authors " +
                "LEFT JOIN FETCH b.location " +
                "WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Book> findByTitleContainingIgnoreCaseWithDetails(@Param("query") String query);

    @Query("SELECT DISTINCT b FROM Book b " +
                "LEFT JOIN FETCH b.authors " +
                "LEFT JOIN FETCH b.location")
    List<Book> findAllWithDetails();

    Optional<Book> findByISBN(String ISBN);
    Optional<Book> findByLocationId(Long locationId);
    long countByLocationId(Long locationId);
    List<Book> findByTitleContainingIgnoreCase(String title);

}
