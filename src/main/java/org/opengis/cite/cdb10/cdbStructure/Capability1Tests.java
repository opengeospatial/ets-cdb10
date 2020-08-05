package org.opengis.cite.cdb10.cdbStructure;

import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.SuiteAttribute;
import org.opengis.cite.cdb10.util.reference.CdbReference;
import org.opengis.cite.cdb10.util.reference.ComponentSelectorValidator;
import org.opengis.cite.cdb10.util.reference.DisCountryCodesValidator;
import org.opengis.cite.cdb10.util.reference.MovingModelCodesValidator;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Includes various tests of capability 1.
 */
public class Capability1Tests extends CommonFixture {

	public Capability1Tests() {
	}

	/**
	 * Obtains the test subject from the ISuite context. The suite attribute
	 * {@link org.opengis.cite.cdb10.SuiteAttribute#TEST_SUBJECT} should evaluate to
	 * a DOM Document node.
	 *
	 * @param testContext
	 *            The test (group) context.
	 */
	@Override
	@BeforeClass
	public void obtainTestSubject(ITestContext testContext) {
		Object obj = testContext.getSuite().getAttribute(SuiteAttribute.LEVELS.getName());
		if ((null != obj)) {
			ArrayList<Integer> levels = new ArrayList<Integer>(Arrays.asList(Integer[].class.cast(obj)));
			Assert.assertTrue(levels.contains(1),
					"Conformance level 1 will not be checked as it is not included in ics");
		}
		super.obtainTestSubject(testContext);
	}
	
	/**
	 * Validate a first-level Component Selector based on a given Dataset.
	 * @param cs1 String of Component Selector with leading zeros
	 * @param dataset String of Dataset ID with leading zeros
	 * @param errors ArrayList (String) of errors, will be modified in-place
	 */
	protected void validateComponentSelector1(String cs1, String dataset, ArrayList<String> errors) {
		CdbReference references = new CdbReference();
		ComponentSelectorValidator validator = references.buildComponentSelectorValidator();
		
		if (!validator.isValidComponentSelector1ForDataset(cs1, dataset)) {
			errors.add(String.format("Invalid Component Selector 1 (%s) for Dataset (%s)", cs1, dataset));
		}
	}
	
	/**
	 * Validate a second-level Component Selector based on the first-level
	 * Component Selector and Dataset.
	 * @param cs2 String of Component Selector 2 with leading zeros
	 * @param cs1 String of Component Selector 1 with leading zeros
	 * @param dataset String of Dataset ID with leading zeros
	 * @param errors ArrayList (String) of errors, will be modified in-place
	 */
	protected void validateComponentSelector2(String cs2, String cs1, String dataset, ArrayList<String> errors) {
		CdbReference references = new CdbReference();
		ComponentSelectorValidator validator = references.buildComponentSelectorValidator();
		
		if (!validator.isValidComponentSelector2ForDataset(cs2, cs1, dataset)) {
			errors.add(String.format("Invalid Component Selector 2 (%s) for CS1 (%s) and Dataset (%s)", cs2, cs1, dataset));
		}
	}
	
	/**
	 * Validate that a Component Selector is a valid format
	 * @param cs The Component Selector substring
	 * @param index Integer for "1" (CS1) or "2" (CS2)
	 * @param filename The filename being tested, used for errors
	 * @param errors ArrayList (String) of errors, will be modified in-place
	 */
	protected void validateComponentSelectorFormat(String cs, Integer index, String filename, ArrayList<String> errors) {
		if (cs.length() != 3) {
			errors.add(String.format("Component Selector %d should be 3 characters: %s", index, filename));
		}
		
		try {
			Integer csInt = Integer.parseInt(cs);

			if (((csInt < 10) && !cs.substring(0,2).equals("00")) ||
					((csInt < 100) && !cs.substring(0,1).equals("0"))) {
				errors.add(String.format("Invalid padding on CS%d: %s", index, filename));
			}
		}
		catch (NumberFormatException e) {
			errors.add(String.format("Invalid CS%d number format: %s", index, filename));
		}
		catch (StringIndexOutOfBoundsException e) {
			errors.add(String.format("Invalid CS%d length: %s", index, filename));
		}
	}
	
