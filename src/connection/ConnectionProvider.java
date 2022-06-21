package connection;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import rmi.UserRMIService;

public class ConnectionProvider {

	private static Connection conn = null;
	private static UserRMIService userService;
	static {
		try {
			Class.forName(IProvider.DRIVER);
			conn = DriverManager.getConnection(IProvider.CONNECTION_URL, IProvider.USER, IProvider.PASSWORD);
			
			//rmi
			userService = new UserRMIService();
			LocateRegistry.createRegistry(1099);
			Naming.rebind("//localhost:1099/Server", userService);
			
			
			
		} catch (ClassNotFoundException | SQLException | RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Connection getConn() {
		return conn;
	}
}
