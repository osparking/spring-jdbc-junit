package space.bumtiger.test.reposi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import space.bumtiger.test.model.Movie;

@DataJdbcTest
public class MovieRepositoryTest {
	
	@Autowired
	private MovieRepository repository;
	
	@Test
	void save() {
		// Arrange
		var movie = new Movie();
		movie.setName("율돌목");
		movie.setGenera("전쟁");
		movie.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));
		
		// Act
		var savedMovie = repository.save(movie);
		
		// Assert
		assertNotNull(savedMovie);		
	}

}