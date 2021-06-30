package pl.com.example.bookster.catalog.infrastructure;

import org.springframework.stereotype.Repository;
import pl.com.example.bookster.catalog.domain.Book;
import pl.com.example.bookster.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SchoolCatalogRepository implements CatalogRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    private final AtomicLong ID_NEXT_VALUE = new AtomicLong(0);

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void save(Book book) {
        long id = nextId();
        book.setId(id);
        storage.put(id, book);
    }

    @Override
    public void removeById(Long id) {
        storage.remove(id);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    private long nextId() {
        return ID_NEXT_VALUE.getAndIncrement();
    };
}
