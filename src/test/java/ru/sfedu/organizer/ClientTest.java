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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static ru.sfedu.organizer.Constants.*;
import ru.sfedu.organizer.api.CsvDataProvider;
import ru.sfedu.organizer.model.*;
import static ru.sfedu.organizer.utils.ConfigurationUtil.getConfigurationEntry;

/**
 *
 * @author sterie
 */
public class ClientTest {
    
    public ClientTest() {
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
    
    @Test
    public void testSomeMethod() throws IOException {
        Client c = new Client();
        c.logBasicSystemInfo();
    }
      
    @Test 
    public void ee(){
        
 
        System.out.println("------ Проверка подключения к PostgreSQL ------");
 
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/organizer", "organizer_user", "music5");
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
 
        if (null != connection) {
            System.out.println("------ Подключение установлено ------");
            System.out.println("Executing statement...");
            try {
                statement = connection.createStatement();
                String sql;
                sql = "SELECT * FROM t1";
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    String str = resultSet.getString("str");
                    System.out.println("id: " + id + " str: " + str);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
            }   
        } else {
            System.out.println("------ Подключение НЕ установлено ------");
        }
    }

 
}
