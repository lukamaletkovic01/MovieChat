package beans;

import java.time.LocalDateTime;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import dao.MoviesDAO;
import dao.ReviewsDAO;
import dao.UsersDAO;
import dao.interfaces.IMoviesDAO;
import dao.interfaces.IReviewsDAO;
import dao.interfaces.IUsersDAO;
import models.Review;

@ManagedBean(name="addreview")
public class AddReviewBean {

	private Integer rating;
	private String text;
	
	private IReviewsDAO reviewsDAO = new ReviewsDAO();
	private IUsersDAO usersDAO = new UsersDAO();

	private UIComponent component;
	
	
	public UIComponent getComponent() {
		return component;
	}
	public void setComponent(UIComponent component) {
		this.component = component;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getRating() {  
	return rating;  
	}  
	public void setRating(Integer rating) {  
	this.rating = rating;  
	}  
	
	
	public void addReview(String username, int movie_id) {
		
		if (!(username == null || username.isEmpty())) {
			
			int user_id = usersDAO.getId(username);
			
			if (reviewsDAO.getReview(user_id, movie_id) == null) {
				
				Review r = new Review(user_id, movie_id, rating, text, LocalDateTime.now());
				if(reviewsDAO.addReview(r))
					FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_INFO, "You have successfully added a review ", null));
				else
					FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_INFO, "An error occured ", null));
				
				
			}
			else
				FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_INFO, "You have already posted a review ", null));
		
			
		}
		else {
			FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "You must log in to leave a review ", null));
			
		}
		
		
	}
}
