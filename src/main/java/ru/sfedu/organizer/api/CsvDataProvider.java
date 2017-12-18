
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
import static ru.sfedu.organizer.utils.ConfigurationUtil.*;



public class CsvDataProvider implements IDataProvider{
    private static final Logger log = Logger.getLogger(CsvDataProvider.class);
    
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
            if (!list.isEmpty()){
                long lastId = list.stream().max(Comparator.comparingLong(e -> e.getId())).get().getId(); 
                obj.setId(lastId+1); 
            }
            else 
                obj.setId(1);
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
            log.error(ex.getMessage());
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
        }
        else{
            Note note = (Note)obj;
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
            log.error(ex.getMessage());
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        } 
    }

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
            list.adaAll(csvToBean.parse());
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
            log.error(ex.getMessage()); 
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        }        
    }
    
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
                    log.warn(ex.getMessage());
                    result.setStatus(ResultStatuses.WARNING);
                    result.setMessage(result.getMessage() + " " + ex.getMessage());
                }
            });
            result.setList(list);
            return result;            
        } catch (IOException ex) {
            log.error(ex.getMessage());
            Result result = new Result(ResultStatuses.ERROR,ex.getMessage());
            return result;
        }
    }
    
    private String getFileName(Generic obj) throws IOException {
        Types type = obj.getType();
        String file = null;
        switch (type){
            case ARIA : file = getConfigurationEntry(CSV_PATH_ARIA);
                break;
            case COMPOSER : file = getConfigurationEntry(CSV_PATH_COMPOSER);
                break;
            case LIBRETTO : file = getConfigurationEntry(CSV_PATH_LIBRETTO);
                break;
            case OPERA : file = getConfigurationEntry(CSV_PATH_OPERA); 
                break;
            case SINGER : file = getConfigurationEntry(CSV_PATH_SINGER);
                break;
            case AUTHOR : file = getConfigurationEntry(CSV_PATH_AUTHOR);
                break; 
            case NOTE: file = getConfigurationEntry(CSV_PATH_NOTE);
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
        reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA_AUTHOR));
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
          
        reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA_COMPOSER));
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
            
        reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA_SINGER));
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
        reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA_COMPOSER));
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
            reader = new FileReader(getConfigurationEntry(CSV_PATH_AUTHOR_LIBRETTO));
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
        reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA));
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
        reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA_SINGER));
        CsvToBean<Relation> csvToBean = new CsvToBeanBuilder(reader)
            .withType(Relation.class)
            .withEscapeChar('\\')
            .withQuoteChar('\'')
            .withSeparator(';')
            .build();
        List<Relation> relations = new ArrayList<Relation>();
                csvToBean.parse();
            reader.close();
            if (relations.isEmpty()) return obj;
            List<Long> list = relations.stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId1()),
                            (a1, a2) -> a1.addAll(a2));
            
            singer.setAries(list);
            Optional<List<Generic>> notes = Optional.ofNullable(getAllRecords(new Note()).getList());
            if(notes.isPresent()){
                list = notes.get().stream()
                        .filter(e -> (obj.getId() == ((Note)e).getObjectId() && ((Note)e).getObjectType().equals(obj.getType().toString())))
                        .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId()),
                            (a1, a2) -> a1.addAll(a2));
                singer.setNotes(list); 
            }
            return singer;
    }
                    
    private Generic getRelationsAuthor(Generic obj) throws IOException{
        Reader reader;  
            reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA_AUTHOR));
            CsvToBean<Relation> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Relation.class)
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .build();
            List<Relation> relations = csvToBean.parse();
            reader.close();
            List<Long> list = relations.stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId1()),
                            (a1, a2) -> a1.addAll(a2));
            Author author = (Author)obj;
            author.setAries(list);
            
            reader = new FileReader(getConfigurationEntry(CSV_PATH_AUTHOR_LIBRETTO));
            csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Relation.class)
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .build();
            relations = csvToBean.parse();
            list = relations.stream()
                    .filter(r -> r.getId1() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId2()),
                            (a1, a2) -> a1.addAll(a2));
            author.setLibrettos(list); 
            Optional<List<Generic>> notes = Optional.ofNullable(getAllRecords(new Note()).getList());
            if(notes.isPresent()){
                list = notes.get().stream()
                        .filter(e -> (obj.getId() == ((Note)e).getObjectId() && ((Note)e).getObjectType().equals(obj.getType().toString())))
                        .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId()),
                            (a1, a2) -> a1.addAll(a2));
                author.setNotes(list); 
            }
            return author;
    }

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
            List<Generic> list = csvToBean.parse();
            reader.close();
            Result result = new Result(ResultStatuses.OK, "Success");
            list.stream().forEach(g -> {
                try {
                    g = getRelations(g);
                } catch (IOException ex) {
                    log.warn(ex.getMessage());
                    result.setStatus(ResultStatuses.WARNING);
                    result.setMessage(result.getMessage() + " " + ex.getMessage());
                }
            });
            result.setList(list);
            return result;            
        } catch (IOException ex) {
            log.error(ex.getMessage());
            Result result = new Result(ResultStatuses.ERROR,ex.getMessage());
            return result;
        } 
    }

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
                case LIBRETTO : result = findLibretto((Libretto)obj);
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
            return result;
        }
    }
    
    private Result findAria(Aria obj){
        Result result = getAllRecords(obj);
        List<Generic> list = new ArrayList();
        if (!result.getStatus().equals(ResultStatuses.OK)){
            return new Result(result.getStatus(), result.getMessage());
        }
        Optional<List<Generic>> list = Optional.ofNullable(result.getList());
        if (!list.isPresent()) return new Result(ResultStatuses.NOTFOUND);
        List<Generic> resultList = new ArrayList<Generic>();
        resultList.addAll(list.get().removeIf( e ->{
            
        }));
    }
}
