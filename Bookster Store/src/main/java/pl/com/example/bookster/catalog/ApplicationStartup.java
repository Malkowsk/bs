package pl.com.example.bookster.catalog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.com.example.bookster.catalog.application.port.CatalogUseCase;
import pl.com.example.bookster.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import pl.com.example.bookster.catalog.domain.Book;

import java.util.List;

import static pl.com.example.bookster.catalog.application.port.CatalogUseCase.*;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalogUseCase;
    private final String title;
    private final Long limit;

    public ApplicationStartup(CatalogUseCase catalogUseCase,
                              @Value("${bookster.catalog.query}") String title,
                              @Value("${bookster.catalog.limit:1}") Long limit) {
        this.catalogUseCase = catalogUseCase;
        this.limit = limit;
        this.title = title;
    }

    @Override
    public void run(String... args) {
//        CatalogService catalogService = new CatalogService();
        initData();
        findByTitle();
        findAndUpdate();
        findByTitle();
    }

    private void initData() {
        catalogUseCase.addBook(new CreateBookCommand("Pan Tadeusz", "Adam Mickiewicz", 1834));
        catalogUseCase.addBook(new CreateBookCommand("Ogniem i Mieczem", "Henryk Sienkiewicz", 1884));
        catalogUseCase.addBook(new CreateBookCommand("Chłopi", "Władysław Reymont", 1904));
        catalogUseCase.addBook(new CreateBookCommand("Pan Wołodyjowski", "Henryk Sienkiewicz", 1899));
    }

    private void findByTitle() {
        List<Book> books = catalogUseCase.findByTitle(title);
        books.stream().limit(limit).forEach(System.out::println);
    }

    private void findAndUpdate() {
        System.out.println("Updating book... ");
        catalogUseCase.findOneByTitleAndAuthor("Pan Tadeusz", "Adam Mickiewicz")
                .ifPresent(book -> {
                    System.out.println(" inside lambda");
                    UpdateBookCommand updateBookCommand = new UpdateBookCommand(
                            book.getId(),
                            "Pan Tadeusz, czyli Ostatni Zajazd na Litwie",
                            book.getAuthor(),
                            book.getYear()
                    );
                    System.out.println(book.getId());
                    catalogUseCase.updateBook(updateBookCommand);
                });
    }
}
