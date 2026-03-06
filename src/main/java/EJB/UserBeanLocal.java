/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SessionLocal.java to edit this template
 */
package EJB;

import Entity.Users;
import jakarta.ejb.Local;

/**
 *
 * @author riya vesuwala
 */
@Local
public interface UserBeanLocal {
    void registerUser(Users user);
    Users login(String username,String password);
    Users getUserById(int userId);
}
