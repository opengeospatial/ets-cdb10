package org.opengis.cite.cdb10.metadataAndVersioning;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-06.
 */
public class VerifyVersionXmlStructureTests extends MetadataTestFixture<VersionXmlStructureTests> {

    private final static Path XSD_FILE = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Version.xsd"));

    private final static Path VALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Version.xml"));

    private final static Path INVALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "VersionInvalid.xml"));
    private final static Path NO_SPECIFICATION_ELEMENT_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "VersionInvalidNoSpecificationElement.xml"));
    private final static Path INVALID_SPECIFICATION_VERSION_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "VersionInvalidSpecificationVersion.xml"));

    public VerifyVersionXmlStructureTests() {
        testSuite = new VersionXmlStructureTests();
    }

    @Test
    public void verifyVersionXmlFileExists_DoesNotExist() throws IOException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Version.xml file.");

        // execute
        testSuite.verifyVersionXmlFileExists();
    }

    @Test
    public void verifyVersionXmlFileExists_DoesExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Version.xml")));
        Files.createFile(schemaFolder.resolve(Paths.get("Version.xsd")));

        // execute
        testSuite.verifyVersionXmlFileExists();
    }

    @Test
    public void verifyVersionXmlAgainstSchema_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Version.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Version.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyVersionXmlAgainstSchema();
    }

    @Test
    public void verifyVersionXmlAgainstSchema_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(INVALID_FILE, metadataFolder.resolve("Version.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Version.xsd"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Version.xml does not contain valid XML. Errors: cvc-complex-type.4: " +
                "Attribute 'name' must appear on element 'PreviousIncrementalRootDirectory'.");

        // execute
        testSuite.verifyVersionXmlAgainstSchema();
    }

    @Test
    public void verifyVersionXmlHasSpecificationElement_HasElement() throws IOException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Version.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Version.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyVersionXmlHasSpecificationElement();
    }

    @Test
    public void verifyVersionXmlHasSpecificationElement_DoesNotHaveElement() throws IOException {
        // setup
        Files.copy(NO_SPECIFICATION_ELEMENT_FILE, metadataFolder.resolve("Version.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Version.xsd"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Version.xml Specification element is mandatory the element was not found.");

        // execute
        testSuite.verifyVersionXmlHasSpecificationElement();
    }

    @Test
    public void verifyVersionXmlSpecificationVersionIsValid_Valid() throws IOException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Version.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Version.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyVersionXmlSpecificationVersionIsValid();
    }

    @Test
    public void verifyVersionXmlSpecificationVersionIsValid_Invalid() throws IOException {
        // setup
        Files.copy(INVALID_SPECIFICATION_VERSION_FILE, metadataFolder.resolve("Version.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Version.xsd"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Version.xml Specification elements attribute version can have values of '3.0', '3.1', '3.1'. Value '1.0' is not valid.");

        // execute
        testSuite.verifyVersionXmlSpecificationVersionIsValid();
    }
}
