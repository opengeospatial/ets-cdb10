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
public abstract class MetadataXmlFile {
    protected File xmlFile;
    protected File xsdFile;

    public MetadataXmlFile(String path, String xmlFileName, String xsdFileName) {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata")), String.format("Metadata directory is missing."));

        xmlFile = Paths.get(path, "Metadata", xmlFileName).toFile();
        xsdFile = Paths.get(path, "Metadata", "Schema", xsdFileName).toFile();

        verifyXmlFileExists();
    }

    public void verifyXmlAgainstSchema() throws IOException, SAXException {
        SchemaValidatorErrorHandler errorHandler = XMLUtils.validateXmlFileIsValid(xmlFile, xsdFile);

        if (!errorHandler.noErrors()) {
            Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
        }
    }

    private void verifyXmlFileExists() {
        Assert.assertTrue(Files.exists(xmlFile.toPath()), String.format("Metadata directory should contain %s file.", xmlFile.getName()));
        Assert.assertTrue(Files.exists(xsdFile.toPath()), String.format("Metadata directory should contain %s file.", xsdFile.getName()));
    }

}
