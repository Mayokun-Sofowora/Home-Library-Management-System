package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.Genre;
import com.dsa.HomeLibrarySystem.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> getGenreById(Long genreId) {
        return genreRepository.findById(genreId);
    }

    public Genre saveOrUpdateGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public void deleteGenreById(Long genreId) {
        genreRepository.deleteById(genreId);
    }
}
