/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.organizer;

import com.opencsv.CSVWriter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.sfedu.organizer.data.Aria;
import ru.sfedu.organizer.data.ObjectTypes;


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
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        System.out.println("ololo");
        Aria aria = new Aria();
        aria.setType(ObjectTypes.Aria);
        aria.setId(12);
        aria.setTitle("title");
        aria.setText("some text \n text");
        CSVWriter writer = new FileWriter("yourfile.csv");
        StatefulBeanToCsvBuilder beanToCsv = StatefulBeanToCsvBuilder(writer).build();
        beanToCsv.write(beans);
        writer.close();
    }
    
}
