package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
