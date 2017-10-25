
package ru.sfedu.organizer;

import org.apache.log4j.Logger;

/**
 *
 * @author orus-kade
 */
public class Main {
    private static Client log;
    private static Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        log = new Client();
        log.logBasicSystemInfo();
        logger.info("ssssss");
    }
}
