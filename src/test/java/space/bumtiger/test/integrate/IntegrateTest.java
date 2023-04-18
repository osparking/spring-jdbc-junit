package space.bumtiger.test.integrate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import space.bumtiger.test.reposi.MovieRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrateTest {
	@LocalServerPort
	private int port;
	private String baseUrl = "http://localhost";
	private static RestTemplate restTemplate;
	@Autowired
	private MovieRepository repository;

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
