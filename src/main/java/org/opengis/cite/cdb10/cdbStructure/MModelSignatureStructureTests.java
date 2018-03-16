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

public class MModelSignatureStructureTests extends CommonFixture {
	/**
	 * Validates that MModelSignature DIS Entity Kind directories have valid codes/names.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyDISEntityKind() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		MovingModelCodesXml mmcDefs = new MovingModelCodesXml("src/test/resources/CDB");

		for (Path kindDir : Files.newDirectoryStream(Paths.get(this.path, "MModel", "606_MModelSignature"))) {
			String filename = kindDir.getFileName().toString();
			String code = null;
			Integer codeID = null;
			String kindName = null;
			try {
				code = filename.split("_")[0];
				codeID = Integer.parseInt(code);
				kindName = filename.split("_")[1];
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
				} else if (!mmcDefs.isValidKindCode(codeID)) {
					errors.add("Invalid DIS Entity Kind code: " + filename);
				} else if (!mmcDefs.isValidKindName(kindName)) {
					errors.add("Invalid DIS Entity Kind name: " + filename);
				} else if (!mmcDefs.kindNameForCode(codeID).equals(kindName)) {
					errors.add("Invalid DIS Entity Kind code/name combination: " + filename);
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that MModelSignature DIS Domain directories have valid codes/names.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyDISDomain() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		MovingModelCodesXml mmcDefs = new MovingModelCodesXml("src/test/resources/CDB");

		for (Path kindDir : Files.newDirectoryStream(Paths.get(this.path, "MModel", "606_MModelSignature"))) {
			DirectoryStream<Path> domainDirs = Files.newDirectoryStream(kindDir);

			for (Path domainDir : domainDirs) {
				String filename = domainDir.getFileName().toString();
				String code = null;
				Integer codeID = null;
				String domainName = null;
				try {
					code = filename.split("_")[0];
					codeID = Integer.parseInt(code);
					domainName = filename.split("_")[1];
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
					} else if (!mmcDefs.isValidDomainCode(codeID)) {
						errors.add("Invalid DIS Domain code: " + filename);
					} else if (!mmcDefs.isValidDomainName(domainName)) {
						errors.add("Invalid DIS Domain name: " + filename);
					} else if (!mmcDefs.domainNameForCode(codeID).equals(domainName)) {
						errors.add("Invalid DIS Domain code/name combination: " + filename);
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that MModelSignature DIS Country directories have valid codes/names.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyDISCountry() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		DISCountryCodesXml dccDefs = new DISCountryCodesXml("src/test/resources/CDB");

		for (Path kindDir : Files.newDirectoryStream(Paths.get(this.path, "MModel", "606_MModelSignature"))) {
			DirectoryStream<Path> domainDirs = Files.newDirectoryStream(kindDir);

			for (Path domainDir : domainDirs) {
				DirectoryStream<Path> countryDirs = Files.newDirectoryStream(domainDir);

				for (Path countryDir : countryDirs) {
					String filename = countryDir.getFileName().toString();
					String code = null;
					Integer codeID = null;
					String countryName = null;
					try {
						code = filename.split("_")[0];
						codeID = Integer.parseInt(code);
						countryName = filename.split("_")[1];
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
						} else if (!dccDefs.isValidCountryCode(codeID)) {
							errors.add("Invalid DIS Country code: " + filename);
						} else if (!dccDefs.isValidCountryName(countryName)) {
							errors.add("Invalid DIS Country name: " + filename);
						} else if (!dccDefs.countryNameForCode(codeID).equals(countryName)) {
							errors.add("Invalid DIS Country code/name combination: " + filename);
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that MModelSignature DIS Category directories have valid codes/names.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyDISCategory() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		MovingModelCodesXml mmcDefs = new MovingModelCodesXml("src/test/resources/CDB");

		for (Path kindDir : Files.newDirectoryStream(Paths.get(this.path, "MModel", "606_MModelSignature"))) {
			DirectoryStream<Path> domainDirs = Files.newDirectoryStream(kindDir);

			for (Path domainDir : domainDirs) {
				DirectoryStream<Path> countryDirs = Files.newDirectoryStream(domainDir);

				for (Path countryDir : countryDirs) {
					DirectoryStream<Path> categoryDirs = Files.newDirectoryStream(countryDir);

					for (Path categoryDir : categoryDirs) {
						String filename = categoryDir.getFileName().toString();
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
							} else if (!mmcDefs.isValidCategoryCode(codeID)) {
								errors.add("Invalid DIS Category code: " + filename);
							} else if (!mmcDefs.isValidCategoryName(categoryName)) {
								errors.add("Invalid DIS Category name: " + filename);
							} else if (!mmcDefs.categoryNameForCode(codeID).equals(categoryName)) {
								errors.add("Invalid DIS Category code/name combination: " + filename);
							}
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that MModelSignature DIS Entity directories have valid codes/names.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyDISEntity() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		Pattern entityPattern = Pattern.compile("^(?<kind>\\d+)_(?<domain>\\d+)_(?<country>\\d+)_(?<category>\\d+)_(\\d+)_(\\d+)_(\\d+)$");

		for (Path kindDir : Files.newDirectoryStream(Paths.get(this.path, "MModel", "606_MModelSignature"))) {
			DirectoryStream<Path> domainDirs = Files.newDirectoryStream(kindDir);
			String kindFilename = kindDir.getFileName().toString();
			String kindCode = kindFilename.split("_")[0];

			for (Path domainDir : domainDirs) {
				DirectoryStream<Path> countryDirs = Files.newDirectoryStream(domainDir);
				String domainFilename = domainDir.getFileName().toString();
				String domainCode = domainFilename.split("_")[0];

				for (Path countryDir : countryDirs) {
					DirectoryStream<Path> categoryDirs = Files.newDirectoryStream(countryDir);
					String countryFilename = countryDir.getFileName().toString();
					String countryCode = countryFilename.split("_")[0];

					for (Path categoryDir : categoryDirs) {
						DirectoryStream<Path> entityDirs = Files.newDirectoryStream(categoryDir);
						String categoryFilename = categoryDir.getFileName().toString();
						String categoryCode = categoryFilename.split("_")[0];

						for (Path entityDir : entityDirs) {
							String filename = entityDir.getFileName().toString();

							if (StringUtils.countMatches(filename, "_") != 6) {
								errors.add("Should be six underscore separators: " + filename);
							} else {
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
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that MModelSignature LOD directories have valid names.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyLOD() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		Pattern LODPattern = Pattern.compile("LC|L0[0-9]|L1[0-9]|L2[0-3]");

		for (Path kindDir : Files.newDirectoryStream(Paths.get(this.path, "MModel", "606_MModelSignature"))) {
			DirectoryStream<Path> domainDirs = Files.newDirectoryStream(kindDir);

			for (Path domainDir : domainDirs) {
				DirectoryStream<Path> countryDirs = Files.newDirectoryStream(domainDir);

				for (Path countryDir : countryDirs) {
					DirectoryStream<Path> categoryDirs = Files.newDirectoryStream(countryDir);

					for (Path categoryDir : categoryDirs) {
						DirectoryStream<Path> entityDirs = Files.newDirectoryStream(categoryDir);

						for (Path entityDir : entityDirs) {
							DirectoryStream<Path> lods = Files.newDirectoryStream(entityDir);

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
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that MModelSignature filenames have valid codes/names.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyFile() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		/*
		 * Example of valid filename:
		 * D606_S001_T001_LC_0_0_0_0_0_0_0.shp
		 */
		Pattern filePattern = Pattern.compile(
				"^D606_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<lod>LC|L\\d{2})_(?<mmdc>(?<kind>\\d+)" +
						"_(?<domain>\\d+)_(?<country>\\d+)_(?<category>\\d+)_(\\d+)_(\\d+)_(\\d+))\\." +
						"(?<ext>.+)$"
				);

