
package ru.sfedu.organizer.api;

import java.io.IOException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.sfedu.organizer.model.*;

import ru.sfedu.organizer.model.Generic;
import ru.sfedu.organizer.model.Note;
import ru.sfedu.organizer.model.Result;

/**
 *
 * @author orus-kade
 */
public class DbDataProviderTest {
    
    public DbDataProviderTest() {
    }
    
    @Test 
    public void my_test(){
        
        try {
            DbDataProvider provider = new DbDataProvider();
            Aria obj = new Aria(1);
            Result result = provider.getAllRecords(obj);
            System.out.println(result.getStatus());
            if (result.getStatus().equals(ResultStatuses.OK)){
                System.out.println(result.getList());
            }
            
        } catch (IOException ex) {

            Logger.getLogger(DbDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
//        try {
//            DbDataProvider provider = new DbDataProvider();
//            Note note = new Note();
//            note.setDescription("ertet");
//            note.setObjectId(2);
//            note.setObjectType("ARIA");
//            System.out.println(provider.addRecord(note).getStatus());
//        } catch (IOException ex) {
//            Logger.getLogger(DbDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    
    @Test
    public void testGetById(){
        try {
            DbDataProvider provider = new DbDataProvider();
            Generic obj = new Note(6);
            Result result = provider.getRecordById(obj);
            System.out.println(result.getStatus());
            if (result.getStatus().equals(ResultStatuses.OK)){
                System.out.println(result.getList().get(0));
            }
            else
                System.err.println(result.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(DbDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testGetAll(){
        try {
            DbDataProvider provider = new DbDataProvider();
            Generic obj = new Aria();
            Result result = provider.getAllRecords(obj);
            System.out.println(result.getStatus());
            if (result.getStatus().equals(ResultStatuses.OK)){
                result.getList().stream().forEach(System.out::println);
            }
            else
                System.err.println(result.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(DbDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testAdd(){
        try {
            DbDataProvider provider = new DbDataProvider();
            Note obj = new Note();
            obj.setDescription("descqqq");
            obj.setObjectId(3);
            obj.setObjectType(Types.OPERA.toString());
            Result result = provider.addRecord(obj);
            System.out.println(result.getStatus());
            if (result.getStatus().equals(ResultStatuses.OK)){
                result = provider.getAllRecords(obj);
                result.getList().stream().forEach(System.out::println);
            }
            else
                System.err.println(result.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(DbDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testEdit(){
        try {
            DbDataProvider provider = new DbDataProvider();
            Note obj = new Note(6);
            obj.setObjectId(3);
            obj.setDescription("descqqq");
            obj.setObjectType(Types.LIBRETTO.toString());
            Result result = provider.editRecord(obj);
            System.out.println(result.getStatus());
            if (result.getStatus().equals(ResultStatuses.OK)){
                result = provider.getAllRecords(obj);
                result.getList().stream().forEach(System.out::println);
            }
            else
                System.err.println(result.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(DbDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testDelete(){
        try {
            DbDataProvider provider = new DbDataProvider();
            Note obj = new Note();
            Result result = provider.getAllRecords(obj);
            System.out.println(result.getStatus());
            if (result.getStatus().equals(ResultStatuses.OK)){
                result.getList().stream().forEach(System.out::println);
            }
            else
                System.err.println(result.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(DbDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testFind() {
        try {
            //Generic obj = new Aria();
            //Generic obj = new Author();
            //Generic obj = new Composer();
            //Generic obj = new Libretto();
            //Generic obj = new Note();
            //Generic obj = new Opera();
            //Generic obj = new Singer();
            //obj.setId(1);
            Aria obj = new Aria();
            obj.setTitle("ti");
            obj.setText("2");
            DbDataProvider instance = new DbDataProvider();
            Result result = instance.findRecord(obj);
            System.out.println(result.getStatus());
            System.out.println(result.getMessage());
            if (result.getStatus().equals(ResultStatuses.OK))
                result.getList().stream().forEach(r -> System.out.println(r));
        } catch (IOException ex) {
            Logger.getLogger(DbDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
