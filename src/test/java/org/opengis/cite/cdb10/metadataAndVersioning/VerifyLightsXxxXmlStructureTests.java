package org.opengis.cite.cdb10.metadataAndVersioning;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by martin on 2016-09-12.
 */
public class VerifyLightsXxxXmlStructureTests extends MetadataTestFixture<LightsXxxXmlStructureTests> {

	private final static Path XSD_FILE = SOURCE_DIRECTORY.resolve(Paths.get("schema", "Lights_Tuning.xsd"));

	private final static Path VALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("valid", "Lights_Client.xml"));

	private final static Path INVALID_FILE = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "Lights_ClientInvalid.xml"));

	private final static Path INVALID_DIRECTIONALITY_VALUE_FILE = SOURCE_DIRECTORY
		.resolve(Paths.get("invalid", "Lights_ClientInvalidDirectionalityValue.xml"));

	private final static Path INVALID_FREQUENCY_VALUE_FILE = SOURCE_DIRECTORY
		.resolve(Paths.get("invalid", "Lights_ClientInvalidFrequencyValue.xml"));

	private final static Path INTENSITY_OUT_OF_RANGE_FILE = SOURCE_DIRECTORY
		.resolve(Paths.get("invalid", "Lights_ClientInvalidIntensityOutOfRange.xml"));

	private final static Path RESIDUAL_INTENSITY_OUT_OF_RANGE_FILE = SOURCE_DIRECTORY
		.resolve(Paths.get("invalid", "Lights_ClientInvalidResidual_IntensityOutOfRange.xml"));

	private final static Path DUTY_CYCLE_OUT_OF_RANGE_FILE = SOURCE_DIRECTORY
		.resolve(Paths.get("invalid", "Lights_ClientInvalidDuty_CycleOutOfRange.xml"));

	private final static Path COLOR_VALUE_IS_OUT_OF_RANGE_FILE = SOURCE_DIRECTORY
		.resolve(Paths.get("invalid", "Lights_ClientInvalidColorValueIsOutOfRange.xml"));

	public VerifyLightsXxxXmlStructureTests() {
		testSuite = new LightsXxxXmlStructureTests();
	}

	@Test
	public void verifyLightsXmlFileNameIsValid_IsNotValid() throws Exception {
		// setup
		Files.createFile(metadataFolder.resolve(Paths.get("Lights_123451234512345123451234512345.xml")));

		String expectedMessage = "[Lights_123451234512345123451234512345.xml] are not a valid file name(s) the file name must start "
				+ "with 'Lights_', can only be a maximum of 32 characters and contain letters, numbers, underscores and dashes.";

		expectedException.expect(AssertionError.class);
		expectedException.expectMessage(expectedMessage);

		// execute
		testSuite.verifyLightsXmlFileNameIsValid();
	}

	@Test
	public void verifyLightsXmlFileNameIsValid_IsValid() throws IOException {
		// setup
		Files.createFile(metadataFolder.resolve(Paths.get("Lights_Client.xml")));

		// execute
		testSuite.verifyLightsXmlFileNameIsValid();
	}

	@Test
	public void verifyLightsXmlFileNameIsValidWithMultipleFiles_IsNotValid() throws Exception {
		// setup
		Files.createFile(metadataFolder.resolve(Paths.get("Lights_123451234512345123451234512345_example1.xml")));
		Files.createFile(metadataFolder.resolve(Paths.get("Lights_123451234512345123451234512345_example2.xml")));

		String expectedMessage = "[Lights_123451234512345123451234512345_example1.xml, "
				+ "Lights_123451234512345123451234512345_example2.xml] are not a valid file name(s) the file name must start with "
				+ "'Lights_', can only be a maximum of 32 characters and contain letters, numbers, underscores and dashes.";

		expectedException.expect(AssertionError.class);
		expectedException.expectMessage(expectedMessage);

		// execute
		testSuite.verifyLightsXmlFileNameIsValid();
	}

	@Test
	public void verifyLightsXmlFileNameIsValidWithMultipleFiles_IsValid() throws IOException {
		// setup
		Files.createFile(metadataFolder.resolve(Paths.get("Lights_Client_example1.xml")));
		Files.createFile(metadataFolder.resolve(Paths.get("Lights_Client_example2.xml")));

		// execute
		testSuite.verifyLightsXmlFileNameIsValid();
	}

	@Test
	public void verifyLightsXxxXmlAgainstSchema_XmlIsValid() throws IOException, SAXException {
		// setup
		Files.copy(VALID_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);
		Files.copy(XSD_FILE, schemaFolder.resolve("Lights_Tuning.xsd"), REPLACE_EXISTING);

		// execute
		testSuite.verifyLightsXxxXmlAgainstSchema();
	}

	@Test
	public void verifyLightsXxxXmlAgainstSchema_XmlIsNotValid() throws IOException, SAXException {
		// setup
		Files.copy(INVALID_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);
		Files.copy(XSD_FILE, schemaFolder.resolve("Lights_Tuning.xsd"), REPLACE_EXISTING);

		String expectedMessage = "Lights_Client.xml does not validate against its XML Schema file. "
				+ "Errors: cvc-pattern-valid: Value 'Invalid' is not facet-valid with respect to pattern "
				+ "'Omnidirectional|Directional|Bidirectional' for type '#AnonType_DirectionalityLight'., "
				+ "cvc-type.3.1.3: The value 'Invalid' of element 'Directionality' is not valid.";

		expectedException.expect(AssertionError.class);
		expectedException.expectMessage(expectedMessage);

		// execute
		testSuite.verifyLightsXxxXmlAgainstSchema();
	}

	@Test
	public void verifyLightsXxxXmlElementIntensityIsInRange_InRange() throws IOException {
		// setup
		Files.copy(VALID_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);

		// execute
		testSuite.verifyLightsXxxXmlElementIntensityIsInRange();
	}

	@Test
	public void verifyLightsXxxXmlElementIntensityIsInRange_OutOfRange() throws IOException {
		// setup
		Files.copy(INTENSITY_OUT_OF_RANGE_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);

		String expectedMessage = "'Lights_Client.xml' Intensity elements value can range from 0.0 to 1.0. "
				+ "Values [-0.1, 1.01] are not valid. expected [0] but found [2]";

		expectedException.expect(AssertionError.class);
		expectedException.expectMessage(expectedMessage);

		// execute
		testSuite.verifyLightsXxxXmlElementIntensityIsInRange();
	}

	@Test
	public void verifyLightsXxxXmlDirectionalityValueIsValid_Valid() throws IOException {
		// setup
		Files.copy(VALID_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);

		// execute
		testSuite.verifyLightsXxxXmlDirectionalityValueIsValid();
	}

	@Test
	public void verifyLightsXxxXmlDirectionalityValueIsValid_Invalid() throws IOException {
		// setup
		Files.copy(INVALID_DIRECTIONALITY_VALUE_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);

		String expectedMessage = "'Lights_Client.xml' element Directionality should have a value of 'Omnidirectional', "
				+ "'Directional' or 'Bidirectional'. Value 'INVALID_VALUE' is not valid. expected [true] but found [false]";

		expectedException.expect(AssertionError.class);
		expectedException.expectMessage(expectedMessage);

		// execute
		testSuite.verifyLightsXxxXmlDirectionalityValueIsValid();
	}

	@Test
	public void verifyLightsXxxXmlElementResidualIntensityIsInRange_InRange() throws IOException {
		// setup
		Files.copy(VALID_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);

		// execute
		testSuite.verifyLightsXxxXmlElementResidualIntensityIsInRange();
	}

	@Test
	public void verifyLightsXxxXmlElementResidualIntensityIsInRange_OutOfRange() throws IOException {
		// setup
		Files.copy(RESIDUAL_INTENSITY_OUT_OF_RANGE_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);

		String expectedMessage = "'Lights_Client.xml' Residual_Intensity elements value can range from 0.0 to 1.0. "
				+ "Values [-0.1, 1.01] are not valid. expected [0] but found [2]";

		expectedException.expect(AssertionError.class);
		expectedException.expectMessage(expectedMessage);

		// execute
		testSuite.verifyLightsXxxXmlElementResidualIntensityIsInRange();
	}

	@Test
	public void verifyLightsXxxXmlElementDuty_CycleIsInRange_InRange() throws IOException {
		// setup
		Files.copy(VALID_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);

		// execute
		testSuite.verifyLightsXxxXmlElementDuty_CycleIsInRange();
	}

	@Test
	public void verifyLightsXxxXmlElementDuty_CycleIsInRange_OutOfRange() throws IOException {
		// setup
		Files.copy(DUTY_CYCLE_OUT_OF_RANGE_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);

		String expectedMessage = "'Lights_Client.xml' Duty_Cycle elements value can range from 0.0 to 1.0. "
				+ "Values [-0.1, 1.01] are not valid. expected [0] but found [2]";

		expectedException.expect(AssertionError.class);
		expectedException.expectMessage(expectedMessage);

		// execute
		testSuite.verifyLightsXxxXmlElementDuty_CycleIsInRange();
	}

	@Test
	public void verifyLightsXxxXmlFrequencyValueIsValid_Valid() throws IOException {
		// setup
		Files.copy(VALID_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);

		// execute
		testSuite.verifyLightsXxxXmlFrequencyValueIsValid();
	}

	@Test
	public void verifyLightsXxxXmlFrequencyValueIsValid_Invalid() throws IOException {
		// setup
		Files.copy(INVALID_FREQUENCY_VALUE_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);

		String expectedMessage = "'Lights_Client.xml' Duty_Cycle elements value can range from 0.0 to 1.0. "
				+ "Values [-0.1] are not valid. expected [0] but found [1]";

		expectedException.expect(AssertionError.class);
		expectedException.expectMessage(expectedMessage);

		// execute
		testSuite.verifyLightsXxxXmlFrequencyValueIsValid();
	}

	@Test
	public void verifyLightsXxxXmlColorIsInRange_InRange() throws IOException {
		// setup
		Files.copy(VALID_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);

		// execute
		testSuite.verifyLightsXxxXmlColorIsInRange();
	}

	@Test
	public void verifyLightsXxxXmlColorIsInRange_NotInRange() throws IOException {
		// setup
		Files.copy(COLOR_VALUE_IS_OUT_OF_RANGE_FILE, metadataFolder.resolve("Lights_Client.xml"), REPLACE_EXISTING);

		String expectedMessage = "'Lights_Client.xml' Duty_Cycle elements value can range from 0.0 to 1.0. "
				+ "Values [1.1, -0.1] are not valid. expected [0] but found [2]";

		expectedException.expect(AssertionError.class);
		expectedException.expectMessage(expectedMessage);

		// execute
		testSuite.verifyLightsXxxXmlColorIsInRange();
	}

}
