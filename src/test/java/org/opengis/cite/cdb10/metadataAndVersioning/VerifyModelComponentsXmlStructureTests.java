package org.opengis.cite.cdb10.metadataAndVersioning;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-02.
 */
public class VerifyModelComponentsXmlStructureTests extends MetadataTestFixture<ModelComponentsXmlStructureTests> {

    private final static Path XSD_FILE = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Model_Components.xsd"));

    private final static Path VALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Model_Components.xml"));

    private final static Path INVALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "Model_ComponentsInvalid.xml"));

    public VerifyModelComponentsXmlStructureTests() {
        testSuite = new ModelComponentsXmlStructureTests();
    }

    @Test
    public void verifyModelComponentsXmlExists_DoesNotExist() throws IOException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Model_Components.xml file.");

        // execute
        testSuite.verifyModelComponentsXmlFileExists();
    }

    @Test
    public void verifyModelComponentsXmlExists_XmlDoesExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Model_Components.xml")));
        Files.createFile(schemaFolder.resolve(Paths.get("Model_Components.xsd")));

        // execute
        testSuite.verifyModelComponentsXmlFileExists();
    }

    @Test
    public void verifyModelComponentsXmlAgainstSchema_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Model_Components.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Model_Components.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyModelComponentsXmlAgainstSchema();
    }

    @Test
    public void verifyModelComponentsXmlAgainstSchema_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(INVALID_FILE, metadataFolder.resolve("Model_Components.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Model_Components.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Model_Components.xml does not contain valid XML. " +
                "Errors: cvc-complex-type.4: Attribute 'name' must appear on element 'Component'., " +
                "cvc-complex-type.2.4.b: The content of element 'Component' is not complete. One of " +
                "'{\"CDB\":Description}' is expected.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyModelComponentsXmlAgainstSchema();
    }
}
