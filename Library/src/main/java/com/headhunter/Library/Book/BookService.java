package com.headhunter.Library.Book;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return this.bookRepository.findAll();
    }

    public Optional<Book> findById(Long requestedId) {
        return this.bookRepository.findById(requestedId);
    }

    public Book createBook(BookRequest bookRequest) {
        Book book = new Book(bookRequest.name(), bookRequest.author(), bookRequest.publisher());
        return this.bookRepository.save(book);
    }

    public Optional<Book> updateBook(Long requestedId, BookRequest bookRequest) {
        Optional<Book> book = this.bookRepository.findById(requestedId);
        if (book.isEmpty())
            return Optional.empty();
        Book updatedBook = new Book(requestedId, bookRequest.name(), bookRequest.author(), bookRequest.publisher());
        return Optional.of(this.bookRepository.save(updatedBook));
    }

    public boolean deleteBook(Long requestedId) {
        if(!this.bookRepository.existsById(requestedId))
            return false;
        this.bookRepository.deleteById(requestedId);
        return true;
    }
}
