package org.opengis.cite.cdb10.CDBStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.SchemaValidatorErrorHandler;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-06.
 */
public class VersionXmlStructureTests extends CommonFixture {

    @Test
    public void verifyVersionXmlFileExist() {
        Assert.assertTrue(Files.exists(Paths.get(path, "Metadata", "Version.xml")),
                "Metadata directory should contain Version.xml file.");
    }

    @Test
    public void verifyVersionXmlFileIsValid() throws IOException, SAXException {
        File xmlFile = Paths.get(path, "Metadata", "Version.xml").toFile();
        File xsdFile = Paths.get(path, "Metadata", "Schema", "Version.xsd").toFile();

        SchemaValidatorErrorHandler errorHandler = XmlUtilities.validateXmlFileIsValid(xmlFile, xsdFile);

        if (!errorHandler.noErrors()) {
            Assert.fail(xmlFile.getName() + " does not contain valid XML. Errors: " + errorHandler.getMessages());
        }
    }

    @Test
    public void verifyVersionXmlHasSpecificationElement() {
        NodeList nodeList = XmlUtilities.getNodeList("//Specification", Paths.get(path, "Metadata", "Version.xml"));

        if (nodeList.getLength() == 0) {
            Assert.fail("Version.xml Specification element is mandatory the element was not found.");
        }
    }
}
