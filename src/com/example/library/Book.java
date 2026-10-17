package com.example.library;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private final Long bookId;
    private Author bookAuthor;
    private String bookName;
    private double bookPrice;
    private boolean isBookAvailable;
    private int bookEdition;
    private final LocalDate dateOfPurchase;

    public Book(Long bookId, Author bookAuthor, String bookName, double bookPrice,
                boolean isBookAvailable, int bookEdition, LocalDate dateOfPurchase) {
        if (bookId == null || bookId <= 0) {
            throw new IllegalArgumentException("Book id must be positive.");
        }
        this.bookId = bookId;
        this.bookAuthor = Objects.requireNonNull(bookAuthor, "Author is required.");
        setBookName(bookName);
        setBookPrice(bookPrice);
        setBookEdition(bookEdition);
        this.isBookAvailable = isBookAvailable;
        this.dateOfPurchase = Objects.requireNonNull(dateOfPurchase, "Purchase date is required.");
    }

    public Long getBookId() {
        return bookId;
    }

    public Author getBookAuthor() {
        return bookAuthor;
    }

    public String getBookName() {
        return bookName;
    }

    public String getTitle() {
        return bookName;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public boolean isBookAvailable() {
        return isBookAvailable;
    }

    public int getBookEdition() {
        return bookEdition;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void updateDetails(String name, double price, int edition, Author author) {
        validateBookName(name);
        validateBookPrice(price);
        validateBookEdition(edition);
        Author validatedAuthor = Objects.requireNonNull(author, "Author is required.");
        this.bookName = name.trim();
        this.bookPrice = price;
        this.bookEdition = edition;
        this.bookAuthor = validatedAuthor;
    }

    public void setBookName(String bookName) {
        validateBookName(bookName);
        this.bookName = bookName.trim();
    }

    public void setBookPrice(double bookPrice) {
        validateBookPrice(bookPrice);
        this.bookPrice = bookPrice;
    }

    public void setBookEdition(int bookEdition) {
        validateBookEdition(bookEdition);
        this.bookEdition = bookEdition;
    }

    private static void validateBookName(String bookName) {
        if (bookName == null || bookName.isBlank()) {
            throw new IllegalArgumentException("Book name cannot be empty.");
        }
    }

    private static void validateBookPrice(double bookPrice) {
        if (!Double.isFinite(bookPrice) || bookPrice < 0) {
            throw new IllegalArgumentException("Book price must be a non-negative number.");
        }
    }

    private static void validateBookEdition(int bookEdition) {
        if (bookEdition < 1) {
            throw new IllegalArgumentException("Book edition must be positive.");
        }
    }

    public void updateBookStatus() {
        isBookAvailable = !isBookAvailable;
    }

    void setBookAvailable(boolean available) {
        isBookAvailable = available;
    }

    public BookCategory getCategory() {
        return BookCategory.GENERAL;
    }

    @Override
    public String toString() {
        return "Book{id=" + bookId + ", title='" + bookName + "', author='" +
                bookAuthor.getName() + "', price=" + bookPrice + ", available=" +
                isBookAvailable + ", edition=" + bookEdition + ", category=" +
                getCategory() + "}";
    }
}
