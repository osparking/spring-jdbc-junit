package space.bumtiger.test.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import space.bumtiger.test.model.Movie;
import space.bumtiger.test.reposi.MovieRepository;

@Service
@RequiredArgsConstructor
public class MovieService {

	private final MovieRepository movieRepository;

	public Movie save(Movie movie) {
		return movieRepository.save(movie);
	}

	public Iterable<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	public Movie getMovieById(Long id) {
		return movieRepository.findById(id).orElseThrow(
				() -> new RuntimeException("찾으려 했던 영화 ID: " + id));
	}

	public Movie updateMovie(Movie movie, Long id) {
		Movie existingMovie = movieRepository.findById(id).get();
//		existingMovie.setGenera(movie.getGenera());
		existingMovie.setName(movie.getName());
		existingMovie.setReleaseDate(movie.getReleaseDate());
		return movieRepository.save(existingMovie);
	}

	public void deleteMovie(Long id) {
		Movie existingMovie = movieRepository.findById(id).get();
		movieRepository.delete(existingMovie);

	}
}
