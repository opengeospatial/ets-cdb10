package org.opengis.cite.cdb10.metadataAndVersioning;

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
 * Created by martin on 2016-09-07.
 */
public class ConfigurationXmlStructureTests extends CommonFixture {

    @Test
    public void verifyConfigurationXmlFileExists() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Configuration.xml")),
                "Metadata directory should contain Configuration.xml file.");
    }

    @Test
    public void verifyConfigurationXmlAgainstSchema() throws IOException, SAXException {
        File xmlFile = Paths.get(path, "Metadata", "Configuration.xml").toFile();
        File xsdFile = Paths.get(path, "Metadata", "Schema", "Configuration.xsd").toFile();

        SchemaValidatorErrorHandler errorHandler = XMLUtils.validateXmlFileIsValid(xmlFile, xsdFile);

        if (!errorHandler.noErrors()) {
            Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
        }
    }
}
