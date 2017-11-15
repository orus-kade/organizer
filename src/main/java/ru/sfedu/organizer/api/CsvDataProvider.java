
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
    public Result addRecord(Generic obj) {        
        try {
            Result r = checkObject(obj);
            if (!"OK".equals(r.getStatus())) return r;
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
            long lastId = list.stream().max(Comparator.comparingLong(e -> e.getId())).get().getId();            
            java.io.Writer writer;
            writer = new FileWriter(getFileName(obj));  
            obj.setId(lastId+1);
            list.add(obj);
            StatefulBeanToCsv<Generic> beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withEscapechar('\\')
                    .withQuotechar('\'')
                    .withSeparator(';')
                    .build();
            beanWriter.write(list);
            writer.close();
            addRelations(objs);
            Result result = new Result("OK", "Record added");
            return result;
        } catch (IOException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result("Error", ex.getMessage());
            return result;
        } catch (CsvDataTypeMismatchException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result("Error", ex.getMessage());
            return result;
        } catch (CsvRequiredFieldEmptyException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result("Error", ex.getMessage());
            return result;
        }         
    }
    
    private Result checkObject(Generic obj){
        Types type = obj.getType(); 
        Result result = new Result();
        switch (type){
           case ARIA : result = checkAria(obj);
                break;
           case COMPOSER : result = checkComposer(obj);
                break;
           case LIBRETTO : result = checkLibretto(obj);
                break;
           case OPERA : result = checkOpera(obj); 
                break;
           case SINGER : result = checkSinger(obj);
                break;
           case AUTHOR : result = checkAuthor(obj);
                break;
           case NOTE: result = checkNote(obj);
                break;    
        }
        return result;
    }
    
    private Result checkAria(Generic obj){
        List<Generic> list = ((Aria)obj).getAuthors();
        Result result = new Result("OK", "");
        list.stream().forEach(e -> 
                {
                    Result r = getRecordById(e, true);
                    if (!"OK".equals(r.getStatus())){
                        result.setStatus("Error");
                        result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                    } 
                });
        
        list = ((Aria)obj).getComposers();
        list.stream().forEach(e -> 
                {
                    Result r = getRecordById(e, true);
                    if (!"OK".equals(r.getStatus())){
                        result.setStatus("Error");
                        result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                    } 
                }); 
        list = ((Aria)obj).getSingers();
        list.stream().forEach(e -> 
                {
                    Result r = getRecordById(e, true);
                    if (!"OK".equals(r.getStatus())){
                        result.setStatus("Error");
                        result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                    } 
                }); 
        return result;         
    }
    
    private Result checkAuthor(Generic obj){
        List<Generic> list = ((Author)obj).getAries();
        Result result = new Result("OK", "");
        list.stream().forEach(e -> 
                {
                    Result r = getRecordById(e, true);
                    if (!"OK".equals(r.getStatus())){
                        result.setStatus("Error");
                        result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                    } 
                });
        
        list = ((Author)obj).getLibrettos();
        list.stream().forEach(e -> 
                {
                    Result r = getRecordById(e, true);
                    if (!"OK".equals(r.getStatus())){
                        result.setStatus("Error");
                        result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                    } 
                });        
        return result; 
    }
    
    private Result checkComposer(Generic obj){
        List<Generic> list = ((Composer)obj).getAries();
        Result result = new Result("OK", "");
        list.stream().forEach(e -> 
                {
                    Result r = getRecordById(e, true);
                    if (!"OK".equals(r.getStatus())){
                        result.setStatus("Error");
                        result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                    } 
                });
        return result;        
    }
    
    private Result checkLibretto(Generic obj){
        List<Generic> list = ((Libretto)obj).getAuthors();
        Result result = new Result("OK", "");
        list.stream().forEach(e -> 
                {
                    Result r = getRecordById(e, true);
                    if (!"OK".equals(r.getStatus())){
                        result.setStatus("Error");
                        result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                    } 
                });
        return result;        
    }
    
    private Result checkOpera(Generic obj){
       List<Generic> list = ((Opera)obj).getAries();
        Result result = new Result("OK", "");
        list.stream().forEach(e -> 
                {
                    Result r = getRecordById(e, true);
                    if (!"OK".equals(r.getStatus())){
                        result.setStatus("Error");
                        result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                    } 
                });
        return result;  
    }
    
    private Result checkSinger(Generic obj){        
        List<Generic> list = ((Singer)obj).getAries();
        Result result = new Result("OK", "");
        list.stream().forEach(e -> 
                {
                    Result r = getRecordById(e, true);
                    if (!"OK".equals(r.getStatus())){
                        result.setStatus("Error");
                        result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                    } 
                });
        return result;
    }
    
    private Result checkNote(Generic obj){
        Note note = (Note)obj;
        Result result = new Result();
        if (note == null){
            result.setStatus("Error");
            result.setMessage("Note: Object is null");
        }
        else{
            result = getRecordById(note.getObject(), true);
        }       
        return result;
    }
    
    private Result addRelations(Generic obj) throws IOException{
        Types type = obj.getType(); 
        Result result = new Result();
        switch (type){
           case ARIA : result = addRelationsAria(obj);
                break;
           case COMPOSER : result = addRelationsComposer(obj);
                break;
           case LIBRETTO : result = addRelationsLibretto(obj);
                break;
           case OPERA : result = addRelationsOpera(obj); 
                break;
           case SINGER : result = addRelationsSinger(obj);
                break;
           case AUTHOR : result = addRelationsAuthor(obj);
                break;
           case NOTE: break;    
        }
        return result;
    }
    
    private Result addRelationsAria(Generic obj){
                
    }
    
    private Result addRelationsAuthor(Generic obj){
        
    }
    
    private Result addRelationsComposer(Generic obj){
        
    }
    
    private Result addRelationsLibretto(Generic obj){
        
    }
    
    private Result addRelationsOpera(Generic obj){
        
    }
    
    private Result addRelationsSinger(Generic obj) throws IOException{
        Singer object = (Singer)obj;
        List<Generic> list = object.getAries();
        if (list.isEmpty()) return new Result("OK", "There are no relations");
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
            List<Relation> newR = list.stream().collect(ArrayList<Relation>::new,
                                                         (a, r) -> a.add(new Relation(r.getId(), obj.getId())),
                                                         (a1, a2) -> a1.addAll(a2));
            Singer singer = (Singer)obj;
            singer.setAries(arias);
            return null;
    }
    

    @Override
    public Result editRecord(Generic obj) {
        try {
            Result r = checkObject(obj);
            if (!"OK".equals(r.getStatus())) return r;
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
            java.io.Writer writer;
            writer = new FileWriter(getFileName(obj)); 
            list.removeIf(e -> e.getId() == obj.getId());
            list.add(obj);
            StatefulBeanToCsv<Generic> beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withEscapechar('\\')
                    .withQuotechar('\'')
                    .withSeparator(';')
                    .build();
            beanWriter.write(list);
            writer.close();
            Result result = new Result("OK", "Record edited");
            return result;
        } catch (IOException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result("Error", ex.getMessage());
            return result;
        } catch (CsvDataTypeMismatchException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result("Error", ex.getMessage());
            return result;
        } catch (CsvRequiredFieldEmptyException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result("Error", ex.getMessage());
            return result;
        }  
    }

    @Override
    public Result deleteRecord(Generic obj) {
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
            if (!list.removeIf(e -> e.getId() == obj.getId())){
                Result result = new Result("Error", "Object " + obj + " not found");
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
            Result result = new Result("OK", "Record edited");
            return result;
        } catch (IOException ex) {
            //Logger.getLogger(CsvDataProvider.class.getName()).log(Priority.DEBUG, null, ex);
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result("Error", ex.getMessage());
            return result;
        } catch (CsvDataTypeMismatchException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result("Error", ex.getMessage());
            return result;
        } catch (CsvRequiredFieldEmptyException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result("Error", ex.getMessage());
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
            List<Generic> list = csvToBean.parse();
            reader.close();
            if (list.isEmpty()){
                Result result = new Result("Error", "Can't find object "+obj.getType()+" with id = "+obj.getId());
                return result;
            }
            if (check){
                Result result = new Result("OK", "Object exsists", list);
                return result;
            }
            Result result = new Result("OK", "Success");
            list.stream().forEach(g -> {
                try {
                    g = getRelations(g);
                } catch (IOException ex) {
                    Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                    result.setStatus("Warning");
                    result.setMessage(result.getMessage() + " " + ex.getMessage());
                }
            });
            result.setList(list);
            return result;            
        } catch (IOException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            Result result = new Result("exseption",ex.getMessage());
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
            List<Generic> list = relations.stream()
                    .filter(r -> r.getId1() == obj.getId())
                    .collect(ArrayList<Generic>::new,
                            (a, r) ->  a.add(new Generic(r.getId2(), AUTHOR)),
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
                    .collect(ArrayList<Generic>::new,
                            (a, r) ->  a.add(new Generic(r.getId2(), COMPOSER)),
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
                    .collect(ArrayList<Generic>::new,
                            (a, r) ->  a.add(new Generic(r.getId2(), SINGER)),
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
            List<Generic> aries = relations.stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Generic>::new,
                            (a, r) ->  a.add(new Generic(r.getId1(), ARIA)),
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
            List<Generic> authors = relations.stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Generic>::new,
                            (a, r) ->  a.add(new Generic(r.getId1(), AUTHOR)),
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
            List<Generic> ar = aries.stream()
                    .filter(r -> r.getOpera().getId() == obj.getId())
                    .collect(ArrayList<Generic>::new,
                            (a, r) ->  a.add(new Generic(r.getId(), ARIA)),
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
            List<Generic> arias = relations.stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Generic>::new,
                            (a, r) ->  a.add(new Generic(r.getId1(), ARIA)),
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
            List<Generic> list = relations.stream()
                    .filter(r -> r.getId2() == obj.getId())
                    .collect(ArrayList<Generic>::new,
                            (a, r) ->  a.add(new Generic(r.getId1(), ARIA)),
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
                    .collect(ArrayList<Generic>::new,
                            (a, r) ->  a.add(new Generic(r.getId2(), LIBRETTO)),
                            (a1, a2) -> a1.addAll(a2));
            author.setLibrettos(list);            
            return author;
    }
}

    

