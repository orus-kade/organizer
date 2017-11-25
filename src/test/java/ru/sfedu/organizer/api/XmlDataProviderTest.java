
package ru.sfedu.organizer.api;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.sfedu.organizer.model.*;


/**
 *
 * @author sterie
 */
public class XmlDataProviderTest {
    
    public XmlDataProviderTest() {
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
     * Test of addRecord method, of class XmlDataProvider.
     */
    @Test
    public void testAddRecord() {
        System.out.println("addRecord");
        Generic obj = null;
        XmlDataProvider instance = new XmlDataProvider();
        Result expResult = null;
        Result result = instance.addRecord(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editRecord method, of class XmlDataProvider.
     */
    @Test
    public void testEditRecord() {
        System.out.println("editRecord");
        Generic obj = null;
        XmlDataProvider instance = new XmlDataProvider();
        Result expResult = null;
        Result result = instance.editRecord(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteRecord method, of class XmlDataProvider.
     */
    @Test
    public void testDeleteRecord() {
        System.out.println("deleteRecord");
        Generic obj = null;
        XmlDataProvider instance = new XmlDataProvider();
        Result expResult = null;
        Result result = instance.deleteRecord(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRecordById method, of class XmlDataProvider.
     */
    @Test
    public void testGetRecordById() {
        System.out.println("getRecordById");
        Generic obj = null;
        XmlDataProvider instance = new XmlDataProvider();
        Result expResult = null;
        Result result = instance.getRecordById(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllRecords method, of class XmlDataProvider.
     */
    @Test
    public void testGetAllRecords() {
        System.out.println("getAllRecords");
        Generic obj = null;
        XmlDataProvider instance = new XmlDataProvider();
        Result expResult = null;
        Result result = instance.getAllRecords(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    
    @Test
    public void test(){
        CsvDataProvider csv = new CsvDataProvider();
        Generic aria = new Aria(1);
        Generic a = (csv.getRecordById(aria).getList().get(0));
        System.out.println(a);
        
    }
    
}
