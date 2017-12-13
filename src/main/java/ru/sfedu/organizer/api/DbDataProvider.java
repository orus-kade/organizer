
package ru.sfedu.organizer.api;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;

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
        Result r = checkNote(obj);  
        if (!ResultStatuses.OK.equals(r.getStatus())) return r;
        try {
            Connection connection = initConnection();
            Statement statement = connection.createStatement();
            String sql = "Insert into " + getTableName(obj) + " values (default, '" +
                    obj.getDescription() + "' , " +
                    obj.getObjectId() + ", '" +
                    obj.getObjectType() + "');";
            if (statement.executeUpdate(sql) == 0)
                return new Result(ResultStatuses.ERROR);
            else return new Result(ResultStatuses.OK);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }        
    }

    private Result checkNote(Generic obj){
        Note note = (Note)obj;
        Result result = new Result();
        if (note == null){
            result.setStatus(ResultStatuses.ERROR);
            result.setMessage("Note: Object is null");
        }
        else{
            try{
                Types.valueOf(note.getObjectType());
            } catch(IllegalArgumentException ex){
                log.error(ex.getMessage());
                return new Result(ResultStatuses.ERROR, ex.getMessage());
            } 
            Generic object = null;
            switch (Types.valueOf(note.getObjectType())){
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
            result = getRecordById(object);
        }       
        return result;
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
            Connection connection = initConnection();            
            Statement stmt = connection.createStatement();
            String sql = "SELECT * from " + getTableName(obj) + " where id = " + obj.getId() + ";";
           
            ResultSet resultSet = stmt.executeQuery(sql);
            List<Generic> list = new ArrayList<Generic>(); 
            Types type = obj.getType();
            switch (type){
                case ARIA : list = getAria(resultSet);
                    break;
                case AUTHOR : list = getAuthor(resultSet);
                    break;
                case COMPOSER : list = getComposer(resultSet);
                    break;
                case LIBRETTO : list = getLibretto(resultSet);
                    break;
                case OPERA : list = getOpera(resultSet);
                    break;
                case SINGER : list = getSinger(resultSet);
                    break;
                case NOTE : list = getNote(resultSet);
                    break;                
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
    
    private List<Generic> getAuthor(ResultSet resultSet) throws SQLException{
        List<Generic> list = new ArrayList<Generic>(); 
        while(resultSet.next()){
            Author author = new Author(resultSet.getLong(1));
            author.setName(resultSet.getString(2));
            author.setSurname(resultSet.getString(3));
            author.setPatronymic(resultSet.getString(4));
            author.setBiography(resultSet.getString(5));
            author.setBirthDate(resultSet.getLong(6));
            author.setDeathDate(resultSet.getLong(7));
            list.add(author);
        }
        return list;
    }
    
    private List<Generic> getComposer(ResultSet resultSet) throws SQLException{
        List<Generic> list = new ArrayList<Generic>(); 
        while(resultSet.next()){
            Composer composer = new Composer(resultSet.getLong(1));
            composer.setName(resultSet.getString(2));
            composer.setSurname(resultSet.getString(3));
            composer.setPatronymic(resultSet.getString(4));
            composer.setBiography(resultSet.getString(5));
            composer.setBirthDate(resultSet.getLong(6));
            composer.setDeathDate(resultSet.getLong(7));
            list.add(composer);
        }
        return list;
    }
    
    private List<Generic> getLibretto(ResultSet resultSet) throws SQLException{
        List<Generic> list = new ArrayList<Generic>(); 
        while(resultSet.next()){
            Libretto libretto = new Libretto(resultSet.getLong(1));
            libretto.setText(resultSet.getString(2));
            libretto.setOperaId(resultSet.getLong(3));
            list.add(libretto);
        }
        return list;
    }
    
    private List<Generic> getOpera(ResultSet resultSet) throws SQLException{
        List<Generic> list = new ArrayList<Generic>(); 
        while(resultSet.next()){
            Opera opera = new Opera(resultSet.getLong(1));
            opera.setTitle(resultSet.getString(2));
            opera.setHistory(resultSet.getString(3));
            opera.setLibrettoId(resultSet.getLong(4));
            list.add(opera);
        }
        return list;
    }
    
    private List<Generic> getNote(ResultSet resultSet) throws SQLException{
        List<Generic> list = new ArrayList<Generic>(); 
        while(resultSet.next()){
            Note note = new Note(resultSet.getLong(1));
            note.setDescription(resultSet.getString(2));
            note.setObjectId(resultSet.getLong(3));
            note.setObjectType(resultSet.getString(4));
            list.add(note);
        }
        return list;
    }
    
    private List<Generic> getSinger(ResultSet resultSet) throws SQLException{
        List<Generic> list = new ArrayList<Generic>(); 
        while(resultSet.next()){
            Singer singer = new Singer(resultSet.getLong(1));
            singer.setName(resultSet.getString(2));
            singer.setSurname(resultSet.getString(3));
            singer.setPatronymic(resultSet.getString(4));
            singer.setBiography(resultSet.getString(5));
            singer.setBirthDate(resultSet.getLong(6));
            singer.setDeathDate(resultSet.getLong(7));
            singer.setVoice(resultSet.getString(8));
            list.add(singer);
        }
        return list;
    }
    
    
    
    @Override
    public Result getAllRecords(Generic obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Connection initConnection() throws SQLException{
        Connection connection = null;
            connection  = DriverManager.getConnection(url, user, pass);
        return connection;
    }       

    
    private String getTableName(Generic obj){
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
