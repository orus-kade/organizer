
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.model.ResultStatuses;
import ru.sfedu.organizer.model.Result;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.sfedu.organizer.model.*;


/**
 *
 * @author sterie
 */
public class CsvDataProviderTest {
    
    public CsvDataProviderTest() {
    }
    
    
    @Test
    public void testGetAll() {
        System.out.println("getAllRecords");
        Generic obj = new Author();
        CsvDataProvider instance = new CsvDataProvider();
        Result result = instance.getAllRecords(obj);
        System.out.println(result.getStatus());
        System.out.println(result.getMessage());
        if (result.getStatus().equals(ResultStatuses.OK))
        result.getList().stream().forEach(r -> System.out.println(r));
    }
    
    @Test
    public void testGetById() {
        System.out.println("getAllRecords");
        Generic obj = new Author();
        //Generic obj = new Author();
        //Generic obj = new Composer();
        //Generic obj = new Libretto();
        //Generic obj = new Note();
        //Generic obj = new Opera();
        //Generic obj = new Singer();
        obj.setId(1);
        CsvDataProvider instance = new CsvDataProvider();
        Result result = instance.getRecordById(obj);
        System.out.println(result.getStatus());
        System.out.println(result.getMessage());
        if (result.getStatus().equals(ResultStatuses.OK))
        result.getList().stream().forEach(r -> System.out.println(r));
    }
    
    @Test
    public void testEdit() {
       Note note = new Note(3);
       CsvDataProvider instance = new CsvDataProvider();
       Result result = instance.getRecordById(note);
       if (result.getStatus().equals(ResultStatuses.OK)){
           note = (Note)result.getList().get(0);
           note.setObjectType(Types.ARIA.toString());
           note.setObjectId(55);
           result = instance.editRecord(note);
           System.out.println(result.getStatus());
       }
    }
    
    @Test
    public void testAdd() {
       Note note = new Note();
       CsvDataProvider instance = new CsvDataProvider();
       note.setDescription("desc");
       note.setObjectId(1);
       note.setObjectType(Types.COMPOSER.toString());
       Result result = instance.addRecord(note);
       System.out.println(result.getStatus());
       System.out.println(result.getMessage());        
    }
    
    @Test
    public void testDelete() {
       Note note = new Note(112);
       CsvDataProvider instance = new CsvDataProvider();
       Result result = instance.deleteRecord(note);
       System.out.println(result.getStatus());
       System.out.println(result.getMessage());  
    }
    
}
