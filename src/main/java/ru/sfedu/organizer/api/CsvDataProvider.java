
package ru.sfedu.organizer.api;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static ru.sfedu.organizer.Constants.*;
import ru.sfedu.organizer.model.Generic;
import ru.sfedu.organizer.model.ClassType;
import static ru.sfedu.organizer.utils.ConfigurationUtil.*;



public class CsvDataProvider implements IDataProvider{
    
    @Override
    public int saveRecord(Generic obj) {
        ClassType type = obj.getType();
//        switch (type){
//            case ARIA : 
//                break;
//        }
                 
        return 0;      
    }

    @Override
    public int deleteRecord(Generic obj) {
        return 0;
        
    }

    @Override
    public Generic getRecordById(long id) {
       return null;
       
    }

    private String getFile(Generic obj) throws IOException {
        ClassType type = obj.getType();
        String file = getConfigurationEntry(CSV_PATH) + type.getType() + ".csv";
        return file;
    }

    
}
