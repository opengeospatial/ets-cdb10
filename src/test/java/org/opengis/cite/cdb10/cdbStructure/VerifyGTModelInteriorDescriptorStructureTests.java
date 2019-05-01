package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class VerifyGTModelInteriorDescriptorStructureTests  extends StructureTestFixture<GTModelInteriorDescriptorStructureTests> {
	
	public VerifyGTModelInteriorDescriptorStructureTests() throws IOException {
		this.testSuite = new GTModelInteriorDescriptorStructureTests();
	}
	
	/*
	 * GTModelInteriorDescriptor File Naming
	 */
	@Test(expected = AssertionError.class)
	public void verifyInteriorDescriptorFile_invalid() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "508_GTModelInteriorDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"asdf")));

		// execute
		this.testSuite.verifyInteriorDescriptorFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyInteriorDescriptorFile_invalidDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "508_GTModelInteriorDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D501_S001_T001_12345_001_amodelname.xml")));

		// execute
		this.testSuite.verifyInteriorDescriptorFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyInteriorDescriptorFile_invalidExtension() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "508_GTModelInteriorDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D508_S001_T001_12345_001_amodelname.txt")));

		// execute
		this.testSuite.verifyInteriorDescriptorFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyInteriorDescriptorFile_badCS1Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "508_GTModelInteriorDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D508_S1_T001_12345_001_amodelname.xml")));

		// execute
		this.testSuite.verifyInteriorDescriptorFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyInteriorDescriptorFile_badCS1Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "508_GTModelInteriorDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D508_SABC_T001_12345_001_amodelname.xml")));

		// execute
		this.testSuite.verifyInteriorDescriptorFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyInteriorDescriptorFile_badCS1() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "508_GTModelInteriorDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D508_S002_T001_12345_001_amodelname.xml")));

		// execute
		this.testSuite.verifyInteriorDescriptorFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyInteriorDescriptorFile_badCS2Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "508_GTModelInteriorDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D508_S001_T1_12345_001_amodelname.xml")));

		// execute
		this.testSuite.verifyInteriorDescriptorFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyInteriorDescriptorFile_badCS2Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "508_GTModelInteriorDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D508_S001_TABC_12345_001_amodelname.xml")));

		// execute
		this.testSuite.verifyInteriorDescriptorFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyInteriorDescriptorFile_badCS2() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "508_GTModelInteriorDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D508_S001_T002_12345_001_amodelname.xml")));

		// execute
		this.testSuite.verifyInteriorDescriptorFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyInteriorDescriptorFile_badFCLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "508_GTModelInteriorDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D508_S001_T001_45_001_amodelname.xml")));

		// execute
		this.testSuite.verifyInteriorDescriptorFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyInteriorDescriptorFile_badFSCLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "508_GTModelInteriorDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D508_S001_T001_12345_1_amodelname.xml")));

		// execute
		this.testSuite.verifyInteriorDescriptorFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyInteriorDescriptorFile_badFSCType() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "508_GTModelInteriorDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D508_S001_T001_12345_ABC_amodelname.xml")));

		// execute
		this.testSuite.verifyInteriorDescriptorFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyInteriorDescriptorFile_badMODLLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "508_GTModelInteriorDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D508_S001_T001_12345_001_amodelnameamodelnameamodelnameamodelname.xml")));

		// execute
		this.testSuite.verifyInteriorDescriptorFile();
	}

	@Test
	public void verifyInteriorDescriptorFile_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "508_GTModelInteriorDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D508_S001_T001_12345_001_amodelname.xml")));

		// execute
		this.testSuite.verifyInteriorDescriptorFile();
	}

	@Test
	public void verifyInteriorDescriptorFile_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyInteriorDescriptorFile();
	}

}
