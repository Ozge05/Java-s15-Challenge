package com.example.library;

import java.util.List;
import java.util.Optional;

public class Librarian extends Person {
    private final Library library;

    public Librarian(String name, int id, Library library) {
        super(id, name);
        if (library == null) {
            throw new IllegalArgumentException("Library is required.");
        }
        this.library = library;
    }

    public List<Book> searchBook(String query) {
        Optional<Book> byId = parseId(query).flatMap(library::getBook);
        return byId.map(List::of).orElseGet(() -> library.searchBooksByName(query));
    }

    public boolean verifyMember(int memberId) {
        return library.getReader(memberId).isPresent();
    }

    public Invoice issueBook(int memberId, long bookId) {
        return library.borrowBook(memberId, bookId);
    }

    public Invoice returnBook(int memberId, long bookId) {
        return library.returnBook(memberId, bookId);
    }

    public double calculateFine(long daysOverdue, double finePerDay) {
        if (daysOverdue < 0 || !Double.isFinite(finePerDay) || finePerDay < 0) {
            throw new IllegalArgumentException("Overdue days and daily fine must be non-negative.");
        }
        return daysOverdue * finePerDay;
    }

    public Invoice createBill(int memberId, long bookId) {
        return issueBook(memberId, bookId);
    }

    @Override
    public String whoYouAre() {
        return "I am a Librarian";
    }

    private Optional<Long> parseId(String query) {
        try {
            return Optional.of(Long.parseLong(query.trim()));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }
}
