/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.organizer.api;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author orus-kade
 */
public class HibernateDataProviderTest {
    
    public HibernateDataProviderTest() {
    }
    
    @Test
    public void testGetTablies() {
        System.out.println("getTables");
        HibernateDataProvider hdp = new HibernateDataProvider();
        List results = hdp.getTables();
        results .forEach(res ->{
            System.out.println(res);
        });
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getTables method, of class HibernateDataProvider.
     */
    @Test
    public void testGetTables() {
        System.out.println("getTables");
        HibernateDataProvider instance = new HibernateDataProvider();
        List expResult = null;
        List result = instance.getTables();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
