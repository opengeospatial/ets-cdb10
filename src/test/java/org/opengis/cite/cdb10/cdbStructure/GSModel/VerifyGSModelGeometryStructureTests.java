package org.opengis.cite.cdb10.cdbStructure.GSModel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import org.junit.Test;
import org.opengis.cite.cdb10.cdbStructure.GSModel.GSModelGeometryStructureTests;

public class VerifyGSModelGeometryStructureTests extends GSModelStructureTests<GSModelGeometryStructureTests> {
	
	protected final static Path SOURCE_DIRECTORY = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "org", "opengis", "cite", "cdb10", "fixtures");
	private final static Path EMPTY_ZIP = SOURCE_DIRECTORY.resolve(Paths.get("valid", "empty.zip"));
	
	protected static final String VALID_ARCHIVE_NAME = "N62W162_D300_S001_T001_L07_U38_R102.zip";
	protected static final String VALID_ENTRY_NAME = "N62W162_D300_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.flt";
	
	public VerifyGSModelGeometryStructureTests() throws IOException {
		this.testSuite = new GSModelGeometryStructureTests();
	}
	
	/**
	 * Creates a Path for a GSmodelGeometry archive with a custom filename.
	 * Filename must include file extension. Archive will be placed in:
	 * CDB Root > Tiles > 300_GSModelGeometry > Lod > Uref
	 * 
	 * @param archiveFilename
	 * @param Lod				Full string for Level of Detail (including "L")
	 * @param Uref				Full string for UREF (including "U")
	 * @return Path for GSModelGeometry archive file
	 * @throws IOException Error creating parent directory
	 */
	protected Path createGSModelGeometryArchive(String archiveFilename, String Lod, String Uref) throws IOException {
		Path parentDir = Paths.get("Tiles", 
				GSModelGeometryStructureTests.DATASET_DIRECTORY, Lod, Uref);
		
		Files.createDirectories(this.cdb_root.resolve(parentDir));
		
		return this.cdb_root.resolve(Paths.get(parentDir.toString(), archiveFilename));
	}

	/*
	 * Verify GS Model Geometry filenames
	 */
	@Test
	public void verifyGSModelGeometryFile_valid() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		createArchiveWithEntryNamed(archive, filename);

		// execute
		this.testSuite.verifyGSModelGeometryFile();
	}
	
	@Test
	public void verifyGSModelGeometryFile_invalid() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("0.zip", "L07", "U38");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid file name");

		// execute
		this.testSuite.verifyGSModelGeometryFile();
	}
	
	@Test
	public void verifyGSModelGeometryFile_invalidLatitude() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N99W162_D300_S001_T001_L07_U38_R102.zip", "L07", "U38");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid latitude (N99)");

		// execute
		this.testSuite.verifyGSModelGeometryFile();
	}
	
	@Test
	public void verifyGSModelGeometryFile_invalidLongitude() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W999_D300_S001_T001_L07_U38_R102.zip", "L07", "U38");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid longitude (W999)");

		// execute
		this.testSuite.verifyGSModelGeometryFile();
	}
	
	@Test
	public void verifyGSModelGeometryFile_invalidDataset() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D000_S001_T001_L07_U38_R102.zip", "L07", "U38");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid code 000");

		// execute
		this.testSuite.verifyGSModelGeometryFile();
	}
	
	@Test
	public void verifyGSModelGeometryFile_invalidCS1() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D300_S000_T001_L07_U38_R102.zip", "L07", "U38");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 1 (000) for Dataset (300)");

		// execute
		this.testSuite.verifyGSModelGeometryFile();
	}
	
	@Test
	public void verifyGSModelGeometryFile_invalidCS2() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T000_L07_U38_R102.zip", "L07", "U38");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 2 (000) for CS1 (001) and Dataset (300)");

		// execute
		this.testSuite.verifyGSModelGeometryFile();
	}
	
	@Test
	public void verifyGSModelGeometryFile_invalidLod() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L99_U38_R102.zip", "L07", "U38");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid LOD name: L99");

		// execute
		this.testSuite.verifyGSModelGeometryFile();
	}
	
	@Test
	public void verifyGSModelGeometryFile_invalidUref() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U999_R102.zip", "L07", "U38");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("UREF value out of bounds: 999");

		// execute
		this.testSuite.verifyGSModelGeometryFile();
	}
	
	@Test
	public void verifyGSModelGeometryFile_invalidRref() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R9999.zip", "L07", "U38");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("RREF out of bounds for LOD: 9999");

		// execute
		this.testSuite.verifyGSModelGeometryFile();
	}
	
	@Test
	public void verifyGSModelGeometryFile_invalidExt() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.7z", "L07", "U38");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid archive extension: 7z");

		// execute
		this.testSuite.verifyGSModelGeometryFile();
	}
	
	/*
	 * Verify GS Model Geometry archive
	 */
	@Test
	public void verifyGSModelGeometryFileArchive_valid() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		Files.copy(EMPTY_ZIP, archive);

		// execute
		this.testSuite.verifyGSModelGeometryFileArchive();
	}
	
	@Test
	public void verifyGSModelGeometryFileArchive_zeroZip() throws IOException {
		// setup
		Path archivePath = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		byte[] bytes = new byte[0];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Zero-length ZIP archive");

		// execute
		this.testSuite.verifyGSModelGeometryFileArchive();
	}
	
	@Test
	public void verifyGSModelGeometryFileArchive_tooBigZip() throws IOException {
		// setup
		Path archivePath = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		byte[] bytes = new byte[32000001];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("ZIP archive exceeds 32 Megabytes");

		// execute
		this.testSuite.verifyGSModelGeometryFileArchive();
	}
	
	@Test
	public void verifyGSModelGeometryFileArchive_notAZip() throws IOException {
		// setup
		Path archivePath = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		byte[] bytes = new byte[100];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid ZIP archive file");

		// execute
		this.testSuite.verifyGSModelGeometryFileArchive();
	}
	
	@Test
	public void verifyGSModelGeometryFileArchive_compressedEntry() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		createArchiveWithCompressedEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("should not be compressed");

		// execute
		this.testSuite.verifyGSModelGeometryFileArchive();
	}
	
	/*
	 * Verify GS Model Geometry entries
	 */
	@Test
	public void verifyGSModelGeometryEntry_valid() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		createArchiveWithEntryNamed(archive, filename);

		// execute
		this.testSuite.verifyGSModelGeometryEntry();
	}
	
	@Test
	public void verifyGSModelGeometryEntry_invalid() throws IOException {
		// setup
		String filename = "0.flt";
		Path archive = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid entry '0.flt' in ZIP archive");

		// execute
		this.testSuite.verifyGSModelGeometryEntry();
	}
	
	@Test
	public void verifyGSModelGeometryEntry_invalidLatitude() throws IOException {
		// setup
		String filename = "N92W162_D300_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid latitude (N92)");

		// execute
		this.testSuite.verifyGSModelGeometryEntry();
	}
	
	@Test
	public void verifyGSModelGeometryEntry_invalidLongitude() throws IOException {
		// setup
		String filename = "N62W962_D300_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid longitude (W962)");

		// execute
		this.testSuite.verifyGSModelGeometryEntry();
	}
	
	@Test
	public void verifyGSModelGeometryEntry_invalidDataset() throws IOException {
		// setup
		String filename = "N62W162_D000_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid code 000 in entry");

		// execute
		this.testSuite.verifyGSModelGeometryEntry();
	}
	
	@Test
	public void verifyGSModelGeometryEntry_invalidCS1() throws IOException {
		// setup
		String filename = "N62W162_D300_S000_T001_L07_U38_R102_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 1 (000) for Dataset (300)");

		// execute
		this.testSuite.verifyGSModelGeometryEntry();
	}
	
	@Test
	public void verifyGSModelGeometryEntry_invalidCS2() throws IOException {
		// setup
		String filename = "N62W162_D300_S001_T000_L07_U38_R102_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 2 (000) for CS1 (001) and Dataset (300)");

		// execute
		this.testSuite.verifyGSModelGeometryEntry();
	}
	
	@Test
	public void verifyGSModelGeometryEntry_invalidLod() throws IOException {
		// setup
		String filename = "N62W162_D300_S001_T001_L99_U38_R102_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid LOD name: L99");

		// execute
		this.testSuite.verifyGSModelGeometryEntry();
	}
	
	@Test
	public void verifyGSModelGeometryEntry_invalidUref() throws IOException {
		// setup
		String filename = "N62W162_D300_S001_T001_L07_U999_R102_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("UREF value out of bounds: 999");

		// execute
		this.testSuite.verifyGSModelGeometryEntry();
	}
	
	@Test
	public void verifyGSModelGeometryEntry_invalidRref() throws IOException {
		// setup
		String filename = "N62W162_D300_S001_T001_L07_U38_R9999_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("RREF out of bounds for LOD: 9999");

		// execute
		this.testSuite.verifyGSModelGeometryEntry();
	}
	
	@Test
	public void verifyGSModelGeometryEntry_invalidExt() throws IOException {
		// setup
		String filename = "N62W162_D300_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.txt";
		Path archive = createGSModelGeometryArchive(VALID_ARCHIVE_NAME, "L07", "U38");
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid archive extension: txt");

		// execute
		this.testSuite.verifyGSModelGeometryEntry();
	}
}
