package com.headhunter.Library.Author;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.headhunter.Library.Book.Book;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table
public class Author {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "author")
    @JsonManagedReference
    private List<Book> books;
}
