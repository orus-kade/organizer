
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
    
    
}
