/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.organizer;

import java.io.IOException;
import ru.sfedu.organizer.Client;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.sfedu.organizer.api.CsvDataProvider;
import ru.sfedu.organizer.model.Aria;
import ru.sfedu.organizer.model.EntityTypes;

import ru.sfedu.organizer.utils.ConfigurationUtil;

/**
 *
 * @author orus-kade
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
    public void testSomeMethod() {
        Client c = new Client();
        c.logBasicSystemInfo();
    }
    @Test
    public void test1() throws IOException{
        CsvDataProvider pr = new CsvDataProvider();
        pr.initDataSource();
        Aria aria = new Aria();
        aria.setType(EntityTypes.ARI);
        pr.saveRecord(aria);
    }
}