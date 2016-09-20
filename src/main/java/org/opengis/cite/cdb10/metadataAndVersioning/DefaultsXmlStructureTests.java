package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
