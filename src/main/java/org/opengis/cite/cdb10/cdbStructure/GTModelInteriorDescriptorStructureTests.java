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
import org.testng.Assert;
import org.testng.annotations.Test;

public class GTModelInteriorDescriptorStructureTests extends Capability1Tests {
	
	/**
	 * Validates that GTModelInteriorDescriptor filenames have valid codes/names.
	 * Test based on Section 3.4.3, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test
	public void verifyInteriorDescriptorFile() throws IOException {
		Path gtModelGeomPath = Paths.get(this.path, "GTModel", "508_GTModelInteriorDescriptor");

		if (Files.notExists(gtModelGeomPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		Pattern filePattern = Pattern.compile(FilenamePatterns.GTModelInteriorDescriptor);

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
								validateComponentSelectorFormat(match.group("cs1"), 1, filename, errors);
								validateComponentSelectorFormat(match.group("cs2"), 2, filename, errors);
								
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
