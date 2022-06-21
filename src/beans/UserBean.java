package beans;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import dao.UsersDAO;
import dao.interfaces.IUsersDAO;
import models.User;
import rmi.IUserRMIService;

@ManagedBean(name="user")
@SessionScoped
public class UserBean {

	private IUsersDAO usersDAO = new UsersDAO();
	
	private String email;
	private String username;
	private String password;
	private String confirm_password;
	private int role;
	
	
	private UIComponent password_component;
	private UIComponent username_component;
	private UIComponent email_component;
    
	
	
	public UIComponent getEmail_component() {
		return email_component;
	}

	public void setEmail_component(UIComponent email_component) {
		this.email_component = email_component;
	}

	public UIComponent getUsername_component() {
		return username_component;
	}

	public void setUsername_component(UIComponent username_component) {
		this.username_component = username_component;
	}

	public UIComponent getPassword_component() {
		return password_component;
	}

	public void setPassword_component(UIComponent password_component) {
		this.password_component = password_component;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm_password() {
		return confirm_password;
	}

	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String signup() throws MalformedURLException, RemoteException, NotBoundException {
		
		IUserRMIService server = (IUserRMIService) Naming.lookup("rmi://localhost:1099/Server");
		
		if (password.equals(confirm_password)) {
			
			if (server.validateEmail(email)) {
				
				if (server.validateUsername(username)) {
					
					User u = new User(0, email, username, password, 0);
					Boolean b = server.register(u);
					
					if (b) {
						this.role = 0;
						return "index.jsf?faces-redirect=true";	
					}
						
				}
				
				this.emptyUser();
				FacesContext.getCurrentInstance().addMessage(username_component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username already in use", null));
				return null;
			}
			
			this.emptyUser();
			FacesContext.getCurrentInstance().addMessage(email_component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email already in use", null));
			return null;
			
		}
		
		this.emptyUser();
		FacesContext.getCurrentInstance().addMessage(password_component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords don't match", null));
		return null;
		
	}
	
	public String login() {
		
		User u = usersDAO.getUser(username, password);
		if (u != null) {
			
			this.email = u.getEmail();
			this.role = u.getRole();
			return "index.jsf?faces-redirect=true";
		}
		
		this.emptyUser();
		FacesContext.getCurrentInstance().addMessage(username_component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong username or password", null));
		return null;
	}
	public String logout() {
		emptyUser();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.jsf?faces-redirect=true";
    }
	
	private void emptyUser() {
		
		this.email = null;
		this.username = null;
		this.password = null;
		this.confirm_password = null;
		
	}

	public Boolean isLogged() {
		
		if (username == null || username.length() == 0) {
			return false;
		}
		
		return true;
	}
	
	public Boolean isAdmin() {
		if (this.role == 1)
			return true;
		return false;
	}
	
	public String editProfile() {
		
		return "editprofile.jsf?faces-redirect=true&u="+username;
	}
	public String changePassword() {
		return "changepassword.jsf?faces-redirect=true";
	}
	
}
