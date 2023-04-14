package space.bumtiger.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;

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























