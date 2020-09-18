package org.opengis.cite.cdb10.cdbStructure;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import org.junit.Before;
import org.junit.Test;
import org.opengis.cite.cdb10.cdbStructure.GSModelGeometryStructureTests;

public class VerifyGSModelGeometryStructureTests extends StructureTestFixture<GSModelGeometryStructureTests> {
	
	protected final static Path SOURCE_DIRECTORY = Paths.get(System.getProperty("user.dir"), "src", "test", "java", "org", "opengis", "cite", "cdb10", "fixtures");
	private final static Path VALID_ZIP = SOURCE_DIRECTORY.resolve(Paths.get("valid", "GSModelGeometry.zip"));
	private final static Path EMPTY_ZIP = SOURCE_DIRECTORY.resolve(Paths.get("valid", "empty.zip"));
	private final static Path COMPRESSED_ZIP = SOURCE_DIRECTORY.resolve(Paths.get("invalid", "GSModelGeometry_compressed.zip"));
	
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
	
	/**
	 * Copy an empty ZIP archive onto "archivePath", then create a 1-byte length
	 * file entry in the ZIP archive named "entryName". Will replace any
	 * existing entries with the same name.
	 * @param archivePath Path to ZIP archive
	 * @param entryName Name of entry to create in ZIP archive
	 * @throws IOException
	 */
	protected void createArchiveWithEntryNamed(Path archivePath, String entryName) throws IOException {
		Files.copy(EMPTY_ZIP, archivePath);
		byte[] bytes = new byte[1];
		
		try (FileSystem fs = FileSystems.newFileSystem(archivePath, null)) {
			Path source = fs.getPath("/" + entryName);
			Files.write(source, bytes, CREATE, TRUNCATE_EXISTING);
		}
	}

	/*
	 * Verify GS Model Geometry filenames
	 */
	@Test
	public void verifyGSModelFile_valid() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
		Files.copy(VALID_ZIP, archive);

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
	
	/*
	 * Verify GS Model Geometry archive
	 */
	@Test
	public void verifyGSModelFileArchive_valid() throws IOException {
		// setup
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive);

		// execute
		this.testSuite.verifyGSModelFileArchive();
	}
	
	@Test
	public void verifyGSModelFileArchive_zeroZip() throws IOException {
		// setup
		Path archivePath = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
		byte[] bytes = new byte[0];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Zero-length ZIP archive");

		// execute
		this.testSuite.verifyGSModelFileArchive();
	}
	
	@Test
	public void verifyGSModelFileArchive_tooBigZip() throws IOException {
		// setup
		Path archivePath = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
		byte[] bytes = new byte[32000001];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("ZIP archive exceeds 32 Megabytes");

		// execute
		this.testSuite.verifyGSModelFileArchive();
	}
	
	@Test
	public void verifyGSModelFileArchive_notAZip() throws IOException {
		// setup
		Path archivePath = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
		byte[] bytes = new byte[100];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid ZIP archive file");

		// execute
		this.testSuite.verifyGSModelFileArchive();
	}
	
	@Test
	public void verifyGSModelFileArchive_compressedEntry() throws IOException {
		// setup
		Path archivePath = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
		Files.copy(COMPRESSED_ZIP, archivePath, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("should not be compressed");

		// execute
		this.testSuite.verifyGSModelFileArchive();
	}
	
	/*
	 * Verify GS Model Geometry entries
	 */
	@Test
	public void verifyGSModelGeometryEntry_valid() throws IOException {
		// setup
		String filename = "N62W162_D300_S001_T001_L07_U38_R102_AL015_116_AcmeFactory.flt";
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
		createArchiveWithEntryNamed(archive, filename);

		// execute
		this.testSuite.verifyGSModelGeometryEntry();
	}
	
	@Test
	public void verifyGSModelGeometryEntry_invalid() throws IOException {
		// setup
		String filename = "0.flt";
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
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
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
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
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
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
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
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
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
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
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
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
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
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
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
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
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
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
		Path archive = createGSModelGeometryArchive("N62W162_D300_S001_T001_L07_U38_R102.zip");
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid archive extension: txt");

		// execute
		this.testSuite.verifyGSModelGeometryEntry();
	}
}
