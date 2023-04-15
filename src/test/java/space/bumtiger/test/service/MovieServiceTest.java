package space.bumtiger.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
	@DisplayName("영화를 삭제 DB 자료가 바뀐다.")
	void deleteTest() {
		// arrange: done
		// act
		when(repository.findById(anyLong())).thenReturn(Optional.of(yulDolMok));
		doNothing().when(repository).delete(any(Movie.class));
		
		service.deleteMovie(1L);
		
		// assert
		verify(repository, times(1)).delete(yulDolMok);	
	}

	@Test
	@DisplayName("영화를 갱신하면 DB 자료가 바뀐다.")
	void updateTest() throws CloneNotSupportedException {
		// arrange: done
		// act
		Movie personMovie = yulDolMok.clone();
		personMovie.setGenera("인물");
		when(repository.findById(anyLong())).thenReturn(Optional.of(yulDolMok));
		when(repository.save(any(Movie.class))).thenReturn(personMovie);

		Movie updatedMovie = service.updateMovie(yulDolMok, yulDolMok.getId());
		assertNotNull(updatedMovie);
		assertEquals(updatedMovie.getName(), yulDolMok.getName());
		assertThat(updatedMovie.getGenera()).isEqualTo("인물");
	}

	@Test
	@DisplayName("없는 ID는 예외를 발생시킨다")
	void invalidIdCauseException() {
		// arrange : done by beforeEach annotated method
		// act
		when(repository.findById(1L)).thenReturn (Optional.of(yulDolMok));
		
		// assert
		assertThrows(RuntimeException.class, () -> {
			service.getMovieById(2L);
		});
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
