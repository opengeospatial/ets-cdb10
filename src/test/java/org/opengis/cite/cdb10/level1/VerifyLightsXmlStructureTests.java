package org.opengis.cite.cdb10.level1;

import org.junit.Before;
import org.junit.Test;
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
    private static File invalidLightsXmlFile = new File(System.getProperty("user.dir") + "/src/test/java/org/opengis/cite/cdb10/fixtures/invalid/Lights.xml");
    private static File validLightsXmlFile = new File(System.getProperty("user.dir") + "/src/test/java/org/opengis/cite/cdb10/fixtures/valid/Lights.xml");

    public VerifyLightsXmlStructureTests() {
        testSuite = new LightsXmlStructureTests();
    }

    @Before
    public void createMetadataDirectory() throws IOException {
        metadata = Files.createDirectories(cdb_root.resolve(Paths.get("Metadata")));
    }

    @Test(expected = AssertionError.class)
    public void verifyLightsXmlExist_lightsXmlDoesNotExist() throws IOException {
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

    @Test(expected = AssertionError.class)
    public void verifyLightsXmlHasUniqueCodes_lightsXmlDoesNotHaveUniqueCodes() throws IOException {
        // setup
        Files.copy(invalidLightsXmlFile.toPath(), metadata.resolve("Lights.xml"), REPLACE_EXISTING);

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

    @Test(expected = AssertionError.class)
    public void verifyLightsXmlHasCodesWithinRange_lightsXmlDoesNotHaveCodesInRange() throws IOException {
        // setup
        Files.copy(invalidLightsXmlFile.toPath(), metadata.resolve("Lights.xml"), REPLACE_EXISTING);

        // execute
        testSuite.verifyLightsXmlCodesAreWithinRange();
    }
}
