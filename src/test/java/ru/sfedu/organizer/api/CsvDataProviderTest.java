
package ru.sfedu.organizer.api;

import java.util.ArrayList;
import java.util.List;
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
public class CsvDataProviderTest {
    
    public CsvDataProviderTest() {
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
     * Test of addRecord method, of class CsvDataProvider.
     */
    @Test
    public void testAddRecord() {
        System.out.println("addRecord");
        Note obj = null;
        CsvDataProvider instance = new CsvDataProvider();
        Result expResult = null;
        Result result = instance.addRecord(obj);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of editRecord method, of class CsvDataProvider.
     */
    @Test
    public void testEditRecord() {
        System.out.println("editRecord");
        Note obj = null;
        CsvDataProvider instance = new CsvDataProvider();
        Result expResult = null;
        Result result = instance.editRecord(obj);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteRecord method, of class CsvDataProvider.
     */
    @Test
    public void testDeleteRecord() {
        System.out.println("deleteRecord");
        Note obj = null;
        CsvDataProvider instance = new CsvDataProvider();
        Result expResult = null;
        Result result = instance.deleteRecord(obj);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteRelationsOpera method, of class CsvDataProvider.
     */

    @Test
    public void testGetRecordById() {
        System.out.println("getRecordById");
        Generic obj = null;
        CsvDataProvider instance = new CsvDataProvider();
        Result expResult = null;
        Result result = instance.getRecordById(obj);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllRecords method, of class CsvDataProvider.
     */
    @Test
    public void testGetAllRecords() {
        System.out.println("getAllRecords");
        Generic obj = null;
        CsvDataProvider instance = new CsvDataProvider();
        Result expResult = null;
        Result result = instance.getAllRecords(obj);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
    
    @Test
    public void testGetAll() {
        System.out.println("getAllRecords");
        Generic obj = new Aria();
        CsvDataProvider instance = new CsvDataProvider();
        Result result = instance.getAllRecords(obj);
        System.out.println(result.getStatus());
        System.out.println(result.getMessage());
        result.getList().stream().forEach(r -> System.out.println(r));
    }
    
    @Test
    public void testGetById() {
        System.out.println("getAllRecords");
        Generic obj = new Aria();
        //Generic obj = new Author();
        //Generic obj = new Composer();
        //Generic obj = new Libretto();
        //Generic obj = new Note();
        //Generic obj = new Opera();
        //Generic obj = new Singer();
        obj.setId(1);
        CsvDataProvider instance = new CsvDataProvider();
        Result result = instance.getRecordById(obj);
        System.out.println(result.getStatus());
        System.out.println(result.getMessage());
        if (result.getStatus().equals(ResultStatuses.OK))
        result.getList().stream().forEach(r -> System.out.println(r));
    }
    
    @Test
    public void testEdit() {
       Note note = new Note(3);
       CsvDataProvider instance = new CsvDataProvider();
       Result result = instance.getRecordById(note);
       if (result.getStatus().equals(ResultStatuses.OK)){
           note = (Note)result.getList().get(0);
           note.setObjectType(Types.ARIA.toString());
           note.setObjectId(55);
           result = instance.editRecord(note);
           System.out.println(result.getStatus());
       }
    }
    
    @Test
    public void testAdd() {
       Note note = new Note();
       CsvDataProvider instance = new CsvDataProvider();
       note.setDescription("desc");
       note.setObjectId(1);
       note.setObjectType(Types.COMPOSER.toString());
       Result result = instance.addRecord(note);
       System.out.println(result.getStatus());
       System.out.println(result.getMessage());        
    }
    
    @Test
    public void testDelete() {
       Note note = new Note(112);
       CsvDataProvider instance = new CsvDataProvider();
       Result result = instance.deleteRecord(note);
       System.out.println(result.getStatus());
       System.out.println(result.getMessage());  
    }
    
}
