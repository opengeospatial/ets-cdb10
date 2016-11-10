package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-09.
 */
public class VendorAttributesXmlStructureTests extends CommonFixture {

    @Test
    public void verifyVendorAttributesXmlFileExists() {
        new VendorAttributesXml(path);
    }

    @Test
    public void verifyVendorAttributesXsdFileExists() {
        new VendorAttributesXml(path).verifyVendorAttributesXsdFileExists();
    }

    @Test
    public void verifyVendorAttributesXmlAgainstSchema() throws IOException, SAXException {
        new VendorAttributesXml(path).verifyXmlAgainstSchema();
    }
}
