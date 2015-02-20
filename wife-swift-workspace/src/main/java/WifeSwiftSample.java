import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class WifeSwiftSample {

    private static Logger log =
            LoggerFactory.getLogger(WifeSwiftSample.class);

    public static void main(String[] args) throws IOException {

        String fileName =
                WifeSwiftSample.class.getClassLoader()
                        .getResource("mt103.txt")
                        .getFile();

        SwiftParser parser = new SwiftParser();
		parser.setReader(new FileReader(fileName));

        SwiftMessage msg = parser.message();

        MT103 mt = new MT103(msg);

        log.info("Sender: " + mt.getSender());
        log.info("Receiver: " + mt.getReceiver());
        log.info("Reference: " + mt.getField20().getValue());

        Field32A f = mt.getField32A();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        log.info("Value Date: " + sdf.format(f.getDateAsCalendar().getTime()));
        log.info("Amount: " + f.getCurrency() + " " + f.getAmount());
    }
}
