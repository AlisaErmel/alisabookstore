package fi.haagahelia.alisabookstore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fi.haagahelia.alisabookstore.web.BookController;
import fi.haagahelia.alisabookstore.web.BookRestController;

@SpringBootTest
class AlisabookstoreApplicationTests {

	private final BookController bookcontroller;
	private final BookRestController restcontroller;

	@Autowired
	public AlisabookstoreApplicationTests(BookController bookcontroller, BookRestController restcontroller) {
		this.bookcontroller = bookcontroller;
		this.restcontroller = restcontroller;
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void controllerLoads() throws Exception {
		assertThat(bookcontroller).isNotNull();
		assertThat(restcontroller).isNotNull();
	}

}