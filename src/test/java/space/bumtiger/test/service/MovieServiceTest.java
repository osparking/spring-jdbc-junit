package space.bumtiger.test.service;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import space.bumtiger.test.model.Movie;
import space.bumtiger.test.reposi.MovieRepository;

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
		
	}

}
