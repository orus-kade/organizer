
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.model.ResultStatuses;
import ru.sfedu.organizer.model.Result;
import org.junit.*;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.sfedu.organizer.model.*;
import static ru.sfedu.organizer.utils.MyGenerator.*;



/**
 *
 * @author orus-kade
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class XmlDataProviderTest {
    
    private static Note note;
    private static final XmlDataProvider provider = new XmlDataProvider("./src/main/resources");
    
    /**
     *
     */
    public XmlDataProviderTest() {
    }
    
    /**
     *
     */
    @BeforeClass
    public static void test() {
        note = generateNote(provider, generateId());
        System.out.println("Generated note : " + note);
    }
    
    /**
     *
     */
    @Test 
    public void aTestAdd(){
        System.out.println("ru.sfedu.organizer.api.XmlDataProviderTest.aTestAdd");
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
        System.out.println("ru.sfedu.organizer.api.XmlDataProviderTest.bTestEdit");
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
        System.out.println("ru.sfedu.organizer.api.XmlDataProviderTest.cTestDelete");
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

