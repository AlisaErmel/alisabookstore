package fi.haagahelia.alisabookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.alisabookstore.model.AppUser;
import fi.haagahelia.alisabookstore.model.AppUserRepository;
import fi.haagahelia.alisabookstore.model.Book;
import fi.haagahelia.alisabookstore.model.BookRepository;
import fi.haagahelia.alisabookstore.model.Category;
import fi.haagahelia.alisabookstore.model.CategoryRepository;

@SpringBootApplication
public class AlisabookstoreApplication {

	private static final Logger log = LoggerFactory.getLogger(AlisabookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AlisabookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner bookDemo(BookRepository repository, CategoryRepository crepository,
			AppUserRepository arepository) {
		return (args) -> {
			Category category1 = new Category("Biography");
			Category category2 = new Category("Science Fiction");
			Category category3 = new Category("Horror");

			crepository.save(category1);
			crepository.save(category2);
			crepository.save(category3);

			log.info("Saving some example books...");
			repository.save(new Book("Beautiful evening", "Tom Tomson", 2020, "56678934", 20, category1));
			repository.save(new Book("Life in Finland", "Ankka Kerilainen", 2018, "38895503", 90, category2));
			repository.save(new Book("City of Helsinki", "Musta Lakkonen", 2013, "49902022", 76, category3));

			// Create users: admin/password2 user/password
			AppUser user1 = new AppUser("user", "$2a$12$sC0PNpiaqWZAVc5Y2BgWqe.24ZhxLGC1pc3WpKsAfYD0lUDK3DPwK",
					"USER");
			AppUser user2 = new AppUser("admin", "$2a$12$UvNDCyZrgmGm7/EEKiwMNOgKiEb4z0r3QA2KG.RfbOKvBnUnyIlZK",
					"ADMIN");
			arepository.save(user1);
			arepository.save(user2);

			log.info("Fetching all books:");
			for (Book book : repository.findAll()) {
				log.info(book.toString());
			}

		};
	}

}
