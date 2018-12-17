package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class VerifyGTModelSignatureStructureTests  extends StructureTestFixture<GTModelSignatureStructureTests> {
	
	public VerifyGTModelSignatureStructureTests() throws IOException {
		this.testSuite = new GTModelSignatureStructureTests();
	}
	
	/*
	 * GTModelSignature File Naming
	 */
	
	@Test(expected = AssertionError.class)
	public void verifyGeometrySignatureFile_invalid() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "502_GTModelSignature",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"asdf")));

		// execute
		this.testSuite.verifyGeometrySignatureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometrySignatureFile_invalidDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "502_GTModelSignature",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D501_S001_T001_L10_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometrySignatureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometrySignatureFile_badCS1Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "502_GTModelSignature",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D512_S1_T001_L10_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometrySignatureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometrySignatureFile_badCS1Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "502_GTModelSignature",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D512_SABC_T001_L10_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometrySignatureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometrySignatureFile_badCS2Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "502_GTModelSignature",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D512_S001_T1_L10_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometrySignatureFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometrySignatureFile_badCS2Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "502_GTModelSignature",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D512_S001_TABC_L10_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometrySignatureFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyGeometrySignatureFile_badFCLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "502_GTModelSignature",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D512_S001_T001_L10_45_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometrySignatureFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyGeometrySignatureFile_badFSCLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "502_GTModelSignature",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D512_S001_T001_L10_12345_1_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometrySignatureFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyGeometrySignatureFile_badFSCType() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "502_GTModelSignature",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D512_S001_T001_L10_12345_ABC_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometrySignatureFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyGeometrySignatureFile_badMODLLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "502_GTModelSignature",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D512_S001_T001_L10_12345_001_amodelnameamodelnameamodelnameamodelname.flt")));

		// execute
		this.testSuite.verifyGeometrySignatureFile();
	}

	@Test
	public void verifyGeometrySignatureFile_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "502_GTModelSignature",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D512_S001_T001_L10_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometrySignatureFile();
	}

	@Test
	public void verifyGeometrySignatureFile_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyGeometrySignatureFile();
	}

}
