package org.opengis.cite.cdb10.metadataAndVersioning;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-07.
 */
public class VerifyConfigurationXmlStructureTests extends MetadataTestFixture<ConfigurationXmlStructureTests> {

    private final static Path XSD_FILE = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Configuration.xsd"));

    private final static Path VALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Configuration.xml"));

    private final static Path INVALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "ConfigurationInvalid.xml"));

    public VerifyConfigurationXmlStructureTests() {
        testSuite = new ConfigurationXmlStructureTests();
    }

    @Test
    public void verifyConfigurationXmlExists_DoesNotExist() throws IOException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Configuration.xml file.");

        // execute
        testSuite.verifyConfigurationXmlFileExists();
    }

    @Test
    public void verifyConfigurationXmlExists_DoesExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Configuration.xml")));
        Files.createFile(schemaFolder.resolve(Paths.get("Configuration.xsd")));

        // execute
        testSuite.verifyConfigurationXmlFileExists();
    }

    @Test
    public void verifyConfigurationXmlAgainstSchema_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Configuration.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Configuration.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyConfigurationXmlAgainstSchema();
    }

    @Test
    public void verifyConfigurationXmlAgainstSchema_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(INVALID_FILE, metadataFolder.resolve("Configuration.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Configuration.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Configuration.xml does not contain valid XML. " +
                "Errors: cvc-complex-type.4: Attribute 'path' must appear on element 'Folder'., " +
                "cvc-complex-type.4: Attribute 'version' must appear on element 'Specification'., " +
                "cvc-complex-type.4: Attribute 'name' must appear on element 'Extension'., " +
                "cvc-complex-type.4: Attribute 'version' must appear on element 'Extension'.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyConfigurationXmlAgainstSchema();
    }
}
