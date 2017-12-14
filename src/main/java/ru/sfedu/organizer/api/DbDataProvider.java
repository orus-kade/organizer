
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
 * @author orus-kade
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
            if (statement.executeUpdate(sql) == 0){
                log.error("Adding failed");
                return new Result(ResultStatuses.ERROR);
            }
            else return new Result(ResultStatuses.OK);
        } catch (Exception ex) {
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
            log.error(result.getMessage());
            return result;
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
        Result r = checkNote(obj);  
        if (!ResultStatuses.OK.equals(r.getStatus())) return r;
        r = getRecordById(obj, true);
        if (!ResultStatuses.OK.equals(r.getStatus())) return r;
        try {
            Connection connection = initConnection();
            Statement statement = connection.createStatement();
            String sql = "Update " + getTableName(obj) + 
                    " set description = '" + obj.getDescription() + 
                    "' , set objectid = " + obj.getObjectId() +
                    ", set objecttype = '" + obj.getObjectType()
                    + "' where id = " + obj.getId() + ";";
            if (statement.executeUpdate(sql) == 0){
                log.error("Updating failed");
                return new Result(ResultStatuses.ERROR);
            }                
            else return new Result(ResultStatuses.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        } 
    }

    @Override
    public Result deleteRecord(Note obj) {
        Result r = checkNote(obj);  
        if (!ResultStatuses.OK.equals(r.getStatus())) return r;
        r = getRecordById(obj, true);
        if (!ResultStatuses.OK.equals(r.getStatus())) return r;
        try {
            Connection connection = initConnection();
            Statement statement = connection.createStatement();
            String sql = "delete from " + getTableName(obj) + 
                    " where id = " + obj.getId() + ";";
            if (statement.executeUpdate(sql) == 0){
                log.error("Deleting failed");
                return new Result(ResultStatuses.ERROR);
            }                
            else return new Result(ResultStatuses.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        } 
    }

    @Override
    public Result getRecordById(Generic obj) {
        return getRecordById(obj, false);
    }
    
    
    public Result getRecordById(Generic obj, boolean check) {
        try {
            Connection connection = initConnection();            
            Statement stmt = connection.createStatement();
            String sql = "SELECT * from " + getTableName(obj) + " where id = " + obj.getId() + ";";
            ResultSet resultSet = stmt.executeQuery(sql);
            List<Generic> list = new ArrayList<Generic>(); 
            Types type = obj.getType();
            switch (type){
                case ARIA : list = getAria(resultSet, connection, check);
                    break;
                case AUTHOR : list = getAuthor(resultSet, connection, check);
                    break;
                case COMPOSER : list = getComposer(resultSet, connection, check);
                    break;
                case LIBRETTO : list = getLibretto(resultSet, connection, check);
                    break;
                case OPERA : list = getOpera(resultSet, connection, check);
                    break;
                case SINGER : list = getSinger(resultSet, connection, check);
                    break;
                case NOTE : list = getNote(resultSet);
                    break;                
            }
            if (list.isEmpty()) return new Result(ResultStatuses.ERROR, "Can't find object " + obj.getType() + " with id = " + obj.getId());
            return new Result(ResultStatuses.OK, list);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }

    }

    private List<Generic> getAria(ResultSet resultSet, Connection connection, boolean check) throws SQLException, IOException{
        List<Generic> list = new ArrayList<Generic>(); 
        while(resultSet.next()){
            Aria aria = new Aria(resultSet.getLong(1));
            aria.setTitle(resultSet.getString(2));
            aria.setText(resultSet.getString(3));
            aria.setOperaId(resultSet.getLong(4));
            list.add(aria);
        }
        if (!check && !list.isEmpty()){
            list = getRelationsAria(list, connection);
        }
        return list;
    }
    
    private List<Generic> getAuthor(ResultSet resultSet, Connection connection, boolean check) throws SQLException, IOException{
        List<Generic> list = new ArrayList<Generic>(); 
        while(resultSet.next()){
            Author author = new Author(resultSet.getLong(1));
            Date date = null;
            author.setName(resultSet.getString(2));
            author.setSurname(resultSet.getString(3));
            author.setPatronymic(resultSet.getString(4));
            author.setBiography(resultSet.getString(5));
            date = resultSet.getDate(6);
            if (date != null) 
                author.setBirthDate(date.getTime());
            date = resultSet.getDate(7);
            if (date != null)
                author.setDeathDate(date.getTime());
            list.add(author);
        }
        if (!check){
            list = getRelationsAuthor(list, connection);
        }
        return list;
    }
    
    private List<Generic> getComposer(ResultSet resultSet, Connection connection, boolean check) throws SQLException, IOException{
        List<Generic> list = new ArrayList<Generic>(); 
        while(resultSet.next()){
            Date date = null;
            Composer composer = new Composer(resultSet.getLong(1));
            composer.setName(resultSet.getString(2));
            composer.setSurname(resultSet.getString(3));
            composer.setPatronymic(resultSet.getString(4));
            composer.setBiography(resultSet.getString(5));
            date = resultSet.getDate(6);
            if (date != null) 
                composer.setBirthDate(date.getTime());
            date = resultSet.getDate(7);
            if (date != null) 
                composer.setDeathDate(date.getTime());
            list.add(composer);
        }
        if (!check){
            list = getRelationsComposer(list, connection);
        }
        return list;
    }
    
    private List<Generic> getLibretto(ResultSet resultSet, Connection connection, boolean check) throws SQLException, IOException{
        List<Generic> list = new ArrayList<Generic>(); 
        while(resultSet.next()){
            Libretto libretto = new Libretto(resultSet.getLong(1));
            libretto.setText(resultSet.getString(2));
            libretto.setOperaId(resultSet.getLong(3));
            list.add(libretto);
        }
        if (!check){
            list = getRelationsLibretto(list, connection);
        }
        return list;
    }
    
    private List<Generic> getOpera(ResultSet resultSet, Connection connection, boolean check) throws SQLException, IOException{
        List<Generic> list = new ArrayList<Generic>(); 
        while(resultSet.next()){
            Opera opera = new Opera(resultSet.getLong(1));
            opera.setTitle(resultSet.getString(2));
            opera.setHistory(resultSet.getString(3));
            opera.setLibrettoId(resultSet.getLong(4));
            list.add(opera);
        }
        if (!check){
            list = getRelationsOpera(list, connection);
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
    
    private List<Generic> getSinger(ResultSet resultSet, Connection connection, boolean check) throws SQLException, IOException{
        List<Generic> list = new ArrayList<Generic>(); 
        while(resultSet.next()){
            Date date = null;
            Singer singer = new Singer(resultSet.getLong(1));
            singer.setName(resultSet.getString(2));
            singer.setSurname(resultSet.getString(3));
            singer.setPatronymic(resultSet.getString(4));
            singer.setBiography(resultSet.getString(5));
            date = resultSet.getDate(6);
            if (date != null) 
                singer.setBirthDate(date.getTime());
            date = resultSet.getDate(7);
            if (date != null) 
                singer.setDeathDate(date.getTime());
            singer.setVoice(resultSet.getString(8));
            list.add(singer);
        }
        if (!check){
            list = getRelationsSinger(list, connection);
        }
        return list;
    }
    
    private List<Generic> getRelationsAria(List<Generic> list, Connection connection) throws IOException, SQLException{                   
            String sql = "select * from " + getConfigurationEntry(DB_TABLE_ARIA_AUTHOR) + ";";
            List<Relation> allRelations = new ArrayList<Relation>();
            Statement stmt = connection.createStatement();            
            ResultSet relations = stmt.executeQuery(sql);
            while(relations.next()){
                allRelations.add(new Relation(relations.getLong(1), relations.getLong(2)));
            }
            
            list.stream().forEach(e -> {
                    List<Long> relationList = new ArrayList<Long>();
                    relationList.addAll(allRelations
                            .stream()
                            .filter(r -> r.getId1() == e.getId())
                            .collect(ArrayList<Long>::new,
                                    (a, r) ->  a.add(r.getId2()),
                                    (a1, a2) -> a1.addAll(a2)));                            
                    ((Aria)e).setAuthors(relationList);
            });
            
            sql = "select * from " + getConfigurationEntry(DB_TABLE_ARIA_COMPOSER) + ";";          
            relations = stmt.executeQuery(sql);  
            allRelations.clear();
            while(relations.next()){
                allRelations.add(new Relation(relations.getLong(1), relations.getLong(2)));
            }
            list.stream().forEach(e -> {
                    List<Long> relationList = new ArrayList<Long>();
                    relationList.addAll(allRelations
                            .stream()
                            .filter(r -> r.getId1() == e.getId())
                            .collect(ArrayList<Long>::new,
                                    (a, r) ->  a.add(r.getId2()),
                                    (a1, a2) -> a1.addAll(a2)));                            
                    ((Aria)e).setComposers(relationList);
            });
            
            sql = "select * from " + getConfigurationEntry(DB_TABLE_ARIA_SINGER) + ";";          
            relations = stmt.executeQuery(sql);
            allRelations.clear();            
            while(relations.next()){
                allRelations.add(new Relation(relations.getLong(1), relations.getLong(2)));
            }
            list.stream().forEach(e -> {
                    List<Long> relationList = new ArrayList<Long>();
                    relationList.addAll(allRelations
                            .stream()
                            .filter(r -> r.getId1() == e.getId())
                            .collect(ArrayList<Long>::new,
                                    (a, r) ->  a.add(r.getId2()),
                                    (a1, a2) -> a1.addAll(a2)));                            
                    ((Aria)e).setComposers(relationList);
            });
            return list;
    }
    
    private List<Generic> getRelationsAuthor(List<Generic> list, Connection connection) throws IOException, SQLException{                   
            String sql = "select * from " + getConfigurationEntry(DB_TABLE_ARIA_AUTHOR) + ";";
            List<Relation> allRelations = new ArrayList<Relation>();
            Statement stmt = connection.createStatement();            
            ResultSet relations = stmt.executeQuery(sql);
            while(relations.next()){
                allRelations.add(new Relation(relations.getLong(1), relations.getLong(2)));
            }
            list.stream().forEach(e -> {
                    List<Long> relationList = new ArrayList<Long>();
                    relationList.addAll(allRelations
                            .stream()
                            .filter(r -> r.getId2() == e.getId())
                            .collect(ArrayList<Long>::new,
                                    (a, r) ->  a.add(r.getId1()),
                                    (a1, a2) -> a1.addAll(a2)));                            
                    ((Author)e).setAries(relationList);
            });
            
            sql = "select * from " + getConfigurationEntry(DB_TABLE_AUTHOR_LIBRETTO) + ";";          
            relations = stmt.executeQuery(sql);
            allRelations.clear();            
            while(relations.next()){
                allRelations.add(new Relation(relations.getLong(1), relations.getLong(2)));
            }
            list.stream().forEach(e -> {
                    List<Long> relationList = new ArrayList<Long>();
                    relationList.addAll(allRelations
                            .stream()
                            .filter(r -> r.getId1() == e.getId())
                            .collect(ArrayList<Long>::new,
                                    (a, r) ->  a.add(r.getId2()),
                                    (a1, a2) -> a1.addAll(a2)));                            
                    ((Author)e).setLibrettos(relationList);
            });
            return list;
    }
    
    private List<Generic> getRelationsComposer(List<Generic> list, Connection connection) throws IOException, SQLException{                   
            String sql = "select * from " + getConfigurationEntry(DB_TABLE_ARIA_COMPOSER) + ";";
            List<Relation> allRelations = new ArrayList<Relation>();
            Statement stmt = connection.createStatement();            
            ResultSet relations = stmt.executeQuery(sql);
            while(relations.next()){
                allRelations.add(new Relation(relations.getLong(1), relations.getLong(2)));
            }
            list.stream().forEach(e -> {
                    List<Long> relationList = new ArrayList<Long>();
                    relationList.addAll(allRelations
                            .stream()
                            .filter(r -> r.getId2() == e.getId())
                            .collect(ArrayList<Long>::new,
                                    (a, r) ->  a.add(r.getId1()),
                                    (a1, a2) -> a1.addAll(a2)));                            
                    ((Composer)e).setAries(relationList);
            });
            return list;
    }   
    
    private List<Generic> getRelationsLibretto(List<Generic> list, Connection connection) throws IOException, SQLException{                   
            String sql = "select * from " + getConfigurationEntry(DB_TABLE_AUTHOR_LIBRETTO) + ";";
            List<Relation> allRelations = new ArrayList<Relation>();
            Statement stmt = connection.createStatement();            
            ResultSet relations = stmt.executeQuery(sql);
            while(relations.next()){
                allRelations.add(new Relation(relations.getLong(1), relations.getLong(2)));
            }
            list.stream().forEach(e -> {
                    List<Long> relationList = new ArrayList<Long>();
                    relationList.addAll(allRelations
                            .stream()
                            .filter(r -> r.getId2() == e.getId())
                            .collect(ArrayList<Long>::new,
                                    (a, r) ->  a.add(r.getId1()),
                                    (a1, a2) -> a1.addAll(a2)));                            
                    ((Libretto)e).setAuthors(relationList);
            });
            return list;
    }
    
    private List<Generic> getRelationsOpera(List<Generic> list, Connection connection) throws IOException, SQLException{                   
            String sql = "select id, operaid from " + getConfigurationEntry(DB_TABLE_ARIA) + ";";
            List<Relation> allRelations = new ArrayList<Relation>();
            Statement stmt = connection.createStatement();            
            ResultSet relations = stmt.executeQuery(sql);
            while(relations.next()){
                allRelations.add(new Relation(relations.getLong(1), relations.getLong(2)));
            }
            list.stream().forEach(e -> {
                    List<Long> relationList = new ArrayList<Long>();
                    relationList.addAll(allRelations
                            .stream()
                            .filter(r -> r.getId2() == e.getId())
                            .collect(ArrayList<Long>::new,
                                    (a, r) ->  a.add(r.getId1()),
                                    (a1, a2) -> a1.addAll(a2)));                            
                    ((Opera)e).setAries(relationList);
            });
            return list;
    }
    
    private List<Generic> getRelationsSinger(List<Generic> list, Connection connection) throws IOException, SQLException{                   
            String sql = "select * from " + getConfigurationEntry(DB_TABLE_ARIA_SINGER) + ";";
            List<Relation> allRelations = new ArrayList<Relation>();
            Statement stmt = connection.createStatement();            
            ResultSet relations = stmt.executeQuery(sql);
            while(relations.next()){
                allRelations.add(new Relation(relations.getLong(1), relations.getLong(2)));
            }
            list.stream().forEach(e -> {
                    List<Long> relationList = new ArrayList<Long>();
                    relationList.addAll(allRelations
                            .stream()
                            .filter(r -> r.getId2() == e.getId())
                            .collect(ArrayList<Long>::new,
                                    (a, r) ->  a.add(r.getId1()),
                                    (a1, a2) -> a1.addAll(a2)));                            
                    ((Singer)e).setAries(relationList);
            });
            return list;
    }
    
    @Override
    public Result getAllRecords(Generic obj) {
        try {
            boolean check = false;
            Connection connection = initConnection();            
            Statement stmt = connection.createStatement();
            String sql = "SELECT * from " + getTableName(obj) + ";";
            ResultSet resultSet = stmt.executeQuery(sql);
            List<Generic> list = new ArrayList<Generic>(); 
            Types type = obj.getType();
            switch (type){
                case ARIA : list = getAria(resultSet, connection, check);
                    break;
                case AUTHOR : list = getAuthor(resultSet, connection, check);
                    break;
                case COMPOSER : list = getComposer(resultSet, connection, check);
                    break;
                case LIBRETTO : list = getLibretto(resultSet, connection, check);
                    break;
                case OPERA : list = getOpera(resultSet, connection, check);
                    break;
                case SINGER : list = getSinger(resultSet, connection, check);
                    break;
                case NOTE : list = getNote(resultSet);
                    break;                
            }
            if (list.isEmpty()) return new Result(ResultStatuses.ERROR, "Can't find object " + obj.getType() + " with id = " + obj.getId());
            return new Result(ResultStatuses.OK, list);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }
    }
    
    public Connection initConnection() throws SQLException{
        Connection connection = null;
            connection  = DriverManager.getConnection(url, user, pass);
        return connection;
    }       

    
    private String getTableName(Generic obj) throws IOException{
        Types type = obj.getType();
        String tname = "";
        switch (type){
            case ARIA : tname = getConfigurationEntry(DB_TABLE_ARIA);
                break;
            case AUTHOR : tname = getConfigurationEntry(DB_TABLE_AUTHOR);
                break;
            case COMPOSER : tname = getConfigurationEntry(DB_TABLE_COMPOSER);
                break;
            case LIBRETTO : tname = getConfigurationEntry(DB_TABLE_LIBRETTO);
                break;
            case OPERA : tname = getConfigurationEntry(DB_TABLE_OPERA);
                break;
            case SINGER : tname = getConfigurationEntry(DB_TABLE_SINGER);
                break;
            case NOTE : tname = getConfigurationEntry(DB_TABLE_NOTE);
                break;
        }
        return tname;
    }
}
