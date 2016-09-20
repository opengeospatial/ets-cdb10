package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.util.XMLUtils;
import org.testng.Assert;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by martin on 2016-09-20.
 */
public class LightsXml extends MetadataXmlFile {
    public LightsXml(String path) {
        super(path, "Lights.xml", "Lights.xsd");
    }

    public void verifyHasUniqueCodes() {
        NodeList nodeList = XMLUtils.getNodeList("//Light", xmlFile.toPath());

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

    public void verifyCodesAreWithinRange() {
        NodeList nodeList = XMLUtils.getNodeList("//Light", xmlFile.toPath());

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            int key = Integer.parseInt(currentItem.getAttributes().getNamedItem("code").getNodeValue());

            Assert.assertTrue((key >= 0) && (key <= 9999), "Lights.xml element Light should have a code from 0 - 9999 inclusive.");
        }
    }
}
