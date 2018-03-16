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

}
