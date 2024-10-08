package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);
}

