package com.headhunter.Library.Book;

import com.headhunter.Library.Author.Author;
import com.headhunter.Library.Publisher.Publisher;

public record BookRequest(String name, Author author, Publisher publisher) {
}
