package CDIBean;

import Entity.Society;
import Client.RestClient;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Named(value = "registerBean")
@SessionScoped
public class RegisterBean implements Serializable {

    private String fullname;
    private String email;
    private String mobile;
    private String username;
    private String password;
    private String confirmPassword;
    private Integer societyId;

    private Collection<Society> societies;

    private RestClient rl = new RestClient();

    public RegisterBean() {
    }

    @PostConstruct
    public void init() {

        try {

            Response rs = rl.getAllSocities(Response.class, "1");

            societies = rs.readEntity(
                    new GenericType<Collection<Society>>() {
                    });

        } catch (Exception e) {

            e.printStackTrace();
            societies = new ArrayList<>();
        }
    }

    public void register() {

        try {

            // Full Name Validation
            if (fullname == null || fullname.trim().isEmpty()) {
                addError("Full Name is required");
                return;
            }

            if (!fullname.matches("^[A-Za-z ]{3,50}$")) {
                addError("Full Name must contain only letters and spaces");
                return;
            }

            // Email Validation
            if (email == null || email.trim().isEmpty()) {
                addError("Email is required");
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                addError("Invalid Email Address");
                return;
            }

            // Mobile Validation
            if (mobile == null || mobile.trim().isEmpty()) {
                addError("Mobile Number is required");
                return;
            }

            if (!mobile.matches("\\d{10}")) {
                addError("Mobile Number must be exactly 10 digits");
                return;
            }

            // Username Validation
            if (username == null || username.trim().isEmpty()) {
                addError("Username is required");
                return;
            }

            if (username.length() < 4) {
                addError("Username must be at least 4 characters");
                return;
            }

            // Password Validation
            if (password == null || password.trim().isEmpty()) {
                addError("Password is required");
                return;
            }

            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
                addError("Password must contain Uppercase, Lowercase, Number and be 8+ characters");
                return;
            }

            // Confirm Password Validation
            if (confirmPassword == null || confirmPassword.isEmpty()) {
                addError("Confirm Password is required");
                return;
            }

            if (!password.equals(confirmPassword)) {
                addError("Password and Confirm Password do not match");
                return;
            }

            // Society Validation
            if (societyId == null) {
                addError("Please select a Society");
                return;
            }

            // Register User
            Response response = rl.registerUser(
                    fullname,
                    email,
                    mobile,
                    username,
                    password,
                    String.valueOf(societyId)
            );

            if (response.getStatus() == 200) {

                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                "Registration Successful",
                                "Citizen account created successfully."
                        )
                );
                clearForm();

            } else {

                String errorMessage = response.readEntity(String.class);

                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "Registration Failed",
                                errorMessage
                        )
                );

                return;
            }

        } catch (Exception e) {

            e.printStackTrace();

            addError("Email or Username already exists");
        }
    }

    private void addError(String message) {

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Validation Error",
                        message
                )
        );
    }

    private void clearForm() {

        fullname = "";
        email = "";
        mobile = "";
        username = "";
        password = "";
        confirmPassword = "";
        societyId = null;
    }

    // ================= GETTERS & SETTERS =================

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Integer getSocietyId() {
        return societyId;
    }

    public void setSocietyId(Integer societyId) {
        this.societyId = societyId;
    }

    public Collection<Society> getSocieties() {
        return societies;
    }

    public void setSocieties(Collection<Society> societies) {
        this.societies = societies;
    }
}