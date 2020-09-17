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

	/*
	 * Verify GS Model Geometry filenames
	 */
	@Test
	public void verifyGSModelFile_valid() throws IOException {
		// setup
		Path archive = this.cdb_root.resolve(
				Paths.get("Tiles", "300_GSModelGeometry",
						"N62W162_D300_S001_T001_L07_U38_R102.zip"));
		Files.copy(EMPTY_ZIP, archive);

		// execute
		this.testSuite.verifyGSModelFile();
	}
	
	@Test
	public void verifyGSModelFile_invalid() throws IOException {
		// setup
		Path archive = this.cdb_root.resolve(
				Paths.get("Tiles", "300_GSModelGeometry",
						"0.zip"));
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid file name");

		// execute
		this.testSuite.verifyGSModelFile();
	}

}
