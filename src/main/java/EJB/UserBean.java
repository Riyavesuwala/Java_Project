/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package EJB;

import Entity.Users;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Date;

/**
 *
 * @author riya vesuwala
 */
@Stateless
public class UserBean implements UserBeanLocal {
    @PersistenceContext(unitName="jpu1")
    EntityManager em;
    
    @Override
    public Users login(String username, String password) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            return em.createQuery("select u from Users u where u.username=:username and u.password=:password and u.status='Active'",Users.class).setParameter("username", username).setParameter("password", password).getSingleResult();
        }catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public void registerUser(Users user) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            user.setStatus("Active");
            user.setCreatedAt(new Date());

            em.persist(user);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Users getUserById(int userId) {
        
        Users user=em.find(Users.class, userId);
        if(user == null){
            System.out.println("User not found");
        }
        return user;
    }
    
}
