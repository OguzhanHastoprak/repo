package com.headhunter.Library.Book;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.headhunter.Library.Author.Author;
import com.headhunter.Library.Publisher.Publisher;
import com.headhunter.Library.User.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonBackReference(value = "book-author")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @JsonBackReference(value = "book-publisher")
    private Publisher publisher;

    @ManyToMany
    @JoinTable(name = "Checkout",
            joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> bookOwners;

    public Book(String name, Author author, Publisher publisher) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
    }

    public Book(Long requestedId, String name, Author author, Publisher publisher) {
        this.id = requestedId;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
    }

    public void addBookOwner(User user){
        bookOwners.add(user);
    }
}
