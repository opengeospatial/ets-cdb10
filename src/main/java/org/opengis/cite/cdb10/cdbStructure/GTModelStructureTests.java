package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.opengis.cite.cdb10.util.reference.CdbReference;
import org.opengis.cite.cdb10.util.reference.DatasetsValidator;
import org.opengis.cite.cdb10.util.reference.FeatureDataDictionaryValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>
 * GTModelStructureTests class.
 * </p>
 *
 */
public class GTModelStructureTests extends Capability1Tests {

	/**
	 * Validates that GTModel directories have valid codes/names.
	 * @throws java.io.IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.14, Test 45 - based on Section 3.4.1")
	public void verifyDataset() throws IOException {
		Path gtModelsPath = Paths.get(this.path, "GTModel");

		if (Files.notExists(gtModelsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		CdbReference references = new CdbReference();
		DatasetsValidator validator = references.buildDatasetsValidator();

		final String[] allowedDatasets = { "500", "501", "502", "503", "504", "505", "506", "507", "508", "509", "510",
				"511", "512", "513" };

		for (Path file : Files.newDirectoryStream(gtModelsPath)) {
			String filename = file.getFileName().toString();
			String prefix = null;
			Integer prefixID = null;
			String datasetName = null;
			try {
				prefix = filename.substring(0, 3);
				prefixID = Integer.parseInt(prefix);
				datasetName = filename.split("_")[1];
			}
			catch (StringIndexOutOfBoundsException e) {
				errors.add("Invalid prefix length: " + filename);
			}
			catch (NumberFormatException e) {
				errors.add("Invalid number format: " + filename);
			}
			catch (ArrayIndexOutOfBoundsException e) {
				errors.add("Missing dataset name: " + filename);
			}

			if (prefixID != null && !validator.isExtendedCode(prefixID)) {
				if (prefixID < 1) {
					errors.add("Invalid prefix cannot be below 001: " + filename);
				}
				else if (!validator.isValidCode(prefixID)) {
					errors.add("Invalid dataset code: " + filename);
				}
				else if (!validator.isValidName(datasetName)) {
					errors.add("Invalid dataset name: " + filename);
				}
				else if (!validator.datasetNameForCode(prefixID).equals(datasetName)) {
					errors.add("Invalid dataset code/name combination: " + filename);
				}
				else if (!Arrays.asList(allowedDatasets).contains(prefix)) {
					errors.add("Invalid dataset for GTModel: " + filename);
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that GTModel Category directories have valid codes/names. D500, D502,
	 * D503, D506, D508, D510 only.
	 *
	 * Level 1: Dataset Level 2: Feature Category Level 3: Feature Sub-Category Level 4:
	 * Feature Type Level 5: LOD
	 * @throws java.io.IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.14, Test 46/47/48/52/53 - based on Section 3.4.1")
	public void verifyCategory() throws IOException {
		Path gtModelsPath = Paths.get(this.path, "GTModel");

		if (Files.notExists(gtModelsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		CdbReference references = new CdbReference();
		FeatureDataDictionaryValidator validator = references.buildFeatureDataDictionaryValidator();

		for (Path dataset : Files.newDirectoryStream(gtModelsPath)) {

			// Only apply to 500, 502, 503, 506, 508, 510
			final String[] allowedDatasets = { "500", "502", "503", "506", "508", "510" };
			String datasetName = dataset.getFileName().toString().substring(0, 3);

			if (!Arrays.asList(allowedDatasets).contains(datasetName)) {
				return;
			}

			DirectoryStream<Path> categories = Files.newDirectoryStream(dataset);

			for (Path category : categories) {
				String filename = category.getFileName().toString();
				String code = null;
				String categoryLabel = null;
				try {
					code = filename.substring(0, 1);
					categoryLabel = filename.split("_")[1];
				}
				catch (StringIndexOutOfBoundsException e) {
					errors.add("Invalid prefix length: " + filename);
				}
				catch (ArrayIndexOutOfBoundsException e) {
					errors.add("Missing dataset name: " + filename);
				}

				if ((code != null) && (categoryLabel != null)) {
					if (!validator.isValidCategoryCode(code)) {
						errors.add("Invalid category code: " + filename);
					}

					if (!validator.isValidCategoryLabel(categoryLabel)) {
						errors.add("Invalid category label: " + filename);
					}

					if (!validator.isCategoryLabelinCategoryCode(categoryLabel, code)) {
						errors.add("Category label not a child of category code: " + filename);
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that GTModel Sub-Category directories have valid codes/names. D500, D502,
	 * D503, D506, D508, D510 only.
	 *
	 * Level 1: Dataset Level 2: Feature Category Level 3: Feature Sub-Category Level 4:
	 * Feature Type Level 5: LOD
	 * @throws java.io.IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.14, Test 46/47/48/52/53 - based on Section 3.4.1")
	public void verifySubcategory() throws IOException {
		Path gtModelsPath = Paths.get(this.path, "GTModel");

		if (Files.notExists(gtModelsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		CdbReference references = new CdbReference();
		FeatureDataDictionaryValidator validator = references.buildFeatureDataDictionaryValidator();

		for (Path dataset : Files.newDirectoryStream(gtModelsPath)) {

			// Only apply to 500, 502, 503, 506, 508, 510
			final String[] allowedDatasets = { "500", "502", "503", "506", "508", "510" };
			String datasetName = dataset.getFileName().toString().substring(0, 3);

			if (!Arrays.asList(allowedDatasets).contains(datasetName)) {
				return;
			}

			DirectoryStream<Path> categories = Files.newDirectoryStream(dataset);

			for (Path category : categories) {
				DirectoryStream<Path> subcategories = Files.newDirectoryStream(category);

				for (Path subcategory : subcategories) {
					String filename = subcategory.getFileName().toString();
					String code = null;
					String subcategoryLabel = null;
					try {
						code = filename.substring(0, 1);
						subcategoryLabel = filename.split("_")[1];
					}
					catch (StringIndexOutOfBoundsException e) {
						errors.add("Invalid prefix length: " + filename);
					}
					catch (ArrayIndexOutOfBoundsException e) {
						errors.add("Missing dataset name: " + filename);
					}

					if ((code != null) && (subcategoryLabel != null)) {
						if (!validator.isValidSubcategoryCode(code)) {
							errors.add("Invalid subcategory code: " + filename);
						}

						if (!validator.isValidSubcategoryLabel(subcategoryLabel)) {
							errors.add("Invalid subcategory label: " + filename);
						}

						if (!validator.isSubcategoryLabelinSubcategoryCode(subcategoryLabel, code)) {
							errors.add("Subcategory label not a child of subcategory code: " + filename);
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that GTModel Feature Type directories have valid codes/names. D500, D502,
	 * D503, D506, D508, D510 only.
	 *
	 * Level 1: Dataset Level 2: Feature Category Level 3: Feature Sub-Category Level 4:
	 * Feature Type Level 5: LOD
	 * @throws java.io.IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.14, Test 46/47/48/52/53 - based on Section 3.4.1")
	public void verifyFeatureType() throws IOException {
		Path gtModelsPath = Paths.get(this.path, "GTModel");

		if (Files.notExists(gtModelsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		CdbReference references = new CdbReference();
		FeatureDataDictionaryValidator validator = references.buildFeatureDataDictionaryValidator();

		for (Path dataset : Files.newDirectoryStream(gtModelsPath)) {

			// Only apply to 500, 502, 503, 506, 508, 510
			final String[] allowedDatasets = { "500", "502", "503", "506", "508", "510" };
			String datasetName = dataset.getFileName().toString().substring(0, 3);

			if (!Arrays.asList(allowedDatasets).contains(datasetName)) {
				return;
			}

			DirectoryStream<Path> categories = Files.newDirectoryStream(dataset);

			for (Path category : categories) {
				DirectoryStream<Path> subcategories = Files.newDirectoryStream(category);

				for (Path subcategory : subcategories) {
					DirectoryStream<Path> featureTypes = Files.newDirectoryStream(subcategory);

					for (Path featureType : featureTypes) {
						String filename = featureType.getFileName().toString();
						String code = null;
						Integer codeID = null;
						String featureTypeLabel = null;
						try {
							code = filename.substring(0, 3);
							codeID = Integer.parseInt(code);
							featureTypeLabel = filename.substring(4, filename.length());
						}
						catch (StringIndexOutOfBoundsException e) {
							errors.add("Invalid prefix length: " + filename);
						}
						catch (NumberFormatException e) {
							errors.add("Invalid number format: " + filename);
						}
						catch (ArrayIndexOutOfBoundsException e) {
							errors.add("Missing type name: " + filename);
						}

						if ((code != null) && (codeID != null) && (featureTypeLabel != null)) {
							if (!validator.isValidFeatureTypeCode(code)) {
								errors.add("Invalid subcategory code: " + filename);
							}

							if (!validator.isValidFeatureTypeLabel(featureTypeLabel)) {
								errors.add("Invalid subcategory label: " + filename);
							}

							if (!validator.isFeatureTypeLabelinFeatureTypeCode(featureTypeLabel, code)) {
								errors.add("Subcategory label not a child of subcategory code: " + filename);
							}
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that GTModel LOD directories have valid names. D500, D502, D503, D506,
	 * D508, D510 only.
	 *
	 * Level 1: Dataset Level 2: Feature Category Level 3: Feature Sub-Category Level 4:
	 * Feature Type Level 5: LOD
	 * @throws java.io.IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.14, Test 46/47/48/52/53 - based on Section 3.4.1")
	public void verifyLOD() throws IOException {
		Path gtModelsPath = Paths.get(this.path, "GTModel");

		if (Files.notExists(gtModelsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		for (Path dataset : Files.newDirectoryStream(gtModelsPath)) {

			// Only apply to 500, 502, 503, 506, 508, 510
			final String[] allowedDatasets = { "500", "502", "503", "506", "508", "510" };
			String datasetName = dataset.getFileName().toString().substring(0, 3);

			if (!Arrays.asList(allowedDatasets).contains(datasetName)) {
				return;
			}

			DirectoryStream<Path> categories = Files.newDirectoryStream(dataset);

			for (Path category : categories) {
				DirectoryStream<Path> subcategories = Files.newDirectoryStream(category);

				for (Path subcategory : subcategories) {
					DirectoryStream<Path> featureTypes = Files.newDirectoryStream(subcategory);

					for (Path featureType : featureTypes) {
						DirectoryStream<Path> lods = Files.newDirectoryStream(featureType);

						for (Path lod : lods) {
							validateLod(lod.getFileName().toString(), errors);
						}

					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that GTModel directories have valid codes/names. D501, D511, D504, D505,
	 * 507, 509, 513 only.
	 *
	 * Level 1: Dataset Level 2: TNAM First Character Level 3: TNAM Second Character Level
	 * 4: Texture Name (TNAM)
	 * @throws java.io.IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.14, Test 49/50/51/54/55 - based on Section 3.4.2")
	public void verifyTNAMPrefix() throws IOException {
		Path gtModelsPath = Paths.get(this.path, "GTModel");

		if (Files.notExists(gtModelsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		for (Path dataset : Files.newDirectoryStream(gtModelsPath)) {

			// Only apply to 501, 511, 504, 505, 507, 509, 513 datasets
			final String[] allowedDatasets = { "501", "511", "504", "505", "507", "509", "513" };
			String datasetName = dataset.getFileName().toString().substring(0, 3);

			if (!Arrays.asList(allowedDatasets).contains(datasetName)) {
				return;
			}

			DirectoryStream<Path> prefixes = Files.newDirectoryStream(dataset);

			for (Path prefix : prefixes) {
				String filename = prefix.getFileName().toString();

				if (filename.length() != 1) {
					errors.add("Invalid length on texture name prefix directory: " + filename);
				}

				if (!filename.toUpperCase().equals(filename)) {
					errors.add("Texture name prefix directory should be uppercase: " + filename);
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that GTModel directories have valid codes/names. D501, D511, D504, D505,
	 * 507, 509, 513 only.
	 *
	 * Level 1: Dataset Level 2: TNAM First Character Level 3: TNAM Second Character Level
	 * 4: Texture Name (TNAM)
	 * @throws java.io.IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.14, Test 49/50/51/54/55 - based on Section 3.4.2")
	public void verifyTNAMSecond() throws IOException {
		Path gtModelsPath = Paths.get(this.path, "GTModel");

		if (Files.notExists(gtModelsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		for (Path dataset : Files.newDirectoryStream(gtModelsPath)) {

			// Only apply to 501, 511, 504, 505, 507, 509, 513 datasets
			final String[] allowedDatasets = { "501", "511", "504", "505", "507", "509", "513" };
			String datasetName = dataset.getFileName().toString().substring(0, 3);

			if (!Arrays.asList(allowedDatasets).contains(datasetName)) {
				return;
			}

			DirectoryStream<Path> tnamPrefixDirs = Files.newDirectoryStream(dataset);

			for (Path tnamPrefixDir : tnamPrefixDirs) {
				DirectoryStream<Path> secondDirs = Files.newDirectoryStream(tnamPrefixDir);

				for (Path secondDir : secondDirs) {
					String filename = secondDir.getFileName().toString();

					if (filename.length() != 1) {
						errors.add("Invalid length on level 3 texture name directory: " + filename);
					}

					if (!filename.toUpperCase().equals(filename)) {
						errors.add("Level 3 texture name directory should be uppercase: " + filename);
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that GTModel directories have valid codes/names. D501, D511, D504, D505,
	 * 507, 509, 513 only.
	 *
	 * - Level 1: Dataset - Level 2: TNAM First Character - Level 3: TNAM Second Character
	 * - Level 4: Texture Name (TNAM)
	 * @throws java.io.IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.14, Test 49/50/51/54/55 - based on Section 3.4.2")
	public void verifyTNAM() throws IOException {
		Path gtModelsPath = Paths.get(this.path, "GTModel");

		if (Files.notExists(gtModelsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		Pattern startPattern = Pattern.compile("^\\p{Alnum}{2}");

		for (Path dataset : Files.newDirectoryStream(gtModelsPath)) {

			// Only apply to 501, 511, 504, 505, 507, 509, 513 datasets
			final String[] allowedDatasets = { "501", "511", "504", "505", "507", "509", "513" };
			String datasetName = dataset.getFileName().toString().substring(0, 3);

			if (!Arrays.asList(allowedDatasets).contains(datasetName)) {
				return;
			}

			DirectoryStream<Path> tnamPrefixDirs = Files.newDirectoryStream(dataset);

			for (Path tnamPrefixDir : tnamPrefixDirs) {
				DirectoryStream<Path> secondDirs = Files.newDirectoryStream(tnamPrefixDir);
				String firstDirFilename = tnamPrefixDir.getFileName().toString();

				for (Path secondDir : secondDirs) {
					DirectoryStream<Path> textureNames = Files.newDirectoryStream(secondDir);
					String secondDirFilename = secondDir.getFileName().toString();

					for (Path textureName : textureNames) {
						String filename = textureName.getFileName().toString();

						if ((filename.length() < 2) || (filename.length() > 32)) {
							errors.add("Invalid length on texture name directory: " + filename);
						}
						else {
							if (!filename.substring(0, 1).equals(firstDirFilename)) {
								errors.add("Texture name directory does not match grandparent: " + filename);
							}

							if (!filename.substring(1, 2).equals(secondDirFilename)) {
								errors.add("Texture name directory does not match parent: " + filename);
							}

							Matcher match = startPattern.matcher(filename);

							if (!match.find()) {
								errors.add("Texture name directory must start with two alphanumeric characters: "
										+ filename);
							}
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

}
