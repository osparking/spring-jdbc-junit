package space.bumtiger.test.integrate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import space.bumtiger.test.model.Movie;
import space.bumtiger.test.reposi.MovieRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrateTest {
	@LocalServerPort
	private int port;
	private String baseUrl = "http://localhost";
	private static RestTemplate restTemplate;
	@Autowired
	private MovieRepository repository;

	@Test
	@DisplayName("집적 시험 - 영화 저장")
	void shouldCreateMovieTest() {
		Movie yulDolMok = new Movie();
		yulDolMok.setName("율돌목");
		yulDolMok.setGenera("역사");
		yulDolMok.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));

		Movie savedMovie = restTemplate.postForObject(baseUrl, yulDolMok,
				Movie.class);
		
		assertNotNull(savedMovie);
		assertThat(savedMovie.getId()).isNotNull();
	}

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void beforeSetup() {
		StringBuffer urlBuffer = new StringBuffer(baseUrl);
		urlBuffer.append(":");
		urlBuffer.append(port);
		urlBuffer.append("/movies");
		baseUrl = urlBuffer.toString();
	}

	@AfterEach
	public void afterSetup() {
		repository.deleteAll();
	}

}
