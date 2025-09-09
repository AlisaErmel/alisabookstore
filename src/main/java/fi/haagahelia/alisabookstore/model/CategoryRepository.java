package fi.haagahelia.alisabookstore.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    List<Book> findByName(String name);
}