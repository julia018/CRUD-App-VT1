import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static final String XML_FILE = "CoffeeList.xml";
    public static final String SCHEMA_FILE = "schema.xsd";

    public static void main(String[] args) {


        String dbUrl="jdbc:mysql://localhost/orders?user=root&password=YuliaKisliuk2000!&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try {
            Connection conn = DriverManager.getConnection(dbUrl);
            /*PreparedStatement stmt = conn
                    .prepareStatement("INSERT INTO coffee(" +
                            " id, container, volume, trademark, milk, sugar) " +
                            "VALUES(0, 'TAKEAWAY', 'BIG', 'NESCAFE', 0, 0) ");*/
            //stmt.execute();
            boolean valid = validate(XML_FILE, SCHEMA_FILE);

            System.out.printf("%s validation = %b.", XML_FILE, valid);
            conn.close();

        } catch (SQLException e) {
           e.printStackTrace();
        }


    }

    private static boolean validate(String xmlFile, String schemaFile) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(new File(schemaFile));

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlFile)));
            return true;
        } catch (SAXException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
