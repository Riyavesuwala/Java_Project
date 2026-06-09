/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDIBean;

import Client.RestClient;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riya vesuwala
 */
@Named("notificationBean")
@SessionScoped
public class NotificationBean implements Serializable {

    private List<List<Object>> notifications =
            new ArrayList<>();

    private RestClient rl = new RestClient();

    @Inject
    private LoginBean loginBean;

    @PostConstruct
    public void init() {
        loadNotifications();
    }

    public void loadNotifications() {

        try {

            Integer userId =
                    loginBean.getLoggedInUser()
                             .getUserId();

            notifications =
                    rl.getCitizenNotifications(
                            List.class,
                            String.valueOf(userId),
                            loginBean.getToken());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public List<List<Object>> getNotifications() {

        loadNotifications();
        return notifications;
    }
}
