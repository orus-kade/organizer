
package ru.sfedu.organizer.api;

import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import ru.sfedu.organizer.model.*;
import ru.sfedu.organizer.model.Note;
import ru.sfedu.organizer.model.Result;
import static ru.sfedu.organizer.utils.MyGenerator.*;


/**
 *
 * @author orus-kade
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DbDataProviderTest {
    
    private static Note note;
    private static DbDataProvider provider;
    
    /**
     *
     */
    public DbDataProviderTest() {
    }
    
    /**
     *
     * @throws IOException
     */
    @BeforeClass
    public static void test() throws IOException {
        provider = new DbDataProvider();
        note = generateNote(provider, generateId());
        System.out.println("Generated note : " + note);
    }
    
    /**
     *
     */
    @Test 
    public void aTestAdd(){
        System.out.println("ru.sfedu.organizer.api.DbDataProviderTest.aTestAdd");
        Result result = provider.addRecord(note);
        System.out.println("Adding : " + result.getStatus());
        if (result.getStatus().equals(ResultStatuses.OK)){
            System.out.println("Note : " + note);
            result = provider.getRecordById(note);
            System.out.println("Get by id : " + result.getStatus());
            if (result.getStatus().equals(ResultStatuses.OK)){
                boolean equals = result.getList().stream().findFirst().get().equals(note);
                System.out.println(note);
                Assert.assertFalse("aTestAdd faild", equals);
            }
        }
    }
    
    /**
     *
     */
    @Test
    public void bTestEdit(){        
        System.out.println("ru.sfedu.organizer.api.DbDataProviderTest.bTestEdit");
        Note newNote = generateNote(provider);        
        note.setDescription(newNote.getDescription());
        note.setObjectId(newNote.getObjectId());
        note.setObjectType(newNote.getObjectType());         
        Result result = provider.editRecord(note);
        System.out.println("Editing : " + result.getStatus());
        if (result.getStatus().equals(ResultStatuses.OK)){
            System.out.println("Note : " + note);
            result = provider.getRecordById(note);
            System.out.println("Get by id : " + result.getStatus());
            if (result.getStatus().equals(ResultStatuses.OK)){
                boolean equals = result.getList().stream().findFirst().get().equals(note);
                System.out.println(note);
                Assert.assertFalse("TestEdit faild", equals);
            }
        } 
    }
    
    /**
     *
     */
    @Test 
    public void cTestDelete(){
        System.out.println("ru.sfedu.organizer.api.DbDataProviderTest.cTestDelete");
        Result result = provider.deleteRecord(note);
        System.out.println("Deleting : " + result.getStatus());
        if (result.getStatus().equals(ResultStatuses.OK)){
            System.out.println("Note : " + note);
            result = provider.getRecordById(note);
            System.out.println("Get by id : " + result.getStatus());
            if (!result.getStatus().equals(ResultStatuses.NOTFOUND))
                Assert.fail("TestDeleted faild");
        }
    }
}
