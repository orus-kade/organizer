
package ru.sfedu.organizer;

import com.google.protobuf.Descriptors;
import com.opencsv.*;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.*;
import static org.junit.Assert.*;
import ru.sfedu.organizer.api.CsvDataProvider;
import ru.sfedu.organizer.api.CsvFilter;
import ru.sfedu.organizer.model.*;
import ru.sfedu.organizer.model.Types;
import ru.sfedu.organizer.utils.*;
import static ru.sfedu.organizer.Constants.*;
import static ru.sfedu.organizer.utils.ConfigurationUtil.*;

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
    public void test2() throws IOException {
        CsvDataProvider p = new CsvDataProvider();
        Aria a = new Aria();
        a.setId(2);
        Generic w;
        w = p.getRecordById(a);
        
        System.out.println(w);     
    }
    
    
    @Test
    public void test4(){

        java.io.Writer writer;
        try {
            writer = new FileWriter(getConfigurationEntry(CSV_PATH) + "Aria.csv");        
        Aria a = new Aria();
        a.setId(2);
        a.setText("tyext");
        a.setTitle("title");
        Generic lib = new Generic(Types.LIBRETTO);
        a.setLibretto(lib);
        System.out.println(a);      
        ColumnPositionMappingStrategy<Aria> beanStrategy = new ColumnPositionMappingStrategy<Aria>();           
        beanStrategy.setType(Aria.class);
        StatefulBeanToCsv<Aria> beanWriter = new StatefulBeanToCsv<Aria>('\\', "\n", beanStrategy, '\'', ';', true, writer);
        //StatefulBeanToCsvBuilder<Aria> builder = new StatefulBeanToCsvBuilder<Aria>(writer);
        //StatefulBeanToCsv<Aria> beanWriter = builder.withMappingStrategy(beanStrategy).build();
            beanWriter.write(a);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CsvDataTypeMismatchException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CsvRequiredFieldEmptyException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        Reader reader;
        try {
            reader = new FileReader(getConfigurationEntry(CSV_PATH) + "Aria.csv");        
            CsvToBean<Aria> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Aria.class)
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .build();
         
            List<Aria> list = csvToBean.parse();
            reader.close();
            System.out.println(list);
        } catch (IOException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Voices v;
        v = Voices.1;
        
        
    }
    
    
    @Test 
    public void test3() throws IOException{
        java.io.Writer writer = new FileWriter(getConfigurationEntry(CSV_PATH) + "Composer.csv");
        Composer com = new Composer();
        com.setId(2);
        com.setBiography("sds");
        com.setBirthDate("12.12.1222");
        com.setDeathDate("12.12.1234");
        com.setName("NAME");
        List<Generic> operas = new ArrayList<>();
        operas.add(new Generic(Types.OPERA));
        operas.add(new Generic(Types.OPERA));
        com.setOperas((List<Generic>)operas);
        System.out.println(com);      
        ColumnPositionMappingStrategy<Composer> beanStrategy = new ColumnPositionMappingStrategy<Composer>();           
        beanStrategy.setType(Composer.class);
        //beanStrategy.setColumnMapping(new String[] {"id", "name", "biography", "birthDate", "deathDate", "operas"});
        StatefulBeanToCsv<Composer> beanWriter = new StatefulBeanToCsv<Composer>('\\', "\n", beanStrategy, '\'', ';', true, writer);
        //StatefulBeanToCsvBuilder<Composer> builder = new StatefulBeanToCsvBuilder<Composer>(writer);
        //StatefulBeanToCsv<Composer> beanWriter = builder.withMappingStrategy(beanStrategy).build();
        try {
            // Writing data to csv file
            beanWriter.write(com);
        } catch (CsvDataTypeMismatchException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CsvRequiredFieldEmptyException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        writer.close();
    }
    
    
    /**
     * Test of logBasicSystemInfo method, of class Client.
     */
    @Test
    public void testLogBasicSystemInfo() {
        System.out.println("logBasicSystemInfo");
        Client instance = new Client();
        instance.logBasicSystemInfo();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }   
                    
}
