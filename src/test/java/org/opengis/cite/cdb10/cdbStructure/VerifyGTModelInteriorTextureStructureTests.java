package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class VerifyGTModelInteriorTextureStructureTests extends StructureTestFixture<GTModelInteriorTextureStructureTests> {
	
	public VerifyGTModelInteriorTextureStructureTests() throws IOException {
		this.testSuite = new GTModelInteriorTextureStructureTests();
	}

	/*
	 * Verify Model Interior Texture Files
	 */
	@Test(expected = AssertionError.class)
	public void verifyModelInteriorTextureFile_invalid() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "507_GTModelInteriorTexture",
				"A", "B", "AB_1",
				"asdf")));

		// execute
		this.testSuite.verifyModelInteriorTextureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelInteriorTextureFile_invalidDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "507_GTModelInteriorTexture",
				"A", "B", "AB_1",
				"D211_S001_T001_L10_AB_1.rgb")));

		// execute
		this.testSuite.verifyModelInteriorTextureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelInteriorTextureFile_invalidExtension() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "507_GTModelInteriorTexture",
				"A", "B", "AB_1",
				"D507_S001_T001_L10_AB_1.txt")));

		// execute
		this.testSuite.verifyModelInteriorTextureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelInteriorTextureFile_badCS1Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "507_GTModelInteriorTexture",
				"A", "B", "AB_1",
				"D507_S1_T001_L10_AB_1.rgb")));

		// execute
		this.testSuite.verifyModelInteriorTextureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelInteriorTextureFile_badCS1Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "507_GTModelInteriorTexture",
				"A", "B", "AB_1",
				"D507_SABC_T001_L10_AB_1.rgb")));

		// execute
		this.testSuite.verifyModelInteriorTextureFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyModelInteriorTextureFile_badCS1() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "507_GTModelInteriorTexture",
				"A", "B", "AB_1",
				"D507_S001_T001_L10_AB_1.rgb")));

		// execute
		this.testSuite.verifyModelInteriorTextureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelInteriorTextureFile_badCS2Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "507_GTModelInteriorTexture",
				"A", "B", "AB_1",
				"D507_S001_T1_L10_AB_1.rgb")));

		// execute
		this.testSuite.verifyModelInteriorTextureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelInteriorTextureFile_badCS2Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "507_GTModelInteriorTexture",
				"A", "B", "AB_1",
				"D507_S001_TABC_L10_AB_1.rgb")));

		// execute
		this.testSuite.verifyModelInteriorTextureFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyModelInteriorTextureFile_badCS2() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "507_GTModelInteriorTexture",
				"A", "B", "AB_1",
				"D507_S002_T014_L10_AB_1.rgb")));

		// execute
		this.testSuite.verifyModelInteriorTextureFile();
	}

	@Test
	public void verifyModelInteriorTextureFile_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "507_GTModelInteriorTexture",
				"A", "B", "AB_1",
				"D507_S002_T001_L10_AB_1.rgb")));

		// execute
		this.testSuite.verifyModelInteriorTextureFile();
	}

	@Test
	public void verifyModelInteriorTextureFile_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyModelInteriorTextureFile();
	}

}
