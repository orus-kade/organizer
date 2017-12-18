
package ru.sfedu.organizer.api;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import ru.sfedu.organizer.model.ResultStatuses;
import ru.sfedu.organizer.model.Result;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.sfedu.organizer.model.*;
import static ru.sfedu.organizer.utils.MyGenerator.generateType;


/**
 *
 * @author orus-kade
 */

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CsvDataProviderTest {
    private static final Logger log = Logger.getLogger(CsvDataProviderTest.class);
    
    public CsvDataProviderTest() {
    }
    
//    @Test 
//    public void aTestAdd(){
    
    @Test 
    public void Test(){
        System.out.println("ru.sfedu.organizer.api.CsvDataProviderTest.TestAdd");
        long objId = 0;
        Note note = new Note();
        String description = "Some description";
        Types type;
        CsvDataProvider provider = new CsvDataProvider();
        while(true){
            type = generateType();            
            Result result = null;
            switch (type){
                case ARIA : result = provider.getAllRecords(new Aria());
                    break;
                case COMPOSER : result = provider.getAllRecords(new Aria());
                    break;
                case LIBRETTO : result = provider.getAllRecords(new Aria());
                    break;
                case OPERA : result = provider.getAllRecords(new Aria());
                    break;
                case SINGER : result = provider.getAllRecords(new Aria());
                    break;
                case AUTHOR : result = provider.getAllRecords(new Aria());
                    break;     
            }
            if (result.getStatus().equals(ResultStatuses.OK)){
                objId = result.getList().stream().findAny().get().getId();
                break;
            }                      
        }
        note.setDescription(description);
        note.setObjectId(objId);
        note.setObjectType(type.toString());  
        
        Result result = provider.addRecord(note);
        System.out.println("Adding : " + result.getStatus());
        if (result.getStatus().equals(ResultStatuses.OK)){
            note.setId(result.getList().stream().findAny().get().getId());
            System.out.println("Note : " + note);
            result = provider.getRecordById(note);
            System.out.println("Get by id : " + result.getStatus());
            if (result.getStatus().equals(ResultStatuses.OK)){
                boolean equals = result.getList().stream().findFirst().get().equals(note);
                System.out.println(note);
                Assert.assertFalse("aTestAdd faild", equals);
            }
        }
        
        System.out.println("ru.sfedu.organizer.api.CsvDataProviderTest.TestEdit");
        description = "Another description";
        while(true){
            type = generateType();            
            switch (type){
                case ARIA : result = provider.getAllRecords(new Aria());
                    break;
                case COMPOSER : result = provider.getAllRecords(new Aria());
                    break;
                case LIBRETTO : result = provider.getAllRecords(new Aria());
                    break;
                case OPERA : result = provider.getAllRecords(new Aria());
                    break;
                case SINGER : result = provider.getAllRecords(new Aria());
                    break;
                case AUTHOR : result = provider.getAllRecords(new Aria());
                    break;     
            }
            if (result.getStatus().equals(ResultStatuses.OK)){
                objId = result.getList().stream().findAny().get().getId();
                break;
            }                      
        }
        note.setDescription(description);
        note.setObjectId(objId);
        note.setObjectType(type.toString()); 
        
        result = provider.editRecord(note);
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
        
        System.out.println("ru.sfedu.organizer.api.CsvDataProviderTest.TestDelete");
        result = provider.deleteRecord(note);
        System.out.println("Deleting : " + result.getStatus());
        if (result.getStatus().equals(ResultStatuses.OK)){
            System.out.println("Note : " + note);
            result = provider.getRecordById(note);
            System.out.println("Get by id : " + result.getStatus());
            boolean equals = result.getStatus().equals(ResultStatuses.NOTFOUND);
            //Assert.assertFalse("TestDeleted faild", result.getStatus().equals(ResultStatuses.NOTFOUND));
        }
    }
 
/*    
    @Test
    public void bTestEdit() {
        System.out.println("ru.sfedu.organizer.api.CsvDataProviderTest.bTestEdit()");
        String description = "Another description";
        Types type;
        long objId = 0;
        CsvDataProvider provider = new CsvDataProvider();
        System.out.println("Note : " + this.note);
        Result result = provider.getRecordById(this.note);
        System.out.println("Get by id : " + result.getStatus());
        if (result.getStatus().equals(ResultStatuses.OK)){
            while(true){
                type = generateType();            
                switch (type){
                    case ARIA : result = provider.getAllRecords(new Aria());
                        break;
                    case COMPOSER : result = provider.getAllRecords(new Aria());
                        break;
                    case LIBRETTO : result = provider.getAllRecords(new Aria());
                        break;
                    case OPERA : result = provider.getAllRecords(new Aria());
                        break;
                    case SINGER : result = provider.getAllRecords(new Aria());
                        break;
                    case AUTHOR : result = provider.getAllRecords(new Aria());
                        break;     
                }
                if (result.getStatus().equals(ResultStatuses.OK)){
                    objId = result.getList().stream().findAny().get().getId();
                    break;
                }                      
            }
            this.note.setDescription(description);
            this.note.setObjectId(objId);
            this.note.setObjectType(type.toString());  

            result = provider.editRecord(this.note);
            System.out.println("Editing : " + result.getStatus());
            if (result.getStatus().equals(ResultStatuses.OK)){
                this.note.setId(result.getList().stream().findAny().get().getId());
                System.out.println(this.note);
                result = provider.getRecordById(this.note);
                System.out.println("Get by id : " + result.getStatus());
                if (result.getStatus().equals(ResultStatuses.OK)){
                boolean equals = result.getList().stream().findFirst().get().equals(this.note);
                    Assert.assertFalse("aTestAdd faild", equals);
                }
            }
        }
    }
*/
    
    

//    
//    @Test
//    public void testDelete() {
//       Note note = new Note(112);
//       CsvDataProvider instance = new CsvDataProvider();
//       Result result = instance.deleteRecord(note);
//       System.out.println(result.getStatus());
//       System.out.println(result.getMessage());  
//    }
//    
//    @Test
//    public void testFind() {
//        //Generic obj = new Aria();
//        //Generic obj = new Author();
//        //Generic obj = new Composer();
//        //Generic obj = new Libretto();
//        //Generic obj = new Note();
//        //Generic obj = new Opera();
//        //Generic obj = new Singer();
//        //obj.setId(1);
//        Aria obj = new Aria();
//        obj.setTitle("ti");
//        obj.setText("e");
//        CsvDataProvider instance = new CsvDataProvider();
//        Result result = instance.findRecord(obj);
//        System.out.println(result.getStatus());
//        System.out.println(result.getMessage());
//        if (result.getStatus().equals(ResultStatuses.OK))
//            result.getList().stream().forEach(r -> System.out.println(r));
//    }
}
