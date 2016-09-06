package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by martin on 2016-09-03.
 */
public class DefaultsXmlStructureTests extends CommonFixture {
    public void verifyDefaultsXmlFileExist() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Defaults.xml")),
                "Metadata directory should contain Defaults.xml file.");
    }

    public void verifyDefaultsXmlFileIsValid() {
        SchemaValidatorErrorHandler errorHandler = new SchemaValidatorErrorHandler();
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(Paths.get(path, "Metadata", "Schema", "Defaults.xsd").toFile());

            Validator validator = schema.newValidator();
            validator.setErrorHandler(errorHandler);

            validator.validate(new StreamSource(Paths.get(path, "Metadata", "Defaults.xml").toFile()));

            if (!errorHandler.noErrors()) {
                Assert.fail("Defaults.xml does not contain valid XML. Errors: " + errorHandler.getMessages());
            }
        } catch (SAXException | IOException | NullPointerException | IllegalArgumentException ex) {

        }
    }

    public void verifyDefaultsXmlElementR_W_TypeHasValidValues() {
        NodeList nodeList = getNodeList("//R_W_Type");

        ArrayList<String> values = new ArrayList<>();
        List<String> VALID_VALUES = Arrays.asList("W", "R");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            values.add(currentItem.getTextContent());
        }

        for (String value : values) {
            Assert.assertTrue(VALID_VALUES.contains(value),
                    String.format("Defaults.xml element R_W_Type should have a value of R or W. Value %s is not valid.", value));
        }
    }

    private NodeList getNodeList(String element) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(String.valueOf(Paths.get(path, "Metadata", "Defaults.xml")));
            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression exp = xPath.compile(element);
            return (NodeList) exp.evaluate(doc, XPathConstants.NODESET);
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            return null;
        }
    }
}
