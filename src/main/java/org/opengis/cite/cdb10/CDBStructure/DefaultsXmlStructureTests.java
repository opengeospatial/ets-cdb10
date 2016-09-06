package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
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
    public void verifyDefaultsXmlFileExist() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Defaults.xml")),
                "Metadata directory should contain Defaults.xml file.");
    }

    public void verifyDefaultsXmlFileIsValid() throws IOException, SAXException {
        File xmlFile = Paths.get(path, "Metadata", "Defaults.xml").toFile();
        File xsdFile = Paths.get(path, "Metadata", "Schema", "Defaults.xsd").toFile();

        SchemaValidatorErrorHandler errorHandler = XmlValidator.validateXmlFileIsValid(xmlFile, xsdFile);

        if (!errorHandler.noErrors()) {
            Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
        }
    }

    @Test
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
                    String.format("Defaults.xml element R_W_Type should have a value of R or W. Value '%s' is not valid.", value));
        }
    }

    @Test
    public void verifyDefaultsXmlNameIsUniqueForEachDataset() {
        for (String datasetValue : getDatasetValues()) {
            assertNamesAreUnique(datasetValue);
        }
    }

    @Test
    public void verifyDefaultsXmlElementTypeHasValidValue() {
        NodeList nodeList = getNodeList("//Type");

        ArrayList<String> values = new ArrayList<>();
        List<String> VALID_VALUES = Arrays.asList("string", "integer", "float");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            values.add(currentItem.getTextContent());
        }

        for (String value : values) {
            Assert.assertTrue(VALID_VALUES.contains(value),
                    String.format("Defaults.xml element Type should have a value of 'float', 'integer' and 'string'. Value '%s' is not valid.", value));
        }
    }

    private HashSet<String> getDatasetValues() {
        NodeList datasetNodes = getNodeList("//Dataset");

        HashSet<String> datasetValues = new HashSet<>();

        for (int i = 0; i < datasetNodes.getLength(); i++) {
            Node currentItem = datasetNodes.item(i);
            datasetValues.add(currentItem.getTextContent());
        }
        return datasetValues;
    }

    private ArrayList<String> collectNameNodesWithDatasetValue(String datasetValue) {
        NodeList nameNodes = getNodeList("//Default_Value[Dataset/text() = \"" + datasetValue + "\"]/Name");

        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < nameNodes.getLength(); i++) {
            names.add(nameNodes.item(i).getTextContent());
        }
        return names;
    }

    private void assertNamesAreUnique(String datasetValue) {
        ArrayList<String> names = collectNameNodesWithDatasetValue(datasetValue);

        for (String name : names) {
            Assert.assertEquals(Collections.frequency(names, name), 1,
                    String.format("Defaults.xml element Name should be unique under each Dataset. '%s' is not unique.", name));
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
