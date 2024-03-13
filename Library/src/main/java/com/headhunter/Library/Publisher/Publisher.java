package com.headhunter.Library.Publisher;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.headhunter.Library.Book.Book;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table
@NoArgsConstructor
public class Publisher {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "publisher")
    @JsonManagedReference
    private List<Book> books;

    public Publisher(String name) {
        this.name = name;
    }

    public Publisher(Long requestedId, String name) {
        this.id = requestedId;
        this.name = name;
    }
}
