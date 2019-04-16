package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.util.XMLUtils;
import org.opengis.cite.cdb10.util.metadataXml.LightsXml;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by martin on 2016-09-01.
 */
public class LightsXmlStructureTests extends Capability2Tests {
	
	private LightsXml lights;

	private void loadXmlFile() {
		this.lights = new LightsXml(path);
	}

    @Test
    public void verifyLightsXmlFileExists() {
        this.loadXmlFile();
    	Assert.assertTrue(lights.xmlFileExists(),
    			String.format("Metadata directory should contain %s file.", lights.getXmlFileName()));
    }

    @Test
    public void verifyLightsXmlFileAgainstSchema() throws IOException, SAXException {
        this.loadXmlFile();
        String errors = lights.schemaValidationErrors();
        Assert.assertEquals(errors, "", lights.getXmlFileName() + 
        		" does not contain valid XML. Errors: " + errors);
    }

    @Test
    public void verifyLightsXmlHasUniqueCodes() {
        this.loadXmlFile();
        NodeList nodeList = XMLUtils.getNodeList("//Light", lights.getXmlFilePath());

        ArrayList<String> codes = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            codes.add(currentItem.getAttributes().getNamedItem("code").getNodeValue());
        }

        for (String code : codes) {
            Assert.assertEquals(Collections.frequency(codes, code), 1,
                    String.format("Lights.xml element Light should have unique codes. Code '%s' is not unique.", code));
        }
    }

    @Test
    public void verifyLightsXmlCodesAreWithinRange() {
        this.loadXmlFile();
        NodeList nodeList = XMLUtils.getNodeList("//Light", lights.getXmlFilePath());

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            int key = Integer.parseInt(currentItem.getAttributes().getNamedItem("code").getNodeValue());

            Assert.assertTrue((key >= 0) && (key <= 9999), "Lights.xml element Light should have a code from 0 - 9999 inclusive.");
        }
    }
}
