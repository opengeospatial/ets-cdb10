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
import org.testng.Assert;
import org.testng.annotations.Test;

public class MModelTextureStructureTests extends Capability1Tests {
	/**
	 * Validates that MModelTexture Texture Name Prefix directories have valid codes.
	 *
	 * @throws IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.15, Test 60 - based on Section 3.5.2")
	public void verifyTNAMPrefix() throws IOException {
		Path mmtPath = Paths.get(this.path, "MModel", "601_MModelTexture");

		if (Files.notExists(mmtPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		for (Path tnamPrefixDir : Files.newDirectoryStream(mmtPath)) {
			String filename = tnamPrefixDir.getFileName().toString();

			if (filename.length() != 1) {
				errors.add("Invalid length on texture name prefix directory: " + filename);
			}

			if (!filename.toUpperCase().equals(filename)) {
				errors.add("Texture name prefix directory should be uppercase: " + filename);
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that MModelTexture Texture Name level 3 directories have valid codes.
	 *
	 * @throws IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.15, Test 60 - based on Section 3.5.2")
	public void verifyTNAMSecond() throws IOException {
		Path mmtPath = Paths.get(this.path, "MModel", "601_MModelTexture");

		if (Files.notExists(mmtPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		for (Path tnamPrefixDir : Files.newDirectoryStream(mmtPath)) {
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

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that MModelTexture Texture Name directories have valid names.
	 *
	 * @throws IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.15, Test 60 - based on Section 3.5.2")
	public void verifyTNAM() throws IOException {
		Path mmtPath = Paths.get(this.path, "MModel", "601_MModelTexture");

		if (Files.notExists(mmtPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		Pattern startPattern = Pattern.compile("^\\p{Alnum}{2}");

		for (Path firstDir : Files.newDirectoryStream(mmtPath)) {
			DirectoryStream<Path> secondDirs = Files.newDirectoryStream(firstDir);
			String firstDirFilename = firstDir.getFileName().toString();

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

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that MModelTexture filenames have valid codes/names.
	 *
	 * @throws IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.15, Test 60 - based on Section 3.5.2")
	public void verifyFile() throws IOException {
		Path mmtPath = Paths.get(this.path, "MModel", "601_MModelTexture");

		if (Files.notExists(mmtPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		Pattern filePattern = Pattern.compile(FilenamePatterns.MModelTexture);

		for (Path firstDir : Files.newDirectoryStream(mmtPath)) {
			DirectoryStream<Path> secondDirs = Files.newDirectoryStream(firstDir);

			for (Path secondDir : secondDirs) {
				DirectoryStream<Path> textureNames = Files.newDirectoryStream(secondDir);

				for (Path textureName : textureNames) {
					DirectoryStream<Path> files = Files.newDirectoryStream(textureName);

					for (Path file : files) {
						String filename = file.getFileName().toString();

						Matcher match = filePattern.matcher(filename);
						if (!match.find()) {
							errors.add("Invalid file name: " + filename);
						} else {
							String dataset = match.group("dataset");

							if (!dataset.equals("D601") && !dataset.equals("D604") &&
									!dataset.equals("D605")) {
								errors.add("Invalid dataset: " + filename);
							}

							if (dataset.equals("D601") && !match.group("ext").equals("rgb")) {
								errors.add("Invalid file extension for D601: " + filename);
							}

							if (dataset.equals("D604") && !match.group("ext").equals("tif")) {
								errors.add("Invalid file extension for D604: " + filename);
							}

							if (dataset.equals("D605") && !match.group("ext").equals("xml")) {
								errors.add("Invalid file extension for D605: " + filename);
							}
							
							String datasetID = dataset.substring(1);
							String cs1 = match.group("cs1");
							String cs2 = match.group("cs2");
							
							validateComponentSelectorFormat(cs1, 1, filename, errors);
							validateComponentSelector1(cs1, datasetID, errors);
							validateComponentSelectorFormat(cs2, 2, filename, errors);
							validateComponentSelector2(cs2, cs1, datasetID, errors);

							validateTextureNameCode(match.group("tnam"), file, errors);

						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

}
