package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.util.XMLUtils;
import org.testng.Assert;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

/**
 * Created by martin on 2016-09-20.
 */
public class DefaultsXml extends MetadataXmlFile {
    public DefaultsXml(String path) {
        super(path, "Defaults.xml", "Defaults.xsd");
    }

    public void verifyElementR_W_TypeHasValidValues() {
        NodeList nodeList = XMLUtils.getNodeList("//R_W_Type", xmlFile.toPath());

        ArrayList<String> values = new ArrayList<>();
        List<String> VALID_VALUES = Arrays.asList("W", "R");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            values.add(currentItem.getTextContent());
        }

        for (String value : values) {
            Assert.assertTrue(VALID_VALUES.contains(value),
                    String.format("Defaults.xml element R_W_Type should " +
                            "have a value of R or W. Value '%s' is not valid.", value));
        }
    }

    public void verifyNameIsUniqueForEachDataset() {
        for (String datasetValue : getDatasetValues()) {
            assertNamesAreUnique(datasetValue);
        }
    }

    public void verifyElementTypeHasValidValue() {
        NodeList nodeList = XMLUtils.getNodeList("//Type", xmlFile.toPath());

        ArrayList<String> values = new ArrayList<>();
        List<String> VALID_VALUES = Arrays.asList("string", "integer", "float");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            values.add(currentItem.getTextContent());
        }

        for (String value : values) {
            Assert.assertTrue(VALID_VALUES.contains(value),
                    String.format("Defaults.xml element Type should have a value of " +
                            "'float', 'integer' or 'string'. Value '%s' is not valid.", value));
        }
    }

    private HashSet<String> getDatasetValues() {
        NodeList datasetNodes = XMLUtils.getNodeList("//Dataset", xmlFile.toPath());

        HashSet<String> datasetValues = new HashSet<>();

        for (int i = 0; i < datasetNodes.getLength(); i++) {
            Node currentItem = datasetNodes.item(i);
            datasetValues.add(currentItem.getTextContent());
        }
        return datasetValues;
    }

    private ArrayList<String> collectNameNodesWithDatasetValue(String datasetValue) {
        NodeList nameNodes = XMLUtils.getNodeList("//Default_Value[Dataset/text() = \"" + datasetValue + "\"]/Name", xmlFile.toPath());

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
                    String.format("Defaults.xml element Name should be " +
                            "unique under each Dataset. '%s' is not unique.", name));
        }
    }
}
