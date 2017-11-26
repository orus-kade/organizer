
package ru.sfedu.organizer.api;
import com.opencsv.bean.*;
import com.opencsv.exceptions.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//import org.apache.log4j.Priority;
import static ru.sfedu.organizer.Constants.*;
import ru.sfedu.organizer.model.*;
import static ru.sfedu.organizer.model.Types.*;
import static ru.sfedu.organizer.utils.ConfigurationUtil.*;



public class CsvDataProvider implements IDataProvider{
    
    @Override
    public Result addRecord(Note obj) {        
        try {
            Result r = checkNote(obj);
            if (!ResultStatuses.OK.equals(r.getStatus())) return r;
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
            if (list != null){
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
            result.setMessage("Record added");
            return result;
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        }        
    }
        
    private Result checkNote(Generic obj){
        Note note = (Note)obj;
        Result result = new Result();
        if (note == null){
            result.setStatus(ResultStatuses.ERROR);
            result.setMessage("Note: Object is null");
        }
        else{
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
        try {
            Result r = checkNote(obj);
            if (!ResultStatuses.OK.equals(r.getStatus())) return r;
            Reader reader;
            reader = new FileReader(getFileName(obj));        
            CsvToBean<Generic> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(getClass(obj))
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .build();         
            List<Generic> list = csvToBean.parse();
            List<Generic> oldList = new ArrayList<Generic>();
            oldList.addAll(list);
            reader.close();  
            list.removeIf(e -> e.getId() == obj.getId());
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
            result.setMessage("Record edited");
            return result;
        } catch (IOException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        } catch (CsvDataTypeMismatchException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        } catch (CsvRequiredFieldEmptyException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
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
            List<Generic> list = csvToBean.parse();
            List<Generic> oldList = new ArrayList<Generic>();
            oldList.addAll(list);
            reader.close(); 
            if (!list.removeIf(e -> e.getId() == obj.getId())){
                Result result = new Result(ResultStatuses.ERROR, "Object " + obj + " not found");
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
            result.setMessage("Record deleted");
            return result;
        } catch (IOException ex) {
            //Logger.getLogger(CsvDataProvider.class.getName()).log(Priority.DEBUG, null, ex);
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        } catch (CsvDataTypeMismatchException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result(ResultStatuses.ERROR, ex.getMessage());
            return result;
        } catch (CsvRequiredFieldEmptyException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
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
            ColumnPositionMappingStrategy st = new ColumnPositionMappingStrategy();
            CsvToBean<Generic> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(getClass(obj))
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .withFilter(filter)
                    .build();         
            List<Generic> list = csvToBean.parse();
            reader.close();
            if (list.isEmpty()){
                Result result = new Result(ResultStatuses.ERROR, "Can't find object "+obj.getType()+" with id = "+obj.getId());
                return result;
            }
            if (check){
                Result result = new Result(ResultStatuses.OK, "Object exsists", list);
                return result;
            }
            Result result = new Result(ResultStatuses.OK, "Success");
            list.stream().forEach(g -> {
                try {
                    g = getRelations(g);
                } catch (IOException ex) {
                    Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                    result.setStatus(ResultStatuses.WARNING);
                    result.setMessage(result.getMessage() + " " + ex.getMessage());
                }
            });
            result.setList(list);
            return result;            
        } catch (IOException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
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
                    .filter(r -> r.getId1() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId2()),
                            (a1, a2) -> a1.addAll(a2));
            Aria aria = (Aria)obj;
            aria.setAuthors(list);
            
            reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA_COMPOSER));
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
            aria.setComposers(list); 
            
            reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA_SINGER));
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
            aria.setSingers(list);   
            
            return aria;
    }
    
    private Generic getRelationsComposer(Generic obj) throws IOException{        
        Reader reader;  
            reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA_COMPOSER));
            CsvToBean<Relation> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Relation.class)
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .build();
            List<Relation> relations = csvToBean.parse();
            reader.close();
            if (relations.isEmpty()) return obj;
            List<Long> aries = relations.stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId1()),
                            (a1, a2) -> a1.addAll(a2));
            Composer composer = (Composer)obj;
            composer.setAries(aries);
            return composer;
    }
        
    private Generic getRelationsLibretto(Generic obj) throws IOException{
            Reader reader;  
            reader = new FileReader(getConfigurationEntry(CSV_PATH_AUTHOR_LIBRETTO));
            CsvToBean<Relation> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Relation.class)
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .build();
            List<Relation> relations = csvToBean.parse();
            reader.close();
            if (relations.isEmpty()) return obj;
            List<Long> authors = relations.stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId1()),
                            (a1, a2) -> a1.addAll(a2));
            Libretto libretto = (Libretto)obj;
            libretto.setAuthors(authors);
            return libretto;
    }    
            
    private Generic getRelationsOpera(Generic obj) throws IOException{        
        Reader reader;  
            reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA));
            CsvToBean<Aria> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Aria.class)
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .build();
            List<Aria> aries = csvToBean.parse();
            reader.close();
            if (aries.isEmpty()) return obj;
            List<Long> ar = aries.stream()
                    .filter(r -> r.getOperaId() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId()),
                            (a1, a2) -> a1.addAll(a2));
            Opera opera = (Opera)obj;
            opera.setAries(ar);
            return opera;
    }
                
    private Generic getRelationsSinger(Generic obj) throws IOException{
            Reader reader;  
            reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA_SINGER));
            CsvToBean<Relation> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Relation.class)
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .build();
            List<Relation> relations = csvToBean.parse();
            reader.close();
            if (relations.isEmpty()) return obj;
            List<Long> arias = relations.stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Long>::new,
                            (a, r) ->  a.add(r.getId1()),
                            (a1, a2) -> a1.addAll(a2));
            Singer singer = (Singer)obj;
            singer.setAries(arias);
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
                    Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                    result.setStatus(ResultStatuses.WARNING);
                    result.setMessage(result.getMessage() + " " + ex.getMessage());
                }
            });
            result.setList(list);
            return result;            
        } catch (IOException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result(ResultStatuses.ERROR,ex.getMessage());
            return result;
        } 
    }    
}
