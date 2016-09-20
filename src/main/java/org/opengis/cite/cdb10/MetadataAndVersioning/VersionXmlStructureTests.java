package org.opengis.cite.cdb10.MetadataAndVersioning;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.opengis.cite.cdb10.util.XMLUtils;
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
import java.util.List;

/**
 * Created by martin on 2016-09-06.
 */
public class VersionXmlStructureTests extends CommonFixture {

    @Test
    public void verifyVersionXmlFileExists() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Version.xml")),
                "Metadata directory should contain Version.xml file.");
    }

    @Test
    public void verifyVersionXmlAgainstSchema() throws IOException, SAXException {
        File xmlFile = Paths.get(path, "Metadata", "Version.xml").toFile();
        File xsdFile = Paths.get(path, "Metadata", "Schema", "Version.xsd").toFile();

        SchemaValidatorErrorHandler errorHandler = XMLUtils.validateXmlFileIsValid(xmlFile, xsdFile);

        if (!errorHandler.noErrors()) {
            Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
        }
    }

    @Test
    public void verifyVersionXmlHasSpecificationElement() {
        NodeList nodeList = XMLUtils.getNodeList("//Specification", Paths.get(path, "Metadata", "Version.xml"));

        if (nodeList.getLength() == 0) {
            Assert.fail("Version.xml Specification element is mandatory the element was not found.");
        }
    }

    @Test
    public void verifyVersionXmlSpecificationVersionIsValid() {
        NodeList nodeList = XMLUtils.getNodeList("//Specification", Paths.get(path, "Metadata", "Version.xml"));

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
