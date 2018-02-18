/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.organizer.api;

import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author orus-kade
 */
public class HibernateDataProvider {
    
    private static final Session session = HibernateUtil.getSessionFactory().openSession();
    
    public List getTables(){
        return session.getNamedNativeQuery("GET_TABLES").getResultList();
    }
    
}
