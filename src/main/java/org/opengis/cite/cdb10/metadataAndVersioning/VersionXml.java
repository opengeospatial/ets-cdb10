package org.opengis.cite.cdb10.metadataAndVersioning;

import org.opengis.cite.cdb10.util.XMLUtils;
import org.testng.Assert;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by martin on 2016-09-20.
 */
public class VersionXml extends MetadataXmlFile {
    public VersionXml(String path) {
        super(path, "Version.xml", "Version.xsd");
    }

    public void verifyHasSpecificationElement() {
        NodeList nodeList = XMLUtils.getNodeList("//Specification", xmlFile.toPath());

        if (nodeList.getLength() == 0) {
            Assert.fail("Version.xml Specification element is mandatory the element was not found.");
        }
    }

    public void verifySpecificationVersionIsValid() {
        NodeList nodeList = XMLUtils.getNodeList("//Specification", xmlFile.toPath());

        ArrayList<String> values = new ArrayList<>();
        List<String> VALID_VALUES = Arrays.asList("3.0", "3.1", "3.2");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentItem = nodeList.item(i);
            values.add(currentItem.getAttributes().getNamedItem("version").getNodeValue());
        }

        for (String value : values) {
            Assert.assertTrue(VALID_VALUES.contains(value),
                    String.format("Version.xml Specification elements attribute version can have values " +
                            "of '3.0', '3.1', '3.1'. Value '%s' is not valid.", value));
        }
    }
}
