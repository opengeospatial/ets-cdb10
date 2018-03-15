package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.opengis.cite.cdb10.TestFixture;

public class VerifyTilesStructureTests extends TestFixture<TilesStructureTests> {

	public VerifyTilesStructureTests() {
		this.testSuite = new TilesStructureTests();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeocellLatitudeDirNamePrefix_Bad() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "B99")));

		// execute
		this.testSuite.verifyGeocellLatitudeDirNamePrefix();
	}

	@Test
	public void verifyGeocellLatitudeDirNamePrefix_GoodNorth() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N00")));

		// execute
		this.testSuite.verifyGeocellLatitudeDirNamePrefix();
	}

	@Test
	public void verifyGeocellLatitudeDirNamePrefix_GoodSouth() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "S90")));

		// execute
		this.testSuite.verifyGeocellLatitudeDirNamePrefix();
	}



	@Test(expected = AssertionError.class)
	public void verifyGeocellLatitudeDirNameSlice_BadNorth() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N90")));

		// execute
		this.testSuite.verifyGeocellLatitudeDirNameSlice();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeocellLatitudeDirNameSlice_BadSouth() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "S0")));

		// execute
		this.testSuite.verifyGeocellLatitudeDirNameSlice();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeocellLatitudeDirNameSlice_BadRange() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "S91")));

		// execute
		this.testSuite.verifyGeocellLatitudeDirNameSlice();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeocellLatitudeDirNameSlice_BadLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N0")));

		// execute
		this.testSuite.verifyGeocellLatitudeDirNameSlice();
	}

	@Test
	public void verifyGeocellLatitudeDirNameSlice_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N00")));

		// execute
		this.testSuite.verifyGeocellLatitudeDirNameSlice();
	}



	@Test(expected = AssertionError.class)
	public void verifyGeocellLongitudeDirNamePrefix_Bad() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N00", "A000")));

		// execute
		this.testSuite.verifyGeocellLongitudeDirNamePrefix();
	}

	@Test
	public void verifyGeocellLongitudeDirNamePrefix_GoodEast() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N00", "E000")));

		// execute
		this.testSuite.verifyGeocellLongitudeDirNamePrefix();
	}

	@Test
	public void verifyGeocellLongitudeDirNamePrefix_GoodWest() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N00", "W180")));

		// execute
		this.testSuite.verifyGeocellLongitudeDirNamePrefix();
	}



	@Test(expected = AssertionError.class)
	public void verifyGeocellLongitudeDirNameSlice_BadLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N00", "E00100")));

		// execute
		this.testSuite.verifyGeocellLongitudeDirNameSlice();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeocellLongitudeDirNameSlice_BadRange() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N00", "E999")));

		// execute
		this.testSuite.verifyGeocellLongitudeDirNameSlice();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeocellLongitudeDirNameSlice_BadRangeWest() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N00", "W0")));

		// execute
		this.testSuite.verifyGeocellLongitudeDirNameSlice();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeocellLongitudeDirNameSlice_BadPadding() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N00", "E90")));

		// execute
		this.testSuite.verifyGeocellLongitudeDirNameSlice();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeocellLongitudeDirNameSlice_BadPaddingShort() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N00", "E5")));

		// execute
		this.testSuite.verifyGeocellLongitudeDirNameSlice();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeocellLongitudeDirNameSlice_BadPaddingChar() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N00", "E__5")));

		// execute
		this.testSuite.verifyGeocellLongitudeDirNameSlice();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeocellLongitudeDirNameSlice_BadOffset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N62", "W161")));

		// execute
		this.testSuite.verifyGeocellLongitudeDirNameSlice();
	}

	@Test
	public void verifyGeocellLongitudeDirNameSlice_GoodWest() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N00", "W180")));

		// execute
		this.testSuite.verifyGeocellLongitudeDirNameSlice();
	}

	@Test
	public void verifyGeocellLongitudeDirNameSlice_GoodEast() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N00", "E000")));

		// execute
		this.testSuite.verifyGeocellLongitudeDirNameSlice();
	}

	@Test
	public void verifyGeocellLongitudeDirNameSlice_GoodOffset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N62", "W162")));

		// execute
		this.testSuite.verifyGeocellLongitudeDirNameSlice();
	}



	@Test(expected = AssertionError.class)
	public void verifyDatasetPrefix_BadLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N62", "W162", "_")));

		// execute
		this.testSuite.verifyDatasetPrefix();
	}

	@Test(expected = AssertionError.class)
	public void verifyDatasetPrefix_BadNumeric() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N62", "W162", "abc_Elevation")));

		// execute
		this.testSuite.verifyDatasetPrefix();
	}

	@Test(expected = AssertionError.class)
	public void verifyDatasetPrefix_BadChars() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N62", "W162", "-11_Elevation")));

		// execute
		this.testSuite.verifyDatasetPrefix();
	}

	@Test
	public void verifyDatasetPrefix_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N62", "W162", "001_Elevation")));

		// execute
		this.testSuite.verifyDatasetPrefix();
	}



	@Test(expected = AssertionError.class)
	public void verifyDatasetCodeName_Missing() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N62", "W162", "001_")));

		// execute
		this.testSuite.verifyDatasetCodeName();
	}

	@Test(expected = AssertionError.class)
	public void verifyDatasetCodeName_Unknown() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N62", "W162", "001_Bogus")));

		// execute
		this.testSuite.verifyDatasetCodeName();
	}

	@Test(expected = AssertionError.class)
	public void verifyDatasetCodeName_Mismatch() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N62", "W162", "001_Imagery")));

		// execute
		this.testSuite.verifyDatasetCodeName();
	}

	@Test(expected = AssertionError.class)
	public void verifyDatasetCodeName_BadCode() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N62", "W162", "999_Imagery")));

		// execute
		this.testSuite.verifyDatasetCodeName();
	}

	@Test
	public void verifyDatasetCodeName_Correct() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N62", "W162", "001_Elevation")));

		// execute
		this.testSuite.verifyDatasetCodeName();
	}



	@Test(expected = AssertionError.class)
	public void verifyLODName_Unknown() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N62", "W162", "004_Imagery",
				"LAA")));

		// execute
		this.testSuite.verifyLODName();
	}

	@Test(expected = AssertionError.class)
	public void verifyLODName_BadRange() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N62", "W162", "004_Imagery",
				"L99")));

		// execute
		this.testSuite.verifyLODName();
	}

	@Test
	public void verifyLODName_GoodCoarse() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N62", "W162", "004_Imagery",
				"LC")));

		// execute
		this.testSuite.verifyLODName();
	}

	@Test
	public void verifyLODName_GoodHiRes() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "N62", "W162", "004_Imagery",
				"L23")));

		// execute
		this.testSuite.verifyLODName();
	}
}
