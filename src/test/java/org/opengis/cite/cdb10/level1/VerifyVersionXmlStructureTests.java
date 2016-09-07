package org.opengis.cite.cdb10.level1;

import org.junit.Test;
import org.opengis.cite.cdb10.CDBStructure.VersionXmlStructureTests;
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

    private static Path validVersionXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Version.xml"));
    private static Path invalidVersionXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "VersionInvalid.xml"));
    private static Path invalidNoSpecificationElementVersionXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "VersionInvalidNoSpecificationElement.xml"));
    private static Path versionXsdFile = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Version.xsd"));

    public VerifyVersionXmlStructureTests() {testSuite = new VersionXmlStructureTests(); }

    @Test
    public void verifyVersionXmlFileExist_ModelComponentsXmlDoesNotExist() throws IOException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Version.xml file.");

        // execute
        testSuite.verifyVersionXmlFileExist();
    }

    @Test
    public void verifyVersionXmlFileExist_DefaultsXmlDoesExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Version.xml")));

        // execute
        testSuite.verifyVersionXmlFileExist();
    }

    @Test
    public void verifyVersionXmlIsValid_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(validVersionXmlFile, metadataFolder.resolve("Version.xml"), REPLACE_EXISTING);
        Files.copy(versionXsdFile, schemaFolder.resolve("Version.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyVersionXmlFileIsValid();
    }

    @Test
    public void verifyVersionXmlIsValid_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(invalidVersionXmlFile, metadataFolder.resolve("Version.xml"), REPLACE_EXISTING);
        Files.copy(versionXsdFile, schemaFolder.resolve("Version.xsd"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Version.xml does not contain valid XML. Errors: cvc-complex-type.4: " +
                "Attribute 'name' must appear on element 'PreviousIncrementalRootDirectory'.");

        // execute
        testSuite.verifyVersionXmlFileIsValid();
    }

    @Test
    public void verifyVersionXmlHasSpecificationElement_HasElement() throws IOException {
        // setup
        Files.copy(validVersionXmlFile, metadataFolder.resolve("Version.xml"), REPLACE_EXISTING);
        Files.copy(versionXsdFile, schemaFolder.resolve("Version.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyVersionXmlHasSpecificationElement();
    }

    @Test
    public void verifyVersionXmlHasSpecificationElement_DoesNotHaveElement() throws IOException {
        // setup
        Files.copy(invalidNoSpecificationElementVersionXmlFile, metadataFolder.resolve("Version.xml"), REPLACE_EXISTING);
        Files.copy(versionXsdFile, schemaFolder.resolve("Version.xsd"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Version.xml Specification element is mandatory the element was not found.");

        // execute
        testSuite.verifyVersionXmlHasSpecificationElement();
    }
}
