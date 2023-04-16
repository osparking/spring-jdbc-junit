package space.bumtiger.test.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import space.bumtiger.test.model.Movie;
import space.bumtiger.test.service.MovieService;

@WebMvcTest
class MovieControllerTest {

	@MockBean
	private MovieService service;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private Movie yulDolMok;
	private Movie chunHyangJeon;

	@BeforeEach
	void makeMovies() {
		yulDolMok = new Movie();
		yulDolMok.setId(1L);
		yulDolMok.setName("율돌목");
		yulDolMok.setGenera("역사");
		yulDolMok.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));

		chunHyangJeon = new Movie();
		chunHyangJeon.setId(2L);
		chunHyangJeon.setName("춘향전");
		chunHyangJeon.setGenera("로맨스");
		chunHyangJeon.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));
	}

	@Test
	@DisplayName("영화를 ID로 읽으면 바로 영화 1 건 올라온다.")
	void readMovieByIdTest() throws Exception {
		// arrange
		// act and expect(=assert)
		// @formatter:off -- act and expect(=assert)
		when(service.getMovieById(anyLong())).thenReturn(yulDolMok);
		
		mockMvc.perform
		(get("/movies/{id}", 1L))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is(yulDolMok.getName())))
			.andExpect(jsonPath("$.genera", is(yulDolMok.getGenera())))
			.andExpect(jsonPath("$.releaseDate", 
					is(yulDolMok.getReleaseDate().toString())));
	}
	
	@Test
	@DisplayName("모든 영화 읽으면 2 건이 올라온다.")
	void readAllMoviesTest() throws Exception {
		// arrange
		List<Movie> movieList = new ArrayList<Movie>();
		movieList.add(chunHyangJeon);
		movieList.add(yulDolMok);
		
		// act and expect(=assert)
		when(service.getAllMovies()).thenReturn(movieList);
		
		mockMvc.perform
		(get("/movies"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.*", hasSize(movieList.size())));
	}

	@Test
	@DisplayName("영화 생성하면 DB에 저장된다.")
	void createTest() throws Exception {
		// arrange
		when(service.save(any(Movie.class))).thenReturn(yulDolMok);
		
		mockMvc.perform
		(post("/movies")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(yulDolMok))
		).andExpect(status().isCreated())
		 .andExpect(jsonPath("$.name", is(yulDolMok.getName())))
		 .andExpect(jsonPath("$.genera", is(yulDolMok.getGenera())))
		 .andExpect(jsonPath("$.releaseDate", 
				 is(yulDolMok.getReleaseDate().toString())));
		// @formatter:on
	}
}
