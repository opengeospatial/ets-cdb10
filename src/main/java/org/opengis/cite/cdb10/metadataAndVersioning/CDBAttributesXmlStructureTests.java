package org.opengis.cite.cdb10.metadataAndVersioning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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

    @Test
    public void verifyCDBAttributesXmlFileExists() {
        new CDBAttributesXml(path);
    }

    @Test
    public void verifyCDBAttributesXmlAgainstSchema() throws IOException, SAXException {
        new CDBAttributesXml(path).verifyXmlAgainstSchema();
    }

    @Test
    public void verifyCDBAttributesXmlCodeIsAnInteger() {
        new CDBAttributesXml(path).verifyCodeIsAnInteger();
    }

    @Test
    public void verifyCDBAttributesXmlSymbolIsUnique() {
        CDBAttributesXml cdbAttributes = new CDBAttributesXml(path);
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
        new CDBAttributesXml(path).verifyValueHasAValidType();
    }

    @Test
    public void verifyCDBAttributesXmlScalerCodeIsValid() {
        new CDBAttributesXml(path).verifyScalerCodeIsValid();
    }

    @Test
    public void verifyCDBAttributesXmlUnitCodeIsValid() {
        new CDBAttributesXml(path).verifyUnitCodeIsValid();
    }
}
