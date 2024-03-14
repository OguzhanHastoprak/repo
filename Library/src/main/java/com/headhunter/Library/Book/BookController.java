package com.headhunter.Library.Book;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(this.bookService.getBooks());
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<Book> findById(@PathVariable Long requestedId) {
        return this.bookService.findById(requestedId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookRequest bookRequest, UriComponentsBuilder uriComponentsBuilder) {
        Book savedBook = this.bookService.createBook(bookRequest);
        URI locationOfSavedBook = uriComponentsBuilder
                .path("api/v1/book/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(locationOfSavedBook).body(savedBook);
    }

    @PutMapping("/{requestedId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long requestedId, @RequestBody BookRequest bookRequest) {
        return this.bookService.updateBook(requestedId, bookRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{requestedId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long requestedId) {
        return this.bookService.deleteBook(requestedId) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}
