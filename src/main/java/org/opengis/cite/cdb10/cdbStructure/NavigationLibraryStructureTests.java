package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.opengis.cite.cdb10.util.FilenamePatterns;
import org.opengis.cite.cdb10.util.reference.CdbReference;
import org.opengis.cite.cdb10.util.reference.DatasetsValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>
 * NavigationLibraryStructureTests class.
 * </p>
 *
 */
public class NavigationLibraryStructureTests extends Capability1Tests {

	/**
	 * Validate the Navigation datasets.
	 * @throws java.io.IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.18, Test 73 - based on Section 3.7")
	public void verifyDatasets() throws IOException {
		Path navPath = Paths.get(this.path, "Navigation");

		if (Files.notExists(navPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		CdbReference references = new CdbReference();
		DatasetsValidator validator = references.buildDatasetsValidator();

		for (Path file : Files.newDirectoryStream(navPath)) {
			String filename = file.getFileName().toString();
			String prefix = null;
			Integer prefixID = null;
			try {
				prefix = filename.substring(0, 3);
				prefixID = Integer.parseInt(prefix);
			}
			catch (StringIndexOutOfBoundsException e) {
				errors.add("Invalid prefix length: " + filename);
			}
			catch (NumberFormatException e) {
				errors.add("Invalid number format: " + filename);
			}
			catch (ArrayIndexOutOfBoundsException e) {
				errors.add("Missing dataset name: " + filename);
			}

			if (!filename.equals("400_NavData") && !validator.isExtendedCode(prefixID)) {
				errors.add("Invalid dataset: " + filename);
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that Navigation filenames have valid codes/names.
	 * @throws java.io.IOException Error reading from CDB
	 */
	@Test(description = "OGC 15-113r3, A.1.18, Test 73 - based on Section 3.7")
	public void verifyFile() throws IOException {
		Path navPath = Paths.get(this.path, "Navigation", "400_NavData");

		if (Files.notExists(navPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		Pattern filePattern = Pattern.compile(FilenamePatterns.NavigationLibrary);

		for (Path file : Files.newDirectoryStream(navPath)) {
			String filename = file.getFileName().toString();

			Matcher match = filePattern.matcher(filename);
			if (!match.find()) {
				errors.add("Invalid file name: " + filename);
			}
			else {
				if (!match.group("dataset").equals("D400")) {
					errors.add("Invalid dataset: " + filename);
				}

				String cs1 = match.group("cs1");
				String cs2 = match.group("cs2");

				validateComponentSelectorFormat(cs1, 1, filename, errors);
				validateComponentSelector1(cs1, "400", errors);
				validateComponentSelectorFormat(cs2, 2, filename, errors);
				validateComponentSelector2(cs2, cs1, "400", errors);
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

}
