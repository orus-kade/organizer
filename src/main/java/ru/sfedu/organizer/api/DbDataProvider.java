
package ru.sfedu.organizer.api;


import ru.sfedu.organizer.model.Relation;
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
import ru.sfedu.organizer.utils.ConfigurationUtil;



/**
 *
 * @author orus-kade
 */
public class DbDataProvider implements IDataProvider<Generic>{
    private static final Logger log = Logger.getLogger(DbDataProvider.class);
    private ConfigurationUtil config;
    
    String user;
    String url;
    String pass;
    
    /**
     *
     * @param path
     * @throws IOException
     */
    public DbDataProvider(String path) throws IOException{
            this.config = new ConfigurationUtil(path);
            this.user = config.getConfigurationEntry(DB_USER);
            this.url = config.getConfigurationEntry(DB_URL);
            this.pass = config.getConfigurationEntry(DB_PASS);            
    }
    
    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Result addRecord(Note obj) {
        Result r = checkNote(obj);  
        if (!ResultStatuses.OK.equals(r.getStatus())) return r;
        try {
            Connection connection = initConnection();
            try{
                Statement statement = connection.createStatement();
                String sql = "Insert into " + getTableName(obj) + " values (" +
                        obj.getId() + ", '" +
                        obj.getDescription() + "' , " +
                        obj.getObjectId() + ", '" +
                        obj.getObjectType() + "');";
                if (statement.executeUpdate(sql) == 0){
                    log.error("Adding failed");
                    connection.close();
                    return new Result(ResultStatuses.ERROR);
                }
                else {
                    sql = "select max(id) from " + getTableName(obj) + ";";
                    ResultSet set = statement.executeQuery(sql);
                    set.next();
                    long id = set.getLong(1);                    
                    obj.setId(id);
                    List<Generic> resultList = new ArrayList<Generic>();
                    resultList.add(obj);
                    connection.close();
                    return new Result(ResultStatuses.OK, resultList);
                }  
            } catch (SQLException ex) {
                connection.close();
                log.error(ex);
                return new Result(ResultStatuses.ERROR);
            }
        } catch (IOException | SQLException  ex) {
            log.error(ex);
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
        if (obj.getId() <= 0){
            result.setStatus(ResultStatuses.ERROR);
            result.setMessage("Note: imposible id <= 0");
            log.error(result.getMessage());
            return result;
        }
            try{
                Types.valueOf(note.getObjectType());
            } catch(IllegalArgumentException ex){
                log.error(ex);
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
            result = getRecordById(object, true);    
        return result;
    }
       
    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Result editRecord(Note obj) {
        Result r = checkNote(obj);  
        if (!ResultStatuses.OK.equals(r.getStatus())) return r;
        r = getRecordById(obj, true);
        if (!ResultStatuses.OK.equals(r.getStatus())) return r;
        try {
            Connection connection = initConnection();
            try{
                Statement statement = connection.createStatement();
                String sql = "Update " + getTableName(obj) + 
                        " set description = '" + obj.getDescription() + 
                        "' , objectid = " + obj.getObjectId() +
                        ", objecttype = '" + obj.getObjectType()
                        + "' where id = " + obj.getId() + ";";
                if (statement.executeUpdate(sql) == 0){
                    log.error("Updating failed");
                    connection.close();
                    return new Result(ResultStatuses.ERROR);
                }                
                else return new Result(ResultStatuses.OK);
            } catch (SQLException ex) {
                log.error(ex);
                connection.close();
                return new Result(ResultStatuses.ERROR, ex.getMessage());
            }
        } catch (IOException  | SQLException ex) {
            log.error(ex);
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        } 
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Result deleteRecord(Note obj) {
        Result r = checkNote(obj);  
        if (!ResultStatuses.OK.equals(r.getStatus())) return r;
        r = getRecordById(obj, true);
        if (!ResultStatuses.OK.equals(r.getStatus())) return r;
        try {
            Connection connection = initConnection();
            try {
                Statement statement = connection.createStatement();
                String sql = "delete from " + getTableName(obj) + 
                        " where id = " + obj.getId() + ";";
                if (statement.executeUpdate(sql) == 0){
                    log.error("Deleting failed");
                    connection.close();
                    return new Result(ResultStatuses.ERROR);
                }                
                else return new Result(ResultStatuses.OK);
            } catch (SQLException ex) {
                log.error(ex);
                connection.close();
                return new Result(ResultStatuses.ERROR, ex.getMessage());
            }
        } catch (IOException | SQLException ex) {
            log.error(ex);
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        } 
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Result getRecordById(Generic obj) {
        return getRecordById(obj, false);
    }    
    
    /**
     *
     * @param obj
     * @param check
     * @return
     */
    public Result getRecordById(Generic obj, boolean check) {
        try {
            Connection connection = initConnection();   
            try{
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
                connection.close();
                if (list.isEmpty()) return new Result(ResultStatuses.NOTFOUND, "Can't find object " + obj.getType() + " with id = " + obj.getId());
                return new Result(ResultStatuses.OK, list);
            } catch (SQLException ex) {
                log.error(ex);
                connection.close();
                return new Result(ResultStatuses.ERROR, ex.getMessage());
            }
        } catch (IOException | SQLException ex) {
            log.error(ex);
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
            List<Generic> listWithRelations = new ArrayList<Generic>();
            listWithRelations.addAll(getRelationsAria(list, connection));
            return listWithRelations;
        }
        return list;
    }
    
    private List<Generic> getAuthor(ResultSet resultSet, Connection connection, boolean check) throws SQLException, IOException{
        List<Generic> list = new ArrayList<Generic>(); 
        while(resultSet.next()){
            Author author = new Author(resultSet.getLong(1));
            Optional<Date> date = Optional.empty();
            author.setName(resultSet.getString(2));
            author.setSurname(resultSet.getString(3));
            author.setPatronymic(resultSet.getString(4));
            author.setBiography(resultSet.getString(5));
            date.ofNullable(resultSet.getDate(6));
            if (date.isPresent()) 
                author.setBirthDate(date.get().getTime());
            date.ofNullable(resultSet.getDate(7));
            if (date.isPresent())
                author.setDeathDate(date.get().getTime());
            list.add(author);
        }
        if (!check  && !list.isEmpty()){
            List<Generic> listWithRelations = new ArrayList<Generic>();
            listWithRelations.addAll(getRelationsAuthor(list, connection));
            return listWithRelations;
        }
        return list;
    }
    
    private List<Generic> getComposer(ResultSet resultSet, Connection connection, boolean check) throws SQLException, IOException{
        List<Generic> list = new ArrayList<Generic>(); 
        while(resultSet.next()){
            Optional<Date> date = Optional.empty();
            Composer composer = new Composer(resultSet.getLong(1));
            composer.setName(resultSet.getString(2));
            composer.setSurname(resultSet.getString(3));
            composer.setPatronymic(resultSet.getString(4));
            composer.setBiography(resultSet.getString(5));
            date.ofNullable(resultSet.getDate(6));
            if (date.isPresent()) 
                composer.setBirthDate(date.get().getTime());
            date.ofNullable(resultSet.getDate(7));
            if (date.isPresent())
                composer.setDeathDate(date.get().getTime());
            list.add(composer);
        }
        if (!check && !list.isEmpty()){
            List<Generic> listWithRelations = new ArrayList<Generic>();
            listWithRelations.addAll(getRelationsComposer(list, connection));
            return listWithRelations;
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
        if (!check && !list.isEmpty()){
            List<Generic> listWithRelations = new ArrayList<Generic>();
            listWithRelations.addAll(getRelationsLibretto(list, connection));
            return listWithRelations;
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
        if (!check && !list.isEmpty()){
            List<Generic> listWithRelations = new ArrayList<Generic>();
            listWithRelations.addAll(getRelationsOpera(list, connection));
            return listWithRelations;
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
            Optional<Date> date = Optional.empty();
            Singer singer = new Singer(resultSet.getLong(1));
            singer.setName(resultSet.getString(2));
            singer.setSurname(resultSet.getString(3));
            singer.setPatronymic(resultSet.getString(4));
            singer.setBiography(resultSet.getString(5));
             date.ofNullable(resultSet.getDate(6));
            if (date.isPresent()) 
                singer.setBirthDate(date.get().getTime());
            date.ofNullable(resultSet.getDate(7));
            if (date.isPresent())
                singer.setDeathDate(date.get().getTime());
            singer.setVoice(resultSet.getString(8));
            list.add(singer);
        }
        if (!check && !list.isEmpty()){
            List<Generic> listWithRelations = new ArrayList<Generic>();
            listWithRelations.addAll(getRelationsSinger(list, connection));
            return listWithRelations;
        }
        return list;
    }
    
    private List<Generic> getRelationsAria(List<Generic> list, Connection connection) throws IOException, SQLException{                   
            String sql = "select * from " + config.getConfigurationEntry(DB_TABLE_ARIA_AUTHOR) + ";";
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
            
            sql = "select * from " + config.getConfigurationEntry(DB_TABLE_ARIA_COMPOSER) + ";";          
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
            
            sql = "select * from " + config.getConfigurationEntry(DB_TABLE_ARIA_SINGER) + ";";          
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
            
            sql = "select t.id, n.id from " + config.getConfigurationEntry(DB_TABLE_ARIA )
                    + " t join " + config.getConfigurationEntry(DB_TABLE_NOTE) + " n on t.id = n.objectId;";          
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
                    ((Aria)e).setNotes(relationList);
            });            
            return list;
    }
    
    private List<Generic> getRelationsAuthor(List<Generic> list, Connection connection) throws IOException, SQLException{                   
            String sql = "select * from " + config.getConfigurationEntry(DB_TABLE_ARIA_AUTHOR) + ";";
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
            
            sql = "select * from " + config.getConfigurationEntry(DB_TABLE_AUTHOR_LIBRETTO) + ";";          
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
            
            sql = "select t.id, n.id from " + config.getConfigurationEntry(DB_TABLE_AUTHOR)
                    + " t join " + config.getConfigurationEntry(DB_TABLE_NOTE) + " n on t.id = n.objectId;";          
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
                    ((Author)e).setNotes(relationList);
            });            
            return list;
    }
    
    private List<Generic> getRelationsComposer(List<Generic> list, Connection connection) throws IOException, SQLException{                   
            String sql = "select * from " + config.getConfigurationEntry(DB_TABLE_ARIA_COMPOSER) + ";";
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
            sql = "select t.id, n.id from " + config.getConfigurationEntry(DB_TABLE_COMPOSER)
                    + " t join " + config.getConfigurationEntry(DB_TABLE_NOTE) + " n on t.id = n.objectId;";          
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
                    ((Composer)e).setNotes(relationList);
            });
            return list;
    }   
    
