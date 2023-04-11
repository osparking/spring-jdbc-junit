package space.bumtiger.test.reposi;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import space.bumtiger.test.model.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long> {
	List<Movie> findByGenera(String genera);
}
