/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.organizer.api;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
/**
 *
 * @author orus-kade
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;
    /**
    * Создание фабрики
    *
    */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
        // loads configuration and mappings
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            //metadataSources.addAnnotatedClass(TestEntity.class);// Аннотированная сущность
            metadataSources.addResource("named-queries.hbm.xml");// Именованные запросы
            sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
        }
        return sessionFactory;
    }
   
}

