package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by martin on 2016-09-07.
 */
public class ConfigurationXmlStructureTests extends CommonFixture {

    @Test
    public void verifyConfigurationXmlFileExists() {
        new ConfigurationXml(path);
    }

    @Test
    public void verifyConfigurationXmlAgainstSchema() throws IOException, SAXException {
        new ConfigurationXml(path).verifyXmlAgainstSchema();
    }
}
