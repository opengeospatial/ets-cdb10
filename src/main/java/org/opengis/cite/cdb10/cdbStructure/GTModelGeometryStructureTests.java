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
import org.opengis.cite.cdb10.util.DirectoryStreamFilters;
import org.opengis.cite.cdb10.util.FilenamePatterns;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GTModelGeometryStructureTests extends Capability1Tests {

	/**
	 * Validates that GTModelGeometry Entry filenames have valid codes/names.
	 * Test based on Section 3.4.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test
	public void verifyGeometryEntryFile() throws IOException {
		Path gtModelGeomPath = Paths.get(this.path, "GTModel", "500_GTModelGeometry");

		if (Files.notExists(gtModelGeomPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		Pattern filePattern = Pattern.compile(FilenamePatterns.GTModelGeometry);
		Pattern otherPattern = Pattern.compile("^(LC|L\\d{2}|D503.+)");

		for (Path category : Files.newDirectoryStream(gtModelGeomPath)) {
			DirectoryStream<Path> subcategories = Files.newDirectoryStream(category);

			for (Path subcategory : subcategories) {
				DirectoryStream<Path> featureTypes = Files.newDirectoryStream(subcategory);

				for (Path featureType : featureTypes) {
					DirectoryStream<Path> files = Files.newDirectoryStream(featureType);

					for (Path file : files) {
						String filename = file.getFileName().toString();

						// Ignore other valid directories/files that could be
						// in this directory
						Matcher otherMatch = otherPattern.matcher(filename);
						if (otherMatch.find()) {
							return;
						} else {
							Matcher match = filePattern.matcher(filename);
							if (!match.find()) {
								errors.add("Invalid file name: " + filename);
							} else {
								String cs1 = match.group("cs1");
								String cs2 = match.group("cs2");
								
								validateComponentSelectorFormat(cs1, 1, filename, errors);
								validateComponentSelector1(cs1, "500", errors);
								validateComponentSelectorFormat(cs2, 2, filename, errors);
								validateComponentSelector2(cs2, cs1, "500", errors);
								validateFeatureCode(match.group("featureCode"), file, errors);
								validateFeatureSubCode(match.group("fsc"), file, errors);
								validateModelName(match.group("modl"), file, errors);
								
								if (!match.group("ext").equals("flt")) {
									errors.add("File extension must be flt: " + filename);
								}

							}
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
	
	/**
	 * Validates that GTModelGeometry LoD filenames have valid codes/names.
	 * Test based on Section 3.4.1, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test
	public void verifyGeometryLoDFile() throws IOException {
		Path gtModelGeomPath = Paths.get(this.path, "GTModel", "510_GTModelGeometry");

		if (Files.notExists(gtModelGeomPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		Pattern filePattern = Pattern.compile(FilenamePatterns.GTModelGeometry510);

		for (Path category : Files.newDirectoryStream(gtModelGeomPath)) {
			DirectoryStream<Path> subcategories = Files.newDirectoryStream(category);

			for (Path subcategory : subcategories) {
				DirectoryStream<Path> featureTypes = Files.newDirectoryStream(subcategory);

				for (Path featureType : featureTypes) {
					DirectoryStream<Path> lods = Files.newDirectoryStream(featureType, DirectoryStreamFilters.lodFilter());
					
					for (Path lod : lods) {
						DirectoryStream<Path> files = Files.newDirectoryStream(lod);
						
						for (Path file : files) {
							String filename = file.getFileName().toString();
	
							Matcher match = filePattern.matcher(filename);
							if (!match.find()) {
								errors.add("Invalid file name: " + filename);
							} else {
								String cs1 = match.group("cs1");
								String cs2 = match.group("cs2");
								
								validateComponentSelectorFormat(cs1, 1, filename, errors);
								validateComponentSelector1(cs1, "510", errors);
								validateComponentSelectorFormat(cs2, 2, filename, errors);
								validateComponentSelector2(cs2, cs1, "510", errors);
								validateFeatureCode(match.group("featureCode"), file, errors);
								validateFeatureSubCode(match.group("fsc"), file, errors);
								validateModelName(match.group("modl"), file, errors);

								if (!match.group("ext").equals("flt")) {
									errors.add("File extension must be flt: " + filename);
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
