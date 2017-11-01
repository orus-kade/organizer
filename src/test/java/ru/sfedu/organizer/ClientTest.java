
package ru.sfedu.organizer;

import com.opencsv.*;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
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
import ru.sfedu.organizer.api.CsvFilter;
import ru.sfedu.organizer.model.*;
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
        a.setId(3);        
        System.out.println(p.getRecordById(a));     
       
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
        beanStrategy.setColumnMapping(new String[] {"id", "name", "biography", "birthDate", "deathDate", "operas"});
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
    
    @Test 
    public void test4() throws IOException{
        //CSVReader reader = new CSVReader(new FileWriter(getConfigurationEntry(CSV_PATH) + "Composer.csv"), ';', '\'', '\\');
        //CSVReader reader = new CSVReader();
 
        final CSVParser parser = new CSVParserBuilder().withSeparator(';').withEscapeChar('\\').withQuoteChar('\'').build();
        final CSVReader reader;
        reader = new CSVReaderBuilder(new FileReader("datasources/csv/Composer.csv")).withCSVParser(parser).build();
        
        ColumnPositionMappingStrategy<Composer> beanStrategy = new ColumnPositionMappingStrategy<Composer>();           
        beanStrategy.setType(Composer.class);
        beanStrategy.setColumnMapping(new String[] {"id", "name", "biography", "birthDate", "deathDate"});
        CsvToBean<Composer> ctb = new CsvToBean<Composer>();
        ctb.setMappingStrategy(beanStrategy);
        ctb.setCsvReader(reader);
        
        System.out.print(ctb.parse());
        reader.close();
        
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
