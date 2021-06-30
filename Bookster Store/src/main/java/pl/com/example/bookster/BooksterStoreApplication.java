package pl.com.example.bookster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BooksterStoreApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(BooksterStoreApplication.class, args);
        System.out.println(run);
    }

//    @Bean
//    CatalogRepository catalogRepository() {
//        return new SchoolCatalogRepository();
//    }

}
