package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@SuppressWarnings("CallToPrintStackTrace")
public class AppLogger {
    private static final Logger logger;

    static {
        logger = Logger.getLogger("ProjetoHotelLogger");
        // Remove o ConsoleHandler padrão
        logger.setUseParentHandlers(false);
        try {
            String logDir = "logs";
            Files.createDirectories(Paths.get(logDir));
            String day = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String logFile = logDir + "/hotel_" + day + ".log";
            FileHandler fh = new FileHandler(logFile, true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void error(String msg, Throwable t) {
        logger.log(Level.SEVERE, msg, t);
    }
}