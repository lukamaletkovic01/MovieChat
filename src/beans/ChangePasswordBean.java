package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import dao.UsersDAO;
import dao.interfaces.IUsersDAO;
import models.User;

@ManagedBean(name="changepassword")
public class ChangePasswordBean {

	private IUsersDAO usersDAO = new UsersDAO();
	
	@ManagedProperty("#{user.username}")
	private String username;
	
	private String currentPassword;
	private String newPassword;
	private String confirmNewPassword;
	
	private UIComponent password_component;
	private UIComponent component;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
	public UIComponent getPassword_component() {
		return password_component;
	}
	public void setPassword_component(UIComponent password_component) {
		this.password_component = password_component;
	}
	
	public UIComponent getComponent() {
		return component;
	}
	public void setComponent(UIComponent component) {
		this.component = component;
	}
	public void changePassword() {
		
		if (newPassword.equals(confirmNewPassword)) {
			
			if (usersDAO.getUser(username, currentPassword) != null) {
				
				User u = usersDAO.getUser(username);
				u.setPassword(newPassword);
				if (usersDAO.updatePassword(u))
					FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "You have successfully changed password ", null));
				else
					FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error occured ", null));

			
			}
			else {
				
				FacesContext.getCurrentInstance().addMessage(password_component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong password ", null));
			
			}
			
			
		}
		else {
			FacesContext.getCurrentInstance().addMessage(password_component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords don't match", null));
		}
		
		
	}
	
}
