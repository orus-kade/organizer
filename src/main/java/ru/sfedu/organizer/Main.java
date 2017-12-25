
package ru.sfedu.organizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;
import static ru.sfedu.organizer.Constants.FILE_PATH;
import ru.sfedu.organizer.api.CsvDataProvider;
import ru.sfedu.organizer.api.DbDataProvider;
import ru.sfedu.organizer.api.IDataProvider;
import ru.sfedu.organizer.api.XmlDataProvider;
import ru.sfedu.organizer.model.*;
import ru.sfedu.organizer.utils.MyGenerator;


/**
 *
 * @author sterie
 */

public class Main {
    private static Logger log = Logger.getLogger(Main.class);
    private static String defaultSource = Constants.SOURCE_DEFAULT;

    private static void logBasicSystemInfo() {
        log.info("Launching the application...");
        log.info("Operating System: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
        log.info("JRE: " + System.getProperty("java.version"));
        log.info("Java Launched From: " + System.getProperty("java.home"));
        log.info("Class Path: " + System.getProperty("java.class.path"));
        log.info("Library Path: " + System.getProperty("java.library.path"));
        log.info("User Home Directory: " + System.getProperty("user.home"));
        log.info("User Working Directory: " + System.getProperty("user.dir"));
        log.info("Test INFO logging.");
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {        
        log.debug("Main[0]: starting application.........");        
        logBasicSystemInfo();         
        cli(args);        
    }
    
    private static void cli(String[] args){
                
        Options options = new Options();
        
        options.addOption("t", "type", true, "type of objects");
        options.addOption("id", true, "id of objects");        
        options.addOption("ttl", "title", true, "title of objects");
        options.addOption("txt", "text", true, "text of objects");
        options.addOption("n", "name", true, "name of human");
        options.addOption("sn", "surname", true, "surname of objects");
        options.addOption("pat", "patronymic", true, "patronymic of objects");
        options.addOption("v", "voice", true, "voice of singer");
        options.addOption("ot", "objectType", true, "object type");
        options.addOption("desc", "description", false, "note descrption");
        options.addOption("oid", "objectId", true, "object id");
        options.addOption("a","all", false, "get all objects");
        options.addOption("h", "help", false, "show help");
        options.addOption("src", "source", true, "set source");
        
        Scanner scan = new Scanner(System.in);
        try{                
            CommandLine line = new BasicParser().parse(options, args);
                        
            log.info(System.getProperty(FILE_PATH));
            
            IDataProvider provider = new CsvDataProvider(System.getProperty(FILE_PATH));
            String source;
            if(line.hasOption("src")){
                source = line.getOptionValue("src");
            }
            else {
                source = defaultSource;
            }
            if (Arrays.asList(Constants.SOURCES).contains(source)){
                try{
                    switch(source){
                        case "csv" : provider = new CsvDataProvider(System.getProperty(FILE_PATH));
                            break;
                        case "xml" : provider = new XmlDataProvider(System.getProperty(FILE_PATH));
                            break;
                        case "db" :  provider = new DbDataProvider(System.getProperty(FILE_PATH));
                        case "database":
                            break;
                    }
                } catch (IOException ex){
                    log.error(ex);
                    log.info("Changing source to default...");
                    if (source.equals(defaultSource)){
                        log.error("Can't change source. Default source: " + defaultSource);
                        log.error("Stop execution");
                        System.exit(1);
                    }
                    source = defaultSource;
                    switch(source){
                        case "cvs" : provider = new CsvDataProvider(System.getProperty(FILE_PATH));
                            break;
                        case "xml" : provider = new XmlDataProvider(System.getProperty(FILE_PATH));
                            break;
                    }
                    log.info("Source wa changed from \"database\" to default source \"" + defaultSource + "\"");
                }
            }
            log.info("Current source : " + source);
            
            if (line.hasOption("h")){
                List<Option> optionsFind = new ArrayList<Option>();
                optionsFind.add(options.getOption("src"));
                optionsFind.add(options.getOption("h"));
                log.info("Arguments:");
                optionsFind.stream().forEach(e -> {
                    String str = "-" + e.getOpt() +  ", -" + e.getLongOpt();
                    if(e.getArgs() > 0) str += "\t<arg>";
                    else str += "\t";
                    str += "\t" + e.getDescription();
                    log.info(str);
                });
                log.info("Commands:");
                log.info("find \t - to find objects");
                log.info("create \t - to create note");
                log.info("edit \t - to edit note");
                log.info("delete \t - to delete note");
                log.info("help \t - to show help message");
                log.info("exit \t - to exit application");                
            }
            
            
            
            log.info("Enter command");
            while(true){
                try{
                    String[] arr = scan.nextLine().split(" ");    
                    //String[] arr = System.console().readLine().split(" ");    
                    line = new BasicParser().parse(options, arr); 

                    if (arr[0].equals("exit")){
                        System.exit(0);
                    }             

                    if (arr[0].equals("help")){
                        log.info("Commands:");
                        log.info("find \t - to find objects");
                        log.info("create \t - to create note");
                        log.info("edit \t - to edit note");
                        log.info("delete \t - to delete note");
                        log.info("help \t - to show help message");
                        log.info("exit \t - to exit application");
                    }
                    
                    if (arr[0].equals("find")){
                        if (arr.length > 1 && arr[1].equals("help")){
                            List<Option> optionsFind = new ArrayList<Option>();
                            optionsFind.add(options.getOption("type"));
                            optionsFind.add(options.getOption("all"));
                            optionsFind.add(options.getOption("title"));
                            optionsFind.add(options.getOption("text"));
                            optionsFind.add(options.getOption("objectType"));
                            optionsFind.add(options.getOption("name"));
                            optionsFind.add(options.getOption("surname"));
                            optionsFind.add(options.getOption("patronymic"));
                            log.info("Options for command find:");
                            optionsFind.stream().forEach(e -> {
                                String str = "-" + e.getOpt() +  ", -" + e.getLongOpt();
                                if(e.getArgs() > 0) str += "\t<arg>";
                                else str += "\t";
                                str += "\t" + e.getDescription();
                                log.info(str);
                            });
                        }
                        else {
                            if (line.hasOption("type")){
                                try{
                                    Types type = Types.valueOf(line.getOptionValue("type").toUpperCase());
                                    Generic obj = new Aria();
                                    switch (type){
                                        case ARIA : obj = new Aria();
                                            break;
                                        case COMPOSER : obj = new Composer();
                                            break;
                                        case LIBRETTO : obj = new Libretto();;
                                            break;
                                        case OPERA : obj = new Opera();
                                            break;
                                        case SINGER : obj = new Singer();
                                            break;
                                        case AUTHOR : obj = new Author();
                                            break;
                                        case NOTE : obj = new Note();
                                            break;
                                    }
                                    if (line.hasOption("all")){
                                        Result result = provider.getAllRecords(obj);
                                        log.info(result.getStatus()); 
                                        if (result.getStatus().equals(ResultStatuses.OK)){
                                            log.info(obj.getType());
                                            result.getList().stream().forEach(e -> log.info(e));
                                        }
                                    }
                                    else {
                                        if (type.equals(Types.ARIA)){
                                            if (line.hasOption("title")){
                                                ((Aria)obj).setTitle(line.getOptionValue("title"));
                                            }
                                            if (line.hasOption("text")){
                                                ((Aria)obj).setText(line.getOptionValue("text"));
                                            }
                                        }
                                        if (type.equals(Types.OPERA)){
                                            if (line.hasOption("title")){
                                                ((Opera)obj).setTitle(line.getOptionValue("title"));
                                            }
                                        }
                                        if (type.equals(Types.NOTE)){
                                            if (line.hasOption("objectType")){
                                                Types objectType = Types.valueOf(line.getOptionValue("objectType").toUpperCase()); 
                                                ((Note)obj).setObjectType(objectType.toString());
                                            }
                                        }
                                        if (Arrays.asList(new Types[] {Types.AUTHOR, Types.COMPOSER, Types.SINGER}).contains(type)){
                                            if (line.hasOption("name")){
                                                ((Human)obj).setName(line.getOptionValue("name"));
                                            }
                                            if (line.hasOption("surname")){
                                                ((Human)obj).setSurname(line.getOptionValue("surname"));
                                            }
                                            if (line.hasOption("patronymic")){
                                                ((Human)obj).setPatronymic(line.getOptionValue("patronymic"));
                                            }
                                            if (type.equals(Types.SINGER) && line.hasOption("voice")){
                                                ((Singer)obj).setVoice(line.getOptionValue("voice"));
                                            }
                                        }
                                        //log.info(obj);
                                        Result result = provider.findRecord(obj);
                                        log.info(result.getStatus()); 
                                        if (result.getStatus().equals(ResultStatuses.OK)){
                                            log.info(obj.getType());
                                            result.getList().stream().forEach(e -> log.info(e));
                                        }
                                    }
                                } catch(IllegalArgumentException ex){
                                    log.error(ex);
                                    log.error("Incorrect type!");
                                } 
                            }
                            else log.info("-type is requeded!");
                        }                    
                    } 

                    if (arr[0].equals("create")){
                        if (arr.length > 1 && arr[1].equals("help")){
                            List<Option> optionsCreate = new ArrayList<Option>();
                            optionsCreate.add(options.getOption("objectType"));
                            optionsCreate.add(options.getOption("objectId"));
                            optionsCreate.add(options.getOption("description"));
                            log.info("Options for command create:");
                            optionsCreate.stream().forEach(e -> {
                                String str = "-" + e.getOpt() +  ", -" + e.getLongOpt();
                                if(e.getArgs() > 0) str += "\t<arg>";
                                else str += "\t";
                                str += "\t" + e.getDescription();
                                log.info(str);
                            });                            
                        }
                        else {
                            if(line.hasOption("objectType") && line.hasOption("objectId") && line.hasOption("description")){
                                try{
                                    Types type = Types.valueOf(line.getOptionValue("objectType").toUpperCase());
                                    Note note = new Note(); 
                                    note.setId(MyGenerator.generateId());
                                    note.setObjectType(type.toString());
                                    note.setObjectId(Long.parseLong(line.getOptionValue("objectId")));
                                    log.info("Enter description:");
                                    String desc = scan.nextLine();
                                    note.setDescription(desc);
                                    Result result = provider.addRecord(note);
                                    log.info(result.getStatus());
                                    if (!result.getStatus().equals(ResultStatuses.OK) && result.getMessage()!=null){
                                        log.info(result.getMessage());
                                    }
                                } catch (IllegalArgumentException ex){
                                    log.error(ex);
                                    log.error("Incorrect argument!");
                                }
                            }
                            else 
                                log.error("-objectType, -objectId and -description are requered!");
                        }                    
                    }

                    if (arr[0].equals("edit")){
                        if (arr.length > 1 && arr[1].equals("help")){
                            List<Option> optionsCreate = new ArrayList<Option>();
                            optionsCreate.add(options.getOption("id"));
                            optionsCreate.add(options.getOption("objectType"));
                            optionsCreate.add(options.getOption("objectId"));
                            optionsCreate.add(options.getOption("description"));
                            log.info("Options for command edit:");
                            optionsCreate.stream().forEach(e -> {
                                String str = "-" + e.getOpt() +  ", -" + e.getLongOpt();
                                if(e.getArgs() > 0) str += "\t<arg>";
                                else str += "\t";
                                str += "\t" + e.getDescription();
                                log.info(str);
                            });  
                        }
                        else {
                            if (line.hasOption("id")){
                                if(line.hasOption("desc") || line.hasOption("objecId") || line.hasOption("objectType")){
                                    try{
                                        Note note = new Note(Long.parseLong(line.getOptionValue("id")));
                                        Result result = provider.getRecordById(note);
                                        if (result.getStatus().equals(ResultStatuses.OK)){
                                            note = (Note)result.getList().stream().findFirst().get();
                                            if(line.hasOption("objectType")){
                                                Types type = Types.valueOf(line.getOptionValue("objectType").toUpperCase());
                                                note.setObjectType(type.toString());
                                            }
                                            if(line.hasOption("objectId"))
                                                note.setObjectId(Long.parseLong(line.getOptionValue("objectId")));
                                            if(line.hasOption("desc")){
                                                log.info("Enter description:");
                                                String desc = scan.nextLine();
                                                note.setDescription(desc);
                                            }
                                            result = provider.editRecord(note);
                                            log.info(result.getStatus());
                                            if (!result.getStatus().equals(ResultStatuses.OK) && result.getMessage()!=null){
                                                log.error(result.getMessage());
                                            }
                                        }  
                                        else {
                                            log.error(result.getStatus());
                                            if (result.getMessage() != null)
                                                log.error(result.getMessage());
                                        }
                                    } catch (IllegalArgumentException ex){
                                        log.error(ex);
                                        log.error("Incorrect argument!");
                                    }
                                }
                                else{
                                    log.error("-description, -objectId or -objectType is requered!");
                                }
                            }
                            else{
                                log.error("-id is requered!");
                            }
                        }                    
                    }

                    if (arr[0].equals("delete")){
                        if (arr.length > 1 && arr[1].equals("help")){
                            List<Option> optionsCreate = new ArrayList<Option>();
                            optionsCreate.add(options.getOption("id"));
                            log.info("Options for command delete:");
                            optionsCreate.stream().forEach(e -> {
                                String str = "-" + e.getOpt() +  ", -" + e.getLongOpt();
                                if(e.getArgs() > 0) str += "\t<arg>";
                                else str += "\t";
                                str += "\t" + e.getDescription();
                                log.info(str);
                            }); 
                        }
                        else {
                            if (line.hasOption("id")){
                                try{
                                    Note note = new Note(Long.parseLong(line.getOptionValue("id")));
                                    Result result = provider.deleteRecord(note);
                                    log.info(result.getStatus());
                                    if (!result.getStatus().equals(ResultStatuses.OK) && result.getMessage()!=null){
                                        log.error(result.getMessage());
                                    } 
                                } catch (IllegalArgumentException ex){
                                    log.error(ex);
                                    log.error("Incorrect argument!");
                                }
                            }
                            else{
                                log.error("-id is requered!");
                            }
                        }                    
                    }                
                    log.info("Enter command"); 
                } catch( ParseException exp ){
                    log.error( "Unexpected exception:" + exp.getMessage() );
                }
            }            
        } catch( ParseException exp ) {
            log.error( "Unexpected exception:" + exp.getMessage() );
        }        
    }
}

