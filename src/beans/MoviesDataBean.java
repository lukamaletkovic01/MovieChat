package beans;

import java.util.LinkedList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import dao.MoviesDAO;
import dao.interfaces.IMoviesDAO;
import models.Movie;

@ManagedBean(name="movieRecords")
@SessionScoped
public class MoviesDataBean {
	
	private IMoviesDAO moviesDAO = new MoviesDAO();
	private LinkedList<Movie> movies = (LinkedList<Movie>) moviesDAO.getMovies();
	private String searchQuery = "";
	private Movie currentMovie;
	
	private UIComponent component;
	
	public UIComponent getComponent() {
		return component;
	}


	public void setComponent(UIComponent component) {
		this.component = component;
	}


	public Movie getCurrentMovie() {
		return currentMovie;
	}


	public void setCurrentMovie(Movie currentMovie) {
		this.currentMovie = currentMovie;
	}


	public LinkedList<Movie> getMovies(){
		if (searchQuery == null || searchQuery.isEmpty())
			movies = (LinkedList<Movie>) moviesDAO.getMovies();
		else
			movies = moviesDAO.getMovies(searchQuery);
		return movies;
	}

	public String getSearchQuery() {
		return searchQuery;
	}


	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}


	public String openChatRoom(int id) {

		this.currentMovie = moviesDAO.getMovie(id);
		return "chatroom.jsf?faces-redirect=true&id=" + id;
	}
	public String openReviews(int id) {
		this.currentMovie = moviesDAO.getMovie(id);
		return "reviews.jsf?faces-redirect=true&id=" + id;
	}
	
	public String search() {
		movies = moviesDAO.getMovies(searchQuery);
		return "index.jsf";
	}
	
	public String redirectNewMovie() {
		
		return "addmovie.jsf?faces-redirect=true";
	}
	public String redirectEditMovie(int id) {
		this.currentMovie = moviesDAO.getMovie(id);
		return "editmovie.jsf?faces-redirect=true";
	}
	public void deleteMovie(int id) {
		
		Movie m = moviesDAO.getMovie(id);
		moviesDAO.deleteMovie(m);
		movies = (LinkedList<Movie>) moviesDAO.getMovies();
	}
	
	public void editMovie() {
		
		Boolean b = moviesDAO.updateMovie(currentMovie);
		if (b) {
			FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_INFO, "You have successfully edited a movie ", null));
			movies = (LinkedList<Movie>) moviesDAO.getMovies();
		}
		else
			FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred ", null));
	}
	public Boolean hasQuery() {
		if (this.searchQuery == null || this.searchQuery.isEmpty())
			return false;
		return true;
	}
}
