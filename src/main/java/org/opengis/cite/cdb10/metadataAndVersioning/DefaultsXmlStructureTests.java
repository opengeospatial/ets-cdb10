package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.util.XMLUtils;
import org.opengis.cite.cdb10.util.metadataXml.DefaultsXml;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by martin on 2016-09-03.
 */
public class DefaultsXmlStructureTests extends Capability2Tests {
	
	private DefaultsXml defaults;

	private void loadXmlFile() {
		this.defaults = new DefaultsXml(path);
	}
	
	private Boolean xmlFileExists() {
		return Files.exists(this.defaults.getXmlFilePath());
	}
	
	private ArrayList<String> collectNameNodesWithDatasetValue(String datasetValue) {
        NodeList nameNodes = XMLUtils.getNodeList("//Default_Value[Dataset/text() = \"" + datasetValue + "\"]/Name", defaults.getXmlFilePath());

        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < nameNodes.getLength(); i++) {
            names.add(nameNodes.item(i).getTextContent());
        }
        return names;
    }

    @Test(description = "OGC 15-113r5, A.1.19, Test 76")
    public void verifyDefaultsXmlAgainstSchema() throws IOException, SAXException {
        this.loadXmlFile();
        if (!this.xmlFileExists()) { return; }
        
        String errors = defaults.schemaValidationErrors();
        Assert.assertEquals(errors, "", defaults.getXmlFileName() + 
        		" does not validate against its XML Schema file. Errors: " + errors);
    }

    @Test(description = "OGC 15-113r5, A.1.19, Test 77")
    public void verifyDefaultsXmlElementR_W_TypeHasValidValues() {
    	this.loadXmlFile();
    	if (!this.xmlFileExists()) { return; }
    	
    	NodeList nodeList = XMLUtils.getNodeList("//R_W_Type", defaults.getXmlFilePath());

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

    @Test(description = "OGC 15-113r5, A.1.19, Test 77")
    public void verifyDefaultsXmlNameIsUniqueForEachDataset() {
    	this.loadXmlFile();
    	if (!this.xmlFileExists()) { return; }
    	
    	NodeList datasetNodes = XMLUtils.getNodeList("//Dataset", defaults.getXmlFilePath());

        HashSet<String> datasetValues = new HashSet<>();

        for (int i = 0; i < datasetNodes.getLength(); i++) {
            Node currentItem = datasetNodes.item(i);
            datasetValues.add(currentItem.getTextContent());
        }
    	
        for (String datasetValue : datasetValues) {
        	ArrayList<String> names = collectNameNodesWithDatasetValue(datasetValue);

        	for (String name : names) {
        		Assert.assertEquals(Collections.frequency(names, name), 1,
        				String.format("Defaults.xml element Name should be " +
        						"unique under each Dataset. '%s' is not unique.", name));
        	}
        }
    }

    @Test(description = "OGC 15-113r5, A.1.19, Test 77")
    public void verifyDefaultsXmlElementTypeHasValidValue() {
    	this.loadXmlFile();
    	if (!this.xmlFileExists()) { return; }
    	
        NodeList nodeList = XMLUtils.getNodeList("//Type", defaults.getXmlFilePath());

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
}
