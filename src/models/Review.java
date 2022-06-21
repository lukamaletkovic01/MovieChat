package models;

import java.time.LocalDateTime;

public class Review {

	private int user_id;
	private String username;
	private int movie_id;
	private int mark;
	private String text;
	private LocalDateTime created;
	
	public Review() {}
	
	public Review(int user_id, int movie_id, int mark, String text, LocalDateTime created) {
		super();
		this.user_id = user_id;
		this.movie_id = movie_id;
		this.mark = mark;
		this.text = text;
		this.created = created;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
	
	
}
