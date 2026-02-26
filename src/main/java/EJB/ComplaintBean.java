/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package EJB;

import Entity.Complaint;
import Entity.Users;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author riya vesuwala
 */
@Stateless
public class ComplaintBean implements ComplaintBeanLocal {
    @PersistenceContext(unitName="jpu1")
    EntityManager em;
    
    @Override
    public void registerComplaint(Complaint complaint) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try {
            em.persist(complaint);
            System.out.println("Complaint Registered Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public List<Complaint> getCitizenComplaints(Integer citizenId) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            return em.createQuery("select c from Complaint c where c.citizenId.userId=:cid").setParameter("cid",citizenId).getResultList();
        }catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public Complaint trackComplaint(Integer citizenId) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            return (Complaint)em.createQuery("select c from Complaint c where c.citizenId.userId=:cid order by c.complaintId desc").setParameter("cid",citizenId).setMaxResults(1).getSingleResult();
        }catch(Exception e)
        {
            return null;
        }
    }   

    
}
