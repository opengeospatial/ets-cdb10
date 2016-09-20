package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by martin on 2016-09-01.
 */
public class LightsXmlStructureTests extends CommonFixture {

    @Test
    public void verifyLightsXmlFileExists() {
        new LightsXml(path);
    }

    @Test
    public void verifyLightsXmlFileAgainstSchema() throws IOException, SAXException {
        new LightsXml(path).verifyXmlAgainstSchema();
    }

    @Test
    public void verifyLightsXmlHasUniqueCodes() {
        new LightsXml(path).verifyHasUniqueCodes();
    }

    @Test
    public void verifyLightsXmlCodesAreWithinRange() {
        new LightsXml(path).verifyCodesAreWithinRange();
    }
}
