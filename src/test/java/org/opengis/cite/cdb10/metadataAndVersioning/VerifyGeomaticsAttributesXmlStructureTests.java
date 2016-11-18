package org.opengis.cite.cdb10.metadataAndVersioning;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-10.
 */
public class VerifyGeomaticsAttributesXmlStructureTests extends MetadataTestFixture<GeomaticsAttributesXmlStructureTests> {

    private final static Path XSD_FILE = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Geomatics_Attributes.xsd"));

    private final static Path VALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Geomatics_Attributes.xml"));

    private final static Path INVALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "Geomatics_AttributesInvalid.xml"));

    public VerifyGeomaticsAttributesXmlStructureTests() {
        testSuite = new GeomaticsAttributesXmlStructureTests();
    }

    @Test
    public void verifyVendorAttributesXsdFileExists_DoesNotExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Geomatics_Attributes.xml")));
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Geomatics_Attributes.xsd file.");

        // execute
        testSuite.verifyGeomaticsAttributesXsdFileExists();
    }

    @Test
    public void verifyGeomaticsAttributesXsdFileExists_DoesExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Geomatics_Attributes.xml")));
        Files.createFile(schemaFolder.resolve(Paths.get("Geomatics_Attributes.xsd")));

        // execute
        testSuite.verifyGeomaticsAttributesXsdFileExists();
    }

    @Test
    public void verifyGeomaticsAttributesXmlAgainstSchema_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Geomatics_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Geomatics_Attributes.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyGeomaticsAttributesXmlAgainstSchema();
    }

    @Test
    public void verifyGeomaticsAttributesXmlAgainstSchema_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(INVALID_FILE, metadataFolder.resolve("Geomatics_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Geomatics_Attributes.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Geomatics_Attributes.xml does not contain valid XML. " +
                "Errors: cvc-complex-type.2.4.b: The content of element 'Geomatics_Attributes' is not complete. " +
                "One of '{\"http://www.CDB-Spec.org/Schema/Version/3.2\":Example_Element_2}' is expected.";


        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyGeomaticsAttributesXmlAgainstSchema();
    }

    @Test
    public void verifyGeomaticsAttributesXmlAgainstSchema_GeomaticsAttributesXmlFileDoesNotExist() throws IOException, SAXException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Geomatics_Attributes.xsd")));
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Geomatics_Attributes.xml file.");

        // execute
        testSuite.verifyGeomaticsAttributesXmlAgainstSchema();
    }

    @Test
    public void verifyGeomaticsAttributesXmlAgainstSchema_GeomaticsAttributesXsdFileDoesNotExist() throws IOException, SAXException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Geomatics_Attributes.xml")));
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Geomatics_Attributes.xsd file.");

        // execute
        testSuite.verifyGeomaticsAttributesXmlAgainstSchema(); // will not return an assertion error
    }
}
