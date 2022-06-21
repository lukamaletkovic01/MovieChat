package dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.ConnectionProvider;
import dao.interfaces.IUsersDAO;
import models.Movie;
import models.User;

public class UsersDAO implements IUsersDAO{

	@Override
	public Boolean addUser(User u) {
		int status = 0;
		Connection conn = ConnectionProvider.getConn();
		
		try {
			PreparedStatement ps = conn.prepareStatement("insert into users values(null, ?, ?, ?, ?)");
			ps.setString(1, u.getEmail());
			ps.setString(2, u.getUsername());
			ps.setString(3, sha1(u.getPassword()));
			ps.setInt(4, u.getRole());
			
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
	public User getUser(String username, String password) {
		Connection conn = ConnectionProvider.getConn();		
		try {
			
			PreparedStatement ps = conn.prepareStatement("select * from users where username=? and password=?");
			ps.setString(1, username);
			ps.setString(2, sha1(password));
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String email = rs.getString("email").trim();
				int role = rs.getInt("role");
				
				User u = new User(id, email, username, password, role);
				
				return u;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String sha1(String input) {
		
		String sha1 = null;
		MessageDigest digest;
		
		try {
		
			digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			
			digest.update(input.getBytes("utf8"));
			sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return sha1;
	}

	@Override
	public int getId(String username) {
		Connection conn = ConnectionProvider.getConn();		
		try {
			
			PreparedStatement ps = conn.prepareStatement("select id from users where username=?");
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				
				return id;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return -1;	}

	@Override
	public User getUser(String username) {
		Connection conn = ConnectionProvider.getConn();		
		try {
			
			PreparedStatement ps = conn.prepareStatement("select * from users where username=?");
			ps.setString(1, username);
			
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String email = rs.getString("email").trim();
				int role = rs.getInt("role");
				String password = rs.getString("password");
				User u = new User(id, email, username, password, role);
				
				return u;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Boolean updateUser(User u) {
		int status = 0;
		Connection conn = ConnectionProvider.getConn();
		try {
			
			PreparedStatement ps = conn.prepareStatement("update users set email=?, username=? where id=?");
			ps.setString(1, u.getEmail());
			ps.setString(2, u.getUsername());
			ps.setInt(3, u.getId());
		
			
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
	public boolean updatePassword(User u) {
		int status = 0;
		Connection conn = ConnectionProvider.getConn();
		try {
			
			PreparedStatement ps = conn.prepareStatement("update users set password=? where id=?");
			ps.setString(1, sha1(u.getPassword()));
			ps.setInt(2, u.getId());
		
			
			status = ps.executeUpdate();
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		if (status > 0)
			return true;
		
		return false;
	}

}
