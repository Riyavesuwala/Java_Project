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
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 *
 * @author riya vesuwala
 */

@Named
@SessionScoped
public class ResetPasswordBean implements Serializable {

    private String password;
    private String confirmPassword;

    @Inject
    private ForgotPasswordBean forgotPasswordBean;
    
    @Inject
    private LoginBean loginBean;

    private RestClient rl = new RestClient();

    public String resetPassword() {

        try {

            if(!password.equals(confirmPassword)){

                FacesContext.getCurrentInstance()
                        .addMessage(
                                null,
                                new FacesMessage(
                                        FacesMessage.SEVERITY_ERROR,
                                        "Error",
                                        "Passwords do not match"
                                ));

                return null;
            }
            
            Users user = forgotPasswordBean.getUser();
            
            System.out.println("USER = " + user);

            rl.resetPassword(
                    String.valueOf(user.getUserId()),
                    password,
                    loginBean.getToken()
            );

            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_INFO,
                                    "Success",
                                    "Password changed successfully"
                            ));

            return "/public/login.xhtml?faces-redirect=true";

        } catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}