		for (Path kindDir : Files.newDirectoryStream(Paths.get(this.path, "MModel", "606_MModelSignature"))) {
			DirectoryStream<Path> domainDirs = Files.newDirectoryStream(kindDir);

			for (Path domainDir : domainDirs) {
				DirectoryStream<Path> countryDirs = Files.newDirectoryStream(domainDir);

				for (Path countryDir : countryDirs) {
					DirectoryStream<Path> categoryDirs = Files.newDirectoryStream(countryDir);

					for (Path categoryDir : categoryDirs) {
						DirectoryStream<Path> entityDirs = Files.newDirectoryStream(categoryDir);

						for (Path entityDir : entityDirs) {
							DirectoryStream<Path> lods = Files.newDirectoryStream(entityDir);
							String entityFilename = entityDir.getFileName().toString();

							for (Path lod : lods) {
								DirectoryStream<Path> files = Files.newDirectoryStream(lod);

								for (Path file : files) {
									String filename = file.getFileName().toString();

									if (StringUtils.countMatches(filename, "_") != 10) {
										errors.add("Should be ten underscore separators: " + filename);
									} else {
										Matcher match = filePattern.matcher(filename);
										if (!match.find()) {
											errors.add("Invalid file name: " + filename);
										} else {
											if (!match.group("mmdc").equals(entityFilename)) {
												errors.add("Moving Model DIS Code does not match parent directory: "
														+ filename);
											}

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

										}
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

}
