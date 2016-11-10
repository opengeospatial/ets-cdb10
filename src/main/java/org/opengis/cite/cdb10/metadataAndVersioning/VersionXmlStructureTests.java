package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by martin on 2016-09-06.
 */
public class VersionXmlStructureTests extends CommonFixture {

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
