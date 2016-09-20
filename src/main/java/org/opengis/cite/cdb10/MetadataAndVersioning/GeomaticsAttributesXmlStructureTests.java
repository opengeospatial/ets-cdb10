package org.opengis.cite.cdb10.MetadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.opengis.cite.cdb10.util.XmlUtilities;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-10.
 */
public class GeomaticsAttributesXmlStructureTests extends CommonFixture {

    @Test
    public void verifyGeomaticsAttributesXsdFileExists() {
        if (geomaticsAttributesXmlExists()) {
            Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Schema", "Geomatics_Attributes.xsd")),
                    "If Geomatics_Attributes.xml exists there should be a Geomatics_Attributes.xsd in the Schema folder");
        }
    }

    @Test
    public void verifyGeomaticsAttributesXmlAgainstSchema() throws IOException, SAXException {
        if (geomaticsAttributesXmlExists() && geomaticsAttributesXsdExists()) {
            File xmlFile = Paths.get(path, "Metadata", "Geomatics_Attributes.xml").toFile();
            File xsdFile = Paths.get(path, "Metadata", "Schema", "Geomatics_Attributes.xsd").toFile();

            SchemaValidatorErrorHandler errorHandler = XmlUtilities.validateXmlFileIsValid(xmlFile, xsdFile);

            if (!errorHandler.noErrors()) {
                Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
            }
        }
    }

    private boolean geomaticsAttributesXmlExists() {
        return Paths.get(path, "Metadata", "Geomatics_Attributes.xml").toFile().exists();
    }

    private boolean geomaticsAttributesXsdExists() {
        return Paths.get(path, "Metadata", "Schema", "Geomatics_Attributes.xsd").toFile().exists();
    }
}
