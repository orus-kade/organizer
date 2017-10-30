
package ru.sfedu.organizer.api;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.sfedu.organizer.Constants;
import ru.sfedu.organizer.model.Entity;
import ru.sfedu.organizer.model.EntityTypes;
import ru.sfedu.organizer.utils.ConfigurationUtil;


public class CsvDataProvider implements IDataProvider{
    String ariaPath;
    String composerPath;
    String librettoPath;
    String notePath;
    String operaPath;
    String singerPath;
    String writerPath;
    @Override
    public int saveRecord(Entity obj) {
        EntityTypes type = obj.getType();
//        switch (type){
//            case ARIA : 
//                break;
//        }
                 
        return 0;      
    }

    @Override
    public int deleteRecord(Entity obj) {
        return 0;
        
    }

    @Override
    public Entity getRecordById(long id) {
       return null;
       
    }

    @Override
    public int initDataSource(){
        try {
            ariaPath = ConfigurationUtil.getConfigurationEntry(Constants.CSV_ARIA_PATH);
            composerPath = ConfigurationUtil.getConfigurationEntry(Constants.CSV_COMPOSER_PATH);
            librettoPath = ConfigurationUtil.getConfigurationEntry(Constants.CSV_LIBRETTO_PATH);
            notePath = ConfigurationUtil.getConfigurationEntry(Constants.CSV_NOTE_PATH);
            operaPath = ConfigurationUtil.getConfigurationEntry(Constants.CSV_OPERA_PATH);
            singerPath = ConfigurationUtil.getConfigurationEntry(Constants.CSV_SINGER_PATH);
            writerPath = ConfigurationUtil.getConfigurationEntry(Constants.CSV_WRITER_PATH);
            
        } catch (IOException ex) {
            Logger.getLogger(CsvDataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
}
