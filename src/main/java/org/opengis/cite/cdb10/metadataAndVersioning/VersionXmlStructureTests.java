package org.opengis.cite.cdb10.metadataAndVersioning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opengis.cite.cdb10.util.XMLUtils;
import org.opengis.cite.cdb10.util.metadataXml.VersionXml;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Created by martin on 2016-09-06.
 */
public class VersionXmlStructureTests extends Capability2Tests {
	
	private VersionXml version;

	private void loadXmlFile() {
		this.version = new VersionXml(path);
	}

    @Test
    public void verifyVersionXmlFileExists() {
        this.loadXmlFile();
    	Assert.assertTrue(version.xmlFileExists(),
    			String.format("Metadata directory should contain %s file.", version.getXmlFileName()));
    }

    @Test
    public void verifyVersionXmlAgainstSchema() throws IOException, SAXException {
        this.loadXmlFile();
        String errors = version.schemaValidationErrors();
        Assert.assertEquals(errors, "", version.getXmlFileName() + 
        		" does not contain valid XML. Errors: " + errors);
    }

    @Test
    public void verifyVersionXmlHasSpecificationElement() {
    	this.loadXmlFile();
        NodeList nodeList = XMLUtils.getNodeList("//Specification", version.getXmlFilePath());

        if (nodeList.getLength() == 0) {
            Assert.fail("Version.xml Specification element is mandatory the element was not found.");
        }
    }

    @Test
    public void verifyVersionXmlSpecificationVersionIsValid() {
    	this.loadXmlFile();
        NodeList nodeList = XMLUtils.getNodeList("//Specification", version.getXmlFilePath());

        ArrayList<String> values = new ArrayList<>();
        List<String> VALID_VALUES = Arrays.asList("1.0", "1.1", "3.0", "3.1", "3.2");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            values.add(currentItem.getAttributes().getNamedItem("version").getNodeValue());
        }

        for (String value : values) {
            Assert.assertTrue(VALID_VALUES.contains(value),
                    String.format("Version.xml Specification elements attribute version can have values " +
                            "of '1.0', '1.1', '3.0', '3.1', '3.2'. Value '%s' is not valid.", value));
        }
    }
}
