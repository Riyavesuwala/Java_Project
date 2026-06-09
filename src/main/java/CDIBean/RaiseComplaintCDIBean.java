/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBean;

import Client.RestClient;
import Entity.ComplaintCategory;
import Entity.Society;
import Entity.Users;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riya vesuwala
 */

@Named("raiseComplaintBean")
@ViewScoped
public class RaiseComplaintCDIBean implements Serializable {

    private Integer wardId;

    private String title;
    private String description;
    private String priority;

    private Integer categoryId;
    private Integer societyId;

    private List<Society> societies = new ArrayList<>();
    private List<ComplaintCategory> categories = new ArrayList<>();

    private RestClient rl = new RestClient();

    @Inject
    private LoginBean loginBean;

    public void loadPage() {

        try {

            System.out.println("Loading Raise Complaint Page");

            if (loginBean == null) {
                System.out.println("loginBean is NULL");
                return;
            }

            Users user = loginBean.getLoggedInUser();

            if (user == null) {
                System.out.println("loggedInUser is NULL");
                return;
            }

            System.out.println("Logged User = "
                    + user.getUsername());

            System.out.println("TOKEN FROM LOGIN = "
        + loginBean.getToken());
            loadCategories();
            System.out.println("Total Categories = " + categories.size());

            if (user.getSocietyId() != null) {

                societyId =
                        user.getSocietyId()
                                .getSocietyId();

                /*
                 * ONLY if Society entity contains Ward
                 */

                if (user.getSocietyId().getWardId() != null) {

                    wardId =
                            user.getSocietyId()
                                    .getWardId()
                                    .getWardId();

                    loadSocieties();
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void loadCategories() {

        try {

            categories =
            rl.getAllCategories(
                    List.class,
                    loginBean.getToken());

            System.out.println(
                    "Categories Loaded = "
                            + categories.size());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void loadSocieties() {

        try {

            if (wardId == null) {
                return;
            }

            societies =
                    rl.getAllSocities(
                            List.class,
                            String.valueOf(wardId));

            System.out.println(
                    "Societies Loaded = "
                            + societies.size());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void submitComplaint() {

        try {

            Users user =
                    loginBean.getLoggedInUser();

            if (user == null) {

                FacesContext.getCurrentInstance()
                        .addMessage(
                                null,
                                new FacesMessage(
                                        FacesMessage.SEVERITY_ERROR,
                                        "Error",
                                        "Please login first"
                                ));

                return;
            }

            rl.createComplaint(
                String.valueOf(user.getUserId()),
                String.valueOf(categoryId),
                String.valueOf(societyId),
                String.valueOf(wardId),
                title,
                description,
                "PENDING",
                priority,
                loginBean.getToken()
        );

            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_INFO,
                                    "Success",
                                    "Complaint submitted successfully"
                            ));

            clearForm();

        } catch (Exception e) {

            e.printStackTrace();

            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "Error",
                                    "Unable to submit complaint"
                            ));
        }
    }

    private void clearForm() {

        title = "";
        description = "";
        priority = "MEDIUM";
        categoryId = null;
    }

    // Getters & Setters

    public Integer getWardId() {
        return wardId;
    }

    public void setWardId(Integer wardId) {
        this.wardId = wardId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSocietyId() {
        return societyId;
    }

    public void setSocietyId(Integer societyId) {
        this.societyId = societyId;
    }

    public List<Society> getSocieties() {
        return societies;
    }

    public void setSocieties(List<Society> societies) {
        this.societies = societies;
    }

    public List<ComplaintCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ComplaintCategory> categories) {
        this.categories = categories;
    }
}