package ru.sfedu.organizer;

import ru.sfedu.organizer.api.Relation;
import ru.sfedu.organizer.model.Result;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.*;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static ru.sfedu.organizer.Constants.*;
import ru.sfedu.organizer.api.CsvDataProvider;
import ru.sfedu.organizer.model.*;
import static ru.sfedu.organizer.utils.ConfigurationUtil.getConfigurationEntry;
import ru.sfedu.organizer.utils.MyGenerator;

/**
 *
 * @author orus-kade
 */
public class ClientTest {
    
    public ClientTest() {
    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
    
    @Test
    public void testSomeMethod() throws IOException {
        Client c = new Client();
        c.logBasicSystemInfo();
    }
      
    @Test 
    public void ee(){
        String str = "Hello world";
//        int index1 = str.indexOf('l'); // 2
//        int index2 = str.indexOf("wo"); //6
//        int index4 = str.indexOf("Wo"); //6
//        int index3 = str.lastIndexOf('l'); //9
//        System.out.println(index1);
//        System.out.println(index2);
//        System.out.println(index3);
//        System.out.println(index4);
        System.out.println(str.contains("w7"));
        
    }
    
    @Test
    public void eqe(){
        
    }
 
}
