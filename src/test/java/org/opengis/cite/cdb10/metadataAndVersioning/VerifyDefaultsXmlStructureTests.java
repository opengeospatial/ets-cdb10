package org.opengis.cite.cdb10.metadataAndVersioning;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-03.
 */
public class VerifyDefaultsXmlStructureTests extends MetadataTestFixture<DefaultsXmlStructureTests> {

    private final static Path XSD_FILE = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Defaults.xsd"));

    private final static Path VALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Defaults.xml"));

    private final static Path INVALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "DefaultsInvalid.xml"));
    private final static Path ELEMENTS_OUT_OF_SEQUENCE_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "DefaultsInvalidElementSequence.xml"));
    private final static Path INVALID_R_W_TYPE_VALUE_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "DefaultsInvalidR_W_TypeValue.xml"));
    private final static Path NAME_NOT_UNIQUE_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "DefaultsInvalidNameNotUnique.xml"));
    private final static Path INVALID_TYPE_VALUE_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "DefaultsInvalidTypeValue.xml"));

    public VerifyDefaultsXmlStructureTests() {
        testSuite = new DefaultsXmlStructureTests();
    }

    @Test
    public void verifyDefaultsXmlExists_DoesNotExist() throws IOException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Defaults.xml file.");

        // execute
        testSuite.verifyDefaultsXmlFileExists();
    }

    @Test
    public void verifyDefaultsXmlExists_DoesExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Defaults.xml")));
        Files.createFile(schemaFolder.resolve(Paths.get("Defaults.xsd")));

        // execute
        testSuite.verifyDefaultsXmlFileExists();
    }


    @Test
    public void verifyDefaultsXmlAgainstSchema_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyDefaultsXmlAgainstSchema();
    }

    @Test
    public void verifyDefaultsXmlAgainstSchema_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(INVALID_FILE, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Defaults.xml does not contain valid XML. Errors: cvc-complex-type.4: Attribute " +
                "'version' must appear on element 'Default_Value_Table'.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyDefaultsXmlAgainstSchema();
    }

    @Test
    public void verifyDefaultsXmlIsValid_ElementsOutOfSequence() throws IOException, SAXException {
        // setup
        Files.copy(ELEMENTS_OUT_OF_SEQUENCE_FILE, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        String expectedMessage = "cvc-complex-type.2.4.a: Invalid content was found starting with element 'R_W_Type'. " +
                "One of '{\"http://www.CDB-Spec.org/Schema/Defaults/1.0\":Value}' is expected.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyDefaultsXmlAgainstSchema();
    }

    // check all <R_W_Type> have (‘R’ or ‘W’.) as values
    @Test
    public void verifyElementR_W_TypeHasValidValues_ValidValues() throws IOException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyDefaultsXmlElementR_W_TypeHasValidValues();
    }

    @Test
    public void verifyElementR_W_TypeHasValidValues_InvalidValues() throws IOException {
        // setup
        Files.copy(INVALID_R_W_TYPE_VALUE_FILE, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Defaults.xml element R_W_Type should have a value of R or W. Value 'K' is not valid.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyDefaultsXmlElementR_W_TypeHasValidValues();
    }

    // check The default value name is a unique name identifying a default value for a given dataset.
    @Test
    public void verifyElementNameIsUniqueForEachDataset_IsUnique() throws IOException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyDefaultsXmlNameIsUniqueForEachDataset();
    }


    @Test
    public void verifyElementNameIsUniqueForEachDataset_IsNotUnique() throws IOException {
        // setup
        Files.copy(NAME_NOT_UNIQUE_FILE, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Defaults.xml element Name should be unique under each Dataset. " +
                "'Default_Primary_Elevation_Control' is not unique.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyDefaultsXmlNameIsUniqueForEachDataset();
    }

    // check Each default value has a type. Valid default value data types are “float”, “integer” and “string”.
    @Test
    public void verifyElementTypeHasValidValue_ValidValues() throws IOException {
        // setup
        Files.copy(VALID_FILE, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyDefaultsXmlElementTypeHasValidValue();
    }

    @Test
    public void verifyElementTypeHasValidValue_InvalidValues() throws IOException {
        // setup
        Files.copy(INVALID_TYPE_VALUE_FILE, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(XSD_FILE, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Defaults.xml element Type should have a value of 'float', " +
                "'integer' or 'string'. Value 'invalid_type' is not valid. expected [true] but found [false]";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyDefaultsXmlElementTypeHasValidValue();
    }
}
