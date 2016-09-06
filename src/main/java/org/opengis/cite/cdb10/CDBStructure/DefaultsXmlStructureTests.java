package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.testng.Assert;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-03.
 */
public class DefaultsXmlStructureTests extends CommonFixture {
    public void verifyDefaultsXmlFileExist() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Defaults.xml")),
                "Metadata directory should contain Defaults.xml file.");
    }

    public void verifyDefaultsXmlFileIsValid() {
        SchemaValidatorErrorHandler errorHandler = new SchemaValidatorErrorHandler();
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(Paths.get(path, "Metadata", "Schema", "Defaults.xsd").toFile());

            Validator validator = schema.newValidator();
            validator.setErrorHandler(errorHandler);

            validator.validate(new StreamSource(Paths.get(path, "Metadata", "Defaults.xml").toFile()));

            if (!errorHandler.noErrors()) {
                Assert.fail("Defaults.xml does not contain valid XML. Errors: " + errorHandler.getMessages());
            }
        } catch ( SAXException | IOException | NullPointerException | IllegalArgumentException ex) {

        }
    }
}
