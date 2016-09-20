package org.opengis.cite.cdb10.metadataAndVersioning;

import org.junit.Test;
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
    private final static Path NAME_NOT_UNIQUE_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "MaterialsInvalidNameNotUnique.xml"));
    private final static Path MISSING_NAME_ELEMENT_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "MaterialsInvalidMissingNameElement.xml"));
    private final static Path INVALID_NAME_VALUE_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "MaterialsInvalidValueForNameElement.xml"));

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
        Files.createFile(schemaFolder.resolve(Paths.get("Base_Material_Table.xsd")));

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

    @Test
    public void verifyMaterialsXmlElementNameIsUnique_IsUnique() throws IOException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Materials.xml"), REPLACE_EXISTING);
        Files.createFile(schemaFolder.resolve(Paths.get("Base_Material_Table.xsd")));

        // execute
        testSuite.verifyMaterialsXmlElementNameIsUnique();
    }

    @Test
    public void verifyMaterialsXmlElementNameIsUnique_IsNotUnique() throws IOException {
        // setup
        Files.copy(NAME_NOT_UNIQUE_FILE, metadataFolder.resolve("Materials.xml"), REPLACE_EXISTING);
        Files.createFile(schemaFolder.resolve(Paths.get("Base_Material_Table.xsd")));

        String expectedMessage = "Materials.xml element Name should be unique. " +
                "'BM_ASH-VOLCANIC' is not unique. expected [1] but found [2]";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyMaterialsXmlElementNameIsUnique();
    }

    @Test
    public void verifyMaterialsXmlAllBaseMaterialElementsHaveAChildNodeName_TheyDo() throws IOException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Materials.xml"), REPLACE_EXISTING);
        Files.createFile(schemaFolder.resolve(Paths.get("Base_Material_Table.xsd")));

        // execute
        testSuite.verifyMaterialsXmlAllBaseMaterialElementsHaveAChildNodeName();
    }

    @Test
    public void verifyMaterialsXmlAllBaseMaterialElementsHaveAChildNodeName_TheyDoNot() throws IOException {
        // setup
        Files.copy(MISSING_NAME_ELEMENT_FILE, metadataFolder.resolve("Materials.xml"), REPLACE_EXISTING);
        Files.createFile(schemaFolder.resolve(Paths.get("Base_Material_Table.xsd")));

        String expectedMessage = "Materials.xml element Base_Material requires a " +
                "child element Name. expected [0] but found [1]";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyMaterialsXmlAllBaseMaterialElementsHaveAChildNodeName();
    }

    @Test
    public void verifyMaterialsXmlBaseMaterialNameIsValid_IsValid() throws Exception {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Materials.xml"), REPLACE_EXISTING);
        Files.createFile(schemaFolder.resolve(Paths.get("Base_Material_Table.xsd")));

        // execute
        testSuite.verifyMaterialsXmlBaseMaterialNameIsValid();
    }

    @Test
    public void verifyMaterialsXmlBaseMaterialNameIsValid_IsNotValid() throws Exception {
        // setup
        Files.copy(INVALID_NAME_VALUE_FILE, metadataFolder.resolve("Materials.xml"), REPLACE_EXISTING);
        Files.createFile(schemaFolder.resolve(Paths.get("Base_Material_Table.xsd")));

        String expectedMessage = "Materials.xml element Name is always in format \"BM__*\", " +
                "has a maximum of 32 characters, and can only contain letters, digits, " +
                "underscores, and hyphens. " +
                "[ASH, BM_ASH-VOLCANIC*, BM_12345123451234512345123451234512345] " +
                "do not conform expected [0] but found [3]";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyMaterialsXmlBaseMaterialNameIsValid();
    }
}
