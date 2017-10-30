
package ru.sfedu.organizer.api;

import com.opencsv.*;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.sfedu.organizer.Constants;
import static ru.sfedu.organizer.Constants.*;
import ru.sfedu.organizer.model.Aria;
import ru.sfedu.organizer.model.Generic;
import ru.sfedu.organizer.model.Types;
import ru.sfedu.organizer.utils.ConfigurationUtil;
import static ru.sfedu.organizer.utils.ConfigurationUtil.*;



public class CsvDataProvider implements IDataProvider{
    
    @Override
    public int saveRecord(Generic obj) {
        Types type = obj.getType();
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(getFileName(obj)));
        } catch (IOException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return 0;      
    }

    @Override
    public int deleteRecord(Generic obj) {
        return 0;
        
    }

    @Override
    public Generic getRecordById(Generic obj) {
        try {
            Types type = obj.getType();
            CSVReader reader = new CSVReader(new FileReader(getFileName(obj)));
            ColumnPositionMappingStrategy<Generic> beanStrategy = new ColumnPositionMappingStrategy<Generic>();            
            beanStrategy.setType(type.getClassName());
            beanStrategy.setColumnMapping(type.getFields());
            CsvFilter filter = new CsvFilter(beanStrategy, obj.getId());
            CsvToBean<Generic> csvToBean = new CsvToBean<Generic>();
            List<Generic> list = csvToBean.parse(beanStrategy, reader, filter);
            if (list.isEmpty()) return null;
            return list.get(0);
            
        } catch (IOException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
       return null;
       
    }

    private String getFileName(Generic obj) throws IOException {
        Types type = obj.getType();
        String file = getConfigurationEntry(CSV_PATH) + type.getType() + ".csv";
        return file;
    }

    
}

