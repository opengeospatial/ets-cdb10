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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by martin on 2016-09-08.
 */
public class CDBAttributesXmlStructureTests extends CommonFixture {

    @Test
    public void verifyCDBAttributesXmlFileExists() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "CDB_Attributes.xml")),
                "Metadata directory should contain CDB_Attributes.xml file.");
    }

    @Test
    public void verifyCDBAttributesXmlAgainstSchema() throws IOException, SAXException {
        File xmlFile = Paths.get(path, "Metadata", "CDB_Attributes.xml").toFile();
        File xsdFile = Paths.get(path, "Metadata", "Schema", "Vector_Attributes.xsd").toFile();

        SchemaValidatorErrorHandler errorHandler = XmlUtilities.validateXmlFileIsValid(xmlFile, xsdFile);

        if (!errorHandler.noErrors()) {
            Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
        }
    }

    @Test
    public void verifyCodeIsAnInteger() {
        NodeList nodeList = XmlUtilities.getNodeList("//Attribute", Paths.get(path, "Metadata", "CDB_Attributes.xml"));

        ArrayList<String> values = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            values.add(currentItem.getAttributes().getNamedItem("code").getNodeValue());
        }

        for (String value : values) {
            Assert.assertTrue(value.matches("^\\d+$"),
                    String.format("CDB_Attributes.xml attribute code should be an integer. Code '%s' is not valid.", value));
        }
    }

    @Test
    public void verifySymbolIsUnique() {
        NodeList nodeList = XmlUtilities.getNodeList("//Attribute", Paths.get(path, "Metadata", "CDB_Attributes.xml"));

        ArrayList<String> symbols = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            symbols.add(currentItem.getAttributes().getNamedItem("symbol").getNodeValue());
        }

        for (String symbol : symbols) {
            Assert.assertEquals(Collections.frequency(symbols, symbol), 1,
                    String.format("CDB_Attributes.xml element Attribute should have unique symbols. Symbol '%s' is not unique.", symbol));
        }
    }

    @Test
    public void verifyValueHasAValidType() {
        NodeList nodeList = XmlUtilities.getNodeList("//Value/Type", Paths.get(path, "Metadata", "CDB_Attributes.xml"));

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
    public void verifyScalerCodeIsValid() {
        NodeList nodeList = XmlUtilities.getNodeList("//Scalers/Scaler", Paths.get(path, "Metadata", "CDB_Attributes.xml"));

        ArrayList<String> values = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            values.add(currentItem.getAttributes().getNamedItem("code").getNodeValue());
        }

        for (String value : values) {
            Assert.assertTrue(value.matches("^[1-9]\\d*$"),
                    String.format("CDB_Attributes.xml attribute code should be a positive integer. Code '%s' is not valid.", value));
        }
    }
}
