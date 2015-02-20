import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingStuff {

    private static Logger log = LoggerFactory.getLogger(LoggingStuff.class);

    public static void main(String[] args) {
        log.info("some stuff to log");
        log.info("some stuff to log with value {}", "1");
        log.error("here is an exception", new Exception("some error"));
    }
}
