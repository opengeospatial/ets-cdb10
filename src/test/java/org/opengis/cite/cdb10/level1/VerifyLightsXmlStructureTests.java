package org.opengis.cite.cdb10.level1;

import org.junit.Test;
import org.opengis.cite.cdb10.CDBStructure.LightsXmlStructureTests;
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

    private static Path duplicatedCodeLightsXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "LightsDuplicatedCode.xml"));
    private static Path invalidCodeTenThousandLightsXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "LightsInvalidCodeTenThousand.xml"));
    private static Path invalidCodeNegativeOneLightsXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "LightsInvalidCodeNegativeOne.xml"));
    private static Path invalidLightsXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "LightsInvalid.xml"));
    private static Path validLightsXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Lights.xml"));
    private static Path lightsXsdFile = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Lights.xsd"));

    public VerifyLightsXmlStructureTests() {
        testSuite = new LightsXmlStructureTests();
    }

    @Test
    public void verifyLightsXmlExist_LightsXmlDoesNotExist() throws IOException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Lights.xml file.");

        // execute
        testSuite.verifyLightsXmlFileExist();
    }

    @Test
    public void verifyLightsXmlExist_LightsXmlDoesExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Lights.xml")));

        // execute
        testSuite.verifyLightsXmlFileExist();
    }

    @Test
    public void verifyLightsXmlIsValid_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(validLightsXmlFile, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);
        Files.copy(lightsXsdFile, schemaFolder.resolve("Lights.xsd"), REPLACE_EXISTING);

        Files.copy(validLightsXmlFile, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);

        // execute
        testSuite.verifyLightsXmlFileHasValidXml();
    }

    @Test
    public void verifyLightsXmlIsValid_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(invalidLightsXmlFile, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);
        Files.copy(lightsXsdFile, schemaFolder.resolve("Lights.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Lights.xml does not contain valid XML. Errors: cvc-minInclusive-valid: Value '-1' " +
                "is not facet-valid with respect to minInclusive '0' for type '#AnonType_codeLight'., " +
                "cvc-attribute.3: The value '-1' of attribute 'code' on element 'Light' is not valid with respect " +
                "to its type, '#AnonType_codeLight'.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyLightsXmlFileHasValidXml();
    }

    @Test
    public void verifyLightsXmlHasUniqueCodes_LightsXmlHasUniqueCodes() throws IOException {
        // setup
        Files.copy(validLightsXmlFile, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);

        // execute
        testSuite.verifyLightsXmlHasUniqueCodes();
    }

    @Test
    public void verifyLightsXmlHasUniqueCodes_LightsXmlDoesNotHaveUniqueCodes() throws IOException {
        // setup
        Files.copy(duplicatedCodeLightsXmlFile, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Lights.xml element Light should have unique codes. Code 1 is not unique.");

        // execute
        testSuite.verifyLightsXmlHasUniqueCodes();
    }

    @Test
    public void verifyLightsXmlHasCodesWithinRange_LightsXmlHasCodesInRange() throws IOException {
        // setup
        Files.copy(validLightsXmlFile, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);

        // execute
        testSuite.verifyLightsXmlCodesAreWithinRange();
    }

    @Test
    public void verifyLightsXmlHasCodesWithinRange_LightsXmlCodeIsOver9999() throws IOException {
        // setup
        Files.copy(invalidCodeTenThousandLightsXmlFile, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Lights.xml element Light should have a code from 0 - 9999 inclusive.");

        // execute
        testSuite.verifyLightsXmlCodesAreWithinRange();
    }

    @Test
    public void verifyLightsXmlHasCodesWithinRange_LightsXmlCodeLessThanZero() throws IOException {
        // setup
        Files.copy(invalidCodeNegativeOneLightsXmlFile, metadataFolder.resolve("Lights.xml"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Lights.xml element Light should have a code from 0 - 9999 inclusive.");

        // execute
        testSuite.verifyLightsXmlCodesAreWithinRange();
    }
}
