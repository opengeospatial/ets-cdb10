package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by martin on 2016-09-01.
 */
public class LightsXmlStructureTests extends CommonFixture {

    @Test
    public void verifyLightsXmlFileExist() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Lights.xml")), "Metadata directory should contain Lights.xml file.");
    }

    @Test
    public void verifyLightsXmlFileHasValidXml() {
        Assert.assertTrue(XmlUtilities.getNodeList("//Light", Paths.get(path, "Metadata", "Lights.xml")) != null, "Lights.xml does not contain valid XML.");
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
                    String.format("Lights.xml element Light should have unique codes. Code %s is not unique.", code));
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
