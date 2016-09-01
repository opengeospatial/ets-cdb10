package org.opengis.cite.cdb10.level1;

import org.junit.Test;
import org.opengis.cite.cdb10.CDBStructure.LightsXmlStructureTests;
import org.opengis.cite.cdb10.TestFixture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-01.
 */
public class VerifyLightsXmlStructureTests extends TestFixture<LightsXmlStructureTests> {
    public VerifyLightsXmlStructureTests() {
        testSuite = new LightsXmlStructureTests();
    }

    @Test(expected = AssertionError.class)
    public void verifyLightsXmlExist_lightsXmlDoesNotExist() throws IOException {
        // setup
        Files.createDirectories(cdb_root.resolve(Paths.get("Metadata")));

        // execute
        testSuite.verifyLightsXmlFileExist();
    }

    @Test
    public void verifyLightsXmlExist_lightsXmlDoesExist() throws IOException {
        // setup
        Files.createDirectories(cdb_root.resolve(Paths.get("Metadata", "Lights.xml")));

        // execute
        testSuite.verifyLightsXmlFileExist();
    }

    
}
