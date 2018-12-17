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

public class GTModelDescriptorStructureTests extends CommonFixture {
	
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
}
