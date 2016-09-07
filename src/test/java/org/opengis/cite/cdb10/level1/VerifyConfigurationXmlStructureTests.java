package org.opengis.cite.cdb10.level1;

import org.junit.Test;
import org.opengis.cite.cdb10.CDBStructure.ConfigurationXmlStructureTests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        // execute
        testSuite.verifyConfigurationXmlFileExists();
    }
}
