package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
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
import java.util.Objects;

/**
 * Created by martin on 2016-09-01.
 */
public class LightsXmlStructureTests extends CommonFixture {
    public void verifyLightsXmlFileExist() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Lights.xml")), "Metadata should contain Lights.xml file.");
    }

    public void verifyLightsXmlHasUniqueCodes() {
        try {
            Document doc;
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(String.valueOf(Paths.get(path, "Metadata", "Lights.xml")));
            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression exp = xPath.compile("//Light");
            NodeList nl = (NodeList) exp.evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < nl.getLength(); i++) {
                Node currentItem = nl.item(i);
                String key = currentItem.getAttributes().getNamedItem("code").getNodeValue();

                int codeCount = 0;
                for (int j = 0; j < nl.getLength(); j++) {
                    if (Objects.equals(key, nl.item(j).getAttributes().getNamedItem("code").getNodeValue())) {
                        codeCount++;
                    }
                }
                Assert.assertEquals(codeCount, 1, "Lights.xml element Light should have a unique codes.");
            }
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            ex.printStackTrace();
        }
    }

    public void verifyLightsXmlCodesAreWithinRange() {
        try {
            Document doc;
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(String.valueOf(Paths.get(path, "Metadata", "Lights.xml")));
            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression exp = xPath.compile("//Light");
            NodeList nl = (NodeList) exp.evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < nl.getLength(); i++) {
                Node currentItem = nl.item(i);
                int key = Integer.parseInt(currentItem.getAttributes().getNamedItem("code").getNodeValue());

                Assert.assertTrue((key >= 0) && (key <= 9999), "Lights.xml element Light should have a code from 0 - 9999 inclusive.");
            }
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            ex.printStackTrace();
        }
    }
}
