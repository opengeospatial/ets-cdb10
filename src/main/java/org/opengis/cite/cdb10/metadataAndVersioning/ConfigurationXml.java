package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.opengis.cite.cdb10.util.XMLUtils;
import org.testng.Assert;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-20.
 */
public class ConfigurationXml {
    private File xmlFile;
    private File xsdFile;

    public ConfigurationXml(String path) {
        xmlFile = Paths.get(path, "Metadata", "Configuration.xml").toFile();
        xsdFile = Paths.get(path, "Metadata", "Schema", "Configuration.xsd").toFile();

        verifyXmlFileExists();
    }

    private void verifyXmlFileExists() {
        Assert.assertTrue(Files.exists(xmlFile.toPath()), "Metadata directory should contain Configuration.xml file.");
    }

    public void verifyAgainstSchema() throws IOException, SAXException {
        SchemaValidatorErrorHandler errorHandler = XMLUtils.validateXmlFileIsValid(xmlFile, xsdFile);

        if (!errorHandler.noErrors()) {
            Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
        }
    }
}
