
package ru.sfedu.organizer;

import java.io.IOException;
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

    static void logBasicSystemInfo() {
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
    
    public static void main(String[] args) {        
        log.debug("Main[0]: starting application.........");        
        //logBasicSystemInfo();
        
        
        
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
        options.addOption("exit", false, "exit programm");
        options.addOption("all", false, "get all");
        
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(
                80,
                "Organizer", 
                "Options", 
                options, 
                "-- HELP --" 
        );
        
        Scanner scan = new Scanner(System.in);
        try{ 
            String dataProvider = "csv";
            IDataProvider provider = new CsvDataProvider();;
            switch (dataProvider){
                case "csv" : provider = new CsvDataProvider();
                    break;
                case "xml" : provider = new XmlDataProvider();
                    break;
                case "db" : try {provider = new DbDataProvider();
                } catch (IOException ex) {
                    log.error(ex);
                }
                    break;
            }                
        
            while(true){
                String[] arr = scan.nextLine().split(" ");                
                CommandLine line = new BasicParser().parse(options, arr); 
                if (line.hasOption("exit")) System.exit(0);  
                if (line.hasOption( "find" )) {
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
                            else{
                                if(obj.getType().equals(Types.AUTHOR) || obj.getType().equals(Types.COMPOSER) || obj.getType().equals(Types.SINGER)){
                                    if(line.hasOption("name")){
                                        ((Human)obj).setName(line.getOptionValue("name"));
                                    }
                                    if(line.hasOption("surname")){
                                        ((Human)obj).setSurname(line.getOptionValue("surname"));
                                    }
                                    if(line.hasOption("patronymic")){
                                        ((Human)obj).setPatronymic(line.getOptionValue("patronymic"));
                                    }
                                    if(line.hasOption("voice") && obj.getType().equals(Types.SINGER)){
                                        ((Singer)obj).setVoice(line.getOptionValue("voice"));
                                    }
                                }
                                if(obj.getType().equals(Types.ARIA)){
                                    if (line.hasOption("title")){
                                        ((Aria)obj).setTitle(line.getOptionValue("title"));
                                    }
                                    if (line.hasOption("text")){
                                        ((Aria)obj).setText(line.getOptionValue("text"));
                                    }
                                }
                                if(obj.getType().equals(Types.OPERA)){
                                    if (line.hasOption("title")){
                                        ((Opera)obj).setTitle(line.getOptionValue("title"));
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
                            log.info(ex);
                        }                
                    }
                    else log.info("Type is required!");
                }                
            else log.info("ololo");            
            }            
        } catch( ParseException exp ) {
            System.out.println( "Unexpected exception:" + exp.getMessage() );
        }        
    }
}

