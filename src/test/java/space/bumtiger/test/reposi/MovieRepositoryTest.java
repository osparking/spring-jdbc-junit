package space.bumtiger.test.reposi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import space.bumtiger.test.model.Movie;

@DataJdbcTest
@DisplayName("DB 영화 테이블 연산")
@ExtendWith(MockitoExtension.class)
public class MovieRepositoryTest {

	@Autowired
	private MovieRepository repository;

	private Movie yulDolMok;
	private Movie chunHyangJeon;

	@BeforeEach
	void makeMovies() {
		yulDolMok = new Movie();
		yulDolMok.setName("율돌목");
		yulDolMok.setGenera("역사");
		yulDolMok.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));

		chunHyangJeon = new Movie();
		chunHyangJeon.setName("춘향전");
		chunHyangJeon.setGenera("로맨스");
		chunHyangJeon.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));
	}

	@Test
	@DisplayName("쟝르 영화 검색 성공")
	void findRomanceMovie() {
		// arrange
		repository.save(yulDolMok);
		repository.save(chunHyangJeon);

		// act
		var romances = repository.findByGenera("로맨스");

		// assert
		assertNotNull(romances);
		assertThat(romances.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("영화 삭제 성공")
	void deleteMovie() {
		// arrange
		Movie firstMovie = repository.save(yulDolMok);
		repository.save(chunHyangJeon); // save movie again

		// act
		repository.delete(firstMovie);

		// assert
		var optionalMovie = repository.findById(firstMovie.getId());
		assertTrue(optionalMovie.isEmpty());

		int count = 0;
		var movies = repository.findAll();
		var iter = movies.iterator();
		while (iter.hasNext()) {
			count++;
		}
		assertThat(count).isEqualTo(1);
	}

	@Test
	@DisplayName("영화 장르 갱신 성공")
	void updateMovie() {
		// arrange
		repository.save(yulDolMok);
		var foundMovie = repository.findById(yulDolMok.getId());

		// act
		String newGenere = "전쟁사";
		foundMovie.ifPresent(m -> m.setGenera(newGenere));
		repository.save(foundMovie.get());
		foundMovie = repository.findById(yulDolMok.getId());

		// assert
		assertNotNull(foundMovie);
		assertThat(foundMovie.get().getGenera()).isEqualTo(newGenere);
		assertNotEquals(yulDolMok.getGenera(), foundMovie.get().getGenera());
	}

	@Test
	@DisplayName("영화가 ID로 찾아짐")
	void findMovieById() {
		// Arrange
		var savedMovie = repository.save(yulDolMok);

		// Act
		var foundMovie = repository.findById(savedMovie.getId());

		// Assert
		assertNotNull(foundMovie);
		Movie fMovie = foundMovie.isPresent() ? foundMovie.get() : null;
		assertThat(fMovie.getGenera()).isEqualTo(yulDolMok.getGenera());
		assertThat(fMovie.getReleaseDate())
				.isAfter(yulDolMok.getReleaseDate().minusDays(1L));
	}

	@Test
	@DisplayName("영화 2 건이 읽혀질 것임")
	void fetchAll() {
		// Arrange
		repository.save(yulDolMok);
		repository.save(chunHyangJeon);

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
		// Act
		var savedMovie = repository.save(yulDolMok);

		// Assert
		assertNotNull(savedMovie);
		assertThat(savedMovie.getId()).isNotEqualTo(null);
	}

}
