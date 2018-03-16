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

public class MModelTextureStructureTests extends CommonFixture {
	/**
	 * Validates that MModelTexture Texture Name Prefix directories have valid codes.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyTNAMPrefix() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();

		for (Path tnamPrefixDir : Files.newDirectoryStream(Paths.get(this.path, "MModel", "601_MModelTexture"))) {
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
	 * @throws IOException
	 */
	@Test
	public void verifyTNAMSecond() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();

		for (Path tnamPrefixDir : Files.newDirectoryStream(Paths.get(this.path, "MModel", "601_MModelTexture"))) {
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
	 * @throws IOException
	 */
	@Test
	public void verifyTNAM() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		Pattern startPattern = Pattern.compile("^\\p{Alnum}{2}");

		for (Path firstDir : Files.newDirectoryStream(Paths.get(this.path, "MModel", "601_MModelTexture"))) {
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
	 * @throws IOException
	 */
	@Test
	public void verifyFile() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		/*
		 * Example of valid filename:
		 * D601_S005_T001_W10_M1A2_SEP.rgb
		 */
		Pattern filePattern = Pattern.compile(
				"^(?<dataset>D601|D604|D605)_S(?<cs1>\\d+)_T(?<cs2>\\d+)_W(?<tsc>\\d{2})_(?<tnam>[^.]+)\\.(?<ext>.+)$");

		for (Path firstDir : Files.newDirectoryStream(Paths.get(this.path, "MModel", "601_MModelTexture"))) {
			DirectoryStream<Path> secondDirs = Files.newDirectoryStream(firstDir);

			for (Path secondDir : secondDirs) {
				DirectoryStream<Path> textureNames = Files.newDirectoryStream(secondDir);

				for (Path textureName : textureNames) {
					DirectoryStream<Path> files = Files.newDirectoryStream(textureName);
					String textureNameFilename = textureName.getFileName().toString();

					for (Path file : files) {
						String filename = file.getFileName().toString();

						if (StringUtils.countMatches(filename, "_") != 4) {
							errors.add("Should be four underscore separators: " + filename);
						} else {
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

								if (!match.group("tnam").equals(textureNameFilename)) {
									errors.add("Texture Name Code does not match parent directory: "
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

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

}
