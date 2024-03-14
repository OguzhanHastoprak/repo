package com.headhunter.Library.Book;

import com.headhunter.Library.User.User;
import com.headhunter.Library.User.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
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

    public boolean checkedOut(Long bookId, Integer userId) {
        Optional<Book> book = this.bookRepository.findById(bookId);
        Optional<User> user = this.userRepository.findById(userId);
        if (book.isEmpty() || user.isEmpty())
            return false;
        book.get().addBookOwner(user.get());
        return true;
    }
}
