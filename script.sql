START TRANSACTION;

drop table if exists reviews;
drop table if exists movies;
drop table if exists users;

-- movies
create table movies(
	id int AUTO_INCREMENT,
	title varchar(100),
	description varchar(300),
	release_year int,
	poster_url varchar(150),
	trailer_url varchar(150),
	clicks int,
	PRIMARY KEY (id)
);
insert into movies values (null, "The Way Back", "Jack Cunningham was a high school basketball phenom who walked away from the game, forfeiting his future. Years later, when he reluctantly accepts a coaching job at his alma mater, he may get one last shot at redemption.", 2020, "https://m.media-amazon.com/images/I/71GVGBBQE-L._AC_SY550_.jpg", "https://www.youtube.com/watch?v=VzNJVSsjE-I", 0),
	(null, "Dune", "Feature adaptation of Frank Herbert's science fiction novel about the son of a noble family entrusted with the protection of the most valuable asset and most vital element in the galaxy.", 2021, "https://m.media-amazon.com/images/I/61ux6FzCdGL._AC_SL1280_.jpg", "https://www.youtube.com/watch?v=8g18jFHCLXk", 0),
	(null, "Don't Look Up", "Two low-level astronomers must go on a giant media tour to warn mankind of an approaching comet that will destroy planet Earth.", 2021, "https://m.media-amazon.com/images/M/MV5BNjZjNDE1NTYtYTgwZS00M2VmLWEyODktM2FlNjhiYTk3OGU2XkEyXkFqcGdeQXVyMTEyMjM2NDc2._V1_.jpg", "https://www.youtube.com/watch?v=RbIxYm3mKzI", 0);

-- users
create table users(
	id int AUTO_INCREMENT,
	email varchar(50),
	username varchar(50),
	password varchar(50),
	role int,
	PRIMARY KEY (id)
);
insert into users values(null, "admin@gmail.com", "admin", "6C7CA345F63F835CB353FF15BD6C5E052EC08E7A", 1),
	(null, "user1@gmail.com", "korisnik1", "1EFCC2DD7906F4870A0BEB5FAC3C5DA3ABB6C2D2", 0),
	(null, "user2@gmail.com", "korisnik2", "A175647DB970F1FC08D5D895E883D9F8B3AF869F", 0);

-- reviews
create table reviews(
	user_id int,
	movie_id int,
	mark int,
	text varchar(300),
	created DATETIME,
	PRIMARY KEY (user_id, movie_id),
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (movie_id) REFERENCES movies(id)
);
insert into reviews values (2, 3, 3, "Ultimately, Don't Look Up is more than just mean-spirited and smug. It's aggressively those things -- that is, until that sentimental coda.", "2022-01-17 12:35:00"),
	(3, 3, 4, "Very silly, yet undeniably urgent. I loved every second.", "2022-01-17 12:35:00"),
	(2, 2, 5, "When the anticipation is feverish, it sets up movies to disappoint and fail. This long-awaited movie smashes those expectations.", "2022-01-17 12:35:00");

COMMIT;
