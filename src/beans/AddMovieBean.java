package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import dao.MoviesDAO;
import dao.UsersDAO;
import dao.interfaces.IMoviesDAO;
import dao.interfaces.IUsersDAO;
import models.Movie;

@ManagedBean(name="addmovie")
public class AddMovieBean {

	private String title;
	private String description;
	private int release_year;
	private String poster_url;
	private String trailer_url;
	
	private IMoviesDAO moviesDAO = new MoviesDAO();

	private UIComponent component;
	
	public UIComponent getComponent() {
		return component;
	}
	public void setComponent(UIComponent component) {
		this.component = component;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getRelease_year() {
		return release_year;
	}
	public void setRelease_year(int release_year) {
		this.release_year = release_year;
	}
	public String getPoster_url() {
		return poster_url;
	}
	public void setPoster_url(String poster_url) {
		this.poster_url = poster_url;
	}
	public String getTrailer_url() {
		return trailer_url;
	}
	public void setTrailer_url(String trailer_url) {
		this.trailer_url = trailer_url;
	}
	
	
	public void addNewMovie() {
		
		Movie m = new Movie(0, title, description, release_year, poster_url, trailer_url, 0);
		Boolean b = moviesDAO.addMovie(m);
		if (b) 
			FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_INFO, "You have successfully added a movie ", null));
		else
			FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred ", null));
	}
	
	
}
