package org.opengis.cite.cdb10.level1;

import org.junit.Test;
import org.opengis.cite.cdb10.CDBStructure.MaterialsXmlStructureTests;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-09.
 */
public class VerifyMaterialsXmlStructureTests extends MetadataTestFixture<MaterialsXmlStructureTests> {

    private final static Path XSD_FILE = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Base_Material_Table.xsd"));

    private final static Path VALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Materials.xml"));

    private final static Path INVALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "MaterialsInvalid.xml"));

    public VerifyMaterialsXmlStructureTests() {
        testSuite = new MaterialsXmlStructureTests();
    }

    @Test
    public void verifyMaterialsXmlFileExists_DoesNotExist() throws IOException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Materials.xml file.");

        // execute
        testSuite.verifyMaterialsXmlFileExists();
    }

    @Test
    public void verifyMaterialsXmlFileExists_DoesExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Materials.xml")));

        // execute
        testSuite.verifyMaterialsXmlFileExists();
    }

    @Test
    public void verifyMaterialsXmlAgainstSchema_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Materials.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Base_Material_Table.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyMaterialsXmlAgainstSchema();
    }

    @Test
    public void verifyMaterialsXmlAgainstSchema_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(INVALID_FILE, metadataFolder.resolve("Materials.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Base_Material_Table.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Materials.xml does not contain valid XML. Errors: cvc-pattern-valid: " +
                "Value 'BMASH' is not facet-valid with respect to pattern '[B][M][_]([A-Za-z0-9_-])+' " +
                "for type '#AnonType_NameBase_Material'., cvc-type.3.1.3: The value 'BMASH' " +
                "of element 'Name' is not valid.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyMaterialsXmlAgainstSchema();
    }
}
