package org.opengis.cite.cdb10.metadataAndVersioning;

import org.testng.Assert;

import java.nio.file.Files;

/**
 * Created by martin on 2016-11-10.
 */
public class VendorAttributesXml extends MetadataXmlFile {
    public VendorAttributesXml(String path) {
        super(path, "Vendor_Attributes.xml", "Vendor_Attributes.xsd");
    }

    public void verifyVendorAttributesXsdFileExists() {
            Assert.assertTrue(Files.exists(xsdFile.toPath()),
                    "If Vendor_Attributes.xml exists there should be a Vendor_Attributes.xsd in the Schema folder");
    }
}
