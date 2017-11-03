
package ru.sfedu.organizer.api;

import com.opencsv.*;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.StatefulBeanToCsv;
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
    public int saveRecord(Generic obj) {
        Types type = obj.getType();
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(getFileName(obj)));
            
            StatefulBeanToCsv
            
            
        } catch (IOException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
        return 0;      
    }

    @Override
    public int deleteRecord(Generic obj) {
        Types type = obj.getType();
        try {
            CSVReader reader = new CSVReader(new FileReader(getFileName(obj)), ';');
            
            reader.close
            
            CSVWriter writer  new CSVWriter(new FileReader(getFileName(obj)), ';');
            
            
        } catch (IOException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        return 0;
        
    }

    @Override
    public Generic getRecordById(Generic obj) {
        try {
            Types type = obj.getType();
            CSVReader reader = new CSVReader(new FileReader(getFileName(obj)), ';');
            ColumnPositionMappingStrategy<Generic> beanStrategy = new ColumnPositionMappingStrategy<Generic>();           
            beanStrategy.setType(getClass(obj));
            //beanStrategy.setColumnMapping(getFields(obj));
            CsvFilter filter = new CsvFilter(beanStrategy, obj.getId());
            CsvToBean<Generic> csvToBean = new CsvToBean<Generic>();
            List<Generic> list = csvToBean.parse(beanStrategy, reader, filter);
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

