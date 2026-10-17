package com.example.library;

import java.time.LocalDate;

public class Magazine extends Book {
    public Magazine(Long bookId, Author author, String name, double price, int edition, LocalDate purchaseDate) {
        super(bookId, author, name, price, true, edition, purchaseDate);
    }

    @Override
    public BookCategory getCategory() {
        return BookCategory.MAGAZINE;
    }
}
