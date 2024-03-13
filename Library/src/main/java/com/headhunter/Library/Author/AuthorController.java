package com.headhunter.Library.Author;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAuthors() {
        return ResponseEntity.ok(this.authorService.getAuthors());
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<Author> findById(@PathVariable Long requestedId) {
        return this.authorService.findById(requestedId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorRequest authorRequest, UriComponentsBuilder uriComponentsBuilder) {
        Author savedAuthor = this.authorService.createAuthor(authorRequest);
        URI locationOfSavedAuthor = uriComponentsBuilder
                .path("/api/v1/author/{id}")
                .buildAndExpand(savedAuthor.getId())
                .toUri();
        return ResponseEntity.created(locationOfSavedAuthor).body(savedAuthor);
    }

    @PutMapping("/{requestedId}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long requestedId, @RequestBody AuthorRequest authorRequest) {
        return this.authorService.updateAuthor(requestedId, authorRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{requestedId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long requestedId) {
        return this.authorService.deleteAuthor(requestedId) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}
