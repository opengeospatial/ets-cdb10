package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class VerifyGTModelInteriorMaterialStructureTests  extends StructureTestFixture<GTModelInteriorMaterialStructureTests> {
	
	public VerifyGTModelInteriorMaterialStructureTests() throws IOException {
		this.testSuite = new GTModelInteriorMaterialStructureTests();
	}
	
	/*
	 * Verify Model Interior Material Files
	 */
	@Test(expected = AssertionError.class)
	public void verifyModelInteriorMaterialFile_invalid() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "509_GTModelInteriorMaterial",
				"A", "B", "AB_1",
				"asdf")));

		// execute
		this.testSuite.verifyModelInteriorMaterialFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelInteriorMaterialFile_invalidDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "509_GTModelInteriorMaterial",
				"A", "B", "AB_1",
				"D211_S001_T001_L10_AB_1.tif")));

		// execute
		this.testSuite.verifyModelInteriorMaterialFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelInteriorMaterialFile_invalidExtension() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "509_GTModelInteriorMaterial",
				"A", "B", "AB_1",
				"D509_S001_T001_L10_AB_1.txt")));

		// execute
		this.testSuite.verifyModelInteriorMaterialFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelInteriorMaterialFile_badCS1Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "509_GTModelInteriorMaterial",
				"A", "B", "AB_1",
				"D509_S1_T001_L10_AB_1.tif")));

		// execute
		this.testSuite.verifyModelInteriorMaterialFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelInteriorMaterialFile_badCS1Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "509_GTModelInteriorMaterial",
				"A", "B", "AB_1",
				"D509_SABC_T001_L10_AB_1.tif")));

		// execute
		this.testSuite.verifyModelInteriorMaterialFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyModelInteriorMaterialFile_badCS1() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "509_GTModelInteriorMaterial",
				"A", "B", "AB_1",
				"D509_S003_T001_L10_AB_1.tif")));

		// execute
		this.testSuite.verifyModelInteriorMaterialFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelInteriorMaterialFile_badCS2Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "509_GTModelInteriorMaterial",
				"A", "B", "AB_1",
				"D509_S001_T1_L10_AB_1.tif")));

		// execute
		this.testSuite.verifyModelInteriorMaterialFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelInteriorMaterialFile_badCS2Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "509_GTModelInteriorMaterial",
				"A", "B", "AB_1",
				"D509_S001_TABC_L10_AB_1.tif")));

		// execute
		this.testSuite.verifyModelInteriorMaterialFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyModelInteriorMaterialFile_badCS2() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "509_GTModelInteriorMaterial",
				"A", "B", "AB_1",
				"D509_S002_T255_L10_AB_1.tif")));

		// execute
		this.testSuite.verifyModelInteriorMaterialFile();
	}

	@Test
	public void verifyModelInteriorMaterialFile_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "509_GTModelInteriorMaterial",
				"A", "B", "AB_1",
				"D509_S001_T001_L10_AB_1.tif")));

		// execute
		this.testSuite.verifyModelInteriorMaterialFile();
	}

	@Test
	public void verifyModelInteriorMaterialFile_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyModelInteriorMaterialFile();
	}

}
