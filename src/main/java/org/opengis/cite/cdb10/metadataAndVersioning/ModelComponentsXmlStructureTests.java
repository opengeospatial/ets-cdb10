package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.ModelComponentsXml;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by martin on 2016-09-02.
 */
public class ModelComponentsXmlStructureTests extends Capability2Tests {

    @Test
    public void verifyModelComponentsXmlFileExists() {
        new ModelComponentsXml(path);
    }

    @Test
    public void verifyModelComponentsXmlAgainstSchema() throws IOException, SAXException {
        new ModelComponentsXml(path).verifyXmlAgainstSchema();
    }
}
