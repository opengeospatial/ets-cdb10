package org.opengis.cite.cdb10.level1;

import org.junit.Test;
import org.opengis.cite.cdb10.CDBStructure.LightsXxxXmlStructureTests;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-12.
 */
public class VerifyLightsXxxXmlStructureTests extends MetadataTestFixture<LightsXxxXmlStructureTests> {

    private final static Path XSD_FILE = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Lights_Tuning.xsd"));

    private final static Path VALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Lights_Client.xml"));

    private final static Path INVALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "Lights_ClientInvalid.xml"));

    public VerifyLightsXxxXmlStructureTests() {
        testSuite = new LightsXxxXmlStructureTests();
    }

    @Test
    public void verifyLightsXmlFileNameIsValid_IsNotValid() throws Exception {
        //setup
        Files.createFile(metadataFolder.resolve(Paths.get("Lights_123451234512345123451234512345.xml")));

        String expectedMessage = "'Lights_123451234512345123451234512345.xml' is not a valid file name it must start " +
                "with 'Lights_', can only be a maximum of 32 characters and contain letters, numbers, underscores and dashes.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyLightsXmlFileNameIsValid();
    }

    @Test
    public void verifyLightsXmlFileNameIsValid_IsValid() throws Exception {
        //setup
        Files.createFile(metadataFolder.resolve(Paths.get("Lights_Client.xml")));

        // execute
        testSuite.verifyLightsXmlFileNameIsValid();
    }

    @Test
    public void verifyLightsTuningXsdFileExists_DoesNotExist() throws IOException {
        //setup
        Files.createFile(metadataFolder.resolve(Paths.get("Lights_Client.xml")));

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("If a custom Lights_xxx.xml exists there should be Lights_Tuning.xsd in the Schema folder.");

        // execute
        testSuite.verifyLightsTuningXsdFFileExists();
    }

    @Test
    public void verifyLightsTuningXsdFileExists_DoesExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Lights_Client.xml")));
        Files.createFile(schemaFolder.resolve(Paths.get("Lights_Tuning.xsd")));

        // execute
        testSuite.verifyLightsTuningXsdFFileExists();
    }

    @Test
    public void verifyLightsXxxXmlAgainstSchema_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Lights_Tuning.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyLightsXxxXmlAgainstSchema();
    }

    @Test
    public void verifyLightsXxxXmlAgainstSchema_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(INVALID_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Lights_Tuning.xsd"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Lights_Client.xml does not contain valid XML. " +
                "Errors: cvc-pattern-valid: Value 'Invalid' is not facet-valid with respect to pattern " +
                "'Omnidirectional|Directional|Bidirectional' for type '#AnonType_DirectionalityLight'., " +
                "cvc-type.3.1.3: The value 'Invalid' of element 'Directionality' is not valid.");

        // execute
        testSuite.verifyLightsXxxXmlAgainstSchema();
    }
}
