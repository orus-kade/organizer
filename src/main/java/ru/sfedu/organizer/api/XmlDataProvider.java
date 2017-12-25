
package ru.sfedu.organizer.api;

import ru.sfedu.organizer.model.ResultStatuses;
import ru.sfedu.organizer.model.Result;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import static ru.sfedu.organizer.Constants.*;
import ru.sfedu.organizer.model.*;
import ru.sfedu.organizer.utils.ConfigurationUtil;


/**
 *
 * @author orus-kade
 */
public class XmlDataProvider implements IDataProvider{    
    private static final Logger log = Logger.getLogger(XmlDataProvider.class);
    //private final ConfigurationUtil config = new ConfigurationUtil();  

    private ConfigurationUtil config;

    /**
     *
     * @param path
     */
    public XmlDataProvider(String path) {
        this.config = new ConfigurationUtil(path);
    }
    
    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Result addRecord(Note obj) {
        Result r = checkNote(obj);        
        if (!ResultStatuses.OK.equals(r.getStatus())) return r;
        try {
            Serializer serializer = new Persister();
            File file = new File(getFileName(obj));
            XmlListEntity readObj = serializer.read(XmlListEntity.class, file);
            List<Generic> list = new ArrayList<Generic>();
            list.addAll(readObj.getList());          
//            if (!list.isEmpty()){
//               long lastId = list.stream().max(Comparator.comparingLong(e -> e.getId())).get().getId(); 
//               obj.setId(lastId+1); 
//            }
//            else 
//               obj.setId(1);
            if (!list.isEmpty()){   
                if (list.stream().filter(e -> e.getId() == obj.getId()).count() > 0){
                    Result result = new Result(ResultStatuses.ERROR, "Object with id = " + obj.getId() + " is already exists!");
                    log.error(result.getMessage());
                    return result;                    
                }
            }

            list.add(obj);
            List<Generic> resultList = new ArrayList<Generic>();
            resultList.add(obj);
            XmlListEntity writeObj = new XmlListEntity(list);
            serializer.write(writeObj, file);
            Result result = new Result(ResultStatuses.OK, resultList);
            return result;
        } catch (Exception ex) {
            log.error(ex);
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        }
    }    
    
    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Result editRecord(Note obj) {
        Result r = checkNote(obj);        
        if (!ResultStatuses.OK.equals(r.getStatus())) return r;
        try {
            Serializer serializer = new Persister();
            File file = new File(getFileName(obj));
            XmlListEntity readObj = serializer.read(XmlListEntity.class, file);
            List<Generic> list = new ArrayList<Generic>();
            list.addAll(readObj.getList());
            if(!list.removeIf(e -> e.getId() == obj.getId())){
                Result result = new Result(ResultStatuses.ERROR, "Object " + obj + " not found");
                log.error(result.getMessage());
                return result; 
            }
            list.add(obj);
            XmlListEntity writeObj = new XmlListEntity(list);
            serializer.write(writeObj, file);
            Result result = new Result(ResultStatuses.OK);
            return result;
        } catch (Exception ex) {
            log.error(ex);
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        }    
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Result deleteRecord(Note obj) {
        try {
            Serializer serializer = new Persister();
            File file = new File(getFileName(obj));
            XmlListEntity readObj = serializer.read(XmlListEntity.class, file);
            List<Generic> list = new ArrayList<Generic>();
            list.addAll(readObj.getList());
            if (!list.removeIf(e -> e.getId() == obj.getId())){
                Result result = new Result(ResultStatuses.ERROR, "Object " + obj + " not found");
                log.error(result.getMessage());
                return result; 
            }
            XmlListEntity writeObj = new XmlListEntity(list);
            serializer.write(writeObj, file);
            Result result = new Result(ResultStatuses.OK);
            return result;
        } catch (Exception ex) {
            log.error(ex);
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        }
    }
    
    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Result getRecordById(Generic obj) {
        return getRecordById(obj, false);
    }
    
    /**
     *
     * @param obj
     * @param check
     * @return
     */
    public Result getRecordById(Generic obj, boolean check) {
        try {
            XmlListEntity readObj = new XmlListEntity();
            Serializer serializer = new Persister();
            File file = new File(getFileName(obj));
            readObj = serializer.read(XmlListEntity.class, file);
            List<Generic> list = new ArrayList<Generic>();
            list.addAll(readObj.getList());
            list.removeIf(e -> e.getId()!=obj.getId());
            if (list.isEmpty()){
                Result result = new Result(ResultStatuses.NOTFOUND, "Can't find object " + obj.getType() + " with id = " + obj.getId());
                log.error(result.getMessage());
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
                    log.error(ex);
                    result.setStatus(ResultStatuses.WARNING);
                    result.setMessage(result.getMessage() + " " + ex.getMessage());
                }
            }); 
            result.setList(list);
            return result;
        } catch (Exception ex) {
            log.error(ex);
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        }
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Result getAllRecords(Generic obj) {
        try {
            XmlListEntity readObj = new XmlListEntity();
            Serializer serializer = new Persister();
            File file = new File(getFileName(obj));
            readObj = serializer.read(XmlListEntity.class, file);
            List<Generic> list = new ArrayList<Generic>();
            list.addAll(readObj.getList());
            if (list.isEmpty()){
                return new Result(ResultStatuses.NOTFOUND, obj.getType().toString());
            }
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
            log.error(ex);
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        } 
    }
    
