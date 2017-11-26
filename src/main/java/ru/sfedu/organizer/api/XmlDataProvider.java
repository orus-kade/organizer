
package ru.sfedu.organizer.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import static ru.sfedu.organizer.Constants.*;
import ru.sfedu.organizer.model.*;
import static ru.sfedu.organizer.utils.ConfigurationUtil.getConfigurationEntry;

/**
 *
 * @author sterie
 */
public class XmlDataProvider implements IDataProvider{

    @Override
    public Result addRecord(Generic obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result editRecord(Generic obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result deleteRecord(Generic obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result getRecordById(Generic obj) {
        Result result; 
        try {
            XmlListEntity readObj = new XmlListEntity();
            Serializer serializer = new Persister();
            File file = new File(getFileName(obj));
            readObj = serializer.read(XmlListEntity.class, file);
            List<Generic> list = readObj.getList();
            list.removeIf(e -> e.getId()!=obj.getId());
            if (list.isEmpty()) result = new Result(ResultStatuses.ERROR, "Can't find object " + obj.getType() + " with id = " + obj.getId());
            else result = new Result(ResultStatuses.OK, list);            
        } catch (IOException ex) {
            Logger.getLogger(XmlDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(ResultStatuses.ERROR, ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(XmlDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            result = new Result(ResultStatuses.ERROR, ex.getMessage());
        }
        return result;
    }

    @Override
    public Result getAllRecords(Generic obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String getFileName(Generic obj) throws IOException {
        Types type = obj.getType();
        String file = null;
        switch (type){
            case ARIA : file = getConfigurationEntry(XML_PATH_ARIA);
                break;
            case COMPOSER : file = getConfigurationEntry(XML_PATH_COMPOSER);
                break;
            case LIBRETTO : file = getConfigurationEntry(XML_PATH_LIBRETTO);
                break;
            case OPERA : file = getConfigurationEntry(XML_PATH_OPERA); 
                break;
            case SINGER : file = getConfigurationEntry(XML_PATH_SINGER);
                break;
            case AUTHOR : file = getConfigurationEntry(XML_PATH_AUTHOR);
                break; 
            case NOTE: file = getConfigurationEntry(XML_PATH_NOTE);
                break; 
        }
        return file;
    }
    
    private Class getClass(Generic obj) {
        Types type = obj.getType();
        Class cl = null;
        switch (type){
            case ARIA : cl = Aria.class;
                break;
            case COMPOSER : cl = Composer.class;
                break;
            case LIBRETTO : cl = Libretto.class;
                break;
            case OPERA : cl = Opera.class; 
                break;
            case SINGER : cl = Singer.class;
                break;
            case AUTHOR : cl = Author.class;
                break;    
            case NOTE: cl = Note.class;    
                break; 
        }
        return cl;
    }
}
