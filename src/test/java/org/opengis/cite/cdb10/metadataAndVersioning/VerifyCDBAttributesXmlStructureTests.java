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
		this.testSuite = new CDBAttributesXmlStructureTests();
	}

	@Test
	public void verifyCDBAttributesXmlAgainstSchema_XmlIsValid() throws IOException, SAXException {
		// setup
		Files.copy(VALID_FILE, this.metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
		Files.copy(XSD_FILE, this.schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

		// execute
		this.testSuite.verifyCDBAttributesXmlAgainstSchema();
	}

	@Test
	public void verifyCDBAttributesXmlAgainstSchema_XmlIsNotValid() throws IOException, SAXException {
		// setup
		Files.copy(INVALID_FILE, this.metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
		Files.copy(XSD_FILE, this.schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

		String expectedMessage = "CDB_Attributes.xml does not validate against its XML Schema file.";

		this.expectedException.expect(AssertionError.class);
		this.expectedException.expectMessage(expectedMessage);

		// execute
		this.testSuite.verifyCDBAttributesXmlAgainstSchema();
	}

	@Test
	public void verifyCodeIsAnInteger_CodeIsAnInteger() throws IOException {
		// setup
		Files.copy(VALID_FILE, this.metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
		Files.copy(XSD_FILE, this.schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

		// execute
		this.testSuite.verifyCDBAttributesXmlCodeIsAnInteger();
	}

	@Test
	public void verifyCodeIsAnInteger_XsdFileDoesNotExist() throws IOException {
		// setup
		Files.copy(VALID_FILE, this.metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);

		this.expectedException.expect(AssertionError.class);
		this.expectedException.expectMessage("Metadata directory should contain Vector_Attributes.xsd file.");

		// execute
		this.testSuite.verifyCDBAttributesXmlCodeIsAnInteger();
	}

	@Test
	public void verifyCodeIsAnInteger_CodeIsNotAnInteger() throws IOException {
		// setup
		Files.copy(CODE_NOT_INTEGER_FILE, this.metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
		Files.copy(XSD_FILE, this.schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

		String expectedMessage = "CDB_Attributes.xml attribute code should be an integer. Code 'not_integer' is not valid.";

		this.expectedException.expect(AssertionError.class);
		this.expectedException.expectMessage(expectedMessage);

		// execute
		this.testSuite.verifyCDBAttributesXmlCodeIsAnInteger();
	}

	@Test
	public void verifySymbolIsUnique_SymbolIsUnique() throws IOException {
		// setup
		Files.copy(VALID_FILE, this.metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
		Files.copy(XSD_FILE, this.schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

		// execute
		this.testSuite.verifyCDBAttributesXmlSymbolIsUnique();
	}

	@Test
	public void verifySymbolIsUnique_SymbolIsNotUnique() throws IOException {
		// setup
		Files.copy(SYMBOL_IS_NOT_UNIQUE_FILE, this.metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
		Files.copy(XSD_FILE, this.schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

		String expectedMessage = "CDB_Attributes.xml element Attribute should have unique symbols. " +
				"Symbol 'AEAC' is not unique. expected [1] but found [2]";

		this.expectedException.expect(AssertionError.class);
		this.expectedException.expectMessage(expectedMessage);

		// execute
		this.testSuite.verifyCDBAttributesXmlSymbolIsUnique();
	}

	@Test
	public void verifyValueHasAValidType_IsValid() throws IOException {
		// setup
		Files.copy(VALID_FILE, this.metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
		Files.copy(XSD_FILE, this.schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

		// execute
		this.testSuite.verifyCDBAttributesXmlValueHasAValidType();
	}

	@Test
	public void verifyValueHasAValidType_IsNotValid() throws IOException {
		// setup
		Files.copy(INVALID_TYPE_FILE, this.metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
		Files.copy(XSD_FILE, this.schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

		String expectedMessage = "CDB_Attributes.xml element Type should have a value of " +
				"'Text', 'Numeric' or 'Boolean'. Type 'Invalid_type' is not valid. expected [true] but found [false]";

		this.expectedException.expect(AssertionError.class);
		this.expectedException.expectMessage(expectedMessage);

		// execute
		this.testSuite.verifyCDBAttributesXmlValueHasAValidType();
	}

	@Test
	public void verifyScalerCodeIsValid_IsValid() throws IOException {
		// setup
		Files.copy(VALID_FILE, this.metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
		Files.copy(XSD_FILE, this.schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

		// execute
		this.testSuite.verifyCDBAttributesXmlScalerCodeIsValid();
	}

	@Test
	public void verifyScalerCodeIsValid_IsNotValid() throws IOException {
		// setup
		Files.copy(INVALID_SCALER_CODE_FILE, this.metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
		Files.copy(XSD_FILE, this.schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

		String expectedMessage = "CDB_Attributes.xml Scaler code should be a positive integer. " +
				"Code '-1' is not valid. expected [true] but found [false]";

		this.expectedException.expect(AssertionError.class);
		this.expectedException.expectMessage(expectedMessage);

		// execute
		this.testSuite.verifyCDBAttributesXmlScalerCodeIsValid();
	}

	@Test
	public void verifyUnitCodeIsValid_IsValid() throws IOException {
		// setup
		Files.copy(VALID_FILE, this.metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
		Files.copy(XSD_FILE, this.schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

		// execute
		this.testSuite.verifyCDBAttributesXmlUnitCodeIsValid();
	}

	@Test
	public void verifyUnitCodeIsValid_IsNotValid() throws IOException {
		// setup
		Files.copy(INVALID_UNIT_CODE_FILE, this.metadataFolder.resolve("CDB_Attributes.xml"), REPLACE_EXISTING);
		Files.copy(XSD_FILE, this.schemaFolder.resolve("Vector_Attributes.xsd"), REPLACE_EXISTING);

		String expectedMessage = "CDB_Attributes.xml Unit code should be a positive integer. " +
				"Code '-1' is not valid. expected [true] but found [false]";

		this.expectedException.expect(AssertionError.class);
		this.expectedException.expectMessage(expectedMessage);

		// execute
		this.testSuite.verifyCDBAttributesXmlUnitCodeIsValid();
	}
}