    private String getFileName(Generic obj) throws IOException {
        Types type = obj.getType();
        String file = null;
        switch (type){
            case ARIA : file = config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_ARIA);
                break;
            case COMPOSER : file = config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_COMPOSER);
                break;
            case LIBRETTO : file = config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_LIBRETTO);
                break;
            case OPERA : file = config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_OPERA); 
                break;
            case SINGER : file = config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_SINGER);
                break;
            case AUTHOR : file = config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_AUTHOR);
                break; 
            case NOTE: file = config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_NOTE);
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
        Aria object = (Aria)obj;
        File file = new File(config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_ARIA_AUTHOR));
        XmlListRelations xmlList = serializer.read(XmlListRelations.class, file);
        List<Long> list = new ArrayList<Long>();
        list.addAll(xmlList.getList().stream()
                    .filter(r -> r.getId1() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId2()),
                            (a1, a2) -> a1.addAll(a2)));
        object.setAuthors(list);
                   
        file = new File(config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_ARIA_COMPOSER));
        xmlList = serializer.read(XmlListRelations.class, file);
        list.clear();
        list.addAll(xmlList.getList().stream()
                    .filter(r -> r.getId1() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId2()),
                            (a1, a2) -> a1.addAll(a2)));
        object.setComposers(list); 
            
        file = new File(config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_ARIA_SINGER));
        xmlList = serializer.read(XmlListRelations.class, file);
        list.clear();
        list.addAll(xmlList.getList().stream()
                    .filter(r -> r.getId1() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId2()),
                            (a1, a2) -> a1.addAll(a2)));
        object.setSingers(list);   
        
        Result resultNotes = getAllRecords(new Note());            
        if (!resultNotes.getStatus().equals(ResultStatuses.OK)){
            log.warn(resultNotes.getStatus() + " " + resultNotes.getMessage());
        }
        else{
            list.clear();
            list.addAll(resultNotes.getList().stream()
                        .filter(e -> (obj.getId() == ((Note)e).getObjectId() && ((Note)e).getObjectType().equals(obj.getType().toString())))
                        .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId()),
                            (a1, a2) -> a1.addAll(a2)));
            object.setNotes(list); 
        }
        return object;
    }
    
    private Generic getRelationsAuthor(Generic obj) throws  Exception{
        Serializer serializer = new Persister();
        Author object = (Author)obj;
        File file = new File(config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_ARIA_AUTHOR));
        XmlListRelations xmlList = serializer.read(XmlListRelations.class, file);
        List<Long> list = new ArrayList<Long>();
        list.addAll(xmlList.getList().stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId1()),
                            (a1, a2) -> a1.addAll(a2)));        
        object.setAries(list);
            
        file = new File(config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_AUTHOR_LIBRETTO));
        xmlList = serializer.read(XmlListRelations.class, file);
        list.clear();
        list.addAll(xmlList.getList().stream()
                    .filter(r -> r.getId1() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId2()),
                            (a1, a2) -> a1.addAll(a2)));
        object.setLibrettos(list); 
        
        Result resultNotes = getAllRecords(new Note());            
        if (!resultNotes.getStatus().equals(ResultStatuses.OK)){
            log.warn(resultNotes.getStatus() + " " + resultNotes.getMessage());
        }
        else{
            list.clear();
            list.addAll(resultNotes.getList().stream()
                        .filter(e -> (obj.getId() == ((Note)e).getObjectId() && ((Note)e).getObjectType().equals(obj.getType().toString())))
                        .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId()),
                            (a1, a2) -> a1.addAll(a2)));
            object.setNotes(list); 
        }
        return object;
    }
    
    private Generic getRelationsComposer(Generic obj) throws  Exception{
        Serializer serializer = new Persister();
        Composer object = (Composer)obj;
        File file = new File(config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_ARIA_COMPOSER));
        XmlListRelations xmlList = serializer.read(XmlListRelations.class, file);
        List<Long> list = new ArrayList<Long>();
        list.addAll(xmlList.getList().stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId1()),
                            (a1, a2) -> a1.addAll(a2)));
        object.setAries(list);
        
        Result resultNotes = getAllRecords(new Note());            
        if (!resultNotes.getStatus().equals(ResultStatuses.OK)){
            log.warn(resultNotes.getStatus() + " " + resultNotes.getMessage());
        }
        else{
            list.clear();
            list.addAll(resultNotes.getList().stream()
                        .filter(e -> (obj.getId() == ((Note)e).getObjectId() && ((Note)e).getObjectType().equals(obj.getType().toString())))
                        .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId()),
                            (a1, a2) -> a1.addAll(a2)));
            object.setNotes(list); 
        }
        return object;
    }
    
    private Generic getRelationsLibretto(Generic obj) throws  Exception{
        Serializer serializer = new Persister();
        Libretto object = (Libretto)obj;
        File file = new File(config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_AUTHOR_LIBRETTO));
        XmlListRelations xmlList = serializer.read(XmlListRelations.class, file);
        List<Long> list = new ArrayList<Long>();
        list.addAll(xmlList.getList().stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId1()),
                            (a1, a2) -> a1.addAll(a2)));
        object.setAuthors(list);
        
        Result resultNotes = getAllRecords(new Note());            
        if (!resultNotes.getStatus().equals(ResultStatuses.OK)){
            log.warn(resultNotes.getStatus() + " " + resultNotes.getMessage());
        }
        else{
            list.clear();
            list.addAll(resultNotes.getList().stream()
                        .filter(e -> (obj.getId() == ((Note)e).getObjectId() && ((Note)e).getObjectType().equals(obj.getType().toString())))
                        .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId()),
                            (a1, a2) -> a1.addAll(a2)));
            object.setNotes(list); 
        }
        return object;
    }
    
    private Generic getRelationsOpera(Generic obj) throws  Exception{
        Serializer serializer = new Persister();
        Opera object = (Opera)obj;
        File file = new File(config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_ARIA));
        XmlListEntity xmlList = serializer.read(XmlListEntity.class, file);
        List<Long> list = new ArrayList<Long>();
        list.addAll(xmlList.getList().stream()
                    .filter(r -> ((Aria)r).getOperaId() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId()),
                            (a1, a2) -> a1.addAll(a2)));
        object.setAries(list);
        
        Result resultNotes = getAllRecords(new Note());            
        if (!resultNotes.getStatus().equals(ResultStatuses.OK)){
            log.warn(resultNotes.getStatus() + " " + resultNotes.getMessage());
        }
        else{
            list.clear();
            list.addAll(resultNotes.getList().stream()
                        .filter(e -> (obj.getId() == ((Note)e).getObjectId() && ((Note)e).getObjectType().equals(obj.getType().toString())))
                        .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId()),
                            (a1, a2) -> a1.addAll(a2)));
            object.setNotes(list); 
        }
        return object;
    }
    
    private Generic getRelationsSinger(Generic obj) throws  Exception{
        Serializer serializer = new Persister();
        Singer object = (Singer)obj;
        File file = new File(config.getConfigurationEntry(XML_PATH) + config.getConfigurationEntry(XML_PATH_ARIA_SINGER));
        XmlListRelations xmlList = serializer.read(XmlListRelations.class, file);
        List<Long> list = new ArrayList<Long>();
        list.addAll(xmlList.getList().stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId1()),
                            (a1, a2) -> a1.addAll(a2)));
        object.setAries(list);
        
        Result resultNotes = getAllRecords(new Note());            
        if (!resultNotes.getStatus().equals(ResultStatuses.OK)){
            log.warn(resultNotes.getStatus() + " " + resultNotes.getMessage());
        }
        else{
            list.clear();
            list.addAll(resultNotes.getList().stream()
                        .filter(e -> (obj.getId() == ((Note)e).getObjectId() && ((Note)e).getObjectType().equals(obj.getType().toString())))
                        .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId()),
                            (a1, a2) -> a1.addAll(a2)));
            object.setNotes(list); 
        }
        return object;
    }   
    
    private Result checkNote(Generic obj){
        Note note = (Note)obj;
        Result result = new Result();
        if (note == null){
            result.setStatus(ResultStatuses.ERROR);
            result.setMessage("Note: Object is null");
            log.error(result.getMessage());
            return result;
        }
        if (obj.getId() <= 0){
            result.setStatus(ResultStatuses.ERROR);
            result.setMessage("Note: imposible id <= 0");
            log.error(result.getMessage());
            return result;
        }
            try{
                Types.valueOf(note.getObjectType());
            } catch(IllegalArgumentException ex){
                log.error(ex);
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
        return result;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Result findRecord(Generic obj) {
        Optional<Generic> object = Optional.ofNullable(obj);
        if (!object.isPresent()) {
            log.error("Object is null");
            return new Result(ResultStatuses.ERROR, "Object is null");
        }
        else{            
            Optional<Types> type = Optional.ofNullable(obj.getType());
            if (!type.isPresent()){
                log.error("Object type is null");
                return new Result(ResultStatuses.ERROR, "Object type is null");
            }  
            if (obj.getId() > 0){
                return getRecordById(obj);
            }
            Result result = new Result();
            switch (type.get()){
                case ARIA : result = findAria((Aria)obj);
                    break;
                case COMPOSER : result = findComposer((Composer)obj);
                    break;
                case LIBRETTO : //result = findLibretto((Libretto)obj);
                    result = new Result(ResultStatuses.WARNING, "Unsupported type");
                    break;
                case OPERA : result = findOpera((Opera)obj);
                    break;
                case SINGER : result = findSinger((Singer)obj);
                    break;
                case AUTHOR : result = findAuthor((Author)obj);
                    break;    
                case NOTE: result = findNote((Note)obj);  
                    break; 
            }
            if (result.getStatus().equals(ResultStatuses.OK)){
                List<Generic> list = new ArrayList<Generic>();
                list.addAll(result.getList());
                list.stream().forEach(g -> {
                    try {
                        g = getRelations(g);
                    } catch (IOException ex) {
                        log.warn(ex);
                    } catch (Exception ex) {
                        log.warn(ex);
                    }
                });
                result.setList(list);
            }
            return result;
        }
    }
    
    private Result findAria(Aria obj){
        Result result = getAllRecords(obj);
        List<Generic> list = new ArrayList();
        if (!result.getStatus().equals(ResultStatuses.OK)){
            return new Result(result.getStatus(), result.getMessage());
        }
        list.addAll(result.getList());
        List<Generic> resultList = new ArrayList<Generic>();
        list.removeIf( e ->{
            if (((Aria)e).getText() != null)
                if (obj.getText() != null && !((Aria)e).getText().toLowerCase().contains(obj.getText().toLowerCase()))
                    return true;                               
            if (((Aria)e).getTitle() != null)
                if (obj.getTitle() != null && !((Aria)e).getTitle().toLowerCase().contains(obj.getTitle().toLowerCase()))
                    return true;
            return false;
        });
        
        resultList.addAll(list);
        if (resultList.isEmpty()) return new Result(ResultStatuses.NOTFOUND, obj.toString());
        return new Result(ResultStatuses.OK, list);
    }
    
    private Result findComposer(Composer obj){
        Result result = getAllRecords(obj);
        List<Generic> list = new ArrayList();
        if (!result.getStatus().equals(ResultStatuses.OK)){
            return new Result(result.getStatus(), result.getMessage());
        }
        list.addAll(result.getList());
        List<Generic> resultList = new ArrayList<Generic>();
        list.removeIf( e ->{
            if (((Composer)e).getName() != null)
                if (obj.getName()!= null && !((Composer)e).getName().toLowerCase().contains(obj.getName().toLowerCase()))
                    return true;
            if (((Composer)e).getSurname() != null)
                if (obj.getSurname()!= null && !((Composer)e).getSurname().toLowerCase().contains(obj.getSurname().toLowerCase()))
                    return true;
            if (((Composer)e).getPatronymic() != null)
                if (obj.getPatronymic()!= null && !((Composer)e).getPatronymic().toLowerCase().contains(obj.getPatronymic().toLowerCase()))
                    return true;                  
            return false;                       
        });
        resultList.addAll(list);
        if (resultList.isEmpty()) return new Result(ResultStatuses.NOTFOUND, obj.toString());
        return new Result(ResultStatuses.OK, list);
    }
    
    private Result findAuthor(Author obj){
        Result result = getAllRecords(obj);
        List<Generic> list = new ArrayList();
        if (!result.getStatus().equals(ResultStatuses.OK)){
            return new Result(result.getStatus(), result.getMessage());
        }
        list.addAll(result.getList());
        List<Generic> resultList = new ArrayList<Generic>();
        list.removeIf( e ->{
            if (((Author)e).getName() != null)
                if (obj.getName()!= null && !((Author)e).getName().toLowerCase().contains(obj.getName().toLowerCase()))
                    return true;
            if (((Author)e).getSurname() != null)
                if (obj.getSurname()!= null && !((Author)e).getSurname().toLowerCase().contains(obj.getSurname().toLowerCase()))
                    return true;
            if (((Author)e).getPatronymic() != null)
                if (obj.getPatronymic()!= null && !((Author)e).getPatronymic().toLowerCase().contains(obj.getPatronymic().toLowerCase()))
                    return true;
            return false;                       
        });
        resultList.addAll(list);
        if (resultList.isEmpty()) return new Result(ResultStatuses.NOTFOUND, obj.toString());
        return new Result(ResultStatuses.OK, list);
    }
    
    private Result findSinger(Singer obj){
        Result result = getAllRecords(obj);
        List<Generic> list = new ArrayList();
        if (!result.getStatus().equals(ResultStatuses.OK)){
            return new Result(result.getStatus(), result.getMessage());
        }
        list.addAll(result.getList());
        List<Generic> resultList = new ArrayList<Generic>();
        list.removeIf( e ->{
            if (((Singer)e).getName() != null)
                if (obj.getName()!= null && !((Singer)e).getName().toLowerCase().contains(obj.getName().toLowerCase()))
                    return true;
            if (((Singer)e).getSurname() != null)
                if (obj.getSurname()!= null && !((Singer)e).getSurname().toLowerCase().contains(obj.getSurname().toLowerCase()))
                    return true;
            if (((Singer)e).getPatronymic() != null)
                if (obj.getPatronymic()!= null && !((Singer)e).getPatronymic().toLowerCase().contains(obj.getPatronymic().toLowerCase()))
                    return true;
            if (((Singer)e).getVoice() != null)
                if (obj.getVoice()!= null && !((Singer)e).getVoice().toLowerCase().contains(obj.getVoice().toLowerCase()))
                    return true;
            return false;                       
        });
        resultList.addAll(list);
        if (resultList.isEmpty()) return new Result(ResultStatuses.NOTFOUND, obj.toString());
        return new Result(ResultStatuses.OK, list);
    }
    
    
    private Result findOpera(Opera obj){
        Result result = getAllRecords(obj);
        List<Generic> list = new ArrayList();
        if (!result.getStatus().equals(ResultStatuses.OK)){
            return new Result(result.getStatus(), result.getMessage());
        }
        list.addAll(result.getList());
        List<Generic> resultList = new ArrayList<Generic>();
        list.removeIf( e ->{
            if (((Opera)e).getTitle() != null)
                if (obj.getTitle() != null && !((Opera)e).getTitle().toLowerCase().contains(obj.getTitle().toLowerCase()))
                    return true;
            return false;                       
        });
        resultList.addAll(list);
        if (resultList.isEmpty()) return new Result(ResultStatuses.NOTFOUND, obj.toString());
        return new Result(ResultStatuses.OK, list);
    }
    
    private Result findNote(Note obj){
        Result result = getAllRecords(obj);
        List<Generic> list = new ArrayList();
        if (!result.getStatus().equals(ResultStatuses.OK)){
            return new Result(result.getStatus(), result.getMessage());
        }
        list.addAll(result.getList());
        List<Generic> resultList = new ArrayList<Generic>();
        list.removeIf( e ->{
            if (((Note)e).getObjectType() != null)
                if (obj.getObjectType()!= null && !((Note)e).getObjectType().toUpperCase().equals(obj.getObjectType().toUpperCase()))
                    return true;
            if (((Note)e).getDescription() != null)
                if (obj.getDescription()!= null && !((Note)e).getDescription().toLowerCase().contains(obj.getDescription().toLowerCase()))  
                    return true;
            if (obj.getObjectId()> 0 && ((Note)e).getObjectId() != obj.getObjectId())
                return true;
            return false;                     
        });
        resultList.addAll(list);
        if (resultList.isEmpty()) return new Result(ResultStatuses.NOTFOUND, obj.toString());
        return new Result(ResultStatuses.OK, list);
    }
}
