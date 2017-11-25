
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
        Generic obj = null;
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
        Generic obj = null;
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
        Generic obj = null;
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
    public void testDeleteRelationsOpera() throws Exception {
        System.out.println("deleteRelationsOpera");
        Generic obj = null;
        CsvDataProvider instance = new CsvDataProvider();
        Result expResult = null;
        Result result = instance.deleteRelationsOpera(obj);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRecordById method, of class CsvDataProvider.
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
        System.out.println("getAllRecords");
        Generic obj = new Aria();
        //Generic obj = new Author();
        //Generic obj = new Composer();
        //Generic obj = new Libretto();
        //Generic obj = new Note();
        //Generic obj = new Opera();
        //Generic obj = new Singer();
        obj.setId(2);
        CsvDataProvider instance = new CsvDataProvider();
        Result  result = instance.getRecordById(obj);
        System.out.println(result.getStatus());
        System.out.println(result.getMessage());
        result.getList().stream().forEach(r -> System.out.println(r));
        Aria a = new Aria();
        a.setId(2);
        a.setOpera(new Opera(5));
        a.setTitle("NewTitle");
        List<Generic> singers = new ArrayList<Generic>();
        singers.add(new Singer(3));
        singers.add(new Singer(4));
        a.setSingers(singers);
        result = instance.editRecord(a);
        System.out.println(result.getStatus());
        System.out.println(result.getMessage());
        
        result = instance.getRecordById(a);
        System.out.println(result.getStatus());
        System.out.println(result.getMessage());
        result.getList().stream().forEach(r -> System.out.println(r));
        
    }
    
    @Test
    public void testAdd() {
        System.out.println("getAllRecords");
        //Generic obj = new Aria();
        //Generic obj = new Author();
        //Generic obj = new Composer();
        //Generic obj = new Libretto();
        //Generic obj = new Note();
        //Generic obj = new Opera();
        //Generic obj = new Singer();
        CsvDataProvider instance = new CsvDataProvider();
        Aria a = new Aria();
        a.setOpera(new Opera(5));
        a.setTitle("NewTitle");
        List<Generic> singers = new ArrayList<Generic>();
        singers.add(new Singer (3));
        singers.add(new Singer (4));
        a.setSingers(singers);
        Result result = instance.addRecord(a);
        System.out.println(result.getStatus());
        System.out.println(result.getMessage());
        
        result = instance.getRecordById(a);
        System.out.println(result.getStatus());
        System.out.println(result.getMessage());
        result.getList().stream().forEach(r -> System.out.println(r));
        
    }
    
    @Test
    public void testDelete() {
        System.out.println("getAllRecords");
        //Generic obj = new Aria();
        //Generic obj = new Author();
        //Generic obj = new Composer();
        //Generic obj = new Libretto();
        //Generic obj = new Note();
        //Generic obj = new Opera();
        //Generic obj = new Singer();
        CsvDataProvider instance = new CsvDataProvider();
        Aria a = new Aria();
        a.setOpera(new Opera(5));
        a.setTitle("NewTitle");
        List<Generic> singers = new ArrayList<Generic>();
        singers.add(new Singer (3));
        singers.add(new Singer (4));
        a.setSingers(singers);
        Result result = instance.addRecord(a);
        System.out.println(result.getStatus());
        System.out.println(result.getMessage());
        
        result = instance.getRecordById(a);
        System.out.println(result.getStatus());
        System.out.println(result.getMessage());
        result.getList().stream().forEach(r -> System.out.println(r));
        
        result = instance.deleteRecord(a);
        System.out.println(result.getStatus());
        System.out.println(result.getMessage());
        
        result = instance.getRecordById(a);
        System.out.println(result.getStatus());
        System.out.println(result.getMessage());
        
    }
    
}
