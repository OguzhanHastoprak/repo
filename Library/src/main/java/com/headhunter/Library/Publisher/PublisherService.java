package com.headhunter.Library.Publisher;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> getPublishers() {
        return this.publisherRepository.findAll();
    }

    public Optional<Publisher> findById(Long requestedId) {
        return this.publisherRepository.findById(requestedId);
    }

    public Publisher createPublisher(PublisherRequest publisherRequest) {
        Publisher publisher = new Publisher(publisherRequest.name());
        return this.publisherRepository.save(publisher);
    }

    public Optional<Publisher> updatePublisher(Long requestedId, PublisherRequest publisherRequest) {
        Optional<Publisher> publisher = this.publisherRepository.findById(requestedId);
        if (publisher.isEmpty())
            return Optional.empty();
        Publisher updatedPublisher = new Publisher(requestedId, publisherRequest.name());
        return Optional.of(this.publisherRepository.save(updatedPublisher));
    }

    public boolean deletePublisher(Long requestedId) {
        if (!this.publisherRepository.existsById(requestedId))
            return false;
        this.publisherRepository.deleteById(requestedId);
        return true;
    }
}
