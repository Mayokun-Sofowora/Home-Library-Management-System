package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByISBN(Long ISBN);
    Optional<Book> findByLocationId(Long locationId);
    long countByLocationId(Long locationId);
}
