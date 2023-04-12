package space.bumtiger.test.controller;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import space.bumtiger.test.model.Movie;
import space.bumtiger.test.reposi.MovieRepository;

@RestController
public class HomeController {
	@GetMapping("/")
	public String home() {
		var movie = new Movie();
		movie.setName("율돌목");
		movie.setGenera("전쟁");
		movie.setReleaseDate(LocalDate.of(2009, Month.NOVEMBER, 10));
		repository.save(movie);
		
		return "Greeting from spring-jdbc-junit";
	}
	
	@Autowired
	private MovieRepository repository;

}
