package beans;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dao.ReviewsDAO;
import dao.interfaces.IReviewsDAO;
import models.Movie;
import models.Review;

@ManagedBean(name="reviewsBean")
public class ReviewsBean {

	private LinkedList<Review> reviews;
	private IReviewsDAO reviewsDAO = new ReviewsDAO();
	
	public ReviewsBean() {
		
		Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
		String param = parameterMap.get("id");
		if (param != null)
			this.reviews = (LinkedList<Review>) reviewsDAO.getReviews(Integer.parseInt(param));
		
		
	}

	public LinkedList<Review> getReviews() {
		return reviews;
	}

	public void setReviews(LinkedList<Review> reviews) {
		this.reviews = reviews;
	}
	public String formatDate(LocalDateTime date) {
		return date.getDayOfMonth() + ". " + date.getMonthValue() + ". " + date.getYear() + ". " + date.getHour() + ":" + date.getMinute(); 
	}
	
	public Boolean hasReviews() {
		
		if (this.reviews == null || this.reviews.size() == 0)
			return false;
		
		return true;
	}
}
