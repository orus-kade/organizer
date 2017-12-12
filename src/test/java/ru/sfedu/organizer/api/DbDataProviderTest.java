
package ru.sfedu.organizer.api;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.sfedu.organizer.model.*;

import ru.sfedu.organizer.model.Generic;
import ru.sfedu.organizer.model.Note;
import ru.sfedu.organizer.model.Result;

/**
 *
 * @author user
 */
public class DbDataProviderTest {
    
    public DbDataProviderTest() {
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
     * Test of addRecord method, of class DbDataProvider.
     */
    @Test
    public void testAddRecord() {
        System.out.println("addRecord");
        Note obj = null;
        DbDataProvider instance = new DbDataProvider();
        Result expResult = null;
        Result result = instance.addRecord(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editRecord method, of class DbDataProvider.
     */
    @Test
    public void testEditRecord() {
        System.out.println("editRecord");
        Note obj = null;
        DbDataProvider instance = new DbDataProvider();
        Result expResult = null;
        Result result = instance.editRecord(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteRecord method, of class DbDataProvider.
     */
    @Test
    public void testDeleteRecord() {
        System.out.println("deleteRecord");
        Note obj = null;
        DbDataProvider instance = new DbDataProvider();
        Result expResult = null;
        Result result = instance.deleteRecord(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRecordById method, of class DbDataProvider.
     */
    @Test
    public void testGetRecordById() {
        System.out.println("getRecordById");
        Generic obj = null;
        DbDataProvider instance = new DbDataProvider();
        Result expResult = null;
        Result result = instance.getRecordById(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllRecords method, of class DbDataProvider.
     */
    @Test
    public void testGetAllRecords() {
        System.out.println("getAllRecords");
        Generic obj = null;
        DbDataProvider instance = new DbDataProvider();
        Result expResult = null;
        Result result = instance.getAllRecords(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test 
    public void my_test(){
        try {
            DbDataProvider provider = new DbDataProvider();
            Aria aria = new Aria(1);
            Result result = provider.getRecordById(aria);
            System.out.println(result.getStatus());
            if (result.getStatus().equals(ResultStatuses.OK)){
                System.out.println(result.getList());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(DbDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }
    
    
}
