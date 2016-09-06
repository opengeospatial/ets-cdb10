package org.opengis.cite.cdb10.level1;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.opengis.cite.cdb10.CDBStructure.LightsXmlStructureTests;
import org.opengis.cite.cdb10.TestFixture;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-01.
 */
public class VerifyLightsXmlStructureTests extends TestFixture<LightsXmlStructureTests> {

    private Path metadata;
    private Path schema;
    private static Path sourceDirectory = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "org", "opengis", "cite", "cdb10", "fixtures");
    private static Path duplicatedCodeLightsXmlFile = sourceDirectory.resolve(Paths.get("invalid", "LightsDuplicatedCode.xml"));
    private static Path invalidCodeTenThousandLightsXmlFile = sourceDirectory.resolve(Paths.get("invalid", "LightsInvalidCodeTenThousand.xml"));
    private static Path invalidCodeNegativeOneLightsXmlFile = sourceDirectory.resolve(Paths.get("invalid", "LightsInvalidCodeNegativeOne.xml"));
    private static Path invalidLightsXmlFile = sourceDirectory.resolve(Paths.get("invalid", "LightsInvalid.xml"));
    private static Path validLightsXmlFile = sourceDirectory.resolve(Paths.get("valid", "Lights.xml"));
    private static Path lightsXsdFile = sourceDirectory.resolve(Paths.get("schema", "Lights.xsd"));

    public VerifyLightsXmlStructureTests() {
        testSuite = new LightsXmlStructureTests();
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void createMetadataDirectory() throws IOException {
        metadata = Files.createDirectories(cdb_root.resolve(Paths.get("Metadata")));
        schema = Files.createDirectories(cdb_root.resolve(Paths.get(String.valueOf(metadata), "Schema")));
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
        Files.createFile(metadata.resolve(Paths.get("Lights.xml")));

        // execute
        testSuite.verifyLightsXmlFileExist();
    }

    @Test
    public void verifyLightsXmlIsValid_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(validLightsXmlFile, metadata.resolve("Lights.xml"), REPLACE_EXISTING);
        Files.copy(lightsXsdFile, schema.resolve("Lights.xsd"), REPLACE_EXISTING);

        Files.copy(validLightsXmlFile, metadata.resolve("Lights.xml"), REPLACE_EXISTING);

        // execute
        testSuite.verifyLightsXmlFileHasValidXml();
    }

    @Test
    public void verifyLightsXmlIsValid_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(invalidLightsXmlFile, metadata.resolve("Lights.xml"), REPLACE_EXISTING);
        Files.copy(lightsXsdFile, schema.resolve("Lights.xsd"), REPLACE_EXISTING);

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
        Files.copy(validLightsXmlFile, metadata.resolve("Lights.xml"), REPLACE_EXISTING);

        // execute
        testSuite.verifyLightsXmlHasUniqueCodes();
    }

    @Test
    public void verifyLightsXmlHasUniqueCodes_LightsXmlDoesNotHaveUniqueCodes() throws IOException {
        // setup
        Files.copy(duplicatedCodeLightsXmlFile, metadata.resolve("Lights.xml"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Lights.xml element Light should have unique codes. Code 1 is not unique.");

        // execute
        testSuite.verifyLightsXmlHasUniqueCodes();
    }

    @Test
    public void verifyLightsXmlHasCodesWithinRange_LightsXmlHasCodesInRange() throws IOException {
        // setup
        Files.copy(validLightsXmlFile, metadata.resolve("Lights.xml"), REPLACE_EXISTING);

        // execute
        testSuite.verifyLightsXmlCodesAreWithinRange();
    }

    @Test
    public void verifyLightsXmlHasCodesWithinRange_LightsXmlCodeIsOver9999() throws IOException {
        // setup
        Files.copy(invalidCodeTenThousandLightsXmlFile, metadata.resolve("Lights.xml"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Lights.xml element Light should have a code from 0 - 9999 inclusive.");

        // execute
        testSuite.verifyLightsXmlCodesAreWithinRange();
    }

    @Test
    public void verifyLightsXmlHasCodesWithinRange_LightsXmlCodeLessThanZero() throws IOException {
        // setup
        Files.copy(invalidCodeNegativeOneLightsXmlFile, metadata.resolve("Lights.xml"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Lights.xml element Light should have a code from 0 - 9999 inclusive.");

        // execute
        testSuite.verifyLightsXmlCodesAreWithinRange();
    }
}
