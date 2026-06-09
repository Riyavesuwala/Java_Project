/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBean;

import Client.RestClient;
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
@Named("changePasswordBean")
@SessionScoped
public class ChangePasswordBean implements Serializable {

    private String password;
    private String confirmPassword;

    private RestClient rl = new RestClient();

    @Inject
    private LoginBean loginBean;

    public void changePassword() {

        try {

            if (!password.equals(confirmPassword)) {

                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "Passwords do not match",
                                null));

                return;
            }

            Integer userId =
                    loginBean.getLoggedInUser().getUserId();

            rl.resetPassword(
                    String.valueOf(userId),
                    password,
                    loginBean.getToken());

            password = "";
            confirmPassword = "";

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Password changed successfully",
                            null));

        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Unable to change password",
                            null));

            e.printStackTrace();
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