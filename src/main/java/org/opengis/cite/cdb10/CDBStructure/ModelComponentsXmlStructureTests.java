package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-02.
 */
public class ModelComponentsXmlStructureTests extends CommonFixture {

    @Test
    public void verifyModelComponentsXmlFileExist() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Model_Components.xml")),
                "Metadata directory should contain Model_Components.xml file.");
    }

    @Test
    public void verifyModelComponentsXmlFileIsValid() throws IOException, SAXException {
        File xmlFile = Paths.get(path, "Metadata", "Model_Components.xml").toFile();
        File xsdFile = Paths.get(path, "Metadata", "Schema", "Model_Components.xsd").toFile();

        SchemaValidatorErrorHandler errorHandler = XmlValidator.validateXmlFileIsValid(xmlFile, xsdFile);

        if (!errorHandler.noErrors()) {
            Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
        }
    }
}
