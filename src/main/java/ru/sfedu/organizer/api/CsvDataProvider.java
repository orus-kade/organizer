
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
            long lastId = list.stream().max(Comparator.comparingLong(e -> e.getId())).get().getId(); 
            obj.setId(lastId+1);
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
            r = editRelations(obj);
            Result result = new Result();
            if (!ResultStatuses.OK.equals(r.getStatus())){
                result.setStatus(ResultStatuses.ERROR);
                result.setMessage(r.getMessage());
                writer = new FileWriter(getFileName(obj));              
                beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withEscapechar('\\')
                    .withQuotechar('\'')
                    .withSeparator(';')
                    .build();
                    beanWriter.write(oldList);
                    writer.close();
            }
            else{
                result.setStatus(ResultStatuses.OK);
                result.setMessage("Record added");
            }
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
        Result result = new Result(ResultStatuses.OK, "");
        if (list != null){            
            list.stream().forEach(e -> 
                    {
                        Result r = getRecordById(e, true);
                        if (!ResultStatuses.OK.equals(r.getStatus())){
                            result.setStatus(ResultStatuses.ERROR);
                            result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                        } 
                    });
        }
        list = ((Aria)obj).getComposers();
        if (list != null){ 
            list.stream().forEach(e -> 
                    {
                        Result r = getRecordById(e, true);
                        if (!ResultStatuses.OK.equals(r.getStatus())){
                            result.setStatus(ResultStatuses.ERROR);
                            result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                        } 
                    }); 
        }
        list = ((Aria)obj).getSingers();
        if (list != null){ 
            list.stream().forEach(e -> 
                    {
                        Result r = getRecordById(e, true);
                        if (!ResultStatuses.OK.equals(r.getStatus())){
                            result.setStatus(ResultStatuses.ERROR);
                            result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                        } 
                    }); 
        }
        return result;         
    }
    
    private Result checkAuthor(Generic obj){
        List<Generic> list = ((Author)obj).getAries();
        Result result = new Result(ResultStatuses.OK, "");
        if (list != null){
            list.stream().forEach(e -> 
                    {
                        Result r = getRecordById(e, true);
                        if (!ResultStatuses.OK.equals(r.getStatus())){
                            result.setStatus(ResultStatuses.ERROR);
                            result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                        } 
                    });
        }
        if (list != null){
        list = ((Author)obj).getLibrettos();
            list.stream().forEach(e -> 
                    {
                        Result r = getRecordById(e, true);
                        if (!ResultStatuses.OK.equals(r.getStatus())){
                            result.setStatus(ResultStatuses.ERROR);
                            result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                        } 
                    });   
        }
        return result; 
    }
    
    private Result checkComposer(Generic obj){
        List<Generic> list = ((Composer)obj).getAries();
        Result result = new Result(ResultStatuses.OK, "");
        if (list != null){
            list.stream().forEach(e -> 
                    {
                        Result r = getRecordById(e, true);
                        if (!"OK".equals(r.getStatus())){
                            result.setStatus(ResultStatuses.ERROR);
                            result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                        } 
                    });
        }
        return result;        
    }
    
    private Result checkLibretto(Generic obj){
        List<Generic> list = ((Libretto)obj).getAuthors();
        Result result = new Result(ResultStatuses.OK, "");
        if (list != null){
            list.stream().forEach(e -> 
                    {
                        Result r = getRecordById(e, true);
                        if (!ResultStatuses.OK.equals(r.getStatus())){
                            result.setStatus(ResultStatuses.ERROR);
                            result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                        } 
                    });
        }
        return result;        
    }
    
    private Result checkOpera(Generic obj){
       List<Generic> list = ((Opera)obj).getAries();
        Result result = new Result(ResultStatuses.OK, "");
        if (list != null){
            list.stream().forEach(e -> 
                    {
                        Result r = getRecordById(e, true);
                        if (!ResultStatuses.OK.equals(r.getStatus())){
                            result.setStatus(ResultStatuses.ERROR);
                            result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                        } 
                    });
        }
        return result;  
    }
    
    private Result checkSinger(Generic obj){        
        List<Generic> list = ((Singer)obj).getAries();
        Result result = new Result(ResultStatuses.OK, "");
        if (list != null){
            list.stream().forEach(e -> 
                    {
                        Result r = getRecordById(e, true);
                        if (!ResultStatuses.OK.equals(r.getStatus())){
                            result.setStatus(ResultStatuses.ERROR);
                            result.setMessage(result.getMessage() + " " + e + " doesn't exsist!\n");
                        } 
                    });
        }
        return result;
    }
    
    private Result checkNote(Generic obj){
        Note note = (Note)obj;
        Result result = new Result();
        if (note == null){
            result.setStatus(ResultStatuses.ERROR);
            result.setMessage("Note: Object is null");
        }
        else{
            result = getRecordById(note.getObject(), true);
        }       
        return result;
    }
    
    private Result editRelations(Generic obj) throws IOException{
        Types type = obj.getType(); 
        Result result = new Result();
        switch (type){
           case ARIA : result = editRelationsAria(obj, false);
                break;
           case COMPOSER : result = editRelationsComposer(obj, false);
                break;
           case LIBRETTO : result = editRelationsLibretto(obj, false);
                break;
           case OPERA : //result = editRelationsOpera(obj); 
                break;
           case SINGER : result = editRelationsSinger(obj, false);
                break;
           case AUTHOR : result = editRelationsAuthor(obj, false);
                break;
           case NOTE: break;    
        }
        return result;
    }
    
    private Result editRelationsAria(Generic obj, boolean delete) throws IOException{
        List<Generic> list = ((Aria)obj).getAuthors();
        Reader reader; 
        Writer writer;
        Result result = new Result(ResultStatuses.OK, "");
        if (list == null) result.setMessage(result.getMessage() + " There are no relations to authors");
        else{
            reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA_AUTHOR));
            CsvToBean<Relation> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Relation.class)
                        .withEscapeChar('\\')
                        .withQuoteChar('\'')
                        .withSeparator(';')
                        .build();
            List<Relation> relations = csvToBean.parse();
            reader.close();      
            relations.removeIf(r -> r.getId1() == obj.getId());
            if (!delete){
                List<Relation> newR = list.stream().collect(ArrayList<Relation>::new,
                                                                 (a, r) -> a.add(new Relation(obj.getId(), r.getId())),
                                                                 (a1, a2) -> a1.addAll(a2));
                relations.addAll(newR);
            }
            writer = new FileWriter(getConfigurationEntry(CSV_PATH_ARIA_AUTHOR));
            StatefulBeanToCsv<Relation> beanWriter = new StatefulBeanToCsvBuilder(writer)
                        .withEscapechar('\\')
                        .withQuotechar('\'')
                        .withSeparator(';')
                        .build();
            try {
                beanWriter.write(relations);
                result.setMessage("Relations to authors edited");
            } catch (CsvDataTypeMismatchException ex) {
                Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                result.setStatus(ResultStatuses.ERROR);
                result.setMessage(result.getMessage() + " " +  ex.getMessage());
            } catch (CsvRequiredFieldEmptyException ex) {
                Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                result.setStatus(ResultStatuses.ERROR);
                result.setMessage(result.getMessage() + " " +  ex.getMessage());
            }
            writer.close();            
        }
        
        list = ((Aria)obj).getComposers();
        if (list == null){
            result.setMessage(result.getMessage() + " There are no relations to composers");
        }
        else{        
        reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA_COMPOSER));
            CsvToBean<Relation> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Relation.class)
                        .withEscapeChar('\\')
                        .withQuoteChar('\'')
                        .withSeparator(';')
                        .build();
            List<Relation> relations = csvToBean.parse();
            reader.close();      
            relations.removeIf(r -> r.getId1() == obj.getId());
            if (!delete){
                List<Relation> newR = list.stream().collect(ArrayList<Relation>::new,
                                                             (a, r) -> a.add(new Relation(obj.getId(), r.getId())),
                                                             (a1, a2) -> a1.addAll(a2));
                relations.addAll(newR);
            }
            writer = new FileWriter(getConfigurationEntry(CSV_PATH_ARIA_COMPOSER));
            StatefulBeanToCsv<Relation> beanWriter = new StatefulBeanToCsvBuilder(writer)
                        .withEscapechar('\\')
                        .withQuotechar('\'')
                        .withSeparator(';')
                        .build();
            try {
                beanWriter.write(relations);
                result.setMessage("Relations to composers edited");
            } catch (CsvDataTypeMismatchException ex) {
                Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                result.setStatus(ResultStatuses.ERROR);
                result.setMessage(result.getMessage() + " " +  ex.getMessage());
            } catch (CsvRequiredFieldEmptyException ex) {
                Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                result.setStatus(ResultStatuses.ERROR);
                result.setMessage(result.getMessage() + " " +  ex.getMessage());
            }
            writer.close(); 
        }
        list = ((Aria)obj).getSingers();
        if (list == null){
            result.setMessage(result.getMessage() + " There are no relations to singers");
            return result;
        }    
        reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA_SINGER));
            CsvToBean<Relation> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Relation.class)
                        .withEscapeChar('\\')
                        .withQuoteChar('\'')
                        .withSeparator(';')
                        .build();
            List<Relation> relations = csvToBean.parse();
            reader.close();      
            relations.removeIf(r -> r.getId1() == obj.getId());
            if (!delete){
                List<Relation> newR = list.stream().collect(ArrayList<Relation>::new,
                                                             (a, r) -> a.add(new Relation(obj.getId(), r.getId())),
                                                             (a1, a2) -> a1.addAll(a2));
                relations.addAll(newR);
            }
            writer = new FileWriter(getConfigurationEntry(CSV_PATH_ARIA_SINGER));
            StatefulBeanToCsv<Relation> beanWriter = new StatefulBeanToCsvBuilder(writer)
                        .withEscapechar('\\')
                        .withQuotechar('\'')
                        .withSeparator(';')
                        .build();
            try {
                beanWriter.write(relations);
                result.setMessage("Relations to singers edited");
            } catch (CsvDataTypeMismatchException ex) {
                Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                result.setStatus(ResultStatuses.ERROR);
                result.setMessage(result.getMessage() + " " +  ex.getMessage());
            } catch (CsvRequiredFieldEmptyException ex) {
                Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                result.setStatus(ResultStatuses.ERROR);
                result.setMessage(result.getMessage() + " " +  ex.getMessage());
            }
            writer.close(); 
        return result;       
    }
    
    private Result editRelationsAuthor(Generic obj, boolean delete) throws IOException{
        List<Generic> list = ((Author)obj).getAries();
        Reader reader; 
        Writer writer;
        Result result = new Result(ResultStatuses.OK, "");
        if (list == null) result.setMessage(result.getMessage() + " There are no relations to Aries");
        else{
            reader = new FileReader(getConfigurationEntry(CSV_PATH_ARIA_AUTHOR));
            CsvToBean<Relation> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Relation.class)
                        .withEscapeChar('\\')
                        .withQuoteChar('\'')
                        .withSeparator(';')
                        .build();
            List<Relation> relations = csvToBean.parse();
            reader.close();      
            relations.removeIf(r -> r.getId2() == obj.getId());
            if (!delete){
                List<Relation> newR = list.stream().collect(ArrayList<Relation>::new,
                                                             (a, r) -> a.add(new Relation(r.getId(), obj.getId())),
                                                             (a1, a2) -> a1.addAll(a2));
                relations.addAll(newR);
            }
            writer = new FileWriter(getConfigurationEntry(CSV_PATH_ARIA_AUTHOR));
            StatefulBeanToCsv<Relation> beanWriter = new StatefulBeanToCsvBuilder(writer)
                        .withEscapechar('\\')
                        .withQuotechar('\'')
                        .withSeparator(';')
                        .build();
            try {
                beanWriter.write(relations);
                result.setMessage("Relations to aries edited");
            } catch (CsvDataTypeMismatchException ex) {
                Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                result.setStatus(ResultStatuses.ERROR);
                result.setMessage(result.getMessage() + " " +  ex.getMessage());
            } catch (CsvRequiredFieldEmptyException ex) {
                Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                result.setStatus(ResultStatuses.ERROR);
                result.setMessage(result.getMessage() + " " +  ex.getMessage());
            }
            writer.close();
            
        }
        
        list = ((Author)obj).getLibrettos();
        if (list == null){
            result.setMessage(result.getMessage() + " There are no relations to Librettos");
            return result;
        }
        reader = new FileReader(getConfigurationEntry(CSV_PATH_AUTHOR_LIBRETTO));
            CsvToBean<Relation> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Relation.class)
                        .withEscapeChar('\\')
                        .withQuoteChar('\'')
                        .withSeparator(';')
                        .build();
            List<Relation> relations = csvToBean.parse();
            reader.close();      
            relations.removeIf(r -> r.getId1() == obj.getId());
            if (!delete){
                List<Relation> newR = list.stream().collect(ArrayList<Relation>::new,
                                                             (a, r) -> a.add(new Relation(obj.getId(), r.getId())),
                                                             (a1, a2) -> a1.addAll(a2));
                relations.addAll(newR);
            }
            writer = new FileWriter(getConfigurationEntry(CSV_PATH_AUTHOR_LIBRETTO));
            StatefulBeanToCsv<Relation> beanWriter = new StatefulBeanToCsvBuilder(writer)
                        .withEscapechar('\\')
                        .withQuotechar('\'')
                        .withSeparator(';')
                        .build();
            try {
                beanWriter.write(relations);
                result.setMessage("Relations to librettos edited");
            } catch (CsvDataTypeMismatchException ex) {
                Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                result.setStatus(ResultStatuses.ERROR);
                result.setMessage(result.getMessage() + " " +  ex.getMessage());
            } catch (CsvRequiredFieldEmptyException ex) {
                Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                result.setStatus(ResultStatuses.ERROR);
                result.setMessage(result.getMessage() + " " +  ex.getMessage());
            }
            writer.close(); 
        return result;
    }
    
    private Result editRelationsComposer(Generic obj, boolean delete) throws IOException {
        List<Generic> list = ((Composer)obj).getAries();
        if (list == null) return new Result(ResultStatuses.OK, "There are no relations");
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
        relations.removeIf(r -> r.getId2() == obj.getId());
        if (!delete){
            List<Relation> newR = list.stream().collect(ArrayList<Relation>::new,
                                                         (a, r) -> a.add(new Relation(r.getId(), obj.getId())),
                                                         (a1, a2) -> a1.addAll(a2));
            relations.addAll(newR);
        }
        Writer writer = new FileWriter(getConfigurationEntry(CSV_PATH_ARIA_COMPOSER));
        StatefulBeanToCsv<Relation> beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withEscapechar('\\')
                    .withQuotechar('\'')
                    .withSeparator(';')
                    .build();
        try {
            beanWriter.write(relations);
        } catch (CsvDataTypeMismatchException ex) {
            writer.close();
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        } catch (CsvRequiredFieldEmptyException ex) {
            writer.close();
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }
        writer.close();
        Result result = new Result(ResultStatuses.OK, "Relations edited");
        return result;
    }
    
    private Result editRelationsLibretto(Generic obj, boolean delete) throws IOException{
        List<Generic> list = ((Libretto)obj).getAuthors();
        if (list == null) return new Result(ResultStatuses.OK, "There are no relations");
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
        relations.removeIf(r -> r.getId2() == obj.getId());
        if (!delete){
            List<Relation> newR = list.stream().collect(ArrayList<Relation>::new,
                                                         (a, r) -> a.add(new Relation(r.getId(), obj.getId())),
                                                         (a1, a2) -> a1.addAll(a2));
            relations.addAll(newR);
        }
        Writer writer = new FileWriter(getConfigurationEntry(CSV_PATH_AUTHOR_LIBRETTO));
        StatefulBeanToCsv<Relation> beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withEscapechar('\\')
                    .withQuotechar('\'')
                    .withSeparator(';')
                    .build();
        try {
            beanWriter.write(relations);
        } catch (CsvDataTypeMismatchException ex) {
            writer.close();
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        } catch (CsvRequiredFieldEmptyException ex) {
            writer.close();
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }
        writer.close();
        return new Result(ResultStatuses.OK, "Relations edited");
    }
        
    private Result editRelationsSinger(Generic obj, boolean delete) throws IOException{
        List<Generic> list = ((Singer)obj).getAries();
        if (list == null) return new Result(ResultStatuses.OK, "There are no relations");
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
        relations.removeIf(r -> r.getId2() == obj.getId());
        if (!delete){
            List<Relation> newR = list.stream().collect(ArrayList<Relation>::new,
                                                         (a, r) -> a.add(new Relation(r.getId(), obj.getId())),
                                                         (a1, a2) -> a1.addAll(a2));
            relations.addAll(newR);
        }
        Writer writer = new FileWriter(getConfigurationEntry(CSV_PATH_ARIA_SINGER));
        StatefulBeanToCsv<Relation> beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withEscapechar('\\')
                    .withQuotechar('\'')
                    .withSeparator(';')
                    .build();
        try {
            beanWriter.write(relations);
        } catch (CsvDataTypeMismatchException ex) {
            writer.close();
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        } catch (CsvRequiredFieldEmptyException ex) {
            writer.close();
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }
        writer.close();
        return new Result(ResultStatuses.OK, "Relations edited");
    }
    

    @Override
    public Result editRecord(Generic obj) {
        try {
            Result r = checkObject(obj);
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
            r = editRelations(obj);
            Result result = new Result();
            if (!ResultStatuses.OK.equals(r.getStatus())){
                result.setStatus(ResultStatuses.ERROR);
                result.setMessage(r.getMessage());
                writer = new FileWriter(getFileName(obj));             
                beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withEscapechar('\\')
                    .withQuotechar('\'')
                    .withSeparator(';')
                    .build();
                beanWriter.write(oldList);
                writer.close();
            }
            else{
                result.setStatus(ResultStatuses.OK);
                result.setMessage("Record edited");
            }
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
            Result r = deleteRelations(obj);
            Result result = new Result();
            if (!ResultStatuses.OK.equals(r.getStatus())){
                result.setStatus(ResultStatuses.ERROR);
                result.setMessage(r.getMessage());
                writer = new FileWriter(getFileName(obj)); 
                beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withEscapechar('\\')
                    .withQuotechar('\'')
                    .withSeparator(';')
                    .build();
                beanWriter.write(oldList);
                writer.close();
            }
            else{
                result.setStatus(ResultStatuses.OK);
                result.setMessage("Record deleted");
            }
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

    private Result deleteRelations(Generic obj) throws IOException{
        Types type = obj.getType(); 
        Result result = new Result();
        switch (type){
           case ARIA : result = editRelationsAria(obj, true);
                break;
           case COMPOSER : result = editRelationsComposer(obj, true);
                break;
           case LIBRETTO : result = editRelationsLibretto(obj, true);
                break;
           case OPERA : result = deleteRelationsOpera(obj); 
                break;
           case SINGER : result = editRelationsSinger(obj, true);
                break;
           case AUTHOR : result = editRelationsAuthor(obj, true);
                break;
           case NOTE: break;    
        }
        return result;
    }
    
    public Result deleteRelationsOpera(Generic obj) throws IOException{
        List<Generic> list = ((Opera)obj).getAries();
        if (list.isEmpty()){
            return new Result (ResultStatuses.OK, "There are no relations to aries");
        }
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
        aries.removeIf(a -> list.contains(new Aria(a.getId())));
        Writer writer;
        writer = new FileWriter(getConfigurationEntry(CSV_PATH_ARIA));  
        StatefulBeanToCsv<Aria> beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withEscapechar('\\')
                    .withQuotechar('\'')
                    .withSeparator(';')
                    .build();
        try {
            beanWriter.write(aries);
        } catch (CsvDataTypeMismatchException ex) {
            writer.close();
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        } catch (CsvRequiredFieldEmptyException ex) {
            writer.close();
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }
        writer.close();       
        return new Result(ResultStatuses.OK, "Aries deleted");
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
            List<Generic> list = relations.stream()
                    .filter(r -> r.getId1() == obj.getId())
                    .collect(ArrayList<Generic>::new,
                            (a, r) ->  a.add(new Author(r.getId2())),
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
            List<Generic> l = relations.stream()
                    .filter(r -> r.getId1() == obj.getId())
                    .collect(ArrayList<Generic>::new,
                            (a, r) ->  a.add(new Composer(r.getId2())),
                            (a1, a2) -> a1.addAll(a2));
            aria.setComposers(l); 
            
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
                            (a, r) ->  a.add(new Singer(r.getId2())),
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
                            (a, r) ->  a.add(new Aria(r.getId1())),
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
                            (a, r) ->  a.add(new Author(r.getId1())),
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
                            (a, r) ->  a.add(new Aria(r.getId())),
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
                            (a, r) ->  a.add(new Aria(r.getId1())),
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
                            (a, r) ->  a.add(new Aria(r.getId1())),
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
                            (a, r) ->  a.add(new Libretto(r.getId2())),
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
