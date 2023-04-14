package space.bumtiger.test.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import space.bumtiger.test.reposi.MovieRepository;

class MovieServiceTest {

	@InjectMocks
	private MovieService service;
	
	@Mock
	private MovieRepository repository;
	
	@Test
	@DisplayName("영화 객체를 DB 에 저장한다")
	void save() {
		fail("Not yet implemented");
	}

}