	/**
	 * Validate that a DIS Category directory is a valid format
	 * @param file The Path to the DIS Country directory
	 * @param errors ArrayList (String) of errors, will be modified in-place
	 */
	protected void validateDISCategory(Path file, ArrayList<String> errors) {
		CdbReference references = new CdbReference();
		MovingModelCodesValidator validator = references.buildMovingModelCodesValidator();
		
		String filename = file.getFileName().toString();
		String code = null;
		Integer codeID = null;
		String categoryName = null;
		try {
			code = filename.split("_")[0];
			codeID = Integer.parseInt(code);
			categoryName = filename.split("_")[1];
		}
		catch (NumberFormatException e) {
			errors.add("Invalid number format: " + filename);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			errors.add("Missing kind name: " + filename);
		}

		if (codeID != null) {
			if (codeID < 0) {
				errors.add("Invalid prefix cannot be below 0: " + filename);
			} else if (!validator.isValidCategoryCode(codeID)) {
				errors.add("Invalid DIS Category code: " + filename);
			} else if (!validator.isValidCategoryName(categoryName)) {
				errors.add("Invalid DIS Category name: " + filename);
			} else if (!validator.categoryNameForCode(codeID).equals(categoryName)) {
				errors.add("Invalid DIS Category code/name combination: " + filename);
			}
		}
	}
	
	/**
	 * Validate that a DIS Country directory is a valid format
	 * @param file The Path to the DIS Country directory
	 * @param errors ArrayList (String) of errors, will be modified in-place
	 */
	protected void validateDISCountry(Path file, ArrayList<String> errors) {		
		CdbReference references = new CdbReference();
		DisCountryCodesValidator validator = references.buildDisCountryCodesValidator();
		
		String filename = file.getFileName().toString();
		String code = null;
		Integer codeID = null;
		String countryName = null;
		try {
			Integer splitIndex = filename.indexOf("_");
			if (splitIndex == -1) {
				errors.add("Invalid DIS country code name: " + filename);
			} else {
				code = filename.substring(0, splitIndex);
				codeID = Integer.parseInt(code);
				countryName = filename.substring(splitIndex + 1, filename.length());
			}
		}
		catch (NumberFormatException e) {
			errors.add("Invalid number format: " + filename);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			errors.add("Missing kind name: " + filename);
		}

		if (codeID != null) {
			if (codeID < 0) {
				errors.add("Invalid prefix cannot be below 0: " + filename);
			} else if (!validator.isValidCountryCode(codeID)) {
				errors.add("Invalid DIS Country code: " + filename);
			} else if (!validator.isValidCountryName(countryName)) {
				errors.add("Invalid DIS Country name: " + filename);
			} else if (!validator.countryNameForCode(codeID).equals(countryName)) {
				errors.add("Invalid DIS Country code/name combination: " + filename);
			}
		}
	}
	
	/**
	 * Validate that a DIS Domain directory is a valid format
	 * @param file The Path to the DIS Domain directory
	 * @param errors ArrayList (String) of errors, will be modified in-place
	 */
	protected void validateDISDomain(Path file, ArrayList<String> errors) {
		CdbReference references = new CdbReference();
		MovingModelCodesValidator validator = references.buildMovingModelCodesValidator();
		
		String filename = file.getFileName().toString();
		String code = null;
		Integer codeID = null;
		String name = null;
		try {
			code = filename.split("_")[0];
			codeID = Integer.parseInt(code);
			name = filename.split("_")[1];
		}
		catch (NumberFormatException e) {
			errors.add("Invalid number format: " + filename);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			errors.add("Missing kind name: " + filename);
		}

		if (codeID != null) {
			if (codeID < 0) {
				errors.add("Invalid prefix cannot be below 0: " + filename);
			} else if (!validator.isValidDomainCode(codeID)) {
				errors.add("Invalid DIS Domain code: " + filename);
			} else if (!validator.isValidDomainName(name)) {
				errors.add("Invalid DIS Domain name: " + filename);
			} else if (!validator.domainNameForCode(codeID).equals(name)) {
				errors.add("Invalid DIS Domain code/name combination: " + filename);
			}
		}
	}
	
	/**
	 * Validate that a DIS Entity Kind directory is a valid format
	 * @param file The Path to the DIS Entity Kind directory
	 * @param errors ArrayList (String) of errors, will be modified in-place
	 */
	protected void validateDISEntityKind(Path file, ArrayList<String> errors) {
		CdbReference references = new CdbReference();
		MovingModelCodesValidator validator = references.buildMovingModelCodesValidator();
		
		String filename = file.getFileName().toString();
		String code = null;
		Integer codeID = null;
		String name = null;
		try {
			code = filename.split("_")[0];
			codeID = Integer.parseInt(code);
			name = filename.split("_")[1];
		}
		catch (NumberFormatException e) {
			errors.add("Invalid number format: " + filename);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			errors.add("Missing kind name: " + filename);
		}

		if (codeID != null) {
			if (codeID < 0) {
				errors.add("Invalid prefix cannot be below 0: " + filename);
			} else if (!validator.isValidKindCode(codeID)) {
				errors.add("Invalid DIS Entity Kind code: " + filename);
			} else if (!validator.isValidKindName(name)) {
				errors.add("Invalid DIS Entity Kind name: " + filename);
			} else if (!validator.kindNameForCode(codeID).equals(name)) {
				errors.add("Invalid DIS Entity Kind code/name combination: " + filename);
			}
		}
	}
	
