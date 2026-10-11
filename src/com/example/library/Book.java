package com.example.library;

import java.time.LocalDate;

public class Book {
    private Long bookId;
    private String bookAuthor;
    private String bookName;
    private double bookPrice;
    private boolean isBookAvailable;
    private int bookEdition;
    private LocalDate dateOfPurchase;

    public Book(Long bookId, String bookAuthor, String bookName, double bookPrice, boolean isBookAvailable, int bookEdition, LocalDate dateOfPurchase) {
        this.bookId = bookId;
        this.bookAuthor = bookAuthor;
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.isBookAvailable = isBookAvailable;
        this.bookEdition = bookEdition;
        this.dateOfPurchase = dateOfPurchase;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookName() {
        return bookName;
    }

    public boolean updateBookStatus() {
        if (isBookAvailable) {
            isBookAvailable = false;
        } else {
            isBookAvailable = true;
        }
        return isBookAvailable;
    }

    @Override
    public String toString() {
        String s = "Book{" +
                "bookId=" + bookId +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookPrice=" + bookPrice +
                ", isBookAvailable=" + isBookAvailable +
                ", bookEdition=" + bookEdition +
                ", dateOfPurchase=" + dateOfPurchase +
                '}';
        return s;
    }
}
