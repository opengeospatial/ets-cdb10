package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GTModelStructureTests extends CommonFixture {
	/**
	 * Validates that GTModel directories have valid codes/names.
	 * Test based on Section 3.4.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyDataset() throws IOException {
		Path gtModelsPath = Paths.get(this.path, "GTModel");

		if (Files.notExists(gtModelsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		DatasetsXml datasetDefs = new DatasetsXml("src/test/resources/CDB");

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

			if (prefixID != null) {
				if (prefixID < 1) {
					errors.add("Invalid prefix cannot be below 001: " + filename);
				} else if (!datasetDefs.isValidCode(prefixID)) {
					errors.add("Invalid dataset code: " + filename);
				} else if (!datasetDefs.isValidName(datasetName)) {
					errors.add("Invalid dataset name: " + filename);
				} else if (!datasetDefs.datasetNameForCode(prefixID).equals(datasetName)) {
					errors.add("Invalid dataset code/name combination: " + filename);
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that GTModel Category directories have valid codes/names.
	 * Test based on Section 3.4.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyCategory() throws IOException {
		Path gtModelsPath = Paths.get(this.path, "GTModel");

		if (Files.notExists(gtModelsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		FeatureDataDictionaryXml fddDefs = new FeatureDataDictionaryXml("src/test/resources/CDB");

		for (Path dataset : Files.newDirectoryStream(gtModelsPath)) {
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
					if (!fddDefs.isValidCategoryCode(code)) {
						errors.add("Invalid category code: " + filename);
					}

					if (!fddDefs.isValidCategoryLabel(categoryLabel)) {
						errors.add("Invalid category label: " + filename);
					}

					if (!fddDefs.isCategoryLabelinCategoryCode(categoryLabel, code)) {
						errors.add("Category label not a child of category code: " + filename);
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that GTModel Sub-Category directories have valid codes/names.
	 * Test based on Section 3.4.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException
	 */
	@Test
	public void verifySubcategory() throws IOException {
		Path gtModelsPath = Paths.get(this.path, "GTModel");

		if (Files.notExists(gtModelsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		FeatureDataDictionaryXml fddDefs = new FeatureDataDictionaryXml("src/test/resources/CDB");

		for (Path dataset : Files.newDirectoryStream(gtModelsPath)) {
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
						if (!fddDefs.isValidSubcategoryCode(code)) {
							errors.add("Invalid subcategory code: " + filename);
						}

						if (!fddDefs.isValidSubcategoryLabel(subcategoryLabel)) {
							errors.add("Invalid subcategory label: " + filename);
						}

						if (!fddDefs.isSubcategoryLabelinSubcategoryCode(subcategoryLabel, code)) {
							errors.add("Subcategory label not a child of subcategory code: " + filename);
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that GTModel Feature Type directories have valid codes/names.
	 * Test based on Section 3.4.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyFeatureType() throws IOException {
		Path gtModelsPath = Paths.get(this.path, "GTModel");

		if (Files.notExists(gtModelsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		FeatureDataDictionaryXml fddDefs = new FeatureDataDictionaryXml("src/test/resources/CDB");

		for (Path dataset : Files.newDirectoryStream(gtModelsPath)) {
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
							if (!fddDefs.isValidFeatureTypeCode(code)) {
								errors.add("Invalid subcategory code: " + filename);
							}

							if (!fddDefs.isValidFeatureTypeLabel(featureTypeLabel)) {
								errors.add("Invalid subcategory label: " + filename);
							}

							if (!fddDefs.isFeatureTypeLabelinFeatureTypeCode(featureTypeLabel, code)) {
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
	 * Validates that GTModel LOD directories have valid names.
	 * Test based on Section 3.4.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyLOD() throws IOException {
		Path gtModelsPath = Paths.get(this.path, "GTModel");

		if (Files.notExists(gtModelsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		Pattern LODPattern = Pattern.compile("LC|L0[0-9]|L1[0-9]|L2[0-3]");

		for (Path dataset : Files.newDirectoryStream(gtModelsPath)) {
			DirectoryStream<Path> categories = Files.newDirectoryStream(dataset);

			for (Path category : categories) {
				DirectoryStream<Path> subcategories = Files.newDirectoryStream(category);

				for (Path subcategory : subcategories) {
					DirectoryStream<Path> featureTypes = Files.newDirectoryStream(subcategory);

					for (Path featureType : featureTypes) {
						DirectoryStream<Path> lods = Files.newDirectoryStream(featureType);

						for (Path lod : lods) {
							String filename = lod.getFileName().toString();
							Matcher match = LODPattern.matcher(filename);
							if (!match.find()) {
								errors.add("Invalid LOD name: " + filename);
							}
						}

					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
}
