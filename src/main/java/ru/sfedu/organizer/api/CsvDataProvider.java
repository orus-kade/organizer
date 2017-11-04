
package ru.sfedu.organizer.api;

import com.opencsv.*;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static ru.sfedu.organizer.Constants.*;
import ru.sfedu.organizer.model.Aria;
import ru.sfedu.organizer.model.Composer;
import ru.sfedu.organizer.model.Generic;
import ru.sfedu.organizer.model.Libretto;
import ru.sfedu.organizer.model.Opera;
import ru.sfedu.organizer.model.Singer;
import ru.sfedu.organizer.model.Types;
import static ru.sfedu.organizer.utils.ConfigurationUtil.*;



public class CsvDataProvider implements IDataProvider{
    
    @Override
    public int addRecord(Generic obj) {        
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
    public int editRecord(Generic obj) {
        
        return 0;
    }

    @Override
    public int deleteRecord(Generic obj) {
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
    public Generic getRecordById(Generic obj) {
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
            if (list.isEmpty()) return null;
            return list.get(0);
        } catch (IOException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }       
       return null;
       
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
             case WRITER : file = getConfigurationEntry(CSV_PATH) + "Writer.csv";
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
             case WRITER : cl = Writer.class;
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
             case WRITER : fields = FIELDS_WRITER;
                break;    
        }
        return fields;
    }

    
    
}

