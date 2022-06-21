package dao.interfaces;

import java.util.List;

import models.Review;


public interface IReviewsDAO {
	
	Boolean addReview(Review r);
	Review getReview(int user_id, int movie_id);
	List<Review> getReviews(int movie_id);

}
