package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.Author;
import com.dsa.HomeLibrarySystem.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private static AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        AuthorService.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public static Author getOrCreateAuthor(String authorName) {
        return authorRepository.findByName(authorName)
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(authorName);
                    return authorRepository.save(newAuthor);
                });
    }

    public Optional<Author> getAuthorById(Long authorId) {
        return authorRepository.findById(authorId);
    }

    public Author saveOrUpdateAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthorById(Long authorId) {
        authorRepository.deleteById(authorId);
    }
}
