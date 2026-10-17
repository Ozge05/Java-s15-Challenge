package com.example.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Reader extends Person {
    private final List<Book> borrowedBooks;
    private final MemberRecord memberRecord;

    public Reader(String name, int id) {
        this(name, id, MemberType.READER);
    }

    protected Reader(String name, int id, MemberType memberType) {
        super(id, name);
        this.borrowedBooks = new ArrayList<>();
        this.memberRecord = new MemberRecord(id, memberType, name, 5);
    }

    void addBorrowedBook(Book book) {
        Objects.requireNonNull(book, "Book is required.");
        if (borrowedBooks.size() >= memberRecord.getMaxBookLimit()) {
            throw new IllegalStateException("The member has reached the borrowing limit.");
        }
        if (!borrowedBooks.contains(book)) {
            borrowedBooks.add(book);
            memberRecord.incrementBooksIssued();
        }
    }

    void removeBorrowedBook(Book book) {
        if (borrowedBooks.remove(book)) {
            memberRecord.decrementBooksIssued();
        }
    }

    public List<Book> getBorrowedBooks() {
        return Collections.unmodifiableList(borrowedBooks);
    }

    public MemberRecord getMemberRecord() {
        return memberRecord;
    }

    public void showBorrowedBooks() {
        borrowedBooks.forEach(System.out::println);
    }

    @Override
    public String whoYouAre() {
        return "I am a Reader";
    }
}
