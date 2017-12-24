
package ru.sfedu.organizer.api;
import ru.sfedu.organizer.model.ResultStatuses;
import ru.sfedu.organizer.model.Result;
import com.opencsv.bean.*;
import com.opencsv.exceptions.*;
import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;
import static ru.sfedu.organizer.Constants.*;
import ru.sfedu.organizer.model.*;
import static ru.sfedu.organizer.model.Types.*;
import ru.sfedu.organizer.utils.ConfigurationUtil;

/**
 *
 * @author sterie
 */
public class CsvDataProvider implements IDataProvider{
    private static final Logger log = Logger.getLogger(CsvDataProvider.class);
    //private final ConfigurationUtil config = new ConfigurationUtil();
    private ConfigurationUtil config;

    /**
     *
     * @param path
     */
    public CsvDataProvider(String path) {
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
            Reader reader;
            reader = new FileReader(getFileName(obj));        
            CsvToBean<Generic> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(getClass(obj))
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .build();         
            List<Generic> list = new ArrayList<Generic>();
            list.addAll(csvToBean.parse());
            reader.close();
                        
//            if (!list.isEmpty()){
//                long lastId = list.stream().max(Comparator.comparingLong(e -> e.getId())).get().getId(); 
//                obj.setId(lastId+1); 
//            }
//            else 
//                obj.setId(1);

            if (!list.isEmpty()){   
                if (list.stream().filter(e -> e.getId() == obj.getId()).count() > 0){
                    Result result = new Result(ResultStatuses.ERROR, "Object with id = " + obj.getId() + " is already exists!");
                    log.error(result.getMessage());
                    return result;                    
                }
            }
            
            list.add(obj);
            Writer writer;
            writer = new FileWriter(getFileName(obj));              
            StatefulBeanToCsv<Generic> beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withEscapechar('\\')
                    .withQuotechar('\'')
                    .withSeparator(';')
                    .build();
            beanWriter.write(list);
            writer.close();
            Result result = new Result();
            List<Generic> resultList = new ArrayList<Generic>();
            resultList.add(obj);
            result.setList(resultList);
            result.setStatus(ResultStatuses.OK);            
            return result;
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException | IllegalStateException ex) {
            log.error(ex);
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        }        
    }
        
    private Result checkNote(Generic obj){        
        Result result = new Result();
        if (obj == null){
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
            Note note = (Note)obj;
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
    public Result editRecord(Note obj) {        
        Result r = checkNote(obj);
        if (!ResultStatuses.OK.equals(r.getStatus())) return r;
        try {    
            Reader reader;
            reader = new FileReader(getFileName(obj));        
            CsvToBean<Generic> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(getClass(obj))
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .build();         
            List<Generic> list = new ArrayList<Generic>();
            list.addAll(csvToBean.parse());
            reader.close();  
            if (!list.removeIf(e -> e.getId() == obj.getId())){
                Result result = new Result(ResultStatuses.ERROR, "Object " + obj + " not found");
                log.error(result.getMessage());
                return result; 
            }
            list.add(obj);
            Writer writer;
            writer = new FileWriter(getFileName(obj));             
            StatefulBeanToCsv<Generic> beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withEscapechar('\\')
                    .withQuotechar('\'')
                    .withSeparator(';')
                    .build();
            beanWriter.write(list);
            writer.close();
            Result result = new Result();            
            result.setStatus(ResultStatuses.OK);
            return result;
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException | IllegalStateException ex) {
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
            Reader reader;
            reader = new FileReader(getFileName(obj));        
            CsvToBean<Generic> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(getClass(obj))
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .build();         
            List<Generic> list = new ArrayList<Generic>();
            list.addAll(csvToBean.parse());
            reader.close(); 
            if (!list.removeIf(e -> e.getId() == obj.getId())){
                Result result = new Result(ResultStatuses.ERROR, "Object " + obj + " not found");
                log.error(result.getMessage());
                return result; 
            }
            Writer writer;
            writer = new FileWriter(getFileName(obj)); 
            StatefulBeanToCsv<Generic> beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withEscapechar('\\')
                    .withQuotechar('\'')
                    .withSeparator(';')
                    .build();
            beanWriter.write(list);
            writer.close();
            Result result = new Result();
            result.setStatus(ResultStatuses.OK);
            return result;
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException | IllegalStateException ex) {
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

    private Result getRecordById(Generic obj, boolean check){
        try {
            Reader reader;
            reader = new FileReader(getFileName(obj));  
            CsvFilter filter = new CsvFilter(obj.getId());
            CsvToBean<Generic> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(getClass(obj))
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .withFilter(filter)
                    .build();         
            List<Generic> list = new ArrayList<Generic>();
            list.addAll(csvToBean.parse());
            reader.close();
            if (list.isEmpty()){
                Result result = new Result(ResultStatuses.NOTFOUND, "Can't find object "+obj.getType()+" with id = "+obj.getId());
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
                } catch (IOException ex) {
                    log.warn(ex);
                    result.setStatus(ResultStatuses.WARNING);
                    result.setMessage(result.getMessage() + " " + ex.getMessage());
                }
            });
            result.setList(list);
            return result;            
        } catch (IOException ex) {
            log.error(ex);
            Result result = new Result(ResultStatuses.ERROR,ex.getMessage());
            return result;
        }
    }
    
    private String getFileName(Generic obj) throws IOException {
        Types type = obj.getType();
        String file = null;
        switch (type){
            case ARIA : file = config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_ARIA);
                break;
            case COMPOSER : file = config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_COMPOSER);
                break;
            case LIBRETTO : file = config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_LIBRETTO);
                break;
            case OPERA : file = config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_OPERA); 
                break;
            case SINGER : file = config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_SINGER);
                break;
            case AUTHOR : file = config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_AUTHOR);
                break; 
            case NOTE: file = config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_NOTE);
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
    
    private Generic getRelations(Generic obj) throws IOException{
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
    
    private Generic getRelationsAria(Generic obj) throws IOException{
        Reader reader; 
        Aria aria = (Aria)obj;
        reader = new FileReader(config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_ARIA_AUTHOR));
        CsvToBean<Relation> csvToBean = new CsvToBeanBuilder(reader)
            .withType(Relation.class)
            .withEscapeChar('\\')
            .withQuoteChar('\'')
            .withSeparator(';')
            .build();
        List<Relation> relations = new ArrayList<Relation>();
        relations.addAll(csvToBean.parse());
        reader.close();
        List<Long> list = new ArrayList<Long>();
        if (!relations.isEmpty()){
            list.addAll(relations.stream()
                            .filter(r -> r.getId1() == obj.getId())
                            .collect(ArrayList<Long>::new,
                                    (a, r) ->  a.add(r.getId2()),
                                    (a1, a2) -> a1.addAll(a2)));
            aria.setAuthors(list);
        }
          
        reader = new FileReader(config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_ARIA_COMPOSER));
        csvToBean = new CsvToBeanBuilder(reader)
            .withType(Relation.class)
            .withEscapeChar('\\')
            .withQuoteChar('\'')
            .withSeparator(';')
            .build();
        relations.clear();
        relations.addAll(csvToBean.parse());
        list.clear();
        if (!relations.isEmpty()){
            list.addAll(relations.stream()
                            .filter(r -> r.getId1() == obj.getId())
                            .collect(ArrayList<Long>::new,
                                    (a, r) ->  a.add(r.getId2()),
                                    (a1, a2) -> a1.addAll(a2)));
            aria.setComposers(list); 
        }
            
        reader = new FileReader(config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_ARIA_SINGER));
        csvToBean = new CsvToBeanBuilder(reader)
            .withType(Relation.class)
            .withEscapeChar('\\')
            .withQuoteChar('\'')
            .withSeparator(';')
            .build();
        relations.clear();
        relations.addAll(csvToBean.parse());
        list.clear();
        if (!relations.isEmpty()){
            list.addAll(relations.stream()
                            .filter(r -> r.getId1() == obj.getId())
                            .collect(ArrayList<Long>::new,
                                    (a, r) ->  a.add(r.getId2()),
                                    (a1, a2) -> a1.addAll(a2)));
            aria.setSingers(list);
        }
            
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
            aria.setNotes(list); 
        }           
        return aria;
    }
    
    private Generic getRelationsComposer(Generic obj) throws IOException{        
        Reader reader;  
        Composer composer = (Composer)obj;
        reader = new FileReader(config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_ARIA_COMPOSER));
        CsvToBean<Relation> csvToBean = new CsvToBeanBuilder(reader)
            .withType(Relation.class)
            .withEscapeChar('\\')
            .withQuoteChar('\'')
            .withSeparator(';')
            .build();
        List<Relation> relations = new ArrayList<Relation>();
        relations.addAll(csvToBean.parse());
        reader.close();
        List<Long> list = new ArrayList<Long>();
        if (!relations.isEmpty()){
            list.addAll(relations.stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId1()),
                            (a1, a2) -> a1.addAll(a2)));
            composer.setAries(list);
        }
            
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
            composer.setNotes(list); 
        }     
        return composer;
    }
        
    private Generic getRelationsLibretto(Generic obj) throws IOException{
            Reader reader;  
            Libretto libretto = (Libretto)obj;
            reader = new FileReader(config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_AUTHOR_LIBRETTO));
            CsvToBean<Relation> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Relation.class)
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .build();
            List<Relation> relations = new ArrayList<Relation>();
            relations.addAll(csvToBean.parse());
            reader.close();
            List<Long> list = new ArrayList<Long>();
            if (!relations.isEmpty()){
                list.addAll(relations.stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId1()),
                            (a1, a2) -> a1.addAll(a2)));            
                libretto.setAuthors(list);
            }
            
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
            libretto.setNotes(list); 
        }
        return libretto;
    }    
            
    private Generic getRelationsOpera(Generic obj) throws IOException{        
        Reader reader;  
        Opera opera = (Opera)obj;
        reader = new FileReader(config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_ARIA));
        CsvToBean<Aria> csvToBean = new CsvToBeanBuilder(reader)
            .withType(Aria.class)
            .withEscapeChar('\\')
            .withQuoteChar('\'')
            .withSeparator(';')
            .build();
        List<Aria> aries = new ArrayList<Aria>();
        aries.addAll(csvToBean.parse());
        reader.close();
        List<Long> list = new ArrayList<Long>();
        if (!aries.isEmpty()){
            list.addAll(aries.stream()
                    .filter(r -> r.getOperaId() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId()),
                            (a1, a2) -> a1.addAll(a2)));           
            opera.setAries(list);
        }
            
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
            opera.setNotes(list); 
        }
        return opera;
    }
                
    private Generic getRelationsSinger(Generic obj) throws IOException{
        Reader reader; 
        Singer singer = (Singer)obj;
        reader = new FileReader(config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_ARIA_SINGER));
        CsvToBean<Relation> csvToBean = new CsvToBeanBuilder(reader)
            .withType(Relation.class)
            .withEscapeChar('\\')
            .withQuoteChar('\'')
            .withSeparator(';')
            .build();
        List<Relation> relations = new ArrayList<Relation>();
        relations.addAll(csvToBean.parse());
        reader.close();
        List<Long> list = new ArrayList<Long>();
        if (!relations.isEmpty()){            
            list.addAll(relations.stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId1()),
                            (a1, a2) -> a1.addAll(a2)));            
            singer.setAries(list);
        }
        
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
            singer.setNotes(list); 
        }
        return singer;
    }
                    
    private Generic getRelationsAuthor(Generic obj) throws IOException{
        Reader reader;  
        Author author = (Author)obj;
        reader = new FileReader(config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_ARIA_AUTHOR));
        CsvToBean<Relation> csvToBean = new CsvToBeanBuilder(reader)
            .withType(Relation.class)
            .withEscapeChar('\\')
            .withQuoteChar('\'')
            .withSeparator(';')
            .build();
        List<Relation> relations = csvToBean.parse();
        reader.close();
        List<Long> list = new ArrayList<Long>();   
        if (!relations.isEmpty()){
            list.addAll(relations.stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId1()),
                            (a1, a2) -> a1.addAll(a2)));            
            author.setAries(list);
        }            
            
        reader = new FileReader(config.getConfigurationEntry(CSV_PATH) + config.getConfigurationEntry(CSV_PATH_AUTHOR_LIBRETTO));
        csvToBean = new CsvToBeanBuilder(reader)
            .withType(Relation.class)
            .withEscapeChar('\\')
            .withQuoteChar('\'')
            .withSeparator(';')
            .build();
        relations.clear();
        relations.addAll(csvToBean.parse());
        if (!relations.isEmpty()){
            list.clear();        
            list.addAll(relations.stream()
                    .filter(r -> r.getId1() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId2()),
                            (a1, a2) -> a1.addAll(a2)));
            author.setLibrettos(list); 
        }
        
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
            author.setNotes(list); 
        }
        return author;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Result getAllRecords(Generic obj) {
       try {
            Reader reader;
            reader = new FileReader(getFileName(obj));  
            CsvToBean<Generic> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(getClass(obj))
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .build();         
            List<Generic> list = new ArrayList<Generic>();
            list.addAll(csvToBean.parse());
            reader.close();
            Result result = new Result(ResultStatuses.OK);
            if(!list.isEmpty()){
                list.stream().forEach(g -> {
                    try {
                        g = getRelations(g);
                    } catch (IOException ex) {
                        log.warn(ex);
                        result.setStatus(ResultStatuses.WARNING);
                        result.setMessage(result.getMessage() + " " + ex.getMessage());
                    }
                });
                result.setList(list);
            }
            else{
                result.setStatus(ResultStatuses.NOTFOUND);
                result.setMessage(obj.getType().toString());
            }            
            return result;            
        } catch (IOException ex) {
            log.error(ex);
            Result result = new Result(ResultStatuses.ERROR,ex.getMessage());
            return result;
        } 
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
            return false;                       
        });
        list.removeIf( e ->{
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
            return false;
        });
        list.removeIf( e ->{
            if (((Composer)e).getSurname() != null)
                if (obj.getSurname()!= null && !((Composer)e).getSurname().toLowerCase().contains(obj.getSurname().toLowerCase()))
                    return true;
            return false;
        });
        list.removeIf( e ->{
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
            return false;                       
        });
        list.removeIf( e ->{
            if (((Author)e).getSurname() != null)
                if (obj.getSurname()!= null && !((Author)e).getSurname().toLowerCase().contains(obj.getSurname().toLowerCase()))
                    return true;
            return false;                       
        });
        list.removeIf( e ->{
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
            return false;
        });
        list.removeIf( e ->{
            if (((Singer)e).getSurname() != null)
                if (obj.getSurname()!= null && !((Singer)e).getSurname().toLowerCase().contains(obj.getSurname().toLowerCase()))
                    return true;
            return false;
        });
        list.removeIf( e ->{
            if (((Singer)e).getPatronymic() != null)
                if (obj.getPatronymic()!= null && !((Singer)e).getPatronymic().toLowerCase().contains(obj.getPatronymic().toLowerCase()))
                    return true;
            return false;
        });
        list.removeIf( e ->{
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
            return false;                     
        });
        resultList.addAll(list);
        if (resultList.isEmpty()) return new Result(ResultStatuses.NOTFOUND, obj.toString());
        return new Result(ResultStatuses.OK, list);
    }
}
