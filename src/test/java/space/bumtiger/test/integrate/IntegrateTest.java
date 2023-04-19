package space.bumtiger.test.integrate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;

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
	@DisplayName("집적 시험 - 영화 정보 갱신")
	void shouldUpdateMovieWorksWell() {
		// arrange - 영화 생성, 저장, ID 확보
		Movie yulDolMok = new Movie();
		yulDolMok.setName("율돌목");
		yulDolMok.setGenera("역사");
		yulDolMok.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));

		yulDolMok = restTemplate.postForObject(baseUrl, yulDolMok, Movie.class);

		// act
		String newGenera = "인물";
		yulDolMok.setGenera(newGenera);
		restTemplate.put(baseUrl + "/{id}", yulDolMok, yulDolMok.getId());

		// assert
		Movie existingMovie = restTemplate
				.getForObject(baseUrl + "/" + yulDolMok.getId(), Movie.class);
		assertNotNull(existingMovie);
		assertThat(newGenera).isEqualTo(existingMovie.getGenera());
	}

	@Test
	@DisplayName("집적 시험 - 특정 영화 삭제")
	void shouldDeleteMovieByIdTest() {

		// arrange - 영화 생성, 저장, ID 확보
		Movie yulDolMok = new Movie();
		yulDolMok.setName("율돌목");
		yulDolMok.setGenera("역사");
		yulDolMok.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));

		Movie movie2delete = restTemplate.postForObject(baseUrl, yulDolMok,
				Movie.class);

		Movie chunHyangJeon = new Movie();
		chunHyangJeon.setName("춘향전");
		chunHyangJeon.setGenera("로맨스");
		chunHyangJeon.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));

		restTemplate.postForObject(baseUrl, chunHyangJeon, Movie.class);

		// act
		String url = baseUrl + "/" + movie2delete.getId();
		restTemplate.delete(url);

		// assert
		@SuppressWarnings("unchecked")
		var listSize = ((Collection<?>) repository.findAll()).size();

		assertThat(listSize).isEqualTo(1);
	}

	@Test
	@DisplayName("집적 시험 - 영화 ID로 찾기")
	void shouldFindMovieByIdTest() {

		// arrange - 영화 생성, 저장, ID 확보
		Movie yulDolMok = new Movie();
		yulDolMok.setName("율돌목");
		yulDolMok.setGenera("역사");
		yulDolMok.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));

		Movie savedMovie = restTemplate.postForObject(baseUrl, yulDolMok,
				Movie.class);

		// act
		String url = baseUrl + "/" + savedMovie.getId();
		Movie foundMovie = restTemplate.getForObject(url, Movie.class);

		// assert
		assertNotNull(foundMovie);
		assertThat(foundMovie.getId()).isEqualTo(savedMovie.getId());
	}

	@Test
	@DisplayName("집적 시험 - 영화 목록")
	void shouldReadListTest() {
		Movie yulDolMok = new Movie();
		yulDolMok.setName("율돌목");
		yulDolMok.setGenera("역사");
		yulDolMok.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));
		restTemplate.postForObject(baseUrl, yulDolMok, Movie.class);

		Movie chunHyangJeon = new Movie();
		chunHyangJeon.setName("춘향전");
		chunHyangJeon.setGenera("로맨스");
		chunHyangJeon.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));
		restTemplate.postForObject(baseUrl, chunHyangJeon, Movie.class);

		Movie[] savedMovie = restTemplate.getForObject(baseUrl, Movie[].class);

		assertNotNull(savedMovie);
		assertThat(savedMovie.length).isEqualTo(2);
	}

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
