package org.opengis.cite.cdb10.level1;

import org.junit.Test;
import org.opengis.cite.cdb10.CDBStructure.VersionXmlStructureTests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by martin on 2016-09-06.
 */
public class VerifyVersionXmlStructureTests extends MetadataTestFixture<VersionXmlStructureTests> {

    private static Path validVersionXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Defaults.xml"));
    private static Path versionXsdFile = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Defaults.xsd"));

    public VerifyVersionXmlStructureTests() {testSuite = new VersionXmlStructureTests(); }

    @Test
    public void verifyVersionXmlFileExist_ModelComponentsXmlDoesNotExist() throws IOException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Version.xml file.");

        // execute
        testSuite.verifyVersionXmlFileExist();
    }

    @Test
    public void verifyVersionXmlFileExist_DefaultsXmlDoesExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Version.xml")));

        // execute
        testSuite.verifyVersionXmlFileExist();
    }

}
