/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.organizer;

import com.opencsv.CSVReader;
import java.util.*;
import com.opencsv.CSVWriter;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.sfedu.organizer.data.*;
import ru.sfedu.organizer.utils.ConfigurationUtil;



/**
 *
 * @author orus-kade
 */
public class MainTest {
    
    public MainTest() {
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
    public void testSomeMethod() throws FileNotFoundException, IOException {
            
                CSVReader reader = new CSVReader(new FileReader(ConfigurationUtil.getConfigurationEntry(Constants.CSV_ARIA_PATH)), ';'); 
                List<Aria> aries = new ArrayList<>();
                String[] record = null;
                record = reader.readNext();
                while ((record = reader.readNext()) != null) {
                    Entity obj;
                    List<Entity> objs;
                    Aria aria = new Aria();
                    aria.setId(Long.parseLong(record[0]));
                    aria.setType(EntityTypes.ARIA);
                    aria.setTitle(record[1]);
                    aria.setText(record[2]);
                    List<String> strlist = Arrays.asList(record[3].split(","));
                    objs = new ArrayList<>();
                    for(String str: strlist){
                        obj = new Entity();
                        obj.setId(Long.parseLong(str));
                        obj.setType(EntityTypes.COMPOSER);
                        objs.add(obj);
                    }
                    aria.setComposers(objs);
                    objs = new ArrayList<>();
                    strlist = Arrays.asList(record[4].split(","));
                    for(String str: strlist){
                        obj = new Entity();
                        obj.setId(Long.parseLong(str));
                        obj.setType(EntityTypes.WRITER);
                        objs.add(obj);
                    }
                    aria.setWriters(objs);
                    
                    objs = new ArrayList<>();
                    strlist = Arrays.asList(record[5].split(","));
                    for(String str: strlist){
                        obj = new Entity();
                        obj.setId(Long.parseLong(str));
                        obj.setType(EntityTypes.SINGER);
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
            aria.setType(EntityTypes.ARIA);
            aria.setId(12);
            aria.setTitle("title");
            aria.setText("some text text");
            List<Entity> list = new ArrayList<>();
            Entity obj = new Entity();
            obj.setId(1);
            obj.setType(EntityTypes.COMPOSER);
            list.add(obj);
            obj = new Entity();
            obj.setId(2);
            obj.setType(EntityTypes.COMPOSER);
            list.add(obj);
            aria.setComposers(list);
            
            list = new ArrayList<>();
            obj = new Entity();
            obj.setId(1);
            obj.setType(EntityTypes.WRITER);
            list.add(obj);
            obj = new Entity();
            obj.setId(2);
            obj.setType(EntityTypes.WRITER);
            list.add(obj);
            aria.setWriters(list);
            
            list = new ArrayList<>();
            obj = new Entity();
            obj.setId(1);
            obj.setType(EntityTypes.SINGER);
            list.add(obj);
            obj = new Entity();
            obj.setId(2);
            obj.setType(EntityTypes.SINGER);
            list.add(obj);
            aria.setFamousSingers(list);
            
            System.out.println(aria);
            
            String str = aria.getId() + ";";
            str += aria.getTitle() + ";";
            
            CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_ARIA_PATH), '#');
            
            
            
            //writer.writeNext(nextLine);
        
            

    }
}
