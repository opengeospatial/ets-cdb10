package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.opengis.cite.cdb10.TestFixture;

public class VerifyNavigationStructureTests extends StructureTestFixture<NavigationStructureTests> {

	public VerifyNavigationStructureTests() throws IOException {
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
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Navigation", "400_NavData")));

		// execute
		this.testSuite.verifyDatasets();
	}

	@Test
	public void verifyDatasets_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyDatasets();
	}



	@Test(expected = AssertionError.class)
	public void verifyFile_WrongDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Navigation", "400_NavData",
				"D300_S001_T002.dbf")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS1Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Navigation", "400_NavData",
				"D400_S1_T002.dbf")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS1Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Navigation", "400_NavData",
				"D400_SABC_T002.dbf")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS2Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Navigation", "400_NavData",
				"D400_S001_T2.dbf")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS2Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Navigation", "400_NavData",
				"D400_S001_TABC.dbf")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test
	public void verifyFile_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("Navigation", "400_NavData",
				"D400_S001_T002.dbf")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test
	public void verifyFile_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyFile();
	}

}
