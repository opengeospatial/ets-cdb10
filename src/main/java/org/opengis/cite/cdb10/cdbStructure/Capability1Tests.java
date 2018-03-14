package org.opengis.cite.cdb10.cdbStructure;

import org.apache.commons.lang3.StringUtils;
import org.opengis.cite.cdb10.CommonFixture;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Includes various tests of capability 1.
 */
public class Capability1Tests extends CommonFixture {

	public Capability1Tests() {
	}

	/**
	 * Validate the contents of the root directory of the CDB, checking for stray
	 * files or directories not on the allowed list.
	 */
	@Test
	public void verifyRootContents() throws IOException {
		ArrayList<String> permittedRootDirectories = new ArrayList<String>(
				Arrays.asList("Metadata", "GTModel", "MModel", "Tiles", "Navigation"));
		ArrayList<String> invalidFiles = new ArrayList<String>();

		for (Path file : Files.newDirectoryStream(Paths.get(this.path))) {
			String filename = file.getFileName().toString();
			if (!permittedRootDirectories.contains(filename)) {
				invalidFiles.add(filename);
			}
		}

		Assert.assertTrue(invalidFiles.size() == 0,
				"Invalid files in root directory: " + StringUtils.join(invalidFiles, ", "));
	}

	/**
	 * Validates that latitude geocell directories start with "S" or "N". (See
	 * volume 1, section 3.6).
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyGeocellLatitudeDirNamePrefix() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();

		for (Path file : Files.newDirectoryStream(Paths.get(this.path, "Tiles"))) {
			String filename = file.getFileName().toString();

			if (!filename.substring(0, 1).equals("S") && !filename.substring(0, 1).equals("N")) {
				errors.add("Invalid prefix on directory name in Tiles directory: " + filename);
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that latitude geocell directories end with a valid slice latitude.
	 * (See volume 1, section 3.6).
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyGeocellLatitudeDirNameSlice() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();

		for (Path file : Files.newDirectoryStream(Paths.get(this.path, "Tiles"))) {
			String filename = file.getFileName().toString();
			String slice = filename.substring(1, filename.length());

			if (filename.substring(0, 1).equals("S")) {
				Integer sliceID = Integer.parseInt(slice);
				if ((sliceID > 90) || (sliceID < 0)) {
					errors.add("Invalid latitude for geocell directory name: " + filename);
				}

			} else if (filename.substring(0, 1).equals("N")) {
				Integer sliceID = Integer.parseInt(slice);
				if ((sliceID > 89) || (sliceID < 0)) {
					errors.add("Invalid latitude for geocell directory name: " + filename);
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
}
