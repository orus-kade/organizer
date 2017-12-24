package ru.sfedu.organizer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import static ru.sfedu.organizer.Constants.FILE_PATH;
import ru.sfedu.organizer.api.DbDataProvider;

/**
 * Configuration utility. Allows to get configuration properties from the
 * default configuration file
 *
 * @author Boris Jmailov
 */
public class ConfigurationUtil {
    private static final Logger log = Logger.getLogger(ConfigurationUtil.class);
    private static final String DEFAULT_CONFIG_PATH = "enviroment.properties";
    private String configPath;
    private static final Properties configuration = new Properties();

    /**
     * Hides default constructor
     */
//    public ConfigurationUtil() {
//        String path = System.getProperty(FILE_PATH);      
//        if (path != null){
//            configPath = path + "\\enviroment.properties";
//        }
//        else {
//            configPath = DEFAULT_CONFIG_PATH;
//        }
//    }
    
    public ConfigurationUtil(String path) {       
        if (path != null){
            configPath = path + "\\enviroment.properties";
        }
        else {
            configPath = DEFAULT_CONFIG_PATH;
        }
    }   
   

    
    private Properties getConfiguration() throws IOException {
        if(configuration.isEmpty()){
            loadConfiguration();
        }
        return configuration;
    }

    /**
     * Loads configuration from <code>DEFAULT_CONFIG_PATH</code>
     * @throws IOException In case of the configuration file read failure
     */
    private void loadConfiguration() throws IOException{
        File file = new File(configPath);
        InputStream in = new FileInputStream(file);
        //InputStream in = DEFAULT_CONFIG_PATH.getClass().getResourceAsStream(DEFAULT_CONFIG_PATH);
        try {
            configuration.load(in);
        } catch (IOException ex) {
            throw new IOException(ex);
        } finally{
            in.close();
        }
    }
    /**
     * Gets configuration entry value
     * @param key Entry key
     * @return Entry value by key
     * @throws IOException In case of the configuration file read failure
     */
    public String getConfigurationEntry(String key) throws IOException{
        return getConfiguration().getProperty(key);
    }
    
}
