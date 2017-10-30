
package ru.sfedu.organizer;

import com.opencsv.*;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import java.io.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import ru.sfedu.organizer.api.CsvDataProvider;
import ru.sfedu.organizer.api.CsvFilter;
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
        a.setId(4);        
        System.out.println(p.getRecordById(a));     
       
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
                    aria.setType(Types.ARIA);
                    aria.setTitle(record[1]);
                    aria.setText(record[2]);
                    List<String> strlist = Arrays.asList(record[3].split(","));
                    objs = new ArrayList<>();
                    for(String str: strlist){
                        obj = new Generic();
                        obj.setId(Long.parseLong(str));
                        obj.setType(Types.COMPOSER);
                        objs.add(obj);
                    }
                    aria.setComposers(objs);
                    objs = new ArrayList<>();
                    strlist = Arrays.asList(record[4].split(","));
                    for(String str: strlist){
                        obj = new Generic();
                        obj.setId(Long.parseLong(str));
                        obj.setType(Types.WRITER);
                        objs.add(obj);
                    }
                    aria.setWriters(objs);
                    
                    objs = new ArrayList<>();
                    strlist = Arrays.asList(record[5].split(","));
                    for(String str: strlist){
                        obj = new Generic();
                        obj.setId(Long.parseLong(str));
                        obj.setType(Types.SINGER);
                        objs.add(obj);
                    }
                    aria.setFamousSingers(objs);
                    
                    aries.add(aria);
                }
            System.out.println(aries);   
    }
    
    
    @Test
    public void test1() throws IOException {
        CSVReader reader = new CSVReader(new FileReader(ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH)+"Aria.csv"), ';');
	HeaderColumnNameMappingStrategy<Aria> beanStrategy = new HeaderColumnNameMappingStrategy<Aria>();		
	beanStrategy.setType(Aria.class);
        CsvFilter filter = new CsvFilter(beanStrategy, 2);
	CsvToBean<Aria> csvToBean = new CsvToBean<Aria>();		
	List<Aria> a = csvToBean.parse(beanStrategy, reader, filter);		
	System.out.println(a);  
                      

    }
}
