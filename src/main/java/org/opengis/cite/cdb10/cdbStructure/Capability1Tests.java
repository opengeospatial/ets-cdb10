package org.opengis.cite.cdb10.cdbStructure;

import org.apache.commons.lang3.StringUtils;
import org.opengis.cite.cdb10.CommonFixture;
import org.opengis.cite.cdb10.SuiteAttribute;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
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
	 * Obtains the test subject from the ISuite context. The suite attribute
	 * {@link org.opengis.cite.cdb10.SuiteAttribute#TEST_SUBJECT} should evaluate to
	 * a DOM Document node.
	 *
	 * @param testContext
	 *            The test (group) context.
	 */
	@Override
	@BeforeClass
	public void obtainTestSubject(ITestContext testContext) {
		Object obj = testContext.getSuite().getAttribute(SuiteAttribute.LEVELS.getName());
		if ((null != obj)) {
			ArrayList<Integer> levels = new ArrayList<Integer>(Arrays.asList(Integer[].class.cast(obj)));
			Assert.assertTrue(levels.contains(1),
					"Conformance level 1 will not be checked as it is not included in ics");
		}
		super.obtainTestSubject(testContext);
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
	 * volume 1, section 3.6.2.1).
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
	 * latitudes should be zero-padded to 2 width.
	 * (See volume 1, section 3.6.2.1).
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
				if ((sliceID > 90) || (sliceID <= 0)) {
					errors.add("Invalid latitude for geocell directory name: " + filename);
				}

				if ((sliceID < 10) && (sliceID > 0) && !(slice.substring(1,2).equals("0"))) {
					errors.add("Invalid zero-pad on geocell directory name: " + filename);
				}

			} else if (filename.substring(0, 1).equals("N")) {
				Integer sliceID = Integer.parseInt(slice);
				if ((sliceID > 89) || (sliceID < 0)) {
					errors.add("Invalid latitude for geocell directory name: " + filename);
				}

				if ((sliceID < 10) && (sliceID >= 0) && !(slice.substring(1,2).equals("0"))) {
					errors.add("Invalid zero-pad on geocell directory name: " + filename);
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that longitude geocell directories start with "E" or "W". (See
	 * volume 1, section 3.6.2.2).
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyGeocellLongitudeDirNamePrefix() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		DirectoryStream<Path> latitudeCells = Files.newDirectoryStream(Paths.get(this.path, "Tiles"));

		for (Path latCell : latitudeCells) {
			DirectoryStream<Path> longitudeCells = Files.newDirectoryStream(latCell);

			for (Path lonCell : longitudeCells) {
				String filename = lonCell.getFileName().toString();

				if (!filename.substring(0, 1).equals("E") && !filename.substring(0, 1).equals("W")) {
					errors.add("Invalid prefix on longitude geocell directory: " + filename);
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that longitude geocell directories end with a valid slice longitude.
	 * longitudes should be zero-padded to 3 width.
	 * (See volume 1, section 3.6.2.1).
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyGeocellLongitudeDirNameSlice() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		DirectoryStream<Path> latitudeCells = Files.newDirectoryStream(Paths.get(this.path, "Tiles"));

		for (Path latCell : latitudeCells) {
			DirectoryStream<Path> longitudeCells = Files.newDirectoryStream(latCell);

			for (Path lonCell : longitudeCells) {
				String filename = lonCell.getFileName().toString();
				String slice = filename.substring(1, filename.length());

				if (filename.substring(0, 1).equals("E")) {
					Integer sliceID = Integer.parseInt(slice);
					if ((sliceID > 179) || (sliceID < 0)) {
						errors.add("Invalid longitude for geocell directory name: " + filename);
					}

					if ((sliceID < 10) && (sliceID >= 0) && (slice.substring(1,3).equals("00"))) {
						errors.add("Invalid zero-pad on geocell directory name: " + filename);
					} else if ((sliceID < 100) && (sliceID >= 10) && (slice.substring(1,3).equals("0"))) {
						errors.add("Invalid zero-pad on geocell directory name: " + filename);
					}
				} else if (filename.substring(0, 1).equals("W")) {
					Integer sliceID = Integer.parseInt(slice);
					if ((sliceID > 180) || (sliceID <= 0)) {
						errors.add("Invalid longitude for geocell directory name: " + filename);
					}

					if ((sliceID < 10) && (sliceID >= 0) && (slice.substring(1,3).equals("00"))) {
						errors.add("Invalid zero-pad on geocell directory name: " + filename);
					} else if ((sliceID < 100) && (sliceID >= 10) && (slice.substring(1,3).equals("0"))) {
						errors.add("Invalid zero-pad on geocell directory name: " + filename);
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
}
