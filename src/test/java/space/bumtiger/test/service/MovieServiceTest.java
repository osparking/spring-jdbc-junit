package space.bumtiger.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import space.bumtiger.test.model.Movie;
import space.bumtiger.test.reposi.MovieRepository;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

	@InjectMocks
	private MovieService service;

	@Mock
	private MovieRepository repository;

	private Movie yulDolMok;
	
	@BeforeEach
	void createMovie() {
		yulDolMok = new Movie();
		yulDolMok.setId(1L);
		yulDolMok.setName("울돌목");
		yulDolMok.setGenera("역사");
		yulDolMok.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));
	}

	@Test
	@DisplayName("ID 1인 영화를 잘 읽어온다")
	void readMovieById() {
		// arrange : done by beforeEach annotated method
		// act
		when(repository.findById(anyLong())).thenReturn (Optional.of(yulDolMok));
		var movie = service.getMovieById(yulDolMok.getId());
		
		// assert
		assertNotNull(movie);
		assertThat(movie.getId()).isEqualTo(1L);
	}
	
	@Test
	@DisplayName("영화 2 건 목록이 반환된다")
	void getMovies() {
		var yulDolMok = new Movie();
		yulDolMok.setId(2L);
		yulDolMok.setName("율돌목");
		yulDolMok.setGenera("역사");
		yulDolMok.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));

		var chunHyangJeon = new Movie();
		chunHyangJeon.setId(2L);
		chunHyangJeon.setName("춘향전");
		chunHyangJeon.setGenera("로맨스");
		chunHyangJeon.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));

		List<Movie> movies = new ArrayList<Movie>();
		movies.add(yulDolMok);
		movies.add(chunHyangJeon);

		when(repository.findAll()).thenReturn(movies);

		Collection<Movie> movieColl = (Collection<Movie>) service.getAllMovies();
		assertNotNull(movieColl);
		assertThat(movieColl.size()).isEqualByComparingTo(2);
	}

	@Test
	@DisplayName("영화 객체를 DB 에 저장한다")
	void save() {
		var yulDolMok = new Movie();
		yulDolMok.setId(2L);
		yulDolMok.setName("율돌목");
		yulDolMok.setGenera("역사");
		yulDolMok.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));

		when(repository.save(any(Movie.class))).thenReturn(yulDolMok);

		var saved1 = service.save(yulDolMok);
		assertNotNull(saved1);
		assertThat(saved1.getName()).isEqualTo(yulDolMok.getName());
	}

}
