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
		DatasetsXml datasetDefs = new DatasetsXml(this.path);

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
	 * Validates that GTModel Category directories have valid codes/names. D500, D503, D510 only.
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
		FeatureDataDictionaryXml fddDefs = new FeatureDataDictionaryXml(this.path);

		for (Path dataset : Files.newDirectoryStream(gtModelsPath)) {
			
			// Only apply to 500, 503, 510 datasets
			String datasetName = dataset.getFileName().toString();
			if (!datasetName.startsWith("500") && !datasetName.startsWith("510") && 
					!datasetName.startsWith("503")) {
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
		FeatureDataDictionaryXml fddDefs = new FeatureDataDictionaryXml(this.path);

		for (Path dataset : Files.newDirectoryStream(gtModelsPath)) {
			
			// Only apply to 500, 503, 510 datasets
			String datasetName = dataset.getFileName().toString();
			if (!datasetName.startsWith("500") && !datasetName.startsWith("510") && 
					!datasetName.startsWith("503")) {
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
		FeatureDataDictionaryXml fddDefs = new FeatureDataDictionaryXml(this.path);

		for (Path dataset : Files.newDirectoryStream(gtModelsPath)) {
			
			// Only apply to 500, 503, 510 datasets
			String datasetName = dataset.getFileName().toString();
			if (!datasetName.startsWith("500") && !datasetName.startsWith("510") &&
					!datasetName.startsWith("503")) {
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
			
			// Only apply to 500, 503, 510 datasets
			String datasetName = dataset.getFileName().toString();
			if (!datasetName.startsWith("500") && !datasetName.startsWith("510") &&
					!datasetName.startsWith("503")) {
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
	 * Validates that GTModelGeometry Entry filenames have valid codes/names.
	 * Test based on Section 3.4.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyGeometryEntryFile() throws IOException {
		Path gtModelGeomPath = Paths.get(this.path, "GTModel", "500_GTModelGeometry");

		if (Files.notExists(gtModelGeomPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		/*
		 * Example of valid filename:
		 * D500_S001_T001_12345_001_modelnamehere.flt
		 */
		Pattern filePattern = Pattern.compile(
				"^D500_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<featureCode>.{5})_(?<fsc>\\d+)_(?<modl>[^.]+)\\.(?<ext>.+)$"
				);

		for (Path category : Files.newDirectoryStream(gtModelGeomPath)) {
			DirectoryStream<Path> subcategories = Files.newDirectoryStream(category);

			for (Path subcategory : subcategories) {
				DirectoryStream<Path> featureTypes = Files.newDirectoryStream(subcategory);

				for (Path featureType : featureTypes) {
					DirectoryStream<Path> files = Files.newDirectoryStream(featureType);

					for (Path file : files) {
						String filename = file.getFileName().toString();

						if (StringUtils.countMatches(filename, "_") != 5) {
							errors.add("Should be five underscore separators: " + filename);
						} else {
							Matcher match = filePattern.matcher(filename);
							if (!match.find()) {
								errors.add("Invalid file name: " + filename);
							} else {

								if (match.group("cs1").length() != 3) {
									errors.add("Component Selector 1 should be 3 characters: " + filename);
								}

								try {
									Integer cs1 = Integer.parseInt(match.group("cs1"));

									if (((cs1 < 10) && !match.group("cs1").substring(0,2).equals("00")) ||
											((cs1 < 100) && !match.group("cs1").substring(0,1).equals("0"))) {
										errors.add("Invalid padding on CS1: " + filename);
									}
								}
								catch (NumberFormatException e) {
									errors.add("Invalid CS1 number format: " + filename);
								}
								catch (StringIndexOutOfBoundsException e) {
									errors.add("Invalid CS1 length: " + filename);
								}

								if (match.group("cs2").length() != 3) {
									errors.add("Component Selector 2 should be 3 characters: " + filename);
								}

								try {
									Integer cs2 = Integer.parseInt(match.group("cs2"));

									if (((cs2 < 10) && !match.group("cs2").substring(0,2).equals("00")) ||
											((cs2 < 100) && !match.group("cs2").substring(0,1).equals("0"))) {
										errors.add("Invalid padding on CS2: " + filename);
									}
								}
								catch (NumberFormatException e) {
									errors.add("Invalid CS2 number format: " + filename);
								}
								catch (StringIndexOutOfBoundsException e) {
									errors.add("Invalid CS2 length: " + filename);
								}
								
								if (match.group("featureCode").length() != 5) {
									errors.add("Feature Code should be 5 characters: " + filename);
								}
								
								if (match.group("fsc").length() != 3) {
									errors.add("Feature Sub-Code should be 3 digits: " + filename);
								}

								try {
									Integer fsc = Integer.parseInt(match.group("fsc"));

									if (((fsc < 10) && !match.group("fsc").substring(0,2).equals("00")) ||
											((fsc < 100) && !match.group("fsc").substring(0,1).equals("0"))) {
										errors.add("Invalid padding on FSC: " + filename);
									}
								}
								catch (NumberFormatException e) {
									errors.add("Invalid FSC number format: " + filename);
								}
								catch (StringIndexOutOfBoundsException e) {
									errors.add("Invalid FSC length: " + filename);
								}
								
								if (match.group("modl").length() > 32) {
									errors.add("Model name should not exceed 32 characters: " + filename);
								}
								
								if (!match.group("ext").equals("flt")) {
									errors.add("File extension must be flt: " + filename);
								}

							}
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
	
	/**
	 * Validates that GTModelGeometry LoD filenames have valid codes/names.
	 * Test based on Section 3.4.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyGeometryLoDFile() throws IOException {
		Path gtModelGeomPath = Paths.get(this.path, "GTModel", "510_GTModelGeometry");

		if (Files.notExists(gtModelGeomPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		/*
		 * Example of valid filename:
		 * D510_S001_T001_L10_12345_001_modelnamehere.flt
		 */
		Pattern filePattern = Pattern.compile(
				"^D510_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<lod>LC|L\\d{2})_(?<featureCode>.{5})_"
				+ "(?<fsc>\\d+)_(?<modl>[^.]+)\\.(?<ext>.+)$"
				);

		for (Path category : Files.newDirectoryStream(gtModelGeomPath)) {
			DirectoryStream<Path> subcategories = Files.newDirectoryStream(category);

			for (Path subcategory : subcategories) {
				DirectoryStream<Path> featureTypes = Files.newDirectoryStream(subcategory);

				for (Path featureType : featureTypes) {
					DirectoryStream<Path> lods = Files.newDirectoryStream(featureType);
					
					for (Path lod : lods) {
						DirectoryStream<Path> files = Files.newDirectoryStream(lod);
						
						for (Path file : files) {
							String filename = file.getFileName().toString();
	
							if (StringUtils.countMatches(filename, "_") != 6) {
								errors.add("Should be six underscore separators: " + filename);
							} else {
								Matcher match = filePattern.matcher(filename);
								if (!match.find()) {
									errors.add("Invalid file name: " + filename);
								} else {
	
									if (match.group("cs1").length() != 3) {
										errors.add("Component Selector 1 should be 3 characters: " + filename);
									}
	
									try {
										Integer cs1 = Integer.parseInt(match.group("cs1"));
	
										if (((cs1 < 10) && !match.group("cs1").substring(0,2).equals("00")) ||
												((cs1 < 100) && !match.group("cs1").substring(0,1).equals("0"))) {
											errors.add("Invalid padding on CS1: " + filename);
										}
									}
									catch (NumberFormatException e) {
										errors.add("Invalid CS1 number format: " + filename);
									}
									catch (StringIndexOutOfBoundsException e) {
										errors.add("Invalid CS1 length: " + filename);
									}
	
									if (match.group("cs2").length() != 3) {
										errors.add("Component Selector 2 should be 3 characters: " + filename);
									}
	
									try {
										Integer cs2 = Integer.parseInt(match.group("cs2"));
	
										if (((cs2 < 10) && !match.group("cs2").substring(0,2).equals("00")) ||
												((cs2 < 100) && !match.group("cs2").substring(0,1).equals("0"))) {
											errors.add("Invalid padding on CS2: " + filename);
										}
									}
									catch (NumberFormatException e) {
										errors.add("Invalid CS2 number format: " + filename);
									}
									catch (StringIndexOutOfBoundsException e) {
										errors.add("Invalid CS2 length: " + filename);
									}
									
									if (match.group("featureCode").length() != 5) {
										errors.add("Feature Code should be 5 characters: " + filename);
									}
									
									if (match.group("fsc").length() != 3) {
										errors.add("Feature Sub-Code should be 3 digits: " + filename);
									}
	
									try {
										Integer fsc = Integer.parseInt(match.group("fsc"));
	
										if (((fsc < 10) && !match.group("fsc").substring(0,2).equals("00")) ||
												((fsc < 100) && !match.group("fsc").substring(0,1).equals("0"))) {
											errors.add("Invalid padding on FSC: " + filename);
										}
									}
									catch (NumberFormatException e) {
										errors.add("Invalid FSC number format: " + filename);
									}
									catch (StringIndexOutOfBoundsException e) {
										errors.add("Invalid FSC length: " + filename);
									}
									
									if (match.group("modl").length() > 32) {
										errors.add("Model name should not exceed 32 characters: " + filename);
									}
									
									if (!match.group("ext").equals("flt")) {
										errors.add("File extension must be flt: " + filename);
									}
	
								}
							}
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
	
	/**
	 * Validates that GTModelDescriptor filenames have valid codes/names.
	 * Test based on Section 3.4.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyDescriptorFile() throws IOException {
		Path gtModelGeomPath = Paths.get(this.path, "GTModel", "503_GTModelDescriptor");

		if (Files.notExists(gtModelGeomPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		/*
		 * Example of valid filename:
		 * D503_S001_T001_12345_001_modelnamehere.xml
		 */
		Pattern filePattern = Pattern.compile(
				"^D503_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<featureCode>.{5})_(?<fsc>\\d+)_(?<modl>[^.]+)\\.(?<ext>.+)$"
				);

		for (Path category : Files.newDirectoryStream(gtModelGeomPath)) {
			DirectoryStream<Path> subcategories = Files.newDirectoryStream(category);

			for (Path subcategory : subcategories) {
				DirectoryStream<Path> featureTypes = Files.newDirectoryStream(subcategory);

				for (Path featureType : featureTypes) {
					DirectoryStream<Path> files = Files.newDirectoryStream(featureType);

					for (Path file : files) {
						String filename = file.getFileName().toString();

						if (StringUtils.countMatches(filename, "_") != 5) {
							errors.add("Should be five underscore separators: " + filename);
						} else {
							Matcher match = filePattern.matcher(filename);
							if (!match.find()) {
								errors.add("Invalid file name: " + filename);
							} else {

								if (match.group("cs1").length() != 3) {
									errors.add("Component Selector 1 should be 3 characters: " + filename);
								}

								try {
									Integer cs1 = Integer.parseInt(match.group("cs1"));

									if (((cs1 < 10) && !match.group("cs1").substring(0,2).equals("00")) ||
											((cs1 < 100) && !match.group("cs1").substring(0,1).equals("0"))) {
										errors.add("Invalid padding on CS1: " + filename);
									}
								}
								catch (NumberFormatException e) {
									errors.add("Invalid CS1 number format: " + filename);
								}
								catch (StringIndexOutOfBoundsException e) {
									errors.add("Invalid CS1 length: " + filename);
								}

								if (match.group("cs2").length() != 3) {
									errors.add("Component Selector 2 should be 3 characters: " + filename);
								}

								try {
									Integer cs2 = Integer.parseInt(match.group("cs2"));

									if (((cs2 < 10) && !match.group("cs2").substring(0,2).equals("00")) ||
											((cs2 < 100) && !match.group("cs2").substring(0,1).equals("0"))) {
										errors.add("Invalid padding on CS2: " + filename);
									}
								}
								catch (NumberFormatException e) {
									errors.add("Invalid CS2 number format: " + filename);
								}
								catch (StringIndexOutOfBoundsException e) {
									errors.add("Invalid CS2 length: " + filename);
								}
								
								if (match.group("featureCode").length() != 5) {
									errors.add("Feature Code should be 5 characters: " + filename);
								}
								
								if (match.group("fsc").length() != 3) {
									errors.add("Feature Sub-Code should be 3 digits: " + filename);
								}

								try {
									Integer fsc = Integer.parseInt(match.group("fsc"));

									if (((fsc < 10) && !match.group("fsc").substring(0,2).equals("00")) ||
											((fsc < 100) && !match.group("fsc").substring(0,1).equals("0"))) {
										errors.add("Invalid padding on FSC: " + filename);
									}
								}
								catch (NumberFormatException e) {
									errors.add("Invalid FSC number format: " + filename);
								}
								catch (StringIndexOutOfBoundsException e) {
									errors.add("Invalid FSC length: " + filename);
								}
								
								if (match.group("modl").length() > 32) {
									errors.add("Model name should not exceed 32 characters: " + filename);
								}
								
								if (!match.group("ext").equals("xml")) {
									errors.add("File extension must be xml: " + filename);
								}

							}
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
	
	/**
	 * Validates that GTModel Category directories have valid codes/names. D501, D511, D504, D505 only.
	 * Test based on Section 3.4.2, Volume 1, OGC CDB Core Standard (Version 1.0)
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
		FeatureDataDictionaryXml fddDefs = new FeatureDataDictionaryXml(this.path);

		for (Path dataset : Files.newDirectoryStream(gtModelsPath)) {
			
			// Only apply to 501, 511, 504, 505 datasets
			String datasetName = dataset.getFileName().toString();
			if (!datasetName.startsWith("501") && !datasetName.startsWith("511") && 
					!datasetName.startsWith("504") && !datasetName.startsWith("505")) {
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
}
