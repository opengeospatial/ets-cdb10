package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class VerifyGTModelTextureStructureTests extends StructureTestFixture<GTModelTextureStructureTests> {
	
	public VerifyGTModelTextureStructureTests() throws IOException {
		this.testSuite = new GTModelTextureStructureTests();
	}

	/*
	 * Verify Model Texture Files
	 */
	@Test(expected = AssertionError.class)
	public void verifyModelTextureFile_invalid() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"A", "B", "AB_1",
				"asdf")));

		// execute
		this.testSuite.verifyModelTextureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelTextureFile_invalidDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"A", "B", "AB_1",
				"D211_S002_T001_L10_AB_1.rgb")));

		// execute
		this.testSuite.verifyModelTextureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelTextureFile_invalidExtension() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"A", "B", "AB_1",
				"D511_S002_T001_L10_AB_1.txt")));

		// execute
		this.testSuite.verifyModelTextureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelTextureFile_badCS1Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"A", "B", "AB_1",
				"D511_S1_T001_L10_AB_1.rgb")));

		// execute
		this.testSuite.verifyModelTextureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelTextureFile_badCS1Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"A", "B", "AB_1",
				"D511_SABC_T001_L10_AB_1.rgb")));

		// execute
		this.testSuite.verifyModelTextureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelTextureFile_badCS2Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"A", "B", "AB_1",
				"D511_S002_T1_L10_AB_1.rgb")));

		// execute
		this.testSuite.verifyModelTextureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyModelTextureFile_badCS2Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"A", "B", "AB_1",
				"D511_S002_TABC_L10_AB_1.rgb")));

		// execute
		this.testSuite.verifyModelTextureFile();
	}

	@Test
	public void verifyModelTextureFile_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"A", "B", "AB_1",
				"D511_S002_T001_L10_AB_1.rgb")));

		// execute
		this.testSuite.verifyModelTextureFile();
	}

	@Test
	public void verifyModelTextureFile_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyModelTextureFile();
	}
}
