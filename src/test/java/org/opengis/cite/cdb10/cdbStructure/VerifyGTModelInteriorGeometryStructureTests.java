package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class VerifyGTModelInteriorGeometryStructureTests extends StructureTestFixture<GTModelInteriorGeometryStructureTests> {
	
	public VerifyGTModelInteriorGeometryStructureTests() throws IOException {
		this.testSuite = new GTModelInteriorGeometryStructureTests();
	}
	
	/*
	 * GTModelInteriorGeometry File Naming
	 */
	@Test(expected = AssertionError.class)
	public void verifyInteriorGeometryFile_invalid() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "506_GTModelInteriorGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L20",
				"asdf")));

		// execute
		this.testSuite.verifyInteriorGeometryFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyInteriorGeometryFile_invalidDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "506_GTModelInteriorGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L20",
				"D501_S001_T001_L20_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyInteriorGeometryFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyInteriorGeometryFile_invalidExtension() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "506_GTModelInteriorGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L20",
				"D506_S001_T001_L20_12345_001_amodelname.txt")));

		// execute
		this.testSuite.verifyInteriorGeometryFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyInteriorGeometryFile_badCS1Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "506_GTModelInteriorGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L20",
				"D506_S1_T001_L20_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyInteriorGeometryFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyInteriorGeometryFile_badCS1Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "506_GTModelInteriorGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L20",
				"D506_SABC_T001_L20_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyInteriorGeometryFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyInteriorGeometryFile_badCS1() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "506_GTModelInteriorGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L20",
				"D506_S002_T001_L20_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyInteriorGeometryFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyInteriorGeometryFile_badCS2Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "506_GTModelInteriorGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L20",
				"D506_S001_T1_L20_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyInteriorGeometryFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyInteriorGeometryFile_badCS2Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "506_GTModelInteriorGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L20",
				"D506_S001_TABC_L20_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyInteriorGeometryFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyInteriorGeometryFile_badCS2() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "506_GTModelInteriorGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L20",
				"D506_S001_T000_L20_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyInteriorGeometryFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyInteriorGeometryFile_badFCLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "506_GTModelInteriorGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L20",
				"D506_S001_T001_L20_45_001_amodelname.flt")));

		// execute
		this.testSuite.verifyInteriorGeometryFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyInteriorGeometryFile_badFSCLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "506_GTModelInteriorGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L20",
				"D506_S001_T001_L20_12345_1_amodelname.flt")));

		// execute
		this.testSuite.verifyInteriorGeometryFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyInteriorGeometryFile_badFSCType() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "506_GTModelInteriorGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L20",
				"D506_S001_T001_L20_12345_ABC_amodelname.flt")));

		// execute
		this.testSuite.verifyInteriorGeometryFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyInteriorGeometryFile_badMODLLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "506_GTModelInteriorGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L20",
				"D506_S001_T001_L20_12345_001_amodelnameamodelnameamodelnameamodelname.flt")));

		// execute
		this.testSuite.verifyInteriorGeometryFile();
	}

	@Test
	public void verifyInteriorGeometryFile_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "506_GTModelInteriorGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L20",
				"D506_S001_T001_L20_12345_001_amodelname.flt")));

		// execute
		this.testSuite.verifyInteriorGeometryFile();
	}

	@Test
	public void verifyInteriorGeometryFile_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyInteriorGeometryFile();
	}

}
