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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class BookRestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

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
}