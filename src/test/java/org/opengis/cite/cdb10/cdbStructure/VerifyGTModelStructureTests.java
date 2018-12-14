package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.opengis.cite.cdb10.TestFixture;

public class VerifyGTModelStructureTests extends StructureTestFixture<GTModelStructureTests> {

	public VerifyGTModelStructureTests() throws IOException {
		this.testSuite = new GTModelStructureTests();
	}

	@Test(expected = AssertionError.class)
	public void verifyDataset_BadShort() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "5_GTModelGeometry")));

		// execute
		this.testSuite.verifyDataset();
	}

	@Test(expected = AssertionError.class)
	public void verifyDataset_BadNumber() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "5xx_GTModelGeometry")));

		// execute
		this.testSuite.verifyDataset();
	}

	@Test(expected = AssertionError.class)
	public void verifyDataset_BadRange() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "-1_GTModelGeometry")));

		// execute
		this.testSuite.verifyDataset();
	}

	@Test(expected = AssertionError.class)
	public void verifyDataset_BadValue() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "999_GTModelGeometry")));

		// execute
		this.testSuite.verifyDataset();
	}

	@Test
	public void verifyDataset_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry")));

		// execute
		this.testSuite.verifyDataset();
	}

	@Test
	public void verifyDataset_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyDataset();
	}



	@Test(expected = AssertionError.class)
	public void verifyCategory_WrongCategory() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"B_Culture")));

		// execute
		this.testSuite.verifyCategory();
	}

	@Test(expected = AssertionError.class)
	public void verifyCategory_WrongLabel() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Hydrography")));

		// execute
		this.testSuite.verifyCategory();
	}

	@Test(expected = AssertionError.class)
	public void verifyCategory_UnspecifiedCategory() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"Z_Culture")));

		// execute
		this.testSuite.verifyCategory();
	}

	@Test(expected = AssertionError.class)
	public void verifyCategory_UnspecifiedLabel() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Bogus")));

		// execute
		this.testSuite.verifyCategory();
	}

	@Test
	public void verifyCategory_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture")));

		// execute
		this.testSuite.verifyCategory();
	}

	@Test
	public void verifyCategory_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyCategory();
	}



	@Test(expected = AssertionError.class)
	public void verifySubcategory_WrongCategory() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "B_Extraction")));

		// execute
		this.testSuite.verifySubcategory();
	}

	@Test(expected = AssertionError.class)
	public void verifySubcategory_WrongLabel() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Disposal")));

		// execute
		this.testSuite.verifySubcategory();
	}

	@Test(expected = AssertionError.class)
	public void verifySubcategory_UnspecifiedCategory() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "ZZZZ_Extraction")));

		// execute
		this.testSuite.verifySubcategory();
	}

	@Test(expected = AssertionError.class)
	public void verifySubcategory_UnspecifiedLabel() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Bogus")));

		// execute
		this.testSuite.verifySubcategory();
	}

	@Test
	public void verifySubcategory_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction")));

		// execute
		this.testSuite.verifySubcategory();
	}

	@Test
	public void verifySubcategory_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifySubcategory();
	}



	@Test(expected = AssertionError.class)
	public void verifyFeatureType_WrongCategory() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "001_Mine")));

		// execute
		this.testSuite.verifyFeatureType();
	}

	@Test(expected = AssertionError.class)
	public void verifyFeatureType_WrongLabel() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Quarry_Wall")));

		// execute
		this.testSuite.verifyFeatureType();
	}

	@Test(expected = AssertionError.class)
	public void verifyFeatureType_UnspecifiedCategory() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "999_Mine")));

		// execute
		this.testSuite.verifyFeatureType();
	}

	@Test(expected = AssertionError.class)
	public void verifyFeatureType_UnspecifiedLabel() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Bogus")));

		// execute
		this.testSuite.verifyFeatureType();
	}

	@Test
	public void verifyFeatureType_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine")));

		// execute
		this.testSuite.verifyFeatureType();
	}

	@Test
	public void verifyFeatureType_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyFeatureType();
	}



	@Test(expected = AssertionError.class)
	public void verifyLOD_BadLevel() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "LA")));

		// execute
		this.testSuite.verifyLOD();
	}

	@Test(expected = AssertionError.class)
	public void verifyLOD_TooHigh() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L99")));

		// execute
		this.testSuite.verifyLOD();
	}

	@Test(expected = AssertionError.class)
	public void verifyLOD_NoPadding() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L9")));

		// execute
		this.testSuite.verifyLOD();
	}

	@Test
	public void verifyLOD_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "500_GTModelGeometry",
				"A_Culture", "A_Extraction", "010_Mine", "L23")));

		// execute
		this.testSuite.verifyLOD();
	}

	@Test
	public void verifyLOD_Skip() throws IOException {
		// setup
		// execute
		this.testSuite.verifyLOD();
	}
	
	
	/*
	 * TNAM Prefix Directory
	 */
	@Test(expected = AssertionError.class)
	public void verifyTNAMPrefix_TooLong() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"ABC")));

		// execute
		this.testSuite.verifyTNAMPrefix();
	}

	@Test(expected = AssertionError.class)
	public void verifyTNAMPrefix_WrongCase() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"a")));

		// execute
		this.testSuite.verifyTNAMPrefix();
	}

	@Test
	public void verifyTNAMPrefix_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
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
	
	/*
	 * TNAM Second Directory
	 */
	@Test(expected = AssertionError.class)
	public void verifyTNAMSecond_TooLong() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"A", "BCD")));

		// execute
		this.testSuite.verifyTNAMSecond();
	}

	@Test(expected = AssertionError.class)
	public void verifyTNAMSecond_WrongCase() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"A", "b")));

		// execute
		this.testSuite.verifyTNAMSecond();
	}

	@Test
	public void verifyTNAMSecond_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
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
	
	/*
	 * TNAM Directory 
	 */
	@Test(expected = AssertionError.class)
	public void verifyTNAM_TooShort() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"A", "B", "A")));

		// execute
		this.testSuite.verifyTNAM();
	}

	@Test(expected = AssertionError.class)
	public void verifyTNAM_TooLong() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"A", "B", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")));

		// execute
		this.testSuite.verifyTNAM();
	}

	@Test(expected = AssertionError.class)
	public void verifyTNAM_WrongChars() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"A", "B", "$$AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")));

		// execute
		this.testSuite.verifyTNAM();
	}

	@Test(expected = AssertionError.class)
	public void verifyTNAM_Mismatch() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
				"A", "B", "AC_1")));

		// execute
		this.testSuite.verifyTNAM();
	}

	@Test
	public void verifyTNAM_Good() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(Paths.get("GTModel", "511_GTModelTexture",
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
}
