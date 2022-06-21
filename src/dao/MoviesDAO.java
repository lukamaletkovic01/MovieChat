package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import connection.ConnectionProvider;
import dao.interfaces.IMoviesDAO;
import models.Movie;

public class MoviesDAO implements IMoviesDAO{

	
	@Override
	public Boolean addMovie(Movie m) {
		
		int status = 0;
		Connection conn = ConnectionProvider.getConn();
		
		try {
			PreparedStatement ps = conn.prepareStatement("insert into movies values(null, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, m.getTitle());
			ps.setString(2, m.getDescription());
			ps.setInt(3, m.getRelease_year());
			ps.setString(4, m.getPoster_url());
			ps.setString(5, m.getTrailer_url());
			ps.setInt(6, m.getClicks());
			status = ps.executeUpdate();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		if (status > 0)
			return true;
		
		return false;
	
	}

	@Override
	public Boolean updateMovie(Movie m) {
		
		int status = 0;
		Connection conn = ConnectionProvider.getConn();
		try {
			
			PreparedStatement ps = conn.prepareStatement("update movies set title=?, description=?, release_year=?, poster_url=?, trailer_url=? where id=?");
			ps.setString(1, m.getTitle());
			ps.setString(2, m.getDescription());
			ps.setInt(3, m.getRelease_year());
			ps.setString(4, m.getPoster_url());
			ps.setString(5, m.getTrailer_url());
			
			ps.setInt(6, m.getId());
			
			status = ps.executeUpdate();
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		if (status > 0)
			return true;
		
		return false;
	}

	@Override
	public Boolean deleteMovie(Movie m) {
		
		Connection conn = ConnectionProvider.getConn();
		
		try {
			
			PreparedStatement ps = conn.prepareStatement("delete from movies where id=?");
			ps.setInt(1, m.getId());
			ps.executeUpdate();
			return true;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Movie getMovie(int id) {
		
		Connection conn = ConnectionProvider.getConn();		
		try {
			
			PreparedStatement ps = conn.prepareStatement("select movies.*, avg(reviews.mark) as average_rating from movies left join reviews on movies.id=reviews.movie_id where id=? group by movies.id");
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String title = rs.getString("title").trim();
				String description = rs.getString("description").trim();
				int release_year = rs.getInt("release_year");
				String poster_url = rs.getString("poster_url").trim();
				String trailer_url = rs.getString("trailer_url").trim();
				int clicks = rs.getInt("clicks");
				float average = rs.getFloat("average_rating");
				
				Movie m = new Movie(id, title, description, release_year, poster_url, trailer_url, clicks);
				m.setAverageRating(average);
				return m;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Movie> getMovies() {
		Connection conn = ConnectionProvider.getConn();		
		List<Movie> movies = null;
		try {
			
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery("select movies.*, avg(reviews.mark) as average_rating from movies left join reviews on reviews.movie_id=movies.id group by movies.id order by id desc");
			
			movies = new LinkedList<Movie>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title").trim();
				String description = rs.getString("description").trim();
				int release_year = rs.getInt("release_year");
				String poster_url = rs.getString("poster_url").trim();
				String trailer_url = rs.getString("trailer_url").trim();
				int clicks = rs.getInt("clicks");
				float average = rs.getFloat("average_rating");
				
				Movie m = new Movie(id, title, description, release_year, poster_url, trailer_url, clicks);
				m.setAverageRating(average);
				movies.add(m);
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return movies;
	}

	@Override
	public LinkedList<Movie> getMovies(String search) {
		Connection conn = ConnectionProvider.getConn();		
		List<Movie> movies = null;
		try {
			
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery("select movies.*, avg(reviews.mark) as average_rating from movies left join reviews on reviews.movie_id=movies.id where title like '%" + search + "%' group by movies.id order by id desc");
			
			movies = new LinkedList<Movie>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title").trim();
				String description = rs.getString("description").trim();
				int release_year = rs.getInt("release_year");
				String poster_url = rs.getString("poster_url").trim();
				String trailer_url = rs.getString("trailer_url").trim();
				int clicks = rs.getInt("clicks");
				float average = rs.getFloat("average_rating");
				
				Movie m = new Movie(id, title, description, release_year, poster_url, trailer_url, clicks);
				m.setAverageRating(average);
				movies.add(m);
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return (LinkedList<Movie>) movies;
	}
	
	
	

}
