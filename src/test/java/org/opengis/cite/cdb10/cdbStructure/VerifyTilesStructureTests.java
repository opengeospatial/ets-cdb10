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
}
