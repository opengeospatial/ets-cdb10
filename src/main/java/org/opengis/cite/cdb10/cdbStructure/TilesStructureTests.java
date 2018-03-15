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

/**
 * File/directory structure tests for the Tiles directory of the CDB
 * @author jpbadger
 *
 */
public class TilesStructureTests extends CommonFixture {

	public Integer sliceWidthForLatitude(Integer latitude) {
		Integer dLonZone = 1;
		if (((latitude >= 89) && (latitude < 90)) || ((latitude >= -90) && (latitude < -89))) {
			dLonZone = 12;
		} else if (((latitude >= 80) && (latitude < 89)) || ((latitude >= -89) && (latitude < -80))) {
			dLonZone = 6;
		} else if (((latitude >= 75) && (latitude < 80)) || ((latitude >= -80) && (latitude < -75))) {
			dLonZone = 4;
		} else if (((latitude >= 70) && (latitude < 75)) || ((latitude >= -75) && (latitude < -70))) {
			dLonZone = 3;
		} else if (((latitude >= 50) && (latitude < 70)) || ((latitude >= -70) && (latitude < -50))) {
			dLonZone = 2;
		} else if (((latitude >= 0) && (latitude < 50)) || ((latitude >= -50) && (latitude < 0))) {
			dLonZone = 1;
		}

		return dLonZone;
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

			if (filename.length() != 3) {
				errors.add("Invalid padding/length on geocell directory name: " + filename);
			} else if (filename.substring(0, 1).equals("S")) {
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
			String latFilename = latCell.getFileName().toString();
			String latSlice = latFilename.substring(1, latFilename.length());
			Integer latSliceID = null;
			try {
				latSliceID = Integer.parseInt(latSlice);
			}
			catch (NumberFormatException e) {
				errors.add("Invalid numeric format on geocell slice: " + latFilename);
			}

			for (Path lonCell : longitudeCells) {
				String filename = lonCell.getFileName().toString();
				String slice = filename.substring(1, filename.length());
				Integer sliceID = 0;
				try {
					sliceID = Integer.parseInt(slice);
				}
				catch (NumberFormatException e) {
					errors.add("Invalid numeric format on geocell slice: " + filename);
				}

				if (filename.length() != 4) {
					errors.add("Invalid padding/length for geocell directory name: " + filename);
				} else if (filename.substring(0, 1).equals("E")) {
					if ((sliceID > 179) || (sliceID < 0)) {
						errors.add("Invalid longitude for geocell directory name: " + filename);
					}

					if ((sliceID % this.sliceWidthForLatitude(latSliceID)) != 0) {
						errors.add("Invalid slice width for geocell directory name: " + filename);
					}

					if ((sliceID < 10) && (sliceID >= 0) && !(slice.substring(1,3).equals("00"))) {
						errors.add("Invalid zero-pad on geocell directory name: " + filename);
					} else if ((sliceID < 100) && (sliceID >= 10) && !(slice.substring(1,2).equals("0"))) {
						errors.add("Invalid zero-pad on geocell directory name: " + filename);
					}
				} else if (filename.substring(0, 1).equals("W")) {
					if ((sliceID > 180) || (sliceID <= 0)) {
						errors.add("Invalid longitude for geocell directory name: " + filename);
					}

					if ((sliceID % this.sliceWidthForLatitude(latSliceID)) != 0) {
						errors.add("Invalid slice width for geocell directory name: " + filename);
					}

					if ((sliceID < 10) && (sliceID >= 0) && !(slice.substring(1,3).equals("00"))) {
						errors.add("Invalid zero-pad on geocell directory name: " + filename);
					} else if ((sliceID < 100) && (sliceID >= 10) && !(slice.substring(1,2).equals("0"))) {
						errors.add("Invalid zero-pad on geocell directory name: " + filename);
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that dataset directories begin with a 3-digit prefix.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyDatasetPrefix() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		DirectoryStream<Path> latitudeCells = Files.newDirectoryStream(Paths.get(this.path, "Tiles"));

		for (Path latCell : latitudeCells) {
			DirectoryStream<Path> longitudeCells = Files.newDirectoryStream(latCell);

			for (Path lonCell : longitudeCells) {
				DirectoryStream<Path> datasets = Files.newDirectoryStream(lonCell);

				for (Path dataset : datasets) {
					String filename = dataset.getFileName().toString();
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

					if ((prefixID != null) && (prefixID < 1)) {
						errors.add("Invalid prefix cannot be below 001: " + filename);
					}

				}

			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that dataset directories prefix code and name match and are valid values.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyDatasetCodeName() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		DirectoryStream<Path> latitudeCells = Files.newDirectoryStream(Paths.get(this.path, "Tiles"));
		DatasetsXml datasetDefs = new DatasetsXml("src/test/resources/CDB");

		for (Path latCell : latitudeCells) {
			DirectoryStream<Path> longitudeCells = Files.newDirectoryStream(latCell);

			for (Path lonCell : longitudeCells) {
				DirectoryStream<Path> datasets = Files.newDirectoryStream(lonCell);

				for (Path dataset : datasets) {
					String filename = dataset.getFileName().toString();
					String prefix = null;
					Integer prefixID = null;
					String datasetName = null;
					try {
						prefix = filename.substring(0, 3);
						prefixID = Integer.parseInt(prefix);
						datasetName = filename.split("_")[1];
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

					if ((prefixID != null) && (datasetName != null)) {
						if (!datasetDefs.isValidCode(prefixID)) {
							errors.add("Invalid dataset code: " + filename);
						} else if (!datasetDefs.isValidName(datasetName)) {
							errors.add("Invalid dataset name: " + filename);
						} else if (!datasetDefs.datasetNameForCode(prefixID).equals(datasetName)) {
							errors.add("Invalid dataset code/name combination: " + filename);
						}
					}

				}

			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that LOD directories have valid names.
	 *
	 * @throws IOException
	 */
	@Test
	public void verifyLODName() throws IOException {
		ArrayList<String> errors = new ArrayList<String>();
		DirectoryStream<Path> latitudeCells = Files.newDirectoryStream(Paths.get(this.path, "Tiles"));
		Pattern LODPattern = Pattern.compile("LC|L0[0-9]|L1[0-9]|L2[0-3]");

		for (Path latCell : latitudeCells) {
			DirectoryStream<Path> longitudeCells = Files.newDirectoryStream(latCell);

			for (Path lonCell : longitudeCells) {
				DirectoryStream<Path> datasets = Files.newDirectoryStream(lonCell);

				for (Path dataset : datasets) {
					DirectoryStream<Path> lods = Files.newDirectoryStream(dataset);

					for (Path lod : lods) {
						String filename = lod.getFileName().toString();
						Matcher match = LODPattern.matcher(filename);
						if (!match.find()) {
							errors.add("Invalid LOD name: " + filename);
						}
					}

				}

			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}
}
