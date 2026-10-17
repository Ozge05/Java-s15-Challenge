package com.example.library;

import java.time.LocalDateTime;

public class Invoice {
    private final Reader reader;
    private final Book book;
    private final double amount;
    private final LocalDateTime issuedAt;
    private LocalDateTime refundedAt;

    Invoice(Reader reader, Book book) {
        this.reader = reader;
        this.book = book;
        this.amount = book.getBookPrice();
        this.issuedAt = LocalDateTime.now();
    }

    public Reader getReader() {
        return reader;
    }

    public Book getBook() {
        return book;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public boolean isRefunded() {
        return refundedAt != null;
    }

    public LocalDateTime getRefundedAt() {
        return refundedAt;
    }

    void refund() {
        if (isRefunded()) {
            throw new IllegalStateException("This invoice has already been refunded.");
        }
        refundedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Invoice{member='" + reader.getName() + "', book='" + book.getBookName() +
                "', amount=" + amount + ", status=" + (isRefunded() ? "refunded" : "charged") + "}";
    }
}
