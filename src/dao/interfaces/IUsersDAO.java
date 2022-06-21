package dao.interfaces;

import models.User;

public interface IUsersDAO {
	Boolean addUser(User u);
	User getUser(String username, String password);
	int getId(String username);
	User getUser(String username);
	Boolean updateUser(User u);
	boolean updatePassword(User u);
}
