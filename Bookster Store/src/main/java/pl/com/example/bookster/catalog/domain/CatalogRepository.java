package pl.com.example.bookster.catalog.domain;

import java.util.List;
import java.util.Optional;

public interface CatalogRepository {

    List<Book> findAll();

    void save(Book book);

    void removeById(Long id);

    Optional<Book> findById(Long id);
}
