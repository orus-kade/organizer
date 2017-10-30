
package ru.sfedu.organizer;

import com.opencsv.*;
import java.io.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import ru.sfedu.organizer.api.CsvDataProvider;
import ru.sfedu.organizer.model.*;
import ru.sfedu.organizer.utils.*;

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
       // System.out.println(p.getFile(a));
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
    
    
    @Test
    public void testSomeMethod1() throws FileNotFoundException, IOException {
            
                CSVReader reader = new CSVReader(new FileReader(ConfigurationUtil.getConfigurationEntry(Constants.CSV_ARIA_PATH)), ';'); 
                List<Aria> aries = new ArrayList<>();
                String[] record = null;
                record = reader.readNext();
                while ((record = reader.readNext()) != null) {
                    Generic obj;
                    List<Generic> objs;
                    Aria aria = new Aria();
                    aria.setId(Long.parseLong(record[0]));
                    aria.setType(ClassType.ARI);
                    aria.setTitle(record[1]);
                    aria.setText(record[2]);
                    List<String> strlist = Arrays.asList(record[3].split(","));
                    objs = new ArrayList<>();
                    for(String str: strlist){
                        obj = new Generic();
                        obj.setId(Long.parseLong(str));
                        obj.setType(ClassType.COM);
                        objs.add(obj);
                    }
                    aria.setComposers(objs);
                    objs = new ArrayList<>();
                    strlist = Arrays.asList(record[4].split(","));
                    for(String str: strlist){
                        obj = new Generic();
                        obj.setId(Long.parseLong(str));
                        obj.setType(ClassType.WRI);
                        objs.add(obj);
                    }
                    aria.setWriters(objs);
                    
                    objs = new ArrayList<>();
                    strlist = Arrays.asList(record[5].split(","));
                    for(String str: strlist){
                        obj = new Generic();
                        obj.setId(Long.parseLong(str));
                        obj.setType(ClassType.SIN);
                        objs.add(obj);
                    }
                    aria.setFamousSingers(objs);
                    
                    aries.add(aria);
                }
            System.out.println(aries);   
    }
    
    
    @Test
    public void test1() {

            // TODO review the generated test code and remove the default call to fail.
            //fail("The test case is a prototype.");
            System.out.println("ololo");
            Aria aria = new Aria();
            aria.setType(ClassType.ARI);
            aria.setId(12);
            aria.setTitle("title");
            aria.setText("some text text");
            List<Generic> list = new ArrayList<>();
            Generic obj = new Generic();
            obj.setId(1);
            obj.setType(ClassType.COM);
            list.add(obj);
            obj = new Generic();
            obj.setId(2);
            obj.setType(ClassType.COM);
            list.add(obj);
            aria.setComposers(list);
            
            list = new ArrayList<>();
            obj = new Generic();
            obj.setId(1);
            obj.setType(ClassType.WRI);
            list.add(obj);
            obj = new Generic();
            obj.setId(2);
            obj.setType(ClassType.WRI);
            list.add(obj);
            aria.setWriters(list);
            
            list = new ArrayList<>();
            obj = new Generic();
            obj.setId(1);
            obj.setType(ClassType.SIN);
            list.add(obj);
            obj = new Generic();
            obj.setId(2);
            obj.setType(ClassType.SIN);
            list.add(obj);
            aria.setFamousSingers(list);
            
            System.out.println(aria);
            
            String str = aria.getId() + ";";
            str += aria.getTitle() + ";";
            
            CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_ARIA_PATH), '#');
            
            
            
            //writer.writeNext(nextLine);
        
            

    
}
