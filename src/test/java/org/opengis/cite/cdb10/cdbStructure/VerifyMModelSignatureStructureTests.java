package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.opengis.cite.cdb10.TestFixture;

public class VerifyMModelSignatureStructureTests extends TestFixture<MModelSignatureStructureTests> {

	public VerifyMModelSignatureStructureTests() {
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

}