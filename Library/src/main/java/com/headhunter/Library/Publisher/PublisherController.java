package com.headhunter.Library.Publisher;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> getPublishers() {
        return ResponseEntity.ok(this.publisherService.getPublishers());
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<Publisher> findById(@PathVariable Long requestedId) {
        return this.publisherService.findById(requestedId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody PublisherRequest publisherRequest, UriComponentsBuilder uriComponentsBuilder) {
        Publisher savedPublisher = this.publisherService.createPublisher(publisherRequest);
        URI locationOfSavedPublisher = uriComponentsBuilder
                .path("api/v1/publisher/{id}")
                .buildAndExpand(savedPublisher.getId())
                .toUri();
        return ResponseEntity.created(locationOfSavedPublisher).body(savedPublisher);
    }

    @PutMapping("/{requestedId}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long requestedId, @RequestBody PublisherRequest publisherRequest) {
        return this.publisherService.updatePublisher(requestedId, publisherRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{requestedId}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long requestedId) {
        return this.publisherService.deletePublisher(requestedId) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}
