package com.example.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Author extends Person {
    private final List<Book> books;

    public Author(String name, int id) {
        super(id, name);
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        Objects.requireNonNull(book, "Book is required.");
        if (!books.contains(book)) {
            books.add(book);
        }
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    @Override
    public String whoYouAre() {
        return "I am an Author";
    }
}
