package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class VerifyMModelSignatureStructureTests extends StructureTestFixture<MModelSignatureStructureTests> {

	public VerifyMModelSignatureStructureTests() throws IOException {
		this.testSuite = new MModelSignatureStructureTests();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISEntityKind_InvalidCode() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"999_Kind")));

		// execute
		this.testSuite.verifyDISEntityKind();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISEntityKind_InvalidName() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Bogus")));

		// execute
		this.testSuite.verifyDISEntityKind();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISEntityKind_Mismatch() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Other")));

		// execute
		this.testSuite.verifyDISEntityKind();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISEntityKind_InvalidFile() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"asdf")));

		// execute
		this.testSuite.verifyDISEntityKind();
	}

	@Test
	public void verifyDISEntityKind_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform")));

		// execute
		this.testSuite.verifyDISEntityKind();
	}

	@Test
	public void verifyDISEntityKind_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyDISEntityKind();
	}



	@Test(expected = AssertionError.class)
	public void verifyDISDomain_InvalidCode() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "999_Other")));

		// execute
		this.testSuite.verifyDISDomain();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISDomain_InvalidName() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Bogus")));

		// execute
		this.testSuite.verifyDISDomain();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISDomain_Mismatch() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Land")));

		// execute
		this.testSuite.verifyDISDomain();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISDomain_InvalidFile() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "asdf")));

		// execute
		this.testSuite.verifyDISDomain();
	}

	@Test
	public void verifyDISDomain_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other")));

		// execute
		this.testSuite.verifyDISDomain();
	}

	@Test
	public void verifyDISDomain_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyDISDomain();
	}



	@Test(expected = AssertionError.class)
	public void verifyDISCountry_InvalidCode() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "999_Other")));

		// execute
		this.testSuite.verifyDISCountry();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISCountry_InvalidName() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Bogus")));

		// execute
		this.testSuite.verifyDISCountry();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISCountry_Mismatch() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Afghanistan")));

		// execute
		this.testSuite.verifyDISCountry();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISCountry_InvalidFile() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "asdf")));

		// execute
		this.testSuite.verifyDISCountry();
	}

	@Test
	public void verifyDISCountry_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other")));

		// execute
		this.testSuite.verifyDISCountry();
	}

	@Test
	public void verifyDISCountry_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyDISCountry();
	}



	@Test(expected = AssertionError.class)
	public void verifyDISCategory_InvalidCode() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "999_Other")));

		// execute
		this.testSuite.verifyDISCategory();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISCategory_InvalidName() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Bogus")));

		// execute
		this.testSuite.verifyDISCategory();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISCategory_Mismatch() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Tank")));

		// execute
		this.testSuite.verifyDISCategory();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISCategory_InvalidFile() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "asdf")));

		// execute
		this.testSuite.verifyDISCategory();
	}

	@Test
	public void verifyDISCategory_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other")));

		// execute
		this.testSuite.verifyDISCategory();
	}

	@Test
	public void verifyDISCategory_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyDISCategory();
	}



	@Test(expected = AssertionError.class)
	public void verifyDISEntity_InvalidFile() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "asdf")));

		// execute
		this.testSuite.verifyDISEntity();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISEntity_Mismatch() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "0_0_0_0_0_0_0")));

		// execute
		this.testSuite.verifyDISEntity();
	}

	@Test
	public void verifyDISEntity_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "1_0_0_0_0_0_0")));

		// execute
		this.testSuite.verifyDISEntity();
	}

	@Test
	public void verifyDISEntity_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyDISEntity();
	}



	@Test(expected = AssertionError.class)
	public void verifyLOD_BadLevel() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "1_0_0_0_0_0_0", "LA")));

		// execute
		this.testSuite.verifyLOD();
	}

	@Test(expected = AssertionError.class)
	public void verifyLOD_TooHigh() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "1_0_0_0_0_0_0", "L99")));

		// execute
		this.testSuite.verifyLOD();
	}

	@Test(expected = AssertionError.class)
	public void verifyLOD_NoPadding() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "1_0_0_0_0_0_0", "L9")));

		// execute
		this.testSuite.verifyLOD();
	}

	@Test
	public void verifyLOD_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "1_0_0_0_0_0_0", "L23")));

		// execute
		this.testSuite.verifyLOD();
	}


	@Test
	public void verifyLOD_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyLOD();
	}



	@Test(expected = AssertionError.class)
	public void verifyFile_invalid() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "0_0_0_0_0_0_0", "LC",
				"asdf")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_invalidDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "0_0_0_0_0_0_0", "LC",
				"D600_S001_T001_LC10_0_0_0_0_0_0_0.shp")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_mismatchMMDC() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "0_0_0_0_0_0_0", "LC",
				"D606_S001_T001_LC10_1_1_0_0_0_0_0.shp")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS1Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "0_0_0_0_0_0_0", "LC",
				"D606_S1_T001_LC10_0_0_0_0_0_0_0.shp")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS1Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "0_0_0_0_0_0_0", "LC",
				"D606_SABC_T001_LC10_0_0_0_0_0_0_0.shp")));

		// execute
		this.testSuite.verifyFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyFile_badCS1() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "0_0_0_0_0_0_0", "LC",
				"D606_S000_T001_LC10_0_0_0_0_0_0_0.shp")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS2Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "0_0_0_0_0_0_0", "LC",
				"D606_S001_T1_LC10_0_0_0_0_0_0_0.shp")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS2Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "0_0_0_0_0_0_0", "LC",
				"D606_S001_TABC_LC10_0_0_0_0_0_0_0.shp")));

		// execute
		this.testSuite.verifyFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyFile_badCS2() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "0_0_0_0_0_0_0", "LC",
				"D606_S001_T033_LC10_0_0_0_0_0_0_0.shp")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test
	public void verifyFile_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "606_MModelSignature",
				"1_Platform", "0_Other", "0_Other", "0_Other", "0_0_0_0_0_0_0", "LC",
				"D606_S001_T001_LC10_0_0_0_0_0_0_0.shp")));

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
