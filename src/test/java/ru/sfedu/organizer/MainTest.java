/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.organizer;

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
    public void testSomeMethod() {
        try {
            // TODO review the generated test code and remove the default call to fail.
            //fail("The test case is a prototype.");
            System.out.println("ololo");
            Aria aria = new Aria();
            aria.setType(EntityTypes.ARIA);
            aria.setId(12);
            aria.setTitle("title");
            aria.setText("some text \n text");
            List<Entity> list = new ArrayList<>();
            Entity obj = new Entity();
            obj.setId(1);
            obj.setType(EntityTypes.COMPOSER);
            list.add(obj);
            obj.setId(2);
            list.add(obj);
            obj.setId(3);
            list.add(obj);
            aria.setComposers(list);
            
            list.clear();
            obj.setId(1);
            obj.setType(EntityTypes.WRITER);
            list.add(obj);
            obj.setId(2);
            list.add(obj);
            obj.setId(3);
            list.add(obj);
            aria.setWriters(list);
            
            list.clear();
            obj.setId(1);
            obj.setType(EntityTypes.SINGER);
            list.add(obj);
            obj.setId(2);
            list.add(obj);
            obj.setId(3);
            list.add(obj);
            aria.setFamousSingers(list);
            
            System.out.println(aria);
            
            String str = aria.getId() + ";";
            str += aria.getTitle() + ";";
            
            CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_ARIA_PATH));
            
        
            
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Test
    public void test1() {
        try {
            // TODO review the generated test code and remove the default call to fail.
            //fail("The test case is a prototype.");
            System.out.println("ololo");
            Aria aria = new Aria();
            aria.setType(EntityTypes.ARIA);
            aria.setId(12);
            aria.setTitle("title");
            aria.setText("some text \n text");
            List<Entity> list = new ArrayList<>();
            Entity obj = new Entity();
            obj.setId(1);
            obj.setType(EntityTypes.COMPOSER);
            list.add(obj);
            obj.setId(2);
            list.add(obj);
            obj.setId(3);
            list.add(obj);
            aria.setComposers(list);
            
            list.clear();
            obj.setId(1);
            obj.setType(EntityTypes.WRITER);
            list.add(obj);
            obj.setId(2);
            list.add(obj);
            obj.setId(3);
            list.add(obj);
            aria.setWriters(list);
            
            list.clear();
            obj.setId(1);
            obj.setType(EntityTypes.SINGER);
            list.add(obj);
            obj.setId(2);
            list.add(obj);
            obj.setId(3);
            list.add(obj);
            aria.setFamousSingers(list);
            
            System.out.println(aria);
            
            String str = aria.getId() + ";";
            str += aria.getTitle() + ";";
            
            CSVWriter writer = new CSVWriter(new FileWriter(Constants.CSV_ARIA_PATH));
            
        
            
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
