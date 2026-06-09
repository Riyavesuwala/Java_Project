/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBean;

import Client.RestClient;
import Entity.Users;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 *
 * @author riya vesuwala
 */
@Named
@SessionScoped
public class ForgotPasswordBean implements Serializable {

    private String username;

    private Users user;

    private RestClient rl = new RestClient();

    public String verifyUser() {

        try {

            user = rl.forgotPassword(
                                Users.class,
                                username);
            
            System.out.println("FOUND USER = " + user);

            if(user == null){

                FacesContext.getCurrentInstance()
                        .addMessage(
                                null,
                                new FacesMessage(
                                        FacesMessage.SEVERITY_ERROR,
                                        "Error",
                                        "User not found"
                                ));

                return null;
            }

            return "/public/resetPassword.xhtml?faces-redirect=true";

        } catch(Exception e){

            e.printStackTrace();

            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "Error",
                                    "User not found"
                            ));

            return null;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}