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
 * Created by martin on 2016-09-09.
 */
public class VendorAttributesXmlStructureTests extends CommonFixture {

    @Test
    public void verifyVendorAttributesXsdFileExists() {
        if (vendorAttributesXmlExists()) {
            Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Schema", "Vendor_Attributes.xsd")),
                    "If Vendor_Attributes.xml exists there should be a Vendor_Attributes.xsd in the Schema folder");
        }
    }

    @Test
    public void verifyVendorAttributesXmlAgainstSchema() throws IOException, SAXException {
        if (vendorAttributesXmlExists() && vendorAttributesXsdExists()) {
            File xmlFile = Paths.get(path, "Metadata", "Vendor_Attributes.xml").toFile();
            File xsdFile = Paths.get(path, "Metadata", "Schema", "Vendor_Attributes.xsd").toFile();

            SchemaValidatorErrorHandler errorHandler = XMLUtils.validateXmlFileIsValid(xmlFile, xsdFile);

            if (!errorHandler.noErrors()) {
                Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
            }
        }
    }

    private boolean vendorAttributesXmlExists() {
        return Paths.get(path, "Metadata", "Vendor_Attributes.xml").toFile().exists();
    }

    private boolean vendorAttributesXsdExists() {
        return Paths.get(path, "Metadata", "Schema", "Vendor_Attributes.xsd").toFile().exists();
    }
}