    private List<Generic> getRelationsLibretto(List<Generic> list, Connection connection) throws IOException, SQLException{                   
            String sql = "select * from " + config.getConfigurationEntry(DB_TABLE_AUTHOR_LIBRETTO) + ";";
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
            sql = "select t.id, n.id from " + config.getConfigurationEntry(DB_TABLE_LIBRETTO)
                    + " t join " + config.getConfigurationEntry(DB_TABLE_NOTE) + " n on t.id = n.objectId;";          
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
                    ((Libretto)e).setNotes(relationList);
            });
            return list;
    }
    
    private List<Generic> getRelationsOpera(List<Generic> list, Connection connection) throws IOException, SQLException{                   
            String sql = "select id, operaid from " + config.getConfigurationEntry(DB_TABLE_ARIA) + ";";
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
            sql = "select t.id, n.id from " + config.getConfigurationEntry(DB_TABLE_OPERA)
                    + " t join " + config.getConfigurationEntry(DB_TABLE_NOTE) + " n on t.id = n.objectId;";          
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
                    ((Opera)e).setNotes(relationList);
            });
            return list;
    }
    
    private List<Generic> getRelationsSinger(List<Generic> list, Connection connection) throws IOException, SQLException{                   
            String sql = "select * from " + config.getConfigurationEntry(DB_TABLE_ARIA_SINGER) + ";";
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
            sql = "select t.id, n.id from " + config.getConfigurationEntry(DB_TABLE_SINGER)
                    + " t join " + config.getConfigurationEntry(DB_TABLE_NOTE) + " n on t.id = n.objectId;";          
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
                    ((Singer)e).setNotes(relationList);
            });
            return list;
    }
    
    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Result getAllRecords(Generic obj) {
        try {
            boolean check = false;
            Connection connection = initConnection();     
            try{
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
            connection.close();
            if (list.isEmpty()) return new Result(ResultStatuses.NOTFOUND, "Can't find object " + obj.getType() + " with id = " + obj.getId());
            return new Result(ResultStatuses.OK, list);
            } catch (SQLException ex) {
                log.error(ex.getMessage());
                connection.close();
                return new Result(ResultStatuses.ERROR, ex.getMessage());
            }
        } catch (IOException | SQLException ex) {
            log.error(ex.getMessage());
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }
    }
    
    /**
     *
     * @return
     * @throws SQLException
     */
    public Connection initConnection() throws SQLException{
        Connection connection = null;
            connection  = DriverManager.getConnection(url, user, pass);
        return connection;
    }       

    
    private String getTableName(Generic obj) throws IOException{
        Types type = obj.getType();
        String tname = "";
        switch (type){
            case ARIA : tname = config.getConfigurationEntry(DB_TABLE_ARIA);
                break;
            case AUTHOR : tname = config.getConfigurationEntry(DB_TABLE_AUTHOR);
                break;
            case COMPOSER : tname = config.getConfigurationEntry(DB_TABLE_COMPOSER);
                break;
            case LIBRETTO : tname = config.getConfigurationEntry(DB_TABLE_LIBRETTO);
                break;
            case OPERA : tname = config.getConfigurationEntry(DB_TABLE_OPERA);
                break;
            case SINGER : tname = config.getConfigurationEntry(DB_TABLE_SINGER);
                break;
            case NOTE : tname = config.getConfigurationEntry(DB_TABLE_NOTE);
                break;
        }
        return tname;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Result findRecord(Generic obj) {
        Optional<Generic> object = Optional.ofNullable(obj);
        if (!object.isPresent()) {
            log.error("Object is null");
            return new Result(ResultStatuses.ERROR, "Object is null");
        }
        else{
            Optional<Types> type = Optional.ofNullable(obj.getType());
            if (!type.isPresent()){
                log.error("Object type is null");
                return new Result(ResultStatuses.ERROR, "Object type is null");
            }  
            Result result = new Result();
            try{
                switch (type.get()){
                    case ARIA : result = findAria((Aria)obj);
                        break;
                    case COMPOSER : result = findComposer((Composer)obj);
                        break;
                    case LIBRETTO : //result = findLibretto((Libretto)obj);
                        result = new Result(ResultStatuses.WARNING, "Unsupported type");
                        break;
                    case OPERA : result = findOpera((Opera)obj);
                        break;
                    case SINGER : result = findSinger((Singer)obj);
                        break;
                    case AUTHOR : result = findAuthor((Author)obj);
                        break;    
                    case NOTE: result = findNote((Note)obj);  
                        break; 
                }  
            } catch (SQLException ex) {
                log.error(ex.getMessage());
                return new Result(ResultStatuses.ERROR, ex.getMessage());
            }
            return result;
        }
    }
    
    private Result findAria(Aria obj) throws SQLException{
        Connection connection = initConnection();
        try{
            List<String> conditions = new ArrayList<String>();
            String query = "Select * from " + getTableName(obj) + " ";
            if (obj.getTitle() != null) conditions.add(" title LIKE '%" + obj.getTitle() + "%' ");
            if (obj.getText()!= null) conditions.add(" text LIKE '%" + obj.getText()+ "%' ");
            if (!conditions.isEmpty()){
                query += " where " + String.join(" AND ", conditions.toArray(new String[conditions.size()]));
            }
            else{
                query += ";";
            }
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(query);
                List<Generic> list = new ArrayList<Generic>();
                list.addAll(getAria(resultSet, connection, false)); 
                if (list.isEmpty()) return new Result(ResultStatuses.NOTFOUND);
                return new Result(ResultStatuses.OK, list);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            connection.close();
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }        
    }
    
    private Result findComposer(Composer obj) throws SQLException{
        Connection connection = initConnection();
        try{
            List<String> conditions = new ArrayList<String>();
            String query = "Select * from " + getTableName(obj) + " ";
            if (obj.getName()!= null) conditions.add(" name LIKE '%" + obj.getName() + "%' ");
            if (obj.getSurname()!= null) conditions.add(" surname LIKE '%" + obj.getSurname()+ "%' ");
            if (obj.getPatronymic()!= null) conditions.add(" patronymic LIKE '%" + obj.getPatronymic()+ "%' ");            
            if (!conditions.isEmpty()){
                query += " where " + String.join(" AND ", conditions.toArray(new String[conditions.size()]));
            }
            else{
                query += ";";
            }
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(query);
                List<Generic> list = new ArrayList<Generic>();
                list.addAll(getComposer(resultSet, connection, false)); 
                if (list.isEmpty()) return new Result(ResultStatuses.NOTFOUND);
                return new Result(ResultStatuses.OK, list);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            connection.close();
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }        
    }
    
    private Result findAuthor(Author obj) throws SQLException{
        Connection connection = initConnection();
        try{
            List<String> conditions = new ArrayList<String>();
            String query = "Select * from " + getTableName(obj) + " ";
            if (obj.getName()!= null) conditions.add(" name LIKE '%" + obj.getName() + "%' ");
            if (obj.getSurname()!= null) conditions.add(" surname LIKE '%" + obj.getSurname()+ "%' ");
            if (obj.getPatronymic()!= null) conditions.add(" patronymic LIKE '%" + obj.getPatronymic()+ "%' ");            
            if (!conditions.isEmpty()){
                query += " where " + String.join(" AND ", conditions.toArray(new String[conditions.size()]));
            }
            else{
                query += ";";
            }
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(query);
                List<Generic> list = new ArrayList<Generic>();
                list.addAll(getAuthor(resultSet, connection, false)); 
                if (list.isEmpty()) return new Result(ResultStatuses.NOTFOUND);
                return new Result(ResultStatuses.OK, list);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            connection.close();
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }        
    }
    
    private Result findSinger(Singer obj) throws SQLException{
        Connection connection = initConnection();
        try{
            List<String> conditions = new ArrayList<String>();
            String query = "Select * from " + getTableName(obj) + " ";
            if (obj.getName()!= null) conditions.add(" name LIKE '%" + obj.getName() + "%' ");
            if (obj.getSurname()!= null) conditions.add(" surname LIKE '%" + obj.getSurname()+ "%' ");
            if (obj.getPatronymic()!= null) conditions.add(" patronymic LIKE '%" + obj.getPatronymic()+ "%' "); 
            if (obj.getVoice()!= null) conditions.add(" voice LIKE '%" + obj.getVoice()+ "%' ");   
            if (!conditions.isEmpty()){
                query += " where " + String.join(" AND ", conditions.toArray(new String[conditions.size()]));
            }
            else{
                query += ";";
            }
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(query);
                List<Generic> list = new ArrayList<Generic>();
                list.addAll(getSinger(resultSet, connection, false)); 
                if (list.isEmpty()) return new Result(ResultStatuses.NOTFOUND);
                return new Result(ResultStatuses.OK, list);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            connection.close();
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }        
    }
    
    private Result findOpera(Opera obj) throws SQLException{
        Connection connection = initConnection();
        try{
            List<String> conditions = new ArrayList<String>();
            String query = "Select * from " + getTableName(obj) + " ";
            if (obj.getTitle() != null) conditions.add(" title LIKE '%" + obj.getTitle() + "%' ");

            if (!conditions.isEmpty()){
                query += " where " + String.join(" AND ", conditions.toArray(new String[conditions.size()]));
            }
            else{
                query += ";";
            }
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(query);
                List<Generic> list = new ArrayList<Generic>();
                list.addAll(getOpera(resultSet, connection, false)); 
                if (list.isEmpty()) return new Result(ResultStatuses.NOTFOUND);
                return new Result(ResultStatuses.OK, list);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            connection.close();
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }        
    }
    
    private Result findNote(Note obj) throws SQLException{
        Connection connection = initConnection();
        try{
            List<String> conditions = new ArrayList<String>();
            String query = "Select * from " + getTableName(obj) + " ";
            if (obj.getObjectType()!= null) conditions.add(" objectType LIKE '%" + obj.getObjectType()+ "%' ");
            
           if (!conditions.isEmpty()){
                query += " where " + String.join(" AND ", conditions.toArray(new String[conditions.size()]));
            }
            else{
                query += ";";
            }
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(query);
                List<Generic> list = new ArrayList<Generic>();
                list.addAll(getNote(resultSet)); 
                if (list.isEmpty()) return new Result(ResultStatuses.NOTFOUND);
                return new Result(ResultStatuses.OK, list);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            connection.close();
            return new Result(ResultStatuses.ERROR, ex.getMessage());
        }        
    }
}
