
package ru.sfedu.organizer.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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
          DbDataProvider provider = new DbDataProvider();
          Result result = provider.init_connection();
          System.out.println(result.getStatus());
          
//        System.out.println("------ Проверка подключения к PostgreSQL ------");
//        Generic o = new Singer();
//        String tname = "singer";
//        XmlDataProvider provider = new XmlDataProvider();        
//        List<Generic> list = provider.getAllRecords(o).getList();
//        //System.out.println(list);
//        Connection connection = null;
//        try {
//            connection  = DriverManager.getConnection(
//                    //getConfigurationEntry(DB_URL), getConfigurationEntry(DB_USER), getConfigurationEntry(DB_PASS));
//                    getConfigurationEntry(DB_URL), getConfigurationEntry("hgfd"), getConfigurationEntry(DB_PASS));
//            //Statement statement = null;        
//        if (null != connection) {
//            System.out.println("------ Подключение установлено ------");
//            System.out.println("Executing statement...");
//                //String sql = "";
//                connection.setAutoCommit(false);  
//                final Statement statement = connection.createStatement(); 
//                list.stream().forEach(e ->{
//                    String sql = "insert into " + tname + 
//                           " values (DEFAULT, '" + 
//                            ((Singer)e).getName()+ "', '" +
//                            ((Singer)e).getBiography()+ "', '" +
//                            ((Singer)e).getBirthDate()+ "', '" +
//                            ((Singer)e).getDeathDate()+ "', '" +
//                            ((Singer)e).getVoice()+  "');";
//                    System.out.println(sql);
//                try {                    
//                    statement.executeUpdate(sql);
//                } catch (SQLException ex) {
//                    Logger.getLogger(DbDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                });
//                connection.commit();
//                connection.setAutoCommit(true);
//                //ResultSet resultSet = statement.executeQuery(sql);
////                while (resultSet.next()){
////                    int id = resultSet.getInt("id");
////                    String str = resultSet.getString("str");
////                    System.out.println("id: " + id + " str: " + str);
//        }
//        else {
//            System.out.println("------ Подключение НЕ установлено ------");
//        }
//        } catch (IOException ex) {
//            Logger.getLogger(DbDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//                Logger.getLogger(DbDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);  
//                if (connection != null){
//                    try {
//                        connection.rollback();
//                        connection.setAutoCommit(true);
//                    } catch (SQLException ex1) {
//                        Logger.getLogger(DbDataProviderTest.class.getName()).log(Level.SEVERE, null, ex1);
//                    }
//                    
//            }
//        } 
    }
    
    
}
