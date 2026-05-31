/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBean;

import Client.RestClient;
import Entity.Users;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;

/**
 *
 * @author riya vesuwala
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    RestClient rl = new RestClient();

    private String username;
    private String password;

    private Users loggedInUser;

    Response rs;

    public LoginBean() {
    }

   public String login() {

    try {

        if (username == null || username.trim().isEmpty()) {
            addError("Username is required");
            return null;
        }

        if (username.length() < 4) {
            addError("Username must be at least 4 characters");
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            addError("Password is required");
            return null;
        }

        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            addError("Invalid password format");
            return null;
        }

        Users requestUser = new Users();
        requestUser.setUsername(username);
        requestUser.setPassword(password);

        Response rs = rl.login(requestUser);

        if (rs.getStatus() != 200) {

            addError("Invalid Username or Password");
            return null;
        }

        loggedInUser = rs.readEntity(Users.class);

        if (loggedInUser == null) {

            addError("Invalid Username or Password");
            return null;
        }

        if ("Admin".equalsIgnoreCase(loggedInUser.getRole())) {
            return "/admin/dashboard.xhtml?faces-redirect=true";
        }

        if ("Officer".equalsIgnoreCase(loggedInUser.getRole())) {
            return "/officer/dashboard.xhtml?faces-redirect=true";
        }

        if ("Citizen".equalsIgnoreCase(loggedInUser.getRole())) {
            return "/citizen/dashboard.xhtml?faces-redirect=true";
        }

    } catch (Exception e) {

        e.printStackTrace();
        addError("Invalid Username or Password");
    }

    return null;
}

    public String logout() {

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();

        return "/login.xhtml?faces-redirect=true";
    }
    
    private void addError(String msg) {

    FacesContext.getCurrentInstance().addMessage(
            null,
            new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Login Failed",
                    msg
            )
    );
}

    // Getters and Setters

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

    public Users getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Users loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}

