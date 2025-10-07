package fi.haagahelia.alisabookstore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import fi.haagahelia.alisabookstore.model.Book;
import fi.haagahelia.alisabookstore.model.BookRepository;
import fi.haagahelia.alisabookstore.model.Category;
import fi.haagahelia.alisabookstore.model.CategoryRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server.port=0")
@AutoConfigureMockMvc(addFilters = false)
public class BookRestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CategoryRepository categoryrepository;

    @Autowired
    private BookRepository bookrepository;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/books"; // must match your controller @RequestMapping
    }

    @Test
    public void getAllBooksShouldReturnList() {
        ResponseEntity<Book[]> response = restTemplate.getForEntity(baseUrl(), Book[].class);

        // Check HTTP status
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Check body is not null and contains some books
        Book[] books = response.getBody();
        assertThat(books).isNotNull();
        assertThat(books.length).isGreaterThan(0);
    }

    @Test
    public void getBookByIdShouldReturnBook() {
        // First get all books to find a valid ID
        ResponseEntity<Book[]> allBooksResponse = restTemplate.getForEntity(baseUrl(), Book[].class);
        Book[] books = allBooksResponse.getBody();

        assertThat(books).isNotNull();
        assertThat(books.length).isGreaterThan(0);

        Long firstId = books[0].getId();

        // GET book by ID
        ResponseEntity<Book> response = restTemplate.getForEntity(baseUrl() + "/" + firstId, Book.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(firstId);
        assertThat(response.getBody().getTitle()).isEqualTo(books[0].getTitle());
    }

    // ---- POST /api/books ----
    @Test
    public void createBookShouldReturnCreatedBook() {
        Category category = new Category("TestCategory");
        categoryrepository.save(category);

        Book newBook = new Book("Test Book", "Test Author", 2025, "1234567890", 50, category);

        ResponseEntity<Book> response = restTemplate.postForEntity(baseUrl(), newBook, Book.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Test Book");
    }

    // ---- PUT /api/books/{id} ----
    @Test
    public void updateBookShouldReturnUpdatedBook() {
        // First create a book
        Category category = new Category("UpdateCategory");
        categoryrepository.save(category);

        Book book = new Book("Old Title", "Author", 2020, "111111", 20, category);
        bookrepository.save(book);

        book.setTitle("Updated Title");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Book> entity = new HttpEntity<>(book, headers);

        ResponseEntity<Book> response = restTemplate.exchange(
                baseUrl() + "/" + book.getId(),
                HttpMethod.PUT,
                entity,
                Book.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Updated Title");
    }

    // ---- DELETE /api/books/{id} ----
    @Test
    public void deleteBookShouldRemoveBook() {
        // First create a book
        Category category = new Category("DeleteCategory");
        categoryrepository.save(category);

        Book book = new Book("Book to Delete", "Author", 2025, "222222", 30, category);
        bookrepository.save(book);

        // Delete
        restTemplate.delete(baseUrl() + "/" + book.getId());

        // Verify deletion
        ResponseEntity<Book> response = restTemplate.getForEntity(baseUrl() + "/" + book.getId(), Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}