import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XStreamSerializationSample {

    private static Logger log =
            LoggerFactory.getLogger(XStreamSerializationSample.class);

    public static void main(String[] args) {
        XStream xstream = getXStream();
        Person joe = getPerson();

        String xml = xstream.toXML(joe);
        log.info("\n" + xml);

        Person newJoe = (Person)xstream.fromXML(xml);

        log.info("newJoe first name: {}",
                newJoe.getFirstname()
        );
        log.info("newJoe last name: {}",
                newJoe.getLastname()
        );
        log.info("newJoe phone number: {} {}",
                newJoe.getPhone().getCode(),
                newJoe.getPhone().getNumber()
        );
        log.info("newJoe fax number: {} {}",
                newJoe.getFax().getCode(),
                newJoe.getFax().getNumber()
        );
    }

    private static XStream getXStream() {
        XStream xstream = new XStream();

        xstream.alias("person", Person.class);
        xstream.alias("phonenumber", PhoneNumber.class);

        return xstream;
    }

    private static Person getPerson() {
        Person joe = new Person("Joe", "Walnes");

        joe.setPhone(new PhoneNumber(123, "1234-456"));
        joe.setFax(new PhoneNumber(123, "9999-999"));

        return joe;
    }
}
