package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class VerifyGTModelCMTStructureTests extends StructureTestFixture<GTModelCMTStructureTests> {
	
	public VerifyGTModelCMTStructureTests() throws IOException {
		this.testSuite = new GTModelCMTStructureTests();
	}

	/*
	 * Verify Model CMT Files
	 */
	@Test(expected = AssertionError.class)
	public void verifyCMTFile_invalid() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "505_GTModelCMT",
				"A", "B", "AB_1",
				"asdf")));

		// execute
		this.testSuite.verifyCMTFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyCMTFile_invalidDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "505_GTModelCMT",
				"A", "B", "AB_1",
				"D211_S001_T001_AB_1.xml")));

		// execute
		this.testSuite.verifyCMTFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyCMTFile_invalidExtension() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "505_GTModelCMT",
				"A", "B", "AB_1",
				"D505_S001_T001_AB_1.txt")));

		// execute
		this.testSuite.verifyCMTFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyCMTFile_badCS1Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "505_GTModelCMT",
				"A", "B", "AB_1",
				"D505_S1_T001_AB_1.xml")));

		// execute
		this.testSuite.verifyCMTFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyCMTFile_badCS1Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "505_GTModelCMT",
				"A", "B", "AB_1",
				"D505_SABC_T001_AB_1.xml")));

		// execute
		this.testSuite.verifyCMTFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyCMTFile_badCS1() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "505_GTModelCMT",
				"A", "B", "AB_1",
				"D505_S002_T001_AB_1.xml")));

		// execute
		this.testSuite.verifyCMTFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyCMTFile_badCS2Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "505_GTModelCMT",
				"A", "B", "AB_1",
				"D505_S001_T1_AB_1.xml")));

		// execute
		this.testSuite.verifyCMTFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyCMTFile_badCS2Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "505_GTModelCMT",
				"A", "B", "AB_1",
				"D505_S001_TABC_AB_1.xml")));

		// execute
		this.testSuite.verifyCMTFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyCMTFile_badCS2() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "505_GTModelCMT",
				"A", "B", "AB_1",
				"D505_S001_T002_AB_1.xml")));

		// execute
		this.testSuite.verifyCMTFile();
	}

	@Test
	public void verifyCMTFile_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "505_GTModelCMT",
				"A", "B", "AB_1",
				"D505_S001_T001_AB_1.xml")));

		// execute
		this.testSuite.verifyCMTFile();
	}

	@Test
	public void verifyCMTFile_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyCMTFile();
	}
}
