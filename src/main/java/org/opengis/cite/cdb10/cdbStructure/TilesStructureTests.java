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
import org.opengis.cite.cdb10.util.metadataXml.DatasetsXml;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * File/directory structure tests for the Tiles directory of the CDB
 */
public class TilesStructureTests extends Capability1Tests {

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
	 * Validates that latitude geocell directories start with "S" or "N".
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test(description = "OGC 15-113r5, A.1.16, Test 65 - based on Section 3.6")
	public void verifyGeocellLatitudeDirNamePrefix() throws IOException {
		Path tilesPath = Paths.get(this.path, "Tiles");

		if (Files.notExists(tilesPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		for (Path file : Files.newDirectoryStream(tilesPath)) {
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
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test(description = "OGC 15-113r5, A.1.16, Test 65 - based on Section 3.6")
	public void verifyGeocellLatitudeDirNameSlice() throws IOException {
		Path tilesPath = Paths.get(this.path, "Tiles");

		if (Files.notExists(tilesPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		for (Path file : Files.newDirectoryStream(tilesPath)) {
			String filename = file.getFileName().toString();
			String slice = filename.substring(1, filename.length());

			if (filename.length() != 3) {
				errors.add("Invalid padding/length on geocell directory name: " + filename);
			} else if (filename.substring(0, 1).equals("S")) {
				Integer sliceID = Integer.parseInt(slice);
				if ((sliceID > 90) || (sliceID <= 0)) {
					errors.add("Invalid latitude for geocell directory name: " + filename);
				}

				if ((sliceID < 10) && (sliceID > 0) && !(slice.substring(0,1).equals("0"))) {
					errors.add("Invalid zero-pad on geocell directory name: " + filename);
				}

			} else if (filename.substring(0, 1).equals("N")) {
				Integer sliceID = Integer.parseInt(slice);
				if ((sliceID > 89) || (sliceID < 0)) {
					errors.add("Invalid latitude for geocell directory name: " + filename);
				}

				if ((sliceID < 10) && (sliceID >= 0) && !(slice.substring(0,1).equals("0"))) {
					errors.add("Invalid zero-pad on geocell directory name: " + filename);
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that longitude geocell directories start with "E" or "W".
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test(description = "OGC 15-113r5, A.1.16, Test 66 - based on Section 3.6")
	public void verifyGeocellLongitudeDirNamePrefix() throws IOException {
		Path tilesPath = Paths.get(this.path, "Tiles");

		if (Files.notExists(tilesPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		DirectoryStream<Path> latitudeCells = Files.newDirectoryStream(tilesPath);

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
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test(description = "OGC 15-113r5, A.1.16, Test 66 - based on Section 3.6")
	public void verifyGeocellLongitudeDirNameSlice() throws IOException {
		Path tilesPath = Paths.get(this.path, "Tiles");

		if (Files.notExists(tilesPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();

		DirectoryStream<Path> latitudeCells = Files.newDirectoryStream(tilesPath);

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

					if ((sliceID < 10) && (sliceID >= 0) && !(slice.substring(0,2).equals("00"))) {
						errors.add("Invalid zero-pad on geocell directory name: " + filename);
					} else if ((sliceID < 100) && (sliceID >= 10) && !(slice.substring(0,1).equals("0"))) {
						errors.add("Invalid zero-pad on geocell directory name: " + filename);
					}
				} else if (filename.substring(0, 1).equals("W")) {
					if ((sliceID > 180) || (sliceID <= 0)) {
						errors.add("Invalid longitude for geocell directory name: " + filename);
					}

					if ((sliceID % this.sliceWidthForLatitude(latSliceID)) != 0) {
						errors.add("Invalid slice width for geocell directory name: " + filename);
					}

					if ((sliceID < 10) && (sliceID >= 0) && !(slice.substring(0,2).equals("00"))) {
						errors.add("Invalid zero-pad on geocell directory name: " + filename);
					} else if ((sliceID < 100) && (sliceID >= 10) && !(slice.substring(0,1).equals("0"))) {
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
	 * @throws IOException DirectoryStream error
	 */
	@Test(description = "OGC 15-113r5, Section 3.6.2.3")
	public void verifyDatasetPrefix() throws IOException {
		Path tilesPath = Paths.get(this.path, "Tiles");

		if (Files.notExists(tilesPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		DirectoryStream<Path> latitudeCells = Files.newDirectoryStream(tilesPath);

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
	 * @throws IOException DirectoryStream error
	 */
	@Test(description = "OGC 15-113r5, Section 3.6.2.3")
	public void verifyDatasetCodeName() throws IOException {
		Path tilesPath = Paths.get(this.path, "Tiles");

		if (Files.notExists(tilesPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		DirectoryStream<Path> latitudeCells = Files.newDirectoryStream(tilesPath);
		DatasetsXml datasetDefs = new DatasetsXml(SAMPLE_CDB_PATH);
		
		if (Files.notExists(datasetDefs.getXmlFilePath())) {
			return;
		}

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

					if ((prefixID != null) && (datasetName != null) && !datasetDefs.isExtendedCode(prefixID)) {
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
	 * @throws IOException DirectoryStream error
	 */
	@Test(description = "OGC 15-113r5, Section 3.6.2.4")
	public void verifyLODName() throws IOException {
		Path tilesPath = Paths.get(this.path, "Tiles");

		if (Files.notExists(tilesPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		DirectoryStream<Path> latitudeCells = Files.newDirectoryStream(tilesPath);

		for (Path latCell : latitudeCells) {
			DirectoryStream<Path> longitudeCells = Files.newDirectoryStream(latCell);

			for (Path lonCell : longitudeCells) {
				DirectoryStream<Path> datasets = Files.newDirectoryStream(lonCell);

				for (Path dataset : datasets) {
					DirectoryStream<Path> lods = Files.newDirectoryStream(dataset, DirectoryStreamFilters.lodFilter());

					for (Path lod : lods) {
						validateLOD(lod, errors);
					}

				}

			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

	/**
	 * Validates that UREF directories have valid names and are in a valid range for the LOD.
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test(description = "OGC 15-113r5, A.1.16, Test 67 - based on Section 3.6")
	public void verifyUREFName() throws IOException {
		Path tilesPath = Paths.get(this.path, "Tiles");

		if (Files.notExists(tilesPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		DirectoryStream<Path> latitudeCells = Files.newDirectoryStream(tilesPath);

		for (Path latCell : latitudeCells) {
			DirectoryStream<Path> longitudeCells = Files.newDirectoryStream(latCell);

			for (Path lonCell : longitudeCells) {
				DirectoryStream<Path> datasets = Files.newDirectoryStream(lonCell);

				for (Path dataset : datasets) {
					DirectoryStream<Path> lods = Files.newDirectoryStream(dataset, DirectoryStreamFilters.lodFilter());

					for (Path lod : lods) {
						String lodFilename = lod.getFileName().toString();
						Integer lodLevel = null;
						if (!lodFilename.equals("LC")) {
							lodLevel = Integer.parseInt(lodFilename.substring(1, lodFilename.length()));
						}

						DirectoryStream<Path> urefs = Files.newDirectoryStream(lod);

						for (Path uref : urefs) {
							String filename = uref.getFileName().toString();

							if (!filename.substring(0, 1).equals("U")) {
								errors.add("Invalid prefix for UREF directory: " + filename);
							} else {
								Integer urefValue = Integer.parseInt(filename.substring(1, filename.length()));

								// Negative LODs cannot be calculated here as we
								// only know that the LOD is < 0
								if ((lodLevel != null) && ((urefValue < 0) || (urefValue > (Math.pow(2, lodLevel) - 1)))) {
									errors.add("UREF value out of bounds: " + filename);
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
	 * Validates that tiled dataset files have valid names.
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test(description = "OGC 15-113r5, Section 3.6.2")
	public void verifyDatasetFileName() throws IOException {
		Path tilesPath = Paths.get(this.path, "Tiles");

		if (Files.notExists(tilesPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		DirectoryStream<Path> latitudeCells = Files.newDirectoryStream(tilesPath);
		Pattern filePattern = Pattern.compile(FilenamePatterns.Tiles);

		for (Path latCell : latitudeCells) {
			String latFilename = latCell.getFileName().toString();
			DirectoryStream<Path> longitudeCells = Files.newDirectoryStream(latCell);

			for (Path lonCell : longitudeCells) {
				String lonFilename = lonCell.getFileName().toString();
				DirectoryStream<Path> datasets = Files.newDirectoryStream(lonCell);

				for (Path dataset : datasets) {
					String datasetFilename = dataset.getFileName().toString();
					DirectoryStream<Path> lods = Files.newDirectoryStream(dataset, DirectoryStreamFilters.lodFilter());

					for (Path lod : lods) {
						String lodFilename = lod.getFileName().toString();
						DirectoryStream<Path> urefs = Files.newDirectoryStream(lod);

						for (Path uref : urefs) {
							String urefFilename = uref.getFileName().toString();
							DirectoryStream<Path> datasetFiles = Files.newDirectoryStream(uref);

							for (Path datasetFile : datasetFiles) {
								String filename = datasetFile.getFileName().toString();
								Matcher match = filePattern.matcher(filename);
								if (!match.find()) {
									errors.add("Invalid dataset file name: " + filename + " according to test pattern.");
								} else {
									if (!match.group("lat").equals(latFilename)) {
										errors.add("Latitude geocell prefix does not match parent directory: "
												+ filename);
									}

									if (!match.group("lon").equals(lonFilename)) {
										errors.add("Longitude geocell prefix does not match parent directory: "
												+ filename);
									}

									if (!match.group("datasetCode").equals(datasetFilename.substring(0, 3))) {
										errors.add("Dataset code does not match parent directory: "
												+ filename);
									}

									if ((match.group("lod").startsWith("LC") && !lodFilename.equals("LC")) ||
										(!match.group("lod").startsWith("LC") && !match.group("lod").equals(lodFilename))) {
										errors.add("LOD does not match parent directory: " + filename);
									}

									if (!match.group("uref").equals(urefFilename)) {
										errors.add("UREF does not match parent directory: " + filename);
									}

									// Only check RREF bounds for positive LODs
									if (!lodFilename.equals("LC")) {
										Integer lodLevel = Integer.parseInt(lodFilename.substring(1, lodFilename.length()));
										
										if (Integer.parseInt(match.group("rref")) > (Math.pow(2, lodLevel) - 1)) {
											errors.add("RREF out of bounds for LOD: " + filename);
										}
									}

									String datasetID = datasetFilename.split("_")[0];
									String cs1 = match.group("cs1");
									String cs2 = match.group("cs2");
									
									validateComponentSelectorFormat(cs1, 1, filename, errors);
									validateComponentSelector1(cs1, datasetID, errors);
									validateComponentSelectorFormat(cs2, 2, filename, errors);
									validateComponentSelector2(cs2, cs1, datasetID, errors);

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
