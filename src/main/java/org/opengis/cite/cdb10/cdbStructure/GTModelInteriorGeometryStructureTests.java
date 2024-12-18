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

/**
 * <p>
 * GTModelInteriorGeometryStructureTests class.
 * </p>
 *
 */
public class GTModelInteriorGeometryStructureTests extends Capability1Tests {

	/**
	 * Validates that GTModelInteriorGeometry filenames have valid codes/names.
	 * @throws java.io.IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.14, Test 52 - based on Section 3.4.3")
	public void verifyInteriorGeometryFile() throws IOException {
		Path gtModelInteriorGeomPath = Paths.get(this.path, "GTModel", "506_GTModelInteriorGeometry");

		if (Files.notExists(gtModelInteriorGeomPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		Pattern filePattern = Pattern.compile(FilenamePatterns.GTModelInteriorGeometry);

		for (Path category : Files.newDirectoryStream(gtModelInteriorGeomPath)) {
			DirectoryStream<Path> subcategories = Files.newDirectoryStream(category);

			for (Path subcategory : subcategories) {
				DirectoryStream<Path> featureTypes = Files.newDirectoryStream(subcategory);

				for (Path featureType : featureTypes) {
					DirectoryStream<Path> lods = Files.newDirectoryStream(featureType,
							DirectoryStreamFilters.lodFilter());

					for (Path lod : lods) {
						DirectoryStream<Path> files = Files.newDirectoryStream(lod);

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
								validateComponentSelector1(cs1, "506", errors);
								validateComponentSelectorFormat(cs2, 2, filename, errors);
								validateComponentSelector2(cs2, cs1, "506", errors);
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
