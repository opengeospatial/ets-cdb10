package org.opengis.cite.cdb10.cdbStructure;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.opengis.cite.cdb10.cdbStructure.GSModelGeometryStructureTests;

public class VerifyGSModelGeometryStructureTests extends StructureTestFixture<GSModelGeometryStructureTests> {
	
	public VerifyGSModelGeometryStructureTests() throws IOException {
		this.testSuite = new GSModelGeometryStructureTests();
	}

	/*
	 * Verify GS Model Geometry filenames
	 */
	@Test
	public void verifyGSModelFile_valid() throws IOException {
		// setup
		Files.createDirectories(this.cdb_root.resolve(
				Paths.get("Tiles", "300_GSModelGeometry",
						"N62W162_D300_S001_T001_L07_U38_R102.zip")));

		// execute
		this.testSuite.verifyGSModelFile();
	}

}
