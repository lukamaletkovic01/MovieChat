package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;

import connection.ConnectionProvider;
import dao.interfaces.IReviewsDAO;
import models.Review;

public class ReviewsDAO implements IReviewsDAO{

	@Override
	public Boolean addReview(Review r) {
		
		int status = 0;
		Connection conn = ConnectionProvider.getConn();
		
		try {
			PreparedStatement ps = conn.prepareStatement("insert into reviews values(?, ?, ?, ?, ?)");
			ps.setInt(1, r.getUser_id());
			ps.setInt(2, r.getMovie_id());
			ps.setInt(3, r.getMark());
			ps.setString(4, r.getText());
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String formattedDateTime = r.getCreated().format(formatter);

			ps.setString(5, formattedDateTime);
			
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
	public Review getReview(int user_id, int movie_id) {
		Connection conn = ConnectionProvider.getConn();		
		try {
			
			PreparedStatement ps = conn.prepareStatement("select * from reviews where user_id=? and movie_id=?");
			ps.setInt(1, user_id);
			ps.setInt(2, movie_id);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String text = rs.getString("text").trim();
				int mark = rs.getInt("mark");
				LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
				Review m = new Review(user_id, movie_id, mark, text, created);
				
				return m;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Review> getReviews(int movie_id) {
		Connection conn = ConnectionProvider.getConn();		
		List<Review> reviews = null;
		try {
			
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery("select reviews.*, users.username from reviews "
					+ " inner join users on reviews.user_id=users.id" 
					+ " where movie_id="+ movie_id 
					+ " order by created desc");
			
			reviews = new LinkedList<Review>();
			while (rs.next()) {
				int user_id = rs.getInt("user_id");
				int mark = rs.getInt("mark");
				String text = rs.getString("text");
				String username = rs.getString("username");
				LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
				Review m = new Review(user_id, movie_id, mark, text, created);
				m.setUsername(username);
				reviews.add(m);
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return (LinkedList<Review>) reviews;
	}

}
