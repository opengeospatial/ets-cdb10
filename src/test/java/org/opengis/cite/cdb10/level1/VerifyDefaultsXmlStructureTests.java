package org.opengis.cite.cdb10.level1;

import org.junit.Test;
import org.opengis.cite.cdb10.CDBStructure.DefaultsXmlStructureTests;
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

    private static Path validDefaultsXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Defaults.xml"));
    private static Path invalidDefaultsXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "DefaultsInvalid.xml"));
    private static Path invalidElementsOutOfSequenceDefaultsXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "DefaultsInvalidElementSequence.xml"));
    private static Path invalidR_W_TypeValueDefaultsXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "DefaultsInvalidR_W_TypeValue.xml"));
    private static Path invalidNameNotUniqueDefaultsXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "DefaultsInvalidNameNotUnique.xml"));
    private static Path invalidTypeValueDefaultsXmlFile = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "DefaultsInvalidTypeValue.xml"));
    private static Path defaultsXsdFile = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Defaults.xsd"));

    public VerifyDefaultsXmlStructureTests() {
        testSuite = new DefaultsXmlStructureTests();
    }

    @Test
    public void verifyDefaultsXmlExist_ModelComponentsXmlDoesNotExist() throws IOException {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Metadata directory should contain Defaults.xml file.");

        // execute
        testSuite.verifyDefaultsXmlFileExist();
    }

    @Test
    public void verifyDefaultsXmlExist_DefaultsXmlDoesExist() throws IOException {
        // setup
        Files.createFile(metadataFolder.resolve(Paths.get("Defaults.xml")));

        // execute
        testSuite.verifyDefaultsXmlFileExist();
    }


    @Test
    public void verifyDefaultsXmlIsValid_XmlIsValid() throws IOException, SAXException {
        // setup
        Files.copy(validDefaultsXmlFile, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyDefaultsXmlFileIsValid();
    }

    @Test
    public void verifyDefaultsXmlIsValid_XmlIsNotValid() throws IOException, SAXException {
        // setup
        Files.copy(invalidDefaultsXmlFile, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Defaults.xml does not contain valid XML. Errors: cvc-complex-type.4: Attribute " +
                "'version' must appear on element 'Default_Value_Table'.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyDefaultsXmlFileIsValid();
    }

    @Test
    public void verifyDefaultsXmlIsValid_ElementsOutOfSequence() throws IOException, SAXException {
        // setup
        Files.copy(invalidElementsOutOfSequenceDefaultsXmlFile, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        String expectedMessage = "cvc-complex-type.2.4.a: Invalid content was found starting with element 'R_W_Type'. " +
                "One of '{\"http://www.CDB-Spec.org/Schema/Defaults/1.0\":Value}' is expected.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyDefaultsXmlFileIsValid();
    }

    // check all <R_W_Type> have (‘R’ or ‘W’.) as values
    @Test
    public void verifyElementR_W_TypeHasValidValues_ValidValues() throws IOException {
        // setup
        Files.copy(validDefaultsXmlFile, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyDefaultsXmlElementR_W_TypeHasValidValues();
    }

    @Test
    public void verifyElementR_W_TypeHasValidValues_InvalidValues() throws IOException {
        // setup
        Files.copy(invalidR_W_TypeValueDefaultsXmlFile, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

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
        Files.copy(validDefaultsXmlFile, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyDefaultsXmlNameIsUniqueForEachDataset();
    }


    @Test
    public void verifyElementNameIsUniqueForEachDataset_IsNotUnique() throws IOException {
        // setup
        Files.copy(invalidNameNotUniqueDefaultsXmlFile, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Defaults.xml element Name should be unique under each Dataset. 'Default_Primary_Elevation_Control' is not unique.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyDefaultsXmlNameIsUniqueForEachDataset();
    }

    // check Each default value has a type. Valid default value data types are “float”, “integer” and “string”.
    @Test
    public void verifyElementTypeHasValidValue_ValidValues() throws IOException {
        // setup
        Files.copy(validDefaultsXmlFile, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyDefaultsXmlElementTypeHasValidValue();
    }

    @Test
    public void verifyElementTypeHasValidValue_InvalidValues() throws IOException {
        // setup
        Files.copy(invalidTypeValueDefaultsXmlFile, metadataFolder.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schemaFolder.resolve("Defaults.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Defaults.xml element Type should have a value of 'float', 'integer' and 'string'. Value 'invalid_type' is not valid.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyDefaultsXmlElementTypeHasValidValue();
    }
}
