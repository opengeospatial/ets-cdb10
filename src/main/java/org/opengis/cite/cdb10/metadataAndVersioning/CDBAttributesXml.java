package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.util.XMLUtils;
import org.testng.Assert;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by martin on 2016-09-20.
 */
public class CDBAttributesXml extends MetadataXmlFile {

    public CDBAttributesXml(String path) {
        super(path, "CDB_Attributes.xml", "Vector_Attributes.xsd");
    }

    public void verifyCodeIsAnInteger() {
        NodeList nodeList = XMLUtils.getNodeList("//Attribute", xmlFile.toPath());

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

    public void verifySymbolIsUnique() {
        NodeList nodeList = XMLUtils.getNodeList("//Attribute", xmlFile.toPath());

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

    public void verifyValueHasAValidType() {
        NodeList nodeList = XMLUtils.getNodeList("//Value/Type", xmlFile.toPath());

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

    public void verifyScalerCodeIsValid() {
        NodeList nodeList = XMLUtils.getNodeList("//Scalers/Scaler", xmlFile.toPath());

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

    public void verifyUnitCodeIsValid() {
        NodeList nodeList = XMLUtils.getNodeList("//Units/Unit", xmlFile.toPath());

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
