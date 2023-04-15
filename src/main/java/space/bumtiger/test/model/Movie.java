package space.bumtiger.test.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_MOVIES")
public class Movie implements Cloneable{
	
	@Id
	private Long id;
	
	private String name;
	
	private String genera;
	
	private LocalDate releaseDate;
	
	public Movie clone() throws CloneNotSupportedException {
		return (Movie) super.clone();
	}
}
