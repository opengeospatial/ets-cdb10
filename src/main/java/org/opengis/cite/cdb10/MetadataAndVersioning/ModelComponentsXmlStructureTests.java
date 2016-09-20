package org.opengis.cite.cdb10.MetadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.opengis.cite.cdb10.util.XMLUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-02.
 */
public class ModelComponentsXmlStructureTests extends CommonFixture {

    @Test
    public void verifyModelComponentsXmlFileExists() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Model_Components.xml")),
                "Metadata directory should contain Model_Components.xml file.");
    }

    @Test
    public void verifyModelComponentsXmlAgainstSchema() throws IOException, SAXException {
        File xmlFile = Paths.get(path, "Metadata", "Model_Components.xml").toFile();
        File xsdFile = Paths.get(path, "Metadata", "Schema", "Model_Components.xsd").toFile();

        SchemaValidatorErrorHandler errorHandler = XMLUtils.validateXmlFileIsValid(xmlFile, xsdFile);

        if (!errorHandler.noErrors()) {
            Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
        }
    }
}
