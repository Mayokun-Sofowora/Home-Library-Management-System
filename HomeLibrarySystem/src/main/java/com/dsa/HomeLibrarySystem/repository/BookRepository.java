package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
