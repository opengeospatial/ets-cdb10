package org.opengis.cite.cdb10.MetadataAndVersioning;

import org.opengis.cite.cdb10.CDBStructure.XmlUtilities;
import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
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
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Lights.xml")), "Metadata directory should contain Lights.xml file.");
    }

    @Test
    public void verifyLightsXmlFileAgainstSchema() throws IOException, SAXException {
        File xmlFile = Paths.get(path, "Metadata", "Lights.xml").toFile();
        File xsdFile = Paths.get(path, "Metadata", "Schema", "Lights.xsd").toFile();

        SchemaValidatorErrorHandler errorHandler = XmlUtilities.validateXmlFileIsValid(xmlFile, xsdFile);

        if (!errorHandler.noErrors()) {
            Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
        }
    }

    @Test
    public void verifyLightsXmlHasUniqueCodes() {
        NodeList nodeList = XmlUtilities.getNodeList("//Light", Paths.get(path, "Metadata", "Lights.xml"));

        ArrayList<String> codes = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            codes.add(currentItem.getAttributes().getNamedItem("code").getNodeValue());
        }

        for (String code : codes) {
            Assert.assertEquals(Collections.frequency(codes, code), 1,
                    String.format("Lights.xml element Light should have unique codes. Code '%s' is not unique.", code));
        }
    }

    @Test
    public void verifyLightsXmlCodesAreWithinRange() {
        NodeList nodeList = XmlUtilities.getNodeList("//Light", Paths.get(path, "Metadata", "Lights.xml"));

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            int key = Integer.parseInt(currentItem.getAttributes().getNamedItem("code").getNodeValue());

            Assert.assertTrue((key >= 0) && (key <= 9999), "Lights.xml element Light should have a code from 0 - 9999 inclusive.");
        }
    }

}
