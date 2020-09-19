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

public class GTModelMaterialStructureTests extends Capability1Tests {
	/**
	 * Validates that GTModelMaterial filenames have valid codes/names.
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test(description = "OGC 15-113r3, A.1.14, Test 50 - based on Section 3.4.2")
	public void verifyModelMaterialFile() throws IOException {
		Path gtModelGeomPath = Paths.get(this.path, "GTModel", "504_GTModelMaterial");

		if (Files.notExists(gtModelGeomPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		Pattern filePattern = Pattern.compile(FilenamePatterns.GTModelMaterial);
		
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
							validateComponentSelector1(cs1, "504", errors);
							validateComponentSelectorFormat(cs2, 2, filename, errors);
							validateComponentSelector2(cs2, cs1, "504", errors);
							validateTextureNameCode(match.group("tnam"), file, errors);

							if (!match.group("ext").equals("tif")) {
								errors.add("Invalid file extension for D504: " + filename);
							}
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
}
