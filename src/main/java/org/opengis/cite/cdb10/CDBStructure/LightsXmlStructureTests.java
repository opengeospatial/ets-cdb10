package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by martin on 2016-09-01.
 */
public class LightsXmlStructureTests extends CommonFixture {

    @Test
    public void verifyLightsXmlFileExist() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Lights.xml")), "Metadata should contain Lights.xml file.");
    }

    @Test
    public void verifyLightsXmlHasUniqueCodes() {
        NodeList nodeList = getNodeList();

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
        NodeList nodeList = getNodeList();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            int key = Integer.parseInt(currentItem.getAttributes().getNamedItem("code").getNodeValue());

            Assert.assertTrue((key >= 0) && (key <= 9999), "Lights.xml element Light should have a code from 0 - 9999 inclusive.");
        }
    }

    private NodeList getNodeList() {
        try {
            Document doc;
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(String.valueOf(Paths.get(path, "Metadata", "Lights.xml")));
            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression exp = xPath.compile("//Light");
            return (NodeList) exp.evaluate(doc, XPathConstants.NODESET);
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
