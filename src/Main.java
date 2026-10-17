import com.example.library.Author;
import com.example.library.Book;
import com.example.library.BookCategory;
import com.example.library.Faculty;
import com.example.library.Invoice;
import com.example.library.Journal;
import com.example.library.Library;
import com.example.library.Librarian;
import com.example.library.Magazine;
import com.example.library.Reader;
import com.example.library.Student;
import com.example.library.StudyBook;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final Library library = new Library();
    private final Librarian librarian = new Librarian("Library Staff", 1, library);

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        System.out.println("Library management system");
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choice: ");
            try {
                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> searchBooks();
                    case 3 -> updateBook();
                    case 4 -> removeBook();
                    case 5 -> listBooksByCategory();
                    case 6 -> listBooksByAuthor();
                    case 7 -> registerReader();
                    case 8 -> borrowBook();
                    case 9 -> returnBook();
                    case 10 -> listBooks();
                    case 0 -> running = false;
                    default -> System.out.println("Choose a listed option.");
                }
            } catch (IllegalArgumentException | IllegalStateException exception) {
                System.out.println("Operation failed: " + exception.getMessage());
            }
        }
        System.out.println("Goodbye.");
    }

    private void printMenu() {
        System.out.println("""
                \n1. Add book
                2. Search by id, title, or author
                3. Update book
                4. Remove book
                5. List books by category
                6. List an author's books
                7. Register reader
                8. Borrow book
                9. Return book
                10. List all books
                0. Exit""");
    }

    private void addBook() {
        long id = readLong("Book id: ");
        int authorId = readInt("Author id: ");
        Author author = library.getAuthor(authorId).orElseGet(() -> {
            Author newAuthor = new Author(readNonEmpty("Author name: "), authorId);
            library.registerAuthor(newAuthor);
            return newAuthor;
        });
        String title = readNonEmpty("Title: ");
        double price = readDouble("Price: ");
        int edition = readInt("Edition: ");
        BookCategory category = readCategory();

        Book book = switch (category) {
            case JOURNAL -> new Journal(id, author, title, price, edition, LocalDate.now());
            case STUDY_BOOK -> new StudyBook(id, author, title, price, edition, LocalDate.now());
            case MAGAZINE -> new Magazine(id, author, title, price, edition, LocalDate.now());
            case GENERAL -> new Book(id, author, title, price, true, edition, LocalDate.now());
        };
        library.addBook(book);
        System.out.println("Added: " + book);
    }

    private void searchBooks() {
        String query = readNonEmpty("Book id, title, or author: ");
        List<Book> results = librarian.searchBook(query);
        if (results.isEmpty()) {
            results = library.searchBooksByAuthor(query);
        }
        printBooks(results);
    }

    private void updateBook() {
        long id = readLong("Book id to update: ");
        String title = readNonEmpty("New title: ");
        double price = readDouble("New price: ");
        int edition = readInt("New edition: ");
        int authorId = readInt("Author id (add a book by this author first): ");
        library.updateBook(id, title, price, edition, authorId);
        System.out.println("Book updated.");
    }

    private void removeBook() {
        library.removeBook(readLong("Book id to remove: "));
        System.out.println("Book removed.");
    }

    private void listBooksByCategory() {
        BookCategory category = readCategory();
        printBooks(library.getBooksByCategory(category));
    }

    private void listBooksByAuthor() {
        int authorId = readInt("Author id: ");
        printBooks(library.getBooksByAuthor(authorId));
    }

    private void registerReader() {
        int id = readInt("Reader id: ");
        String name = readNonEmpty("Reader name: ");
        System.out.println("Member type: 1. Reader  2. Student  3. Faculty");
        int type = readInt("Type: ");
        Reader reader = switch (type) {
            case 1 -> new Reader(name, id);
            case 2 -> new Student(name, id);
            case 3 -> new Faculty(name, id);
            default -> throw new IllegalArgumentException("Unknown member type.");
        };
        library.registerReader(reader);
        System.out.println("Registered: " + reader + " — " + reader.getMemberRecord());
    }

    private void borrowBook() {
        Invoice invoice = librarian.issueBook(readInt("Reader id: "), readLong("Book id: "));
        System.out.println("Book issued. Invoice: " + invoice);
    }

    private void returnBook() {
        Invoice invoice = librarian.returnBook(readInt("Reader id: "), readLong("Book id: "));
        System.out.println("Book returned; payment refunded. Invoice: " + invoice);
    }

    private void listBooks() {
        printBooks(library.getBooks());
    }

    private void printBooks(Iterable<Book> books) {
        boolean found = false;
        for (Book book : books) {
            System.out.println(book);
            found = true;
        }
        if (!found) {
            System.out.println("No books found.");
        }
    }

    private BookCategory readCategory() {
        printCategories();
        int choice = readInt("Category number: ");
        BookCategory[] categories = BookCategory.values();
        if (choice < 1 || choice > categories.length) {
            throw new IllegalArgumentException("Unknown book category.");
        }
        return categories[choice - 1];
    }

    private void printCategories() {
        BookCategory[] categories = BookCategory.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
    }

    private int readInt(String prompt) {
        return Integer.parseInt(readNonEmpty(prompt));
    }

    private long readLong(String prompt) {
        return Long.parseLong(readNonEmpty(prompt));
    }

    private double readDouble(String prompt) {
        return Double.parseDouble(readNonEmpty(prompt));
    }

    private String readNonEmpty(String prompt) {
        System.out.print(prompt);
        String value = scanner.nextLine().trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty.");
        }
        return value;
    }
}
