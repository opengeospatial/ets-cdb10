package org.opengis.cite.cdb10.metadataAndVersioning;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-08.
 */
public class VerifyCDBAttributesXmlStructureTests extends MetadataTestFixture<CDBAttributesXmlStructureTests> {

    private final static Path XSD_FILE = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Vector_Attributes.xsd"));

    private final static Path VALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("valid", "CDB_Attributes.xml"));

    private final static Path INVALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "CDB_AttributesInvalid.xml"));
    private final static Path CODE_NOT_INTEGER_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "CDB_AttributesInvalidAttributeCodeNotInteger.xml"));
    private final static Path SYMBOL_IS_NOT_UNIQUE_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "CDB_AttributesInvalidSymbolNotUnique.xml"));
    private final static Path INVALID_TYPE_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "CDB_AttributesInvalidType.xml"));
    private final static Path INVALID_SCALER_CODE_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "CDB_AttributesInvalidScalerCode.xml"));
    private final static Path INVALID_UNIT_CODE_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "CDB_AttributesInvalidUnitCode.xml"));

    public VerifyCDBAttributesXmlStructureTests() {
        testSuite = new CDBAttributesXmlStructureTests();
    }

    @Test
    public void verifyCDBAttributesXmlExists_MetadataFolderDoesNotExist() throws IOException {
        // setup
        Files.delete(cdb_root.resolve(Paths.get("Metadata", "Schema")));
        Files.delete(cdb_root.resolve(Paths.get("Metadata")));

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory is missing.");

        // execute
        testSuite.verifyCDBAttributesXmlFileExists();
    }

    @Test
    public void verifyCDBAttributesXmlExists_DoesNotExist() throws IOException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain CDB_Attributes.xml file.");

        // execute
        testSuite.verifyCDBAttributesXmlFileExists();
    }

    @Test
    public void verifyCDBAttributesXmlExists_DoesExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("CDB_Attributes.xml")));
        Files.createFile(schemaFolder.resolve(Paths.get("Vector_Attributes.xsd")));

        // execute
        testSuite.verifyCDBAttributesXmlFileExists();
    }

    @Test
    public void verifyCDBAttributesXmlAgainstSchema_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyCDBAttributesXmlAgainstSchema();
    }

    @Test
    public void verifyCDBAttributesXmlAgainstSchema_FileDoesNotExist() throws IOException, SAXException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain CDB_Attributes.xml file.");

        // execute
        testSuite.verifyCDBAttributesXmlAgainstSchema();
    }

    @Test
    public void verifyCDBAttributesXmlAgainstSchema_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(INVALID_FILE, metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

        String expectedMessage = "CDB_Attributes.xml does not contain valid XML. " +
                "Errors: cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to minLength " +
                "'1' for type '#AnonType_NameAttribute'., cvc-type.3.1.3: The value '' of element 'Name' is not valid., " +
                "cvc-complex-type.2.4.a: Invalid content was found starting with element 'Format'. One of " +
                "'{\"http://www.CDB-Spec.org/Schema/Vector_Attributes/1.2\":Type}' is expected.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyCDBAttributesXmlAgainstSchema();
    }

    @Test
    public void verifyCodeIsAnInteger_CodeIsAnInteger() throws IOException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyCDBAttributesXmlCodeIsAnInteger();
    }

    @Test
    public void verifyCodeIsAnInteger_XsdFileDoesNotExist() throws IOException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Vector_Attributes.xsd file.");

        // execute
        testSuite.verifyCDBAttributesXmlCodeIsAnInteger();
    }

    @Test
    public void verifyCodeIsAnInteger_CodeIsNotAnInteger() throws IOException {
        // setup
        Files.copy(CODE_NOT_INTEGER_FILE, metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

        String expectedMessage = "CDB_Attributes.xml attribute code should be an integer. Code 'not_integer' is not valid.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyCDBAttributesXmlCodeIsAnInteger();
    }

    @Test
    public void verifySymbolIsUnique_SymbolIsUnique() throws IOException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyCDBAttributesXmlSymbolIsUnique();
    }

    @Test
    public void verifySymbolIsUnique_SymbolIsNotUnique() throws IOException {
        // setup
        Files.copy(SYMBOL_IS_NOT_UNIQUE_FILE, metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

        String expectedMessage = "CDB_Attributes.xml element Attribute should have unique symbols. " +
                "Symbol 'AEAC' is not unique. expected [1] but found [2]";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyCDBAttributesXmlSymbolIsUnique();
    }

    @Test
    public void verifyValueHasAValidType_IsValid() throws IOException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyCDBAttributesXmlValueHasAValidType();
    }

    @Test
    public void verifyValueHasAValidType_IsNotValid() throws IOException {
        // setup
        Files.copy(INVALID_TYPE_FILE, metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

        String expectedMessage = "CDB_Attributes.xml element Type should have a value of " +
                "'Text', 'Numeric' or 'Boolean'. Type 'Invalid_type' is not valid. expected [true] but found [false]";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyCDBAttributesXmlValueHasAValidType();
    }

    @Test
    public void verifyScalerCodeIsValid_IsValid() throws IOException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyCDBAttributesXmlScalerCodeIsValid();
    }

    @Test
    public void verifyScalerCodeIsValid_IsNotValid() throws IOException {
        // setup
        Files.copy(INVALID_SCALER_CODE_FILE, metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

        String expectedMessage = "CDB_Attributes.xml Scaler code should be a positive integer. " +
                "Code '-1' is not valid. expected [true] but found [false]";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyCDBAttributesXmlScalerCodeIsValid();
    }

    @Test
    public void verifyUnitCodeIsValid_IsValid() throws IOException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyCDBAttributesXmlUnitCodeIsValid();
    }

    @Test
    public void verifyUnitCodeIsValid_IsNotValid() throws IOException {
        // setup
        Files.copy(INVALID_UNIT_CODE_FILE, metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

        String expectedMessage = "CDB_Attributes.xml Unit code should be a positive integer. " +
                "Code '-1' is not valid. expected [true] but found [false]";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyCDBAttributesXmlUnitCodeIsValid();
    }
}
