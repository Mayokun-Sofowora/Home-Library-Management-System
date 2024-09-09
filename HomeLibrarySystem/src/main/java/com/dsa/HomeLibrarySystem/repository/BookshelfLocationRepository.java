package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.BookshelfLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookshelfLocationRepository extends JpaRepository<BookshelfLocation, Long> {
    // Find by section
    List<BookshelfLocation> findBySection(String section);

    // Find by section and shelf number
    List<BookshelfLocation> findBySectionAndShelfNumber(String section, Long shelfNumber);

    // Find by section, shelf number, and position
    Optional<BookshelfLocation> findBySectionAndShelfNumberAndPosition(String section, Long shelfNumber, String position);
}
