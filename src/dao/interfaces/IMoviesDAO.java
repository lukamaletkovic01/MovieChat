package dao.interfaces;

import java.util.LinkedList;
import java.util.List;

import models.Movie;

public interface IMoviesDAO {

	Boolean addMovie(Movie m);
	Boolean updateMovie(Movie m);
	Boolean deleteMovie(Movie m);
	Movie getMovie(int id);
	List<Movie> getMovies();
	LinkedList<Movie> getMovies(String search);
}
