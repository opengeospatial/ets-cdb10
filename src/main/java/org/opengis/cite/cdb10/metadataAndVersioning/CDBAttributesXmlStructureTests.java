package org.opengis.cite.cdb10.metadataAndVersioning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.opengis.cite.cdb10.util.XMLUtils;
import org.opengis.cite.cdb10.util.metadataXml.CDBAttributesXml;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Created by martin on 2016-09-08.
 */
public class CDBAttributesXmlStructureTests extends Capability2Tests {
	
	private CDBAttributesXml cdbAttributes;

	private void loadXmlFile() {
		this.cdbAttributes = new CDBAttributesXml(path);
	}
	
    @Test
    public void verifyCDBAttributesXmlFileExists() {
    	this.loadXmlFile();
    }

    @Test
    public void verifyCDBAttributesXmlAgainstSchema() throws IOException, SAXException {
    	this.loadXmlFile();
        String errors = cdbAttributes.schemaValidationErrors();
        Assert.assertEquals(errors, "", cdbAttributes.getXmlFileName() + 
        		" does not contain valid XML. Errors: " + errors);
    }

    @Test
    public void verifyCDBAttributesXmlCodeIsAnInteger() {
    	this.loadXmlFile();
        NodeList nodeList = XMLUtils.getNodeList("//Attribute", cdbAttributes.getXmlFilePath());

        ArrayList<String> values = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            values.add(currentItem.getAttributes().getNamedItem("code").getNodeValue());
        }

        for (String value : values) {
            Assert.assertTrue(value.matches("^\\d+$"),
                    String.format("CDB_Attributes.xml attribute code " +
                            "should be an integer. Code '%s' is not valid.", value));
        }
    }

    @Test
    public void verifyCDBAttributesXmlSymbolIsUnique() {
    	this.loadXmlFile();
        NodeList nodeList = XMLUtils.getNodeList("//Attribute", cdbAttributes.getXmlFilePath());

        ArrayList<String> symbols = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            symbols.add(currentItem.getAttributes().getNamedItem("symbol").getNodeValue());
        }

        for (String symbol : symbols) {
            Assert.assertEquals(Collections.frequency(symbols, symbol), 1,
                    String.format("CDB_Attributes.xml element Attribute should " +
                            "have unique symbols. Symbol '%s' is not unique.", symbol));
        }
    }

    @Test
    public void verifyCDBAttributesXmlValueHasAValidType() {
    	this.loadXmlFile();
        NodeList nodeList = XMLUtils.getNodeList("//Value/Type", cdbAttributes.getXmlFilePath());

        ArrayList<String> types = new ArrayList<>();
        List<String> VALID_TYPES = Arrays.asList("Text", "Numeric", "Boolean");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            types.add(currentItem.getTextContent());
        }

        for (String type : types) {
            Assert.assertTrue(VALID_TYPES.contains(type),
                    String.format("CDB_Attributes.xml element Type should have a value of " +
                            "'Text', 'Numeric' or 'Boolean'. Type '%s' is not valid.", type));
        }
    }

    @Test
    public void verifyCDBAttributesXmlScalerCodeIsValid() {
    	this.loadXmlFile();
        NodeList nodeList = XMLUtils.getNodeList("//Scalers/Scaler", cdbAttributes.getXmlFilePath());

        ArrayList<String> values = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            values.add(currentItem.getAttributes().getNamedItem("code").getNodeValue());
        }

        for (String value : values) {
            Assert.assertTrue(value.matches("^[1-9]\\d*$"),
                    String.format("CDB_Attributes.xml Scaler code should be a positive integer. Code '%s' is not valid.", value));
        }
    }

    @Test
    public void verifyCDBAttributesXmlUnitCodeIsValid() {
    	this.loadXmlFile();
        NodeList nodeList = XMLUtils.getNodeList("//Units/Unit", cdbAttributes.getXmlFilePath());

        ArrayList<String> values = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            values.add(currentItem.getAttributes().getNamedItem("code").getNodeValue());
        }

        for (String value : values) {
            Assert.assertTrue(value.matches("^[1-9]\\d*$"),
                    String.format("CDB_Attributes.xml Unit code should be a positive integer. Code '%s' is not valid.", value));
        }
    }
}
