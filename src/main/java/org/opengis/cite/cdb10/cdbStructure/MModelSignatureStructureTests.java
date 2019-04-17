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
import org.opengis.cite.cdb10.util.FilenamePatterns;
import org.opengis.cite.cdb10.util.metadataXml.DISCountryCodesXml;
import org.opengis.cite.cdb10.util.metadataXml.MovingModelCodesXml;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MModelSignatureStructureTests extends Capability1Tests {
	/**
	 * Validates that MModelSignature DIS Entity Kind directories have valid codes/names.
	 * Test based on Section 3.5.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test
	public void verifyDISEntityKind() throws IOException {
		Path mmsPath = Paths.get(this.path, "MModel", "606_MModelSignature");

		if (Files.notExists(mmsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		for (Path kindDir : Files.newDirectoryStream(mmsPath)) {
			validateDISEntityKind(kindDir, errors);
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that MModelSignature DIS Domain directories have valid codes/names.
	 * Test based on Section 3.5.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test
	public void verifyDISDomain() throws IOException {
		Path mmsPath = Paths.get(this.path, "MModel", "606_MModelSignature");

		if (Files.notExists(mmsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		for (Path kindDir : Files.newDirectoryStream(mmsPath)) {
			DirectoryStream<Path> domainDirs = Files.newDirectoryStream(kindDir);

			for (Path domainDir : domainDirs) {
				validateDISDomain(domainDir, errors);
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that MModelSignature DIS Country directories have valid codes/names.
	 * Test based on Section 3.5.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test
	public void verifyDISCountry() throws IOException {
		Path mmsPath = Paths.get(this.path, "MModel", "606_MModelSignature");

		if (Files.notExists(mmsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		for (Path kindDir : Files.newDirectoryStream(mmsPath)) {
			DirectoryStream<Path> domainDirs = Files.newDirectoryStream(kindDir);

			for (Path domainDir : domainDirs) {
				DirectoryStream<Path> countryDirs = Files.newDirectoryStream(domainDir);

				for (Path countryDir : countryDirs) {
					validateDISCountry(countryDir, errors);
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that MModelSignature DIS Category directories have valid codes/names.
	 * Test based on Section 3.5.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test
	public void verifyDISCategory() throws IOException {
		Path mmsPath = Paths.get(this.path, "MModel", "606_MModelSignature");

		if (Files.notExists(mmsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		for (Path kindDir : Files.newDirectoryStream(mmsPath)) {
			DirectoryStream<Path> domainDirs = Files.newDirectoryStream(kindDir);

			for (Path domainDir : domainDirs) {
				DirectoryStream<Path> countryDirs = Files.newDirectoryStream(domainDir);

				for (Path countryDir : countryDirs) {
					DirectoryStream<Path> categoryDirs = Files.newDirectoryStream(countryDir);

					for (Path categoryDir : categoryDirs) {
						validateDISCategory(categoryDir, errors);
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that MModelSignature DIS Entity directories have valid codes/names.
	 * Test based on Section 3.5.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test
	public void verifyDISEntity() throws IOException {
		Path mmsPath = Paths.get(this.path, "MModel", "606_MModelSignature");

		if (Files.notExists(mmsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		for (Path kindDir : Files.newDirectoryStream(mmsPath)) {
			DirectoryStream<Path> domainDirs = Files.newDirectoryStream(kindDir);

			for (Path domainDir : domainDirs) {
				DirectoryStream<Path> countryDirs = Files.newDirectoryStream(domainDir);

				for (Path countryDir : countryDirs) {
					DirectoryStream<Path> categoryDirs = Files.newDirectoryStream(countryDir);

					for (Path categoryDir : categoryDirs) {
						DirectoryStream<Path> entityDirs = Files.newDirectoryStream(categoryDir);

						for (Path entityDir : entityDirs) {
							validateDISEntity(entityDir, errors);
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that MModelSignature LOD directories have valid names.
	 * Test based on Section 3.5.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test
	public void verifyLOD() throws IOException {
		Path mmsPath = Paths.get(this.path, "MModel", "606_MModelSignature");

		if (Files.notExists(mmsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		for (Path kindDir : Files.newDirectoryStream(mmsPath)) {
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
								validateLOD(lod, errors);
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
	 * Test based on Section 3.5.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test
	public void verifyFile() throws IOException {
		Path mmsPath = Paths.get(this.path, "MModel", "606_MModelSignature");

		if (Files.notExists(mmsPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		Pattern filePattern = Pattern.compile(FilenamePatterns.MModelSignature);

		for (Path kindDir : Files.newDirectoryStream(mmsPath)) {
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

									Matcher match = filePattern.matcher(filename);
									if (!match.find()) {
										errors.add("Invalid file name: " + filename);
									} else {
										if (!match.group("mmdc").equals(entityFilename)) {
											errors.add("Moving Model DIS Code does not match parent directory: "
													+ filename);
										}

										validateComponentSelectorFormat(match.group("cs1"), 1, filename, errors);
										validateComponentSelectorFormat(match.group("cs2"), 2, filename, errors);

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
