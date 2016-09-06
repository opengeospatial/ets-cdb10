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
    private static Path invalidElementsOutOfSequenceDefaultsXmlFile = sourceDirectory.resolve(Paths.get("invalid", "DefaultsInvalidElementSequence.xml"));
    private static Path invalidR_W_TypeValueDefaultsXmlFile = sourceDirectory.resolve(Paths.get("invalid", "DefaultsInvalidR_W_TypeValue.xml"));
    private static Path invalidNameNotUniqueDefaultsXmlFile = sourceDirectory.resolve(Paths.get("invalid", "DefaultsInvalidNameNotUnique.xml"));
    private static Path invalidTypeValueDefaultsXmlFile = sourceDirectory.resolve(Paths.get("invalid", "DefaultsInvalidTypeValue.xml"));
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

        String expectedMessage = "Defaults.xml does not contain valid XML. Errors: cvc-complex-type.4: Attribute " +
                "'version' must appear on element 'Default_Value_Table'.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyDefaultsXmlFileIsValid();
    }

    @Test
    public void verifyDefaultsXmlIsValid_ElementsOutOfSequence() throws IOException {
        // setup
        Files.copy(invalidElementsOutOfSequenceDefaultsXmlFile, metadata.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schema.resolve("Defaults.xsd"), REPLACE_EXISTING);

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
        Files.copy(validDefaultsXmlFile, metadata.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schema.resolve("Defaults.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyDefaultsXmlElementR_W_TypeHasValidValues();
    }

    @Test
    public void verifyElementR_W_TypeHasValidValues_InvalidValues() throws IOException {
        // setup
        Files.copy(invalidR_W_TypeValueDefaultsXmlFile, metadata.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schema.resolve("Defaults.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Defaults.xml element R_W_Type should have a value of R or W. Value K is not valid.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyDefaultsXmlElementR_W_TypeHasValidValues();
    }

    // check The default value name is a unique name identifying a default value for a given dataset.
//    @Test
//    public void verifyElementNameIsUniqueForEachDataset_IsUnique() throws IOException {
//        // setup
//        Files.copy(validDefaultsXmlFile, metadata.resolve("Defaults.xml"), REPLACE_EXISTING);
//        Files.copy(defaultsXsdFile, schema.resolve("Defaults.xsd"), REPLACE_EXISTING);
//
//        // execute
//        testSuite.verifyDefaultsXmlNameIsUniqueForEachDataset();
//    }
//
//
//    @Test
//    public void verifyElementNameIsUniqueForEachDataset_IsNotUnique() throws IOException {
//        // setup
//        Files.copy(invalidNameNotUniqueDefaultsXmlFile, metadata.resolve("Defaults.xml"), REPLACE_EXISTING);
//        Files.copy(defaultsXsdFile, schema.resolve("Defaults.xsd"), REPLACE_EXISTING);
//
//        String expectedMessage = "Defaults.xml element Name should be unique under each Dataset";
//
//        expectedException.expect(AssertionError.class);
//        expectedException.expectMessage(expectedMessage);
//
//        // execute
//        testSuite.verifyDefaultsXmlNameIsUniqueForEachDataset();
//    }

    // check Each default value has a type. Valid default value data types are “float”, “integer” and “string”.
    @Test
    public void verifyElementTypeHasValidValue_ValidValues() throws IOException {
        // setup
        Files.copy(validDefaultsXmlFile, metadata.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schema.resolve("Defaults.xsd"), REPLACE_EXISTING);

        // execute
        testSuite.verifyDefaultsXmlElementTypeHasValidValue();
    }

    @Test
    public void verifyElementTypeHasValidValue_InvalidValues() throws IOException {
        // setup
        Files.copy(invalidTypeValueDefaultsXmlFile, metadata.resolve("Defaults.xml"), REPLACE_EXISTING);
        Files.copy(defaultsXsdFile, schema.resolve("Defaults.xsd"), REPLACE_EXISTING);

        String expectedMessage = "Defaults.xml element Type should have a value of 'float', 'integer' and 'string'. Value invalid_type is not valid.";

        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedMessage);

        // execute
        testSuite.verifyDefaultsXmlElementTypeHasValidValue();
    }
}
