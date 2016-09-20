package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by martin on 2016-09-03.
 */
public class DefaultsXmlStructureTests extends CommonFixture {
    @Test
    public void verifyDefaultsXmlFileExists() {
        new DefaultsXml(path);
    }

    @Test
    public void verifyDefaultsXmlAgainstSchema() throws IOException, SAXException {
        new DefaultsXml(path).verifyXmlAgainstSchema();
    }

    @Test
    public void verifyDefaultsXmlElementR_W_TypeHasValidValues() {
        new DefaultsXml(path).verifyElementR_W_TypeHasValidValues();
    }

    @Test
    public void verifyDefaultsXmlNameIsUniqueForEachDataset() {
        new DefaultsXml(path).verifyNameIsUniqueForEachDataset();
    }

    @Test
    public void verifyDefaultsXmlElementTypeHasValidValue() {
        new DefaultsXml(path).verifyElementTypeHasValidValue();
    }
}
