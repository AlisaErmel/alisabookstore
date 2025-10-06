package fi.haagahelia.alisabookstore.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.haagahelia.alisabookstore.model.Book;
import fi.haagahelia.alisabookstore.model.BookRepository;

@RestController
@RequestMapping("/api")
public class BookRestController {

    @Autowired
    private BookRepository bookRepository;

    // REST service that returns all books
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // REST service that returns one book by id
    @GetMapping("/books/{id}")
    public Optional<Book> findBookRest(@PathVariable("id") Long bookId) {
        return bookRepository.findById(bookId);
    }
}
