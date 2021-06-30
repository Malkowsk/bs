package pl.com.example.bookster.catalog.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.example.bookster.catalog.application.port.CatalogUseCase;
import pl.com.example.bookster.catalog.domain.Book;
import pl.com.example.bookster.catalog.domain.CatalogRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class CatalogService implements CatalogUseCase {

    private final CatalogRepository catalogRepository;

    @Override
    public List<Book> findByTitle(String title) {
        return catalogRepository.findAll().stream()
                .filter(book -> book.getTitle().startsWith(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll() {
        return catalogRepository.findAll();
    }

    @Override
    public Optional<Book> findOneByTitleAndAuthor(String title, String author) {
        return catalogRepository.findAll()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .filter(book -> book.getAuthor().startsWith(author))
                .findFirst();
    }

    @Override
    public void addBook(CreateBookCommand command) {
        Book book = new Book(command.getTitle(), command.getAuthor(), command.getYear());
        catalogRepository.save(book);
    }

    @Override
    public void removeById(Long id) {
        catalogRepository.removeById(id);
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookCommand updateBookCommand) {
        return catalogRepository
                .findById(updateBookCommand.getId())
                .map(book -> {
                    book.setAuthor(updateBookCommand.getAuthor());
                    book.setTitle(updateBookCommand.getTitle());
                    book.setYear(updateBookCommand.getYear());
                    catalogRepository.save(book);
                    return UpdateBookResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateBookResponse(false, Collections.singletonList("Book not found for id = " + updateBookCommand.getId())));
    }
}
