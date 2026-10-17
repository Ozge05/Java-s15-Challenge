package com.example.library;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Library {
    public static final int MAX_BOOKS_IN_LIBRARY = 100;

    private final Map<Long, Book> books = new LinkedHashMap<>();
    private final Map<Integer, Reader> readers = new LinkedHashMap<>();
    private final Map<Integer, Author> authors = new LinkedHashMap<>();
    private final List<Invoice> invoices = new ArrayList<>();
    private final Set<BookCategory> categories = EnumSet.allOf(BookCategory.class);

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book is required.");
        }
        if (books.size() >= MAX_BOOKS_IN_LIBRARY) {
            throw new IllegalStateException("The library has reached its book capacity.");
        }
        if (books.containsKey(book.getBookId())) {
            throw new IllegalArgumentException("A book with this id already exists.");
        }
        Author registeredAuthor = authors.get(book.getBookAuthor().getId());
        if (registeredAuthor != null && registeredAuthor != book.getBookAuthor()) {
            throw new IllegalArgumentException("An author with this id already exists.");
        }
        books.put(book.getBookId(), book);
        book.getBookAuthor().addBook(book);
        authors.putIfAbsent(book.getBookAuthor().getId(), book.getBookAuthor());
    }

    public void registerAuthor(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("Author is required.");
        }
        Author existing = authors.putIfAbsent(author.getId(), author);
        if (existing != null && existing != author) {
            throw new IllegalArgumentException("An author with this id already exists.");
        }
    }

    public void registerReader(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("Reader is required.");
        }
        Reader existing = readers.putIfAbsent(reader.getId(), reader);
        if (existing != null && existing != reader) {
            throw new IllegalArgumentException("A reader with this id already exists.");
        }
    }

    public Optional<Book> getBook(long bookId) {
        return Optional.ofNullable(books.get(bookId));
    }

    public Collection<Book> getBooks() {
        return Collections.unmodifiableCollection(books.values());
    }

    public Collection<Reader> getReaders() {
        return Collections.unmodifiableCollection(readers.values());
    }

    public Set<BookCategory> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    public List<Book> searchBooksByName(String name) {
        String query = name.toLowerCase(Locale.ROOT);
        return books.values().stream()
                .filter(book -> book.getBookName().toLowerCase(Locale.ROOT).contains(query))
                .toList();
    }

    public List<Book> searchBooksByAuthor(String authorName) {
        String query = authorName.toLowerCase(Locale.ROOT);
        return books.values().stream()
                .filter(book -> book.getBookAuthor().getName().toLowerCase(Locale.ROOT).contains(query))
                .toList();
    }

    public List<Book> getBooksByCategory(BookCategory category) {
        return books.values().stream()
                .filter(book -> book.getCategory() == category)
                .toList();
    }

    public List<Book> getBooksByAuthor(int authorId) {
        Author author = authors.get(authorId);
        return author == null ? List.of() : author.getBooks();
    }

    public void updateBook(long bookId, String name, double price, int edition, int authorId) {
        Book book = requireBook(bookId);
        Author author = authors.get(authorId);
        if (author == null) {
            throw new IllegalArgumentException("Author not found.");
        }
        Author oldAuthor = book.getBookAuthor();
        book.updateDetails(name, price, edition, author);
        if (oldAuthor != author) {
            oldAuthor.removeBook(book);
            author.addBook(book);
        }
    }

    public void removeBook(long bookId) {
        Book book = requireBook(bookId);
        if (!book.isBookAvailable()) {
            throw new IllegalStateException("A borrowed book cannot be removed.");
        }
        books.remove(bookId);
        book.getBookAuthor().removeBook(book);
    }

    public Invoice borrowBook(int readerId, long bookId) {
        Reader reader = requireReader(readerId);
        Book book = requireBook(bookId);
        if (!book.isBookAvailable()) {
            throw new IllegalStateException("This book is already borrowed.");
        }
        if (reader.getBorrowedBooks().size() >= reader.getMemberRecord().getMaxBookLimit()) {
            throw new IllegalStateException("The reader has reached the five-book limit.");
        }
        reader.addBorrowedBook(book);
        book.setBookAvailable(false);
        Invoice invoice = new Invoice(reader, book);
        invoices.add(invoice);
        return invoice;
    }

    public Invoice returnBook(int readerId, long bookId) {
        Reader reader = requireReader(readerId);
        Book book = requireBook(bookId);
        if (!reader.getBorrowedBooks().contains(book)) {
            throw new IllegalStateException("This reader has not borrowed this book.");
        }
        Invoice invoice = invoices.stream()
                .filter(item -> item.getReader().equals(reader) && item.getBook().equals(book) && !item.isRefunded())
                .reduce((first, second) -> second)
                .orElseThrow(() -> new IllegalStateException("No active invoice exists for this loan."));
        reader.removeBorrowedBook(book);
        book.setBookAvailable(true);
        invoice.refund();
        return invoice;
    }

    public List<Invoice> getInvoices() {
        return Collections.unmodifiableList(invoices);
    }

    public Optional<Reader> getReader(int readerId) {
        return Optional.ofNullable(readers.get(readerId));
    }

    public Optional<Author> getAuthor(int authorId) {
        return Optional.ofNullable(authors.get(authorId));
    }

    private Book requireBook(long bookId) {
        Book book = books.get(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Book not found: " + bookId);
        }
        return book;
    }

    private Reader requireReader(int readerId) {
        Reader reader = readers.get(readerId);
        if (reader == null) {
            throw new IllegalArgumentException("Reader not found: " + readerId);
        }
        return reader;
    }
}
