import org.jaxen.XPath;
import org.jaxen.jdom.JDOMXPath;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class JaxenXPathTest {

    private static Logger log = LoggerFactory.getLogger(JaxenXPathTest.class);

    public static void main(String[] args) {

        try {

            ClassLoader classLoader = JaxenXPathTest.class.getClassLoader();
            File xmlFile = new File(classLoader.getResource("countries.xml").getFile());

            SAXBuilder saxBuilder = new SAXBuilder();

            Document dom4jDocument = saxBuilder.build(xmlFile);
            XPath path = new JDOMXPath("countries/*/name");
            List<Element> results = path.selectNodes(dom4jDocument);

            results.forEach(
                    element -> log.info(element.getText())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
