package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-02.
 */
public class ModelComponentsXmlStructureTests extends CommonFixture {
    public void verifyModelComponentsXmlFileExist() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Model_Components.xml")),
                "Metadata directory should contain Model_Components.xml file.");
    }

    @Test
    public void verifyModelComponentsXmlFileHasValidXml() {
        Assert.assertTrue(getNodeList() != null, "Model_Components.xml does not contain valid XML.");
    }

    private NodeList getNodeList() {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(String.valueOf(Paths.get(path, "Metadata", "Model_Components.xml")));
            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression exp = xPath.compile("//Light");
            return (NodeList) exp.evaluate(doc, XPathConstants.NODESET);
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            return null;
        }
    }
}
