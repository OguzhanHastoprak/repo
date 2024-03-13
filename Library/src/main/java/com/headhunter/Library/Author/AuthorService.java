package com.headhunter.Library.Author;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAuthors() {
        return this.authorRepository.findAll();
    }

    public Optional<Author> findById(Long requestedId) {
        return this.authorRepository.findById(requestedId);
    }

    public Author createAuthor(AuthorRequest authorRequest) {
        Author author = new Author(authorRequest.firstName(), authorRequest.lastName());
        return this.authorRepository.save(author);
    }

    public Optional<Author> updateAuthor(Long requestedId, AuthorRequest authorRequest) {
        Optional<Author> author = this.authorRepository.findById(requestedId);
        if (author.isEmpty())
            return Optional.empty();
        Author updatedAuthor = new Author(requestedId, authorRequest.firstName(), authorRequest.firstName());
        return Optional.of(this.authorRepository.save(updatedAuthor));
    }

    public boolean deleteAuthor(Long requestedId) {
        if (!this.authorRepository.existsById(requestedId))
            return false;
        this.authorRepository.deleteById(requestedId);
        return true;
    }
}
