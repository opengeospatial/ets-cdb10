package org.opengis.cite.cdb10.cdbStructure.GSModel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import org.junit.Test;
import org.opengis.cite.cdb10.cdbStructure.GSModel.GSModelInteriorGeometryStructureTests;

public class VerifyGSModelInteriorGeometryStructureTests extends GSModelStructureTests<GSModelInteriorGeometryStructureTests> {
	
	protected final static Path SOURCE_DIRECTORY = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "org", "opengis", "cite", "cdb10", "fixtures");
	private final static Path EMPTY_ZIP = SOURCE_DIRECTORY.resolve(Paths.get("valid", "empty.zip"));
	
	protected static final String VALID_ARCHIVE_NAME = "N62W162_D305_S001_T001_L07_U38_R102.zip";
	protected static final String VALID_ENTRY_NAME = "N62W162_D305_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.flt";
	protected static final String VALID_LOD = "L07";
	protected static final String VALID_UREF = "U38";
	
	public VerifyGSModelInteriorGeometryStructureTests() throws IOException {
		this.testSuite = new GSModelInteriorGeometryStructureTests();
	}
	
	/**
	 * Creates a Path for a GSModelInteriorGeometry archive with a custom filename.
	 * Filename must include file extension. Archive will be placed in:
	 * CDB Root > Tiles > 305_GSModelInteriorGeometry > Lod > Uref
	 * 
	 * @param archiveFilename
	 * @return Path for GSModelInteriorGeometry archive file
	 * @throws IOException 
	 */
	protected Path createGSModelInteriorGeometryArchive(String archiveFilename) throws IOException {
		Path parentDir = Paths.get("Tiles", 
				GSModelInteriorGeometryStructureTests.DATASET_DIRECTORY, VALID_LOD, VALID_UREF);
		
		Files.createDirectories(this.cdb_root.resolve(parentDir));
		
		return this.cdb_root.resolve(Paths.get(parentDir.toString(), archiveFilename));
	}

	/*
	 * Verify GS Model Interior Geometry filenames
	 */
	@Test
	public void verifyGSModelInteriorGeometryFile_valid() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFile();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryFile_invalid() throws IOException {
		// setup
		Path archive = createGSModelInteriorGeometryArchive("0.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid file name");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFile();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryFile_invalidLatitude() throws IOException {
		// setup
		Path archive = createGSModelInteriorGeometryArchive("N99W162_D305_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid latitude (N99)");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFile();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryFile_invalidLongitude() throws IOException {
		// setup
		Path archive = createGSModelInteriorGeometryArchive("N62W999_D305_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid longitude (W999)");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFile();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryFile_invalidDataset() throws IOException {
		// setup
		Path archive = createGSModelInteriorGeometryArchive("N62W162_D000_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid code 000");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFile();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryFile_invalidCS1() throws IOException {
		// setup
		Path archive = createGSModelInteriorGeometryArchive("N62W162_D305_S000_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 1 (000) for Dataset (305)");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFile();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryFile_invalidCS2() throws IOException {
		// setup
		Path archive = createGSModelInteriorGeometryArchive("N62W162_D305_S001_T000_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 2 (000) for CS1 (001) and Dataset (305)");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFile();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryFile_invalidLod() throws IOException {
		// setup
		Path archive = createGSModelInteriorGeometryArchive("N62W162_D305_S001_T001_L99_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid LOD name: L99");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFile();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryFile_invalidUref() throws IOException {
		// setup
		Path archive = createGSModelInteriorGeometryArchive("N62W162_D305_S001_T001_L07_U999_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("UREF value out of bounds: 999");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFile();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryFile_invalidRref() throws IOException {
		// setup
		Path archive = createGSModelInteriorGeometryArchive("N62W162_D305_S001_T001_L07_U38_R9999.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("RREF out of bounds for LOD: 9999");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFile();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryFile_invalidExt() throws IOException {
		// setup
		Path archive = createGSModelInteriorGeometryArchive("N62W162_D305_S001_T001_L07_U38_R102.7z");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid archive extension: 7z");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFile();
	}
	
	/*
	 * Verify GS Model Interior Geometry archive
	 */
	@Test
	public void verifyGSModelInteriorGeometryFileArchive_valid() throws IOException {
		// setup
		Path archive = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		Files.copy(EMPTY_ZIP, archive);

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFileArchive();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryFileArchive_zeroZip() throws IOException {
		// setup
		Path archivePath = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		byte[] bytes = new byte[0];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Zero-length ZIP archive");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFileArchive();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryFileArchive_tooBigZip() throws IOException {
		// setup
		Path archivePath = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		byte[] bytes = new byte[32000001];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("ZIP archive exceeds 32 Megabytes");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFileArchive();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryFileArchive_notAZip() throws IOException {
		// setup
		Path archivePath = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		byte[] bytes = new byte[100];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid ZIP archive file");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFileArchive();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryFileArchive_compressedEntry() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		createArchiveWithCompressedEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("should not be compressed");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryFileArchive();
	}
	
	/*
	 * Verify GS Model Interior Geometry entries
	 */
	@Test
	public void verifyGSModelInteriorGeometryEntry_valid() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);

		// execute
		this.testSuite.verifyGSModelInteriorGeometryEntry();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryEntry_invalid() throws IOException {
		// setup
		String filename = "0.flt";
		Path archive = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid entry '0.flt' in ZIP archive");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryEntry();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryEntry_invalidLatitude() throws IOException {
		// setup
		String filename = "N92W162_D305_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid latitude (N92)");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryEntry();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryEntry_invalidLongitude() throws IOException {
		// setup
		String filename = "N62W962_D305_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid longitude (W962)");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryEntry();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryEntry_invalidDataset() throws IOException {
		// setup
		String filename = "N62W162_D000_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid code 000 in entry");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryEntry();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryEntry_invalidCS1() throws IOException {
		// setup
		String filename = "N62W162_D305_S000_T001_L07_U38_R102_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 1 (000) for Dataset (305)");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryEntry();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryEntry_invalidCS2() throws IOException {
		// setup
		String filename = "N62W162_D305_S001_T000_L07_U38_R102_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 2 (000) for CS1 (001) and Dataset (305)");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryEntry();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryEntry_invalidLod() throws IOException {
		// setup
		String filename = "N62W162_D305_S001_T001_L99_U38_R102_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid LOD name: L99");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryEntry();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryEntry_invalidUref() throws IOException {
		// setup
		String filename = "N62W162_D305_S001_T001_L07_U999_R102_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("UREF value out of bounds: 999");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryEntry();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryEntry_invalidRref() throws IOException {
		// setup
		String filename = "N62W162_D305_S001_T001_L07_U38_R9999_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("RREF out of bounds for LOD: 9999");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryEntry();
	}
	
	@Test
	public void verifyGSModelInteriorGeometryEntry_invalidExt() throws IOException {
		// setup
		String filename = "N62W162_D305_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.txt";
		Path archive = createGSModelInteriorGeometryArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid archive extension: txt");

		// execute
		this.testSuite.verifyGSModelInteriorGeometryEntry();
	}
}
