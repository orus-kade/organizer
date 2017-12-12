
package ru.sfedu.organizer.api;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import static ru.sfedu.organizer.Constants.*;
import ru.sfedu.organizer.model.*;
import static ru.sfedu.organizer.utils.ConfigurationUtil.getConfigurationEntry;


/**
 *
 * @author user
 */
public class DbDataProvider implements IDataProvider<Generic>{
    Connection connection;
    
    @Override
    public Result addRecord(Note obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result editRecord(Note obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result deleteRecord(Note obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result getRecordById(Generic obj) {
        if (this.connection == null) return new Result(ResultStatuses.ERROR);
        try { 
            Statement statement = connection.createStatement();
            
        } catch (SQLException ex) {
            Logger.getLogger(DbDataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @Override
    public Result getAllRecords(Generic obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Result init_connection(){
        Connection connection = null;
        this.connection = null;
        try {
            connection  = DriverManager.getConnection(
                    getConfigurationEntry(DB_URL), getConfigurationEntry(DB_USER), getConfigurationEntry(DB_PASS));
                    //getConfigurationEntry(DB_URL), "organizer_user", "music5");
            this.connection = connection;
//            Result result = new Result();
//            result.setStatus(ResultStatuses.OK);
            return new Result(ResultStatuses.OK);
        } catch (SQLException ex) {
            Logger.getLogger(DbDataProvider.class.getName()).log(Level.SEVERE, null, ex);            
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(DbDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }        
    }
    
    private String get_table_name(Generic obj){
        Types type = obj.getType();
        switch (type){
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
}
