package ru.sfedu.organizer;

import com.google.gson.annotations.Since;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;
import ru.sfedu.organizer.api.CsvDataProvider;
import ru.sfedu.organizer.model.*;
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
        Singer a = new Singer();
        //Aria a = new Aria();
        //Author a = new Author();
        //Opera a = new Opera();
        a.setId(22);
        Result result = new Result();
        //result = p.getRecordById(a); 
        result = p.deleteRecord(a); 
        System.out.println(result.getStatus()+" "+result.getMessage()); 
//        if ("OK".equals(result.getStatus()) || "Warning".equals(result.getStatus())){
//            List<Generic> list = ((Singer)result.getList().get(0)).getAries();
//            list.stream().forEach(e -> e.setId(e.getId()+55));
//            Generic aria = new Generic(Types.ARIA);
//            aria.setId(123);
//            list.add(aria);
//            a.setAries(list);
//            result = p.addRecord(a);
//            System.out.println(result.getStatus() + " " + result.getMessage());            
//        }
//        else{
//            System.out.println(result.getStatus()+" "+result.getMessage());            
//        }
        //result = p.getRecordById(a, true);        
        //Generic obj = p.getRelationsSinger(a);
        //System.out.println(obj);
        
    }
    @Test
    public void tt(){
        try {
            Writer writer = new FileWriter(getConfigurationEntry(CSV_PATH_ARIA_SINGER));
            StatefulBeanToCsv<Relation> b = new StatefulBeanToCsvBuilder<Relation>(writer)
                    .withSeparator(';')
                    .withQuotechar('\'')
                    .withEscapechar('\\')
                    .build();
            Relation r = new Relation();
            r.setId1(1);
            r.setId2(2);            
            b.write(r);
            writer.close();
            System.out.println(r);
        } catch (CsvDataTypeMismatchException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CsvRequiredFieldEmptyException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
    }   
    
    @Test
    public void test4(){
            Aria a = new Aria();
            a.setText("tyext");
            a.setTitle("title");
            Generic lib = new Generic(Types.LIBRETTO);
            lib.setId(2);
            a.setOpera(lib);
            CsvDataProvider p = new CsvDataProvider();
            p.addRecord(a);
    }
    
    @Test
    public void test5(){
            Aria a = new Aria();
            a.setId(5);
            a.setText("tyext");
            a.setTitle("title");
            Generic lib = new Generic(Types.LIBRETTO);
            lib.setId(2);
            a.setOpera(lib);
            CsvDataProvider p = new CsvDataProvider();
            p.deleteRecord(a);
    }
    
    
    
    
    @Test 
    public void test6() throws IOException{
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
