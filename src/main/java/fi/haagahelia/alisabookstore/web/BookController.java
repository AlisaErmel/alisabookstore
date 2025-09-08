package fi.haagahelia.alisabookstore.web;

import fi.haagahelia.alisabookstore.model.BookRepository;

public class BookController {

    private BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }
}
