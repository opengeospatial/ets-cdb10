package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.opengis.cite.cdb10.util.XMLUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

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
