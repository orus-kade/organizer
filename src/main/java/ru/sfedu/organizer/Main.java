
package ru.sfedu.organizer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;
import ru.sfedu.organizer.api.CsvDataProvider;
import ru.sfedu.organizer.api.DbDataProvider;
import ru.sfedu.organizer.api.IDataProvider;
import ru.sfedu.organizer.api.XmlDataProvider;
import ru.sfedu.organizer.model.*;


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
        options.addOption("f", "find", false, "find objects");
        options.addOption("id", true, "id of objects");
        options.addOption("t", "type", true, "type of objects");
        options.addOption("ttl", "title", true, "title of objects");
        options.addOption("txt", "text", true, "text of objects");
        options.addOption("n", "name", true, "name of human");
        options.addOption("sn", "surname", true, "surname of objects");
        options.addOption("pat", "patronymic", true, "patronymic of objects");
        options.addOption("v", "voice", true, "voice of singer");
        options.addOption("desc", "description", true, "note descrption");
        options.addOption("oid", "objectid", true, "object id");
        options.addOption("a", "add", false, "add note");
        options.addOption("e", "edit", false, "edit note");
        options.addOption("d", "delete", false, "delete note");
        options.addOption("all", false, "get all");
        options.addOption("h", "help", false, "show help");
        options.addOption("src", "source", true, "show help");
        
        HelpFormatter formatter = new HelpFormatter();
//        formatter.printHelp(
//                80,
//                "Organizer", 
//                "Options", 
//                options, 
//                "-- HELP --" 
//        );  
        Scanner scan = new Scanner(System.in);
        try{                
            CommandLine line = new BasicParser().parse(options, args);
            IDataProvider provider = new CsvDataProvider();
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
                        case "csv" : provider = new CsvDataProvider();
                            break;
                        case "xml" : provider = new XmlDataProvider();
                            break;
                        case "db" :  provider = new DbDataProvider();
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
                        case "cvs" : provider = new CsvDataProvider();
                            break;
                        case "xml" : provider = new XmlDataProvider();
                            break;
                    }
                    log.info("Source wa changed from \"database\" to default source \"" + defaultSource + "\"");
                }
            }
            
            if (line.hasOption("h")){
                formatter.printHelp(
                    80,
                    "Organizer", 
                    "Options", 
                    options, 
                    "-- HELP --" 
                );
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
                        //System.console().printf("gg");
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
                            log.info("Options for command find:");
                        }
                        else {
                            //log.info("find here");
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
                                                ((Aria)obj).setTitle(line.getOptionValue("title"));
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
                            else log.info("Type is requeded!");
                        }                    
                    } 

                    if (arr[0].equals("create")){
                        if (arr.length > 1 && arr[1].equals("help")){
                            log.info("Options for command find:");
                        }
                        else {
                            log.info("create here");
                        }                    
                    }

                    if (arr[0].equals("edit")){
                        if (arr.length > 1 && arr[1].equals("help")){
                            log.info("Options for command find:");
                        }
                        else {
                            log.info("edit here");
                        }                    
                    }

                    if (arr[0].equals("delete")){
                        if (arr.length > 1 && arr[1].equals("help")){
                            log.info("Options for command find:");
                        }
                        else {
                            log.info("delete here");
                        }                    
                    }                
                    log.info("Enter command");
//                if (line.hasOption( "find" )) {
//                    if (line.hasOption("type")){
//                        try{
//                            Types type = Types.valueOf(line.getOptionValue("type").toUpperCase());
//                            Generic obj = new Aria();
//                            switch (type){
//                                case ARIA : obj = new Aria();
//                                    break;
//                                case COMPOSER : obj = new Composer();
//                                    break;
//                                case LIBRETTO : obj = new Libretto();;
//                                    break;
//                                case OPERA : obj = new Opera();
//                                    break;
//                                case SINGER : obj = new Singer();
//                                    break;
//                                case AUTHOR : obj = new Author();
//                                    break;
//                                case NOTE : obj = new Note();
//                                    break;
//                            }
//                            if (line.hasOption("all")){
//                                Result result = provider.getAllRecords(obj);
//                                log.info(result.getStatus()); 
//                                if (result.getStatus().equals(ResultStatuses.OK)){
//                                    log.info(obj.getType());
//                                    result.getList().stream().forEach(e -> log.info(e));
//                                }
//                            }
//                            else{
//                                if(obj.getType().equals(Types.AUTHOR) || obj.getType().equals(Types.COMPOSER) || obj.getType().equals(Types.SINGER)){
//                                    if(line.hasOption("name")){
//                                        ((Human)obj).setName(line.getOptionValue("name"));
//                                    }
//                                    if(line.hasOption("surname")){
//                                        ((Human)obj).setSurname(line.getOptionValue("surname"));
//                                    }
//                                    if(line.hasOption("patronymic")){
//                                        ((Human)obj).setPatronymic(line.getOptionValue("patronymic"));
//                                    }
//                                    if(line.hasOption("voice") && obj.getType().equals(Types.SINGER)){
//                                        ((Singer)obj).setVoice(line.getOptionValue("voice"));
//                                    }
//                                }
//                                if(obj.getType().equals(Types.ARIA)){
//                                    if (line.hasOption("title")){
//                                        ((Aria)obj).setTitle(line.getOptionValue("title"));
//                                    }
//                                    if (line.hasOption("text")){
//                                        ((Aria)obj).setText(line.getOptionValue("text"));
//                                    }
//                                }
//                                if(obj.getType().equals(Types.OPERA)){
//                                    if (line.hasOption("title")){
//                                        ((Opera)obj).setTitle(line.getOptionValue("title"));
//                                    }
//                                }
//                                Result result = provider.findRecord(obj);
//                                log.info(result.getStatus()); 
//                                if (result.getStatus().equals(ResultStatuses.OK)){
//                                    log.info(obj.getType());
//                                    result.getList().stream().forEach(e -> log.info(e));
//                                }
//                            }
//                        } catch(IllegalArgumentException ex){
//                            log.info(ex);
//                        }                
//                    }
//                    else log.info("Type is required!");
//                }                
//                else log.info("ololo");   
                } catch( ParseException exp ){
                    log.error( "Unexpected exception:" + exp.getMessage() );
                }
            }            
        } catch( ParseException exp ) {
            log.error( "Unexpected exception:" + exp.getMessage() );
        }        
    }
}

