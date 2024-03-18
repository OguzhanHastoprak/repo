package com.headhunter.Library.Book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorId(Long id);

    List<Book> findByPublisherId(Long id);

}
