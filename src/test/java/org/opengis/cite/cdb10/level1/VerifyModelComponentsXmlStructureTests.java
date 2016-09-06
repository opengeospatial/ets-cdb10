package org.opengis.cite.cdb10.level1;

import org.junit.Test;
import org.opengis.cite.cdb10.CDBStructure.ModelComponentsXmlStructureTests;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-02.
 */
public class VerifyModelComponentsXmlStructureTests extends MetadataTestFixture<ModelComponentsXmlStructureTests> {

    private static Path sourceDirectory = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "org", "opengis", "cite", "cdb10", "fixtures");
    private static Path validModelComponentsXmlFile = sourceDirectory.resolve(Paths.get("valid", "Model_Components.xml"));
    private static Path invalidModelComponentsXmlFile = sourceDirectory.resolve(Paths.get("invalid", "Model_ComponentsInvalid.xml"));
    private static Path modelComponentsXsdFile = sourceDirectory.resolve(Paths.get("schema", "Model_Components.xsd"));

    public VerifyModelComponentsXmlStructureTests() {
        testSuite = new ModelComponentsXmlStructureTests();
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
        Files.createFile(metadataFolder.resolve(Paths.get("Model_Components.xml")));

        // execute
        testSuite.verifyModelComponentsXmlFileExist();
    }

    @Test
    public void verifyModelComponentsXmlIsValid_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(validModelComponentsXmlFile, metadataFolder.resolve("Model_Components.xml"), REPLACE_EXISTING);
        Files.copy(modelComponentsXsdFile, schemaFolder.resolve("Model_Components.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyModelComponentsXmlFileIsValid();
    }

    @Test
    public void verifyModelComponentsXmlIsValid_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(invalidModelComponentsXmlFile, metadataFolder.resolve("Model_Components.xml"), REPLACE_EXISTING);
        Files.copy(modelComponentsXsdFile, schemaFolder.resolve("Model_Components.xsd"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Model_Components.xml does not contain valid XML. Errors: cvc-complex-type.4: Attribute 'name' must appear on element 'Component'., cvc-complex-type.2.4.b: The content of element 'Component' is not complete. One of '{\"CDB\":Description}' is expected.");

        // execute
        testSuite.verifyModelComponentsXmlFileIsValid();
    }
}
