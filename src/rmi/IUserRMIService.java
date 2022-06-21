package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import models.User;

public interface IUserRMIService extends Remote{

	public Boolean validateEmail(String email) throws RemoteException;
	public Boolean validateUsername(String username) throws RemoteException;
	public Boolean register(User u) throws RemoteException;
	
	
	
}
