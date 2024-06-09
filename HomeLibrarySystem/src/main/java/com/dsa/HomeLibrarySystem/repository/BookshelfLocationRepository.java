package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.BookshelfLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookshelfLocationRepository extends JpaRepository<BookshelfLocation, Long> {
}
