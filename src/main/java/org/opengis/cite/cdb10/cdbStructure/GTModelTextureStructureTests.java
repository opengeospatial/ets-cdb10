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

public class GTModelTextureStructureTests extends Capability1Tests {
	/**
	 * Validates that GTModelTexture filenames have valid codes/names.
	 * Test based on Section 3.4.2, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test
	public void verifyModelTextureFile() throws IOException {
		Path gtModelGeomPath = Paths.get(this.path, "GTModel", "511_GTModelTexture");

		if (Files.notExists(gtModelGeomPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		Pattern filePattern = Pattern.compile(FilenamePatterns.GTModelTexture);
		
		DirectoryStream<Path> tnamPrefixDirs = Files.newDirectoryStream(gtModelGeomPath);

		for (Path tnamPrefixDir : tnamPrefixDirs) {
			DirectoryStream<Path> secondDirs = Files.newDirectoryStream(tnamPrefixDir);

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
							String cs1 = match.group("cs1");
							String cs2 = match.group("cs2");
							
							validateComponentSelectorFormat(cs1, 1, filename, errors);
							validateComponentSelector1(cs1, "511", errors);
							validateComponentSelectorFormat(cs2, 2, filename, errors);
							validateComponentSelector2(cs2, cs1, "511", errors);
							validateTextureNameCode(match.group("tnam"), file, errors);

							if (!match.group("ext").equals("rgb")) {
								errors.add("Invalid file extension for D511: " + filename);
							}
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
}
