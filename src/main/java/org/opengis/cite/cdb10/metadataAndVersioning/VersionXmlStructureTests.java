package org.opengis.cite.cdb10.metadataAndVersioning;

import java.io.IOException;

import org.opengis.cite.cdb10.util.metadataXml.VersionXml;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/**
 * Created by martin on 2016-09-06.
 */
public class VersionXmlStructureTests extends Capability2Tests {

    @Test
    public void verifyVersionXmlFileExists() {
        new VersionXml(path);
    }

    @Test
    public void verifyVersionXmlAgainstSchema() throws IOException, SAXException {
        new VersionXml(path).verifyXmlAgainstSchema();
    }

    @Test
    public void verifyVersionXmlHasSpecificationElement() {
        new VersionXml(path).verifyHasSpecificationElement();
    }

    @Test
    public void verifyVersionXmlSpecificationVersionIsValid() {
        new VersionXml(path).verifySpecificationVersionIsValid();
    }
}
