package com.headhunter.Library.Book;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.headhunter.Library.Author.Author;
import com.headhunter.Library.Publisher.Publisher;
import com.headhunter.Library.User.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @JsonBackReference
    private Publisher publisher;

    @ManyToMany
    @JoinTable(name = "Checkout",
    joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonBackReference
    private List<User> bookOwners;
}
