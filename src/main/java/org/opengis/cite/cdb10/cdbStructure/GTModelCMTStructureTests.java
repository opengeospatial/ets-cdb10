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

public class GTModelCMTStructureTests extends CommonFixture {

	/**
	 * Validates that GTModelCMT filenames have valid codes/names.
	 * Test based on Section 3.4.2, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyCMTFile() throws IOException {
		Path gtModelGeomPath = Paths.get(this.path, "GTModel", "505_GTModelCMT");

		if (Files.notExists(gtModelGeomPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		/*
		 * Example of valid filename:
		 * D505_S001_T001_AC_1.xml
		 */
		Pattern filePattern = Pattern.compile(
				"^D505_S(?<cs1>\\d+)_T(?<cs2>\\d+)_(?<tnam>[^.]+)\\.(?<ext>.+)$"
				);
		
		DirectoryStream<Path> tnamPrefixDirs = Files.newDirectoryStream(gtModelGeomPath);

		for (Path tnamPrefixDir : tnamPrefixDirs) {
			DirectoryStream<Path> secondDirs = Files.newDirectoryStream(tnamPrefixDir);
			String firstDirFilename = tnamPrefixDir.getFileName().toString();

			for (Path secondDir : secondDirs) {
				DirectoryStream<Path> textureNames = Files.newDirectoryStream(secondDir);
				String secondDirFilename = secondDir.getFileName().toString();

				for (Path textureName : textureNames) {
					DirectoryStream<Path> files = Files.newDirectoryStream(textureName);
					String textureFilename = textureName.getFileName().toString();

					for (Path file : files) {
						String filename = file.getFileName().toString();

						if (StringUtils.countMatches(filename, "_") != 4) {
							errors.add("Should be four underscore separators: " + filename);
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
								
								if (!match.group("tnam").equals(textureFilename)) {
									errors.add("Texture Name Code does not match parent directory: "
											+ filename);
								}
								
								if (!match.group("ext").equals("xml")) {
									errors.add("Invalid file extension for D505: " + filename);
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
