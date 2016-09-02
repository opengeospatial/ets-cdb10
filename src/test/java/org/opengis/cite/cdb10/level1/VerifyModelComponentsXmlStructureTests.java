package org.opengis.cite.cdb10.level1;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.opengis.cite.cdb10.CDBStructure.ModelComponentsXmlStructureTests;
import org.opengis.cite.cdb10.TestFixture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-02.
 */
public class VerifyModelComponentsXmlStructureTests extends TestFixture<ModelComponentsXmlStructureTests> {

    private Path metadata;
    private static Path sourceDirectory = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "org", "opengis", "cite", "cdb10", "fixtures");
    private static Path validModelComponentsXmlFile = sourceDirectory.resolve(Paths.get("valid", "Model_Components.xml"));

    public VerifyModelComponentsXmlStructureTests() {
        testSuite = new ModelComponentsXmlStructureTests();
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void createMetadataDirectory() throws IOException {
        metadata = Files.createDirectories(cdb_root.resolve(Paths.get("Metadata")));
    }

    @Test
    public void verifyModelComponentsXmlExist_ModelComponentsXmlDoesNotExist() throws IOException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Model_Components.xml file.");

        // execute
        testSuite.verifyModelComponentsXmlFileExist();
    }

    @Test
    public void verifyModelComponentsXmlExist_ModelComponentsXmlDoesExist() throws IOException {
        // setup
        Files.createFile(metadata.resolve(Paths.get("Model_Components.xml")));

        // execute
        testSuite.verifyModelComponentsXmlFileExist();
    }


    @Test
    public void verifyModelComponentsXmlIsValid_XmlIsValid() throws IOException {
        // setup
        Files.copy(validModelComponentsXmlFile, metadata.resolve("Model_Components.xml"), REPLACE_EXISTING);

        // execute
        testSuite.verifyModelComponentsXmlFileHasValidXml();
    }

    @Test
    public void verifyModelComponentsXmlIsValid_XmlIsNotValid() throws IOException {
        // setup
        Files.createFile(metadata.resolve(Paths.get("Model_Components.xml")));

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Model_Components.xml does not contain valid XML.");

        // execute
        testSuite.verifyModelComponentsXmlFileHasValidXml();
    }
}
