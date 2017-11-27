
package ru.sfedu.organizer.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import static ru.sfedu.organizer.Constants.*;
import ru.sfedu.organizer.model.*;
import static ru.sfedu.organizer.utils.ConfigurationUtil.getConfigurationEntry;


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
    public void test() throws IOException{
        CsvDataProvider csv = new CsvDataProvider();
        Aria aria = new Aria(1);
        List<Generic> list = new ArrayList<Generic>();
        list.addAll(csv.getAllRecords(aria).getList());
        System.out.println(list);
        XmlListEntity ll = new XmlListEntity();
        ll.setList(list);
        Serializer serializer = new Persister();
        File result = new File(getConfigurationEntry(XML_PATH_ARIA));
        try {
            serializer.write(ll, result);
            XmlListEntity l = serializer.read(XmlListEntity.class, result);
            System.out.println(l.getList());
        } catch (Exception ex) {
            Logger.getLogger(XmlDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ururur");
        }
    }
    
}
