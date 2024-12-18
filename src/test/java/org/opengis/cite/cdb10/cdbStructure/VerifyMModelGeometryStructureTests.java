package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class VerifyMModelGeometryStructureTests extends StructureTestFixture<MModelGeometryStructureTests> {

	public VerifyMModelGeometryStructureTests() throws IOException {
		this.testSuite = new MModelGeometryStructureTests();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISEntityKind_InvalidCode() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "999_Kind")));

		// execute
		this.testSuite.verifyDISEntityKind();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISEntityKind_InvalidName() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Bogus")));

		// execute
		this.testSuite.verifyDISEntityKind();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISEntityKind_Mismatch() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Other")));

		// execute
		this.testSuite.verifyDISEntityKind();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISEntityKind_InvalidFile() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "asdf")));

		// execute
		this.testSuite.verifyDISEntityKind();
	}

	@Test
	public void verifyDISEntityKind_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform")));

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
		Files.createDirectories(
				this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "999_Other")));

		// execute
		this.testSuite.verifyDISDomain();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISDomain_InvalidName() throws IOException {
		// setup
		Files.createDirectories(
				this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Bogus")));

		// execute
		this.testSuite.verifyDISDomain();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISDomain_Mismatch() throws IOException {
		// setup
		Files.createDirectories(
				this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Land")));

		// execute
		this.testSuite.verifyDISDomain();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISDomain_InvalidFile() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "asdf")));

		// execute
		this.testSuite.verifyDISDomain();
	}

	@Test
	public void verifyDISDomain_Good() throws IOException {
		// setup
		Files.createDirectories(
				this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other")));

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
		Files.createDirectories(
				this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other", "999_Other")));

		// execute
		this.testSuite.verifyDISCountry();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISCountry_InvalidName() throws IOException {
		// setup
		Files.createDirectories(
				this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other", "0_Bogus")));

		// execute
		this.testSuite.verifyDISCountry();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISCountry_Mismatch() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root
			.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other", "0_Afghanistan")));

		// execute
		this.testSuite.verifyDISCountry();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISCountry_InvalidFile() throws IOException {
		// setup
		Files.createDirectories(
				this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other", "asdf")));

		// execute
		this.testSuite.verifyDISCountry();
	}

	@Test
	public void verifyDISCountry_Good() throws IOException {
		// setup
		Files.createDirectories(
				this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other", "0_Other")));

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
		Files.createDirectories(this.cdb_root
			.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other", "0_Other", "999_Other")));

		// execute
		this.testSuite.verifyDISCategory();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISCategory_InvalidName() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root
			.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other", "0_Other", "0_Bogus")));

		// execute
		this.testSuite.verifyDISCategory();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISCategory_Mismatch() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root
			.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other", "0_Other", "0_Tank")));

		// execute
		this.testSuite.verifyDISCategory();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISCategory_InvalidFile() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root
			.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other", "0_Other", "asdf")));

		// execute
		this.testSuite.verifyDISCategory();
	}

	@Test
	public void verifyDISCategory_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root
			.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other", "0_Other", "0_Other")));

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
		Files.createDirectories(this.cdb_root
			.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other", "0_Other", "0_Other", "asdf")));

		// execute
		this.testSuite.verifyDISEntity();
	}

	@Test(expected = AssertionError.class)
	public void verifyDISEntity_Mismatch() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other",
				"0_Other", "0_Other", "0_0_0_0_0_0_0")));

		// execute
		this.testSuite.verifyDISEntity();
	}

	@Test
	public void verifyDISEntity_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other",
				"0_Other", "0_Other", "1_0_0_0_0_0_0")));

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
	public void verifyFile_invalid() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other",
				"0_Other", "0_Other", "0_0_0_0_0_0_0", "asdf")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_invalidDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other",
				"0_Other", "0_Other", "0_0_0_0_0_0_0", "D604_S001_T001_0_0_0_0_0_0_0.flt")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_invalidExtension() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other",
				"0_Other", "0_Other", "0_0_0_0_0_0_0", "D600_S001_T001_0_0_0_0_0_0_0.txt")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_mismatchExtension() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other",
				"0_Other", "0_Other", "0_0_0_0_0_0_0", "D600_S001_T001_0_0_0_0_0_0_0.xml")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_mismatchMMDC() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other",
				"0_Other", "0_Other", "0_0_0_0_0_0_0", "D600_S001_T001_1_1_0_0_0_0_0.flt")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS1Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other",
				"0_Other", "0_Other", "0_0_0_0_0_0_0", "D600_S1_T001_0_0_0_0_0_0_0.flt")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS1Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other",
				"0_Other", "0_Other", "0_0_0_0_0_0_0", "D600_SABC_T001_0_0_0_0_0_0_0.flt")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS1() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other",
				"0_Other", "0_Other", "0_0_0_0_0_0_0", "D600_S000_T001_0_0_0_0_0_0_0.flt")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS2Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other",
				"0_Other", "0_Other", "0_0_0_0_0_0_0", "D600_S001_T1_0_0_0_0_0_0_0.flt")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS2Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other",
				"0_Other", "0_Other", "0_0_0_0_0_0_0", "D600_S001_TABC_0_0_0_0_0_0_0.flt")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyFile_badCS2() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other",
				"0_Other", "0_Other", "0_0_0_0_0_0_0", "D600_S001_T000_0_0_0_0_0_0_0.flt")));

		// execute
		this.testSuite.verifyFile();
	}

	@Test
	public void verifyFile_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("MModel", "600_MModelGeometry", "1_Platform", "0_Other",
				"0_Other", "0_Other", "0_0_0_0_0_0_0", "D600_S001_T001_0_0_0_0_0_0_0.flt")));

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
