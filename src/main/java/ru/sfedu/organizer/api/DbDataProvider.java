
package ru.sfedu.organizer.api;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.log4j.Logger;
import static ru.sfedu.organizer.Constants.*;
import ru.sfedu.organizer.model.*;
import static ru.sfedu.organizer.utils.ConfigurationUtil.getConfigurationEntry;


/**
 *
 * @author user
 */
public class DbDataProvider implements IDataProvider<Generic>{
    private static final Logger log = Logger.getLogger(DbDataProvider.class);
    
    String user;
    String url;
    String pass;
    
    public DbDataProvider() throws IOException{
            this.user = getConfigurationEntry(DB_USER);
            this.url = getConfigurationEntry(DB_URL);
            this.pass = getConfigurationEntry(DB_PASS);
    }
    
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
        try {
            Connection connection = init_connection();            
            Statement stmt = connection.createStatement();
            String sql = "SELECT * from " + get_table_name(obj) + " where id = " + obj.getId() + ";";
           
            ResultSet resultSet = stmt.executeQuery(sql);
            List<Generic> list = new ArrayList<Generic>(); 
            Types type = obj.getType();
            switch (type){
                case ARIA : list = getAria(resultSet);
                    break;
//                case AUTHOR : list = getAuthor(resultSet);
//                    break;
//                case COMPOSER : list = getComposer(resultSet);
//                    break;
//                case LIBRETTO : list = getLibretto(resultSet);
//                    break;
//                case OPERA : list = getOpera(resultSet);
//                    break;
//                case SINGER : list = getSinger(resultSet);
//                    break;
//                case NOTE : list = getNote(resultSet);
//                    break;                
            }
            return new Result(ResultStatuses.OK, list);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }

    }

    private List<Generic> getAria(ResultSet resultSet) throws SQLException{
        List<Generic> list = new ArrayList<Generic>(); 
        while(resultSet.next()){
            Aria aria = new Aria(resultSet.getLong(1));
            aria.setTitle(resultSet.getString(2));
            aria.setText(resultSet.getString(3));
            aria.setOperaId(resultSet.getLong(4));
            list.add(aria);
        }
        return list;
    }   
    
    
    
    @Override
    public Result getAllRecords(Generic obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Connection init_connection() throws SQLException{
        Connection connection = null;
            connection  = DriverManager.getConnection(url, user, pass);
        return connection;
    }       

    
    private String get_table_name(Generic obj){
        Types type = obj.getType();
        String tname = "";
        switch (type){
            case ARIA : tname = "aria";
                break;
            case AUTHOR : tname = "author";
                break;
            case COMPOSER : tname = "composer";
                break;
            case LIBRETTO : tname = "libretto";
                break;
            case OPERA : tname = "opera";
                break;
            case SINGER : tname = "singer";
                break;
            case NOTE : tname = "note";
                break;
        }
        return tname;
    }
}
