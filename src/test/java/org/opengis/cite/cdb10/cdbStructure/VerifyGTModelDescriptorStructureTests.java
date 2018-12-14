package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class VerifyGTModelDescriptorStructureTests extends StructureTestFixture<GTModelDescriptorStructureTests> {
	
	public VerifyGTModelDescriptorStructureTests() throws IOException {
		this.testSuite = new GTModelDescriptorStructureTests();
	}
	
	/*
	 * GTModelDescriptor Entry File Naming
	 */
	@Test(expected = AssertionError.class)
	public void verifyDescriptorFile_invalid() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "503_GTModelDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"asdf")));

		// execute
		this.testSuite.verifyDescriptorFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyDescriptorFile_invalidDataset() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "503_GTModelDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D501_S001_T001_12345_001_amodelname.xml")));

		// execute
		this.testSuite.verifyDescriptorFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyDescriptorFile_invalidExtension() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "503_GTModelDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D503_S001_T001_12345_001_amodelname.txt")));

		// execute
		this.testSuite.verifyDescriptorFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyDescriptorFile_badCS1Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "503_GTModelDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D503_S1_T001_12345_001_amodelname.xml")));

		// execute
		this.testSuite.verifyDescriptorFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyDescriptorFile_badCS1Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "503_GTModelDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D503_SABC_T001_12345_001_amodelname.xml")));

		// execute
		this.testSuite.verifyDescriptorFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyDescriptorFile_badCS2Length() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "503_GTModelDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D503_S001_T1_12345_001_amodelname.xml")));

		// execute
		this.testSuite.verifyDescriptorFile();
	}

	@Test(expected = AssertionError.class)
	public void verifyDescriptorFile_badCS2Type() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "503_GTModelDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D503_S001_TABC_12345_001_amodelname.xml")));

		// execute
		this.testSuite.verifyDescriptorFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyDescriptorFile_badFCLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "503_GTModelDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D503_S001_T001_45_001_amodelname.xml")));

		// execute
		this.testSuite.verifyDescriptorFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyDescriptorFile_badFSCLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "503_GTModelDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D503_S001_T001_12345_1_amodelname.xml")));

		// execute
		this.testSuite.verifyDescriptorFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyDescriptorFile_badFSCType() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "503_GTModelDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D503_S001_T001_12345_ABC_amodelname.xml")));

		// execute
		this.testSuite.verifyDescriptorFile();
	}
	
	@Test(expected = AssertionError.class)
	public void verifyDescriptorFile_badMODLLength() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "503_GTModelDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D503_S001_T001_12345_001_amodelnameamodelnameamodelnameamodelname.xml")));

		// execute
		this.testSuite.verifyDescriptorFile();
	}

	@Test
	public void verifyDescriptorFile_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "503_GTModelDescriptor",
				"A_Culture", "A_Extraction", "010_Mine",
				"D503_S001_T001_12345_001_amodelname.xml")));

		// execute
		this.testSuite.verifyDescriptorFile();
	}

	@Test
	public void verifyDescriptorFile_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyDescriptorFile();
	}
}
