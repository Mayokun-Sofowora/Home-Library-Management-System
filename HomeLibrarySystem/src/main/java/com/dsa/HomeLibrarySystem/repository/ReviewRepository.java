package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
