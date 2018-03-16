package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.opengis.cite.cdb10.TestFixture;

public class VerifyNavigationStructureTests extends TestFixture<NavigationStructureTests> {

	public VerifyNavigationStructureTests() {
		this.testSuite = new NavigationStructureTests();
	}

	@Test(expected = AssertionError.class)
	public void verifyDatasets_WrongDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Navigation", "300")));

		// execute
		this.testSuite.verifyDatasets();
	}

	@Test
	public void verifyDatasets_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Navigation", "400_NavDat")));

		// execute
		this.testSuite.verifyDatasets();
	}

}
