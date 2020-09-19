package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.util.XMLUtils;
import org.opengis.cite.cdb10.util.metadataXml.LightsXml;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
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
	
	private Boolean xmlFileExists() {
		return Files.exists(this.lights.getXmlFilePath());
	}

    @Test(description = "OGC 15-113r3, A.1.19, Test 76")
    public void verifyLightsXmlFileAgainstSchema() throws IOException, SAXException {
        this.loadXmlFile();
        if (!this.xmlFileExists()) { return; }
        
        String errors = lights.schemaValidationErrors();
        Assert.assertEquals(errors, "", lights.getXmlFileName() + 
        		" does not validate against its XML Schema file. Errors: " + errors);
    }

    @Test(description = "OGC 15-113r3, A.1.19, Test 77")
    public void verifyLightsXmlHasUniqueCodes() {
        this.loadXmlFile();
        if (!this.xmlFileExists()) { return; }
        
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

    @Test(description = "OGC 15-113r3, A.1.19, Test 77")
    public void verifyLightsXmlCodesAreWithinRange() {
        this.loadXmlFile();
        if (!this.xmlFileExists()) { return; }
        
        NodeList nodeList = XMLUtils.getNodeList("//Light", lights.getXmlFilePath());

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            int key = Integer.parseInt(currentItem.getAttributes().getNamedItem("code").getNodeValue());

            Assert.assertTrue((key >= 0) && (key <= 9999), "Lights.xml element Light should have a code from 0 - 9999 inclusive.");
        }
    }
}
