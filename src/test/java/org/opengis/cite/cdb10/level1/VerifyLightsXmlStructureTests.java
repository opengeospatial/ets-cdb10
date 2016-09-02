package org.opengis.cite.cdb10.level1;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.opengis.cite.cdb10.CDBStructure.LightsXmlStructureTests;
import org.opengis.cite.cdb10.TestFixture;

import java.io.File;
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
    private static File duplicatedCodeLightsXmlFile = new File(System.getProperty("user.dir") + "/src/test/java/org/opengis/cite/cdb10/fixtures/invalid/LightsDuplicatedCode.xml");
    private static File invalidCodeTenThousandLightsXmlFile = new File(System.getProperty("user.dir") + "/src/test/java/org/opengis/cite/cdb10/fixtures/invalid/LightsInvalidCodeTenThousand.xml");
    private static File invalidCodeTenNegativeOneLightsXmlFile = new File(System.getProperty("user.dir") + "/src/test/java/org/opengis/cite/cdb10/fixtures/invalid/LightsInvalidCodeNegativeOne.xml");
    private static File validLightsXmlFile = new File(System.getProperty("user.dir") + "/src/test/java/org/opengis/cite/cdb10/fixtures/valid/Lights.xml");

    public VerifyLightsXmlStructureTests() {
        testSuite = new LightsXmlStructureTests();
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void createMetadataDirectory() throws IOException {
        metadata = Files.createDirectories(cdb_root.resolve(Paths.get("Metadata")));
    }

    @Test
    public void verifyLightsXmlExist_lightsXmlDoesNotExist() throws IOException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Lights.xml file.");

        // execute
        testSuite.verifyLightsXmlFileExist();
    }

    @Test
    public void verifyLightsXmlExist_lightsXmlDoesExist() throws IOException {
        // setup
        Files.createFile(metadata.resolve(Paths.get("Lights.xml")));

        // execute
        testSuite.verifyLightsXmlFileExist();
    }

    @Test
    public void verifyLightsXmlHasUniqueCodes_lightsXmlHasUniqueCodes() throws IOException {
        // setup
        Files.copy(validLightsXmlFile.toPath(), metadata.resolve("Lights.xml"), REPLACE_EXISTING);

        // execute
        testSuite.verifyLightsXmlHasUniqueCodes();
    }

    @Test
    public void verifyLightsXmlHasUniqueCodes_lightsXmlDoesNotHaveUniqueCodes() throws IOException {
        // setup
        Files.copy(duplicatedCodeLightsXmlFile.toPath(), metadata.resolve("Lights.xml"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Lights.xml element Light should have unique codes. Code 1 is not unique.");

        // execute
        testSuite.verifyLightsXmlHasUniqueCodes();
    }

    @Test
    public void verifyLightsXmlHasCodesWithinRange_lightsXmlHasCodesInRange() throws IOException {
        // setup
        Files.copy(validLightsXmlFile.toPath(), metadata.resolve("Lights.xml"), REPLACE_EXISTING);

        // execute
        testSuite.verifyLightsXmlCodesAreWithinRange();
    }

    @Test
    public void verifyLightsXmlHasCodesWithinRange_lightsXmlCodeIsOver9999() throws IOException {
        // setup
        Files.copy(invalidCodeTenThousandLightsXmlFile.toPath(), metadata.resolve("Lights.xml"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Lights.xml element Light should have a code from 0 - 9999 inclusive.");

        // execute
        testSuite.verifyLightsXmlCodesAreWithinRange();
    }

    @Test
    public void verifyLightsXmlHasCodesWithinRange_lightsXmlCodeLessThanZero() throws IOException {
        // setup
        Files.copy(invalidCodeTenNegativeOneLightsXmlFile.toPath(), metadata.resolve("Lights.xml"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Lights.xml element Light should have a code from 0 - 9999 inclusive.");

        // execute
        testSuite.verifyLightsXmlCodesAreWithinRange();
    }
}
