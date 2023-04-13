package space.bumtiger.test.reposi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import space.bumtiger.test.model.Movie;

@DataJdbcTest
@DisplayName("DB 영화 테이블 연산")
public class MovieRepositoryTest {

	@Autowired
	private MovieRepository repository;

	@Test
	@DisplayName("영화가 ID로 찾아짐")
	void findMovieById() {
		// Arrange
		var movie = new Movie();
		movie.setName("율돌목");
		String genera = "전쟁";
		movie.setGenera(genera);
		var relDate = LocalDate.of(2009, Month.NOVEMBER, 10);
		movie.setReleaseDate(relDate);
		var savedMovie = repository.save(movie);
		
		// Act
		var foundMovie = repository.findById(savedMovie.getId());
		
		// Assert
		assertNotNull(foundMovie);
		Movie fMovie = foundMovie.isPresent() ? foundMovie.get() : null;
		assertThat(fMovie.getGenera()).isEqualTo(genera);
		assertThat(fMovie.getReleaseDate()).isAfter(relDate.minusDays(1L));
	}

	@Test
	@DisplayName("영화 2 건이 읽혀질 것임")
	void fetchAll() {
		// Arrange
		var movie = new Movie();
		movie.setName("율돌목");
		movie.setGenera("전쟁");
		movie.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));
		repository.save(movie);

		movie = new Movie();
		movie.setName("율돌목");
		movie.setGenera("전쟁");
		movie.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));
		repository.save(movie);

		// act
		Iterable<Movie> movies = repository.findAll();

		// Assert 1
		assertNotNull(movies);

		var iter = movies.iterator();
		int count = 0;
		while (iter.hasNext()) {
			iter.next();
			count++;
		}

		// Assert 2
		assertThat(count).isEqualTo(2);
	}

	@Test
	@DisplayName("영화가 DB에 저장됨")
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
		assertThat(savedMovie.getId()).isNotEqualTo(null);
	}

}
