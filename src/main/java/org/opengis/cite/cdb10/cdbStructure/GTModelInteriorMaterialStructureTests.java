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

/**
 * <p>
 * GTModelInteriorMaterialStructureTests class.
 * </p>
 *
 */
public class GTModelInteriorMaterialStructureTests extends Capability1Tests {

	/**
	 * Validates that GTModelInteriorMaterial filenames have valid codes/names.
	 * @throws java.io.IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.14, Test 55 - based on Section 3.4.4")
	public void verifyModelInteriorMaterialFile() throws IOException {
		Path gtModelGeomPath = Paths.get(this.path, "GTModel", "509_GTModelInteriorMaterial");

		if (Files.notExists(gtModelGeomPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		Pattern filePattern = Pattern.compile(FilenamePatterns.GTModelInteriorMaterial);

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
						}
						else {
							String cs1 = match.group("cs1");
							String cs2 = match.group("cs2");

							validateComponentSelectorFormat(cs1, 1, filename, errors);
							validateComponentSelector1(cs1, "500", errors);
							validateComponentSelectorFormat(cs2, 2, filename, errors);
							validateComponentSelector2(cs2, cs1, "500", errors);
							validateTextureNameCode(match.group("tnam"), file, errors);

							if (!match.group("ext").equals("tif")) {
								errors.add("Invalid file extension for D509: " + filename);
							}
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

}
