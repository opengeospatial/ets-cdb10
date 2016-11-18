package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by martin on 2016-09-10.
 */
public class GeomaticsAttributesXmlStructureTests extends CommonFixture {

    @Test
    public void verifyGeomaticsAttributesXmlFileExists() {
        new GeomaticsAttributesXml(path);
    }

    @Test
    public void verifyGeomaticsAttributesXsdFileExists() {
        new GeomaticsAttributesXml(path).verifyGeomaticsAttributesXsdFileExists();
    }

    @Test
    public void verifyGeomaticsAttributesXmlAgainstSchema() throws IOException, SAXException {
        new GeomaticsAttributesXml(path).verifyXmlAgainstSchema();
    }
}
