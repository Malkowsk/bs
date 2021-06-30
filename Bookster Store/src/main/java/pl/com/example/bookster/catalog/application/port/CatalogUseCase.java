package pl.com.example.bookster.catalog.application.port;

import lombok.Value;
import pl.com.example.bookster.catalog.domain.Book;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface CatalogUseCase {

    List<Book> findByTitle(String title);

    List<Book> findAll();

    Optional<Book> findOneByTitleAndAuthor(String title, String author);

    void addBook(CreateBookCommand createBookCommand);

    void removeById(Long id);

    UpdateBookResponse updateBook(UpdateBookCommand updateBookCommand);

    @Value
    class CreateBookCommand {
        String title;
        String author;
        Integer year;
    }

    @Value
    class UpdateBookCommand {
        Long id;
        String title;
        String author;
        Integer year;
    }

    @Value
    class UpdateBookResponse {
        public static UpdateBookResponse SUCCESS = new UpdateBookResponse(true, Collections.emptyList());

        boolean success;
        List<String> errors;
    }
}
