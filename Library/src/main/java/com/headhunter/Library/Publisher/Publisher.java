package com.headhunter.Library.Publisher;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.headhunter.Library.Book.Book;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table
public class Publisher {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "publisher")
    @JsonManagedReference
    private List<Book> books;
}
