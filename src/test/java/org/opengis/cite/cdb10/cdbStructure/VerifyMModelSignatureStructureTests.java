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

}