package org.opengis.cite.cdb10.cdbStructure;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import org.junit.Before;
import org.junit.Test;
import org.opengis.cite.cdb10.cdbStructure.GSModelGeometryStructureTests;

public class VerifyGSModelGeometryStructureTests extends StructureTestFixture<GSModelGeometryStructureTests> {
	
	protected final static Path SOURCE_DIRECTORY = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "org", "opengis", "cite", "cdb10", "fixtures");
	private final static Path EMPTY_ZIP = SOURCE_DIRECTORY.resolve(Paths.get("valid", "empty.zip"));
	
	public VerifyGSModelGeometryStructureTests() throws IOException {
		this.testSuite = new GSModelGeometryStructureTests();
	}
	
	@Before
    public void setupDirs() throws IOException {
    	Files.createDirectories(this.cdb_root.resolve(Paths.get("Tiles", "300_GSModelGeometry")));
    }
	
	/**
	 * Creates a Path for a GSmodelGeometry archive with a custom filename.
	 * Filename must include file extension. Archive will be placed in:
	 * CDB Root > Tiles > 300_GSModelGeometry
	 * 
	 * @param archiveFilename
	 * @return Path for GSModelGeometry archive file
	 */
	protected Path createGSModelGeometryArchive(String archiveFilename) {
		return this.cdb_root.resolve(Paths.get("Tiles", "300_GSModelGeometry", archiveFilename));
	}

	/*
	 * Verify GS Model Geometry filenames
	 */
	@Test
	public void verifyGSModelFile_valid() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive);

		// execute
		this.testSuite.verifyGSModelFile();
	}
	
	@Test
	public void verifyGSModelFile_invalid() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("0.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid file name");

		// execute
		this.testSuite.verifyGSModelFile();
	}
	
	@Test
	public void verifyGSModelFile_invalidLatitude() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N99W162_D300_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid latitude (N99)");

		// execute
		this.testSuite.verifyGSModelFile();
	}
	
	@Test
	public void verifyGSModelFile_invalidLongitude() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W999_D300_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid longitude (W999)");

		// execute
		this.testSuite.verifyGSModelFile();
	}
	
	@Test
	public void verifyGSModelFile_invalidDataset() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D000_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid code 000");

		// execute
		this.testSuite.verifyGSModelFile();
	}
	
	@Test
	public void verifyGSModelFile_invalidCS1() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D300_S000_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 1 (000) for Dataset (300)");

		// execute
		this.testSuite.verifyGSModelFile();
	}
	
	@Test
	public void verifyGSModelFile_invalidCS2() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T000_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 2 (000) for CS1 (001) and Dataset (300)");

		// execute
		this.testSuite.verifyGSModelFile();
	}
	
	@Test
	public void verifyGSModelFile_invalidLod() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L99_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid LOD name: L99");

		// execute
		this.testSuite.verifyGSModelFile();
	}
	
	@Test
	public void verifyGSModelFile_invalidUref() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U999_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("UREF value out of bounds: 999");

		// execute
		this.testSuite.verifyGSModelFile();
	}
	
	@Test
	public void verifyGSModelFile_invalidRref() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R9999.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("RREF out of bounds for LOD: 9999");

		// execute
		this.testSuite.verifyGSModelFile();
	}
	
	@Test
	public void verifyGSModelFile_invalidExt() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.7z");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid archive extension: 7z");

		// execute
		this.testSuite.verifyGSModelFile();
	}
}
