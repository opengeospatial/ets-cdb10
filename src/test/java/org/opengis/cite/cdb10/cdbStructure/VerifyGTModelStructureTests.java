package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.opengis.cite.cdb10.TestFixture;

public class VerifyGTModelStructureTests extends TestFixture<GTModelStructureTests> {

	public VerifyGTModelStructureTests() {
		this.testSuite = new GTModelStructureTests();
	}

	@Test(expected = AssertionError.class)
	public void verifyDatasetCodePrefix_BadShort() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "5_GTModelGeometry")));

		// execute
		this.testSuite.verifyDatasetCodePrefix();
	}

	@Test(expected = AssertionError.class)
	public void verifyDatasetCodePrefix_BadNumber() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "5xx_GTModelGeometry")));

		// execute
		this.testSuite.verifyDatasetCodePrefix();
	}

	@Test(expected = AssertionError.class)
	public void verifyDatasetCodePrefix_BadRange() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "-1_GTModelGeometry")));

		// execute
		this.testSuite.verifyDatasetCodePrefix();
	}

	@Test
	public void verifyDatasetCodePrefix_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry")));

		// execute
		this.testSuite.verifyDatasetCodePrefix();
	}
}
