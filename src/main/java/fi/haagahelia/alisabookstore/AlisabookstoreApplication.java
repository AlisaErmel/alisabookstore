package fi.haagahelia.alisabookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.alisabookstore.model.Book;
import fi.haagahelia.alisabookstore.model.BookRepository;

@SpringBootApplication
public class AlisabookstoreApplication {

	private static final Logger log = LoggerFactory.getLogger(AlisabookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AlisabookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner boookDemo(BookRepository repository) {
		return (args) -> {
			log.info("Saving some example books...");
			repository.save(new Book("Beautiful evening", "Tom Tomson", 2020, "56678934", 20));
			repository.save(new Book("Life in Finland", "Ankka Kerilainen", 2018, "38895503", 90));
			repository.save(new Book("City of Helsinki", "Musta Lakkonen", 2013, "49902022", 76));

			log.info("Fetching all books:");
			for (Book book : repository.findAll()) {
				log.info(book.toString());
			}

		};
	}

}
