
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.model.ResultStatuses;
import ru.sfedu.organizer.model.Result;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import static ru.sfedu.organizer.Constants.*;
import ru.sfedu.organizer.model.*;
import static ru.sfedu.organizer.utils.MyGenerator.generateType;


/**
 *
 * @author orus-kade
 */
public class XmlDataProviderTest {
    
    public XmlDataProviderTest() {
    }
    
    @Test 
    public void Test(){
        System.out.println("ru.sfedu.organizer.api.CsvDataProviderTest.TestAdd");
        long objId = 0;
        Note note = new Note();
        String description = "Some description";
        Types type;
        XmlDataProvider provider = new XmlDataProvider();
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
            if (!result.getStatus().equals(ResultStatuses.NOTFOUND))
                Assert.fail("TestDeleted faild");
        }
    }
    
//    @Test
//    public void test() throws IOException{
//        CsvDataProvider pr = new CsvDataProvider();
//        List<Generic> list = pr.getAllRecords(new Aria()).getList();
//        System.out.println(list);
//        XmlListEntity ll = new XmlListEntity();
//        ll.setList(list);
//        Serializer serializer = new Persister();
//        File result = new File(getConfigurationEntry(XML_PATH_ARIA));
//        try {
//            serializer.write(ll, result);
//            XmlListEntity l = serializer.read(XmlListEntity.class, result);
//            System.out.println(l.getList());
//        } catch (Exception ex) {
//            Logger.getLogger(XmlDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("ururur");
//        }
//    }
//
//    @Test
//    public void testGetById(){
//        XmlDataProvider provider = new XmlDataProvider();
//        Generic obj = new Note(6);
//        Result result = provider.getRecordById(obj);
//        System.out.println(result.getStatus());
//        if (result.getStatus().equals(ResultStatuses.OK)){
//            System.out.println(result.getList().get(0));             
//        }    
//        else
//            System.err.println(result.getMessage());
//    }
//    
//    @Test
//    public void testGetAll(){
//        XmlDataProvider provider = new XmlDataProvider();
//        Generic obj = new Aria();
//        Result result = provider.getAllRecords(obj);
//        System.out.println(result.getStatus());
//        if (result.getStatus().equals(ResultStatuses.OK)){
//            result.getList().stream().forEach(System.out::println);
//        }    
//        else
//            System.err.println(result.getMessage());
//    }
//    
//    @Test
//    public void testAdd(){
//        XmlDataProvider provider = new XmlDataProvider();
//        Note obj = new Note();
//        obj.setDescription("descqqq");
//        obj.setObjectId(3);
//        obj.setObjectType(Types.OPERA.toString());
//        Result result = provider.addRecord(obj);
//        System.out.println(result.getStatus());
//        if (result.getStatus().equals(ResultStatuses.OK)){
//            result = provider.getAllRecords(obj);
//            result.getList().stream().forEach(System.out::println);
//        }    
//        else
//            System.err.println(result.getMessage());
//    }
//    
//    @Test
//    public void testEdit(){
//        XmlDataProvider provider = new XmlDataProvider();
//        Note obj = new Note(6); 
//        obj.setObjectId(3);
//        obj.setDescription("descqqq");
//        obj.setObjectType(Types.LIBRETTO.toString());
//        Result result = provider.editRecord(obj);        
//        System.out.println(result.getStatus());
//        if (result.getStatus().equals(ResultStatuses.OK)){
//            result = provider.getAllRecords(obj);
//            result.getList().stream().forEach(System.out::println);
//        }    
//        else
//            System.err.println(result.getMessage());
//    }
//    
//    @Test
//    public void testDelete(){
//        XmlDataProvider provider = new XmlDataProvider();
//        Note obj = new Note();
//        Result result = provider.getAllRecords(obj);
//        System.out.println(result.getStatus());
//        if (result.getStatus().equals(ResultStatuses.OK)){
//            result.getList().stream().forEach(System.out::println);
//        }    
//        else
//            System.err.println(result.getMessage());
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
//        XmlDataProvider instance = new XmlDataProvider();
//        Result result = instance.findRecord(obj);
//        System.out.println(result.getStatus());
//        System.out.println(result.getMessage());
//        if (result.getStatus().equals(ResultStatuses.OK))
//            result.getList().stream().forEach(r -> System.out.println(r));
//    }
}

