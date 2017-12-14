
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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import static ru.sfedu.organizer.Constants.*;
import ru.sfedu.organizer.model.*;
import static ru.sfedu.organizer.utils.ConfigurationUtil.getConfigurationEntry;


/**
 *
 * @author orus-kade
 */
public class XmlDataProviderTest {
    
    public XmlDataProviderTest() {
    }
    
    @Test
    public void test() throws IOException{
        CsvDataProvider pr = new CsvDataProvider();
        List<Generic> list = pr.getAllRecords(new Aria()).getList();
        System.out.println(list);
        XmlListEntity ll = new XmlListEntity();
        ll.setList(list);
        Serializer serializer = new Persister();
        File result = new File(getConfigurationEntry(XML_PATH_ARIA));
        try {
            serializer.write(ll, result);
            XmlListEntity l = serializer.read(XmlListEntity.class, result);
            System.out.println(l.getList());
        } catch (Exception ex) {
            Logger.getLogger(XmlDataProviderTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ururur");
        }
    }

    @Test
    public void testGetById(){
        XmlDataProvider provider = new XmlDataProvider();
        Generic obj = new Note(6);
        Result result = provider.getRecordById(obj);
        System.out.println(result.getStatus());
        if (result.getStatus().equals(ResultStatuses.OK)){
            System.out.println(result.getList().get(0));             
        }    
        else
            System.err.println(result.getMessage());
    }
    
    @Test
    public void testGetAll(){
        XmlDataProvider provider = new XmlDataProvider();
        Generic obj = new Singer();
        Result result = provider.getAllRecords(obj);
        System.out.println(result.getStatus());
        if (result.getStatus().equals(ResultStatuses.OK)){
            result.getList().stream().forEach(System.out::println);
        }    
        else
            System.err.println(result.getMessage());
    }
    
    @Test
    public void testAdd(){
        XmlDataProvider provider = new XmlDataProvider();
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
    }
    
    @Test
    public void testEdit(){
        XmlDataProvider provider = new XmlDataProvider();
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
    }
    
    @Test
    public void testDelete(){
        XmlDataProvider provider = new XmlDataProvider();
        Note obj = new Note();
        Result result = provider.getAllRecords(obj);
        System.out.println(result.getStatus());
        if (result.getStatus().equals(ResultStatuses.OK)){
            result.getList().stream().forEach(System.out::println);
        }    
        else
            System.err.println(result.getMessage());
    }
}

