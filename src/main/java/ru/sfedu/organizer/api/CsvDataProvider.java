
package ru.sfedu.organizer.api;
import com.opencsv.bean.*;
import com.opencsv.exceptions.*;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static ru.sfedu.organizer.Constants.*;
import ru.sfedu.organizer.model.*;
import static ru.sfedu.organizer.utils.ConfigurationUtil.*;



public class CsvDataProvider implements IDataProvider{
    
    @Override
    public Result addRecord(Generic obj) {        
        try {
            Types type = obj.getType(); 
            Reader reader;
            reader = new FileReader(getFileName(obj));        
            CsvToBean<Generic> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(getClass(obj))
                    .withEscapeChar('\\')
                    .withQuoteChar('\'')
                    .withSeparator(';')
                    .build();         
            List<Generic> list = csvToBean.parse();
            long lastId = list.get(list.size() - 1).getId();            
            reader.close();
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
        } catch (IOException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CsvDataTypeMismatchException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CsvRequiredFieldEmptyException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return 0;
    }

    @Override
    public Result editRecord(Generic obj) {
        
        return 0;
    }

    @Override
    public Result deleteRecord(Generic obj) {
        try {
            Types type = obj.getType(); 
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
            list.removeIf(n -> n.equals(obj));
            StatefulBeanToCsv<Generic> beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withEscapechar('\\')
                    .withQuotechar('\'')
                    .withSeparator(';')
                    .build();
            beanWriter.write(list);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CsvDataTypeMismatchException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CsvRequiredFieldEmptyException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return 0;
        
    }

    @Override
    public Result getRecordById(Generic obj) {
        try {
            Types type = obj.getType(); 
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
            Result result = new Result(list);
            result.setStatus("OK");
            result.setMessage("OK");
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
            case ARIA : file = getConfigurationEntry(CSV_PATH) + "Aria.csv";
                break;
            case COMPOSER : file = getConfigurationEntry(CSV_PATH) + "Composer.csv";
                break;
            case LIBRETTO : file = getConfigurationEntry(CSV_PATH) + "Libretto.csv";
                break;
            case OPERA : file = getConfigurationEntry(CSV_PATH) + "Opera.csv"; 
                break;
            case SINGER : file = getConfigurationEntry(CSV_PATH) + "Singer.csv";
                break;
             case AUTHOR : file = getConfigurationEntry(CSV_PATH) + "Author.csv";
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
        }
        return cl;
    }
    
    private String[] getFields(Generic obj) {
        Types type = obj.getType();
        String[] fields = null;
        switch (type){
            case ARIA : fields = FIELDS_ARIA;
                break;
            case COMPOSER : fields = FIELDS_COMPOSER;
                break;
            case LIBRETTO : fields = FIELDS_LIBRETTO;
                break;
            case OPERA : fields = FIELDS_OPERA; 
                break;
            case SINGER : fields = FIELDS_SINGER;
                break;
             case AUTHOR : fields = FIELDS_AUTHOR;
                break;    
        }
        return fields;
    }

    
    
}

