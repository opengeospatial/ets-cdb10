package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class VerifyGTModelMaterialStructureTests extends StructureTestFixture<GTModelMaterialStructureTests> {
	
	public VerifyGTModelMaterialStructureTests() throws IOException {
		this.testSuite = new GTModelMaterialStructureTests();
	}
	
	/*
	 * Verify Model Material Files
	 */
	@Test(expected = AssertionError.class)
	public void verifyModelMaterialFile_invalid() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "504_GTModelMaterial",
				"A", "B", "AB_1",
				"asdf")));

		// execute
		this.testSuite.verifyModelMaterialFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelMaterialFile_invalidDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "504_GTModelMaterial",
				"A", "B", "AB_1",
				"D211_S001_T001_L10_AC_1.tif")));

		// execute
		this.testSuite.verifyModelMaterialFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelMaterialFile_invalidExtension() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "504_GTModelMaterial",
				"A", "B", "AB_1",
				"D504_S001_T001_L10_AC_1.txt")));

		// execute
		this.testSuite.verifyModelMaterialFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelMaterialFile_badCS1Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "504_GTModelMaterial",
				"A", "B", "AB_1",
				"D504_S1_T001_L10_AC_1.tif")));

		// execute
		this.testSuite.verifyModelMaterialFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelMaterialFile_badCS1Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "504_GTModelMaterial",
				"A", "B", "AB_1",
				"D504_SABC_T001_L10_AC_1.tif")));

		// execute
		this.testSuite.verifyModelMaterialFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelMaterialFile_badCS2Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "504_GTModelMaterial",
				"A", "B", "AB_1",
				"D504_S001_T1_L10_AC_1.tif")));

		// execute
		this.testSuite.verifyModelMaterialFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelMaterialFile_badCS2Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "504_GTModelMaterial",
				"A", "B", "AB_1",
				"D504_S001_TABC_L10_AC_1.tif")));

		// execute
		this.testSuite.verifyModelMaterialFile();
	}

	@Test
	public void verifyModelMaterialFile_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "504_GTModelMaterial",
				"A", "B", "AB_1",
				"D504_S001_T001_L10_AB_1.tif")));

		// execute
		this.testSuite.verifyModelMaterialFile();
	}

	@Test
	public void verifyModelMaterialFile_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyModelMaterialFile();
	}

}
