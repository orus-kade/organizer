
package ru.sfedu.organizer.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static ru.sfedu.organizer.utils.ConfigurationUtil.*;
import static ru.sfedu.organizer.Constants.*;

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
        System.out.println("------ Проверка подключения к PostgreSQL ------");
        try {
            Connection connection = null;
            connection = DriverManager.getConnection(
                    getConfigurationEntry(DB_URL), getConfigurationEntry(DB_USER), getConfigurationEntry(DB_PASS));
            PreparedStatement statement = connection.prepareStatement("INSERT INTO t1 values(DEFAULT, ?)");
        
        
        if (null != connection) {
            System.out.println("------ Подключение установлено ------");
            System.out.println("Executing statement...");
                statement.setInt(1, 23);
                
                System.out.println(statement.executeQuery());
                //ResultSet resultSet = statement.executeQuery(sql);
//                while (resultSet.next()){
//                    int id = resultSet.getInt("id");
//                    String str = resultSet.getString("str");
//                    System.out.println("id: " + id + " str: " + str);
        }
        else {
            System.out.println("------ Подключение НЕ установлено ------");
        }
        } catch (IOException ex) {
            Logger.getLogger(DbDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
                Logger.getLogger(DbDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);  
        } 
    }
}
