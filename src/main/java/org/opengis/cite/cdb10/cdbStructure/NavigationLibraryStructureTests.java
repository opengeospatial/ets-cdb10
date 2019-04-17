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
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavigationLibraryStructureTests extends Capability1Tests {
	/**
	 * Validate the Navigation datasets.
	 * Test based on Section 3.7, Volume 1, OGC CDB Core Standard (Version 1.0)
	 * @throws IOException DirectoryStream error
	 */
	@Test
	public void verifyDatasets() throws IOException {
		Path navPath = Paths.get(this.path, "Navigation");

		if (Files.notExists(navPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		for (Path file : Files.newDirectoryStream(navPath)) {
			String filename = file.getFileName().toString();
			if (!filename.equals("400_NavData")) {
				errors.add("Invalid dataset: " + filename);
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that Navigation filenames have valid codes/names.
	 * Test based on Section 3.7, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test
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
			} else {
				if (!match.group("dataset").equals("D400")) {
					errors.add("Invalid dataset: " + filename);
				}

				validateComponentSelectorFormat(match.group("cs1"), 1, filename, errors);
				validateComponentSelectorFormat(match.group("cs2"), 2, filename, errors);
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

}
