package org.opengis.cite.cdb10.metadataAndVersioning;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-01.
 */
public class VerifyLightsXmlStructureTests extends MetadataTestFixture<LightsXmlStructureTests> {

    private final static Path XSD_FILE = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Lights.xsd"));

    private final static Path VALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Lights.xml"));

    private final static Path DUPLICATED_CODE_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "LightsInvalidDuplicatedCode.xml"));
    private final static Path INVALID_CODE_TEN_THOUSAND_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "LightsInvalidCodeTenThousand.xml"));
    private final static Path INVALID_CODE_NEGATIVE_ONE_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "LightsInvalidCodeNegativeOne.xml"));
    private final static Path INVALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "LightsInvalid.xml"));

    public VerifyLightsXmlStructureTests() {
        testSuite = new LightsXmlStructureTests();
    }

    @Test
    public void verifyLightsXmlExists_DoesNotExist() throws IOException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Lights.xml file.");

        // execute
        testSuite.verifyLightsXmlFileExists();
    }

    @Test
    public void verifyLightsXmlExists_DoesExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Lights.xml")));
        Files.createFile(schemaFolder.resolve(Paths.get("Lights.xsd")));

        // execute
        testSuite.verifyLightsXmlFileExists();
    }

    @Test
    public void verifyLightsXmlAgainstSchema_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Lights.xsd"), REPLACE_EXISTING);

        Files.copy(VALID_FILE, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);

        // execute
        testSuite.verifyLightsXmlFileAgainstSchema();
    }

    @Test
    public void verifyLightsXmlAgainstSchema_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(INVALID_FILE, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Lights.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Lights.xml does not contain valid XML. Errors: cvc-minInclusive-valid: Value '-1' " +
                "is not facet-valid with respect to minInclusive '0' for type '#AnonType_codeLight'., " +
                "cvc-attribute.3: The value '-1' of attribute 'code' on element 'Light' is not valid with respect " +
                "to its type, '#AnonType_codeLight'.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyLightsXmlFileAgainstSchema();
    }

    @Test
    public void verifyLightsXmlHasUniqueCodes_LightsXmlHasUniqueCodes() throws IOException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);
        Files.createFile(schemaFolder.resolve(Paths.get("Lights.xsd")));

        // execute
        testSuite.verifyLightsXmlHasUniqueCodes();
    }

    @Test
    public void verifyLightsXmlHasUniqueCodes_LightsXmlDoesNotHaveUniqueCodes() throws IOException {
        // setup
        Files.copy(DUPLICATED_CODE_FILE, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);
        Files.createFile(schemaFolder.resolve(Paths.get("Lights.xsd")));

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Lights.xml element Light should have unique codes. Code '1' is not unique. expected [1] but found [2]");

        // execute
        testSuite.verifyLightsXmlHasUniqueCodes();
    }

    @Test
    public void verifyLightsXmlHasCodesWithinRange_LightsXmlHasCodesInRange() throws IOException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);
        Files.createFile(schemaFolder.resolve(Paths.get("Lights.xsd")));

        // execute
        testSuite.verifyLightsXmlCodesAreWithinRange();
    }

    @Test
    public void verifyLightsXmlHasCodesWithinRange_LightsXmlCodeIsOver9999() throws IOException {
        // setup
        Files.copy(INVALID_CODE_TEN_THOUSAND_FILE, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);
        Files.createFile(schemaFolder.resolve(Paths.get("Lights.xsd")));

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Lights.xml element Light should have a code from 0 - 9999 inclusive.");

        // execute
        testSuite.verifyLightsXmlCodesAreWithinRange();
    }

    @Test
    public void verifyLightsXmlHasCodesWithinRange_LightsXmlCodeLessThanZero() throws IOException {
        // setup
        Files.copy(INVALID_CODE_NEGATIVE_ONE_FILE, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);
        Files.createFile(schemaFolder.resolve(Paths.get("Lights.xsd")));

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Lights.xml element Light should have a code from 0 - 9999 inclusive.");

        // execute
        testSuite.verifyLightsXmlCodesAreWithinRange();
    }
}
