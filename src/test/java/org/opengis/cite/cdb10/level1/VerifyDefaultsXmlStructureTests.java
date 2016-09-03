package org.opengis.cite.cdb10.level1;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.opengis.cite.cdb10.CDBStructure.DefaultsXmlStructureTests;
import org.opengis.cite.cdb10.TestFixture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-03.
 */
public class VerifyDefaultsXmlStructureTests extends TestFixture<DefaultsXmlStructureTests> {

    private Path metadata;
    private Path schema;
    private static Path sourceDirectory = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "org", "opengis", "cite", "cdb10", "fixtures");
    private static Path validDefaultsXmlFile = sourceDirectory.resolve(Paths.get("valid", "Defaults.xml"));
    private static Path invalidDefaultsXmlFile = sourceDirectory.resolve(Paths.get("invalid", "DefaultsInvalid.xml"));
    private static Path defaultsXsdFile = sourceDirectory.resolve(Paths.get("schema", "Defaults.xsd"));

    public VerifyDefaultsXmlStructureTests() {
        testSuite = new DefaultsXmlStructureTests();
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void createMetadataDirectory() throws IOException {
        metadata = Files.createDirectories(cdb_root.resolve(Paths.get("Metadata")));
        schema = Files.createDirectories(cdb_root.resolve(Paths.get(String.valueOf(metadata), "Schema")));
    }

    @Test
    public void verifyModelComponentsXmlExist_ModelComponentsXmlDoesNotExist() throws IOException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Defaults.xml file.");

        // execute
        testSuite.verifyDefaultsXmlFileExist();
    }

    @Test
    public void verifyDefaultsXmlExist_DefaultsXmlDoesExist() throws IOException {
        // setup
        Files.createFile(metadata.resolve(Paths.get("Defaults.xml")));

        // execute
        testSuite.verifyDefaultsXmlFileExist();
    }


    @Test
    public void verifyDefaultsXmlIsValid_XmlIsValid() throws IOException {
        // setup
        Files.copy(validDefaultsXmlFile, metadata.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schema.resolve("Defaults.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyDefaultsXmlFileIsValid();
    }

    @Test
    public void verifyDefaultsXmlIsValid_XmlIsNotValid() throws IOException {
        // setup
        Files.copy(invalidDefaultsXmlFile, metadata.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schema.resolve("Defaults.xsd"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Defaults.xml does not contain valid XML. Error: cvc-complex-type.4: Attribute 'version' must appear on element 'Default_Value_Table'.");

        // execute
        testSuite.verifyDefaultsXmlFileIsValid();
    }
}
