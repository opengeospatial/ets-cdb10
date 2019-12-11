package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class VerifyMModelTextureStructureTests extends StructureTestFixture<MModelTextureStructureTests> {

	public VerifyMModelTextureStructureTests() throws IOException {
		this.testSuite = new MModelTextureStructureTests();
	}

	@Test(expected = AssertionError.class)
	public void verifyTNAMPrefix_TooLong() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"ABC")));

		// execute
		this.testSuite.verifyTNAMPrefix();
	}

	@Test(expected = AssertionError.class)
	public void verifyTNAMPrefix_WrongCase() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"a")));

		// execute
		this.testSuite.verifyTNAMPrefix();
	}

	@Test
	public void verifyTNAMPrefix_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A")));

		// execute
		this.testSuite.verifyTNAMPrefix();
	}


	@Test
	public void verifyTNAMPrefix_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyTNAMPrefix();
	}



	@Test(expected = AssertionError.class)
	public void verifyTNAMSecond_TooLong() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "BCD")));

		// execute
		this.testSuite.verifyTNAMSecond();
	}

	@Test(expected = AssertionError.class)
	public void verifyTNAMSecond_WrongCase() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "b")));

		// execute
		this.testSuite.verifyTNAMSecond();
	}

	@Test
	public void verifyTNAMSecond_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B")));

		// execute
		this.testSuite.verifyTNAMSecond();
	}

	@Test
	public void verifyTNAMSecond_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyTNAMSecond();
	}



	@Test(expected = AssertionError.class)
	public void verifyTNAM_TooShort() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B", "A")));

		// execute
		this.testSuite.verifyTNAM();
	}

	@Test(expected = AssertionError.class)
	public void verifyTNAM_TooLong() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")));

		// execute
		this.testSuite.verifyTNAM();
	}

	@Test(expected = AssertionError.class)
	public void verifyTNAM_WrongChars() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B", "$$AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")));

		// execute
		this.testSuite.verifyTNAM();
	}

	@Test(expected = AssertionError.class)
	public void verifyTNAM_Mismatch() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B", "AC_1")));

		// execute
		this.testSuite.verifyTNAM();
	}

	@Test
	public void verifyTNAM_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B", "AB_1")));

		// execute
		this.testSuite.verifyTNAM();
	}

	@Test
	public void verifyTNAM_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyTNAM();
	}



	@Test(expected = AssertionError.class)
	public void verifyFile_invalid() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B", "AB", "asdf")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_invalidDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B", "AB", "D600_S001_T001_W10_AB.rgb")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_invalidExtension() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B", "AB", "D601_S001_T001_W10_AB.txt")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_mismatchExtension() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B", "AB", "D601_S001_T001_W10_AB.xml")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_mismatchMMDC() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B", "AB", "D601_S001_T001_W10_ABCD.rgb")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS1Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B", "AB", "D601_S1_T001_W10_AB.rgb")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS1Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B", "AB", "D601_SABC_T001_W10_AB.rgb")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS2Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B", "AB", "D601_S001_T1_W10_AB.rgb")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS2Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B", "AB", "D601_S001_TABC_W10_AB.rgb")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test
	public void verifyFile_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "601_MModelTexture",
				"A", "B", "AB", "D601_S002_T001_W10_AB.rgb")));

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
