package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
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

public class NavigationStructureTests extends CommonFixture {
	/**
	 * Validate the Navigation datasets.
	 * Test based on Section 3.7, Volume 1, OGC CDB Core Standard (Version 1.0)
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
			if (!filename.equals("400_NavDat")) {
				errors.add("Invalid dataset: " + filename);
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that Navigation filenames have valid codes/names.
	 * Test based on Section 3.7, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyFile() throws IOException {
		Path navPath = Paths.get(this.path, "Navigation", "400_NavDat");

		if (Files.notExists(navPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		/*
		 * Example of valid filename:
		 * D400_S001_T002.dbf
		 */
		Pattern filePattern = Pattern.compile("^(?<dataset>[^_]+)_S(?<cs1>\\d+)_T(?<cs2>\\d+)\\.(?<ext>.+)$");

		for (Path file : Files.newDirectoryStream(navPath)) {
			String filename = file.getFileName().toString();

			if (StringUtils.countMatches(filename, "_") != 2) {
				errors.add("Should be two underscore separators: " + filename);
			} else {
				Matcher match = filePattern.matcher(filename);
				if (!match.find()) {
					errors.add("Invalid file name: " + filename);
				} else {
					if (!match.group("dataset").equals("D400")) {
						errors.add("Invalid dataset: " + filename);
					}

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

				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

}
