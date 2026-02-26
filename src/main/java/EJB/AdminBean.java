/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package EJB;

import Entity.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author krishnaiya
 */
@Stateless
public class AdminBean implements AdminBeanLocal {

    @PersistenceContext(unitName="jpu1")
    EntityManager em;
    
    @Override
    public void createZone(Zone zone) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            em.persist(zone);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void updateZone(Integer zoneId, String zoneName, String status, Integer corporationId) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            Zone zone=em.find(Zone.class, zoneId);
            if(zone!=null)
            {
                zone.setZoneName(zoneName);
                zone.setStatus(status);
                Corporation corporation=em.find(Corporation.class, corporationId);
                zone.setCorporationId(corporation);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteZone(Integer zoneId) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try{
            Zone zone=em.find(Zone.class, zoneId);
        
            if(zone!=null)
            {
                em.remove(zone);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void createDepartment(Departments dept) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try {
            em.persist(dept);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDepartment(Integer id, String name, String desc, String status) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try {
            Departments dept = em.find(Departments.class, id);
            if (dept != null) {
                dept.setDepartmentName(name);
                dept.setDescription(desc);
                dept.setStatus(status);
                em.merge(dept);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDepartment(Integer id) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try {
            Departments dept = em.find(Departments.class, id);
            if (dept != null) {
                em.remove(dept);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createOfficer(Officers officer) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try {
            em.persist(officer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOfficer(Integer id, Integer userId, Integer departmentId, Integer wardId, Integer zoneId, String designation) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try {
            Officers officer = em.find(Officers.class, id);

            if (officer != null) {

                Users user = em.find(Users.class, userId);
                Departments dept = em.find(Departments.class, departmentId);
                Ward ward = em.find(Ward.class, wardId);
                Zone zone = em.find(Zone.class, zoneId);

                officer.setUserId(user);
                officer.setDepartmentId(dept);
                officer.setWardId(ward);
                officer.setZoneId(zone);
                officer.setDesignation(designation);

                em.merge(officer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOfficer(Integer id) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try {
            Officers officer = em.find(Officers.class, id);
            if (officer != null) {
                em.remove(officer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createSociety(Society society) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try {
            em.persist(society);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSociety(Integer id, String name, String address, String status, Integer wardId) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try {
            Society soc = em.find(Society.class, id);
            if (soc != null) {

                Ward ward = em.find(Ward.class, wardId);

                soc.setSocietyName(name);
                soc.setAddress(address);
                soc.setStatus(status);
                soc.setWardId(ward);

                em.merge(soc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSociety(Integer id) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try {
            Society soc = em.find(Society.class, id);
            if (soc != null) {
                em.remove(soc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCategory(ComplaintCategory category) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try {
            em.persist(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCategory(Integer id, String name, Integer departmentId) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try {
            ComplaintCategory cat = em.find(ComplaintCategory.class, id);
            if (cat != null) {

                Departments dept = em.find(Departments.class, departmentId);

                cat.setCategoryName(name);
                cat.setDepartmentId(dept);
                em.merge(cat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCategory(Integer id) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        try {
            ComplaintCategory cat = em.find(ComplaintCategory.class, id);
            if (cat != null) {
                em.remove(cat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
