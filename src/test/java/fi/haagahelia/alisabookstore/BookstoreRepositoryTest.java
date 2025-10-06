package fi.haagahelia.alisabookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import fi.haagahelia.alisabookstore.model.AppUser;
import fi.haagahelia.alisabookstore.model.AppUserRepository;
import fi.haagahelia.alisabookstore.model.Book;
import fi.haagahelia.alisabookstore.model.BookRepository;
import fi.haagahelia.alisabookstore.model.Category;
import fi.haagahelia.alisabookstore.model.CategoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SpringBootTest(classes = AlisabookstoreApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookstoreRepositoryTest {

    @Autowired
    private BookRepository bookrepository;

    @Autowired
    private CategoryRepository categoryrepository;

    @Autowired
    private AppUserRepository userrepository;

    @BeforeEach
    public void clearDatabase() {
        bookrepository.deleteAll();
        categoryrepository.deleteAll();
    }

    @Test
    public void findByAuthorShouldReturnBook() {
        Category category = new Category("Fiction");
        categoryrepository.save(category);

        Book book = new Book("Beautiful evening", "Tom Tomson", 2020, "1234567890", 50, category);
        bookrepository.save(book);

        List<Book> books = bookrepository.findByAuthor("Tom Tomson");

        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Beautiful evening");
    }

    @Test
    public void createNewBook() {
        Category category = new Category("History");
        categoryrepository.save(category);
        Book book = new Book("History of art", "Michael Dens", 1998, "4493049", 78, category);
        bookrepository.save(book);
        assertThat(book.getId()).isNotNull();
    }

    @Test
    public void deleteNewBook() {
        Category category = new Category("History");
        categoryrepository.save(category);

        Book book = new Book("History of art", "Michael Dens", 1998, "4493049", 78, category);
        bookrepository.save(book);

        List<Book> books = bookrepository.findByAuthor("Michael Dens");
        assertThat(books).hasSize(1);

        bookrepository.delete(books.get(0));

        List<Book> newBooks = bookrepository.findByAuthor("Michael Dens");
        assertThat(newBooks).isEmpty();
    }

    @Test
    public void findByUsernameShouldReturnUser() {
        AppUser appuser = userrepository.findByUsername("user");

        assertThat(appuser).isNotNull();
        assertThat(appuser.getRole()).isEqualTo("USER");
    }
}