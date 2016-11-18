package org.opengis.cite.cdb10.metadataAndVersioning;

import org.testng.Assert;

import java.nio.file.Files;

/**
 * Created by martin on 2016-11-18.
 */
public class GeomaticsAttributesXml extends MetadataXmlFile {
    public GeomaticsAttributesXml(String path) {
        super(path, "Geomatics_Attributes.xml", "Geomatics_Attributes.xsd");
    }

    public void verifyGeomaticsAttributesXsdFileExists() {
        Assert.assertTrue(Files.exists(xsdFile.toPath()),
                "If Geomatics_Attributes.xml exists there should be a Geomatics_Attributes.xsd in the Schema folder");
    }
}
