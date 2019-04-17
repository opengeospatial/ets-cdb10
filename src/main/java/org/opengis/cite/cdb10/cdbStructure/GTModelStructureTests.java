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
import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.util.metadataXml.DatasetsXml;
import org.opengis.cite.cdb10.util.metadataXml.FeatureDataDictionaryXml;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GTModelStructureTests extends Capability1Tests {
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
		DatasetsXml datasetDefs = new DatasetsXml(SAMPLE_CDB_PATH);
		final String[] allowedDatasets = { "500", "501", "502", "503", "504", "505", "506", "507", "508", 
				"509", "510", "511", "512", "513" };

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
				} else if (!Arrays.asList(allowedDatasets).contains(prefix)) {
					errors.add("Invalid dataset for GTModel: " + filename);
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that GTModel Category directories have valid codes/names.
	 * D500, D502, D503, D506, D508, D510 only.
	 * 
	 * Level 1: Dataset
	 * Level 2: Feature Category
	 * Level 3: Feature Sub-Category
	 * Level 4: Feature Type
	 * Level 5: LOD
	 * 
	 * Test based on Section 3.4.1/3.4.3/3.4.5, Volume 1, OGC CDB Core Standard (Version 1.0)
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
		FeatureDataDictionaryXml fddDefs = new FeatureDataDictionaryXml(SAMPLE_CDB_PATH);

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
	 * D500, D502, D503, D506, D508, D510 only.
	 * 
	 * Level 1: Dataset
	 * Level 2: Feature Category
	 * Level 3: Feature Sub-Category
	 * Level 4: Feature Type
	 * Level 5: LOD
	 * 
	 * Test based on Section 3.4.1/3.4.3, Volume 1, OGC CDB Core Standard (Version 1.0)
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
		FeatureDataDictionaryXml fddDefs = new FeatureDataDictionaryXml(SAMPLE_CDB_PATH);

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
	 * D500, D502, D503, D506, D508, D510 only.
	 * 
	 * Level 1: Dataset
	 * Level 2: Feature Category
	 * Level 3: Feature Sub-Category
	 * Level 4: Feature Type
	 * Level 5: LOD
	 * 
	 * Test based on Section 3.4.1/3.4.3, Volume 1, OGC CDB Core Standard (Version 1.0)
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
		FeatureDataDictionaryXml fddDefs = new FeatureDataDictionaryXml(SAMPLE_CDB_PATH);

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
	 * D500, D502, D503, D506, D508, D510 only.
	 * 
	 * Level 1: Dataset
	 * Level 2: Feature Category
	 * Level 3: Feature Sub-Category
	 * Level 4: Feature Type
	 * Level 5: LOD
	 * 
	 * Test based on Section 3.4.1/3.4.3, Volume 1, OGC CDB Core Standard (Version 1.0)
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
	
	/**
	 * Validates that GTModel directories have valid codes/names.
	 * D501, D511, D504, D505, 507, 509, 513 only.
	 * 
	 * Level 1: Dataset
	 * Level 2: TNAM First Character
	 * Level 3: TNAM Second Character
	 * Level 4: Texture Name (TNAM)
	 * 
	 * Test based on Section 3.4.2/3.4.4, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException
	 */
	@Test
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
	 * Validates that GTModel directories have valid codes/names.
	 * D501, D511, D504, D505, 507, 509, 513 only.
	 * 
	 * Level 1: Dataset
	 * Level 2: TNAM First Character
	 * Level 3: TNAM Second Character
	 * Level 4: Texture Name (TNAM)
	 * 
	 * Test based on Section 3.4.2/3.4.4, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException
	 */
	@Test
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
	 * Validates that GTModel directories have valid codes/names.
	 * D501, D511, D504, D505, 507, 509, 513 only.
	 * 
	 * Level 1: Dataset
	 * Level 2: TNAM First Character
	 * Level 3: TNAM Second Character
	 * Level 4: Texture Name (TNAM)
	 * 
	 * Test based on Section 3.4.2/3.4.4, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException
	 */
	@Test
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
						} else {
							if (!filename.substring(0,1).equals(firstDirFilename)) {
								errors.add("Texture name directory does not match grandparent: " + filename);
							}
	
							if (!filename.substring(1,2).equals(secondDirFilename)) {
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
