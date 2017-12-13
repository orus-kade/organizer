
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.model.ResultStatuses;
import ru.sfedu.organizer.model.Result;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.apache.log4j.Logger;

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
    private static final Logger log = Logger.getLogger(XmlDataProvider.class);
    @Override
    public Result addRecord(Note obj) {
        Result r = checkNote(obj);        
        if (!ResultStatuses.OK.equals(r.getStatus())) return r;
        try {
            Serializer serializer = new Persister();
            File file = new File(getFileName(obj));
            XmlListEntity readObj = serializer.read(XmlListEntity.class, file);
            List<Generic> list = readObj.getList();          
            if (list != null){
               long lastId = list.stream().max(Comparator.comparingLong(e -> e.getId())).get().getId(); 
               obj.setId(lastId+1); 
            }
            else 
               obj.setId(1);
            list.add(obj);
            XmlListEntity writeObj = new XmlListEntity(list);
            serializer.write(writeObj, file);
            Result result = new Result(ResultStatuses.OK);
            return result;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        }
    }    
    
    @Override
    public Result editRecord(Note obj) {
        Result r = checkNote(obj);        
        if (!ResultStatuses.OK.equals(r.getStatus())) return r;
        try {
            Serializer serializer = new Persister();
            File file = new File(getFileName(obj));
            XmlListEntity readObj = serializer.read(XmlListEntity.class, file);
            List<Generic> list = readObj.getList();          
            list.removeIf(e -> e.getId() == obj.getId());
            list.add(obj);
            XmlListEntity writeObj = new XmlListEntity(list);
            serializer.write(writeObj, file);
            Result result = new Result(ResultStatuses.OK);
            return result;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        }    
    }

    @Override
    public Result deleteRecord(Note obj) {
        try {
            Serializer serializer = new Persister();
            File file = new File(getFileName(obj));
            XmlListEntity readObj = serializer.read(XmlListEntity.class, file);
            List<Generic> list = readObj.getList();
            if (!list.removeIf(e -> e.getId() == obj.getId())){
                Result result = new Result(ResultStatuses.ERROR, "Object " + obj + " not found");
                return result; 
            }
            XmlListEntity writeObj = new XmlListEntity(list);
            serializer.write(writeObj, file);
            Result result = new Result(ResultStatuses.OK);
            return result;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        }
    }
    
    @Override
    public Result getRecordById(Generic obj) {
        return getRecordById(obj, false);
    }
    
    public Result getRecordById(Generic obj, boolean check) {
        try {
            XmlListEntity readObj = new XmlListEntity();
            Serializer serializer = new Persister();
            File file = new File(getFileName(obj));
            readObj = serializer.read(XmlListEntity.class, file);
            List<Generic> list = readObj.getList();
            list.removeIf(e -> e.getId()!=obj.getId());
            if (list.isEmpty()){
                Result result = new Result(ResultStatuses.ERROR, "Can't find object " + obj.getType() + " with id = " + obj.getId());
                return result;
            }
            if (check){
                Result result = new Result(ResultStatuses.OK, list);
                return result;
            }
            Result result = new Result(ResultStatuses.OK);
            list.stream().forEach(g -> {
                try {
                    g = getRelations(g);
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                    result.setStatus(ResultStatuses.WARNING);
                    result.setMessage(result.getMessage() + " " + ex.getMessage());
                }
            }); 
            result.setList(list);
            return result;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        }
    }

    @Override
    public Result getAllRecords(Generic obj) {
        try {
            XmlListEntity readObj = new XmlListEntity();
            Serializer serializer = new Persister();
            File file = new File(getFileName(obj));
            readObj = serializer.read(XmlListEntity.class, file);
            List<Generic> list = readObj.getList();
            Result result = new Result(ResultStatuses.OK);
            list.stream().forEach(g -> {
                try {
                    g = getRelations(g);
                } catch (Exception ex) {
                    log.warn(ex.getMessage());
                    result.setStatus(ResultStatuses.WARNING);
                    result.setMessage(result.getMessage() + " " + ex.getMessage());
                }
            }); 
            result.setList(list);
            return result;            
        } catch (Exception ex) {
            log.error(ex.getMessage());
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        } 
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
    
    private Generic getRelations(Generic obj) throws Exception{
        Types type = obj.getType(); 
        switch (type){
           case ARIA : obj = getRelationsAria(obj);
                break;
           case COMPOSER : obj = getRelationsComposer(obj);
                break;
           case LIBRETTO : obj = getRelationsLibretto(obj);
                break;
           case OPERA : obj = getRelationsOpera(obj); 
                break;
           case SINGER : obj = getRelationsSinger(obj);
                break;
           case AUTHOR : obj = getRelationsAuthor(obj);
                break;
           case NOTE: break;    
        }
        return obj;
    }
    
    private Generic getRelationsAria(Generic obj) throws  Exception{
        Serializer serializer = new Persister();
        File file = new File(getConfigurationEntry(XML_PATH_ARIA_AUTHOR));
        XmlListRelations xmlList = serializer.read(XmlListRelations.class, file);
        List<Long> list = xmlList.getList().stream()
            .filter(r -> r.getId1() == obj.getId())
            .collect(ArrayList<Long>::new,
                    (a, r) ->  a.add(r.getId2()),
                    (a1, a2) -> a1.addAll(a2));
        Aria object = (Aria)obj;
        object.setAuthors(list);
            
        file = new File(getConfigurationEntry(XML_PATH_ARIA_COMPOSER));
        xmlList = serializer.read(XmlListRelations.class, file);
        list = xmlList.getList().stream()
            .filter(r -> r.getId1() == obj.getId())
            .collect(ArrayList<Long>::new,
                    (a, r) ->  a.add(r.getId2()),
                    (a1, a2) -> a1.addAll(a2));
        object.setComposers(list); 
            
        file = new File(getConfigurationEntry(XML_PATH_ARIA_SINGER));
        xmlList = serializer.read(XmlListRelations.class, file);
        list = xmlList.getList().stream()
            .filter(r -> r.getId1() == obj.getId())
            .collect(ArrayList<Long>::new,
                    (a, r) ->  a.add(r.getId2()),
                    (a1, a2) -> a1.addAll(a2));
        object.setSingers(list);   
            
        return object;
    }
    
    private Generic getRelationsAuthor(Generic obj) throws  Exception{
        Serializer serializer = new Persister();
        File file = new File(getConfigurationEntry(XML_PATH_ARIA_AUTHOR));
        XmlListRelations xmlList = serializer.read(XmlListRelations.class, file);
        List<Long> list = xmlList.getList().stream()
            .filter(r -> r.getId2() == obj.getId())
            .collect(ArrayList<Long>::new,
                    (a, r) ->  a.add(r.getId1()),
                    (a1, a2) -> a1.addAll(a2));
        Author object = (Author)obj;
        object.setAries(list);
            
        file = new File(getConfigurationEntry(XML_PATH_AUTHOR_LIBRETTO));
        xmlList = serializer.read(XmlListRelations.class, file);
        list = xmlList.getList().stream()
            .filter(r -> r.getId1() == obj.getId())
            .collect(ArrayList<Long>::new,
                    (a, r) ->  a.add(r.getId2()),
                    (a1, a2) -> a1.addAll(a2));
        object.setLibrettos(list); 
        return object;
    }
    
    private Generic getRelationsComposer(Generic obj) throws  Exception{
        Serializer serializer = new Persister();
        File file = new File(getConfigurationEntry(XML_PATH_ARIA_COMPOSER));
        XmlListRelations xmlList = serializer.read(XmlListRelations.class, file);
        List<Long> list = xmlList.getList().stream()
            .filter(r -> r.getId2() == obj.getId())
            .collect(ArrayList<Long>::new,
                    (a, r) ->  a.add(r.getId1()),
                    (a1, a2) -> a1.addAll(a2));
        Composer object = (Composer)obj;
        object.setAries(list);
        return object;
    }
    
    private Generic getRelationsLibretto(Generic obj) throws  Exception{
        Serializer serializer = new Persister();
        File file = new File(getConfigurationEntry(XML_PATH_AUTHOR_LIBRETTO));
        XmlListRelations xmlList = serializer.read(XmlListRelations.class, file);
        List<Long> list = xmlList.getList().stream()
            .filter(r -> r.getId2() == obj.getId())
            .collect(ArrayList<Long>::new,
                    (a, r) ->  a.add(r.getId1()),
                    (a1, a2) -> a1.addAll(a2));
        Libretto object = (Libretto)obj;
        object.setAuthors(list);
        return object;
    }
    
    private Generic getRelationsOpera(Generic obj) throws  Exception{
        Serializer serializer = new Persister();
        File file = new File(getConfigurationEntry(XML_PATH_ARIA));
        XmlListEntity xmlList = serializer.read(XmlListEntity.class, file);
        if (xmlList.getList().isEmpty()) return obj;
        List<Long> list = xmlList.getList().stream()
            .filter(r -> ((Aria)r).getOperaId() == obj.getId())
            .collect(ArrayList<Long>::new,
                    (a, r) ->  a.add(r.getId()),
                    (a1, a2) -> a1.addAll(a2));
        Opera object = (Opera)obj;
        object.setAries(list);
        return object;
    }
    
    private Generic getRelationsSinger(Generic obj) throws  Exception{
        Serializer serializer = new Persister();
        File file = new File(getConfigurationEntry(XML_PATH_ARIA_SINGER));
        XmlListRelations xmlList = serializer.read(XmlListRelations.class, file);
        List<Long> list = xmlList.getList().stream()
            .filter(r -> r.getId2() == obj.getId())
            .collect(ArrayList<Long>::new,
                    (a, r) ->  a.add(r.getId1()),
                    (a1, a2) -> a1.addAll(a2));
        Singer object = (Singer)obj;
        object.setAries(list);
        return object;
    }

    
    
    private Result checkNote(Generic obj){
        Note note = (Note)obj;
        Result result = new Result();
        if (note == null){
            result.setStatus(ResultStatuses.ERROR);
            result.setMessage("Note: Object is null");
        }
        else{
            try{
                Types.valueOf(note.getObjectType());
            } catch(IllegalArgumentException ex){
                log.error(ex.getMessage());
                return new Result(ResultStatuses.ERROR, ex.getMessage());
            } 
            Generic object = null;
            switch (Types.valueOf(note.getObjectType())){
                case ARIA : object = new Aria(note.getObjectId());
                    break;
                case AUTHOR : object = new Author(note.getObjectId());
                    break;
                case COMPOSER : object = new Composer(note.getObjectId());
                    break;
                case LIBRETTO : object = new Libretto(note.getObjectId());
                    break;
                case OPERA : object = new Opera(note.getObjectId());
                    break;
                case SINGER : object = new Singer(note.getObjectId());
                    break;
            }        
            result = getRecordById(object, true);
        }       
        return result;
    }
}
