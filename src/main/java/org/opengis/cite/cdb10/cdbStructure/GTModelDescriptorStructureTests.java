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

public class GTModelDescriptorStructureTests extends Capability1Tests {
	
	/**
	 * Validates that GTModelDescriptor filenames have valid codes/names.
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test(description = "OGC 15-113r3, A.1.14, Test 48 - based on Section 3.4.1")
	public void verifyDescriptorFile() throws IOException {
		Path gtModelGeomPath = Paths.get(this.path, "GTModel", "503_GTModelDescriptor");

		if (Files.notExists(gtModelGeomPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		/*
		 * 
		 */
		Pattern filePattern = Pattern.compile(FilenamePatterns.GTModelDescriptor);

		for (Path category : Files.newDirectoryStream(gtModelGeomPath)) {
			DirectoryStream<Path> subcategories = Files.newDirectoryStream(category);

			for (Path subcategory : subcategories) {
				DirectoryStream<Path> featureTypes = Files.newDirectoryStream(subcategory);

				for (Path featureType : featureTypes) {
					DirectoryStream<Path> files = Files.newDirectoryStream(featureType);

					for (Path file : files) {
						String filename = file.getFileName().toString();
						Matcher match = filePattern.matcher(filename);
						if (!match.find()) {
							errors.add("Invalid file name: " + filename);
						} else {
							String cs1 = match.group("cs1");
							String cs2 = match.group("cs2");
							
							validateComponentSelectorFormat(cs1, 1, filename, errors);
							validateComponentSelector1(cs1, "503", errors);
							validateComponentSelectorFormat(cs2, 2, filename, errors);
							validateComponentSelector2(cs2, cs1, "503", errors);
							validateFeatureCode(match.group("featureCode"), file, errors);
							validateFeatureSubCode(match.group("fsc"), file, errors);
							validateModelName(match.group("modl"), file, errors);

							if (!match.group("ext").equals("xml")) {
								errors.add("File extension must be xml: " + filename);
							}

						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
}
