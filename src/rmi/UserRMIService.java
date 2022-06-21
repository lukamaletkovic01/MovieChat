package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionProvider;
import dao.UsersDAO;
import models.User;

public class UserRMIService extends UnicastRemoteObject implements IUserRMIService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserRMIService() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean validateEmail(String email) throws RemoteException {
		
		Connection conn = ConnectionProvider.getConn();
		
		try {
			
			PreparedStatement ps;
			ps = conn.prepareStatement("select * from users where email=?");
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return true;
	}

	@Override
	public Boolean validateUsername(String username) throws RemoteException {
		Connection conn = ConnectionProvider.getConn();
		
		try {
			
			PreparedStatement ps;
			ps = conn.prepareStatement("select * from users where username=?");
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return true;
	}

	@Override
	public Boolean register(User u) throws RemoteException {
		int status = 0;
		Connection conn = ConnectionProvider.getConn();
		
		try {
			PreparedStatement ps = conn.prepareStatement("insert into users values(null, ?, ?, ?, ?)");
			ps.setString(1, u.getEmail());
			ps.setString(2, u.getUsername());
			ps.setString(3, UsersDAO.sha1(u.getPassword()));
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



	

}