	/**
	 * Validate that a DIS Entity directory is a valid format
	 * @param file The Path to the DIS Entity directory
	 * @param errors ArrayList (String) of errors, will be modified in-place
	 */
	protected void validateDISEntity(Path file, ArrayList<String> errors) {
		Pattern entityPattern = Pattern.compile("^(?<kind>\\d+)_(?<domain>\\d+)_(?<country>\\d+)_(?<category>\\d+)_(\\d+)_(\\d+)_(\\d+)$");
		String filename = file.getFileName().toString();
		String kindCode = file.getParent().getParent().getParent().getParent().getFileName().toString().split("_")[0];
		String domainCode = file.getParent().getParent().getParent().getFileName().toString().split("_")[0];
		String countryCode = file.getParent().getParent().getFileName().toString().split("_")[0];
		String categoryCode = file.getParent().getFileName().toString().split("_")[0];

		Matcher match = entityPattern.matcher(filename);
		if (!match.find()) {
			errors.add("Invalid DIS entity directory name: " + filename);
		} else {
			if (!match.group("kind").equals(kindCode)) {
				errors.add("DIS Entity Code does not match parent directory: "
						+ filename);
			}

			if (!match.group("domain").equals(domainCode)) {
				errors.add("DIS Entity Domain does not match parent directory: "
						+ filename);
			}

			if (!match.group("country").equals(countryCode)) {
				errors.add("DIS Country Code does not match parent directory: "
						+ filename);
			}

			if (!match.group("category").equals(categoryCode)) {
				errors.add("DIS Entity Category does not match parent directory: "
						+ filename);
			}
		}
	}
	
	/**
	 * Validate that the Feature Code is a valid format
	 * @param featureCode The Feature Code substring
	 * @param file The Path to the file being tested, used for errors
	 * @param errors ArrayList (String) of errors, will be modified in-place
	 */
	protected void validateFeatureCode(String featureCode, Path file, ArrayList<String> errors) {
		if (featureCode.length() != 5) {
			errors.add("Feature Code should be 5 characters: " + 
					file.getFileName().toString());
		}
	}
	
	/**
	 * Validate that the Feature Sub Code is a valid format
	 * @param featureSubCode The Feature Sub Code substring
	 * @param file The path to the file being tested, used for errors
	 * @param errors ArrayList (String) of errors, will be modified in-place
	 */
	protected void validateFeatureSubCode(String featureSubCode, Path file, ArrayList<String> errors) {
		String filename = file.getFileName().toString();
		if (featureSubCode.length() != 3) {
			errors.add("Feature Sub-Code should be 3 digits: " + filename);
		}

		try {
			Integer fsc = Integer.parseInt(featureSubCode);

			if (((fsc < 10) && !featureSubCode.substring(0,2).equals("00")) ||
					((fsc < 100) && !featureSubCode.substring(0,1).equals("0"))) {
				errors.add("Invalid padding on FSC: " + filename);
			}
		}
		catch (NumberFormatException e) {
			errors.add("Invalid FSC number format: " + filename);
		}
		catch (StringIndexOutOfBoundsException e) {
			errors.add("Invalid FSC length: " + filename);
		}
	}
	
	/**
	 * Validate that an LOD directory is a valid format
	 * @param file The Path to the LOD directory
	 * @param errors ArrayList (String) of errors, will be modified in-place
	 */
	protected void validateLOD(Path file, ArrayList<String> errors) {
		Pattern LODPattern = Pattern.compile("LC|L0[0-9]|L1[0-9]|L2[0-3]");
		String filename = file.getFileName().toString();
		Matcher match = LODPattern.matcher(filename);
		if (!match.find()) {
			errors.add("Invalid LOD name: " + filename);
		}
	}
	
	/**
	 * Validate that the Model Name substring is a valid format
	 * @param modelName The Model Name substring
	 * @param file The path to the file being tested
	 * @param errors ArrayList (String) of errors, will be modified in-place
	 */
	protected void validateModelName(String modelName, Path file, ArrayList<String> errors) {
		String filename = file.getFileName().toString();
		if (modelName.length() > 32) {
			errors.add("Model name should not exceed 32 characters: " + filename);
		}
	}
	
	/**
	 * Validate that the texture name component of a filename matches the name
	 * of the parent directory for that file.
	 * @param textureName The texture name code substring of the file name
	 * @param file The Path to the file being tested, used for errors
	 * @param errors ArrayList (String) of errors, will be modified in-place
	 */
	protected void validateTextureNameCode(String textureName, Path file, ArrayList<String> errors) {
		String parentTextureFilename = file.getParent().getFileName().toString();
		
		if (!textureName.equals(parentTextureFilename)) {
			errors.add(String.format("Texture Name Code \"%s\" does not match parent directory \"%s\" for file: %s", 
					textureName, parentTextureFilename, file.getFileName().toString()));
		}
	}
}
