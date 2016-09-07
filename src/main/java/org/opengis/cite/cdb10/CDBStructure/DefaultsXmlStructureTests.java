package org.opengis.cite.cdb10.CDBStructure;

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
import java.util.*;

/**
 * Created by martin on 2016-09-03.
 */
public class DefaultsXmlStructureTests extends CommonFixture {
    @Test
    public void verifyDefaultsXmlFileExists() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Defaults.xml")),
                "Metadata directory should contain Defaults.xml file.");
    }

    @Test
    public void verifyDefaultsXmlAgainstSchema() throws IOException, SAXException {
        File xmlFile = Paths.get(path, "Metadata", "Defaults.xml").toFile();
        File xsdFile = Paths.get(path, "Metadata", "Schema", "Defaults.xsd").toFile();

        SchemaValidatorErrorHandler errorHandler = XmlUtilities.validateXmlFileIsValid(xmlFile, xsdFile);

        if (!errorHandler.noErrors()) {
            Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
        }
    }

    @Test
    public void verifyDefaultsXmlElementR_W_TypeHasValidValues() {
        NodeList nodeList = XmlUtilities.getNodeList("//R_W_Type", Paths.get(path, "Metadata", "Defaults.xml"));

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
        NodeList nodeList = XmlUtilities.getNodeList("//Type", Paths.get(path, "Metadata", "Defaults.xml"));

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
        NodeList datasetNodes = XmlUtilities.getNodeList("//Dataset", Paths.get(path, "Metadata", "Defaults.xml"));

        HashSet<String> datasetValues = new HashSet<>();

        for (int i = 0; i < datasetNodes.getLength(); i++) {
            Node currentItem = datasetNodes.item(i);
            datasetValues.add(currentItem.getTextContent());
        }
        return datasetValues;
    }

    private ArrayList<String> collectNameNodesWithDatasetValue(String datasetValue) {
        NodeList nameNodes = XmlUtilities.getNodeList("//Default_Value[Dataset/text() = \"" + datasetValue + "\"]/Name", Paths.get(path, "Metadata", "Defaults.xml"));

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

}
