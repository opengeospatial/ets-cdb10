package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class VerifyGTModelGeometryStructureTests extends StructureTestFixture<GTModelGeometryStructureTests> {
	
	public VerifyGTModelGeometryStructureTests() throws IOException {
		this.testSuite = new GTModelGeometryStructureTests();
	}
	
	/*
	 * GTModelGeometry Entry File Naming
	 */
	@Test(expected = AssertionError.class)
	public void verifyGeometryEntryFile_invalid() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine",
				"asdf")));

		// execute
		this.testSuite.verifyGeometryEntryFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometryEntryFile_invalidDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine",
				"D501_S001_T001_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryEntryFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometryEntryFile_invalidExtension() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine",
				"D500_S001_T001_12345_001_amodelname.txt")));

		// execute
		this.testSuite.verifyGeometryEntryFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometryEntryFile_badCS1Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine",
				"D500_S1_T001_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryEntryFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometryEntryFile_badCS1Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine",
				"D500_SABC_T001_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryEntryFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometryEntryFile_badCS2Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine",
				"D500_S001_T1_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryEntryFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometryEntryFile_badCS2Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine",
				"D500_S001_TABC_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryEntryFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyGeometryEntryFile_badFCLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine",
				"D500_S001_T001_45_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryEntryFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyGeometryEntryFile_badFSCLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine",
				"D500_S001_T001_12345_1_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryEntryFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyGeometryEntryFile_badFSCType() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine",
				"D500_S001_T001_12345_ABC_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryEntryFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyGeometryEntryFile_badMODLLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine",
				"D500_S001_T001_12345_001_amodelnameamodelnameamodelnameamodelname.flt")));

		// execute
		this.testSuite.verifyGeometryEntryFile();
	}

	@Test
	public void verifyGeometryEntryFile_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine",
				"D500_S001_T001_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryEntryFile();
	}

	@Test
	public void verifyGeometryEntryFile_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyGeometryEntryFile();
	}
	
	/*
	 * GTModelGeometry LoD Naming
	 */
	
	@Test(expected = AssertionError.class)
	public void verifyGeometryLoDFile_invalid() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "510_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"asdf")));

		// execute
		this.testSuite.verifyGeometryLoDFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometryLoDFile_invalidDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "510_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D501_S001_T001_L10_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryLoDFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometryLoDFile_invalidExtension() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "510_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D510_S001_T001_L10_12345_001_amodelname.txt")));

		// execute
		this.testSuite.verifyGeometryLoDFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometryLoDFile_badCS1Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "510_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D510_S1_T001_L10_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryLoDFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometryLoDFile_badCS1Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "510_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D510_SABC_T001_L10_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryLoDFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyGeometryLoDFile_badCS1() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "510_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D510_S002_T001_L10_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryLoDFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometryLoDFile_badCS2Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "510_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D510_S001_T1_L10_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryLoDFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyGeometryLoDFile_badCS2Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "510_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D510_S001_TABC_L10_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryLoDFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyGeometryLoDFile_badCS2() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "510_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D510_S001_T002_L10_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryLoDFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyGeometryLoDFile_badFCLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "510_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D510_S001_T001_L10_45_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryLoDFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyGeometryLoDFile_badFSCLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "510_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D510_S001_T001_L10_12345_1_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryLoDFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyGeometryLoDFile_badFSCType() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "510_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D510_S001_T001_L10_12345_ABC_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryLoDFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyGeometryLoDFile_badMODLLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "510_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D510_S001_T001_L10_12345_001_amodelnameamodelnameamodelnameamodelname.flt")));

		// execute
		this.testSuite.verifyGeometryLoDFile();
	}

	@Test
	public void verifyGeometryLoDFile_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "510_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L10",
				"D510_S001_T001_L10_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyGeometryLoDFile();
	}

	@Test
	public void verifyGeometryLoDFile_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyGeometryLoDFile();
	}

}
