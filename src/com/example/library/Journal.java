package com.example.library;

import java.time.LocalDate;

public class Journal extends Book {
    public Journal(Long bookId, Author author, String name, double price, int edition, LocalDate purchaseDate) {
        super(bookId, author, name, price, true, edition, purchaseDate);
    }

    @Override
    public BookCategory getCategory() {
        return BookCategory.JOURNAL;
    }
}
